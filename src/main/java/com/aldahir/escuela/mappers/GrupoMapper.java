package com.aldahir.escuela.mappers;

import com.aldahir.escuela.dtos.datos.DatosAula;
import com.aldahir.escuela.dtos.datos.DatosCurso;
import com.aldahir.escuela.dtos.datos.DatosHorario;
import com.aldahir.escuela.dtos.datos.DatosMaestro;
import com.aldahir.escuela.dtos.grupos.GrupoRequest;
import com.aldahir.escuela.dtos.grupos.GrupoResponse;
import com.aldahir.escuela.entities.Aula;
import com.aldahir.escuela.entities.Curso;
import com.aldahir.escuela.entities.Grupo;
import com.aldahir.escuela.entities.Maestro;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GrupoMapper implements CommonMapper<GrupoRequest, GrupoResponse, Grupo> {

    @Override
    public Grupo requestAEntidad(GrupoRequest request) {

        if (request == null) return null;

        return Grupo.builder()
                .periodo(request.periodo())
                .build();
    }

    public Grupo requestAEntidad(GrupoRequest request, Aula aula, Curso curso, Maestro maestro) {

        if (request == null) return null;

        Grupo grupo = requestAEntidad(request);

        grupo.asignarCursoMaestroYAula(curso, maestro, aula);

        return grupo;
    }

    @Override
    public GrupoResponse entidadAResponse(Grupo entidad) {

        if (entidad == null) return null;

        List<String> horarios = entidadADatosHorario(entidad);

        return new GrupoResponse(
                entidad.getId(),
                entidadADatosCurso(entidad),
                entidadADatosMaestro(entidad),
                entidadADatosAula(entidad),
                horarios,
                entidad.getPeriodo()
        );
    }

    private DatosCurso entidadADatosCurso(Grupo entidad) {
        if (entidad == null) return null;

        return new DatosCurso(
                entidad.getCurso().getNombre(),
                entidad.getCurso().getDescripcion(),
                entidad.getCurso().getCreditos()
        );
    }

    private DatosMaestro entidadADatosMaestro(Grupo entidad) {
        if (entidad == null) return null;
        return new DatosMaestro(
                String.join(" ",
                        entidad.getMaestro().getNombre(),
                        entidad.getMaestro().getApellidoPaterno(),
                        entidad.getMaestro().getApellidoMaterno()
                ),
                entidad.getMaestro().getEmail(),
                entidad.getMaestro().getTelefono()
        );
    }

    private DatosAula entidadADatosAula(Grupo entidad) {
        if (entidad == null) return null;

        return new DatosAula(
                entidad.getAula().getNombre(),
                entidad.getAula().getCapacidad()
        );
    }

    private List<String> entidadADatosHorario(Grupo entidad) {
        if (entidad == null || entidad.getHorarios() == null || entidad.getHorarios().isEmpty())
            return List.of();

        return entidad.getHorarios().stream()
                .map(
                        horario -> String.join(
                                " ",
                                horario.getDia().getDescripcion(),
                                String.join(
                                        " - ",
                                        horario.getHoraInicio(),
                                        horario.getHoraFin()
                                )
                        )
                )
                .toList();
    }
}
