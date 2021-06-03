package webLoadTest.utilities;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import webLoadTest.test.TestBaseBrowser;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

public class BrowserUtil extends TestBaseBrowser {

    private static String OS = System.getProperty("os.name").toLowerCase();
    public static String browserType = System.getProperty("browserType");
    public static String currentDirectory = System.getProperty("user.dir");

    public static WebDriver driver;
    public static WebDriverWait wait;
    public static String fileDownloadPath = currentDirectory + "/downloads";
    public static String chromeDriverPath = currentDirectory + "/lib/chromedriver";
    //public static String chromeDriverPath = currentDirectory + "/lib/chromedriver_linux64/chromedriver";
    //public static String chromeDriverPathForJenkins = currentDirectory + "/lib/chromedriver_linux64/chromedriver";

    public static void openChromeBrowser() {

        System.out.println(">>>>>>>>>>>>>>"+System.getProperty("user.name"));

        String userName =  System.getProperty("user.name");

        if(userName.equals("keshav")){
            System.out.println(">>>>>>>>>>>>>>>Before Browser Opening");
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            System.out.println("??????????????????????????????????????/"+chromeProperties());
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>/"+chromePropertiesForJenkins());
            driver=new ChromeDriver(chromeProperties());
            wait = new WebDriverWait(driver, 5);
            Reporter.test.log(Status.INFO,"Browser Launched Successfully");

        } else if (userName.equals("jenkins")){
            System.out.println(">>>>>>>>>>>>>>>Before Browser Opening");
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
            System.out.println(chromePropertiesForJenkins());

            driver=new ChromeDriver(chromePropertiesForJenkins());
            wait = new WebDriverWait(driver, 5);
            Reporter.test.log(Status.INFO,"Browser Launched Successfully");
        } else if (userName.equals("ubuntu")){
            System.out.println(">>>>>>>>>>>>>>>Before Browser Opening");
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
            System.out.println(chromePropertiesForJenkins());

            driver=new ChromeDriver(chromePropertiesForJenkins());
            wait = new WebDriverWait(driver, 5);
            Reporter.test.log(Status.INFO,"Browser Launched Successfully");
        }
    }

    public static void closeBrowser() {
        if(driver!=null) {
            driver.quit();

            //  Reporter.test.log(Status.INFO,"Browser closed successfully");
        }
    }

    public static ChromeOptions chromeProperties(){
        Map<String, Object> prefsMap = new HashMap<String, Object>();
        prefsMap.put("profile.default_content_settings.popups", 0);
        prefsMap.put("download.default_directory", fileDownloadPath);

        ChromeOptions option = new ChromeOptions();
        option.setExperimentalOption("prefs", prefsMap);
        option.setExperimentalOption("useAutomationExtension", false);
        option.addArguments("--test-type");
        option.addArguments("--disable-extensions");
        //option.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");

        System.out.println("Option is ::"+option);

        return option;
    }

    public static ChromeOptions chromePropertiesForJenkins(){
        Map<String, Object> prefsMap = new HashMap<String, Object>();
        prefsMap.put("profile.default_content_settings.popups", 0);
        prefsMap.put("download.default_directory", fileDownloadPath);

        ChromeOptions option = new ChromeOptions();
        option.setExperimentalOption("prefs", prefsMap);
        option.setExperimentalOption("useAutomationExtension", false);
        option.addArguments("--test-type");
        option.addArguments("--disable-extensions");
        option.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
        option.addArguments("--no-sandbox");
        option.addArguments("--disable-dev-shm-usage");

        System.out.println("Option is ::"+option);

        return option;
    }

    public static void launchBrowser(String URL) throws InterruptedException {

        String userName =  System.getProperty("user.name");

        if(userName.equals("keshav")){

            System.out.println(">>>>>>>>>>>>>>>Before Browser Opening");
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            System.out.println("??????????????????????????????????????/"+chromeProperties());
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>/"+chromePropertiesForJenkins());
            driver=new ChromeDriver(chromeProperties());

            driver.manage().window().maximize();
            driver.get(URL);
            wait = new WebDriverWait(driver, 5);
            //Thread.sleep(5000);
            System.out.println("KEshav>>>>>>>Local>>>>>>>");

            Reporter.test.log(Status.INFO,"Browser Launched Successfully");

        } else if (userName.equals("jenkins")){
            System.out.println(">>>>>>>>>>>>>>>Before Browser Opening Jenkins");
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
            System.out.println(chromePropertiesForJenkins());

            driver=new ChromeDriver(chromePropertiesForJenkins());
            wait = new WebDriverWait(driver, 5);
            Reporter.test.log(Status.INFO,"Browser Launched Successfully");
        }

    }

    public static void loginTataSky(String URL, String MOBILE_NUMBER) throws InterruptedException {
        driver.get(URL);
        Reporter.test.log(Status.INFO,"Checking for "+URL);
        Thread.sleep(10000);
        driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);

