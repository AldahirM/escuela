package com.aldahir.escuela.mappers;

import com.aldahir.escuela.dtos.datos.DatosCurso;
import com.aldahir.escuela.dtos.datos.DatosGrupo;
import com.aldahir.escuela.dtos.horarios.HorarioRequest;
import com.aldahir.escuela.dtos.horarios.HorarioResponse;
import com.aldahir.escuela.entities.Grupo;
import com.aldahir.escuela.entities.Horario;
import com.aldahir.escuela.enums.DiaSemana;
import com.aldahir.escuela.utils.StringCustomUtils;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class HorarioMapper implements CommonMapper<HorarioRequest, HorarioResponse, Horario> {
    @Override
    public Horario requestAEntidad(HorarioRequest request) {

        String horaInicio = StringCustomUtils.
                localTimeAString(
                        LocalTime.parse(request.horaInicio()), "HH:mm");
        String horaFin = StringCustomUtils.
                localTimeAString(
                        LocalTime.parse(request.horaFin()), "HH:mm");

        return Horario.builder()
                .horaInicio(horaInicio)
                .horaFin(horaFin)
                .build();
    }

    public Horario requestAEntidad(HorarioRequest request, Grupo grupo, DiaSemana dia) {
        Horario horario = requestAEntidad(request);
        horario.asignarGrupo(grupo);
        horario.asignarDia(dia);
        return horario;
    }

    @Override
    public HorarioResponse entidadAResponse(Horario entidad) {
        return new HorarioResponse(
                entidad.getId(),
                entidadADatosCurso(entidad),
                String.join(" ",
                        entidad.getDia().getDescripcion(),
                        entidad.getHoraInicio(),
                        entidad.getHoraFin()
                )
        );
    }

    public DatosGrupo entidadADatosCurso(Horario entidad) {
        return new DatosGrupo(
                entidad.getGrupo().getCurso().getNombre(),
                String.join(" ",
                        entidad.getGrupo().getMaestro().getNombre(),
                        entidad.getGrupo().getMaestro().getApellidoPaterno(),
                        entidad.getGrupo().getMaestro().getApellidoMaterno()
                ),
                entidad.getGrupo().getAula().getNombre(),
                entidad.getGrupo().getPeriodo()
        );
    }
}
