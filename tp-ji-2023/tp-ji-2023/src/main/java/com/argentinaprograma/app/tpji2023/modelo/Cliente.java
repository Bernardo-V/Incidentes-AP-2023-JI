package com.argentinaprograma.app.tpji2023.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import com.argentinaprograma.app.tpji2023.modelo.Contrato;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cliente")
    private int idCliente;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "cuit")
    private String cuit;

    @Column(name = "email")
    private String email;

    @Column(name = "telefono")
    private String telefono;
    
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contrato> contratos = new ArrayList<>();
    //un cliente puede tener múltiples contratos y cada contrato pertenece a un único cliente
// modificado: @OneToMany(mappedBy = "id_contrato", cascade = CascadeType.ALL, orphanRemoval = true)
 //private List<Contrato> contratosl = new ArrayList<>();
    //indica que la relación está mapeada por el campo "id_contrato" en la clase Contrato
    

    // Método para buscar un cliente por CUIT
    public static Cliente buscarPorCuit(List<Cliente> clientes, String cuit) throws ClienteNoEncontradoException {
        for (Cliente cliente : clientes) {
            if (cliente.getCuit().equals(cuit)) {
                return cliente;
            }
        }
        throw new ClienteNoEncontradoException("Cliente con CUIT " + cuit + " no encontrado");
    }

    // Método para buscar un cliente por razón social
    public static Cliente buscarPorRazonSocial(List<Cliente> clientes, String razonSocial) {
        for (Cliente cliente : clientes) {
            if (cliente.getRazonSocial().equalsIgnoreCase(razonSocial)) {
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
            if (contrato.getIdContrato() == idContrato) {

                return contrato;
            }
        }
        return null; // o puedes lanzar una excepción si no se encuentra el contrato
    }

 // Clase de excepción específica para cuando no se encuentra un cliente
    public static class ClienteNoEncontradoException extends Exception {
        public ClienteNoEncontradoException(String mensaje) {
            super(mensaje);
        }
    }
    
    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    // Métodos para agregar y eliminar contratos
    public void agregarContrato(Contrato contrato) {
        contratos.add(contrato);
        contrato.setCliente(this);
    }

    public void eliminarContrato(Contrato contrato) {
        contratos.remove(contrato);
        contrato.setCliente(null);
    }

	public Cliente orElseThrow(Object object) {
		// TODO Auto-generated method stub
		return null;
	}


}

