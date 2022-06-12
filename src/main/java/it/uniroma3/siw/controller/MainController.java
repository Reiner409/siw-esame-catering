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
	
	@GetMapping(path = {"/index", "/"})
	public String getIndex(Model model)
	{
		List<Chef> chefs = chefService.findFour();
		List<Buffet> buffets = new LinkedList<>();
		for(Chef chef: chefs)
		{
			buffets.add((Buffet) chef.getBuffet().toArray()[0]);
		}
		return "index";
	}
		
	@GetMapping("/caterings")
	public String getCaterings(Model model)
	{
		return "caterings";
	}
	
	@GetMapping("/admin/home")
	public String getAdminHome(Model model)
	{
		return "admin/home";
	}
	
	@GetMapping("/admin/createchef")
	public String createChef(Model model)
	{
		return "admin/createChef";
	}
	
	@GetMapping("/admin/createbuffet")
	public String createBuffet(Model model)
	{
		return "admin/createBuffet";
	}
	

	
	@GetMapping("/admin/createpiatto")
	public String createPlate(Model model)
	{
		return "admin/createPiatto";
	}
	
	@GetMapping("/admin/deletechef")
	public String deleteChef(Model model)
	{
		return "admin/deleteChef";
	}
	
	@GetMapping("/admin/deletebuffet")
	public String deleteBuffet(Model model)
	{
		return "admin/deleteBuffet";
	}
	
	@GetMapping("/admin/deleteingredient")
	public String deleteIngredient(Model model)
	{
		return "admin/deleteIngredient";
	}
	
	@GetMapping("/admin/deletepiatto")
	public String deletePlate(Model model)
	{
		return "admin/deletePiatto";
	}
}
