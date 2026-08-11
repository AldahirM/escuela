package com.aldahir.escuela.services.maestros;

import com.aldahir.escuela.dtos.maestros.MaestroRequest;
import com.aldahir.escuela.dtos.maestros.MaestroResponse;
import com.aldahir.escuela.entities.Maestro;
import com.aldahir.escuela.mappers.MaestroMapper;
import com.aldahir.escuela.repositories.MaestroRepository;
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
public class MaestroServiceImpl implements MaestroService {

    private final MaestroRepository maestroRepository;

    private final MaestroMapper maestroMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MaestroResponse> listar() {
        log.info("Listando todos los maestros");

        return maestroRepository.findAll().stream()
                .map(maestroMapper::entidadAResponse)
                .toList();
    }

    @Override
    public MaestroResponse obtenerPorId(Long id) {
        return maestroMapper.entidadAResponse(obtenerMaestro(id));
    }

    @Override
    public MaestroResponse registrar(MaestroRequest request) {

        log.info("Registrando nuevo maestro...");

        validarDatosUnicos(request);

        Maestro maestro = maestroMapper.requestAEntidad(request);

        log.info("Nuevo maestro {} registrado", maestro.getNombre());

        maestroRepository.save(maestro);

        return maestroMapper.entidadAResponse(maestro);
    }

    @Override
    public MaestroResponse actualizar(MaestroRequest request, Long id) {

        Maestro maestro = obtenerMaestro(id);

        log.info("Actualizando maestro...");

        validarCambiosUnicos(request, id);

        maestro.actualizar(request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno(),
                request.email(),
                request.telefono());

        log.info("Maestro {} actualizado correctamente", maestro.getNombre());
        return maestroMapper.entidadAResponse(maestro);
    }

    @Override
    public void eliminar(Long id) {

        Maestro maestro = obtenerMaestro(id);

        log.info("Eliminando maestro con id: {}", id);

        maestroRepository.delete(maestro);

        log.info("Maestro {} eliminado correctamente", id);

    }

    private Maestro obtenerMaestro(Long id) {
        return SerivceUtils.obtenerEntidadOException(maestroRepository, id, Maestro.class);
    }

    private void validarDatosUnicos(MaestroRequest request) {

        log.info("Validando email único");

        if (maestroRepository.existsByEmailIgnoreCase(request.email())) {
            throw new IllegalArgumentException("Ya existe un maestro registrado con el email: " + request.email());
        }

        log.info("Validando teléfono único");

        if (maestroRepository.existsByTelefono(request.telefono()))
            throw new IllegalArgumentException("Ya existe un maestro registrando con el teléfono: " + request.telefono());


    }

    private void validarCambiosUnicos(MaestroRequest request, Long id) {

        log.info("Validando cambio en email único");

        if (maestroRepository.existsByEmailIgnoreCaseAndIdNot(request.email(), id)) {
            throw new IllegalArgumentException("Ya existe un maestro registrado con el email: " + request.email());
        }

        log.info("Validando cambio en teléfono único");

        if (maestroRepository.existsByTelefonoAndIdNot(request.telefono(), id))
            throw new IllegalArgumentException("Ya existe un maestro registrando con el teléfono: " + request.telefono());


    }
}
