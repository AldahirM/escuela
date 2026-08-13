package com.aldahir.escuela.controllers;

import com.aldahir.escuela.dtos.aulas.AulaRequest;
import com.aldahir.escuela.dtos.aulas.AulaResponse;
import com.aldahir.escuela.services.aulas.AulaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aulas")
public class AulaController extends CommonController<AulaRequest, AulaResponse, AulaService> {
    public AulaController(AulaService service) {
        super(service);
    }
}
