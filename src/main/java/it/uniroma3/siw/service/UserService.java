package it.uniroma3.siw.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.UserRepository;

@Service
public class UserService implements IServices<User>{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void save(User o) {
		userRepository.save(o);
	}
	
	@Override
	public void update(User o) {
		this.delete(o);
		this.save(o);
	}


	@Override
	public void delete(User o) {
		userRepository.delete(o);
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public List<User> findAll() {
		List<User> users = new LinkedList<User>();

		for (User i : userRepository.findAll()) {
			users.add(i);
		}

		return users;
	}

	@Override
	public boolean alreadyExists(User target) {
		//We still have to see this part during the lessons.
		return false;
	}

}
