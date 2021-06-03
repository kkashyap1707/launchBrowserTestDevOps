package webLoadTest.test.v1;

import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class NetworkTrafficJSONTest {


    //https://www.eviltester.com/2010/05/a-selenium-capturenetworktraffic-example-in-java.html

    public static Har harReader(String harFilePath) throws HarReaderException {
        HarReader harReader = new HarReader();
        Har har = harReader.readFromFile(new File(harFilePath));

        return har;
    }


    @Test
    public void herReader() throws HarReaderException {
        System.out.println("Keshav");

        String harFilePath = "/home/keshav/IdeaProjects/webApplicationFramework/PerformanceTestHar.har";

        Har read = harReader(harFilePath);

        Assert.assertNotNull(read);

        System.out.println(read.getLog().getVersion());
        System.out.println(read.getLog().getPages().get(0).getId());

    }


}
