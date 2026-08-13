package com.aldahir.escuela.dtos.grupos;

import com.aldahir.escuela.dtos.datos.DatosAula;
import com.aldahir.escuela.dtos.datos.DatosCurso;
import com.aldahir.escuela.dtos.datos.DatosHorario;
import com.aldahir.escuela.dtos.datos.DatosMaestro;

import java.util.List;

public record GrupoResponse(
        Long id,
        DatosCurso curso,
        DatosMaestro maestro,
        DatosAula aula,
        List<String> horarios,
        String periodo
) {
}
