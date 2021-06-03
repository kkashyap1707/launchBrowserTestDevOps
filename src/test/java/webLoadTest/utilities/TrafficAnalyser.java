package webLoadTest.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.HarReaderMode;
import de.sstoehr.harreader.jackson.MapperFactory;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.proxy.CaptureType;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import webLoadTest.test.TestBasePerformanceBrowser;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class TrafficAnalyser extends TestBasePerformanceBrowser {

    // https://mvnrepository.com/artifact/edu.umass.cs.benchlab/harlib/1.1.2
    //https://mvnrepository.com/artifact/de.sstoehr/har-reader/2.1.7
    //https://github.com/sdstoehr/har-reader


    public static Set<CaptureType> captureTypes(){

        Set<CaptureType> captureTypes = new HashSet<CaptureType>();
        captureTypes.add(CaptureType.REQUEST_BINARY_CONTENT);
        captureTypes.add(CaptureType.REQUEST_CONTENT);
        captureTypes.add(CaptureType.REQUEST_COOKIES);
        captureTypes.add(CaptureType.REQUEST_HEADERS);
        captureTypes.add(CaptureType.RESPONSE_BINARY_CONTENT);
        captureTypes.add(CaptureType.RESPONSE_CONTENT);
        captureTypes.add(CaptureType.RESPONSE_COOKIES);
        captureTypes.add(CaptureType.RESPONSE_HEADERS);


        return captureTypes;
    }

    public static void launchChromeBrowser(){
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver=new ChromeDriver(TrafficAnalyser.chromePropertiesForPerformance());
        //driver=new ChromeDriver();
        driver.manage().window().maximize();
    }

    public static ChromeOptions chromePropertiesForPerformance(){

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY,seleniumProxy);

        Map<String, Object> prefsMap = new HashMap<String, Object>();
        prefsMap.put("profile.default_content_settings.popups", 0);
        prefsMap.put("download.default_directory", fileDownloadPath);

        ChromeOptions optionsForPerformance = new ChromeOptions().merge(capabilities);

        optionsForPerformance.setExperimentalOption("prefs", prefsMap);
        //optionsForPerformance.setExperimentalOption("useAutomationExtension", false);
        //optionsForPerformance.addArguments("--test-type");
        //optionsForPerformance.addArguments("--disable-extensions");

        /*optionsForPerformance.addArguments("allow-running-insecure-content");
        optionsForPerformance.addArguments("--ignore-certificate-errors");*/

        return optionsForPerformance;

    }

    public static void launchFirefoxBrowser(){
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
        driver = new FirefoxDriver(TrafficAnalyser.firefoxPropertiesForPerformance());
        driver.manage().window().maximize();
    }

    public static FirefoxOptions firefoxPropertiesForPerformance(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY,seleniumProxy);



        FirefoxOptions optionsForFirefoxPerformance = new FirefoxOptions().merge(capabilities);

        /*optionsForFirefoxPerformance.setExperimentalOption("prefs", prefsMap);
        optionsForFirefoxPerformance.setExperimentalOption("useAutomationExtension", false);
        optionsForFirefoxPerformance.addArguments("--test-type");
        optionsForFirefoxPerformance.addArguments("--disable-extensions");*/

        return optionsForFirefoxPerformance;
    }

    public static de.sstoehr.harreader.model.Har harReader(String harFilePath) throws HarReaderException {
        HarReader harReader = new HarReader();
        de.sstoehr.harreader.model.Har har = harReader.readFromFile(new File(harFilePath));

        return har;
    }

    public static class MyMapperFactory implements MapperFactory {
        public ObjectMapper instance(HarReaderMode mode) {
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();

            // configure Jackson object mapper as needed

            mapper.registerModule(module);
            return mapper;
        }
    }

}
