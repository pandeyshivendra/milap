package com.example.demo.googledrive;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.example.demo.utility.StrConst;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.ServiceAccountCredentials;

public class UploadFile {


	private static final String APPLICATION_NAME = "clickpic";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);
	private static final String CREDENTIALS_FILE_PATH = "clickpic-399304-f575789ef36c.json";


	public static String uploadFile(String folderId,String fileName) throws IOException, GeneralSecurityException {


		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(ServiceAccountCredentials.fromStream(new FileInputStream(CREDENTIALS_FILE_PATH))
                .createScoped(SCOPES)
                .createDelegated("deepak.dash@tcdtech.org"));
	 Drive driveService = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, requestInitializer)
                .setApplicationName(APPLICATION_NAME)
                .build();
    File fileMetadata = new File();
    fileMetadata.setName(fileName);
    fileMetadata.setParents(Collections.singletonList(folderId));
    java.io.File filePath = new java.io.File(StrConst.ec2_img_path+"7895842963_user.jpg");
    FileContent mediaContent = new FileContent("image/jpeg", filePath);
   
    File file = driveService.files().create(fileMetadata, mediaContent)
    		.setSupportsTeamDrives(true)
            .setFields("id , name").execute();
    System.out.println("File ID: " + file.getId());
	    return file.getId();
	}
	
	public static void main(String[] args) throws GeneralSecurityException, IOException {
		
	   
	    
//	    FileList result = driveService.files().list() //
//                .setDriveId("0AKy-UiKnFfeUUk9PVA")
//                .setCorpora("drive")
//                .setSupportsAllDrives(true)
//                .setIncludeItemsFromAllDrives(true)
//                .setPageSize(10)
//                .setFields("nextPageToken, files(id, name)")
//                .execute();
//        List<File> files = result.getFiles();
//        String getYourEventFolderId=null;
//	    for( File data:result.getFiles()) {
//	    	if(data.getName().equalsIgnoreCase("test-event-2")) {
//	    		getYourEventFolderId=data.getId();
//	    	}
//	    }
//            System.out.println(getYourEventFolderId);
//            System.out.println(result.getFiles().toString());
            
		 final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(ServiceAccountCredentials.fromStream(new FileInputStream(CREDENTIALS_FILE_PATH))
	                .createScoped(SCOPES)
	                .createDelegated("deepak.dash@tcdtech.org"));
		 Drive driveService = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, requestInitializer)
	                .setApplicationName(APPLICATION_NAME)
	                .build();
            File fileMetadata = new File();
    	    fileMetadata.setName("photorohit.jpg");
    	    fileMetadata.setParents(Collections.singletonList("1pVYLjN0_JzgC5H_XvBrkvAfiqH604rAe"));
    	    java.io.File filePath = new java.io.File(StrConst.ec2_img_path+"big.jpg");
    	    FileContent mediaContent = new FileContent("image/jpeg", filePath);
    	   
    	    File file = driveService.files().create(fileMetadata, mediaContent)
    	    		.setSupportsTeamDrives(true)
                    .setFields("id , name").execute();
    	    System.out.println("File ID: " + file.getId());
	    
	}
	
}

