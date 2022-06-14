package it.uniroma3.siw.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.repository.IngredienteRepository;

@Service
public class IngredienteService implements IServices<Ingrediente> {
	
	@Autowired
	private IngredienteRepository ingredienteRepository;

	@Override
	public void save(Ingrediente o) {
		ingredienteRepository.save(o);
	}
	
	@Override
	public void update(Ingrediente o) {
		this.delete(o);
		this.save(o);
	}
	
	@Override
	public void delete(Ingrediente o) {
		ingredienteRepository.delete(o);
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
		List<Ingrediente> ingredienti = this.ingredienteRepository.findByNomeAndOrigineAndDescrizione(o.getNome(),o.getOrigine(), o.getDescrizione());
		if (ingredienti.size() > 0)
			return true;
		else 
			return false;
	}

}
