package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.controller.validator.IngredienteValidator;
import it.uniroma3.siw.service.IngredienteService;

public class IngredienteController {
	
	@Autowired 
	private IngredienteService ingredienteService;
	
	@Autowired 
	private IngredienteValidator ingredienteValidator;

}
