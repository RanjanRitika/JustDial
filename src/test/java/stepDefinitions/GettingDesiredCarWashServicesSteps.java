package stepDefinitions;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.FreeListingPage;
import pageObjects.JustDialHome;
import pageObjects.SearchResultPage;
import testBase.BaseClass;

public class GettingDesiredCarWashServicesSteps {
	static WebDriver driver;
	static Logger logger;
	static Properties p;
	static JustDialHome jh;
	
	@BeforeAll
	public static void driverSetup() throws IOException {
		BaseClass setupdriver = new BaseClass();
		setupdriver.setup("os","chrome");
		
		driver = BaseClass.getDriver();
	    p = BaseClass.getProperties();
	    logger = BaseClass.getLogger();
	    
	    ChromeOptions cOption = new ChromeOptions();
	    cOption.addArguments("--disable-blink-features=AutomationControlled");
	    cOption.addArguments("--remote-allow-origins=*");
	    
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
	    //navigating to the JustDial home page
	    driver.get(p.getProperty("appURL"));	
	}
	
	@Given("the user is on Just Dial home page")
	public void the_user_is_on_just_dial_home_page() throws IOException {		
	    driver.manage().window().maximize();
	}

	@When("the sign-up pop-up shows the user clicks on Maybe later")
	public void the_sign_up_pop_up_shows_the_user_clicks_on_Maybe_later() {
	    // Write code here that turns the phrase above into concrete actions
		jh = new JustDialHome(BaseClass.getDriver());
		try {
			new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Maybe Later']")));
			jh.closePopup();
			Thread.sleep(1000);
		} catch (NoSuchElementException e) {
			System.out.println("The login pop-up was not displayed");			
		} catch(Exception e) {
			System.out.println("Error closing the pop up");
		}
		
		logger.info("Closing pop-up");
	}

	@Then("the user should be re-directed to the Just Dial home page")
	public void the_user_should_be_re_directed_to_the_just_dial_home_page() {
	    // Write code here that turns the phrase above into concrete actions
		jh.getCategorySearchBox().isDisplayed();
	}

	@When("the search info is typed")
	public void the_search_info_is_typed() {
	    // Write code here that turns the phrase above into concrete actions
		logger.info("Detecting the current location");
		jh.clickLocationSearchBox();
		jh.clickDetectLocation();
		logger.info("Searching Car Washing Services");
		jh.getCategorySearchBox().sendKeys("Car Washing Services");
	}

	@When("the search button is clicked")
	public void the_search_button_is_clicked() {
	    // Write code here that turns the phrase above into concrete actions
		jh.clickSearchButton();
	}

