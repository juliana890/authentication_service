package com.gptw.authenticationservice.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gptw.authenticationservice.dto.ClienteDTO;
import com.gptw.authenticationservice.entities.Cliente;
import com.gptw.authenticationservice.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	//Endpoint GET retorna todos os clientes
	@GetMapping
	public ResponseEntity<List<Cliente>> findAll(){
		List<Cliente> lista = clienteService.findAll();
		
		return ResponseEntity.ok().body(lista);
	}
	
	//Endpoint GET retorna cliente por id
	@GetMapping(value = "/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable Integer id){
		Cliente obj = clienteService.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	//Endpoint POST cria um novo cliente
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteDTO objDto){
		Cliente obj = clienteService.fromDTO(objDto);
		obj = clienteService.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	//Endpoint UPDATE alteração de cliente com DTO
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){
		Cliente obj = clienteService.fromDTO(objDto);
		obj.setId(id);
		obj = clienteService.update(obj);
		
		return ResponseEntity.noContent().build();
	}
	
	//Endpoint DELETE deleção de cliente
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		clienteService.delete(id);
		
		return ResponseEntity.noContent().build();
	}

}