        //Click on Existing Customer
        driver.findElement(By.cssSelector("#mainLandingPage > div.container > div.content-container > div > div.btn-contr.btn-responsive-contr.text-center.modify-pack-sub-heading > button")).click();
        Thread.sleep(5000);
        Reporter.test.log(Status.INFO,"User clicked on the existing customer link.");

        //Click on go to my Account
        driver.findElement(By.cssSelector("#welcomeBack > div.container > div.content-container > div > div.btn-contr.btn-responsive-contr.text-center.modify-pack-sub-heading > button")).click();
        Thread.sleep(5000);
        Reporter.test.log(Status.INFO,"User clicked on Go to my Account.");

        //Enter Mobile Number
        driver.findElement(By.cssSelector("#user-auth > div > div.content-container > div > div.main-container > form > div.body-container > div > div > div > input")).sendKeys(GlobalVars.MOBILE_NUMBER);
        Thread.sleep(5000);
        Reporter.test.log(Status.INFO,"User entered mobile number "+ MOBILE_NUMBER);

        //Click on Continue
        driver.findElement(By.xpath("//*[@id='verify-footer']/div/button")).click();
        Thread.sleep(5000);
        Reporter.test.log(Status.INFO,"User clicked on continue button.");


    }

    public static void loginAdmin() throws InterruptedException {

        Thread.sleep(15000);

        driver.findElement(By.xpath("//input[@name='auth-username']")).sendKeys(GlobalVars.UPLOAD_PROGRESS_USERNAME);
        Thread.sleep(5000);
        Reporter.test.log(Status.INFO,"User entered user name ");

        driver.findElement(By.xpath("//input[@name='auth-password']")).sendKeys(GlobalVars.UPLOAD_PROGRESS_PASSWORD);
        Thread.sleep(5000);
        Reporter.test.log(Status.INFO,"User entered password ");

        driver.findElement(By.cssSelector("body > div.container > div > div > form > button.btn.btn-primary")).click();
        Thread.sleep(10000);
        Reporter.test.log(Status.INFO,"User clicked on Next button redirected to Upload Progress Page.");

        driver.findElement(By.cssSelector("#searchbar")).sendKeys(GlobalVars.UPLOAD_PROGRESS_SFTP_USERNAME);
        Thread.sleep(5000);
        Reporter.test.log(Status.INFO,"User searched for "+GlobalVars.UPLOAD_PROGRESS_SFTP_USERNAME);

        driver.findElement(By.cssSelector("#changelist-search > div > input[type=submit]:nth-child(3)")).click();
        Thread.sleep(5000);
        Reporter.test.log(Status.INFO,"User clicked on search button.");

        String status = driver.findElement(By.cssSelector("#result_list > tbody > tr:nth-child(1) > td.field-state")).getText();

        if(status.equalsIgnoreCase("Completed")){

            Reporter.test.log(Status.FAIL, "Status is "+status);
            Reporter.test.log(Status.FAIL,"Status is Completed. It should be failed.");
            driver.findElement(By.cssSelector("#result_list > tbody > tr:nth-child(1) > th > a")).click();
            Thread.sleep(5000);

        }else {

            System.out.println("Status is failed");
            Reporter.test.log(Status.PASS, "Status is "+status);
            driver.findElement(By.cssSelector("#result_list > tbody > tr:nth-child(1) > th > a")).click();
            Thread.sleep(5000);

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

            Boolean flag = driver.getPageSource().contains("veronicatesta.veronica@allyo.com");

            System.out.println("Error is :: "+flag);

            System.out.println("Boolean Value is ::"+flag);
            if (flag){
                Reporter.test.log(Status.PASS, "Expected First Interviewer Email Id "+GlobalVars.FIRST_INTERVIEWER_EMAIL+" error found.");
            }else {
                Reporter.test.log(Status.FAIL, "Expected First Interviewer Email Id "+GlobalVars.FIRST_INTERVIEWER_EMAIL+" error not found.");

            }
        }

    }

    public static void launch(String URL) throws InterruptedException{

        driver = new ChromeDriver(chromeProperties());
        driver.get(URL);
        Reporter.test.log(Status.PASS, "Browser launched successfully.");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        Thread.sleep(5000);
        System.out.println("Downloaded of csv completed.");

        Reporter.test.log(Status.PASS, "CSV File downloaded successfully.");
        //driver.quit();
        Reporter.test.log(Status.PASS, "Browser closed successfully.");
    }

    public static void deleteFile(){

        File file = new File(currentDirectory + "/downloads/QA_Test41_Corporate_Jobs_1 - good.csv");

        // renaming the file and moving it to a new location
        if(file.renameTo
                (new File(currentDirectory + "/oldFile/QA_Test41_Corporate_Jobs64.csv")))
        {
            // if file copied successfully then delete the original file
            //file.delete();
            System.out.println("File moved successfully");
        }
        else
        {
            System.out.println("Failed to move the file");
        }
    }

    public static void delete(String folderName){

        File dir1 = new File(currentDirectory + folderName);

        if(dir1.isDirectory()) {
            System.out.println("HOLA 1");
            File[] content = dir1.listFiles();

            System.out.println("Total number of files :: "+content.length);
            Reporter.test.log(Status.INFO,"Total number of files :: "+content.length);

            if(content.length==0){
                System.out.println("No previously downloaded file found.");
                Reporter.test.log(Status.INFO,"No previously downloaded file found.");
            }else {
                for(int i = 0; i < content.length; i++) {

                    //move content[i]

                    String currentFileName = content[i].getName();
                    System.out.println("Current file name :: "+currentFileName);
                    Reporter.test.log(Status.INFO,"Current file name :: "+currentFileName);

                    content[i].delete();
                    System.out.println(i+" "+currentFileName+":: Old file deleted.");
                    Reporter.test.log(Status.PASS,i+" Old file deleted.");
                }
            }
        }
    }

    public static String renameFileAsCSV(String oldFileName, String newFileName){

        File file = new File(currentDirectory + "/downloads/"+oldFileName);
        String newCSVFile = null;

        // renaming the file and moving it to a new location
        if(file.renameTo(new File(currentDirectory + "/oldFile/"+newFileName)))
        {
            newCSVFile = newFileName;
            // if file copied successfully then rename the original file
            System.out.println("File renamed successfully "+newFileName);

            Reporter.test.log(Status.INFO,"File renamed successfully "+newFileName);

            System.out.println("New file name is :: "+newFileName);
            Reporter.test.log(Status.INFO,"New file name is :: "+newFileName);
        }
        else
        {
            System.out.println("Failed to renamed the file");
            Reporter.test.log(Status.ERROR,"Failed to renamed the file");
        }

        return newCSVFile;
    }

    public static void copyFileToFile(final File src, final File dest) throws Exception {
        copyInputStreamToFile(new FileInputStream(src), dest);
        dest.setLastModified(src.lastModified());
    }

    public static File renameFileAsCSV1(String oldFileName, String newFileName){

        File file = new File(currentDirectory + "/downloads/"+oldFileName);
        File file2 = new File(currentDirectory + "/downloads/"+newFileName);

        File newCSVFile = null;

        // renaming the file and moving it to a new location
        if(file.renameTo(file2))
        {
            newCSVFile = file2;
            // if file copied successfully then rename the original file
            System.out.println("File renamed successfully "+file2);
            Reporter.test.log(Status.INFO,"File renamed successfully "+file2);
            System.out.println("New file name is :: "+file2);
            Reporter.test.log(Status.INFO,"New file name is :: "+file2);
        }
        else
        {
            System.out.println("Failed to renamed the file");
            Reporter.test.log(Status.ERROR,"Failed to renamed the file");
        }

        return newCSVFile;
    }

    public static void getFileName(){
        System.out.println("currentDirectory is :: "+currentDirectory);
        File folder = new File(currentDirectory + "/downloads/");

        System.out.println("Folder ::>"+folder);

        File[] listOfFiles = folder.listFiles();

        System.out.println("Total File count :: "+listOfFiles.length);

        if(listOfFiles.length > 0){
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println("File " + listOfFiles[i].getName());

                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory " + listOfFiles[i].getName());

                }
            }
        }else{
            System.out.println("No previously downloaded File found.");
        }

    }

    public static String getFileName1(){

        String fileNameInDownloadsFolder = null;

        File folder = new File(currentDirectory + "/downloads/");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());

                fileNameInDownloadsFolder = listOfFiles[i].getName();

            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());

            }
        }

        return fileNameInDownloadsFolder;
    }

    public static void validateProgress(String URL) throws InterruptedException{
        driver.get(URL);
        BrowserUtil.login(GlobalVars.USERNAME,GlobalVars.PASSWORD);
        Thread.sleep(10000);
        driver.navigate().refresh();
    }

    public static void login(String UserName, String Password){

        driver.findElement(By.cssSelector("#id_auth-username")).sendKeys(UserName);
        driver.findElement(By.cssSelector("#id_auth-password")).sendKeys(Password);
        driver.findElement(By.cssSelector("body > div.container > div > div > form > button.btn.btn-primary")).click();

    }

    public static void logout(){
        driver.findElement(By.cssSelector("#user-tools > a:nth-child(4)")).click();
    }

    public static String employerUploadPath() throws InterruptedException{

        Thread.sleep(9000);
        driver.navigate().refresh();
        Thread.sleep(19000);
        String employerUploadPathName =  driver.findElement(By.cssSelector("#result_list > tbody > tr:nth-child(1) > td.field-path")).getText();

        return employerUploadPathName;
    }

    public static String path() throws InterruptedException{
        BrowserUtil.validateProgress(GlobalVars.UPLOAD_PROGRESS_CHECK_URL);
        String uploadPath = BrowserUtil.employerUploadPath();
        System.out.println("Path is :: "+uploadPath);
        BrowserUtil.logout();

        return uploadPath;
    }



}
