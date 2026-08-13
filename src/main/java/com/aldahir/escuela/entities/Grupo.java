package com.aldahir.escuela.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "GRUPOS", uniqueConstraints = @UniqueConstraint(
        name = "UQ_GRUPO_CU_MA_AU_PE",
        columnNames = {
                "ID_CURSO",
                "ID_MAESTRO",
                "ID_AULA",
                "PERIODO"
        }
)
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GRUPO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CURSO", nullable = false)
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MAESTRO", nullable = false)
    private Maestro maestro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AULA", nullable = false)
    private Aula aula;

    @Column(name = "PERIODO", nullable = false, unique = true, length = 20)
    private String periodo;

    @Builder.Default
    @OneToMany(mappedBy = "grupo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Horario> horarios = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "grupo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Inscripcion> inscripciones = new ArrayList<>();

    public void asignarCursoMaestroYAula(Curso curso, Maestro maestro, Aula aula) {
        this.curso = curso;
        this.maestro = maestro;
        this.aula = aula;
    }

    public void asignarPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public boolean cambioEnRelaciones(Long idCurso, Long idMaestro, Long idAula, String periodo) {
        return !idCurso.equals(this.curso.getId()) ||
                !idMaestro.equals(this.maestro.getId()) ||
                !idAula.equals(this.aula.getId()) ||
                !periodo.equals(this.periodo);
    }
}
