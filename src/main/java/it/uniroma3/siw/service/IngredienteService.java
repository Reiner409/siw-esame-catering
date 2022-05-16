package it.uniroma3.siw.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.repository.IngredienteRepository;

public class IngredienteService implements IServices<Ingrediente> {
	
	@Autowired
	private IngredienteRepository ingredienteRepository;

	@Override
	public void save(Ingrediente o) {
		ingredienteRepository.save(o);
	}

	@Override
	public Ingrediente findById(Long id) {
		return ingredienteRepository.findById(id).get();
	}

	@Override
	public List<Ingrediente> findAll() {
		List<Ingrediente> ingredienti = new LinkedList<Ingrediente>();
		
		for(Ingrediente i : ingredienteRepository.findAll())
		{
			ingredienti.add(i);
		}
		
		return ingredienti;
	}

	@Override
	public boolean alreadyExists(Ingrediente o) {
		//We still have to see this part during the lessons.
		return false;
	}

}
