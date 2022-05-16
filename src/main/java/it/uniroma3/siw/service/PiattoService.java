package it.uniroma3.siw.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.repository.PiattoRepository;

public class PiattoService implements IServices<Piatto> {
	
	@Autowired
	private PiattoRepository piattoRepository;

	@Override
	public void save(Piatto o) {
		piattoRepository.save(o);
		
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
		//We still have to see this part during the lessons.
		return false;
	}

}
