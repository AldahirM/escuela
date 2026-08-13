package com.aldahir.escuela.entities;

import com.aldahir.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CURSOS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CURSO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(name = "DESCRIPCION", nullable = true, length = 200)
    private String descripcion;

    @Column(name = "CREDITOS", nullable = false)
    private Integer creditos;

    private void validarDatos(String nombre) {
        StringCustomUtils.validarTamanio(nombre, 1, 100, "El nombre del curso deber ser mayor que 1 y menor a 100 caracteres");
    }

    public void actualizar(String nombre, String descripcion, Integer creditos) {
        validarDatos(nombre);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creditos = creditos;
    }

    public boolean cambioEnNombre(String nombre) {
        return this.nombre.equals(nombre.trim());
    }

}
