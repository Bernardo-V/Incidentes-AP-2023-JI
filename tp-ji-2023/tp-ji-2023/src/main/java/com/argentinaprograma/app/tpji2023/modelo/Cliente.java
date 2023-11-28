package com.argentinaprograma.app.tpji2023.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
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
    
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contrato> contratos = new ArrayList<>();
    //un cliente puede tener múltiples contratos y cada contrato pertenece a un único cliente
// modificado: @OneToMany(mappedBy = "id_contrato", cascade = CascadeType.ALL, orphanRemoval = true)
 //private List<Contrato> contratosl = new ArrayList<>();
    //indica que la relación está mapeada por el campo "id_contrato" en la clase Contrato
    

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

