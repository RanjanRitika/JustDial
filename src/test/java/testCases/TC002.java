package testCases;

import pageObjects.FreeListingPage;
import pageObjects.SearchResultPage;
import org.testng.annotations.Test;
import org.testng.Assert;
import testBase.BaseClass;

public class TC002 extends BaseClass {
	@Test
	public void verifyErrorMsg() {
		logger.info("Starting TC002");
		SearchResultPage sr = new SearchResultPage(driver);
		logger.info("Clicking on Free Listing");
		sr.clickFreeListing();
		FreeListingPage fl = new FreeListingPage(driver);
		logger.info("Entering invalid info and getting error message");
		fl.getMobileNum().sendKeys(p.getProperty("invalidMobNum"));
		fl.clickStartNow();
		System.out.println();
		System.out.println(fl.getErrorMsg());
		logger.info("Finished TC002");
		if(!fl.getRegistrationSuccessDialog().isDisplayed()) {
			Assert.assertEquals(fl.getErrorMsg(), "");
		}
	}
}