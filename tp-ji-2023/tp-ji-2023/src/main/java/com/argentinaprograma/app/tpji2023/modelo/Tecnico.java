package com.argentinaprograma.app.tpji2023.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
//@NoArgsConstructor
//@AllArgsConstructor
@Data
//@ToString
@Table(name="tecnico")
public class Tecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id_tecnico;
    private String nombre;
    private String eMail;
    private String telefono;
    private MedioNotificacion  medioNotificacion;
    private List<Especialidad> especialidades; // Lista de objetos Especialidad

    @OneToMany(mappedBy = "id_incidente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Incidente> incidente = new ArrayList<>();

    @ManyToMany
    @JoinTable(name= "tecnico_especialidad",
            joinColumns = @JoinColumn(name = "id_tecnico"),
            inverseJoinColumns = @JoinColumn(name= "id_especialidad"))
    private Set<Especialidad> listaEspecialidades;
    // Constructor vacío
    public Tecnico() {
    }

    // Constructor con todos los atributos, incluyendo la lista de especialidades
    public Tecnico(int id_tecnico, String nombre, String eMail, String telefono, MedioNotificacion medioNotificacion, List<Especialidad> especialidades) {
        this.id_tecnico = id_tecnico;
        this.nombre = nombre;
        this.eMail = eMail;
        this.telefono = telefono;
        this.medioNotificacion = medioNotificacion;
        this.especialidades = especialidades;
    }

    // Getters y Setters

    public int getId_tecnico() {
        return id_tecnico;
    }

    public String getNombre() {
        return nombre;
    }

    public String geteMail() {
        return eMail;
    }

    public String getTelefono() {
        return telefono;
    }

    public MedioNotificacion getMedioNotificacion() {
        return medioNotificacion;
    }

    public void setId_tecnico(int id_tecnico) {
        this.id_tecnico = id_tecnico;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setMedioNotificacion(MedioNotificacion medioNotificacion) {
        this.medioNotificacion = medioNotificacion;
    }



    public List<Especialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidad> especialidades) {
        this.especialidades = especialidades;
    }
    // Método para definir el medio de notificación preferido
    public MedioNotificacion definirMedioNotificacion(MedioNotificacion medio) {
        this.medioNotificacion = medio;
        return medio; // Devuelve el medio de notificación seleccionado
    }


}

