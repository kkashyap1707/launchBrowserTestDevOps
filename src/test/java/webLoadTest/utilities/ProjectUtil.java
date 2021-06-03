package webLoadTest.utilities;

import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import webLoadTest.test.TestBaseBrowser;


public class ProjectUtil extends TestBaseBrowser {

    public static Response getLoginResponse,postLoginResponse,getChatBotSessionResponse;

    public static String inputGenerator[];

    public static void addJobToSpreadsheet1(String[] inputGenerator) throws Exception{
        BrowserUtil.openChromeBrowser();
        Integer activeRowsCount = Quickstart.getActiveRowCount(GlobalVars.SPREADSHEET_ID,"good!A2:A");
        Integer numberOfLine = activeRowsCount+1;
        Quickstart.AddLineSpreadSheet(GlobalVars.SPREADSHEET_ID,inputGenerator,numberOfLine);
        Integer latestRowCount = Quickstart.getActiveRowCount(GlobalVars.SPREADSHEET_ID,"good!A2:E");
        System.out.println("Latest row count after new line added :: "+latestRowCount);
        Reporter.test.log(Status.INFO,"Latest row count after new line added :: "+latestRowCount);
        BrowserUtil.closeBrowser();
    }

    public static void addJobToSpreadsheet() throws Exception{
        BrowserUtil.openChromeBrowser();
        Integer activeRowsCount = Quickstart.getActiveRowCount(GlobalVars.SPREADSHEET_ID,"good!A2:A");
        Integer numberOfLine = activeRowsCount+1;
        Quickstart.AddLineSpreadSheet(GlobalVars.SPREADSHEET_ID,inputGenerator,numberOfLine);
        Integer latestRowCount = Quickstart.getActiveRowCount(GlobalVars.SPREADSHEET_ID,"good!A2:E");
        System.out.println("Latest row count after new line added :: "+latestRowCount);
        Reporter.test.log(Status.INFO,"Latest row count after new line added :: "+latestRowCount);
        BrowserUtil.closeBrowser();
    }

    public static void addJobToSpreadsheetBlankEmailID() throws Exception{
        BrowserUtil.openChromeBrowser();
        Integer activeRowsCount = Quickstart.getActiveRowCount(GlobalVars.SPREADSHEET_ID,"good!A2:A");
        Integer numberOfLine = activeRowsCount+1;
        Quickstart.AddLineSpreadSheet(GlobalVars.SPREADSHEET_ID,inputGenerator2,numberOfLine);
        Integer latestRowCount = Quickstart.getActiveRowCount(GlobalVars.SPREADSHEET_ID,"good!A2:E");
        System.out.println("Latest row count after new line added :: "+latestRowCount);
        Reporter.test.log(Status.INFO,"Latest row count after new line added :: "+latestRowCount);
        BrowserUtil.closeBrowser();
    }

    public static void deleteJobToSpreadsheet1() throws Exception{
        BrowserUtil.openChromeBrowser();
        Integer latestRowCount = Quickstart.getActiveRowCount(GlobalVars.SPREADSHEET_ID,"good!A2:E");
        System.out.println("Latest row count before line deleted :: "+latestRowCount);
        Quickstart.deleteRow(GlobalVars.SPREADSHEET_ID,latestRowCount-1,latestRowCount);

        Integer latestRowCountAfterDelete = Quickstart.getActiveRowCount(GlobalVars.SPREADSHEET_ID,"good!A2:E");
        System.out.println("Latest row count after line deleted :: "+latestRowCountAfterDelete);

        Reporter.test.log(Status.INFO,"Latest row count after line deleted :: "+latestRowCountAfterDelete);
        BrowserUtil.closeBrowser();
    }

    public static void deleteJobToSpreadsheet() throws Exception{
        BrowserUtil.openChromeBrowser();
        Integer latestRowCount = Quickstart.getActiveRowCount(GlobalVars.SPREADSHEET_ID,"good!A2:E");

        Integer latestRowCountMinus1 = latestRowCount-1;
        Integer latestRowCountPlus1 = latestRowCount+1;

        System.out.println("Latest row count before line deleted :: "+latestRowCount);
        System.out.println("Plus 1 :: "+latestRowCountPlus1);
        System.out.println("Minus 1 :: "+latestRowCountMinus1);

        Quickstart.deleteRow(GlobalVars.SPREADSHEET_ID,latestRowCount,latestRowCount+1);

        Integer latestRowCountAfterDelete = Quickstart.getActiveRowCount(GlobalVars.SPREADSHEET_ID,"good!A2:E");
        System.out.println("Latest row count after line deleted :: "+latestRowCountAfterDelete);

        Reporter.test.log(Status.INFO,"Latest row count after line deleted :: "+latestRowCountAfterDelete);
        BrowserUtil.closeBrowser();
    }

    public static String downloadSpreadsheet() throws Exception{
        //BrowserUtil.getFileName();
        //BrowserUtil.delete("/downloads/");
        System.out.println("CSV File path :: "+DriveUtil.csvFilePath);
        DriveUtil.downloadSpreadSheet(DriveUtil.csvFilePath,"text/csv");
        String fileDownloadFolder = BrowserUtil.getFileName1();

        return fileDownloadFolder;
    }

    public static String downloadSpreadsheet1() throws Exception{
        //BrowserUtil.getFileName();
        //BrowserUtil.delete("/downloads/");
        System.out.println("CSV File path :: "+System.getProperty("user.dir")+"/downloads/"+GlobalVars.SHEET_NAME);
        DriveUtil.downloadSpreadSheet(DriveUtil.csvFilePath,"text/csv");
        String fileDownloadFolder = BrowserUtil.getFileName1();

        return fileDownloadFolder;
    }

}
