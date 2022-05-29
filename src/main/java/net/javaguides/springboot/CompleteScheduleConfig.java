
/*
package net.javaguides.springboot;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import net.javaguides.springboot.repository.ParametrageBackupRepository;
import net.javaguides.springboot.repository.ServerRepository;




@Configuration

@EnableScheduling
public class CompleteScheduleConfig implements SchedulingConfigurer {
	

	@Autowired
	EmailService emailservice;
	@Autowired
	ServerRepository serv;
	@Autowired
	ParametrageBackupRepository backupRepository;
	

	@Autowired
	  @SuppressWarnings("all")

	  CronMapper cronMapper;
	
	 
	private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	// private static final List<String> SCOPES =
	// Collections.singletonList(DriveScopes.DRIVE);

	private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE,
			"https://www.googleapis.com/auth/drive.install");

	private static final String USER_IDENTIFIER_KEY = "MY_DUMMY_USE";

	@Value("${google.oauth.callback.uri}")
	private String CALLBACK_URI;

	@Value("${google.secret.key.path}")
	private Resource gdSecretKeys;

	@Value("${google.credentials.folder.path}")
	private Resource credentialsFolder;
	
	@Value("${google.service.account.key}")
	private Resource serviceAccountKey;

	private GoogleAuthorizationCodeFlow flow;

	@PostConstruct
	public void init() throws Exception {
		
		GoogleClientSecrets secrets = GoogleClientSecrets.load(JSON_FACTORY,
				new InputStreamReader(gdSecretKeys.getInputStream()));
		flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, secrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(credentialsFolder.getFile())).build();
		
	}

	@GetMapping(value = { "/" })
	public String showHomePage() throws Exception {
		boolean isUserAuthenticated = false;
		

		Credential credential = flow.loadCredential(USER_IDENTIFIER_KEY);
		if (credential != null) {
			boolean tokenValid = credential.refreshToken();
			if (tokenValid) {
				isUserAuthenticated = true;
			}
		}

		return isUserAuthenticated ? "" : "index.html";
	}

	@GetMapping(value = { "/googlesignin" })
	public void doGoogleSignIn(HttpServletResponse response) throws Exception {
		GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
		String redirectURL = url.setRedirectUri(CALLBACK_URI).setAccessType("offline").build();
		response.sendRedirect(redirectURL);
	}

	@GetMapping(value = { "/oauth" })
	public String saveAuthorizationCode(HttpServletRequest request) throws Exception {
		String code = request.getParameter("code");
		if (code != null) {
			saveToken(code);

			return "forward:/create";
		}

		return "index.html";
	}

	private void saveToken(String code) throws Exception {
		GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(CALLBACK_URI).execute();
		flow.createAndStoreCredential(response, USER_IDENTIFIER_KEY);

	}
	

  
  public Object create(String client ,String db,String url) throws Exception {
	  Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);
	  	
		 
		 
		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
				.setApplicationName("googledrivespringbootexample").build();
		FileList fileList = drive.files().list().setFields("files(id,name,thumbnailLink)").execute();
		for (File file2 : fileList.getFiles()) {
			if(file2.getName().equals(client+"-"+db)) {
	        	System.out.println(file2.getName());

				
				File file = new File();
				
				file.setName(client+"-BACKUP-"+db);
				file.setParents(Arrays.asList(file2.getId()));
				
				FileContent content = new FileContent("application/octet-stream", new java.io.File(url));
				File uploadedFile = drive.files().create(file, content).setFields("id").execute();
				String fileReference = String.format("{fileID: '%s'}", uploadedFile.getId());
	        	System.out.println("Yeah");

				return null;
			}
			
		}


		File file = new File();
		
		file.setName(client+"-BACKUP-"+db);
		file.setParents(Arrays.asList(createFolder(client,db)));
		
		FileContent content = new FileContent("application/octet-stream", new java.io.File(url));
		File uploadedFile = drive.files().create(file, content).setFields("id").execute();
		String fileReference = String.format("{fileID: '%s'}", uploadedFile.getId());
    	System.out.println("Yeah");

		return null;
  }
  
  public String createFolder(String client,String db) throws Exception  {
	  Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);

		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
				.setApplicationName("googledrivespringbootexample").build();
		 
		
		File file = new File();
		file.setName(client+"-"+db);
		file.setMimeType("application/vnd.google-apps.folder");

	
		File file1 = drive.files().create(file).execute();
		return file1.getId();
	  
  }	
	
  //############################################
	private  void DownloadFile(String fileId) {
		try {
			Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);
			
			Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
					.setApplicationName("googledrivespringbootexample").build();
			
			Drive.Files.Get request = drive.files().get(fileId);
			HttpResponse progress = request.executeMedia();
			if (progress != null) {
				InputStream stream = progress.getContent();
				FileOutputStream file = new FileOutputStream(("d:\\zekiri.backup"));
				try {
					int l;
					byte[] tmp = new byte[2048];
					while ((l = stream.read(tmp)) != -1) {
						file.write(tmp, 0, l);
					}
				} finally {
					file.close();
					stream.close();
				}
			}
		}
		catch(IOException ex)
		{
			System.out.println(ex.toString());
		}
	}
  //############################################

	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) { 


	
	
		String[] cron1 = cronMapper.getCron();
		String[]client=cronMapper.getClient();
		String[] dbName=cronMapper.getsDataBaseName();
		String[]reciver=cronMapper.getReciver();
		String[]sender=cronMapper.getSender();
		
		//for(int i=0;i<cron1.length;i++) {
		serv.findAll().forEach(server-> {
			server.getBackups().forEach(backup->{
				
			
			
			
			Runnable runnable = () ->{

				
			
       		 if(server.getType().equals("POSTGRESQL")) {
	        	 try
	        	 {	        		
	        			 Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"cd C:/Program Files/PostgreSQL/9.3/bin/ &&  pg_dump -h \"localhost\" -U \"postgres\" -F \"custom\" --blobs --verbose  -f \"C:\\Users\\asus\\Desktop\\%PG_DATABASENAME%_%CLIENT_NAME%.backup\" \"backup\"");
	        		 }
	 	         // We are running "dir" and "ping" command on cmd
	 	        
	 	        
	 	        catch (Exception e)
	 	        {
	 	            System.out.println("HEY Buddy ! U r Doing Something Wrong ");
	 	            e.printStackTrace();
	 	        }
       		}
	        
       		
       		

       		 try {
       			
       			 
       			//create(client1,dbName1,"");
       			 System.out.println("test");
       		//	DownloadFile("1VBt1CBwIda7JZyTsUDBnW1J90YC4-d2w");
       			emailservice.sendEmailwithAttachment(backup.getEmailReceiver(), backup.getEmailSender(), "", backup.getDataBaseName(), backup.getClientName());
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

		         return new CronTrigger(backup.getSchedule()).nextExecutionTime(triggerContext);

			});
			});
			
		});
		
		
// Split the cronExpression with pipe and for each expression add the same task.
		
		
		

	   
	
	
	}
}
*/