package net.javaguides.springboot.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.EmailService;
import net.javaguides.springboot.model.ErrorReceiver;
import net.javaguides.springboot.model.Log;
import net.javaguides.springboot.repository.ErrorReceiverRepository;
import net.javaguides.springboot.repository.LogRepository;


@Configuration

@EnableScheduling
@Service
@AllArgsConstructor
public class LogService {

	private final LogRepository logRepository;
	private final EmailService emailService;
	private final ErrorReceiverRepository errorReceiverRepository;
	
	public void sendEmail(Log log) {
		if (log.isNotSent()) {
			if(log.getMessage().split(" ").length>4) {
			Boolean iserror=!log.getMessage().split(" ")[3].equals("INFO");
			if(iserror) {
				errorReceiverRepository.findAll().forEach(receiver->{
					
					try {
						emailService.sendHtmlEmail(receiver.getEmailReceiver(),log.getMessage().split(" ")[2] +log.getMessage().split(" ")[3],log.getMessage());
						logRepository.enableSent(log.getId());
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			}
			else {
				logRepository.delete(log);}
		}
			else {
				logRepository.delete(log);
			}
			}
	}
//	@Scheduled(cron = "* * * * * ?")
	public void checkLog() {
		
		List<Log> logList=logRepository.findAll();
		logList.forEach(log->{
		sendEmail(log);
			
			
			
		});
	}
}
