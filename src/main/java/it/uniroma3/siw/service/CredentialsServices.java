package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;

@Service
public class CredentialsServices {
	
	@Autowired
	private CredentialsRepository credentialsRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;
	//Maybe i need to add this autowired and get the user-id before getting the user by username
	//@Autowired
	//private UserRepository userRepository
	
	public Credentials getCredentials(Long id)
	{
		return credentialsRepository.findById(id).get();
	}
	
	public Credentials getCredentials(String username)
	{
		return credentialsRepository.findByUsername(username);
	}
	
	public Credentials saveCredentials(Credentials credentials)
	{
		credentials.setAdminRole();
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
   
	}
	
	public Boolean alreadyExists(Credentials credentials)
	{
		//We still have to see this part during the lessons.
		return false;
	}
}
