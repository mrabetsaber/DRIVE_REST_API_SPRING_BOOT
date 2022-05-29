
/*
package net.javaguides.springboot;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import net.javaguides.springboot.repository.HistoriqueBackupRepository;
import net.javaguides.springboot.repository.HistoriqueUtilisationRepository;
import net.javaguides.springboot.repository.ParametrageBackupRepository;
import net.javaguides.springboot.repository.ParametrageHistoriqueRepository;
import net.javaguides.springboot.repository.ServerRepository;




@Configuration

@EnableScheduling
public class HistoriqueScheduleConfig implements SchedulingConfigurer {
	

	@Autowired
	HistoriqueBackupRepository historiqueBackupRepository;
	@Autowired
	ParametrageHistoriqueRepository parametrageRepository;
	@Autowired
	HistoriqueUtilisationRepository utilisationRepository;
	

	@Autowired
	  @SuppressWarnings("all")

	  Data data;
	
	 
	
	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) { 


	
	
	
		
		parametrageRepository.findAll().forEach(parametragehistorique-> {
			
				
			
			
			
			Runnable runnable = () ->{
				LocalDateTime myDateObj = LocalDateTime.now();
				
				DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
				
				String formattedDate = myDateObj.format(myFormatObj);
				
			
				HistoriqueBackup h = new HistoriqueBackup(data.getProduction(),data.getVignette(),data.getSORTIE()
						,data.getENGAGEMENT(),data.getEntreTissu(),data.getEntreForniture(),data.getSortieeTissu(),data.getSortieeForniture(),
						data.getINVENTAIRE_INTERNETissu(),data.getINVENTAIRE_INTERNEFourniture(),data.getRETOURTissu(),data.getRETOURFourniture()
						,data.getRESERVATIONTissu(),data.getRESERVATIONFourniture(),formattedDate);
				
       		
       		

       		 try {
       			historiqueBackupRepository.save(h);
       			 
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
}*/
