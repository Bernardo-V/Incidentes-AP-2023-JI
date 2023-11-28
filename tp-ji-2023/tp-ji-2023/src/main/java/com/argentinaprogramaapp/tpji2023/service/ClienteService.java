package com.argentinaprogramaapp.tpji2023.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.argentinaprograma.app.tpji2023.modelo.Cliente;
import com.argentinaprograma.app.tpji2023.modelo.Contrato;
//import com.argentinaprograma.app.tpji2023.repositorio.ClienteRepository;
//import com.argentinaprograma.app.tpji2023.repositorio.ContratoRepository;
//averiguando sobre la recomendacion de una clase repositorio que haga las crud porque ya teniamos en cliente controlador las crud

public class ClienteService {
	
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorCuit(String cuit) {
        return clienteRepository.findByCuit(cuit);
    }

    public Cliente buscarPorRazonSocial(String razonSocial) {
        return clienteRepository.findByRazonSocialIgnoreCase(razonSocial);
    }

    public List<Contrato> getContratosCliente(int idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente).orElse(null);
        if (cliente != null) {
            return cliente.getContratosl();
        } else {
            return Collections.emptyList();
        }
    }

	public Cliente getClienteById(int id) {
		// TODO Auto-generated method stub
		return null;
	}


	public Cliente actualizarCliente(int id, Cliente cliente) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object crearCliente(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Cliente crearCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return null;
	}

	public void eliminarCliente(int id) {
		// TODO Auto-generated method stub
		
	}

}