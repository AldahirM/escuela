package com.aldahir.escuela.services.calificaciones;

import com.aldahir.escuela.dtos.calificaciones.CalificacionRequest;
import com.aldahir.escuela.dtos.calificaciones.CalificacionResponse;
import com.aldahir.escuela.entities.Calificacion;
import com.aldahir.escuela.entities.Inscripcion;
import com.aldahir.escuela.mappers.CalificacionMapper;
import com.aldahir.escuela.repositories.CalificacionRepository;
import com.aldahir.escuela.repositories.InscripcionRepository;
import com.aldahir.escuela.utils.SerivceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CalificacionServiceImpl implements CalificacionService {

    private final CalificacionRepository calificacionRepository;

    private final CalificacionMapper calificacionMapper;

    private final InscripcionRepository inscripcionRepository;

    @Override
    public List<CalificacionResponse> listar() {

        log.info("Listando calificaciones");

        return calificacionRepository.findAll().stream()
                .map(calificacionMapper::entidadAResponse)
                .toList();
    }

    @Override
    public CalificacionResponse obtenerPorId(Long id) {

        log.info("Obteniendo calificacion por id");

        Calificacion calificacion = obtenerCalificacion(id);

        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public CalificacionResponse registrar(CalificacionRequest request) {

        log.info("Registrando calificacion");

        if (calificacionRepository.existsByInscripcionId(request.idInscripcion()))
            throw new IllegalArgumentException("Ya existe una calificacion con esta inscripcion");

        Inscripcion inscripcion = obtenerInscripcion(request.idInscripcion());

        log.info("Inscripcion obtenida: {}", inscripcion.getId());

        Calificacion calificacion = calificacionMapper.requestAEntidad(request, inscripcion);

        log.info("Calificacion obtenida: {}", calificacion.getId());

        calificacionRepository.save(calificacion);

        log.info("Calificacion registrada: {}", calificacion.getId());

        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public CalificacionResponse actualizar(CalificacionRequest request, Long id) {

        log.info("Actualizando calificacion");

        Calificacion calificacion = obtenerCalificacion(id);

        if (calificacion.cambioEnInscripcion(calificacion.getId())) {

            log.info(" {}", calificacion.cambioEnInscripcion(calificacion.getId()));

            if (calificacionRepository.existsByInscripcionId(request.idInscripcion())) {
                throw new IllegalArgumentException("Ya existe una calificacion con esta inscripcion");
            }
        }

        Inscripcion inscripcion = obtenerInscripcion(request.idInscripcion());

        calificacion.actualizarDatos(inscripcion, request.calificacion());

        log.info("Calificacion actualizada: {}", calificacion.getId());

        calificacionRepository.save(calificacion);

        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public void eliminar(Long id) {

        log.info("Eliminando calificacion");

        Calificacion calificacion = obtenerCalificacion(id);

        calificacionRepository.delete(calificacion);

        log.info("Calificacion eliminada: {}", calificacion.getId());
    }

    private Calificacion obtenerCalificacion(Long id) {
        return SerivceUtils.obtenerEntidadOException(calificacionRepository, id, Calificacion.class);
    }

    private Inscripcion obtenerInscripcion(Long id) {
        return SerivceUtils.obtenerEntidadOException(inscripcionRepository, id, Inscripcion.class);
    }
}
