package com.mitocode.controller;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.mitocode.model.Config;
import com.mitocode.model.Genero;
import com.mitocode.model.Menu;
import com.mitocode.model.Usuario;
import com.mitocode.service.IClienteService;
import com.mitocode.service.IUsuarioService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private IClienteService service;
	
	
	@Autowired
	private IUsuarioService serviceUser;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@GetMapping
	public ResponseEntity<List<Cliente>> listar(){
		List<Cliente> lista = service.listar();
		return new ResponseEntity<List<Cliente>>(lista, HttpStatus.OK);		
	}
	
	
	@GetMapping(value="/pageable", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Cliente>> listarPageable(Pageable pageable){
		Page<Cliente> clientes;
		clientes = service.listarPageable(pageable);
		return new ResponseEntity<Page<Cliente>>(clientes, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> listarPorId(@PathVariable("id") Integer id) {
		Cliente c = service.leer(id);
		byte[] data = c.getFoto();
		return new ResponseEntity<byte[]>(data, HttpStatus.OK);
	}
	
	
	/*@GetMapping(value = "/{id}")
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
	/*
	//SIRVE
	@PostMapping
	public Cliente registrar(@RequestPart("cliente") Cliente cliente, @RequestPart("file") MultipartFile file)
			throws IOException {
		Cliente c = cliente;
		c.setFoto(file.getBytes());
		return service.registrar(c);
	}
	*/
	@PostMapping
	public Usuario registrar(@RequestPart("usuario") Usuario usuario, @RequestPart("file") MultipartFile file)
			throws IOException {
		Usuario usuyclie = usuario;
		usuyclie.setPassword(bcrypt.encode(usuario.getPassword()));
		usuyclie.getCliente().setFoto(file.getBytes());
		return serviceUser.registrarTransaccional(usuyclie);
	}
	
	@PutMapping
	public Cliente modificar(@RequestPart("cliente") Cliente cliente, @RequestPart("file") MultipartFile file) 
			throws IOException{
		Cliente cli = cliente;
		cli.setFoto(file.getBytes());
		return service.modificar(cli);
		//return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	
	/*
	//final
	@PutMapping
	public Usuario modificar(@RequestPart("usuario") Usuario usuario, @RequestPart("file") MultipartFile file) 
			throws IOException{
		Usuario usuyclie = usuario;
		usuyclie.getCliente().setFoto(file.getBytes());
		return serviceUser.modificar(usuyclie);
		//return new ResponseEntity<Object>(HttpStatus.OK);
	}*/
	
	/*
	@PutMapping
	public Cliente modificar(@RequestPart("cliente") Cliente cliente, @RequestPart("file") MultipartFile file,@RequestPart("clave") String clave) 
			throws IOException{
		
		Cliente cli = cliente;
		Usuario usu  =new  Usuario();
		usu.setPassword(bcrypt.encode(clave));
		cli.setUsuario(usu);
		cli.setFoto(file.getBytes());
		return service.modificar(cli);
		//return new ResponseEntity<Object>(HttpStatus.OK);
	}
	*/
	
	@DeleteMapping(value = "/{id}")
	public void eliminar(@PathVariable("id") Integer id){
		Cliente gen = service.leer(id);

		if (gen.getIdCliente() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: " + id);
		} else {
			service.eliminar(id);
		}
	}
	
	
	/*@GetMapping(value = "/nusuario/{param}")
	public Cliente listar(@PathVariable("param") String nombre) {
		//Cliente clie = new Cliente();
		return service.listarClientePorNombreUsuario(nombre);
		//return new ResponseEntity<Cliente>(clie, HttpStatus.OK);
	}*/
	
	@GetMapping(value = "/buscarusu/{param}")
	public Usuario CargarDatosUsuario(@PathVariable("param") String param){
		return service.CargarDatosUsuario(param);
	}
	
	
}
