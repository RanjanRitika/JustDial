package testCases;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.JustDialHome;
import pageObjects.SearchResultPage;
import testBase.BaseClass;
import utilities.ExcelUtility;

public class TC001 extends BaseClass {

	@Test(priority = 1, groups= {"sanity"})
	public void closePopUp() {
		logger.info("Starting TC001");
		JustDialHome jh = new JustDialHome(driver);
		//closing the sign-up pop up
		try {
			new WebDriverWait(driver, Duration.ofSeconds(90)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Maybe Later']")));
			Thread.sleep(1000);
			jh.closePopup();
		} catch (Exception e) {
			System.out.println("Error locating the sign-up pop up");
		}
		logger.info("Closing pop-up");
	}
	
	@Test(priority = 2, groups= {"sanity"})
	public void verifyWhetherOnHomePage() {
		//verifying whether at home page or not
		JustDialHome jh = new JustDialHome(driver);
		String title = "Find Businesses Near You on Local Search Engine - Justdial";
		Assert.assertEquals(jh.getTitle(), title);
	}
	
	@Test(priority=3, groups= {"sanity"})
	public void clickLocationSearchBox() {
		JustDialHome jh = new JustDialHome(driver);
		logger.info("Detecting the current location");
		//clicking on the location search box
		jh.clickLocationSearchBox();
	}
	
	@Test(priority=4, groups= {"sanity"})
	public void detectCurrentLocation() {
		JustDialHome jh = new JustDialHome(driver);
		//clicking on the detect location
		new WebDriverWait(driver,Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(jh.getDetectLocation()));
		jh.clickDetectLocation();
		
//		jh.enterLocation("Sirucheri, Chennai");
	}
	
	@Test(priority=5, groups= {"sanity"})
	public void searchCategory() {
		JustDialHome jh = new JustDialHome(driver);
		logger.info("Searching Car Washing Services");
		String categorySearchBoxInput = "Car Washing Services";
		//sending input value to category search box
		jh.getCategorySearchBox().sendKeys(categorySearchBoxInput);
		//Verifying whether correct input typed
		Assert.assertEquals(categorySearchBoxInput, jh.getCategorySearchBox().getAttribute("value"));
	}
	
	@Test(priority=6, groups= {"sanity"})
	public void clickSearch() {
		JustDialHome jh = new JustDialHome(driver);
		//clicking on search button
		jh.clickSearchButton();
	}

	@Test(priority = 7, groups= {"sanity"})
	public void sortingByRating() {
		SearchResultPage sr = new SearchResultPage(driver);
		try {
			new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='jsx-3349e7cd87e12d75 title_anchor_tag']")));
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println("Error loading the search results");
		}
		
		try {
			new WebDriverWait(driver,Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Sort By')]")));
		} catch(Exception e) {
			System.out.println("Error loading the filters");
		}
		
		logger.info("Sorting using filters");
		//clicking on sort by drop down without select class
		sr.clickSortBy();
		//clicking on rating
		sr.clickSortByRating();
	}
	
	@Test(priority=8, groups= {"sanity"})
	public void filterRating() {
		SearchResultPage sr = new SearchResultPage(driver);
		//clicking on Ratings
		sr.clickFilterRatings();
		//clicking on 4.0+
		sr.clickSetRatingLimit();
	}

	@Test(priority=9, groups= {"sanity"})
	public void clickTopRated() {
		SearchResultPage sr = new SearchResultPage(driver);
		//clicking on top rated
		sr.clickTopRated();
	}
	
	
	@Test(priority = 10, groups= {"sanity"})
	public void extractDetails() throws InterruptedException {
		SearchResultPage sr = new SearchResultPage(driver);
		logger.info("Printing out names, number of ratings and phone numbers");
		int index = 1;
		for (int i = 0; i < 5; i++) {
			//getting the list of search results and their respective ratings
			List<WebElement> results = sr.getResults(i);
			List<WebElement> ratings = sr.getRatings(i);
			
			//printing out the names of car washing services displayed
			System.out.println((i + 1) + ". " + results.get(i).getText());
			
			//printing out the number of ratings of corresponding car washing services displayed
			String rating = ratings.get(i).getText();
			System.out.println("Number of ratings: " + rating);

			Thread.sleep(5000);

			//converting the String format number of ratings into integer
			if (rating.contains(",")) {
				String ratingWithoutComma = rating.replace(",", "");     //input: "1,200 Ratings"; output: "1200 Ratings"
				String[] ratingArr = ratingWithoutComma.split(" ");      //input: "1200 Ratings"; output: ["1200", "Ratings"]
				int ratingNum = Integer.parseInt(ratingArr[0]);          //input: ["1200", "Ratings"]; output: 1200     
				if (ratingNum > 20) {
					System.out.println(ratingNum + " : pass");
				} else {
					System.out.println("Fail");
				}
			} else {
     			String[] ratingArr = rating.split(" ");                  //input: "100 Ratings"; output: ["100", "Ratings"]
				int ratingNum = Integer.parseInt(ratingArr[0]);          //input: ["100", "Ratings"]; output: 100
				if (ratingNum > 20) {
					System.out.println(ratingNum + " : pass");
				} else {
					System.out.println("Fail");
				}
			}

			//Printing out the phone numbers
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,70);");
			//clicking on show number
			sr.showNumber(i);
			Thread.sleep(3000);
			
			String data[] = new String[3];
			data[0] = results.get(i).getText();
			data[1] = rating;
								
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			
			try {
				List<WebElement> phoneNumbers = sr.getContactNums();
				String phoneNum = phoneNumbers.get(0).getText();
				System.out.println("Phone number: " + phoneNum);
				data[2] = phoneNum;
				sr.clickCloseContactInfo();
			} catch(Exception e) {
				String phoneNum = sr.getNumber(i);
				Thread.sleep(3000);
				System.out.println("Phone Number: "+phoneNum);
				data[2] = phoneNum;
			}
			
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

			// storing all info in excel
			ExcelUtility.writeRow(data, index++, p.getProperty("excelFile"), p.getProperty("resultSheet"));
		}
		logger.info("Finished TC001");
	}
}