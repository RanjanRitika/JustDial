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

	@Test(priority = 1)
	public void searchForContent() {
		logger.info("Starting TC001");
		JustDialHome jh = new JustDialHome(driver);
		jh.closePopup();
		logger.info("Closing pop-up");
		logger.info("Detecting the current location");
		jh.clickLocationSearchBox();
		//jh.enterLocation("Egattur, Chennai");
		jh.clickDetectLocation();
		logger.info("Searching Car Washing Services");
		String categorySearchBoxInput = "Car Washing Services";
		jh.getCategorySearchBox().sendKeys(categorySearchBoxInput);
		Assert.assertEquals(categorySearchBoxInput, jh.getCategorySearchBox().getAttribute("value"));
		jh.clickSearchButton();
		
	}

	@Test(priority = 2)
	public void applyingFilters() {
		SearchResultPage sr = new SearchResultPage(driver);
		try {
			new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='jsx-3349e7cd87e12d75 title_anchor_tag']")));
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println("Error loading the search results");
		}
		logger.info("Sorting using filters");
		sr.clickSortBy();
		sr.clickSortByRating();
		sr.clickFilterRatings();
		sr.clickSetRatingLimit();
		sr.clickTopRated();
	}

	@Test(priority = 3)
	public void extractDetails() throws InterruptedException {
		SearchResultPage sr = new SearchResultPage(driver);
		logger.info("Printing out names, number of ratings and phone numbers");
		int index = 1;
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

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,70);");
			sr.showNumber(i);
			Thread.sleep(3000);
			String phoneNum = sr.getNumber(i);
			Thread.sleep(3000);

			System.out.println("Phone number: " + phoneNum);

			String data[] = new String[3];
			data[0] = results.get(i).getText();
			data[1] = rating;
			data[2] = sr.getNumber(i);

			// storing in excel
			ExcelUtility.writeRow(data, index++, p.getProperty("excelFile"), p.getProperty("resultSheet"));
		}
		logger.info("Finished TC001");
	}
}