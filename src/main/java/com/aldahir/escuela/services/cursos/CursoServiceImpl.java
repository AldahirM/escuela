package com.aldahir.escuela.services.cursos;

import com.aldahir.escuela.dtos.cursos.CursoRequest;
import com.aldahir.escuela.dtos.cursos.CursoResponse;
import com.aldahir.escuela.entities.Curso;
import com.aldahir.escuela.exceptions.EntidadRelacionalException;
import com.aldahir.escuela.mappers.CursoMapper;
import com.aldahir.escuela.repositories.CursoRepository;
import com.aldahir.escuela.repositories.GrupoRepository;
import com.aldahir.escuela.utils.SerivceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    private final CursoMapper cursoMapper;

    private final GrupoRepository grupoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponse> listar() {
        log.info("Listando todos los cursos");

        return cursoRepository.findAll().stream()
                .map(cursoMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CursoResponse obtenerPorId(Long id) {

        log.info("Obteniendo curso por id: {}", id);

        return cursoMapper.entidadAResponse(obtenerCurso(id));
    }

    @Override
    public CursoResponse registrar(CursoRequest request) {

        log.info("Registrando nuevo curso");

        if (cursoRepository.existsByNombre(request.nombre()))
            throw new IllegalArgumentException("Un curso con este nombre ya existe");

        Curso curso = cursoMapper.requestAEntidad(request);

        cursoRepository.save(curso);

        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public CursoResponse actualizar(CursoRequest request, Long id) {

        log.info("Actualizando curso por id: {}", id);

        Curso curso = obtenerCurso(id);

        if (!curso.cambioEnNombre(request.nombre())) {
            log.info("El nombre del curso no ha cambiado");
            if (cursoRepository.existsByNombre(request.nombre()))
                throw new IllegalArgumentException("Un curso con este nombre ya existe");
        }

        curso.actualizar(request.nombre().trim(), request.descripcion(), request.creditos());

        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public void eliminar(Long id) {

        Curso curso = obtenerCurso(id);

        if (grupoRepository.existsByCursoId(id))
            throw new EntidadRelacionalException("No se puede eliminar este curso porque tiene grupos asignados");

        cursoRepository.delete(curso);

        log.info("Curso con id {} eliminado", id);

    }

    private Curso obtenerCurso(Long id) {
        return SerivceUtils.obtenerEntidadOException
                (cursoRepository, id, Curso.class);
    }
}
