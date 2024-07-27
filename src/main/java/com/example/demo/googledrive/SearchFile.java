package com.example.demo.googledrive;

import com.example.demo.utility.StrConst;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* Class to demonstrate use-case of search files. */
public class SearchFile {

    public static final Logger logger = LoggerFactory.getLogger(SearchFile.class);

    /**
     * Search for specific set of files.
     *
     * @return search result list.
     * @throws IOException              if service account credentials file not found.
     * @throws GeneralSecurityException
     */
    public static DataContainer searchFile(String type, DataContainer dataContainer) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(StrConst.CREDENTIALS_FILE_PATH)).createDelegated(StrConst.tcdEmail)
                .createScoped(StrConst.SCOPES);
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
                credentials);

        // Build a new authorized API client service.
        Drive service = new Drive.Builder(HTTP_TRANSPORT, StrConst.JSON_FACTORY, requestInitializer)
                .setApplicationName(StrConst.APPLICATION_NAME)
                .build();
        //1PC6C71heeOwrKJInUKhCzU8R5dg4quB1
        if (type.equalsIgnoreCase(StrConst.gDrivefilesearch)) {
            Stream<File> ss = getFilesInFolder(service, dataContainer.getFileId());
            dataContainer.setObjArr(ss.toArray());
        } else if (type.equalsIgnoreCase(StrConst.gDriveGetfile)) {
            dataContainer.setByteArr(downloadfile(service, dataContainer.getFileId()));
        }else if (type.equalsIgnoreCase(StrConst.gDriveDelfile)){
            deletefile(service,dataContainer.getFileId());
        }

        return dataContainer;
    }


    private static byte[] downloadfile(final Drive service, String fileId) throws IOException {
        OutputStream outputStream = new ByteArrayOutputStream();

        service.files().get(fileId)
                .executeMediaAndDownloadTo(outputStream);

        ByteArrayOutputStream data = (ByteArrayOutputStream) outputStream;

        return data.toByteArray();


    }

    private static Stream<File> getFilesInFolder(final Drive driveService, final String parentId) throws IOException {
        logger.debug("Listing files in folder {}.", parentId);
        return driveService.files().list()
                .setQ("'" + parentId + "' in parents and trashed = false")
                .setFields("nextPageToken, files(id, name)").setPageSize(200)
                .execute()
                .getFiles()
                .stream()
                .peek(f -> logger.debug("Found '{}' ({}) as {}.", f.getName(), f.getMimeType(), f.getId()));

    }


    private static File searchFile(final Drive driveService, String fileName, String parentId) throws Exception {
        File file = null;
        StringBuffer fileQuery = new StringBuffer();
        fileQuery.append("name = '" + fileName + "'");
        if (parentId != null) {
            fileQuery.append(" and '" + parentId + "' in parents and trashed=false");
        }
        FileList fileList = driveService.files().list().setQ(fileQuery.toString()).execute();
        if (!fileList.getFiles().isEmpty()) {
            file = fileList.getFiles().get(0);
        }
        return file;
    }

    private static void deletefile(final Drive service, String fileId) throws IOException {
//service.files().delete(fileId).execute();
        System.out.println(" File id  ::::: "+service.files().get(fileId));
        service.files().delete(fileId).execute();
    }

    public static void main(String[] args) {

        try {
            DataContainer data = new DataContainer();
            data.setFileId("1rC5CE_rl7mi9CpCWHlwcbmek1_jrIpMh");
            data = searchFile(StrConst.gDrivefilesearch, data);
           List<Map<String, String> > fileNameId = new ArrayList<>();
            for (Object obj : data.getObjArr()){
               while(true) {
                   Map<String, String> objData = convertJsonTomap(obj.toString());
                   System.out.println(objData.toString());
                   if(isFile(objData.get("name"))){
                       fileNameId.add(objData);
                       break;
                   }else{
                       DataContainer data2 = new DataContainer();
                       data2.setFileId(objData.get("id"));
                       data2 = searchFile(StrConst.gDrivefilesearch, data);
                   }
               }

            }

            System.out.println("--->>>" + data.getObjArr());
        } catch (IOException | GeneralSecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
private static boolean isFile(String fname){
      if(fname.contains(".JPG") || fname.contains(".jpg") || fname.contains(".PNG") || fname.contains(".png"))  {
          return true;
      }
      return false;
}

    private static Map<String, String> convertJsonTomap(String jsonStr) {
        //String jsonString = "Your JSON string";
        HashMap<String, String> map = new Gson().fromJson(jsonStr, new TypeToken<HashMap<String, String>>() {
        }.getType());

        return map;
    }
}