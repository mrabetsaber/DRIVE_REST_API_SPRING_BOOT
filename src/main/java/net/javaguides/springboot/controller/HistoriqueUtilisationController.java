package net.javaguides.springboot.controller;

import java.io.IOException;
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
import net.javaguides.springboot.model.ParametrageHistoriqueEntity;
import net.javaguides.springboot.repository.HistoriqueUtilisationRepository;
import net.javaguides.springboot.repository.ParametrageHistoriqueRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping
public class HistoriqueUtilisationController {

	@Autowired
	private HistoriqueUtilisationRepository historiqueUtilisationRepository;
	@Autowired
	private ParametrageHistoriqueRepository parametrageHistoriqueRepository;
	
	/*@PostMapping("/createDB")
	public  void createTable( @RequestBody String dbName) {
		 try {
			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"cd C:/Program Files/PostgreSQL/9.3/bin/ &&"
					+"psql -U postgres &&"
			 		+ "CREATE DATABASE "+dbName
			 		);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	
	@GetMapping("/historiqueUtilisation")
	public List<HistoriqueUtilisation> getAllHistorique() {
		
		return historiqueUtilisationRepository.findAll();
	}
	
	@GetMapping("/historiqueUtilisation/{id}")
	public ResponseEntity<HistoriqueUtilisation> getHistoriqueUtilisationById(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		HistoriqueUtilisation historiqueUtilisation =historiqueUtilisationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
		return ResponseEntity.ok().body(historiqueUtilisation);
	}
	
	
	
	@PostMapping("/historiqueUtilisation")
	public HistoriqueUtilisation createhistoriqueUtilisation( @RequestBody HistoriqueUtilisation historiqueUtilisation) {
		return historiqueUtilisationRepository.save(historiqueUtilisation);
	}
	@PutMapping("/historiqueUtilisation/{id}")
	public ResponseEntity<HistoriqueUtilisation> updateHistoriqueUtilisation(@PathVariable(value = "id") Long historiqueUtilisationID,
			 @RequestBody HistoriqueUtilisation historiqueUtilisationDetails) throws ResourceNotFoundException {
		HistoriqueUtilisation historiqueUtilisation= historiqueUtilisationRepository.findById(historiqueUtilisationID)
				.orElseThrow(() -> new ResourceNotFoundException("Backup not found for this id :: " + historiqueUtilisationID));

		historiqueUtilisation.setLineNumber(historiqueUtilisationDetails.getLineNumber());
		
		final HistoriqueUtilisation updatedhistoriqueUtilisation = historiqueUtilisationRepository.save(historiqueUtilisation);
		return ResponseEntity.ok(updatedhistoriqueUtilisation);
	}

	
	
	
	@DeleteMapping("/historiqueUtilisation/{id}")
	public Map<String, Boolean> deletehistoriqueUtilisation(@PathVariable(value = "id") Long historiqueUtilisationId)
			throws ResourceNotFoundException {
		HistoriqueUtilisation historiqueUtilisation = historiqueUtilisationRepository.findById(historiqueUtilisationId)
				.orElseThrow(() -> new ResourceNotFoundException("Backup not found for this id :: " + historiqueUtilisationId));

		historiqueUtilisationRepository.delete(historiqueUtilisation);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	@PostMapping("/parametrageHistorique/{parametrageHistoriqueId}/historiqueUtilisation")
	public HistoriqueUtilisation addTag(@PathVariable(value = "parametrageHistoriqueId") Long parametrageHistoriqueId, @RequestBody HistoriqueUtilisation historiqueUtilisationRequest) throws ResourceNotFoundException {
		HistoriqueUtilisation historiqueUtilisation = parametrageHistoriqueRepository.findById(parametrageHistoriqueId).map((Function<? super ParametrageHistoriqueEntity, ? extends HistoriqueUtilisation>)parmetrageh -> {
	    long historiqueUtilisationId =historiqueUtilisationRequest.getId();
	    
	    // backup is existed
	    if (historiqueUtilisationId != 0L) {
	      HistoriqueUtilisation _backup = new HistoriqueUtilisation();
		try {
			_backup = historiqueUtilisationRepository.findById(historiqueUtilisationId)
			      .orElseThrow(() -> new ResourceNotFoundException("Not found ParametrageBackupEntity with id = " + historiqueUtilisationId));
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parmetrageh.addHistorique(_backup);
	      parametrageHistoriqueRepository.save(parmetrageh);
	      return _backup;
	    }
	    
	    // add and create new ParametrageBackupEntity
	    parmetrageh.addHistorique(historiqueUtilisationRequest);
	    return historiqueUtilisationRepository.save(historiqueUtilisationRequest);
	  }).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + parametrageHistoriqueId));
	  return historiqueUtilisation;
	}
}
	
	