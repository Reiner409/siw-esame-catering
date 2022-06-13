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
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.service.BuffetService;
import it.uniroma3.siw.service.PiattoService;

@Controller
public class BuffetController {
	@Autowired
	BuffetService buffetService;
	
	@Autowired
	BuffetValidator buffetValidator;
	
	@Autowired
	PiattoService piattoService;
	
	
	@GetMapping("/show/buffet/{id}")
	public String mostraBuffet(@PathVariable("id") Long id, Model model)
	{
		Buffet buffet = buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		return "visualizzaBuffet";
	}
	
	
	@GetMapping("/admin/createbuffet")
	public String createBuffet(Model model)
	{
		model.addAttribute("buffet", new Buffet());
		model.addAttribute("piatti", piattoService.findAll());
		
		return "admin/createBuffet";
	}
	
	@PostMapping("/admin/createbuffet")
	public String creaBuffet(@ModelAttribute("buffet") Buffet buffet,
			BindingResult buffetBindingResult,
			@RequestParam("idpiatti") List<Long> idpiatti,
			@RequestParam("file") MultipartFile image,
			Model model)
	{
		this.buffetValidator.validate(buffet, buffetBindingResult);
		
		if(!buffetBindingResult.hasErrors())
		{
			List<Piatto> ingredienti = new ArrayList<>();
			for (Long idIngr : idpiatti) {
				ingredienti.add(this.piattoService.findById(idIngr));
			}
			buffet.setPiatti(ingredienti);
			buffetService.save(buffet);
			buffet.setImmagine(Shared.SavePicture(buffet.getId(), "/images/buffet/", image));
			buffetService.save(buffet);
			return "admin/creationSuccess";
		}
		else
		{
			return "admin/createBuffet";
		}
	}
	
	@GetMapping("/admin/deletebuffet")
	public String deleteBuffet(Model model)
	{
		return "admin/deleteBuffet";
	}

}
