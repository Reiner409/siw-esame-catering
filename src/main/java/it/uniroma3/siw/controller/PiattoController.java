package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.PiattoValidator;
import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.service.BuffetService;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.PiattoService;


@Controller
public class PiattoController {

	@Autowired
	PiattoService piattoService;
	@Autowired
	PiattoValidator piattoValidator;
	
	@Autowired
	BuffetService buffetService;
	
	@Autowired
	IngredienteService ingredienteService;
	
	@GetMapping("/show/piatto/{id}")
	public String mostraPiatto(@PathVariable("id") Long id, Model model)
	{
		Piatto piatto = piattoService.findById(id);
		model.addAttribute("piatto", piatto);
		return "visualizzaPiatto";
	}
	
	@GetMapping("/admin/createpiatto")
	public String creaPiatto(Model model)
	{
		model.addAttribute("piatto", new Piatto());
		model.addAttribute("ingredienti", ingredienteService.findAll());
		
		return "admin/createPiatto";
	}
	
	@PostMapping("/admin/createpiatto")
	public String ConfermaCreaPiatto(@ModelAttribute("piatto") Piatto piatto,
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
	
	@GetMapping("/admin/modificapiatto/{id}")
	public String aggiornaPiatto(@PathVariable("id") Long id, Model model)
	{
		model.addAttribute("piatto", this.piattoService.findById(id));
		model.addAttribute("ingredienti", ingredienteService.findAll());
		
		return "admin/modificapiatto";
	}
	
	@PostMapping("/admin/modificapiatto/{id}")
	public String confermaAggiornaPiatto(@ModelAttribute("piatto") Piatto piatto,
			@PathVariable("id") Long id,
			BindingResult piattoBindingResult,
			@RequestParam("idingredienti") List<Long> idingredienti,
			@RequestParam("file") MultipartFile image,
			Model model)
	{
		this.piattoValidator.validate(piatto, piattoBindingResult);
		
		if(!piattoBindingResult.hasErrors())
		{
			Piatto old = this.piattoService.findById(id);
			old.updateValues(piatto);
			
			List<Ingrediente> ingredienti = new ArrayList<>();
			for (Long idIngr : idingredienti) {
				ingredienti.add(this.ingredienteService.findById(idIngr));
			}
			old.setIngredienti(ingredienti);
			piattoService.save(old);
			if(!image.isEmpty()) {
			piatto.setImmagine(Shared.SavePicture(piatto.getId(), "/images/piatto/", image));
			piattoService.save(piatto);
			}
			return "admin/creationSuccess";
		}
		else
		{
			return "admin/modificaPiatto";
		}
	}
	
	@GetMapping("/admin/deletepiatto/{id}")
	public String deletePiatto(Model model, @PathVariable("id") Long id)
	{
		Piatto piatto = this.piattoService.findById(id);
		List<Buffet> buffets = this.buffetService.findByPiatto(piatto);
		
		for(Buffet b : buffets) {
			b.getPiatti().remove(piatto);
			this.buffetService.save(b);
		}
		
		this.piattoService.delete(piatto);
		
		return "admin/creationSuccess";
	}
}
