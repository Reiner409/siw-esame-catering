package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.PiattoValidator;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.PiattoService;


@Controller
public class PiattoController {

	@Autowired
	PiattoService piattoService;
	@Autowired
	PiattoValidator piattoValidator;
	
	@Autowired
	IngredienteService ingredienteService;
	
	@GetMapping("/admin/createpiatto")
	public String createPlate(Model model)
	{
		model.addAttribute("piatto", new Piatto());
		model.addAttribute("ingredienti", ingredienteService.findAll());
		
		return "admin/createPiatto";
	}
	
	@PostMapping("/admin/createpiatto")
	public String creaIngrediente(@ModelAttribute("piatto") Piatto piatto,
			BindingResult piattoBindingResult,
			@RequestParam("idingredienti") List<Long> idingredienti,
			@RequestParam("file") MultipartFile image,
			Model model)
	{
		this.piattoValidator.validate(piatto, piattoBindingResult);
		
		if(!piattoBindingResult.hasErrors())
		{
			List<Ingrediente> ingredienti = new ArrayList<>();
			for (Long idIngr : idingredienti) {
				ingredienti.add(this.ingredienteService.findById(idIngr));
			}
			piatto.setIngredienti(ingredienti);
			piattoService.save(piatto);
			piatto.setImmagine(Shared.SavePicture(piatto.getId(), "/images/piatto/", image));
			piattoService.save(piatto);
			return "admin/creationSuccess";
		}
		else
		{
			return "admin/createPiatto";
		}
	}
	
	@GetMapping("/admin/deletepiatto")
	public String deletePlate(Model model)
	{
		return "admin/deletePiatto";
	}
	
}
