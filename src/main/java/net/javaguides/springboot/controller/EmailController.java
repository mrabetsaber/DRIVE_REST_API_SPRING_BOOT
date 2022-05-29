package net.javaguides.springboot.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.EmailService;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Email;
import net.javaguides.springboot.model.EmailAttachment;
import net.javaguides.springboot.model.ParametrageBackupEntity;
import net.javaguides.springboot.repository.ParametrageBackupRepository;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping
public class EmailController {
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	private ParametrageBackupRepository parametrageBackuprepository;
	

	@GetMapping("/sendEmail/{id}")

	public String sendEmail(@PathVariable(value = "id") Long id) {
			
		return emailService.sendEmail();
	}
	
	
	
	@GetMapping("/sendEmailwithAttachment/{id}")
	public String sendEmailwithAttachment(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException{
		ParametrageBackupEntity backup =parametrageBackuprepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
		String reciever =backup.getEmailReceiver();
		return emailService.sendEmailwithAttachment(reciever,backup.getEmailSender(),"","","");
	}
	
	@GetMapping("/getAllEmail")

	public List<EmailAttachment> getAllEmail() throws Exception{
		List<Email> emails = new ArrayList<Email>();
		List<EmailAttachment> attachements = new ArrayList<EmailAttachment>();

		 String host = "imap.gmail.com";
		  String user = "testgpro8@gmail.com";
		  String pass = "qojchltsnjzhixym";
			// Create empty properties
			  Properties props = System.getProperties();
			  props.setProperty("mail.store.protocol", "imaps");

			  // Get the session
			  Session session = Session.getInstance(props, null);

			  // Get the store
			  Store store = session.getStore("imaps");
			  store.connect(host, user, pass);

			  // Get folder
			  Folder folder = store.getFolder("INBOX");
			  folder.open(Folder.READ_WRITE);

			  
			  try {
			   // Get directory listing
			   Message messages[] = folder.getMessages();
			   for (int i = 0; i < messages.length; i++) {

			    Email email = new Email();
			    
			    

			    // from 
			    email.from = messages[i].getFrom()[0].toString();

			    // to list
			    Address[] toArray = messages[i] .getRecipients(Message.RecipientType.TO);
			   if(toArray!=null) { for (Address to : toArray) { email.to.add(to.toString()); }}

			    // cc list
			    Address[] ccArray = null;
			    try {
			     ccArray = messages[i] .getRecipients(Message.RecipientType.CC);
			    } catch (Exception e) { ccArray = null; }
			    if (ccArray != null) {
			     for (Address c : ccArray) {
			      email.cc.add(c.toString());
			     }
			    }

			    // subject
			    email.subject = messages[i].getSubject();

			    // received date
			    if (messages[i].getReceivedDate() != null) {
			     email.received = messages[i].getReceivedDate();
			    } else {
			     email.received = new Date();
			    }

			    // body and attachments
			    email.body = "";
			    Object content = messages[i].getContent();
			    if (content instanceof java.lang.String) {

			     email.body = (String) content;

			    } else if (content instanceof Multipart) {

			     Multipart mp = (Multipart) content;
			   
			     for (int j = 0; j < mp.getCount(); j++) {

			      Part part = mp.getBodyPart(j);
			   boolean hasfile=part.getFileName()!=null;
			      

			      if (!hasfile) {

			       MimeBodyPart mbp = (MimeBodyPart) part;
			       if (mbp.isMimeType("text/plain")) {
			        // Plain
			        email.body += (String) mbp.getContent();
			       } 

			      } else if (hasfile) {

			       // Check if plain
			       MimeBodyPart mbp = (MimeBodyPart) part;
			       if (mbp.isMimeType("text/plain")) {
			        email.body += (String) mbp.getContent();
			       } else {
			        EmailAttachment attachment = new EmailAttachment();
			        attachment.name = decodeName(part.getFileName());  
			        attachment.from=email.from;
			        attachements.add(attachment);
			        email.attachments.add(attachment);
			       }
			      }
			     } // end of multipart for loop 
			    } // end messages for loop

			    emails.add(email);

			    // Finally delete the message from the server.
			 //   messages[i].setFlag(Flags.Flag.DELETED, true);
			   }

			   // Close connection
			   folder.close(true); // true tells the mail server to expunge deleted messages
			   store.close();

			  } catch (Exception e) {
			   folder.close(true); // true tells the mail server to expunge deleted
			   store.close();
			   throw e;
			  }
			 return attachements;
			//  return emails;
			 }
		
		
	 private static String decodeName(String name) throws Exception {
		  if (name == null || name.length() == 0) {
		   return "unknown";
		  }
		  String ret = java.net.URLDecoder.decode(name, "UTF-8");

		  // also check for a few other things in the string:
		  ret = ret.replaceAll("=\\?utf-8\\?q\\?", "");
		  ret = ret.replaceAll("\\?=", "");
		  ret = ret.replaceAll("=20", " ");

		  return ret;
		 }
	
}


















