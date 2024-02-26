package testCases;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pageObjects.FreeListingPage;
import pageObjects.JustDialHome;
import pageObjects.SearchResultPage;
import testBase.BaseClass;
import utilities.ExcelUtility;

public class TC003 extends BaseClass {

	@Test(priority = 1)
	public void searchGyms() throws InterruptedException {
		logger.info("Starting TC003");
		logger.info("Navigating back to the home page");
		FreeListingPage fl = new FreeListingPage(driver);
		fl.clickJustDial();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		JustDialHome jd = new JustDialHome(driver);
		Thread.sleep(3000);
		jse.executeScript("arguments[0].scrollIntoView(true);", jd.getGym());
		Thread.sleep(2000);
		jse.executeScript("arguments[0].click();", jd.getGym());
	}

	@Test(priority = 2)
	public void subMenuItems() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
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