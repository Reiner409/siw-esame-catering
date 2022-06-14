package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Ingrediente;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long>{

	List<Ingrediente> findByNomeAndOrigineAndDescrizione(String nome, String origine, String descrizione);

}
