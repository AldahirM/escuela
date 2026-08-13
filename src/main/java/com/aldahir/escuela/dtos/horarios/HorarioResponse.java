package com.aldahir.escuela.dtos.horarios;

import com.aldahir.escuela.dtos.datos.DatosCurso;
import com.aldahir.escuela.dtos.datos.DatosGrupo;

public record HorarioResponse(
        Long id,
        DatosGrupo grupo,
        String horario
) {
}
