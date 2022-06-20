package net.javaguides.springboot.controller;



import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.services.CommonGoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
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
import com.google.api.services.drive.model.Permission;
import net.javaguides.springboot.model.FileItemDTO;
/**
 * @author Yogesh Jadhav
 *
 */
@CrossOrigin(origins = "*")

@Controller
public class HomepageController {

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
/*	if (credential != null) {
			boolean tokenValid = credential.refreshToken();
			if (tokenValid) {
				isUserAuthenticated = true;
			}
		}*/

		return isUserAuthenticated ? "dashboard.html" : "index.html";
	}

	@GetMapping(value = { "/googlesignin" })
	public void doGoogleSignIn(HttpServletResponse response) throws Exception {
		GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
		String redirectURL = url.setRedirectUri(CALLBACK_URI).setAccessType("offline").build();
		response.sendRedirect(redirectURL);
	}

	@GetMapping(value = { "/oauth" })
	public  String saveAuthorizationCode(HttpServletRequest request) throws Exception {
		String code = request.getParameter("code");
	if (code != null) {
			saveToken(code);
			/*URI yahoo = new URI("http://localhost:4200");
		    HttpHeaders httpHeaders = new HttpHeaders();
		    httpHeaders.setLocation(yahoo);
		    return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);*/

		return "dashboard.html";
		}

	/*URI yahoo = new URI("http://localhost:4200");
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(yahoo);
    return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);*/
	return "index.html";
	}

	private void saveToken(String code) throws Exception {
		GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(CALLBACK_URI).execute();
		flow.createAndStoreCredential(response, USER_IDENTIFIER_KEY);

	}

	@GetMapping(value = { "/create" })
	public void createFile(HttpServletResponse response) throws Exception {
		Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);

		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
				.setApplicationName("googledrivespringbootexample").build();

		File file = new File();
		file.setName("profile.jpg");

		FileContent content = new FileContent("image/jpeg", new java.io.File("D:\\practice\\sbtgd\\sample.jpg"));
		File uploadedFile = drive.files().create(file, content).setFields("id").execute();

		String fileReference = String.format("{fileID: '%s'}", uploadedFile.getId());
		response.getWriter().write(fileReference);
	}

	@GetMapping(value = { "/uploadinfolder" })
	public void uploadFileInFolder(HttpServletResponse response) throws Exception {
		Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);

		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
				.setApplicationName("googledrivespringbootexample").build();

		File file = new File();
		file.setName("digit.jpg");
		file.setParents(Arrays.asList("1_TsS7arQRBMY2t4NYKNdxta8Ty9r6wva"));

		FileContent content = new FileContent("image/jpeg", new java.io.File("D:\\practice\\sbtgd\\digit.jpg"));
		File uploadedFile = drive.files().create(file, content).setFields("id").execute();

		String fileReference = String.format("{fileID: '%s'}", uploadedFile.getId());
		response.getWriter().write(fileReference);
	}
