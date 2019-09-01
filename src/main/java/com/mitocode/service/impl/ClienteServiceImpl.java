package com.mitocode.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Cliente;
import com.mitocode.model.Config;
import com.mitocode.model.Genero;
import com.mitocode.model.Menu;
import com.mitocode.model.Usuario;
import com.mitocode.repo.IClienteRepo;
import com.mitocode.repo.IUsuarioRepo;
import com.mitocode.service.IClienteService;

@Service
public class ClienteServiceImpl implements IClienteService{

	@Autowired
	private IClienteRepo repo;
	
	@Autowired
	private IUsuarioRepo userRepo;
	
	
	@Override
	public Cliente registrar(Cliente obj) {
		return repo.save(obj);
	}

	@Transactional
	@Override
	public Cliente modificar(Cliente obj) {
		if(obj.getFoto().length > 0) {
			repo.modificarFoto(obj.getIdCliente(), obj.getFoto());
			//repoUsu.modificarUsuarioP(obj.getIdCliente(), obj.getUsuario().getPassword());
		}	
		return repo.save(obj);
	}

	@Override
	public List<Cliente> listar() {
		return repo.findAll();
	}
	
	@Override
	public Page<Cliente> listarPageable(Pageable pageable) {
		return repo.findAll(pageable);
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
	
	@Override
	public Cliente listarClientePorNombreUsuario(String nombre) {		
		Cliente menus = new Cliente();
		
		
		menus = repo.listarClientePorNombreUsuario(nombre);
		
		return menus;			
	}
	
	/*@Override
	public Cliente leerNombreUser(String param) {
		return repo.findByParametro(param);
	}*/
	
	@Override
	public Usuario CargarDatosUsuario(String username) throws UsernameNotFoundException {
		Usuario user = userRepo.findOneByUsername(username); //from usuario where username := username
		
		if (user == null) {
			throw new UsernameNotFoundException(String.format("Usuario no existe", username));
		}
		
		
		return user;
	}

	
}
