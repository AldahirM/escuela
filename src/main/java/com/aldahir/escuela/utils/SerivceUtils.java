package com.aldahir.escuela.utils;

import com.aldahir.escuela.exceptions.RecursoNoEncontradoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

@Slf4j
public class SerivceUtils {
    public static <E, ID> E obtenerEntidadOException(
            JpaRepository<E, ID> repository,
            ID id,
            Class<E> clase
    ) {
        String nombreEntidad = clase.getSimpleName();

        log.info("Obteniendo {} con id: {}", nombreEntidad, id);

        return repository.findById(id).orElseThrow(() -> new
                RecursoNoEncontradoException( nombreEntidad + " no encontrad con id: " + id));
    }
}
