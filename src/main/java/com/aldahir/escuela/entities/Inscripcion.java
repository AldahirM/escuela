package com.aldahir.escuela.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "INSCRIPCIONES", uniqueConstraints =
            @UniqueConstraint(
                    name = "UQ_INSCRIPCIONES_ALU_GRU",
                    columnNames = {
                            "ID_ALUMNO",
                            "ID_GRUPO"
                    }

            )
    )
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INSCRIPCION")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ALUMNO",  nullable = false)
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO",   nullable = false)
    private Grupo grupo;

    @Builder.Default
    @Column(name = "FECHA_INSCRIPCION", nullable = true)
    private LocalDate fechaInscripcion = LocalDate.now();

    @OneToOne(mappedBy = "inscripcion")
    private Calificacion calificacion;

    public void asignarAlumnoYGrupo(Alumno alumno, Grupo grupo) {
        this.alumno = alumno;
        this.grupo = grupo;
    }

}
