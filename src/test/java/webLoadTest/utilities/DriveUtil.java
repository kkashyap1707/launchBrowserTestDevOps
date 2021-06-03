package webLoadTest.utilities;

import com.aventstack.extentreports.Status;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import webLoadTest.test.TestBaseBrowser;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class DriveUtil extends TestBaseBrowser {


    public static Drive service ;
    public static String fileId = GlobalVars.SPREADSHEET_ID;
    public static String csvFilePath = System.getProperty("user.dir")+"/downloads/"+GlobalVars.SHEET_NAME;

    //public static String csvFilePath = System.getProperty("user.dir")+"/downloads/";



    public static final String APPLICATION_NAME = "Google Drive API Java Quickstart";

    public static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.dir"), "/drive/.credentials/sheets.googleapis.com-java-quickstart");

    public static FileDataStoreFactory DATA_STORE_FACTORY;

    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static HttpTransport HTTP_TRANSPORT;

    public static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_READONLY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public static Credential authorize() throws IOException {
        InputStream in = Quickstart.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        Reporter.test.log(Status.INFO,"Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    public static Drive getDriveService() throws IOException {
        Credential credential = authorize();

        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
    }

    public static String uploadFilesToDrive() throws Exception{
        Drive driveService = getDriveService();

        File fileMetadata = new File();
        fileMetadata.setName("photo.jpg");
        java.io.File filePath = new java.io.File("files/photo.jpg");
        FileContent mediaContent = new FileContent("image/jpeg", filePath);
        File file = driveService.files().create(fileMetadata, mediaContent).setFields("id").execute();
        String uploadFileID = file.getId();
        System.out.println("File ID: " + file.getId());

        return uploadFileID;
    }

    public static void downloadSpreadSheet(String fileDownloadPath,String mimeType) throws Exception{

        service = DriveUtil.getDriveService();
        System.out.println("Path is :: "+fileDownloadPath);

        OutputStream outputStream = new FileOutputStream(fileDownloadPath);
        service.files().export(DriveUtil.fileId, mimeType).executeMediaAndDownloadTo(outputStream);
        Reporter.test.log(Status.INFO,"CSV File downloaded successfully.");

    }



}
