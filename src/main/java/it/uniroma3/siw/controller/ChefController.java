package it.uniroma3.siw.controller;

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

import it.uniroma3.siw.controller.validator.ChefValidator;
import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.service.ChefService;

@Controller
public class ChefController {

	@Autowired
	ChefService chefService;

	@Autowired
	ChefValidator chefValidator;

	@GetMapping("/show/allchef")
	public String getChefs(Model model) {
		model.addAttribute("chefs", this.chefService.findAll());
		return "showAllChef";
	}

	@GetMapping("/show/chef/{id}")
	public String mostraChef(@PathVariable("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		model.addAttribute("chef", chef);
		return "visualizzaChef";
	}

	@GetMapping("/admin/createchef")
	public String createChef(Model model) {
		model.addAttribute("chef", new Chef());
		return "admin/createChef";
	}

	@PostMapping("/admin/createchef")
	public String creaChef(@ModelAttribute("chef") Chef chef,
			BindingResult chefBindingResult,
			@RequestParam("file") MultipartFile image,
			Model model) {

		this.chefValidator.validate(chef, chefBindingResult);

		if (!chefBindingResult.hasErrors()) {
			chefService.save(chef);
			chef.setImmagine(Shared.SavePicture(chef.getId(), "/images/chef/", image));
			chefService.save(chef);
			return "redirect:/show/chef/"+chef.getId();
		} else {
			return "admin/createChef";
		}
	}

	@GetMapping("/admin/modificachef/{id}")
	public String createChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", this.chefService.findById(id));
		return "admin/modificaChef";
	}

	@PostMapping("/admin/modificachef/{id}")
	public String confermaCreateChef(@PathVariable("id") Long id,
			@ModelAttribute("chef") Chef chef,
			@RequestParam("file") MultipartFile image,
			BindingResult chefBindingResult, Model model) {

		this.chefValidator.validateUpdate(chef, chefBindingResult);

		if (!chefBindingResult.hasErrors()) {
			Chef original = this.chefService.findById(id);
			original.updateValues(chef);

			chefService.save(original);
			if (!image.isEmpty()) {
				original.setImmagine(Shared.SavePicture(original.getId(), "/images/chef/", image));
				chefService.save(original);
			}
			return "redirect:/show/chef/"+id;
		} else {
			return "admin/modificaChef";
		}
	}

	@GetMapping("/admin/deletechef/{id}")
	public String deleteChef(Model model, @PathVariable ("id")Long id) 
	{
		Chef chef = this.chefService.findById(id);
		this.chefService.delete(chef);
		return "admin/creationSuccess";
	}

}
