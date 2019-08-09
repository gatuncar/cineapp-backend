package com.mitocode.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Cliente;
import com.mitocode.model.Comida;
import com.mitocode.service.IClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private IClienteService service;
	
	@GetMapping
	public ResponseEntity<List<Cliente>> listar(){
		List<Cliente> lista = service.listar();
		return new ResponseEntity<List<Cliente>>(lista, HttpStatus.OK);		
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> listarPorId(@PathVariable("id") Integer id) {
		Cliente c = service.leer(id);
		byte[] data = c.getFoto();
		return new ResponseEntity<byte[]>(data, HttpStatus.OK);
	}
	
	/*
	@GetMapping(value = "/{id}")
	public ResponseEntity<Cliente> listarPorId(@PathVariable("id") Integer id){
		
		Cliente cli = service.leer(id);
		if(cli.getIdCliente() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO : " + id);
		}

		return new ResponseEntity<Cliente>(cli, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Cliente> registrar(@RequestBody Cliente cli) {
		Cliente g = service.registrar(cli);
			
		// localhost:8080/generos/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(g.getIdCliente()).toUri();
		return ResponseEntity.created(location).build();
	}
	*/
	
	@PostMapping
	public Cliente registrar(@RequestPart("cliente") Cliente cliente, @RequestPart("file") MultipartFile file)
			throws IOException {
		Cliente c = cliente;
		c.setFoto(file.getBytes());
		return service.registrar(c);
	}
	
	/*
	@PutMapping
	public ResponseEntity<Object> modificar(@RequestBody Cliente gen) {
		service.modificar(gen);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	*/
	
	@PutMapping
	public Cliente modificar(@RequestPart("cliente") Cliente cliente, @RequestPart("file") MultipartFile file)
			throws IOException {
		Cliente c = cliente;
		c.setFoto(file.getBytes());
		return service.modificar(c);
	}
	
	
	@DeleteMapping(value = "/{id}")
	public void eliminar(@PathVariable("id") Integer id){
		Cliente gen = service.leer(id);

		if (gen.getIdCliente() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: " + id);
		} else {
			service.eliminar(id);
		}
	}
	
	
	
}
