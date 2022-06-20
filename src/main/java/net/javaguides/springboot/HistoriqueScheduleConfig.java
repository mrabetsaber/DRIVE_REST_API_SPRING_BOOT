

package net.javaguides.springboot;



import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import net.javaguides.springboot.model.HistoriqueUtilisation;
import net.javaguides.springboot.repository.HistoriqueBackupRepository;
import net.javaguides.springboot.repository.HistoriqueUtilisationRepository;
import net.javaguides.springboot.repository.ParametrageBackupRepository;
import net.javaguides.springboot.repository.ParametrageHistoriqueRepository;
import net.javaguides.springboot.repository.ServerRepository;




@Configuration

@EnableScheduling
public class HistoriqueScheduleConfig implements SchedulingConfigurer {
	

	@Autowired
	HistoriqueUtilisationRepository historiqueUtilisationRepository;
	@Autowired
	ParametrageHistoriqueRepository parametrageRepository;
	@Autowired
	HistoriqueUtilisationRepository utilisationRepository;
	

	
	
	 
	
	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) { 


	
	
	
		
		parametrageRepository.findAll().forEach(parametragehistorique-> {
			
				
			
			
			
			Runnable runnable = () ->{
				LocalDateTime myDateObj = LocalDateTime.now();
				
				DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				
				String formattedDate = myDateObj.format(myFormatObj);
				int PostNumber=0;
				int deleteNumber=0;
				int updateNumber=0;
				int getNumber=0;

				String operation;
				HistoriqueUtilisation h =new HistoriqueUtilisation();
				 try {
				      File myObj = new File(parametragehistorique.getFileName());
				      Scanner myReader = new Scanner(myObj);
				      while (myReader.hasNextLine()) {
				        String data = myReader.nextLine();
				        operation=data.split("\"")[1];
				        if(operation.split("/")[2].equals(parametragehistorique.getTableName())) {
				        	if(operation.split(" ")[0].equals("POST")) {
				        		 PostNumber++;
				        	}else if(operation.split(" ")[0].equals("GET")) {
				        		getNumber++;
				        	}else if(operation.split(" ")[0].equals("UPDATE")) {
				        		updateNumber++;
				        	}else if(operation.split(" ")[0].equals("DELETE")) {
				        		deleteNumber++;
				        	}
				        }
				      }
				      h.setPostNumber(PostNumber);
				      h.setUpdateNumber(updateNumber);
				      h.setDeleteNumber(deleteNumber);
				      h.setGetNumber(getNumber);
				      h.setTableName("C:\\Users\\asus\\Downloads\\logsMois4et5annee22\\"+parametragehistorique.getTableName());
				      h.setDate(formattedDate);
				      myReader.close();
				    } catch (FileNotFoundException e) {
				      System.out.println("An error occurred.");
				      e.printStackTrace();
				    }  		
       		

       		 try {
       			historiqueUtilisationRepository.save(h);
       			 
       			 } catch (Exception e) {
       			 // TODO Auto-generated catch block
       			 e.printStackTrace();
       			 }
	        	
       		 
	        	};
	           
	        
			
			scheduledTaskRegistrar.addTriggerTask(runnable,
			triggerContext -> {

		          //2.1 Get the execution cycle from the database

		          
		          
		          

		          //2.2 Legality check.

		          

		          //2.3 return execution cycle (Date)

		         return new CronTrigger(parametragehistorique.getTime()).nextExecutionTime(triggerContext);

			});
			
			
		});
		
		
// Split the cronExpression with pipe and for each expression add the same task.
		
		
		

	   
	
	
	}
}
