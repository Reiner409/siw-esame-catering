package it.uniroma3.siw.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.repository.BuffetRepository;

@Service
public class BuffetService implements IServices<Buffet>{

	@Autowired
	private BuffetRepository buffetRepository;
	
	@Override
	public void save(Buffet o) {
		buffetRepository.save(o);
	}

	@Override
	public Buffet findById(Long id) {
		return buffetRepository.findById(id).get();
	}

	@Override
	public List<Buffet> findAll() {
		List<Buffet> buffets = new LinkedList<Buffet>();
		for(Buffet b : buffetRepository.findAll())
		{
			buffets.add(b);
		}
		return buffets;
	}

	@Override
	public boolean alreadyExists(Buffet o) {
		//We still have to see this part during the lessons.
		return false;
	}

}
