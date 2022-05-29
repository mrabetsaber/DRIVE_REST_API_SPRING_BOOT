package net.javaguides.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.model.HistoriqueEntity;
import net.javaguides.springboot.repository.HistoriqueRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping
public class HistoriqueController {

	@Autowired
	HistoriqueRepository historiqueRepository;
	
	@GetMapping("/historique")
	public List<HistoriqueEntity> getAllBackup() {
		
		return historiqueRepository.findAll();
	}
	
}
