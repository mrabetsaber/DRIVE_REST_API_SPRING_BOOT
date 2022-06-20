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
import net.javaguides.springboot.model.ParametrageBackupEntity;
import net.javaguides.springboot.model.Server;
import net.javaguides.springboot.repository.ParametrageBackupRepository;
import net.javaguides.springboot.repository.ServerRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping
public class ServerController {
	@Autowired
	private ServerRepository serverepository;
	
	@Autowired
	private ParametrageBackupRepository backupRepository;
	
	@GetMapping("/server")
	public List<Server> getAllBackup() {
		
		return serverepository.findAll();
	}
	
	@GetMapping("/server/{id}")
	public ResponseEntity<Server> getEmployeeById(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		Server server =serverepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("server not found for this id :: " + id));
		return ResponseEntity.ok().body(server);
	}
	
	
	
	@PostMapping("/server")
	public Server createBackup( @RequestBody Server server) {
		return serverepository.save(server);
	}

	
	@PutMapping("/server/{id}")
	public ResponseEntity<Server> updateBackup(@PathVariable(value = "id") Long serverId,
			 @RequestBody Server serverDetails) throws ResourceNotFoundException {
		Server server= serverepository.findById(serverId)
				.orElseThrow(() -> new ResourceNotFoundException("server not found for this id :: " + serverId));

		server.setHost(serverDetails.getHost());
		server.setUserName(serverDetails.getUserName());
		server.setPassword(serverDetails.getPassword());
		
		final Server updatedserver = serverepository.save(server);
		return ResponseEntity.ok(updatedserver);
	}

	
	
	
	@DeleteMapping("/server/{id}")
	public Map<String, Boolean> deleteBackup(@PathVariable(value = "id") Long serverId)
			throws ResourceNotFoundException {
		Server server = serverepository.findById(serverId)
				.orElseThrow(() -> new ResourceNotFoundException("server not found for this id :: " + serverId));

		serverepository.delete(server);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	  @PostMapping("/backup/{backupId}/server")
	  public Server addTag(@PathVariable(value = "backupId") Long backupId, @RequestBody Server serverRequest) throws ResourceNotFoundException {
	    Server server = backupRepository.findById(backupId).map( (Function<? super ParametrageBackupEntity, ? extends Server>) backup -> {
	      Long serverId = serverRequest.getId();
	      
	      // tag is existed
	      if (serverId != 0L) {
	        Server _server = null;
			try {
				_server = serverepository.findById(serverId)
				    .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + serverId));
			} catch (ResourceNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        backup.addServer(_server);
	        backupRepository.save(backup);
	        return _server;
	      }
	      
	      // add and create new Tag
	      backup.addServer(serverRequest);
	      return serverepository.save(serverRequest);
	   }).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = "+ backupId));
	    return server;
	  }
	
	

}
