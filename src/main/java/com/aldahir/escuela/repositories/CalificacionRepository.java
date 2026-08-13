package com.aldahir.escuela.repositories;

import com.aldahir.escuela.entities.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    boolean existsByInscripcionId(Long id);
}
