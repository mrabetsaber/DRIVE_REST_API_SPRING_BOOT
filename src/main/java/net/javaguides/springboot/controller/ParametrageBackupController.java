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
import net.javaguides.springboot.model.Restore;
import net.javaguides.springboot.model.Server;
import net.javaguides.springboot.repository.ParametrageBackupRepository;
import net.javaguides.springboot.repository.ServerRepository;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping
public class ParametrageBackupController {
	@Autowired
	private ParametrageBackupRepository parametrageBackuprepository;
	@Autowired
	private ServerRepository serverepository;
	
	
	@GetMapping("/backup")
	public List<ParametrageBackupEntity> getAllBackup() {
		
		return parametrageBackuprepository.findAll();
	}
	
	@GetMapping("/backup/{id}")
	public ResponseEntity<ParametrageBackupEntity> getEmployeeById(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		ParametrageBackupEntity backup =parametrageBackuprepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
		return ResponseEntity.ok().body(backup);
	}
	
	
	
	@PostMapping("/backup")
	public ParametrageBackupEntity createBackup( @RequestBody ParametrageBackupEntity backup) {
		return parametrageBackuprepository.save(backup);
	}

	
	@PutMapping("/backup/{id}")
	public ResponseEntity<ParametrageBackupEntity> updateBackup(@PathVariable(value = "id") Long BackupId,
			 @RequestBody ParametrageBackupEntity backupDetails) throws ResourceNotFoundException {
		ParametrageBackupEntity backup= parametrageBackuprepository.findById(BackupId)
				.orElseThrow(() -> new ResourceNotFoundException("Backup not found for this id :: " + BackupId));

		backup.setEmailReceiver(backupDetails.getEmailReceiver());
		backup.setEmailSender(backupDetails.getEmailSender());
		backup.setSchedule(backupDetails.getSchedule());
		
		final ParametrageBackupEntity updatedBackup = parametrageBackuprepository.save(backup);
		return ResponseEntity.ok(updatedBackup);
	}

	
	
	
	@DeleteMapping("/backup/{id}")
	public Map<String, Boolean> deleteBackup(@PathVariable(value = "id") Long BackupId)
			throws ResourceNotFoundException {
		ParametrageBackupEntity backup = parametrageBackuprepository.findById(BackupId)
				.orElseThrow(() -> new ResourceNotFoundException("Backup not found for this id :: " + BackupId));

		parametrageBackuprepository.delete(backup);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	@PostMapping("/servers/{serverId}/backups")
	public ParametrageBackupEntity addTag(@PathVariable(value = "serverId") Long serverId, @RequestBody ParametrageBackupEntity backupRequest) throws ResourceNotFoundException {
	  ParametrageBackupEntity backup = serverepository.findById(serverId).map((Function<? super Server, ? extends ParametrageBackupEntity>)server -> {
	    long backupId = backupRequest.getId();
	    
	    // backup is existed
	    if (backupId != 0L) {
	      ParametrageBackupEntity _backup = new ParametrageBackupEntity();
		try {
			_backup = parametrageBackuprepository.findById(backupId)
			      .orElseThrow(() -> new ResourceNotFoundException("Not found ParametrageBackupEntity with id = " + backupId));
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      server.addBackup(_backup);
	      serverepository.save(server);
	      return _backup;
	    }
	    
	    // add and create new ParametrageBackupEntity
	    server.addBackup(backupRequest);
	    return parametrageBackuprepository.save(backupRequest);
	  }).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + serverId));
	  return backup;
	}
	
	@PostMapping("/backup/restore")
	public void restoreBackup( @RequestBody Restore restore) {
		
		
		   System.out.println(restore.getDataBaseName());
		try
   	 {	        		
   			 Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"cd C:/Program Files/PostgreSQL/9.3/bin/ &&  pg_restore.exe --host localhost --port 5432 --username \"postgres\" --dbname \""
   			 		+ restore.getDataBaseName()+"\" --role \"postgres\" --no-password  --verbose \"C:/Users/asus/Desktop/backup_CLIENT.backup\"");
   		 }
         // We are running "dir" and "ping" command on cmd
        
        
        catch (Exception e)
        {
            System.out.println("HEY Buddy ! U r Doing Something Wrong ");
            e.printStackTrace();
        }
	}
	
	
}
