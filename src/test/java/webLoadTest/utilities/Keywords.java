package webLoadTest.utilities;


import webLoadTest.test.TestBaseBrowser;

import java.io.FileReader;
import java.util.Properties;

public class Keywords extends TestBaseBrowser {


    public static void loadSheetValue() {

        Properties properties = new Properties();
        String fileName = TestBaseBrowser.env + "_GoogleSpreadsheet.properties";
        System.out.println(fileName);
        try {
            FileReader reader = new FileReader(fileName);
            properties.load(reader);

            //GlobalVars.SystemId    = properties.getProperty("SystemId").isEmpty()?properties.getProperty("SystemId"):"";
            GlobalVars.SystemId    = properties.getProperty("SystemId");


        }catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static void setURL(String env, String ver) {

        Properties properties = new Properties();
        String fileName = "application-" + TestBaseBrowser.env + ".properties";
        System.out.println(fileName);
        try {
            FileReader reader = new FileReader(fileName);
            properties.load(reader);

            GlobalVars.BASE_URL = properties.getProperty("BASE_URL");
            GlobalVars.BASE_URL_DEV = properties.getProperty("BASE_URL_DEV");
            GlobalVars.BASE_URL_STAGE = properties.getProperty("BASE_URL_STAGE");
            GlobalVars.PROJECT_NAME = properties.getProperty("PROJECT_NAME");
            GlobalVars.MOBILE_NUMBER = properties.getProperty("MOBILE_NUMBER");



        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }



}
