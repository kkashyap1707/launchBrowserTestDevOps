package webLoadTest.test.v1;

import com.aventstack.extentreports.Status;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.HarCreatorBrowser;
import de.sstoehr.harreader.model.HarLog;
import de.sstoehr.harreader.model.HarPage;
import edu.umass.cs.benchlab.har.ISO8601DateFormatter;
import jdk.nashorn.internal.objects.Global;
import net.lightbody.bmp.core.har.Har;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import webLoadTest.test.TestBasePerformanceBrowser;
import webLoadTest.utilities.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;



public class SampleTest extends TestBasePerformanceBrowser {

    @Test
    public void testAnalyticsData_dev() throws IOException, HarReaderException, InterruptedException {

        Reporter.test.log(Status.INFO, "New Har file name is :: ProjectName"+ DateUtil.currentDateTime("MM/dd/yyyy hh:mm:ss a","IST"));

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("????????????????????"+DateUtil.currentDateTime("MM/dd/yyyy hh:mm:ss a","IST"));


        driver.get(GlobalVars.BASE_URL_DEV);
        Reporter.test.log(Status.INFO,"Checking for "+GlobalVars.BASE_URL_DEV);
        Thread.sleep(10000);
        driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);

    }





}
