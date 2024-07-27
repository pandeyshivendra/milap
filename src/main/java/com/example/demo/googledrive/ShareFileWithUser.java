package com.example.demo.googledrive;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.Permission;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShareFileWithUser {
    private static final String APPLICATION_NAME = "clickpic";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "clickpic-399304-f575789ef36c.json";
    private static final Logger logger = LoggerFactory.getLogger(SearchFile.class);


    public static void main(String[] args) throws GeneralSecurityException, IOException {

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(CREDENTIALS_FILE_PATH)).createDelegated("deepak.dash@tcdtech.org")
                .createScoped(SCOPES);
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
                credentials);

        // Build a new authorized API client service.
        Drive driveService = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, requestInitializer)
                .setApplicationName(APPLICATION_NAME)
                .build();


        // Specify the file or folder ID you want to share
        String fileId = "1T_hVEAva5NdMh4x0mXjjN886qYdsYzJn";

        // Specify the email address to grant access to
        String emailAddress = "dash.deepak156@gmail.com";

        // Create a new permission for the user
        Permission userPermission = new Permission()
                .setType("user")
                .setRole("writer")// Change the role as needed (e.g., "writer", "owner")
                .setEmailAddress(emailAddress);

        // Insert the permission
        driveService.permissions().create(fileId, userPermission).execute();

        System.out.println("Access granted to " + emailAddress);
    }
}
