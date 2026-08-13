package com.aldahir.escuela.mappers;

import com.aldahir.escuela.dtos.aulas.AulaRequest;
import com.aldahir.escuela.dtos.aulas.AulaResponse;
import com.aldahir.escuela.entities.Aula;
import org.springframework.stereotype.Component;

@Component
public class AulaMapper implements CommonMapper<AulaRequest, AulaResponse, Aula> {

    @Override
    public Aula requestAEntidad(AulaRequest request) {
        if (request == null) return null;


        return Aula.builder()
                .nombre(request.nombre())
                .capacidad(request.capacidad())
                .build();
    }

    @Override
    public AulaResponse entidadAResponse(Aula entidad) {
        if (entidad == null) return null;

        return new AulaResponse(
                entidad.getId(),
                entidad.getNombre(),
                entidad.getCapacidad()
        );
    }
}
