package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.controller.validator.BuffetValidator;
import it.uniroma3.siw.service.BuffetService;

public class BuffetController {
	
	@Autowired 
	private BuffetService buffetService;
	
	@Autowired 
	private BuffetValidator buffetValidator;

}
