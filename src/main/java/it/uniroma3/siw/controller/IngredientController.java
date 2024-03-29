package it.uniroma3.siw.controller;

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

import it.uniroma3.siw.controller.validator.IngredienteValidator;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.PiattoService;

@Controller
public class IngredientController {
	@Autowired
	IngredienteService ingredienteService;
	
	@Autowired
	PiattoService piattoService;

	@Autowired
	IngredienteValidator ingredienteValidator;

	private static final String pictureFolder = "/images/ingrediente/";

	@GetMapping("/show/ingrediente/{id}")
	public String mostraIngrediente(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente = ingredienteService.findById(id);
		model.addAttribute("ingrediente", ingrediente);
		return "visualizzaIngrediente";
	}

	@GetMapping("/admin/createingrediente")
	public String createIngredient(Model model) {
		model.addAttribute("ingrediente", new Ingrediente());
		return "admin/createIngrediente";
	}

	@PostMapping("/admin/createingrediente")
	public String creaIngrediente(@ModelAttribute("ingrediente") Ingrediente ingrediente,
			@RequestParam("file") MultipartFile image, BindingResult ingredienteBindingResult, Model model) {

		//Valido la creazione dell'ingrediente
		this.ingredienteValidator.validate(ingrediente, ingredienteBindingResult);

		if (!ingredienteBindingResult.hasErrors()) {
			//Salvo l'ingrediente modificato e gli risetto l'immagine per poi risalvarlo
			ingredienteService.save(ingrediente);
			ingrediente.setImmagine(Shared.SavePicture(ingrediente.getId(), pictureFolder, image));
			ingredienteService.save(ingrediente);
			return "redirect:/show/ingrediente/"+ingrediente.getId();
		} else {
			return "admin/createIngrediente";
		}
	}

	@GetMapping("/admin/modificaingrediente/{id}")
	public String modificaIngredient(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente = this.ingredienteService.findById(id);
		model.addAttribute("ingrediente", ingrediente);
		model.addAttribute("immagine", ingrediente.getImmagine());
		return "admin/modificaIngrediente";
	}

	@PostMapping("/admin/modificaingrediente/{id}")
	public String confermaModificaIngredient(@ModelAttribute("ingrediente") Ingrediente ingrediente,
			@PathVariable("id") Long id,
			@RequestParam("file") MultipartFile image,
			BindingResult ingredientBindingResult,
			Model model) {
		//Valida l'aggiornamento dell'ingrediente
		this.ingredienteValidator.validateUpdate(ingrediente, ingredientBindingResult);
		
		if (!ingredientBindingResult.hasErrors()) {

			//Trovo l'ingrediente originale e ci aggiorno i valori
			Ingrediente original = this.ingredienteService.findById(id);
			original.updateValues(ingrediente);

			//Salvo l'ingrediente originale modificato
			ingredienteService.save(original);
			if (!image.isEmpty()) {
				original.setImmagine(Shared.SavePicture(original.getId(), pictureFolder, image));
				ingredienteService.save(original);
			}

			return "redirect:/show/ingrediente/"+id;
		} else {
			model.addAttribute("ingrediente", this.ingredienteService.findById(id));
			return "admin/modificaIngrediente";
		}
	}

	@GetMapping("/admin/deleteingrediente/{id}")
	public String deleteIngrediente(Model model, @PathVariable("id") Long id) {
		
		Ingrediente ingrediente = this.ingredienteService.findById(id);
		List<Piatto> piatti = this.piattoService.findByIngrediente(ingrediente);
		
		//Due vie: Cancellazione del piatto o rimozione dell'ingrediente da ogni piatto.
		
		//Prendo la seconda via perchè non voglio perdere i piatti a causa di un'errore
		
		for(Piatto piatto : piatti)
		{
			piatto.getIngredienti().remove(ingrediente);
			this.piattoService.save(piatto);
		}
		
		this.ingredienteService.delete(ingrediente);
		return "redirect:/index";
	}

}
