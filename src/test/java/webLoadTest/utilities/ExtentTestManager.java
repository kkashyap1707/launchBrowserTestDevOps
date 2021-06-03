package webLoadTest.utilities;

public class ExtentTestManager {

    /*private static final Map extentTestMap = new HashMap();
    private static final ExtentReports extent = ExtentManager.getReporter();
    public static ExtentTest test;

    public static synchronized ExtentTest getTest() {
        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized ExtentTest startTest(String testName) {
        return startTest(testName,"");


    }

    private static synchronized ExtentTest startTest(String testName, String desc) {
      //  test = extent.startTest(testName +" " + TestBase.retrievalID, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }

    public static synchronized ExtentTest assignCategory(String env, String ver) {
        test.assignCategory(env + ver);
        return test;
    }
*/

}
