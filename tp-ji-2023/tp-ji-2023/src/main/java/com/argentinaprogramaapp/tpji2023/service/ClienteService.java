package com.argentinaprogramaapp.tpji2023.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.argentinaprograma.app.tpji2023.modelo.Cliente;
import com.argentinaprograma.app.tpji2023.modelo.Contrato;
import com.argentinaprograma.app.tpji2023.repository.ClienteRepository;
import com.argentinaprograma.app.tpji2023.repository.ContratoRepository;

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
        Optional<Cliente> optionalCliente = Optional.ofNullable(clienteRepository.findById(idCliente));

        if (optionalCliente.isPresent()) {
            return optionalCliente.get().getContratos();
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