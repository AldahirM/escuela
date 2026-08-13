package com.aldahir.escuela.repositories;

import com.aldahir.escuela.entities.Aula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AulaRepository extends JpaRepository<Aula, Long> {
    boolean existsByNombre(String nombre);
}
