package com.argentinaprograma.app.tpji2023.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
//@NoArgsConstructor
//@AllArgsConstructor
@Data
//@ToString
@Table(name="tipodeproblema")
public class TipoDeProblema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id_tipo_de_problema;
    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "id_detalle_servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleServicio> detalleservicio = new ArrayList<>();
    // Constructor vacío
    public TipoDeProblema() {
    }

    // Constructor con todos los atributos
    public TipoDeProblema(int id_tipo_de_problema, String nombre, String descripcion) {
        this.id_tipo_de_problema = id_tipo_de_problema;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getId_tipo_de_problema() {
        return id_tipo_de_problema;
    }

    public void setId_tipo_de_problema(int id_tipo_de_problema) {
        this.id_tipo_de_problema = id_tipo_de_problema;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}
