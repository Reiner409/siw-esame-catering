package it.uniroma3.siw.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Piatto;
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
	public void update(Buffet o) {
		this.delete(o);
		this.save(o);
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
		List<Buffet> buffets = this.buffetRepository.findByNomeAndDescrizione(o.getNome(), o.getDescrizione());
		if (buffets.size() > 0)
			return true;
		else 
			return false;
	}

	@Override
	public void delete(Buffet o) {
		buffetRepository.delete(o);
	}

	public List<Buffet> findAllWithChef() {
		List<Buffet> buffets = new LinkedList<Buffet>();
		for(Buffet b : buffetRepository.findAll())
		{
			if(b.getChef() != null)
				buffets.add(b);
		}
		return buffets;
	}

	public List<Buffet> findByPiatto(Piatto piatto) {
		List<Buffet> buffets = new LinkedList<Buffet>();
		
		for(Buffet b : this.buffetRepository.findByPiatti(piatto))
			buffets.add(b);
		
		return buffets;
	}

}
