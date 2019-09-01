package com.mitocode.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mitocode.model.Cliente;
import com.mitocode.model.Config;
import com.mitocode.model.Menu;
import com.mitocode.model.Usuario;

public interface IClienteService extends ICRUD<Cliente>{

	Page<Cliente> listarPageable(Pageable pageable);
	
	Cliente listarClientePorNombreUsuario(String nombre);
	
	
	//Cliente leerNombreUser(String param);
	
	
	Usuario CargarDatosUsuario(String username);
}
