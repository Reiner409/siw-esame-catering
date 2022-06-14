package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.controller.validator.UserValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsServices;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsServices credentialsService;
	@Autowired
	private CredentialsValidator credentialsValidator;
	@Autowired
	private UserValidator userValidator;

	@GetMapping("/login") 
	public String showLoginForm (Model model) {
		return "loginForm";
	}
		
	@GetMapping("/logout") 
	public String logout(Model model) {
		return "/logout";
	}	
	
    @GetMapping("/default")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "index";
        }
        return "index";
    }

	@GetMapping("/registerForm")
	public String getRegistrationForm(Model model)
	{
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "registerUserForm";
	}
	
	@PostMapping("/registerForm")
	public String registerUser(@ModelAttribute("user") User user,
            BindingResult userBindingResult,
            @ModelAttribute("credentials") Credentials credentials,
            BindingResult credentialsBindingResult,
            Model model)
	{
		this.userValidator.validate(user, userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);
		
		if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            // set the user and store the credentials;
            // this also stores the User, thanks to Cascade.ALL policy
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            return "registrationSuccessful";
		}
		else
		{
			return "registerUser";
		}
	}
	
	@GetMapping("/registrationSuccessful")
	public String getRegistrationSuccessful(Model model)
	{
		return "registrationSuccessful";
	}
	
	
	
	
}