	@Then("the user should be re-directed to the search page")
	public void the_user_should_be_re_directed_to_the_search_page() {
	    // Write code here that turns the phrase above into concrete actions
		try {
			new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='jsx-3349e7cd87e12d75 title_anchor_tag']")));
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println("Error loading the search results");
		}
	}
	
	@Given("the user is on the search page")
	public void the_user_is_on_the_search_page() throws Exception{
		try {
			new WebDriverWait(BaseClass.getDriver(), Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='jsx-3349e7cd87e12d75 title_anchor_tag']")));
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println("Error loading the search results");
		}
	}
	
	@When("the filters have loaded")
	public void the_filters_have_loaded() throws Exception{
		try {
			new WebDriverWait(BaseClass.getDriver(),Duration.ofSeconds(30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Sort By')]")));	
		} catch(Exception e) {
			System.out.println("Error loading the filters");
		}
		
	}
	
    @Then("apply the filters")
    public void apply_the_filters() throws Exception{
    	SearchResultPage sr = new SearchResultPage(BaseClass.driver);
    	sr.clickSortBy();
		sr.clickSortByRating();
		sr.clickFilterRatings();
		sr.clickSetRatingLimit();
		sr.clickTopRated();
    }
    
  @Given("the results have loaded")
  public void the_results_have_loaded() throws Exception{
  	try {
			new WebDriverWait(BaseClass.getDriver(),Duration.ofSeconds(30)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='jsx-3349e7cd87e12d75 resultbox_title_anchor  line_clamp_1']")));	
		} catch(Exception e) {
			System.out.println("Error loading the filters");
		}
  }
  
  @Then("get name, phone number and rating")
  public void get_name_phone_number_and_rating() throws Exception{
  	SearchResultPage sr = new SearchResultPage(BaseClass.driver);
  	//int index = 1;
		for (int i = 0; i < 5; i++) {
			List<WebElement> results = sr.getResults(i);
			List<WebElement> ratings = sr.getRatings(i);
			System.out.println((i + 1) + ". " + results.get(i).getText());
			String rating = ratings.get(i).getText();
			System.out.println("Number of ratings: " + rating);
			
			Thread.sleep(5000);
			if (rating.contains(",")) {
				String ratingWithoutComma = rating.replace(",", "");
				String[] ratingArr = ratingWithoutComma.split(" ");
				int ratingNum = Integer.parseInt(ratingArr[0]);
				if (ratingNum > 20) {
					System.out.println(ratingNum + " : pass");
				} else {
					System.out.println("Fail");
				}
			} else {
   			String[] ratingArr = rating.split(" ");
				int ratingNum = Integer.parseInt(ratingArr[0]);
				if (ratingNum > 20) {
					System.out.println(ratingNum + " : pass");
				} else {
					System.out.println("Fail");
				}
			}

			JavascriptExecutor js = (JavascriptExecutor) BaseClass.driver;
			js.executeScript("window.scrollBy(0,70);");
			sr.showNumber(i);
			Thread.sleep(3000);

			System.out.println("Phone number: " + sr.getNumber(i));		
		}
  }
  
  @Given("the user is on the free listing page")
	public void the_user_is_on_the_free_listing_page() {
		SearchResultPage sr = new SearchResultPage(BaseClass.driver);
		sr.clickFreeListing();
	}
	
  @When("the invalid number is entered")
  public void the_invalid_number_is_entered() {
  	FreeListingPage fl = new FreeListingPage(BaseClass.driver);
  	fl.getMobileNum().sendKeys(p.getProperty("invalidMobNum"));
  }
	
  @And("the start now button is clicked")
  public void the_start_now_button_is_clicked() {
  	FreeListingPage fl = new FreeListingPage(BaseClass.driver);
  	fl.clickStartNow();
  }
  
  @Then("error message is displayed and captured")
  public void error_message_is_displayed_and_captured() {
  	FreeListingPage fl = new FreeListingPage(BaseClass.driver);
  	System.out.println();
  	String errorMsg = fl.getErrorMsg();
  	System.out.println(errorMsg);
  }
  
  @Given("the user is back on Just Dial home page")
  public void the_user_is_back_on_Just_Dial_home_page() {
	FreeListingPage fl = new FreeListingPage(driver);
	fl.clickJustDial();
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
  }
  
  @When("the Gym option is clicked")
  public void the_Gym_option_is_clicked() throws InterruptedException {
	  JavascriptExecutor jse = (JavascriptExecutor) driver;
	  JustDialHome jd = new JustDialHome(driver);
	  Thread.sleep(3000);
	  jse.executeScript("arguments[0].scrollIntoView(true);", jd.getGym());
	  Thread.sleep(2000);
	  jse.executeScript("arguments[0].click();", jd.getGym());
  }
  
  @And("the sub menu items are visible")
  public void the_sub_menu_items_are_visible() {
	  //SearchResultPage sr = new SearchResultPage(driver);
	  try {
			new WebDriverWait(BaseClass.getDriver(),Duration.ofSeconds(30)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='jsx-6ab5af3a8693e5db font15 fw500 mr-6']")));	
		} catch(Exception e) {
			System.out.println("Error loading the sub menu items");
		}
	  
  }
  
  @Then("printing out the sub menu items")
  public void printing_out_the_sub_menu_items() throws Exception {
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		SearchResultPage sr = new SearchResultPage(driver);
		List<WebElement> list = sr.getListItems();
		System.out.println();
		System.out.println("The sub-menu items are as follows: ");
		Thread.sleep(5000);
		for (int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + ". " + list.get(i).getText());
		}
  }
  
  @AfterAll
  public static void tearDown() {
	  driver.close();
  }
}