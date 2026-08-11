package com.aldahir.escuela.enums;

import com.aldahir.escuela.exceptions.RecursoNoEncontradoException;
import com.aldahir.escuela.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DiaSemana {
    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miercoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sábado");
    private final String descripcion;

    public static DiaSemana obtenerDiaPorDescripcion(String descipcion) {
        StringCustomUtils.validarNoVacio(descipcion, "La descipción es requerida");
        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descipcion);
        for (DiaSemana dia : DiaSemana.values()) {
            if (StringCustomUtils.quitarAcentos(dia.descripcion).equalsIgnoreCase(descripcionNormalizada)) {
                return dia;
            }
        }
        throw new RecursoNoEncontradoException("No existe un día de la semana con la descripción: " + descipcion);
    }
}
