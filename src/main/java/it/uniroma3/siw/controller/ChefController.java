package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.ChefValidator;
import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.service.ChefService;

@Controller
public class ChefController {

	@Autowired
	ChefService chefService;

	@Autowired
	ChefValidator chefValidator;


	@GetMapping("/admin/createchef")
	public String createChef(Model model) {
		model.addAttribute("chef", new Chef());

		return "admin/createChef";
	}

	@PostMapping("/admin/createchef")
	public String creaChef(@ModelAttribute("chef") Chef chef, BindingResult chefBindingResult,
			@RequestParam("file") MultipartFile image, Model model) {
		this.chefValidator.validate(chef, chefBindingResult);

		if (!chefBindingResult.hasErrors()) {
			chefService.save(chef);
			chef.setImmagine(Shared.SavePicture(chef.getId(), "/images/chef/", image));
			chefService.save(chef);
			return "admin/creationSuccess";
		} else {
			return "admin/createChef";
		}
	}

	@GetMapping("/admin/deletechef")
	public String deleteChef(Model model) {
		return "admin/deleteChef";
	}

}
