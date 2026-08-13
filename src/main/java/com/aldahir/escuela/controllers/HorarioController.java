package com.aldahir.escuela.controllers;

import com.aldahir.escuela.dtos.horarios.HorarioRequest;
import com.aldahir.escuela.dtos.horarios.HorarioResponse;
import com.aldahir.escuela.services.horarios.HorarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController extends CommonController<HorarioRequest, HorarioResponse, HorarioService> {
    public HorarioController(HorarioService service) {
        super(service);
    }
}
