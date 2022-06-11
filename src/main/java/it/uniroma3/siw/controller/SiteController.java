package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiteController {

	@GetMapping("/index")
	public String getIndex(Model model)
	{
		return "index.html";
	}
	
	@GetMapping("/login")
	public String getLogin(Model model)
	{
		return "login.html";
	}
	
	@GetMapping("/caterings")
	public String getCaterings(Model model)
	{
		return "caterings.html";
	}
	
}
