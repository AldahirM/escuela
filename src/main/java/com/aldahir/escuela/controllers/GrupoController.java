package com.aldahir.escuela.controllers;

import com.aldahir.escuela.dtos.grupos.GrupoRequest;
import com.aldahir.escuela.dtos.grupos.GrupoResponse;
import com.aldahir.escuela.services.grupos.GrupoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController extends CommonController<GrupoRequest, GrupoResponse, GrupoService> {
    public GrupoController(GrupoService service) {
        super(service);
    }
}
