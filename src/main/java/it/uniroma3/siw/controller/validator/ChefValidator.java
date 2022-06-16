package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.service.ChefService;

@Component
public class ChefValidator implements Validator{
	
	@Autowired
	private ChefService chefService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Chef.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {

		//Verifico la presenza di spazi o di stringa vuota

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nazionalita", "required");

		//Nel caso lo chef già esista, rifiuto con errore chef.duplicato

		if (!errors.hasErrors()) {
			if (this.chefService.alreadyExists((Chef)o)) {
				errors.reject("chef.duplicato");
			}
		}
		
	}

	public void validateUpdate(Object o, Errors errors) {

		//Verifico la presenza di spazi o di stringa vuota

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nazionalita", "required");
	}
}
