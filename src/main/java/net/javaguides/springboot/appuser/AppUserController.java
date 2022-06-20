package net.javaguides.springboot.appuser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.ErrorReceiver;
import net.javaguides.springboot.repository.ErrorReceiverRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping

public class AppUserController {

	@Autowired
	AppUserRepository userRepository;
	
	@GetMapping("/appUser")
	public List<AppUser> getAllerrorReceiver() {
		
		return userRepository.findAll();
	}
	
	@GetMapping("/appUser/{id}")
	public ResponseEntity<AppUser> getEmployeeById(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		AppUser appUser =userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found for this id :: " + id));
		return ResponseEntity.ok().body(appUser);
	}
	
	
	
	@PostMapping("/appUser")
	public AppUser createBackup( @RequestBody AppUser appUser) {
		return userRepository.save(appUser);
	}

	@PutMapping("/appUser/{id}")
	public ResponseEntity<AppUser> updateErrorReceiver(@PathVariable(value = "id") Long AppUserId,
			 @RequestBody AppUser appUserDetail) throws ResourceNotFoundException {
		AppUser appUser= userRepository.findById(AppUserId)
				.orElseThrow(() -> new ResourceNotFoundException("Error Receiver not found for this id :: " + AppUserId));

		appUser.setAppUserRole(appUserDetail.getAppUserRole());
		appUser.setEmail(appUserDetail.getEmail());
		appUser.setFirstName(appUserDetail.getFirstName());
		appUser.setLastName(appUserDetail.getLastName());
		
		AppUser updatedUser = userRepository.save(appUser);
		return ResponseEntity.ok(updatedUser);
	}

	
	
	
	@DeleteMapping("/appUser/{id}")
	public Map<String, Boolean> deleteBackup(@PathVariable(value = "id") Long appUserId)
			throws ResourceNotFoundException {
		AppUser appUser = userRepository.findById(appUserId)
				.orElseThrow(() -> new ResourceNotFoundException("Error Receiver not found for this id :: " + appUserId));

		userRepository.delete(appUser);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
