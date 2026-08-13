package com.aldahir.escuela.dtos.datos;

import java.math.BigDecimal;

public record DatosCalificacion(
    String nombre,
    String fechaRegistro,
    BigDecimal calificacion
) {
}
