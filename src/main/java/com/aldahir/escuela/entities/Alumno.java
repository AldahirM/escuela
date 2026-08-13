package com.aldahir.escuela.entities;

import com.aldahir.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ALUMNOS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALUMNO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO_PATERNO", nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", nullable = false, length = 50)
    private String apellidoMaterno;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 1000)
    private String email;

    @Column(name = "MATRICULA", nullable = false, unique = true, length = 10)
    private String matricula;

    @Builder.Default
    @Column(name = "FECHA_INGRESO", nullable = false)
    private LocalDate fechaIngreso = LocalDate.now();

    @Builder.Default
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Inscripcion> inscripciones = new ArrayList<>();

    private void validarDatos(String nombre, String apellidoPaterno, String apellidoMaterno) {
        StringCustomUtils.validarTamanio(nombre, 1, 50, "El nombre es requerido y debe tener entre 1 y 50 caracteres");

        StringCustomUtils.validarTamanio(apellidoPaterno, 1, 50, "El apellido paterno es requerido y debe tener entre 1 y 50 caracteres");

        StringCustomUtils.validarTamanio(apellidoMaterno, 1, 50, "El apellido materno es requerido y debe tener entre 1 y 50 caracteres");

        StringCustomUtils.validarTamanio(email, 1, 100, "El email es requerido y debe tener entre 1 y 100 caracteres");

        StringCustomUtils.validarTamanio(matricula, 10, 10, "La matricula es requerida y debe tener exactamente 10 caracteres");

    }

    public boolean cambioEnDatos(String nombre, String apellidoPaterno, String apellidoMaterno) {
        return !this.nombre.equals(nombre) ||
                !this.apellidoPaterno.equals(apellidoPaterno) ||
                !this.apellidoMaterno.equals(apellidoMaterno);
    }

    public void asignarDatosAcademicos(String email, String matricula) {
        StringCustomUtils.validarTamanio(email, 1, 100, "El email es requerido y debe tener entre 1 y 100 caracteres");

        StringCustomUtils.validarTamanio(matricula, 10, 10, "La matricula es requerida y debe tener exactamente 10 caracteres");

        this.email = email.toLowerCase().trim();
        this.matricula = matricula.trim();
    }

    public void actualizar(String nombre, String apellidoPaterno, String apellidoMaterno, String email, String matricula) {

        validarDatos(nombre, apellidoPaterno, apellidoMaterno);

        asignarDatosAcademicos(email, matricula);

        this.nombre = nombre.trim();
        this.apellidoPaterno = apellidoPaterno.trim();
        this.apellidoMaterno = apellidoMaterno.trim();
    }

    public BigDecimal calcularPromedio() {
        List<BigDecimal> calificaciones = inscripciones.stream()
                .map(Inscripcion::getCalificacion)
                .filter(Objects::nonNull)
                .map(Calificacion::getCalificacion)
                .filter(Objects::nonNull).toList();

        if (calificaciones.isEmpty()) return BigDecimal.ZERO;

        BigDecimal suma = calificaciones.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return suma.divide(BigDecimal.valueOf(calificaciones.size()), 2, BigDecimal.ROUND_HALF_UP);
    }
}
