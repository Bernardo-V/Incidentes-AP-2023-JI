package com.argentinaprograma.app.tpji2023.modelo;

import jakarta.persistence.*;
import com.argentinaprograma.app.tpji2023.modelo.Contrato;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="servicio")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_servicio")
    private int idServicio;
    private String nombre;
    private String descripcion;
    private List<DetalleServicio> detallesServicio; // Lista de detalles de servicio

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contrato> contratos = new ArrayList<>();

    @OneToMany(mappedBy = "id_detalle_servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleServicio> detalleservicio = new ArrayList<>();

   
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
            if (detalle.getIdDetalleServicio() == idDetalleServicio) {
                return detalle;
            }
        }
        return null; // o lanzar una excepción que se maneje la ausencia de un detalle como un error.
    }
 
}

