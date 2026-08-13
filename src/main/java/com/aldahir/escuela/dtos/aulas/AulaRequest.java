package com.aldahir.escuela.dtos.aulas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AulaRequest(
        @NotBlank(message = "El nombre del aula no debe ser nula")
        @Size(min = 1, max = 100)
        String nombre,

        @NotNull(message = "La capacidad del aula no debe ser nula")
        @Positive(message = "La capacidad del aula debe ser positiva")
        Integer capacidad
) {
}
