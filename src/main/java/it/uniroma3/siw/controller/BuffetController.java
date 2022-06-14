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

import it.uniroma3.siw.controller.validator.BuffetValidator;
import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.service.BuffetService;
import it.uniroma3.siw.service.ChefService;
import it.uniroma3.siw.service.PiattoService;

@Controller
public class BuffetController {
	@Autowired
	BuffetService buffetService;
	
	@Autowired
	BuffetValidator buffetValidator;
	
	@Autowired
	PiattoService piattoService;
	
	@Autowired
	ChefService chefService;
	
	private static final String pictureFolder = "/images/buffet/";
	
	
	@GetMapping("/show/allbuffet")
	public String getBuffets(Model model)
	{
		model.addAttribute("buffets", this.buffetService.findAllWithChef());
		return "showAllBuffet";
	}
	
	
	@GetMapping("/show/buffet/{id}")
	public String mostraBuffet(@PathVariable("id") Long id, Model model)
	{
		Buffet buffet = buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		return "visualizzaBuffet";
	}
	
	@GetMapping("/admin/modificabuffet/{id}")
	public String modificaBuffet(@PathVariable("id") Long id, Model model)
	{
		Buffet buffet = buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		model.addAttribute("chef", buffet.getChef());
		model.addAttribute("piatti",this.piattoService.findAll());
		model.addAttribute("piattiSelected",buffet.getPiatti());
		model.addAttribute("chefs",this.chefService.findAll());
		return "admin/modificaBuffet";
	}
	
	@PostMapping("/admin/modificabuffet/{id}")
	public String confermaModificaBuffet(@ModelAttribute("buffet") Buffet updated,
			BindingResult buffetBindingResult,
			@PathVariable("id") Long idBuffet,
			@RequestParam("idpiatti") List<Long> idPiatti,
			@RequestParam("idchef") Long idChef,
			@RequestParam("file") MultipartFile image,
			Model model)
	{
		this.buffetValidator.validateUpdate(updated, buffetBindingResult);
		
		if(!buffetBindingResult.hasErrors())
		{
			Buffet buffet = this.buffetService.findById(idBuffet);
			buffet.impostaDifferenze(updated);
			
			List<Piatto> ingredienti = new ArrayList<>();
			for (Long idIngr : idPiatti) {
				ingredienti.add(this.piattoService.findById(idIngr));
			}
			
			buffet.setPiatti(ingredienti);
			
			if (buffet.getChef()==null || buffet.getChef().getId() != idChef) {
				
				if (buffet.getChef() != null) 
				{
					Chef oldChef = buffet.getChef();
					oldChef.removeBuffet(buffet);
					this.chefService.save(oldChef);
				}

				Chef chef = this.chefService.findById(idChef);
				buffet.setChef(chef);
				chef.getBuffet().add(buffet);
				this.chefService.save(chef);
			}
			
			buffetService.save(buffet);
			if (!image.isEmpty()) {
				buffet.setImmagine(Shared.SavePicture(buffet.getId(), pictureFolder, image));
				buffetService.save(buffet);
			}
			return "admin/creationSuccess";
		}
		else
		{
			return "admin/modificabuffet";
		}
	}
	
	@GetMapping("/admin/createbuffet")
	public String createBuffet(Model model)
	{
		model.addAttribute("buffet", new Buffet());
		model.addAttribute("piatti", piattoService.findAll());
		model.addAttribute("chefs",this.chefService.findAll());
		
		return "admin/createBuffet";
	}
	
	@PostMapping("/admin/createbuffet")
	public String creaBuffet(@ModelAttribute("buffet") Buffet buffet,
			BindingResult buffetBindingResult,
			@RequestParam("idpiatti") List<Long> idPiatti,
			@RequestParam("idchef") Long idChef,
			@RequestParam("file") MultipartFile image,
			Model model)
	{
		this.buffetValidator.validate(buffet, buffetBindingResult);
		if(!buffetBindingResult.hasErrors())
		{
			List<Piatto> ingredienti = new ArrayList<>();
			for (Long idIngr : idPiatti) {
				ingredienti.add(this.piattoService.findById(idIngr));
			}
			buffet.setPiatti(ingredienti);
			
			Chef chef = this.chefService.findById(idChef);
			buffet.setChef(chef);
			buffetService.save(buffet);
			
			chef.getBuffet().add(buffet);
			this.chefService.save(chef);
			

			buffet.setImmagine(Shared.SavePicture(buffet.getId(), pictureFolder, image));
			buffetService.save(buffet);
			return "admin/creationSuccess";
		}
		else
		{
			return "admin/createBuffet";
		}
	}
	
	@GetMapping("/admin/deletebuffet/{id}")
	public String deleteBuffet(Model model, @PathVariable("id") Long id)
	{
		Buffet buffet = this.buffetService.findById(id);
		Chef chef = buffet.getChef();

		if (chef != null) {
			chef.removeBuffet(buffet);
			buffet.setChef(null);
			this.chefService.save(chef);
		}

		this.buffetService.delete(buffet);
		
		return "admin/creationSuccess";
	}

}
