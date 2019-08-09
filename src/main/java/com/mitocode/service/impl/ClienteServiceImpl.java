package com.mitocode.service.impl;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Cliente;
import com.mitocode.repo.IClienteRepo;
import com.mitocode.service.IClienteService;

@Service
public class ClienteServiceImpl implements IClienteService{

	@Autowired
	private IClienteRepo repo;
	
	@Override
	public Cliente registrar(Cliente obj) {
		return repo.save(obj);
	}

	/*
	@Override
	public Cliente modificar(Cliente obj) {
		return repo.save(obj);
	}
	*/
	
	@Transactional
	@Override
	public Cliente modificar(Cliente obj) {
		if(obj.getFoto().length > 0) {
			repo.modificarFoto(obj.getIdCliente(), obj.getFoto());			
		}		
		return repo.save(obj);	
	}
	
	@Override
	public List<Cliente> listar() {
		return repo.findAll();
	}

	@Override
	public Cliente leer(Integer id) {
		Optional<Cliente> op = repo.findById(id);
		return op.isPresent() ? op.get() : new Cliente();
	}

	@Override
	public void eliminar(Integer id) {
		repo.deleteById(id);
	}

	
}
