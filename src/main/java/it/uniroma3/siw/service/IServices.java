package it.uniroma3.siw.service;

import java.util.List;

public interface IServices<T> {
	
	public void save(T o);
	
	public T findById(Long id);
	
	public List<T> findAll();
	
	public boolean alreadyExists(T o);

}
