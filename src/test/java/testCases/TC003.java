package testCases;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pageObjects.FreeListingPage;
import pageObjects.JustDialHome;
import pageObjects.SearchResultPage;
import testBase.BaseClass;
import utilities.ExcelUtility;

public class TC003 extends BaseClass {
	
	@Test(priority = 1, groups= {"sanity"})
	public void navigateBackToHomePage() {
		//going back to the just dial home page
		logger.info("Starting TC003");
		logger.info("Navigating back to the home page");
		FreeListingPage fl = new FreeListingPage(driver);
		fl.clickJustDial();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
	}

	@Test(priority = 2, groups= {"sanity"})
	public void scrollToGyms() throws InterruptedException {
//		logger.info("Starting TC003");
//		logger.info("Navigating back to the home page");
//		FreeListingPage fl = new FreeListingPage(driver);
//		fl.clickJustDial();
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		//scroll to gyms
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		JustDialHome jd = new JustDialHome(driver);
		Thread.sleep(3000);
		jse.executeScript("arguments[0].scrollIntoView(true);", jd.getGym());
//		Thread.sleep(2000);
//		jse.executeScript("arguments[0].click();", jd.getGym());
	}
	
	@Test(priority = 3, groups= {"sanity"})
	public static void clickOnGyms() throws InterruptedException {
		//clicking on gym
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		
		JustDialHome jd = new JustDialHome(driver);
		Thread.sleep(2000);
		jse.executeScript("arguments[0].click();", jd.getGym());
	}

	@Test(priority = 4, groups= {"sanity"})
	public void subMenuItems() throws InterruptedException {
		//getting the sub-menu options
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		new WebDriverWait(driver, Duration.ofSeconds(90)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='jsx-6ab5af3a8693e5db font15 fw500 mr-6']")));
		SearchResultPage sr = new SearchResultPage(driver);
		List<WebElement> list = sr.getListItems();
		System.out.println();
		System.out.println("The sub-menu items are as follows: ");
		Thread.sleep(5000);
		String data[] = new String[list.size()];
		int index = 0;
		for (int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + ". " + list.get(i).getText());
			data[index++] = list.get(i).getText();
		}

		// storing in excel
		ExcelUtility.writeColumn(data, 0, p.getProperty("excelFile"), p.getProperty("subMenuSheet"));
		Thread.sleep(5000);
	}
}