package com.argentinaprograma.app.tpji2023.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
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
    
    // Método para calcular la duración estimada con colchón
    public Duration CalculaColchonDuracionEstimada(Duration colchon) {
        if (duracion_problema != null) {
            return duracion_problema.plus(colchon);
        }
        return colchon; // Devuelve el colchón como duración si no hay duración estimada
    }

}
