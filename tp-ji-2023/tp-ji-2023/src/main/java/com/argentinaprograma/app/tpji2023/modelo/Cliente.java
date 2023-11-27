package com.argentinaprograma.app.tpji2023.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
//@NoArgsConstructor
//@AllArgsConstructor
@Data
//@ToString
@Table(name="cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id_cliente;
    private String nombre;
    private String razon_social;
    private String cuit;
    private String eMail;
    private String telefono;
    private List<Contrato> contratos;

    @OneToMany(mappedBy = "id_contrato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contrato> contratosl = new ArrayList<>();


    // Constructor vacío
    public Cliente() {
    }

    // Constructor con todos los atributos
    public Cliente(int id_cliente, String nombre, String razon_social, String cuit, String eMail, String telefono, List<Contrato> contratos) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.razon_social = razon_social;
        this.cuit = cuit;
        this.eMail = eMail;
        this.telefono = telefono;
        this.contratos = contratos;
    }

    // Getters y Setters
    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getEmail() {
        return eMail;
    }

    public void setEmail(String eMail) {
        this.eMail = eMail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    // Método para buscar un cliente por CUIT
    public static Cliente buscarPorCuit(List<Cliente> clientes, String cuit) {
        for (Cliente cliente : clientes) {
            if (cliente.getCuit().equals(cuit)) {
                return cliente;
            }
        }
        return null; // o puedes lanzar una excepción si prefieres
    }

    // Método para buscar un cliente por razón social
    public static Cliente buscarPorRazonSocial(List<Cliente> clientes, String razonSocial) {
        for (Cliente cliente : clientes) {
            if (cliente.getRazon_social().equalsIgnoreCase(razonSocial)) {
                return cliente;
            }
        }
        return null; // o puedes lanzar una excepción si prefieres
    }

    // Este método ahora toma un objeto Cliente como parámetro y devuelve su lista de contratos.
    public List<Contrato> obtenerContratosDelCliente(Cliente cliente) {
        return cliente.getContratos();
    }

    // Método para devolver un contrato específico

    public Contrato seleccionarContrato(int idContrato) {
        for (Contrato contrato : this.contratos) {
            if (contrato.getId_contrato() == idContrato) {

                return contrato;
            }
        }
        return null; // o puedes lanzar una excepción si no se encuentra el contrato
    }


}

