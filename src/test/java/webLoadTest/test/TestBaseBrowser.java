package webLoadTest.test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.http.Cookies;
import org.jsoup.nodes.Document;
import org.testng.ITestResult;
import org.testng.annotations.*;
import webLoadTest.utilities.*;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static webLoadTest.utilities.Reporter.extent;

public class TestBaseBrowser {

    public static String env = System.getProperty("env");
    public static String ver = System.getProperty("ver");
    public static String retrievalID = System.getProperty("retrievalID");

    public static Document document;
    public static Cookies getLoginCookies , postLoginCookies;
    public static String csrfMiddleWareToken;

    public static String inputGenerator11[];
    public static String inputGenerator12[];
    public static String inputGenerator13[];
    public static String inputGenerator14[];
    public static String inputGenerator15[];
    public static String inputGenerator16[];
    public static String inputGenerator17[];

    public static String inputGenerator18[];

    public static String inputGeneratorBlankEmailID[];

    public static String inputGenerator[];
    public static String inputGenerator2[];

    public static String inputGeneratorEmail1[];
    public static String inputGeneratorEmail2[];
    public static String inputGeneratorEmail3[];
    public static String inputGeneratorEmail4[];
    public static String inputGeneratorEmail5[];
    public static String inputGeneratorEmail6[];

    public static Connection connection;

    @BeforeSuite(alwaysRun=true)
    public void beforeSuite(){
        Keywords.loadSheetValue();
        System.out.println(GlobalVars.SystemId);
        Keywords.setURL(env,ver);
    }

    @BeforeTest(alwaysRun=true)
    public void beforeTest(){

        //Reporter.setReportName("/test-output/ExtentReport.html");
        Reporter.setReportName("/ExtentReport.html");
        Reporter.createObjectExtentReport();
        Reporter.setReportConfig();
        Reporter.setSystemInfoOfReport();
        //inputGenerator = InputGenerator.inputValue1;
        //inputGenerator2 = InputGenerator2.inputValueBlankEmail;

    }

    @BeforeClass
    public static void setUp() {
        /*
        String databaseURL = GlobalVars.DATABASE_URL;
        String user = GlobalVars.DATABASE_USERNAME;
        String password = GlobalVars.DATABASE_PASSWORD;
        connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to Read Database...");
            connection = DriverManager.getConnection(databaseURL, user, password);
            if (connection != null) {
                System.out.println("Connected to the Read Database...");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        */
    }

    @AfterClass
    public static void tearDown() {
       /*
        if (connection != null) {
            try {
                System.out.println("Closing Read Database Connection...");
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        */
    }


    @BeforeMethod(alwaysRun=true)
    public void beforeMethod(Method method){

        //inputGenerator = InputGenerator.inputValue1;
        //inputGenerator2 = InputGenerator2.inputValueBlankEmail;

        Reporter.test = extent.createTest(Feature.class, method.getName()).assignCategory("", this.getClass().getSimpleName());
    }

    @AfterMethod(alwaysRun=true)
    public void getResult(ITestResult result) throws Exception
    {
        if(result.getStatus() == ITestResult.FAILURE) {
            Reporter.test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            Reporter.test.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
        }
        else if(result.getStatus() == ITestResult.SKIP){
            Reporter.test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
            Reporter.test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
        }else if(result.getStatus() == ITestResult.STARTED) {
            Reporter.test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.PURPLE));
        }else if(result.getStatus() == ITestResult.SUCCESS_PERCENTAGE_FAILURE) {
            Reporter.test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.YELLOW));
        }

    }

    @AfterTest
    public void endReport() {
        extent.flush();
    }

    @AfterSuite
    public void afterSuite() {

        //System.out.println(">>>>>>>>>>>>"+System.getProperty("user.dir"));

        System.out.println(">>>>>>>>>>>>"+System.getProperty("user.dir")+"/ExtentReport.html");
    }

}
