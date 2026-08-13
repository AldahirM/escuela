package com.aldahir.escuela.dtos.inscripciones;

import com.aldahir.escuela.dtos.datos.DatosAlumno;
import com.aldahir.escuela.dtos.datos.DatosGrupo;

import java.math.BigDecimal;

public record InscripcionResponse(
        Long id,
        DatosAlumno alumno,
        DatosGrupo grupo,
        BigDecimal calificacion,
        String fechaInscripcion
) {
}
