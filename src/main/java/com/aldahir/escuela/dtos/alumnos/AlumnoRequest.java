package com.aldahir.escuela.dtos.alumnos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AlumnoRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 1, max = 50)
        String nombre,
        @NotBlank(message = "El apellido paterno es requerido")
        @Size(min = 1, max = 50)
        String apellidoPaterno,
        @NotBlank(message = "El apellido materno es requerido")
        @Size(min = 1, max = 50)
        String apellidoMaterno,
        @Null(message = "La fecha puede ser nulla")
        LocalDate fechaIngreso
) {
}
