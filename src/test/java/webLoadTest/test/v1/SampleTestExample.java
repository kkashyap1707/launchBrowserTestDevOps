package webLoadTest.test.v1;

import com.aventstack.extentreports.Status;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.HarCreatorBrowser;
import de.sstoehr.harreader.model.HarLog;
import de.sstoehr.harreader.model.HarPage;
import edu.umass.cs.benchlab.har.ISO8601DateFormatter;
import net.lightbody.bmp.core.har.Har;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import webLoadTest.test.TestBasePerformanceBrowser;
import webLoadTest.utilities.DateUtil;
import webLoadTest.utilities.GlobalVars;
import webLoadTest.utilities.Reporter;
import webLoadTest.utilities.TrafficAnalyser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class SampleTestExample extends TestBasePerformanceBrowser {


    String filename = TestBasePerformanceBrowser.currentDirectory+"/PerformanceTestHar.har";
    String filename1 = TestBasePerformanceBrowser.currentDirectory+"/PerformanceTestHar.txt";
    String filename2 = TestBasePerformanceBrowser.currentDirectory+"/GIFparameters.txt";


    private int totalTime = 0;
    private int sendReceiveTime = 0;
    private int fileSize = 0;
    private int waitTimeSum = 0;
    private int connectingTimeSum = 0;
    private int sendingTimeSum = 0;
    private int receivingTimeSum = 0;
    private int dnsTimeSum = 0;

    @Test
    public void sample() throws IOException, HarReaderException, InterruptedException {
        System.out.println("Keshav");



        Reporter.test.log(Status.INFO, "New Har file name is :: Stitcher"+ DateUtil.currentDateTime("MM/dd/yyyy hh:mm:ss a","IST"));

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("????????????????????"+DateUtil.currentDateTime("MM/dd/yyyy hh:mm:ss a","IST"));

        proxy.newHar(GlobalVars.PROJECT_NAME+ DateUtil.currentDateTime("MM/dd/yyyy hh:mm:ss a","IST"));


        driver.get(GlobalVars.BASE_URL);
        Reporter.test.log(Status.INFO,"Checking for "+GlobalVars.BASE_URL);
        Thread.sleep(20000);
        driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);


    }




    //@Test
    public void testAnalyticsData_dev() throws IOException, HarReaderException, InterruptedException {

        Reporter.test.log(Status.INFO, "New Har file name is :: teachingchannellt"+ DateUtil.currentDateTime("MM/dd/yyyy hh:mm:ss a","IST"));

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("????????????????????"+DateUtil.currentDateTime("MM/dd/yyyy hh:mm:ss a","IST"));

        proxy.newHar(GlobalVars.PROJECT_NAME+ DateUtil.currentDateTime("MM/dd/yyyy hh:mm:ss a","IST"));


        driver.get(GlobalVars.BASE_URL_STAGE);
        Reporter.test.log(Status.INFO,"Checking for "+GlobalVars.BASE_URL_DEV);
        Thread.sleep(10000);
        driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);

        //Click on Login
        driver.findElement(By.cssSelector("#app > div.v-application--wrap > header > div > div:nth-child(5) > span > button")).click();
        Thread.sleep(10000);
        Reporter.test.log(Status.INFO,"User clicked on continue button.");

        //Click on Login
        driver.findElement(By.cssSelector("#auth_login_button > span")).click();
        Thread.sleep(10000);
        Reporter.test.log(Status.INFO,"User clicked on continue button.");

        //Enter Username on Login
        driver.findElement(By.cssSelector("#authLogin_invalidEmail_label")).sendKeys("keshav.kashyap@tothenew.com");
        Thread.sleep(10000);
        Reporter.test.log(Status.INFO,"User clicked on continue button.");

        //Enter Password on Login
        driver.findElement(By.cssSelector("#authLogin_password_field")).sendKeys("Kkashyap@1408");
        Thread.sleep(10000);
        Reporter.test.log(Status.INFO,"User clicked on continue button.");

        //Click on Submit
        driver.findElement(By.cssSelector("#auth_login_button")).click();
        Thread.sleep(10000);
        Reporter.test.log(Status.INFO,"User clicked on continue button.");




        //Login


    }





}
