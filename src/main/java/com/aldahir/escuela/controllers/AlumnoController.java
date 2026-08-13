package com.aldahir.escuela.controllers;

import com.aldahir.escuela.dtos.alumnos.AlumnoRequest;
import com.aldahir.escuela.dtos.alumnos.AlumnoResponse;
import com.aldahir.escuela.services.alumnos.AlumnoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController extends CommonController<AlumnoRequest, AlumnoResponse, AlumnoService> {
    public AlumnoController(AlumnoService service) {
        super(service);
    }
}
