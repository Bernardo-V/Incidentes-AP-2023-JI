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
//@ToString
@Table(name="detalleservicio")
public class DetalleServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id_detalle_servicio;
    private int id_servicio;
    private int id_tipo_de_problema;
    private Duration duracion_estandar; // Duración estándar como un objeto Duration
    private List<Especialidad> especialidades; // Lista de especialidades asociadas a este detalle de servicio

    @OneToMany(mappedBy = "id_incidente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Incidente> incidente = new ArrayList<>();

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

    public DetalleServicio(int id_detalle_servicio, int id_servicio, int id_tipo_de_problema, Duration duracion_estandar, List<Especialidad> especialidades) {
        this.id_detalle_servicio = id_detalle_servicio;
        this.id_servicio = id_servicio;
        this.id_tipo_de_problema = id_tipo_de_problema;
        this.duracion_estandar = duracion_estandar;
        this.especialidades = especialidades;
    }

    // Getters y setters
    public void setId_detalle_servicio(int id_detalle_servicio) {
        this.id_detalle_servicio = id_detalle_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public void setId_tipo_de_problema(int id_tipo_de_problema) {
        this.id_tipo_de_problema = id_tipo_de_problema;
    }

    public int getId_detalle_servicio() {
        return id_detalle_servicio;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public int getId_tipo_de_problema() {
        return id_tipo_de_problema;
    }

    public Duration getDuracion_estandar() {
        return duracion_estandar;
    }

    public void setDuracion_estandar(Duration duracion_estandar) {
        this.duracion_estandar = duracion_estandar;
    }

    public List<Especialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidad> especialidades) {
        this.especialidades = especialidades;
    }



}
