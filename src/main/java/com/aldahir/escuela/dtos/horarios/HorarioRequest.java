package com.aldahir.escuela.dtos.horarios;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalTime;

public record HorarioRequest(
        @NotNull(message = "El id del grupo no debe ser null")
        @Min(value = 1, message = "El id del grupo debe ser positivo")
        Long idGrupo,
        @NotBlank(message = "El día no debe ser nulo o vacío")
        @NotNull(message = "El día no debe ser nulo o vacío")
        String dia,
        @NotNull(message = "El día no debe ser nulo o vacío")
        @Pattern(
                regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$",
                message = "La hora debe tener el formato HH:mm"
        )
        String horaInicio,
        @NotNull(message = "El día no debe ser nulo o vacío")
        @Pattern(
                regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$",
                message = "La hora debe tener el formato HH:mm"
        )
        String horaFin
) {
}
