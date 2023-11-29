package com.argentinaprograma.app.tpji2023.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="contrato")
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_contrato")
    private int idContrato;

    private int idServicio; // Un único servicio asociado con el contrato
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicio servicio;

    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Incidente> incidentes = new ArrayList<>();

    // Método que recibe un objeto Contrato y devuelve el Servicio asociado
    public int obtenerServicioDelContrato(Contrato contrato) {
        if (contrato != null) {
            return contrato.getIdServicio();
        } else {
            return 0; // O manejar la situación de un contrato nulo según sea necesario
        }
    }




}
