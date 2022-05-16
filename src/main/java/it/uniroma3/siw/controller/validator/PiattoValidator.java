package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.service.PiattoService;

public class PiattoValidator implements Validator{
	
	@Autowired
	private PiattoService piattoService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Piatto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (this.piattoService.alreadyExists((Piatto)target)) {
			errors.reject("chef.duplicato");
		}
	}

}
