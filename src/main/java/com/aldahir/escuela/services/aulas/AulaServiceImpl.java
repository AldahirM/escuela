package com.aldahir.escuela.services.aulas;

import com.aldahir.escuela.dtos.aulas.AulaRequest;
import com.aldahir.escuela.dtos.aulas.AulaResponse;
import com.aldahir.escuela.entities.Aula;
import com.aldahir.escuela.exceptions.EntidadRelacionalException;
import com.aldahir.escuela.mappers.AulaMapper;
import com.aldahir.escuela.repositories.AulaRepository;
import com.aldahir.escuela.repositories.GrupoRepository;
import com.aldahir.escuela.utils.SerivceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AulaServiceImpl implements AulaService {

    private final AulaRepository aulaRepository;

    private final AulaMapper aulaMapper;

    private final GrupoRepository grupoRepository;

    @Override
    public List<AulaResponse> listar() {
        log.info("Listando todas los aulas...");

        return aulaRepository.findAll().stream()
                .map(aulaMapper::entidadAResponse)
                .toList();
    }

    @Override
    public AulaResponse obtenerPorId(Long id) {
        log.info("Obteniendo aula por id...");
        return aulaMapper.entidadAResponse(obtenerAula(id));
    }

    @Override
    public AulaResponse registrar(AulaRequest request) {

        log.info("Registrando aula");

        if (aulaRepository.existsByNombre(request.nombre()))
            throw new IllegalArgumentException("Ya hay un aula con este nombre.");

        Aula aula = aulaMapper.requestAEntidad(request);

        aulaRepository.save(aula);

        log.info("Aula registrado exitosamente");

        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public AulaResponse actualizar(AulaRequest request, Long id) {

        log.info("Actualizando aula por id.");

        Aula aula = obtenerAula(id);

        if (aula.cambioNombre(request.nombre())) {
            if (aulaRepository.existsByNombre(request.nombre()))
                throw new IllegalArgumentException("Ya hay un aula con este nombre.");
        }

        aula.actualizar(request.nombre(), request.capacidad());

        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public void eliminar(Long id) {

        log.info("Eliminando aula por id.");

        if (grupoRepository.existsByAulaId(id))
            throw new EntidadRelacionalException("No se puede eliminar esta aula porque tiene grupos inscritos");

        Aula aula = obtenerAula(id);

        aulaRepository.delete(aula);

        log.info("Aula eliminado exitosamente");
    }

    private Aula obtenerAula(Long id) {
        return SerivceUtils.obtenerEntidadOException
                (aulaRepository, id, Aula.class);
    }
}
