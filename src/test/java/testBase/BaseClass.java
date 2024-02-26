package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;

public class BaseClass {
	static ChromeOptions cOption = new ChromeOptions();
	static EdgeOptions eOption = new EdgeOptions();
	public static WebDriver driver;
	public static Logger logger;
	public static Properties p;

	@BeforeTest
	@Parameters({ "os", "browser" })
	public void setup(String os, String br) throws IOException {
		// loading properties file
		p = new Properties();
		
		// loading log4j
		logger = LogManager.getLogger(this.getClass());
		cOption.addArguments("--disable-blink-features=AutomationControlled");
		eOption.addArguments("--disable-blink-features=AutomationControlled");
		cOption.addArguments("--remote-allow-origins=*");
		eOption.addArguments("--remote-allow-origins=*");

        //launching browser based on condition
        switch(br.toLowerCase()) {
        case "chrome":
                      driver = new ChromeDriver(cOption);
                      break;
        case "edge":
                      driver = new EdgeDriver(eOption);
                      break;
        default:
                      System.out.println("No matching browser");
                      return;
        }
    
       driver.manage().window().maximize();
       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
             
       try {
    	   FileInputStream file = new FileInputStream(".//src/test/resources/config.properties");
    	   p.load(file);
    	   logger.info("Loaded properties file");
    	   file.close();
    	   } catch(FileNotFoundException e){
    	   logger.error("config.properties not found");
    	   } catch(IOException e) {
    	   logger.error("Unable to open config.properties");
       }
       
       driver.get(p.getProperty("appURL"));
       driver.manage().deleteAllCookies();
}
	

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

	public String captureScreen(String tname) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot)driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\"+tname+"_"+timeStamp;
		File targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;
	}
	
	//initialize browser for cucumber
//	public static void initializeDriver() {
//		if(driver == null) {
//			p=new Properties();
//			logger = LogManager.getLogger();
//			try {
//				FileReader file = new FileReader(".//src//test//resources//config.properties");
//				p.load(file);
//				logger.info("Loaded properties file");
//				file.close();
//				} catch(FileNotFoundException e) {
//				logger.info("config.properties not found");
//			} catch(IOException e) {
//				logger.info("Cannot open config.properties");
//			}
//			
//			switch(p.getProperty("cucumberTestBrowser").toLowerCase()) {
//			case "chrome":
//				driver = new ChromeDriver(cOption);
//				break;
//			case "edge":
//				driver = new EdgeDriver(eOption);
//				break;
//			default:
//				logger.error("Browser not found");
//				return;
//			} 
//			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//			driver.manage().window().maximize();
//		}
//	}
	
	
	//get methods for returning static variables used in cucumber steps
	public static WebDriver getDriver() {
		return driver;
	}
	
	public static Logger getLogger() {
		return logger;
	}
	
	public static Properties getProperties() throws IOException {
//		FileReader file = new FileReader(".//src/test/resources/config.properties");
//		p = new Properties();
//		p.load(file);
		return p;
	}

}