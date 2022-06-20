package net.javaguides.springboot.controller;

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

public class ErrorReceiverController {

	@Autowired
	ErrorReceiverRepository errorReceiverRepository;
	
	@GetMapping("/errorReceiver")
	public List<ErrorReceiver> getAllerrorReceiver() {
		
		return errorReceiverRepository.findAll();
	}
	
	@GetMapping("/errorReceiver/{id}")
	public ResponseEntity<ErrorReceiver> getEmployeeById(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		ErrorReceiver errorReceiver =errorReceiverRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error Receiver not found for this id :: " + id));
		return ResponseEntity.ok().body(errorReceiver);
	}
	
	
	
	@PostMapping("/errorReceiver")
	public ErrorReceiver createBackup( @RequestBody ErrorReceiver errorReceiver) {
		return errorReceiverRepository.save(errorReceiver);
	}

	
	@PutMapping("/errorReceiver/{id}")
	public ResponseEntity<ErrorReceiver> updateErrorReceiver(@PathVariable(value = "id") Long ErrorReceiverId,
			 @RequestBody ErrorReceiver errorReceiverDetail) throws ResourceNotFoundException {
		ErrorReceiver errorReceiver= errorReceiverRepository.findById(ErrorReceiverId)
				.orElseThrow(() -> new ResourceNotFoundException("Error Receiver not found for this id :: " + ErrorReceiverId));

		errorReceiver.setEmailReceiver(errorReceiverDetail.getEmailReceiver());
		
		
		final ErrorReceiver updatederrorReceiver = errorReceiverRepository.save(errorReceiver);
		return ResponseEntity.ok(updatederrorReceiver);
	}

	
	
	
	@DeleteMapping("/errorReceiver/{id}")
	public Map<String, Boolean> deleteBackup(@PathVariable(value = "id") Long ErrorReceiverId)
			throws ResourceNotFoundException {
		ErrorReceiver errorReceiver = errorReceiverRepository.findById(ErrorReceiverId)
				.orElseThrow(() -> new ResourceNotFoundException("Error Receiver not found for this id :: " + ErrorReceiverId));

		errorReceiverRepository.delete(errorReceiver);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	

}
