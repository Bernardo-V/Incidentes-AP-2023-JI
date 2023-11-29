package com.argentinaprograma.app.tpji2023.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="detalleservicio")
public class DetalleServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_detalle_servicio")
    private int idDetalleServicio;
    private Duration duracion_estandar; // Duración estándar como un objeto Duration
    private List<Especialidad> especialidades; // Lista de especialidades asociadas a este detalle de servicio

    @OneToMany(mappedBy = "detalleServicio") // Assuming "detalleServicio" is the field in Incidente referring to DetalleServicio
    private List<Incidente> incidentes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "id_tipo_de_problema")
    private TipoDeProblema tipodeproblema;

    @ManyToMany
    @JoinTable(name= "detalle_servicio_especialidad",
    joinColumns = @JoinColumn(name = "id_detalle_servicio"),
    inverseJoinColumns = @JoinColumn(name= "id_especialidad"))
    private Set<Especialidad> listaEspecialidades;


}
