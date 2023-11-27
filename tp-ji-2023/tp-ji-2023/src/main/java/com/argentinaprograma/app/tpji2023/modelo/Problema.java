package com.argentinaprograma.app.tpji2023.modelo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;
import java.util.Date;

@Entity
//@NoArgsConstructor
//@AllArgsConstructor
@Data
//@ToString
@Table(name="problema")
public class Problema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id_problema;
    private int id_incidente;
    private String descripcion;
    private Duration duracion_problema; // Duración del problema como objeto Duration
    private Date fecha_cierre;
    private String estado;
    private boolean complejo; // Atributo booleano

    @ManyToOne
    @JoinColumn(name = "id_incidente")
    private Incidente incidente;
    // Constructor vacío
    public Problema() {
    }

    // Constructor con todos los atributos
    public Problema(int id_problema, int id_incidente, String descripcion, Duration duracion_problema, Date fecha_cierre, String estado, boolean complejo) {
        this.id_problema = id_problema;
        this.id_incidente = id_incidente;
        this.descripcion = descripcion;
        this.duracion_problema = duracion_problema;
        this.fecha_cierre = fecha_cierre;
        this.estado = estado;
        this.complejo = complejo;
    }

    // Getters y Setters
    public int getId_problema() {
        return id_problema;
    }

    public void setId_problema(int id_problema) {
        this.id_problema = id_problema;
    }

    public int getId_incidente() {
        return id_incidente;
    }

    public void setId_incidente(int id_incidente) {
        this.id_incidente = id_incidente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Duration getDuracion_problema() {
        return duracion_problema;
    }

    public void setDuracion_problema(Duration duracion_problema) {
        this.duracion_problema = duracion_problema;
    }

    public Date getFecha_cierre() {
        return fecha_cierre;
    }

    public void setFecha_cierre(Date fecha_cierre) {
        this.fecha_cierre = fecha_cierre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isComplejo() {
        return complejo;
    }

    public void setComplejo(boolean complejo) {
        this.complejo = complejo;
    }

    // Método para calcular la duración estimada con colchón
    public Duration CalculaColchonDuracionEstimada(Duration colchon) {
        if (duracion_problema != null) {
            return duracion_problema.plus(colchon);
        }
        return colchon; // Devuelve el colchón como duración si no hay duración estimada
    }

}
