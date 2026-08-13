package com.aldahir.escuela.services.grupos;

import com.aldahir.escuela.dtos.grupos.GrupoRequest;
import com.aldahir.escuela.dtos.grupos.GrupoResponse;
import com.aldahir.escuela.entities.Aula;
import com.aldahir.escuela.entities.Curso;
import com.aldahir.escuela.entities.Grupo;
import com.aldahir.escuela.entities.Maestro;
import com.aldahir.escuela.exceptions.EntidadRelacionalException;
import com.aldahir.escuela.mappers.GrupoMapper;
import com.aldahir.escuela.repositories.*;
import com.aldahir.escuela.utils.SerivceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GrupoServiceImpl implements GrupoService {

    private final GrupoRepository grupoRepository;

    private final GrupoMapper grupoMapper;

    private final CursoRepository cursoRepository;

    private final MaestroRepository maestroRepository;

    private final AulaRepository aulaRepository;

    private final InscripcionRepository inscripcionRepository;

    private final HorarioRepository horarioRepository;

    @Override
    public List<GrupoResponse> listar() {
        return grupoRepository.findAll().stream()
                .map(grupoMapper::entidadAResponse)
                .toList();
    }

    @Override
    public GrupoResponse obtenerPorId(Long id) {
        return grupoMapper.entidadAResponse(obtenerGrupo(id));
    }

    @Override
    public GrupoResponse registrar(GrupoRequest request) {

        log.info("Registrando grupo");

        if (existeRelacionesGrupo(request))
            throw new IllegalArgumentException("El grupo con el curso, aula, maestro y periodo ya existen");

        Curso curso = obtenerCurso(request.idCurso());
        Maestro maestro = obtenerMaestro(request.idMaestro());
        Aula aula = obtenerAula(request.idAula());

        Grupo grupo = grupoMapper.requestAEntidad(request);

        grupo.asignarCursoMaestroYAula(curso, maestro, aula);

        grupo.asignarPeriodo(request.periodo());

        grupoRepository.save(grupo);

        log.info("Grupo registrado");

        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public GrupoResponse actualizar(GrupoRequest request, Long id) {

        Grupo grupo = obtenerGrupo(id);

        if (!grupo.cambioEnRelaciones(request.idCurso(), request.idMaestro(), request.idAula(), request.periodo())) {
            throw new IllegalArgumentException("Los datos del grupo no han cambiado");
        }

        if (existeRelacionesGrupo(request))
            throw new IllegalArgumentException("El grupo con el curso, aula, maestro y periodo ya existen");

        Curso curso = obtenerCurso(request.idCurso());
        Maestro maestro = obtenerMaestro(request.idMaestro());
        Aula aula = obtenerAula(request.idAula());


        grupo.asignarCursoMaestroYAula(curso, maestro, aula);

        grupo.asignarPeriodo(request.periodo());

        log.info("Grupo actualizado");

        grupoRepository.save(grupo);

        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public void eliminar(Long id) {

        log.info("Eliminando grupo");

        Grupo grupo = obtenerGrupo(id);

        if (inscripcionRepository.existsByGrupoId(grupo.getId())) {
            throw new EntidadRelacionalException("No se puede eliminar un grupo con inscripciones");
        }

        if (horarioRepository.existsByGrupoId(grupo.getId())) {
            throw new EntidadRelacionalException("No se puede eliminar un grupo con horarios");
        }

        grupoRepository.delete(grupo);

        log.info("Grupo eliminado");
    }

    private Grupo obtenerGrupo(Long id) {
        return SerivceUtils.obtenerEntidadOException(grupoRepository, id, Grupo.class);
    }

    private Curso obtenerCurso(Long id) {
        return SerivceUtils.obtenerEntidadOException(cursoRepository, id, Curso.class);
    }

    private Maestro obtenerMaestro(Long id) {
        return SerivceUtils.obtenerEntidadOException(maestroRepository, id, Maestro.class);
    }

    private Aula obtenerAula(Long id) {
        return SerivceUtils.obtenerEntidadOException(aulaRepository, id, Aula.class);
    }

    private boolean existeRelacionesGrupo(GrupoRequest request) {
        if (grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodo(request.idCurso(), request.idMaestro(), request.idAula(), request.periodo()))
            throw new IllegalArgumentException("El grupo con el curso, aula, maestro y periodo ya existen");
        return false;
    }
}
