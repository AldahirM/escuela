package com.aldahir.escuela.repositories;

import com.aldahir.escuela.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    boolean existsByAlumnoId(Long idAlumno);
    boolean existsByGrupoId(Long idGrupo);
    boolean existsByGrupoIdAndAlumnoId(Long idCurso, Long idAlumno);
}
