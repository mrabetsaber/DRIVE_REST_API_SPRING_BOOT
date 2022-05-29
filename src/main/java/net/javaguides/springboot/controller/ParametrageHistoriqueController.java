package net.javaguides.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
import net.javaguides.springboot.model.HistoriqueUtilisation;
import net.javaguides.springboot.model.ParametrageBackupEntity;
import net.javaguides.springboot.model.ParametrageHistoriqueEntity;
import net.javaguides.springboot.model.Server;
import net.javaguides.springboot.repository.HistoriqueUtilisationRepository;
import net.javaguides.springboot.repository.ParametrageBackupRepository;
import net.javaguides.springboot.repository.ParametrageHistoriqueRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping
public class ParametrageHistoriqueController {
	@Autowired
	ParametrageHistoriqueRepository parmetrage;
	@Autowired
	HistoriqueUtilisationRepository historiqueUtilisationRepository;
	
	@GetMapping("/parametrageHistorique")
	public List<ParametrageHistoriqueEntity> getAllBackup() {
		
		return parmetrage.findAll();
	}
	
	@GetMapping("/parametrageHistorique/{id}")
	public ResponseEntity<ParametrageHistoriqueEntity> getEmployeeById(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		ParametrageHistoriqueEntity parametrageHistorique =parmetrage.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
		return ResponseEntity.ok().body(parametrageHistorique);
	}
	
	
	
	@PostMapping("/parametrageHistorique")
	public ParametrageHistoriqueEntity createBackup( @RequestBody ParametrageHistoriqueEntity parametrageHistorique) {
		return parmetrage.save(parametrageHistorique);
	}
	@PutMapping("/parametrageHistorique/{id}")
	public ResponseEntity<ParametrageHistoriqueEntity> updateBackup(@PathVariable(value = "id") Long parametrageHistoriqueId,
			 @RequestBody ParametrageHistoriqueEntity parametrageHistoriqueDetails) throws ResourceNotFoundException {
		ParametrageHistoriqueEntity parametrageHistorique= parmetrage.findById(parametrageHistoriqueId)
				.orElseThrow(() -> new ResourceNotFoundException("server not found for this id :: " + parametrageHistoriqueId));

		parametrageHistorique.setTableName(parametrageHistoriqueDetails.getTableName())	;
		parametrageHistorique.setTime(parametrageHistoriqueDetails.getTime());
		final ParametrageHistoriqueEntity updatedparametrageHistorique = parmetrage.save(parametrageHistorique);
		return ResponseEntity.ok(updatedparametrageHistorique);
	}

	
	
	
	@DeleteMapping("/parametrageHistorique/{id}")
	public Map<String, Boolean> deleteBackup(@PathVariable(value = "id") Long parametrageHistoriquenId)
			throws ResourceNotFoundException {
		ParametrageHistoriqueEntity parametrageHistorique = parmetrage.findById(parametrageHistoriquenId)
				.orElseThrow(() -> new ResourceNotFoundException("server not found for this id :: " + parametrageHistoriquenId));

		parmetrage.delete(parametrageHistorique);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	@PostMapping("/historiqueUtilisation/{historiqueUtilisationId}/parametrageHistorique")
	public ParametrageHistoriqueEntity addTag(@PathVariable(value = "historiqueUtilisationId") Long historiqueId, @RequestBody ParametrageHistoriqueEntity parametrageHistoriqueRequest) throws ResourceNotFoundException {
		ParametrageHistoriqueEntity parametrageHistorique = historiqueUtilisationRepository.findById(historiqueId).map((Function<? super HistoriqueUtilisation, ? extends ParametrageHistoriqueEntity>)hesitoriqueh -> {
	    long parametrageHistoriqueId =parametrageHistoriqueRequest.getId();
	    
	    // backup is existed
	    if (parametrageHistoriqueId != 0L) {
	    	ParametrageHistoriqueEntity _backup = new ParametrageHistoriqueEntity();
		try {
			_backup = parmetrage.findById(parametrageHistoriqueId)
			      .orElseThrow(() -> new ResourceNotFoundException("Not found ParametrageBackupEntity with id = " + parametrageHistoriqueId));
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hesitoriqueh.addParametrageHistorique(_backup);
		historiqueUtilisationRepository.save(hesitoriqueh);
	      return _backup;
	    }
	    
	    // add and create new ParametrageBackupEntity
	    hesitoriqueh.addParametrageHistorique(parametrageHistoriqueRequest);
	    return parmetrage.save(parametrageHistoriqueRequest);
	  }).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + historiqueId));
	  return parametrageHistorique;
	}

}
