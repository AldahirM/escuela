package com.aldahir.escuela.mappers;

import com.aldahir.escuela.dtos.cursos.CursoRequest;
import com.aldahir.escuela.dtos.cursos.CursoResponse;
import com.aldahir.escuela.dtos.datos.DatosCurso;
import com.aldahir.escuela.entities.Curso;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper implements CommonMapper<CursoRequest, CursoResponse, Curso> {
    @Override
    public Curso requestAEntidad(CursoRequest request) {
        if(request == null) return null;

        String descripcion = request.descripcion() != null
                ? request.descripcion().trim() : null;

        return Curso.builder()
                .nombre(request.nombre().trim())
                .descripcion(descripcion)
                .creditos(request.creditos())
                .build();
    }

    @Override
    public CursoResponse entidadAResponse(Curso entidad) {
        if(entidad == null) return null;

        String descripcion = entidad.getDescripcion() == null
                ? "Sin descripción" : entidad.getDescripcion();

        return new CursoResponse(
                entidad.getId(),
                entidad.getNombre(),
                descripcion,
                entidad.getCreditos()
        );
    }

    public DatosCurso entidadADatosCurso(Curso entidad) {
        if(entidad == null) return null;

        String descripcion = entidad.getDescripcion() == null
                ? "Sin descripción" : entidad.getDescripcion();

        return new DatosCurso(
                entidad.getNombre(),
                descripcion,
                entidad.getCreditos()
        );
    }
}
