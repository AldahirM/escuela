package com.aldahir.escuela.dtos.maestros;

import com.aldahir.escuela.dtos.datos.DatosCurso;

import java.util.List;

public record MaestroResponse (
        Long id,
        String nombre,
        String email,
        String telefono,
        List<DatosCurso> cursos
) {
}

