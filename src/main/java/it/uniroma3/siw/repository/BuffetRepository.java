package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Piatto;

public interface BuffetRepository extends CrudRepository<Buffet, Long>{

	Buffet[] findByPiatti(Piatto piatto);

	List<Buffet> findByNomeAndDescrizione(String nome, String descrizione);

}
