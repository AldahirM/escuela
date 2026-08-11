package com.aldahir.escuela.controllers;

import com.aldahir.escuela.dtos.maestros.MaestroRequest;
import com.aldahir.escuela.dtos.maestros.MaestroResponse;
import com.aldahir.escuela.services.maestros.MaestroService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/maestros")
public class MaestroController extends CommonController<MaestroRequest, MaestroResponse, MaestroService> {

    public MaestroController(MaestroService service) {
        super(service);
    }

}
