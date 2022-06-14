package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Piatto;

public interface PiattoRepository extends CrudRepository<Piatto, Long>{

	List<Piatto> findByIngredienti(Ingrediente ingrediente);

}
