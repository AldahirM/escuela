package com.aldahir.escuela.dtos.calificaciones;

import com.aldahir.escuela.dtos.datos.DatosInscripcion;

import java.math.BigDecimal;

public record CalificacionResponse(
        Long id,
        DatosInscripcion inscripcion,
        BigDecimal calificacion,
        String fechaRegistro
) {
}
