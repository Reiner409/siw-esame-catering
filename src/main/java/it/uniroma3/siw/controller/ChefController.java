package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.controller.validator.ChefValidator;
import it.uniroma3.siw.service.ChefService;

public class ChefController {
	
	@Autowired 
	private ChefService chefService;
	
	@Autowired 
	private ChefValidator chefValidator;

}
