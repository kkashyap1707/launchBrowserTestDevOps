package webLoadTest.test.v1;

import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.HarCreatorBrowser;
import de.sstoehr.harreader.model.HarLog;
import de.sstoehr.harreader.model.HarPage;
import edu.umass.cs.benchlab.har.ISO8601DateFormatter;
import net.lightbody.bmp.core.har.Har;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import webLoadTest.test.TestBasePerformanceBrowser;
import webLoadTest.utilities.DateUtil;
import webLoadTest.utilities.TrafficAnalyser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Sample1Test extends TestBasePerformanceBrowser {

    String workingDir = System.getProperty("user.dir");
    String filename = workingDir+"/PerformanceTestHar.har";
    String filename1 = workingDir+"/PerformanceTestHar.txt";
    String filename2 = workingDir+"/GIFparameters.txt";

    private int sum = 0;
    private int fileSize = 0;
    private int waitTimeSum = 0;

    //@Test
    public void firefox(){
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
        WebDriver driver = new FirefoxDriver(TrafficAnalyser.firefoxPropertiesForPerformance());
        driver.get("https://www.google.com");
        String title = driver.getTitle();
        System.out.println("Title is " + title );
        driver.quit();

    }

  /*  @BeforeTest
    private void beforeTestAnalyticsData(){

        proxy = new BrowserMobProxyServer();
        proxy.setTrustAllServers(true);
        proxy.start();

        seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        proxy.enableHarCaptureTypes(TrafficAnalyser.captureTypes());

        TrafficAnalyser.launchChromeBrowser();
        //TrafficAnalyser.launchFirefoxBrowser();

    }*/

    @Test
    public void testAnalyticsData() throws IOException, HarReaderException, InterruptedException {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("????????????????????"+DateUtil.currentDateTime("MM/dd/yyyy hh:mm:ss a","GMT"));

        proxy.newHar("Tatasky"+ DateUtil.currentDateTime("MM/dd/yyyy hh:mm:ss a","GMT"));

        driver.get("https://www.tatasky.com/");
        Thread.sleep(10000);
        driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);

        Har har = proxy.getHar();
        FileOutputStream fos = new FileOutputStream(filename);
        har.writeTo(fos);

        de.sstoehr.harreader.model.Har read = TrafficAnalyser.harReader(filename);
        Assert.assertNotNull(read);

        System.out.println(read.getLog().getVersion());
        System.out.println(read.getLog().getPages().get(0).getId());

        try{
            System.out.println("Reading " + filename);

            HarLog harLog = read.getLog();

            HarCreatorBrowser harCreatorBrowser = harLog.getBrowser();

            System.out.println("BrowserName    :: "+harCreatorBrowser.getName());
            System.out.println("BrowserVersion :: "+harCreatorBrowser.getVersion());
            System.out.println("BrowserComment :: "+harCreatorBrowser.getComment());

           /* harLog.getComment();
            harLog.getCreator();

            harLog.getPages();
            harLog.getVersion();*/

            List<HarPage> harPages = harLog.getPages();

            for (HarPage page : harPages) {
                System.out.println("page start time: " + ISO8601DateFormatter.format(page.getStartedDateTime()));
                System.out.println("page id: " + page.getId());
                System.out.println("page title: " + page.getTitle());
            }

            /*
            List<HarEntry> harEntry = harLog.getEntries();

            for (HarEntry entry : harEntry)
            {
               // System.out.println("request code:  "+ entry.getRequest().getMethod()); // Output request// type
                System.out.println("response code: "+ entry.getRequest().getUrl()); // Output url of request
                System.out.println("response code: "+ entry.getResponse().getStatus()); // Output the

                System.out.println("response additional: "+ entry.getResponse().getAdditional());

                System.out.println("Send Time: "+ entry.getTimings().getSend());
                System.out.println("Receive Time: "+ entry.getTimings().getReceive());
                System.out.println("Wait Time: "+ entry.getTimings().getWait());

                System.out.println("Connection: "+ entry.getConnection());

                System.out.println("Started Time: "+ entry.getStartedDateTime().getTime());

                System.out.println("Additional: "+ entry.getAdditional());

                System.out.println("Send Time: "+ entry.getTimings().getSend());
            }

            */

            int size = harLog.getEntries().size();

            System.out.println("Total Number of Requests :: "+size);

            for (int i=0; i<size; i++){
                System.out.println("URL is "+harLog.getEntries().get(i).getRequest().getUrl() + "& Wait time is :: "+ harLog.getEntries().get(i).getTimings().getWait());

                waitTimeSum = waitTimeSum +  harLog.getEntries().get(i).getTimings().getWait();
            }

            System.out.println("Sum value of wait time array elements is : " + waitTimeSum);


            for (int i=0; i < size ; i++) {
                sum = sum + harLog.getEntries().get(i).getTime();

                int headerSize = harLog.getEntries().get(i).getResponse().getHeaders().size();

                for (int j=0; j< headerSize; j++){

                    String contentLength = harLog.getEntries().get(i).getResponse().getHeaders().get(j).getName();

                    if(contentLength.equalsIgnoreCase("Content-Length")){

                        int iNum = Integer.parseInt(harLog.getEntries().get(i).getResponse().getHeaders().get(j).getValue());
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> "+ i +":: "+iNum);
                        fileSize = fileSize+iNum;

                    }
                }
            }

            System.out.println("Sum value of time array elements is : " + sum);
            System.out.println("Total File Size is : " + fileSize);
            System.out.println("Total File Size in KB is : " + fileSize/1000 + "KB");


        }catch(Exception e){

            e.printStackTrace();
        }

    }


    /*@AfterTest
    private void afterTestAnalyticsData() {
        if (driver != null) {
            driver.quit();
        } else if (proxy.isStarted()) {
            proxy.stop();
        }
    }*/




}
