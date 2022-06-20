package net.javaguides.springboot.login;

import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import lombok.AllArgsConstructor;
import net.javaguides.springboot.appuser.AppUser;
import net.javaguides.springboot.appuser.AppUserRepository;
import net.javaguides.springboot.exception.ResourceNotFoundException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping
@AllArgsConstructor
public class LoginController {
	
	AppUserRepository userRepository;
	 private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@PostMapping(path="/login")
    public AppUser login(@RequestBody Login request) throws ResourceNotFoundException {
		 String encodedPassword = bCryptPasswordEncoder
	                .encode(request.getPassword());
		 
		 AppUser user= userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("user not found with email "+request.email));

		user= userRepository.findByEmailAndPassword(request.getEmail(),request.getPassword()).orElseThrow(() -> new ResourceNotFoundException("wrong password "));
    return user;
    
    
    
		
	}
	@GetMapping("/user/{id}")
public Optional<AppUser> getuserById(@PathVariable(value = "id") Long id) {
		return userRepository.findById(id);
	}
	
	

}
