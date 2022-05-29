package net.javaguides.springboot;



import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.HistoriqueEntity;
import net.javaguides.springboot.repository.HistoriqueRepository;

@Service
public class EmailService {
	
	 
	

	
	
	
	
	
	@Autowired
	JavaMailSender javaMailSender;
	@Autowired
	HistoriqueRepository historiqueRepository;
	
	private HistoriqueEntity historique=new HistoriqueEntity();

	public String sendEmail() {
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom("mrabetsaber31@gmail.com");
		message.setTo("testgpro8@gmail.com");
		message.setSubject("test");
		message.setText("test");
		
		javaMailSender.send(message);
		
		return "Mail sent successfully";
	}
	
	
	public String sendEmailwithAttachment(String reciever, String sender, String url,String db,String client) {
		MimeMessage message = javaMailSender.createMimeMessage();
		
		LocalDateTime myDateObj = LocalDateTime.now();
		
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		
		String formattedDate = myDateObj.format(myFormatObj);
		   		try {
		   			MimeMessageHelper messageHelper = 
		   					new MimeMessageHelper(message, true);
			messageHelper.setFrom("mrabetsaber31@gmail.com");
			//String [] reciever1=reciever.split(";");
			messageHelper.setTo("mrabetsaber31@gmail.com");
			message.setSubject(client+"-BACKUP-"+db+formattedDate);
			
			messageHelper.setText("productionJour type production :");
			
			File file = new File("C:/Users/asus/Desktop/backup_CLIENT.backup");
			
		messageHelper.addAttachment(file.getName(), file);
		System.out.print("hey "+file.getName()+" "+reciever);
			javaMailSender.send(message);
			
			historique=new HistoriqueEntity(formattedDate,"Mail sent successfully");
			historiqueRepository.save(historique);
			return "Mail sent successfully";
			
			
		} catch (Exception e) {
			historique=new HistoriqueEntity(formattedDate,"Mail sent failed");
			historiqueRepository.save(historique);
			return "Mail sent failed";
		}
		
		}
	
	
	    public String sendHtmlEmail() throws MessagingException {
	 
	        MimeMessage message = javaMailSender.createMimeMessage();
	 
	        boolean multipart = true;
	         
	        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
	         
	        String htmlMsg = "<h3>Im testing send a HTML email</h3>"
	                +"<img src='http://www.apache.org/images/asf_logo_wide.gif'>";
	         
	        message.setContent(htmlMsg, "text/html");
	         
	        helper.setTo("mrabetsaber31@gmail.com");
	        helper.setFrom("mrabetsaber31@gmail.com");
	         
	        helper.setSubject("Test send HTML email");
	         
	     
	        this.javaMailSender.send(message);
	 
	        return "Email Sent!";
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}