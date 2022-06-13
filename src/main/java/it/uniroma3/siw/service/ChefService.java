package it.uniroma3.siw.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.repository.ChefRepository;

@Service
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

	public List<Chef> findFour() {
		int counter = 0;
		List<Chef> chefs = new LinkedList<Chef>();

		List<Chef> allChefs = this.findAll();
		Collections.shuffle(allChefs);
		
		for (Chef c : allChefs) {
			if (counter == 3)
				break;
			if (c.getBuffet().size() != 0) {
				chefs.add(c);
				counter++;
			}
		}
		return chefs;
	}

}
