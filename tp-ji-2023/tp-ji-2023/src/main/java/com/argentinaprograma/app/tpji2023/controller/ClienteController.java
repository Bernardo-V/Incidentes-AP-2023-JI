/*package com.argentinaprograma.app.tpji2023.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.argentinaprograma.app.tpji2023.modelo.Cliente;
import com.argentinaprogramaapp.tpji2023.service.ClienteService;

@RestController
@RequestMapping("/clientes")


public class ClienteControlador implements BaseControladores {

	 @Autowired
	    private ClienteService clienteService;

	    @GetMapping("/{id}")
	    public ResponseEntity<Cliente> getCliente(@PathVariable int id) {
	        Cliente cliente = clienteService.getClienteById(id);
	        if (cliente != null) {
	            return new ResponseEntity<>(cliente, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }

	    @PostMapping
	    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
	        Cliente nuevoCliente = clienteService.crearCliente(cliente);
	        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Cliente> actualizarCliente(@PathVariable int id, @RequestBody Cliente cliente) {
	        Cliente clienteActualizado = clienteService.actualizarCliente(id, cliente);
	        if (clienteActualizado != null) {
	            return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> eliminarCliente(@PathVariable int id) {
	        clienteService.eliminarCliente(id);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
  }
*/
package com.argentinaprograma.app.tpji2023.controller;


	