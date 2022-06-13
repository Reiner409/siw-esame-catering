package it.uniroma3.siw.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.service.ChefService;

@Controller
public class MainController {

	@Autowired
	ChefService chefService;
	
	@GetMapping(path = {"/index", "/", ""})
	public String getIndex(Model model)
	{
		List<Chef> chefs = chefService.findFour();
		List<Buffet> buffets = new LinkedList<>();
		for(Chef chef: chefs)
		{
			buffets.add((Buffet) chef.getBuffet().toArray()[0]);
		}
		
		model.addAttribute("buffets", buffets);
		model.addAttribute("chefs", chefs);
		return "index";
	}
		
	@GetMapping("/show/caterings")
	public String getCaterings(Model model)
	{
		return "caterings";
	}
	
	@GetMapping("/admin/home")
	public String getAdminHome(Model model)
	{
		return "admin/home";
	}
}
