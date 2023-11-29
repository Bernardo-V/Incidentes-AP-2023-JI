package com.argentinaprograma.app.tpji2023.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
//@NoArgsConstructor
//@AllArgsConstructor
@Data
//@ToString
@Table(name="contrato")
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id_contrato;
    // private int id_cliente;
    private int id_cliente; // Un único clinte asociado con el contrato
    private int id_servicio; // Un único servicio asociado con el contrato
    private Date fecha_inicio;
    private Date fecha_fin;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicio servicio;

    @OneToMany(mappedBy = "id_incidente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Incidente> incidente = new ArrayList<>();

    // Constructor vacío

    public Contrato() {
    }

    // Constructor con todos los atributos
    public Contrato(int id_contrato, int id_cliente, int id_servicio, Date fecha_inicio, Date fecha_fin, String estado) {
        this.id_contrato = id_contrato;
        this.id_cliente = id_cliente;
        this.id_servicio = id_servicio;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.estado = estado;
    }

    public int getId_contrato() {
        return id_contrato;
    }



    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    // Getters y Setters
    public String getEstado() {
        return estado;
    }

    public void setId_contrato(int id_contrato) {
        this.id_contrato = id_contrato;
    }


    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }


    // Método que recibe un objeto Contrato y devuelve el Servicio asociado
    public int obtenerServicioDelContrato(Contrato contrato) {
        if (contrato != null) {
            return contrato.getId_servicio();
        } else {
            return 0; // O manejar la situación de un contrato nulo según sea necesario
        }
    }




}
