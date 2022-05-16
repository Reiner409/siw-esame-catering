package it.uniroma3.siw.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.repository.ChefRepository;

public class ChefService implements IServices<Chef> {

	@Autowired
	private ChefRepository chefRepository;
	
	@Override
	public void save(Chef o) {
		chefRepository.save(o);
	}

	@Override
	public Chef findById(Long id) {
		return chefRepository.findById(id).get();
	}

	@Override
	public List<Chef> findAll() {
		List<Chef> chefs = new LinkedList<Chef>();
		
		for(Chef c : chefRepository.findAll())
		{
			chefs.add(c);
		}
		return chefs;
	}

	@Override
	public boolean alreadyExists(Chef o) {
		//We still have to see this part during the lessons.
		return false;
	}

}
