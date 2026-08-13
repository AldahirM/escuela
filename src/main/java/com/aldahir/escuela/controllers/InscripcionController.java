package com.aldahir.escuela.controllers;

import com.aldahir.escuela.dtos.inscripciones.InscripcionRequest;
import com.aldahir.escuela.dtos.inscripciones.InscripcionResponse;
import com.aldahir.escuela.services.inscripciones.InscripcionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController extends CommonController<InscripcionRequest, InscripcionResponse, InscripcionService> {
    public InscripcionController(InscripcionService service) {
        super(service);
    }
}
