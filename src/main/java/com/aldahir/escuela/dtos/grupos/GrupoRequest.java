package com.aldahir.escuela.dtos.grupos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record GrupoRequest(
        @NotNull(message = "El id del curso es requerido")
        @Min(value = 1, message = "El id del curso debe ser un número positivo")
        Long idCurso,
        @NotNull(message = "El id del maestro es requerido")
        @Min(value = 1, message = "El id del maestro debe ser un número positivo")
        Long idMaestro,
        @NotNull(message = "El id del aula es requerido")
        @Min(value = 1, message = "El id del aula debe ser un número positivo")
        Long idAula,
        @NotNull(message = "El periodo del grupo es requerido")
        @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$", message = "El periodo debe tener el formato YYYY-MM")
        String periodo
) {
}
