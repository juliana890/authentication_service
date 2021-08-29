package com.gptw.authenticationservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gptw.authenticationservice.dto.ClienteDTO;
import com.gptw.authenticationservice.entities.Cliente;
import com.gptw.authenticationservice.exceptions.DataIntegrityViolationException;
import com.gptw.authenticationservice.exceptions.ObjectNotFoundException;
import com.gptw.authenticationservice.repositories.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	//Retorna todos os clientes cadastrados
	public List<Cliente> findAll(){
		List<Cliente> lista = clienteRepository.findAll();
		
		return lista;
	}
	
	//Retorna cliente pelo id
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! ID: " + id + ". Tipo " + Cliente.class.getName()));
	}
	
	//Insere cliente no banco de dados
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = clienteRepository.save(obj);
		
		return obj;
	}
	
	//Atualiza cliente no banco de dados
	public Cliente update(Cliente obj) {
		Cliente newObj = findById(obj.getId());
		updateData(obj, newObj);
		
		return clienteRepository.save(newObj);
	}
	
	//Deleta cliente no banco de dados
	public void delete(Integer id) {
		findById(id);
		
		try {
			clienteRepository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Erro ao deletar cliente! ID: " + id + ". " + e.getMessage());
		}
	}
	
	private void updateData(Cliente obj, Cliente newObj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setTelefone(obj.getTelefone());
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getTelefone(), pe.encode(objDto.getSenha()));
		
		return cli;
	}
	
}
