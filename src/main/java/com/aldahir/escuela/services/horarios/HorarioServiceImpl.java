package com.aldahir.escuela.services.horarios;

import com.aldahir.escuela.dtos.horarios.HorarioRequest;
import com.aldahir.escuela.dtos.horarios.HorarioResponse;
import com.aldahir.escuela.entities.Grupo;
import com.aldahir.escuela.entities.Horario;
import com.aldahir.escuela.enums.DiaSemana;
import com.aldahir.escuela.mappers.HorarioMapper;
import com.aldahir.escuela.repositories.GrupoRepository;
import com.aldahir.escuela.repositories.HorarioRepository;
import com.aldahir.escuela.utils.SerivceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository horarioRepository;

    private final HorarioMapper horarioMapper;

    private final GrupoRepository grupoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<HorarioResponse> listar() {
        return horarioRepository.findAll().stream()
                .map(horarioMapper::entidadAResponse)
                .toList();
    }

    @Override
    public HorarioResponse obtenerPorId(Long id) {

        Horario horario = obtenerHorario(id);

        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public HorarioResponse registrar(HorarioRequest request) {

        Grupo grupo = obtenerGrupo(request.idGrupo());

        LocalTime horaInicio = LocalTime.parse(request.horaInicio());
        LocalTime horaFin = LocalTime.parse(request.horaFin());

        if (horaInicio.isAfter(horaFin))
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora inicial");

        DiaSemana diaSemana = obtenerDiaSemanaPorDescipcion(request.dia());

        verificarTraslape(grupo, request, diaSemana);

        Horario horario = horarioMapper.requestAEntidad(request, grupo, diaSemana);

        horarioRepository.save(horario);

        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public HorarioResponse actualizar(HorarioRequest request, Long id) {

        Grupo grupo = obtenerGrupo(request.idGrupo());

        DiaSemana diaSemana = obtenerDiaSemanaPorDescipcion(request.dia());

        verificarTraslape(grupo, request, diaSemana);

        Horario horario = obtenerHorario(id);

        horario.asignarGrupo(grupo);
        horario.asignarHoras(request.horaInicio().toString(), request.horaFin().toString());
        horario.asignarDia(diaSemana);

        horarioRepository.save(horario);

        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public void eliminar(Long id) {
        Horario horario = obtenerHorario(id);

        horarioRepository.delete(horario);
    }

    public Horario obtenerHorario(Long id) {
        return SerivceUtils.obtenerEntidadOException(horarioRepository, id, Horario.class);
    }

    public Grupo obtenerGrupo(Long id) {
        return SerivceUtils.obtenerEntidadOException(grupoRepository, id, Grupo.class);
    }

    private DiaSemana obtenerDiaSemanaPorDescipcion(String descipcion) {
        return DiaSemana.obtenerDiaPorDescripcion(descipcion.trim());
    }

    private void verificarTraslape(Grupo grupo, HorarioRequest request, DiaSemana diaSemana) {
        if (horarioRepository.existsByGrupoPeriodoAndGrupoAulaId
                (grupo.getPeriodo(), grupo.getAula().getId())) {
            if (horarioRepository.existsByGrupoIdAndDiaAndHoraInicioAndHoraFin
                    (grupo.getId(), diaSemana, request.horaInicio().toString(), request.horaFin().toString())) {
                throw new IllegalArgumentException("Horario con el grupo, aula y horario ya existen");
            }
        }
    }
}
