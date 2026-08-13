package com.aldahir.escuela.controllers;

import com.aldahir.escuela.dtos.calificaciones.CalificacionRequest;
import com.aldahir.escuela.dtos.calificaciones.CalificacionResponse;
import com.aldahir.escuela.services.calificaciones.CalificacionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController extends CommonController<CalificacionRequest, CalificacionResponse, CalificacionService> {
    public CalificacionController(CalificacionService service) {
        super(service);
    }
}
