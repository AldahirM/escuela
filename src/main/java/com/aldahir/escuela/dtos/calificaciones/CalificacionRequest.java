package com.aldahir.escuela.dtos.calificaciones;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CalificacionRequest(
        @NotNull(message = "El id de la inscripción es requerido")
        Long idInscripcion,
        @Min(value = 0, message = "La calificación debe ser un número positivo")
        @Max(value = 10, message = "La calificación no debe superar los 10 puntos")
        BigDecimal calificacion
) {
}
