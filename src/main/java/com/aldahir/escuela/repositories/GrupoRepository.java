package com.aldahir.escuela.repositories;

import com.aldahir.escuela.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    boolean existsByMaestroId(long idMaestro);

    boolean existsByAulaId(long idAula);

    boolean existsByCursoId(long idCurso);

    boolean existsByCursoIdAndMaestroIdAndAulaIdAndPeriodo(Long idCurso, Long idMaestro, Long idAula, String periodo);
}
