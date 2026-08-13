package com.aldahir.escuela.services.inscripciones;

import com.aldahir.escuela.dtos.inscripciones.InscripcionRequest;
import com.aldahir.escuela.dtos.inscripciones.InscripcionResponse;
import com.aldahir.escuela.entities.Alumno;
import com.aldahir.escuela.entities.Grupo;
import com.aldahir.escuela.entities.Inscripcion;
import com.aldahir.escuela.exceptions.EntidadRelacionalException;
import com.aldahir.escuela.mappers.InscripcionMapper;
import com.aldahir.escuela.repositories.*;
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
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;

    private final InscripcionMapper inscripcionMapper;

    private final AlumnoRepository alumnoRepository;

    private final GrupoRepository grupoRepository;

    private final CalificacionRepository calificacionesRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponse> listar() {

        log.info("Listando todas las inscripciones");


        return inscripcionRepository.findAll().stream()
                .map(inscripcionMapper::entidadAResponse)
                .toList();
    }

    @Override
    public InscripcionResponse obtenerPorId(Long id) {
        log.info("Obteniendo inscripcion por id");


        return inscripcionMapper.entidadAResponse(obtenerInscripcion(id));
    }

    @Override
    public InscripcionResponse registrar(InscripcionRequest request) {

        log.info("Registrando inscripcion");

        if (inscripcionRepository.existsByGrupoIdAndAlumnoId(request.idGrupo(), request.idAlumno()))
            throw new IllegalArgumentException("Ya existe un registro con el mismo alumno y grupo");

        Alumno alumno = obtenerAlumno(request.idAlumno());

        Grupo grupo = obtenerGrupo(request.idGrupo());

        Inscripcion inscripcion = inscripcionMapper.requestAEntidad(request, grupo, alumno);

        inscripcionRepository.save(inscripcion);

        return inscripcionMapper.entidadAResponse(inscripcion);
    }

    @Override
    public InscripcionResponse actualizar(InscripcionRequest request, Long id) {

        log.info("Actualizando inscripcion");

        if (inscripcionRepository.existsByGrupoIdAndAlumnoId(request.idGrupo(), request.idAlumno()))
            throw new IllegalArgumentException("Ya existe un registro con el mismo alumno y grupo");

        Inscripcion inscripcion = obtenerInscripcion(id);

        Grupo grupo = obtenerGrupo(request.idGrupo());

        Alumno alumno = obtenerAlumno(request.idAlumno());

        inscripcion.asignarAlumnoYGrupo(alumno, grupo);

        log.info("Inscripcion actualizada correctamente");

        return inscripcionMapper.entidadAResponse(inscripcion);
    }

    @Override
    public void eliminar(Long id) {

        log.info("Eliminando inscripcion");

        if (calificacionesRepository.existsByInscripcionId(id))
            throw new EntidadRelacionalException("No se puede eliminar la inscripción porque tiene relacion con una calificación");

        inscripcionRepository.deleteById(id);

        log.info("Inscripcion eliminada correctamente");

    }

    private Inscripcion obtenerInscripcion(Long id) {
        return SerivceUtils.obtenerEntidadOException(inscripcionRepository, id, Inscripcion.class);
    }

    private Alumno obtenerAlumno(Long id) {
        return SerivceUtils.obtenerEntidadOException(alumnoRepository, id, Alumno.class);
    }

    private Grupo obtenerGrupo(Long id) {
        return SerivceUtils.obtenerEntidadOException(grupoRepository, id, Grupo.class);
    }
}
