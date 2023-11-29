package com.argentinaprograma.app.tpji2023.modelo;

import jakarta.persistence.*;
import com.argentinaprograma.app.tpji2023.modelo.Contrato;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
//@NoArgsConstructor
//@AllArgsConstructor
@Data
//@ToString
@Table(name="servicio")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_servicio")
    private int idServicio;
    private String nombre;
    private String descripcion;
    private List<DetalleServicio> detallesServicio; // Lista de detalles de servicio

    @OneToMany(mappedBy = "idContrato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contrato> contratos = new ArrayList<>();

    @OneToMany(mappedBy = "id_detalle_servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleServicio> detalleservicio = new ArrayList<>();

    // Constructor vacío
    public Servicio() {
    }

    // Constructor con todos los atributos
    public Servicio(int id_servicio, String nombre, String descripcion, List<DetalleServicio> detallesServicio) {
        this.idServicio = id_servicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.detallesServicio = detallesServicio;
    }

    // Getters y Setters
    public int getId_servicio() {
        return idServicio;
    }

    public void setId_servicio(int id_servicio) {
        this.idServicio = id_servicio;
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
    // Método que recibe un objeto Servicio y devuelve su lista de Detalle_servicio
    public List<DetalleServicio> obtenerDetallesServicio(Servicio servicio) {
        if (servicio != null) {
            return servicio.getDetallesServicio();
        } else {
            return null; // O manejar la situación de un servicio nulo según sea necesario
        }
    }
    // Método para seleccionar un detalle de servicio específico por ID
    public DetalleServicio seleccionarDetalleServicioPorId(int idDetalleServicio) {
        for (DetalleServicio detalle : detallesServicio) {
            if (detalle.getId_detalle_servicio() == idDetalleServicio) {
                return detalle;
            }
        }
        return null; // o podrías lanzar una excepción si prefieres que se maneje la ausencia de un detalle como un error.
    }
    // Otros métodos como toString, equals, hashCode...
}

