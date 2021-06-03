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


public class SampleTestLearning extends TestBasePerformanceBrowser {



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
    public void testAnalyticsData_dev() throws IOException, HarReaderException, InterruptedException {

        Reporter.test.log(Status.INFO, "New Har file name is :: teachingchannellt"+ DateUtil.currentDateTime("MM/dd/yyyy hh:mm:ss a","IST"));

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("????????????????????"+DateUtil.currentDateTime("MM/dd/yyyy hh:mm:ss a","IST"));

        proxy.newHar(GlobalVars.PROJECT_NAME+ DateUtil.currentDateTime("MM/dd/yyyy hh:mm:ss a","IST"));


        driver.get(GlobalVars.BASE_URL_DEV);
        Reporter.test.log(Status.INFO,"Checking for "+GlobalVars.BASE_URL_DEV);
        Thread.sleep(10000);
        driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);

        //Click on Login

        //Login

        //Click on Continue
        driver.findElement(By.xpath("//*[@id='verify-footer']/div/button")).click();
        Thread.sleep(10000);
        Reporter.test.log(Status.INFO,"User clicked on continue button.");

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
            Reporter.test.log(Status.INFO,"Total Number of Requests :: "+size);

            for (int i=0; i<size; i++){

                // System.out.println("URL is "+harLog.getEntries().get(i).getRequest().getUrl() + "& Wait time is :: "+ harLog.getEntries().get(i).getTimings().getWait());

                waitTimeSum = waitTimeSum +  harLog.getEntries().get(i).getTimings().getWait();
                connectingTimeSum = connectingTimeSum +  harLog.getEntries().get(i).getTimings().getSend();
                sendingTimeSum = sendingTimeSum +  harLog.getEntries().get(i).getTimings().getReceive();
                receivingTimeSum = receivingTimeSum +  harLog.getEntries().get(i).getTimings().getConnect();
                dnsTimeSum = dnsTimeSum +  harLog.getEntries().get(i).getTimings().getDns();
            }


            for (int i=0; i < size ; i++) {
                totalTime = totalTime + harLog.getEntries().get(i).getTime();

                //System.out.println("response code: "+ harLog.getEntries().get(i).getResponse().getStatus());
                //System.out.println("response code: "+ harLog.getEntries().get(i).getResponse().getStatusText());

                int responseCode = harLog.getEntries().get(i).getResponse().getStatus();

                if(responseCode == 404){
                    Reporter.test.log(Status.WARNING,"Status is : " + responseCode + "for"+harLog.getEntries().get(i).getRequest().getUrl());
                }

                int headerSize = harLog.getEntries().get(i).getResponse().getHeaders().size();

                for (int j=0; j< headerSize; j++){

                    String contentLength = harLog.getEntries().get(i).getResponse().getHeaders().get(j).getName();

                    if(contentLength.equalsIgnoreCase("Content-Length")){

                        int iNum = Integer.parseInt(harLog.getEntries().get(i).getResponse().getHeaders().get(j).getValue());
                        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> "+ i +":: "+iNum);
                        fileSize = fileSize+iNum;

                    }
                }
            }

            System.out.println("Sum value of Wait time array elements is : " + waitTimeSum);
            //Reporter.test.log(Status.INFO,"Sum value of Wait time array elements is : " + waitTimeSum);
            Reporter.test.log(Status.INFO,"Sum value of Wait time array elements in seconds : " + waitTimeSum/1000+"s");

            System.out.println("Sum value of Connecting time array elements is : " + connectingTimeSum);
            //Reporter.test.log(Status.INFO,"Sum value of Connecting time array elements is : " + connectingTimeSum);
            Reporter.test.log(Status.INFO,"Sum value of Connecting time array elements in seconds : " + connectingTimeSum/1000+"s");

            System.out.println("Sum value of Sending time array elements is : " + sendingTimeSum);
            //Reporter.test.log(Status.INFO,"Sum value of Sending time array elements is : " + sendingTimeSum);
            Reporter.test.log(Status.INFO,"Sum value of Sending time array elements in seconds : " + sendingTimeSum/1000+"s");

            System.out.println("Sum value of Receiving time array elements is : " + receivingTimeSum);
            //Reporter.test.log(Status.INFO,"Sum value of Receiving time array elements is : " + receivingTimeSum);
            Reporter.test.log(Status.INFO,"Sum value of Receiving time array elements in seconds : " + receivingTimeSum/1000+"s");

            System.out.println("Sum value of DNS time array elements is : " + dnsTimeSum);
            //Reporter.test.log(Status.INFO,"Sum value of DNS time array elements is : " + dnsTimeSum);
            Reporter.test.log(Status.INFO,"Sum value of DNS time array elements in seconds : " + dnsTimeSum/1000+"s");

            sendReceiveTime = sendingTimeSum + receivingTimeSum;
            System.out.println(">>>>>>>>>>>>>>>>>"+sendReceiveTime);
            Reporter.test.log(Status.INFO,"Sending and Receiving time in seconds : " + sendReceiveTime/1000+"s");

            System.out.println("Sum value of time array elements is : " + totalTime);
            Reporter.test.log(Status.INFO,"Sum value of Total time array elements is : " + totalTime);
            Reporter.test.log(Status.INFO,"Sum value of Total time array elements in seconds : " + totalTime/1000+"s");


            int finalMinusSendReceiveTime        =  totalTime -  sendReceiveTime;
            int finalMinusSendTime               =  totalTime -  sendingTimeSum;
            int finalMinusReceiveTime            =  totalTime -  receivingTimeSum;
            int waitPlusSendTime                 =  sendingTimeSum + waitTimeSum;
            int waitPlusSendMinusReceiveTime     =  receivingTimeSum - waitPlusSendTime;


            Reporter.test.log(Status.INFO,"Final Minus Send Receive Time in seconds : "          + finalMinusSendReceiveTime/1000 +"s");
            Reporter.test.log(Status.INFO,"Final Minus Send Time in seconds : "                  + finalMinusSendTime/1000 +"s");
            Reporter.test.log(Status.INFO,"Final Minus Receive Time in seconds : "               + finalMinusReceiveTime/1000 +"s");
            Reporter.test.log(Status.INFO,"Wait Plus Send Time in seconds : "                    + waitPlusSendTime/1000 +"s");
            Reporter.test.log(Status.INFO,"Wait Plus Send Minus Receive Time in seconds : "      + waitPlusSendMinusReceiveTime/1000 +"s");


            System.out.println("Total File Size is : " + fileSize);
            System.out.println("Total File Size in KB is : " + fileSize/1000 + "KB");

            Reporter.test.log(Status.INFO,"Total File Size in KB is : " + fileSize/1000 + "KB");


        }catch(Exception e){

            e.printStackTrace();
        }

    }





}
