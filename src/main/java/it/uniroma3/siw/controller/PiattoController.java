package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.controller.validator.PiattoValidator;
import it.uniroma3.siw.service.PiattoService;

public class PiattoController {
	
	@Autowired 
	private PiattoService piattoService;
	
	@Autowired 
	private PiattoValidator piattoValidator;

}
