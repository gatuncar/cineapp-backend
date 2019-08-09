package com.mitocode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.model.Usuario;
import com.mitocode.repo.IUsuarioRepo;
import com.mitocode.service.IClienteService;
import com.mitocode.service.IUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private IUsuarioService service;
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IUsuarioRepo userRepo;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@PostMapping(produces = "application/json", consumes = "application/json")
	private ResponseEntity<Object> registrar(@RequestBody Usuario usuario){		
		usuario.setPassword(bcrypt.encode(usuario.getPassword()));
		service.registrarTransaccional(usuario);
		
		System.out.print("La foto: " + usuario.getCliente().getFoto());
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}

	/*
	@PostMapping(produces = "application/json", consumes = "application/json")
	private ResponseEntity<Object> registrar(@RequestBody Usuario usuario){		
		usuario.setPassword(bcrypt.encode(usuario.getPassword()));
		service.registrarTransaccional(usuario);
		
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	*/
	
	@GetMapping(value = "/{username}")
	public ResponseEntity<Usuario> listarPorUsername(@PathVariable("username") String username) {
		
		Usuario user = userRepo.findOneByUsername(username);
		
		System.out.println(user.getCliente());
		
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}
	
	
}
