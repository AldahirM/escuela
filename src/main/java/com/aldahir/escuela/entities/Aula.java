package com.aldahir.escuela.entities;

import com.aldahir.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AULAS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AULA")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    private void validarDatos(String nombre) {
        StringCustomUtils.validarTamanio(nombre, 1, 100, "El nombre es requerido y debe tener entre 1 y 100 caracteres");
    }

    public void actualizar(String nombre, Integer capacidad) {
        validarDatos(nombre);
        this.nombre = nombre.trim();
        this.capacidad = capacidad;
    }

    public boolean cambioNombre(String nombre) {
        validarDatos(nombre);
        return !nombre.trim().equals(this.nombre.trim());
    }

    public boolean cambioCapacidad(Integer capacidad){
        return capacidad.equals(this.capacidad);
    }
}
