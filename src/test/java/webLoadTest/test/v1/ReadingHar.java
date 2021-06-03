package webLoadTest.test.v1;


//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//import java.io.*;
//
//import jxl.Sheet;
//import jxl.Workbook;
//import jxl.WorkbookSettings;
//import jxl.read.biff.BiffException;
//import jxl.write.WritableSheet;
//import jxl.write.WritableWorkbook;
//import jxl.write.WriteException;
//import jxl.write.biff.RowsExceededException;
//
//import org.browsermob.core.har.Har;
//import org.browsermob.proxy.ProxyServer;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Proxy;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.remote.CapabilityType;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.codehaus.jackson.JsonParseException;
//
//import edu.umass.cs.benchlab.har.*;
//import edu.umass.cs.benchlab.har.tools.*;
//
//public class ReadingHar
//
//{
//	private String inputFile;
//
//	public void setInputFile(String inputFile)
//	{
//		this.inputFile = inputFile;
//	}
//
//	public String readCellDataUsingIndexFromXLSFile(String inputFile,int workSheetNumber, int ColIndex, int rowIndex) throws IOException {
//		String cellData = null;
//
//		File inputWorkbook = new File(inputFile);
//		Workbook w;
//
//		try {
//			w = Workbook.getWorkbook(inputWorkbook);
//
//			// Get the first sheet
//			Sheet sheet = w.getSheet(workSheetNumber);
//			System.out.println("Specific Row data: " + sheet);
//
//			WorkbookSettings ws = new WorkbookSettings();
//			ws.setLocale(new Locale("er", "ER"));
//
//			int columns = sheet.getColumns();
//			System.out.println("No. of Columns : " + columns);
//
//			int rows = sheet.getRows();
//			System.out.println("No. of Rows : " + rows);
//
//			// Loop over first 10 column and lines
//			int i = 0, j = 0;
//			for (i = 0; i < sheet.getRows(); i++) {
//				if (i == rowIndex) {
//					break;
//				}
//			}
//			for (j = 0; j < sheet.getColumns(); j++) {
//				if (ColIndex == j) {
//					break;
//				}
//			}
//			cellData = sheet.getCell(j, i).getContents();
//			System.out.println("Cell Data :: " + cellData);
//
//		} catch (BiffException e)
//		{
//			e.printStackTrace();
//		}
//
//		System.out.println("Specific Row data: " + cellData);
//
//		;
//
//		return cellData;
//	}
//
//	public void writeCellDataInExistingXLS(String excelPath, int SheetIndex,int rowIndex, int ColIndex, String Text) throws IOException,RowsExceededException, WriteException, BiffException {
//		Workbook aWorkBook = Workbook.getWorkbook(new File(excelPath));
//		WritableWorkbook aCopy = Workbook.createWorkbook(new File(excelPath),aWorkBook);
//		WritableSheet aCopySheet = aCopy.getSheet(SheetIndex);// index of the
//																// needed sheet
//		jxl.write.Label anotherWritableCell = new jxl.write.Label(ColIndex,rowIndex, Text);
//		aCopySheet.addCell(anotherWritableCell);
//		aCopy.write();
//		aCopy.close();
//	}
//
//
//	public static void main(String[] args)throws Exception {
//
//		String workingDir = System.getProperty("user.dir");
//		System.out.println("Current working directory : " + workingDir);
//
//
//		String filename = workingDir+"\\PerformanceTestHar.har";
//		String filename1 = workingDir+"\\PerformanceTestHar.txt";
//		String filename2 = workingDir+"\\GIFparameters.txt";
//
//		ReadingHar read = new ReadingHar();
//		String url = read.readCellDataUsingIndexFromXLSFile(workingDir+ "\\GA_Excel_Execution.xls", 0, 0, 1);
//
//		File f = new File(filename);
//		HarFileReader readhar = new HarFileReader();
//		HarFileWriter writehar = new HarFileWriter();
//
//
//		//*************************BrowserMob Proxy Started*********************************//
//		String PROXY = "localhost:9070";
//
//			// start the proxy
//	    	ProxyServer server = new ProxyServer(9070);
//	    	server.start();
//
//	    	//captures the mouse movements and navigations
//	    	server.setCaptureHeaders(true);
//        	server.setCaptureContent(true);
//
//	    	// get the Selenium proxy object
//	    	Proxy proxy = server.seleniumProxy();
//
//	    	// configure it as a desired capability
//	    	DesiredCapabilities capabilities = new DesiredCapabilities();
//	    	capabilities.setCapability(CapabilityType.PROXY,proxy);
//
//	    	// start the browser up
//	    	WebDriver driver = new InternetExplorerDriver(capabilities);
//
//	    	// create a new HAR with the label "wwe.com"
//	    	server.newHar("http://64.152.0.51/");
//
//	    	// open the url
//	    	driver.get(url);
//
//	    	// get the HAR data
//        	Har har = server.getHar();
//        	FileOutputStream fos = new FileOutputStream(filename);
//        	har.writeTo(fos);
//        	server.stop();
//
//        	// Browser Close
//		//driver.quit();
//
//		//*******************Try Block***********************
//		try {
//			System.out.println("Reading " + filename);
//			HarLog log = readhar.readHarFile(f);
//
//			// Access all elements as objects
//			HarBrowser browser = log.getBrowser();
//			HarEntries entries = log.getEntries();
//
//			// Used for loops
//			List<HarPage> pages = log.getPages().getPages();
//			List<HarEntry> hentry = entries.getEntries();
//
//			for (HarPage page : pages) {
//				System.out.println("page start time: " + ISO8601DateFormatter.format(page.getStartedDateTime()));
//				System.out.println("page id: " + page.getId());
//				System.out.println("page title: " + page.getTitle());
//			}
//
//			// Output "response" code of entries.
//			for (HarEntry entry : hentry)
//				{
//				System.out.println("request code:  "+ entry.getRequest().getMethod()); // Output request// type
//				System.out.println("response code: "+ entry.getRequest().getUrl()); // Output url of request
//				System.out.println("response code: "+ entry.getResponse().getStatus()); // Output the
//			}
//
//			// Once you are done manipulating the objects, write back to a file
//			System.out.println("Writing " + "fileName1" + ".test");
//
//			File f2 = new File(filename1);
//
//			writehar.writeHarFile(log, f2);
//
//			String line = "";
//
//			ArrayList<String> outputUTME = new ArrayList<String>();
//
//			//File file = new File(workingDir+"\\PerformanceTestHar.txt");
//
//			BufferedReader br = new BufferedReader(new FileReader(filename1));
//			while ((line = br.readLine()) != null)
//			{
//				if (line.contains("\"url\""))
//				{
//					if (line.indexOf("utme") != -1)
//					{
//						outputUTME.add(line.substring(line.indexOf("utme"),line.indexOf("&utmcs")));
//
//					}
//				}
//
//			}
//
//			ReadingHar write = new ReadingHar();
//			write.writeCellDataInExistingXLS(workingDir + "\\GA_Excel_Execution.xls", 0, 1, 1,outputUTME.toString());
//
//
//
//		}
//		 catch (JsonParseException e)
//			{
//
//				e.printStackTrace();    // fail("Parsing error during test");
//			}
//		 catch (IOException e)
//			{
//				e.printStackTrace();   // fail("IO exception during test");
//			}
//
//	}
//}
