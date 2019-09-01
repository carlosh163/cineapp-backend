package com.mitocode.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitocode.model.Cliente;
import com.mitocode.model.Config;

public interface IClienteRepo extends JpaRepository<Cliente, Integer>{

	@Modifying
	@Query("UPDATE Cliente set foto = :foto where id = :id")
	void modificarFoto(@Param("id") Integer id, @Param("foto") byte[] foto);
	


	
	@Query(value="SELECT clie.*\r\n" + 
			"	FROM Cliente clie\r\n" + 
			"	inner join usuario usu	on usu.id_usuario = clie.id_cliente\r\n" + 
			"	where usu.nombre = :nombre ;", nativeQuery = true)
	Cliente listarClientePorNombreUsuario(@Param("nombre") String nombre);
	
	
	
	//Cliente findByParametro(String param);
	
}
