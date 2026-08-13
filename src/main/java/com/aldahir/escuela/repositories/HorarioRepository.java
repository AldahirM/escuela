package com.aldahir.escuela.repositories;

import com.aldahir.escuela.entities.Horario;
import com.aldahir.escuela.enums.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    boolean existsByGrupoId(Long idGrupo);

    boolean existsByGrupoPeriodoAndGrupoAulaId(String periodo, Long idAula);

    boolean existsByGrupoIdAndDiaAndHoraInicioAndHoraFin
            (Long idGrupo, DiaSemana dia, String horaInicio, String horaFin);
}
