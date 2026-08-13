package com.aldahir.escuela.dtos.datos;

public record DatosInscripcion(
        DatosAlumno alumno,
        DatosGrupo grupo,
        String fechaInscripcion
) {
}
