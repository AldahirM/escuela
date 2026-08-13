package com.aldahir.escuela.mappers;

import com.aldahir.escuela.dtos.datos.DatosAlumno;
import com.aldahir.escuela.dtos.datos.DatosGrupo;
import com.aldahir.escuela.dtos.inscripciones.InscripcionRequest;
import com.aldahir.escuela.dtos.inscripciones.InscripcionResponse;
import com.aldahir.escuela.entities.Alumno;
import com.aldahir.escuela.entities.Grupo;
import com.aldahir.escuela.entities.Inscripcion;
import com.aldahir.escuela.mappers.CommonMapper;
import org.springframework.stereotype.Component;

@Component
public class InscripcionMapper implements CommonMapper<InscripcionRequest, InscripcionResponse, Inscripcion> {

    @Override
    public Inscripcion requestAEntidad(InscripcionRequest request) {
        if (request == null) return null;


        return Inscripcion.builder()
                .build();
    }

    public Inscripcion requestAEntidad(InscripcionRequest request, Grupo grupo, Alumno alumno) {
        if (request == null) return null;

        Inscripcion inscripcion = requestAEntidad(request);

        inscripcion.asignarAlumnoYGrupo(alumno, grupo);

        return inscripcion;

    }

    @Override
    public InscripcionResponse entidadAResponse(Inscripcion entidad) {
        return new InscripcionResponse(
                entidad.getId(),
                entidadADatosAlumno(entidad),
                entidadADatosGrupo(entidad),
                entidad.getCalificacion() != null ? entidad.getCalificacion().getCalificacion() : null,
                entidad.getFechaInscripcion().toString()
        );
    }

    private DatosAlumno entidadADatosAlumno(Inscripcion entidad) {
        if (entidad == null || entidad.getAlumno() == null) return null;
        return new DatosAlumno(
                String.join(" ",
                        entidad.getAlumno().getNombre(),
                        entidad.getAlumno().getApellidoPaterno(),
                        entidad.getAlumno().getApellidoMaterno()
                ),
                entidad.getAlumno().getMatricula(),
                entidad.getAlumno().getEmail(),
                entidad.getAlumno().getFechaIngreso().toString()
        );
    }

    private DatosGrupo entidadADatosGrupo(Inscripcion entidad) {
        if (entidad == null || entidad.getGrupo() == null) return null;

        return new DatosGrupo(
                entidad.getGrupo().getCurso().getNombre(),
                String.join(" ",
                        entidad.getGrupo().getMaestro().getNombre(),
                        entidad.getGrupo().getMaestro().getApellidoPaterno(),
                        entidad.getGrupo().getMaestro().getApellidoMaterno()
                ),
                entidad.getGrupo().getAula().getNombre(),
                entidad.getGrupo().getPeriodo()
        );
    }
}
