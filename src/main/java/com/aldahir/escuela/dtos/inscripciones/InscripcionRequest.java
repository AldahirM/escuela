package com.aldahir.escuela.dtos.inscripciones;

import jakarta.validation.constraints.NotNull;

public record InscripcionRequest(
        @NotNull(message = "El id del alumno no debe ser nulo")
        Long idAlumno,
        @NotNull(message = "El id del grupo no debe ser nulo")
        Long idGrupo
) {
}