//###########################
	public void downloadFile(String fileId, OutputStream outputStream) {
        if (fileId != null) {
            try {
            	Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);
    			
    			Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
    					.setApplicationName("googledrivespringbootexample").build();
                drive.files()
                    .get(fileId).executeMediaAndDownloadTo(outputStream);
               
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	
	@GetMapping("/download1/{fileId}")
    public void download(@PathVariable String fileId, HttpServletResponse response) {
		
        try {
        	Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);
			
			Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
					.setApplicationName("googledrivespringbootexample").build();
			String mimeType = drive.files().get(fileId).execute().getMimeType();
			response.setContentType(mimeType);
            downloadFile(fileId, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	
	
	
	
	
	
	
	@PostMapping(value = { "/download/{id}" })
	private  FileOutputStream DownloadFile(@PathVariable(value = "id") String id) {
		try {
			Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);
			
			Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
					.setApplicationName("googledrivespringbootexample").build();
			 
			
			
			Drive.Files.Get request = drive.files().get(id);
		
			HttpResponse progress = request.executeMedia();
			
			if (progress != null) {
				InputStream stream = progress.getContent();
				
				FileOutputStream file = new FileOutputStream(("d:\\"+drive.files().get(id).execute().getName()));
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
		return null;
	}

	
	

	
	
//#######################################
	@GetMapping(value = { "/listfolders" }, produces = { "application/json" })
	public @ResponseBody List<File> listFolders() throws Exception {
		Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);

		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
				.setApplicationName("googledrivespringbootexample").build();
		
		String pageToken = null;
		// list of folder in the "my-drive" or home page of the drive 
		  FileList result = drive.files().list()
				  .setQ("'root' in parents and mimeType='application/vnd.google-apps.folder'")
		      .setSpaces("drive")
		      .setFields("nextPageToken, files(id, name)")
		      .setPageToken(pageToken)
		      .execute();
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  System.out.println("yeah"+auth.getName());
		 
		  return result.getFiles();
//********************************* how I can get the time ******************************/
// https://developers.google.com/drive/api/v3/reference/files#properties

	}
	@GetMapping(value = { "/listfiles/{id}" }, produces = { "application/json" })
	public@ResponseBody List<File> listFiles(@PathVariable(value = "id") String id) throws Exception {
		
		Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);

		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
				.setApplicationName("googledrivespringbootexample").build();
		
		
		String pageToken = null;
		  FileList result = drive.files().list()
				  .setQ("'"+id+"' in parents ")
		      .setSpaces("drive")
		      .setFields("nextPageToken, files(id, name,size,createdTime)")
		      .setPageToken(pageToken)
		      .execute();
		  
		 
		  return result.getFiles();
		
	}

	@PostMapping(value = { "/makepublic/{fileId}" }, produces = { "application/json" })
	public @ResponseBody Message makePublic(@PathVariable(name = "fileId") String fileId) throws Exception {
		Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);

		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
				.setApplicationName("googledrivespringbootexample").build();

		Permission permission = new Permission();
		permission.setType("anyone");
		permission.setRole("reader");

		drive.permissions().create(fileId, permission).execute();

		Message message = new Message();
		message.setMessage("Permission has been successfully granted.");
		return message;
	}

	@DeleteMapping(value = { "/deletefile/{fileId}" }, produces = "application/json")
	public @ResponseBody Message deleteFile(@PathVariable(name = "fileId") String fileId) throws Exception {
		Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);

		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
				.setApplicationName("googledrivespringbootexample").build();

		drive.files().delete(fileId).execute();

		Message message = new Message();
		message.setMessage("File has been deleted.");
		return message;
	}

	@GetMapping(value = { "/createfolder/{folderName}" }, produces = "application/json")
	public @ResponseBody Message createFolder(@PathVariable(name = "folderName") String folder) throws Exception {
		Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);

		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
				.setApplicationName("googledrivespringbootexample").build();

		File file = new File();
		file.setName(folder);
		file.setMimeType("application/vnd.google-apps.folder");

		drive.files().create(file).execute();

		Message message = new Message();
		message.setMessage("Folder has been created successfully.");
		return message;
	}
	
	@GetMapping(value = { "/servicelistfiles" }, produces = { "application/json" })
	public @ResponseBody List<FileItemDTO> listFilesInServiceAccount() throws Exception {
		Credential cred = GoogleCredential.fromStream(serviceAccountKey.getInputStream());
		
		GoogleClientRequestInitializer keyInitializer = new CommonGoogleClientRequestInitializer();

		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, null).setHttpRequestInitializer(cred)
				.setGoogleClientRequestInitializer(keyInitializer).build();

		List<FileItemDTO> responseList = new ArrayList<>();

		FileList fileList = drive.files().list().setFields("files(id,name,thumbnailLink)").execute();
		for (File file : fileList.getFiles()) {
			FileItemDTO item = new FileItemDTO();
			item.setId(file.getId());
			item.setName(file.getName());
			item.setThumbnailLink(file.getThumbnailLink());
			responseList.add(item);
		}

		return responseList;
	}
	

	class Message {
		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}

}