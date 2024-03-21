package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;

public class BaseClass {
	static ChromeOptions cOption = new ChromeOptions(); 	// to customize and configure a ChromeDriver session
	static EdgeOptions eOption = new EdgeOptions();		// to customize and configure a EdgeDriver session
	public static WebDriver driver;
	public static Logger logger;
	public static Properties p;

	@BeforeTest(groups = {"sanity", "regression", "master"})
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
		
		//Loading properties file
		try {
	    	   FileInputStream file = new FileInputStream(".//src/test/resources/config.properties");
	    	   p.load(file);
	    	   logger.info("Loaded properties file");
	    	   //file.close();
	    	   } catch(FileNotFoundException e){
	    	   logger.error("config.properties not found");
	    	   } catch(IOException e) {
	    	   logger.error("Unable to open config.properties");
	       }

		//starting of grid
		if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();	//allows testing in different environments
			// os
			if (os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			} else if (os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			} else {
				System.out.println("no matching os!");
				return;
			}
			// browser
			if (br.equalsIgnoreCase("edge")) {
				eOption.addArguments("--disable-blink-features=AutomationControlled");	//prevents selenium web driver/automation detection
				eOption.addArguments("--disable-notifications");	//disables incoming browser notifications
				capabilities.setBrowserName("MicrosoftEdge");
				capabilities.setCapability(EdgeOptions.CAPABILITY, eOption);
			} else if (br.equalsIgnoreCase("chrome")) {
				cOption.addArguments("--disable-blink-features=AutomationControlled");
				cOption.addArguments("--disable-notifications");
				capabilities.setBrowserName("chrome");
				capabilities.setCapability(ChromeOptions.CAPABILITY, cOption);
			} else {
				System.out.println("no matching browser!");
				return;
			}
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub") , capabilities); 	//specifying the hub url and capabilities of the grid environment
		} else {
			if (br.equalsIgnoreCase("edge")) {
				eOption.addArguments("--disable-blink-features=AutomationControlled");
				eOption.addArguments("--disable-notifications");
				driver = new EdgeDriver(eOption);
			} else {
				cOption.addArguments("--disable-blink-features=AutomationControlled");
				cOption.addArguments("--disable-notifications");
				driver = new ChromeDriver(cOption);
			}
		}
		//ending of grid
    
        //opening the web page
       driver.manage().window().maximize();
       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));     
       driver.get(p.getProperty("appURL"));
       driver.manage().deleteAllCookies();    
}

	@AfterTest(groups = {"sanity", "regression", "master"})
	public void tearDown() {
		driver.quit();
	}

	//Screenshot for Extent Reports
	public String captureScreen(String tname) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot)driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\"+tname+"_"+timeStamp;
		File targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;
	}
	
	//get methods for returning static variables used in cucumber steps
	public static WebDriver getDriver() {
		return driver;
	}
	
	public static Logger getLogger() {
		return logger;
	}
	
	public static Properties getProperties() throws IOException {
		return p;
	}

}