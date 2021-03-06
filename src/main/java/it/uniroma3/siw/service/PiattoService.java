package it.uniroma3.siw.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.repository.PiattoRepository;

@Service
public class PiattoService implements IServices<Piatto> {
	
	@Autowired
	private PiattoRepository piattoRepository;

	@Override
	public void save(Piatto o) {
		piattoRepository.save(o);
	}
	
	@Override
	public void update(Piatto o) {
		this.delete(o);
		this.save(o);
	}
	
	
	@Override
	public void delete(Piatto o) {
		piattoRepository.delete(o);
	}

	@Override
	public Piatto findById(Long id) {
		return piattoRepository.findById(id).get();
	}

	@Override
	public List<Piatto> findAll() {
		List<Piatto> piatti = new LinkedList<>();
		
		for(Piatto p : piattoRepository.findAll())
		{
			piatti.add(p);
		}
		
		return piatti;
	}

	@Override
	public boolean alreadyExists(Piatto o) {
		List<Piatto> piatti = this.piattoRepository.findByNomeAndDescrizione(o.getNome(), o.getDescrizione());
		if (piatti.size() > 0)
			return true;
		else 
			return false;
	}

	public List<Piatto> findByIngrediente(Ingrediente ingrediente) {
		
		List<Piatto> piatti = new LinkedList<>();
		for (Piatto p : piattoRepository.findByIngredienti(ingrediente)) {
				piatti.add(p);
		}
		return piatti;
	}

}
