package webLoadTest.test.v1;

import net.lightbody.bmp.BrowserMobProxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ExampleTest {


    public static String currentDirectory = System.getProperty("user.dir");

    String driverPath = currentDirectory + "/lib/chromedriver";

    public WebDriver driver;
    public BrowserMobProxy proxy;

    @BeforeTest
    public void setUp() throws Exception{

        System.out.println("Keshav New selenium test");

        // configure it as a desired capability
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("acceptInsecureCerts", true);

        ChromeOptions optionsForPerformance = new ChromeOptions().merge(capabilities);
        optionsForPerformance.setExperimentalOption("useAutomationExtension", false);
        optionsForPerformance.addArguments("--test-type");
        optionsForPerformance.addArguments("--disable-extensions");

        optionsForPerformance.addArguments("allow-running-insecure-content");
        optionsForPerformance.addArguments("--ignore-certificate-errors");
        optionsForPerformance.addArguments("--ignore-ssl-errors=yes");
        optionsForPerformance.addArguments("--ignore-certificate-errors");
        optionsForPerformance.addArguments("--allow-insecure-localhost");

        //set chromedriver system property
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver(capabilities);

        // open google.com
        driver.get("https://www.google.com/");
        Thread.sleep(2000);

    }

    @Test
    public void testCaseOne() throws InterruptedException {
        System.out.println("Navigate to gmail page");

        driver.navigate().to("https://www.gmail.com");
        Thread.sleep(2000);
    }

    @AfterTest
    public void tearDown() {

        if (driver != null) {

            driver.quit();
        }
    }


}
