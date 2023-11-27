package com.argentinaprograma.app.tpji2023.modelo;

import jakarta.persistence.*;
import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id_especialidad;
    private String nombre;
    private String descripcion;
    private List<DetalleServicio> detallesServicio; // Lista de detalles de servicio que la especialidad puede atender
    private List<Tecnico> tecnicos; // Lista de técnicos que tienen esta especialidad

    @ManyToMany(mappedBy = "listaEspecialidades")
    private Set<DetalleServicio> detalleServicios;

    @ManyToMany(mappedBy = "listaEspecialidades")
    private Set<Tecnico> tecnico;


    // Constructor vacío
    public Especialidad() {
        tecnicos = new ArrayList<>();
    }

    // Constructor con todos los atributos
    public Especialidad(int id_especialidad, String nombre, String descripcion, List<DetalleServicio> detallesServicio) {
        this.id_especialidad = id_especialidad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.detallesServicio = detallesServicio;
        tecnicos = new ArrayList<>();
    }

    // Getters y Setters
    public int getId_especialidad() {
        return id_especialidad;
    }

    public void setId_especialidad(int id_especialidad) {
        this.id_especialidad = id_especialidad;
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

    public List<DetalleServicio> getDetallesServicio() {
        return detallesServicio;
    }

    public void setDetallesServicio(List<DetalleServicio> detallesServicio) {
        this.detallesServicio = detallesServicio;
    }

    public List<Tecnico> getTecnicos() {
        return tecnicos;
    }

    public void setTecnicos(List<Tecnico> tecnicos) {
        this.tecnicos = tecnicos;
    }

    // Método que agrega un técnico a la lista de técnicos asociados a esta especialidad
    public void agregarTecnico(Tecnico tecnico) {
        tecnicos.add(tecnico);
    }

    // Método que elimina un técnico de la lista de técnicos asociados a esta especialidad
    public void eliminarTecnico(Tecnico tecnico) {
        tecnicos.remove(tecnico);
    }
    // Método que devuelve todos los técnicos que están dentro de una lista de especialidades seleccionadas
    public static List<Tecnico> obtenerTecnicosPorEspecialidades(List<Especialidad> especialidadesSeleccionadas, List<Tecnico> listaDeTecnicos) {
        List<Tecnico> tecnicosPorEspecialidades = new ArrayList<>();

        for (Tecnico tecnico : listaDeTecnicos) {
            // Obtener las especialidades asociadas al técnico
            List<Especialidad> especialidadesDelTecnico = tecnico.getEspecialidades();

            // Verificar si el técnico tiene al menos una de las especialidades seleccionadas
            for (Especialidad especialidad : especialidadesSeleccionadas) {
                if (especialidadesDelTecnico.contains(especialidad)) {
                    tecnicosPorEspecialidades.add(tecnico);
                    break; // Salir del bucle si se encuentra una especialidad coincidente
                }
            }
        }

        return tecnicosPorEspecialidades;
    }

}
