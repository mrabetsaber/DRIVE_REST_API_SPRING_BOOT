package net.javaguides.springboot;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
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

import net.javaguides.springboot.model.HistoriqueEntity;
import net.javaguides.springboot.repository.HistoriqueRepository;
import net.javaguides.springboot.repository.ParametrageBackupRepository;
import net.javaguides.springboot.repository.ServerRepository;




@Configuration

@EnableScheduling
public class CompleteScheduleConfig implements SchedulingConfigurer {
	
	@Autowired
	HistoriqueRepository historiqueRepository;
	@Autowired
	EmailService emailservice;
	@Autowired
	ServerRepository serv;
	@Autowired
	ParametrageBackupRepository backupRepository;
	

	@Autowired
	  @SuppressWarnings("all")

	  CronMapper cronMapper;
	
	 protected long cronExpressions;
	private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	// private static final List<String> SCOPES =
	// Collections.singletonList(DriveScopes.DRIVE);

	private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE,
			"https://www.googleapis.com/auth/drive.install");

	private static final String USER_IDENTIFIER_KEY = "MY_DUMMY_USER";

	@Value("${google.oauth.callback.uri}")
	private String CALLBACK_URI;

	@Value("${google.secret.key.path}")
	private Resource gdSecretKeys;

	@Value("${google.credentials.folder.path}")
	private Resource credentialsFolder;
	
	@Value("${google.service.account.key}")
	private Resource serviceAccountKey;


	private GoogleAuthorizationCodeFlow flow;
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


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

	private void saveToken(String code)  {
		GoogleTokenResponse response;
		try {
			response = flow.newTokenRequest(code).setRedirectUri(CALLBACK_URI).execute();
			flow.createAndStoreCredential(response, USER_IDENTIFIER_KEY);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

  
  public Object create(String client ,String db,String name)  {
	  LocalDateTime myDateObj = LocalDateTime.now();
		
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		
		String formattedDate = myDateObj.format(myFormatObj);
		System.out.print(name);
		 
		Credential cred;
		FileList fileList;
		try {
			cred = flow.loadCredential(USER_IDENTIFIER_KEY);
			Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
					.setApplicationName("googledrivespringbootexample").build();
			fileList = drive.files().list().setFields("files(id,name,thumbnailLink)").execute();
	
		 
	
		for (File file2 : fileList.getFiles()) {
			if(file2.getName().equals(client+"-"+db)) {
	        	System.out.println(file2.getName());

				
				File file = new File();
				
				file.setName(client+"-BACKUP-"+db+formattedDate);
				file.setParents(Arrays.asList(file2.getId()));
				
				FileContent content = new FileContent("application/octet-stream", new java.io.File(name));
				File uploadedFile = drive.files().create(file, content).setFields("id").execute();
				String fileReference = String.format("{fileID: '%s'}", uploadedFile.getId());
	        	System.out.println("Yeah");
	        	HistoriqueEntity historique=new HistoriqueEntity(formattedDate,"file saved on drive succesfully");
				historiqueRepository.save(historique);
				return null;
			}
			
		}


		File file = new File();
		
		file.setName(client+"-BACKUP-"+db+name);
		file.setParents(Arrays.asList(createFolder(client,db,formattedDate)));
	System.out.print(name);
		FileContent content = new FileContent("application/octet-stream", new java.io.File(name));
		File uploadedFile = drive.files().create(file, content).setFields("id").execute();
		String fileReference = String.format("{fileID: '%s'}", uploadedFile.getId());
		System.out.println("Yeah");
    	HistoriqueEntity historique=new HistoriqueEntity(formattedDate,"file saved on drive succesfully");
		historiqueRepository.save(historique);
    	System.out.println("Yeah");

		return null;
		} catch (IOException e) {
			HistoriqueEntity historique=new HistoriqueEntity(formattedDate,"saving file on drive is failed",e.toString());
			//historiqueRepository.save(historique);
			e.printStackTrace();
			return null;
		}
  }
  
  public String createFolder(String client,String db,String formattedDate)   {
	  Credential cred;
	try {
		cred = flow.loadCredential(USER_IDENTIFIER_KEY);
		File file1;
		File file = new File();
		file.setName(client+"-"+db);
		file.setMimeType("application/vnd.google-apps.folder");
		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
				.setApplicationName("googledrivespringbootexample").build();
		file1 = drive.files().create(file).execute();
		return file1.getId();
	} catch (IOException e) {
		HistoriqueEntity historique=new HistoriqueEntity(formattedDate,"saving file on drive is failed",e.toString());
		historiqueRepository.save(historique);
		// TODO Auto-generated catch block
		//e.printStackTrace();
		return e.getMessage();
	}

		 
		

	
		
  }	
  
  
  //###############################backupMethode##############################################
 
   public  String backupPGSQL(String host,String user, String dbase,String password ){
	   String name=""; 
	   String rutaCT ="src\\main\\resources\\BAKCUPS\\";
	   try{
		    Runtime r =Runtime.getRuntime();
		    //Path to the place we store our backup
		    //PostgreSQL variables
		    Process p;
		    ProcessBuilder pb;
		    InputStreamReader reader;
		    BufferedReader buf_reader;
		    String line;
		    //We build a string with today's date (This will be the backup's filename)
		    java.util.TimeZone zonah = java.util.TimeZone.getTimeZone("GMT+1");
		    java.util.Calendar Calendario = java.util.GregorianCalendar.getInstance( zonah, new java.util.Locale("es"));
		    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyyMMdd");
		    StringBuffer date = new StringBuffer();
		    date.append(df.format(Calendario.getTime()));
		    java.io.File file = new java.io.File(rutaCT.toString());
		    // We test if the path to our programs exists
		    if (file.exists()) {
		      // We then test if the file we're going to generate exist too. If so we will delete it
		      StringBuffer fechafile = new StringBuffer();
		     
				fechafile.append(rutaCT);
			
		      fechafile.append(date.toString());
		      fechafile.append(".backup");
		      java.io.File ficherofile = new java.io.File(fechafile.toString());
		      if (ficherofile.exists()) {
		       // ficherofile.delete();
		    	  return null;
		      }
		      r = Runtime.getRuntime();
		      name=fechafile.toString();
				pb = new ProcessBuilder(rutaCT + "pg_dump.exe", "-f", fechafile.toString(),
				      "-F", "c", "-Z", "9", "-v", "-o", "-h",host, "-U", user, dbase);
			
		      pb.environment().put("PGPASSWORD", password);
		      pb.redirectErrorStream(true);
		      p = pb.start();
		      try {
		        InputStream is = p.getInputStream();
		        InputStreamReader isr = new InputStreamReader(is);
		        BufferedReader br = new BufferedReader(isr);
		        String ll;
		        while ((ll = br.readLine()) != null) {
		          System.out.println(ll);
		        }
		      } catch (IOException e) {
		       e.printStackTrace();
		      }
		    }
		  } catch(IOException x) {
		    System.err.println("Could not invoke browser, command=");
		    System.err.println("Caught: " + x.getMessage());
		  }
		return name;
		}
	
  //############################################

  //############################################

	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) { 


		cronExpressions=backupRepository.count();
	
		
		
		//for(int i=0;i<cron1.length;i++) {
		serv.findAll().forEach(server-> {
			server.getBackups().forEach(backup->{
				
			
			
			
			Runnable runnable = () ->{

				
			
       		
		String	name=backupPGSQL(server.getHost(),server.getUserName(),backup.getDataBaseName(),server.getPassword() );
       		
       		

       		 try {
       			
       			 if(backup.getStrategy().equals("1")&!(name==null)) {
       				// System.out.print(server.getUser().getFirstName());
       				create(server.getUser().getFirstName(),backup.getDataBaseName(),name);
       			 }
       			 if(backup.getStrategy().equals("2")&!(name==null)) {
       				 
       				 emailservice.sendEmailwithAttachment(backup.getEmailReceiver(),name ,  backup.getDataBaseName(), server.getUser().getFirstName());
       			 }
       		
       			 } catch (Exception e) {
       			 // TODO Auto-generated catch block
       			 e.printStackTrace();
       			 }
	        	
       		 
	        	};
	           
	        
			scheduledTaskRegistrar.addTriggerTask(runnable,
			triggerContext -> {
				Long newCronExpression ;

				 newCronExpression=backupRepository.count();
                 if (!(newCronExpression== cronExpressions)){

                	 scheduledTaskRegistrar.setTriggerTasksList(new ArrayList<TriggerTask>());

                 configureTasks(scheduledTaskRegistrar); // calling recursively.

                 scheduledTaskRegistrar.destroy(); // destroys previously scheduled tasks.
                 scheduledTaskRegistrar.setScheduler(executor);

                 scheduledTaskRegistrar.afterPropertiesSet(); // this will schedule the task with new cron changes.
                     return null; // return null when the cron changed so the trigger will stop.

                 }

              
				
		         return new CronTrigger(backup.getSchedule()).nextExecutionTime(triggerContext);

			});
			});
			
		});
		
		
// Split the cronExpression with pipe and for each expression add the same task.
		
		
		

	   
	
	
	}
}
