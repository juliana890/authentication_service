package com.gptw.authenticationservice.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gptw.authenticationservice.entities.Cliente;
import com.gptw.authenticationservice.repositories.ClienteRepository;

@Service
public class DBService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	
	//Instancia os dados para inseri-los no banco de dados da base de test
	public void instantiateTestDatabase() throws ParseException {
		
		Cliente c1 = new Cliente(null, "Maria da Silva", "mariasilva@gmail.com", "20201793", pe.encode("12345"));
		Cliente c2 = new Cliente(null, "João Santos", "joaosantos@gmail.com", "30761298", pe.encode("12345"));
		Cliente c3 = new Cliente(null, "Luis Andrade", "luisandrade@gmail.com", "45620012", pe.encode("12345"));
		Cliente c4 = new Cliente(null, "Daniele Lins", "danielelins@gmail.com", "72349083", pe.encode("12345"));
		Cliente c5 = new Cliente(null, "Rafael Ferreira", "rafaelferreira@gmail.com", "34183071", pe.encode("12345"));
		
		clienteRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5));
	}

}
