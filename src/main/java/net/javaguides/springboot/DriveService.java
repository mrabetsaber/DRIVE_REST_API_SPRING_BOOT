package net.javaguides.springboot;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;



import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Value;
import com.google.api.services.drive.DriveScopes;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.FileContent;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class DriveService {
	
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

	
	public void init() throws Exception {
		
		GoogleClientSecrets secrets = GoogleClientSecrets.load(JSON_FACTORY,
				new InputStreamReader(gdSecretKeys.getInputStream()));
		flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, secrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(credentialsFolder.getFile())).build();
		
	}

	



	

  
  public void create(String client ,String db,String url) throws Exception {
	  init();
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

				
			}
			
		}


		File file = new File();
		
		file.setName(client+"-BACKUP-"+db);
		file.setParents(Arrays.asList(createFolder(client,db)));
		
		FileContent content = new FileContent("application/octet-stream", new java.io.File(url));
		File uploadedFile = drive.files().create(file, content).setFields("id").execute();
		String fileReference = String.format("{fileID: '%s'}", uploadedFile.getId());
    	System.out.println("Yeah");

		
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
	
 

}
