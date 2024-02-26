package testCases;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import testBase.BaseClass;

public class TC004_endToEndTest extends BaseClass{
static ChromeOptions option = new ChromeOptions();
	@Test
	public static void endToEndTest() throws InterruptedException {
		
		SoftAssert myAssert = new SoftAssert();
		//opening web page
		option.addArguments("--disable-blink-features=AutomationControlled");
		option.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver(option);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.justdial.com/");
		driver.manage().deleteAllCookies();
		
		//waiting for pop-up and closing it
		new WebDriverWait(driver,Duration.ofSeconds(15)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Maybe Later']")));
		WebElement maybeLater = driver.findElement(By.xpath("//span[text()='Maybe Later']"));
		maybeLater.click();
		
		//selecting location and category
		driver.findElement(By.xpath("//div[@class='input_location_box ']")).click();  //select location
		driver.findElement(By.xpath("//li[@class='location_dropitem']")).click();   //detect current location
		driver.findElement(By.id("main-auto")).sendKeys("Car Washing Services");    //type car wash services
		
		//clicking on the search button
		WebElement searchBtn = driver.findElement(By.className("search_button"));
		JavascriptExecutor jse =  (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click()", searchBtn);
		
		//waiting for next page to load
		try {
			new WebDriverWait(driver,Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='jsx-3349e7cd87e12d75 title_anchor_tag']")));
			Thread.sleep(1000);
		}catch(Exception e) {
			System.out.println("error");
		}
		
		//clicking on sort by(highest rating on top)
		driver.findElement(By.xpath("//div[contains(text(),'Sort By')]")).click();
		driver.findElement(By.xpath("//span[normalize-space()='Rating']")).click();
		
		//clicking on 4+ rating
		driver.findElement(By.xpath("//div[contains(text(),'Ratings')]")).click();
		driver.findElement(By.xpath("//span[normalize-space()='4.0+']")).click();
		
		//clicking on top rated
		driver.findElement(By.xpath("//div[contains(text(),'Top Rated')]")).click();
		
		System.out.println("The top 5 Car Washing Services near the current location are as follows:");
		
		//get the names and phone numbers
		for(int i=0; i<5; i++) {
		    List<WebElement> results = driver.findElements(By.xpath("//div[@class='jsx-3349e7cd87e12d75 resultbox_title_anchor  line_clamp_1']"));		//search results
		    List<WebElement> ratings = driver.findElements(By.xpath("//div[@class='jsx-3349e7cd87e12d75 resultbox_countrate ml-12 mr-12 font14 fw400 color777']"));		//list of web elements of ratings
		    System.out.println((i+1)+". "+results.get(i).getText());	
		    String rating = ratings.get(i).getText(); //output -> 125 Ratings
		    System.out.println("Number of ratings: "+rating);
		    String[] ratingArr = rating.split(" "); //output array -> ["125","Ratings"]
		    int ratingNum = Integer.parseInt(ratingArr[0]); //output number -> 125
		    if(ratingNum>20) {
		    	System.out.println(ratingNum + " : pass");
		    } else {
		    	System.out.println("Fail");
		    }
		    JavascriptExecutor js1=(JavascriptExecutor)driver;
			js1.executeScript("window.scrollBy(0,70);");
			//results.get(i).click(); 	//click on search result
			List<WebElement> showNums = driver.findElements(By.xpath("//div[@class='jsx-3349e7cd87e12d75 button_flare']")); //list of web elements of show number
			showNums.get(i).click();
			Thread.sleep(3000);
			List<WebElement> phoneNums = driver.findElements(By.xpath("//span[@class='jsx-3349e7cd87e12d75 callcontent']")); 	//getting phone num
			Thread.sleep(3000);
			System.out.println("Phone num"+(i+1)+": "+phoneNums.get(i).getText());
		}
		
		//TC002
		driver.findElement(By.xpath("//li[@id='header_freelisting']")).click();		//clicking on free listing 
		driver.findElement(By.xpath("//input[@class='entermobilenumber_input__eCrdc input fw500']")).sendKeys("2345678901"); 		//typing the invalid phone number 
		driver.findElement(By.xpath("//button[@class='primarybutton undefined']")).click();	//clicking on the start now button
		String errorMsg = driver.findElement(By.xpath("//span[@class='undefined entermobilenumber_error__text__uPM09']")).getText();
		System.err.println(errorMsg);
		if(driver.findElement(By.xpath("//span[@class='undefined entermobilenumber_error__text__uPM09']")).isDisplayed()) {
			myAssert.assertEquals(errorMsg, "Please Enter a Valid Mobile Number");
		}
		
		//TC003
//		driver.navigate().back();
		driver.findElement(By.xpath("//img[@alt='justdial logo']")).click();
		WebElement gym = driver.findElement(By.xpath("//div[contains(text(),'Gym')]"));
		jse.executeScript("arguments[0].scrollIntoView();", gym);
		jse.executeScript("arguments[0].click();", gym);
		
		List<WebElement> list = driver.findElements(By.xpath("//div[@class='jsx-6ab5af3a8693e5db font15 fw500 mr-6']"));
		for (int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + ". " + list.get(i).getText()); 
		}
		
		driver.quit();
	}
}
