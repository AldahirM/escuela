package com.aldahir.escuela.mappers;

import com.aldahir.escuela.dtos.alumnos.AlumnoRequest;
import com.aldahir.escuela.dtos.alumnos.AlumnoResponse;
import com.aldahir.escuela.dtos.datos.DatosCalificacion;
import com.aldahir.escuela.entities.Alumno;
import com.aldahir.escuela.entities.Maestro;
import com.aldahir.escuela.utils.StringCustomUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class AlumnoMapper implements CommonMapper<AlumnoRequest, AlumnoResponse, Alumno> {
    @Override
    public Alumno requestAEntidad(AlumnoRequest request) {
        if (request == null) return null;


        return Alumno.builder()
                .nombre(request.nombre())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .build();
    }

    public Alumno requestAEntidad(AlumnoRequest request, String email, String matricula) {
        if (request == null) return null;

        Alumno alumno = requestAEntidad(request);

        alumno.asignarDatosAcademicos(email, matricula);

        return alumno;

    }

    @Override
    public AlumnoResponse entidadAResponse(Alumno entidad) {
        if (entidad == null) return null;

        final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<DatosCalificacion> calificaciones = entidadADatosCalificacion(entidad);

        return new AlumnoResponse(
                entidad.getId(),
                String.join(" ", entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getMatricula(),
                entidad.getFechaIngreso().format(formato),
                calificaciones,
                entidad.calcularPromedio()
        );
    }

    private List<DatosCalificacion> entidadADatosCalificacion(Alumno entidad) {
        if (entidad == null || entidad.getInscripciones() == null || entidad.getInscripciones().isEmpty())
            return List.of();

        return entidad.getInscripciones().stream()
                .map(inscripcion -> new DatosCalificacion(
                        inscripcion.getGrupo().getCurso().getNombre(),
                        inscripcion.getGrupo().getPeriodo(),
                        inscripcion.getCalificacion() != null ? inscripcion.getCalificacion().getCalificacion() : null
                )).toList();
    }

}
