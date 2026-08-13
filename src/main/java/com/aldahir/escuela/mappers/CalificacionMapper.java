package com.aldahir.escuela.mappers;

import com.aldahir.escuela.dtos.calificaciones.CalificacionRequest;
import com.aldahir.escuela.dtos.calificaciones.CalificacionResponse;
import com.aldahir.escuela.dtos.datos.DatosAlumno;
import com.aldahir.escuela.dtos.datos.DatosGrupo;
import com.aldahir.escuela.dtos.datos.DatosInscripcion;
import com.aldahir.escuela.entities.Calificacion;
import com.aldahir.escuela.entities.Inscripcion;
import org.springframework.stereotype.Component;

@Component
public class CalificacionMapper implements CommonMapper<CalificacionRequest, CalificacionResponse, Calificacion> {


    @Override
    public Calificacion requestAEntidad(CalificacionRequest request) {
        if (request == null) return null;

        return Calificacion.builder()
                .calificacion(request.calificacion())
                .build();


    }

    public Calificacion requestAEntidad(CalificacionRequest request, Inscripcion inscripcion) {
        if (request == null) return null;

        Calificacion calificacion = requestAEntidad(request);
        calificacion.asignarInscripcion(inscripcion);
        return calificacion;
    }


    @Override
    public CalificacionResponse entidadAResponse(Calificacion entidad) {

        DatosInscripcion datosInscripcion = entidadADatosInscripcion(entidad);

        return new CalificacionResponse(
                entidad.getId(),
                datosInscripcion,
                entidad.getCalificacion(),
                entidad.getFechaRegistro() == null ? "" : entidad.getFechaRegistro().toString()
        );
    }

    private DatosInscripcion entidadADatosInscripcion(Calificacion calificacion) {
        if (calificacion == null || calificacion.getInscripcion() == null) return null;


        DatosAlumno datosAlumno = entidadADatosAlumno(calificacion);

        DatosGrupo datosGrupo = entidadADatosGrupo(calificacion);

        return new DatosInscripcion(
                datosAlumno,
                datosGrupo,
                calificacion.getInscripcion().getFechaInscripcion() == null ? "" : calificacion.getInscripcion().getFechaInscripcion().toString()
        );
    }

    private DatosAlumno entidadADatosAlumno(Calificacion calificacion) {
        if (calificacion == null || calificacion.getInscripcion() == null) return null;
        return new DatosAlumno(
                String.join(" ",

                        calificacion.getInscripcion().getAlumno().getNombre(),
                        calificacion.getInscripcion().getAlumno().getApellidoPaterno(),
                        calificacion.getInscripcion().getAlumno().getApellidoMaterno()
                ),
                calificacion.getInscripcion().getAlumno().getMatricula(),
                calificacion.getInscripcion().getAlumno().getEmail(),
                calificacion.getInscripcion().getAlumno().getFechaIngreso().toString()
        );
    }

    private DatosGrupo entidadADatosGrupo(Calificacion calificacion) {
        if (calificacion == null || calificacion.getInscripcion() == null) return null;
        return new DatosGrupo(
                calificacion.getInscripcion().getGrupo().getCurso().getNombre(),
                String.join(" ",
                        calificacion.getInscripcion().getGrupo().getMaestro().getNombre(),
                        calificacion.getInscripcion().getGrupo().getMaestro().getApellidoPaterno(),
                        calificacion.getInscripcion().getGrupo().getMaestro().getApellidoMaterno()
                ),
                calificacion.getInscripcion().getGrupo().getAula().getNombre(),
                calificacion.getInscripcion().getGrupo().getPeriodo()

        );
    }
}
