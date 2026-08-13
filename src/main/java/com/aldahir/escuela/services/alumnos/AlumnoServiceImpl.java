package com.aldahir.escuela.services.alumnos;

import com.aldahir.escuela.dtos.alumnos.AlumnoRequest;
import com.aldahir.escuela.dtos.alumnos.AlumnoResponse;
import com.aldahir.escuela.entities.Alumno;
import com.aldahir.escuela.exceptions.EntidadRelacionalException;
import com.aldahir.escuela.mappers.AlumnoMapper;
import com.aldahir.escuela.repositories.AlumnoRepository;
import com.aldahir.escuela.repositories.InscripcionRepository;
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
public class AlumnoServiceImpl implements AlumnoService {
    private final AlumnoRepository alumnoRepository;

    private final AlumnoMapper alumnoMapper;

    private final InscripcionRepository inscripcionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoResponse> listar() {

        log.info("Listando todos los alumnos");

        return alumnoRepository.findAll().stream()
                .map(alumnoMapper::entidadAResponse)
                .toList();
    }

    @Override
    public AlumnoResponse obtenerPorId(Long id) {
        return alumnoMapper.entidadAResponse(obtenerAluno(id));
    }

    @Override
    public AlumnoResponse registrar(AlumnoRequest request) {

        log.info("Registrando nuevo alumno...");

        Alumno alumno = alumnoMapper.requestAEntidad(
                request,
                generarEmail(request),
                generarMatricula(request)
        );

        alumnoRepository.save(alumno);

        log.info("Alumno registrado exitosamente");

        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public AlumnoResponse actualizar(AlumnoRequest request, Long id) {

        Alumno alumno = obtenerAluno(id);

        log.info("Actualizando nuevo alumno...");

        if (alumno.cambioEnDatos(request.nombre().trim(), request.apellidoPaterno().trim(), request.apellidoMaterno().trim())) {
            alumno.actualizar(
                    request.nombre(),
                    request.apellidoPaterno(),
                    request.apellidoMaterno(),
                    generarEmail(request),
                    generarMatricula(request)
            );
        }else{
            log.info("Alumno no tuvo cambios");
        }

        log.info("Alumno actualizado exitosamente");

        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public void eliminar(Long id) {
        Alumno alumno = obtenerAluno(id);

        if(inscripcionRepository.existsByAlumnoId(id)){
            throw new EntidadRelacionalException("No se puede eliminar el alumno porque tiene relaciones con inscripciones");
        }

        log.info("Eliminando alumno con id {}", id);

        alumnoRepository.delete(alumno);

        log.info("Alumno eliminado exitosamente");
    }

    private Alumno obtenerAluno(Long id) {
        return SerivceUtils.obtenerEntidadOException(alumnoRepository, id, Alumno.class);

    }

    private String generarMatricula(AlumnoRequest request) {
        log.info("Gnenerando matricula...");

        String matricula = alumnoRepository.generarMatricula(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim()
        );
        log.info("Matricula generada: {}", matricula);
        return matricula;
    }

    private String generarEmail(AlumnoRequest request) {
        log.info("Gnenerando email...");

        String email = alumnoRepository.generarEmail(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim()
        );
        log.info("Email generado: {}", email);
        return email;
    }
}
