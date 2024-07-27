package com.example.demo.googledrive;

import com.example.demo.utility.StrConst;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* Class to demonstrate use of Drive's create folder API */

public class CreateFolder {
    private static final String APPLICATION_NAME = "clickpic";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "clickpic-399304-f575789ef36c.json";

    /**
     * Create new folder.
     *
     * @return Inserted folder id if successful, {@code null} otherwise.
     * @throws IOException              if service account credentials file not found.
     * @throws GeneralSecurityException
     */
    public static String createFolder(String eventName) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(ServiceAccountCredentials.fromStream(new FileInputStream(CREDENTIALS_FILE_PATH))
                .createScoped(SCOPES).createDelegated("deepak.dash@tcdtech.org"));
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, requestInitializer)
                .setApplicationName(APPLICATION_NAME)
                .build();


        // File's metadata.
        File fileMetadata = new File();
        fileMetadata.setName(eventName);
        fileMetadata.setParents(Collections.singletonList(StrConst.gDriveEventFolderId));
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        try {
            //File file = service.files().create(fileMetadata).setSupportsTeamDrives(true).setFields("id").execute();
            File file = service.files().create(fileMetadata)
                    .setSupportsTeamDrives(true)
                    .setFields("id , name").execute();
            System.out.println("Folder ID: " + file.getId());
            return file.getId();


        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            System.err.println("Unable to create folder: " + e.getDetails());
            throw e;
        }
    }


    public static void main(String[] args) {
        try {

            createFolder("testEvent");

        } catch (GeneralSecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
