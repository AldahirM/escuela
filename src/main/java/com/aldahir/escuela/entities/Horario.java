package com.aldahir.escuela.entities;

import com.aldahir.escuela.enums.DiaSemana;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HORARIOS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HORARIO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO")
    private Grupo grupo;

    @Column(name = "DIA", length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private DiaSemana dia;

    @Column(name = "HORA_INICIO", nullable = false, length = 5)
    private String horaInicio;

    @Column(name = "HORA_FIN", nullable = false,  length = 5)
    private String horaFin;

    public void asignarGrupo(Grupo grupo){
        this.grupo = grupo;
    }

    public void asignarDia(DiaSemana dia){
        this.dia = dia;
    }

    public void asignarHoras(String horaInicio, String horaFin) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
}
