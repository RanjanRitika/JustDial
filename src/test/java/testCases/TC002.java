package testCases;

import pageObjects.FreeListingPage;
import pageObjects.SearchResultPage;
import org.testng.annotations.Test;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import testBase.BaseClass;

public class TC002 extends BaseClass {
	
	@Test(priority=1, groups= {"sanity"})
	public void clickOnFreeListing() {
		//Clicking on free listing 
		logger.info("Starting TC002");
		SearchResultPage sr = new SearchResultPage(driver);
		logger.info("Clicking on Free Listing");
		sr.clickFreeListing();
	}
	
	@Test(priority=2, groups= {"sanity"})
	public void verifyWhetherOnFreeListing() {
		//wait till the title is loaded
		new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.titleIs("Unlock Business Growth | Get Listed for FREE on Justdial"));
		FreeListingPage fl = new FreeListingPage(driver);
		//verify whether on the free listing page
		String title = "Unlock Business Growth | Get Listed for FREE on Justdial";
		Assert.assertEquals(fl.getTitle(), title);
	}
	
	@Test(priority=3, groups= {"sanity"})
	public void enterInvalidNum() {
		//entering the invalid phone number
		FreeListingPage fl = new FreeListingPage(driver);
		logger.info("Entering invalid info and getting error message");
		fl.getMobileNum().sendKeys(p.getProperty("invalidMobNum"));
	}
	
	@Test(priority=4, groups= {"sanity"})
	public void verifyWhetherNumberIsTyped() {
		//verifying whether the required number is typed
		FreeListingPage fl = new FreeListingPage(driver);
		Assert.assertEquals(p.getProperty("invalidMobNum"), fl.getMobileNum().getAttribute("value"));
	}
	
	@Test(priority=5, groups= {"sanity"})
	public void clickStartNow() {
		//clicking on start now
		FreeListingPage fl = new FreeListingPage(driver);
		fl.clickStartNow();
	}
	
	@Test(priority=6, groups= {"sanity"})
	public void getErrorMsg() {
		//getting the error message
		FreeListingPage fl = new FreeListingPage(driver);
		System.out.println();
		System.out.println(fl.getErrorMsg());
		logger.info("Finished TC002");
		
	}
}