package net.javaguides.springboot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/*import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;

import net.javaguides.springboot.model.ParametrageBackupEntity;
import net.javaguides.springboot.repository.ParametrageBackupRepository;
import net.javaguides.springboot.repository.ServerRepository;

@Configuration

@EnableScheduling

public class SchedulerConfig implements SchedulingConfigurer, DisposableBean {


    protected long cronExpressions;

    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @Autowired 
    ServerRepository servRepository;// to store the cronexpression in data base so that we can change on the fly when server is running.
    @Autowired
    ParametrageBackupRepository backupRepository;
    @Override

    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        servRepository.findAll().forEach(server->{
        	
        	cronExpressions=backupRepository.count();
        	server.getBackups().forEach(cron -> {
        

            Runnable runnableTask = () -> System.out.println("Task executed at ->" + new Date());


            Trigger trigger = new Trigger() {

            	Long newCronExpression ;

                @Override
                public Date nextExecutionTime(TriggerContext triggerContext) {
                
                    		
                    			
                    			
                    newCronExpression=backupRepository.count();
                    if (!(newCronExpression== cronExpressions)){

                        taskRegistrar.setTriggerTasksList(new ArrayList<TriggerTask>());

                    configureTasks(taskRegistrar); // calling recursively.

                        taskRegistrar.destroy(); // destroys previously scheduled tasks.
                        taskRegistrar.setScheduler(executor);

                        taskRegistrar.afterPropertiesSet(); // this will schedule the task with new cron changes.
                        System.out.println("fbqdfhbqfbsdhgbsudgoszgbsguysdbog");
                        return null; // return null when the cron changed so the trigger will stop.

                    }

                    CronTrigger crontrigger = new CronTrigger(cron.getSchedule());

                    return crontrigger.nextExecutionTime(triggerContext);

                }
            };

            taskRegistrar.addTriggerTask(runnableTask, trigger);

        });
        });

    }

    @Override

    public void destroy() throws Exception {

        if (executor != null) {
        	
            executor.shutdownNow();

        }

    }

}
*/
