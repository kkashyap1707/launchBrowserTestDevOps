package webLoadTest.utilities;

import com.aventstack.extentreports.Status;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Quickstart {

    public static String currentDirectory = System.getProperty("user.dir");

    private String detailReportName = "";

    /** webLoadTest.model.Application name. */
    private static final String APPLICATION_NAME ="Google Sheets API Java Quickstart";

    /** Directory to store user credentials for this application. */
    //private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart");

    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.dir"), "/sheet/.credentials/sheets.googleapis.com-java-quickstart");


    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart
     */
    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS);

    //private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = Quickstart.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Sheets API client service.
     * @return an authorized Sheets API client service
     * @throws IOException
     */

    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }



    ///////////////////////////////////////////////////


    public static ValueRange getRowCount(String spreadsheetId, String range) throws IOException {
        Sheets service = Quickstart.getSheetsService();
        // [START sheets_get_values]


        ValueRange result = service.spreadsheets().values().get(spreadsheetId, range).execute();
        int numRows = result.getValues() != null ? result.getValues().size() : 0;
        Integer a = result.getValues().size();

        System.out.println("HOLA"+ a);

        System.out.printf(" rows retrieved.Count :: ", numRows);

        // [END sheets_get_values]
        return result;
    }

    public static Integer getActiveRowCount(String spreadsheetId, String range) throws IOException {
        Sheets service = Quickstart.getSheetsService();

        ValueRange result = service.spreadsheets().values().get(spreadsheetId, range).execute();
        int numRows = result.getValues() != null ? result.getValues().size() : 0;
        Integer a = result.getValues().size();

        System.out.println("HOLA"+ a);

        return a;
    }

    public static ValueRange getValuesForSpecificRow(String spreadsheetId, String range) throws IOException {
        Sheets service = Quickstart.getSheetsService();
        // [START sheets_get_values]
        ValueRange result = service.spreadsheets().values().get(spreadsheetId, range).execute();
        /*int numRows = result.getValues() != null ? result.getValues().size() : 0;
        System.out.printf("%d rows retrieved.", numRows);*/

        //System.out.println(">>>>>>"+result.getValues().get(0));

        // [END sheets_get_values]
        return result;
    }

    public static void readCellValue(String spreadsheetId, String range) throws Exception{
        Sheets service = Quickstart.getSheetsService();

        Sheets.Spreadsheets.Get s = service.spreadsheets().get(spreadsheetId);

        System.out.println(">>>>>>>>>>>>>>"+s);




        ValueRange result = service.spreadsheets().values().get(spreadsheetId, range).execute();
        int numRows = result.getValues() != null ? result.getValues().size() : 0;
        Integer a = result.getValues().size();

        System.out.println(a);


    }

    public static void getColumnData(String spreadsheetId, String range) throws IOException {
        Sheets service = Quickstart.getSheetsService();

        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();

        int numRows = response.getValues() != null ? response.getValues().size() : 0;
        System.out.printf("%d rows retrieved.", numRows);

        List<List<Object>> values = response.getValues();
        if (values == null || values.size() == 0) {
            System.out.println("No data found." + values);
        } else {
            System.out.println(values);
            for (List row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                System.out.printf("%s, %s\n", row.get(1), row.get(3));
            }
        }
    }

    public static String create(String title) throws IOException {
        Sheets service =  Quickstart.getSheetsService();
        // [START sheets_create]
        Spreadsheet spreadsheet = new Spreadsheet()
                .setProperties(new SpreadsheetProperties()
                        .setTitle(title));
        spreadsheet = service.spreadsheets().create(spreadsheet)
                .setFields("spreadsheetId")
                .execute();
        System.out.println("Spreadsheet ID: " + spreadsheet.getSpreadsheetId());
        // [END sheets_create]
        return spreadsheet.getSpreadsheetId();
    }

    public static void AddLineSpreadSheet(String spreadsheetId, String[] line, Integer numberOfLine) throws Exception {

        Sheets service =  Quickstart.getSheetsService();

        List<Request> requests = new ArrayList<>();

        List<CellData> values = new ArrayList<>();

        for (int i=0;i<line.length;i++)
        {

            values.add(new CellData()
                    .setUserEnteredValue(new ExtendedValue()
                            .setStringValue(line[i].toString())
                    ));
        }

        requests.add(new Request().setUpdateCells(new UpdateCellsRequest().setStart(new GridCoordinate()
                                .setSheetId(0)
                                .setRowIndex(numberOfLine)
                                .setColumnIndex(0))
                                .setRows(Arrays.asList(new RowData().setValues(values)))
                                .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));

        System.out.println(">>>>>>>>>>> Line added successfully.");

        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest().setRequests(requests);
        service.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();

        System.out.println("Line added");
        Reporter.test.log(Status.INFO,"New Job Req Line added successfully.");
    }

    public static void findAndReplace(String spreadsheetId, String find, String replacement) throws IOException, ServiceException {

        Sheets service = Quickstart.getSheetsService();

        List<Request> requests = new ArrayList<>();

        // Find and replace text.
        requests.add(new Request().setFindReplace(new FindReplaceRequest().setFind(find).setReplacement(replacement).setAllSheets(true)));

        BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);
        BatchUpdateSpreadsheetResponse response = service.spreadsheets().batchUpdate(spreadsheetId, body).execute();

        FindReplaceResponse findReplaceResponse = response.getReplies().get(0).getFindReplace();
        System.out.printf("%d replacements made.", findReplaceResponse.getOccurrencesChanged());

        Integer count = response.getReplies().size();

        System.out.println(count);

    }

    public static void deleteRow(String spreadsheetId,Integer StartIndex, Integer EndIndex) throws IOException {

        Sheets service =  Quickstart.getSheetsService();

        Spreadsheet spreadsheet = null;

        try {
            spreadsheet = service.spreadsheets().get(spreadsheetId).execute();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        BatchUpdateSpreadsheetRequest content = new BatchUpdateSpreadsheetRequest();
        Request request = new Request();
        DeleteDimensionRequest deleteDimensionRequest = new DeleteDimensionRequest();
        DimensionRange dimensionRange = new DimensionRange();
        dimensionRange.setDimension("ROWS");
        dimensionRange.setStartIndex(StartIndex);
        dimensionRange.setEndIndex(EndIndex);

        dimensionRange.setSheetId(spreadsheet.getSheets().get(0).getProperties().getSheetId());
        deleteDimensionRequest.setRange(dimensionRange);

        request.setDeleteDimension(deleteDimensionRequest);

        List<Request> requests = new ArrayList<Request>();
        requests.add(request);
        content.setRequests(requests);

        try {
            service.spreadsheets().batchUpdate(spreadsheetId, content).execute();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            dimensionRange = null;
            deleteDimensionRequest = null;
            request = null;
            requests = null;
            content = null;
        }
    }

    public static void printRow(String spreadsheetId,Integer StartIndex, Integer EndIndex) throws IOException {

        Sheets service =  Quickstart.getSheetsService();

        Spreadsheet spreadsheet = null;

        try {
            spreadsheet = service.spreadsheets().get(spreadsheetId).execute();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        BatchUpdateSpreadsheetRequest content = new BatchUpdateSpreadsheetRequest();
        Request request = new Request();
        DeleteDimensionRequest deleteDimensionRequest = new DeleteDimensionRequest();
        DimensionRange dimensionRange = new DimensionRange();
        dimensionRange.setDimension("ROWS");
        dimensionRange.setStartIndex(StartIndex);
        dimensionRange.setEndIndex(EndIndex);

        dimensionRange.setSheetId(spreadsheet.getSheets().get(0).getProperties().getSheetId());
        deleteDimensionRequest.setRange(dimensionRange);

        System.out.print(request);


        request.setDeleteDimension(deleteDimensionRequest);

        List<Request> requests = new ArrayList<Request>();
        requests.add(request);
        content.setRequests(requests);

        try {
            service.spreadsheets().batchUpdate(spreadsheetId, content).execute();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            dimensionRange = null;
            deleteDimensionRequest = null;
            request = null;
            requests = null;
            content = null;
        }


    }




}
