package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validator.IngredienteValidator;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.service.IngredienteService;


@Controller
public class IngredientController {
	@Autowired
	IngredienteService ingredienteService;
	
	@Autowired
	IngredienteValidator ingredienteValidator;
	
	@GetMapping("/admin/createingredient")
	public String createIngredient(Model model)
	{
		Ingrediente ingrediente = new Ingrediente();
		model.addAttribute("ingrediente", ingrediente);
		return "admin/createIngredient";
	}
	
	
	@PostMapping("/admin/createingredient")
	public String creaIngrediente(@ModelAttribute("ingrediente") Ingrediente ingrediente,
			BindingResult ingredienteBindingResult,
			Model model)
	{
		this.ingredienteValidator.validate(ingrediente, ingredienteBindingResult);
		
		if(!ingredienteBindingResult.hasErrors())
		{
			ingredienteService.save(ingrediente);
			return "admin/creationSuccess";
		}
		else
		{
			return "admin/createIngredient";
		}
	}
	
	
	
}
