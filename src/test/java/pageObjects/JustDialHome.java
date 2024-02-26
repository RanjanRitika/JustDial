package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class JustDialHome extends BasePage {
	// Constructor for calling the driver
	public JustDialHome(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[text()='Maybe Later']")
	WebElement maybeLater;

	@FindBy(xpath = "//div[@class='input_location_box ']")
	WebElement locationSearchBox;

	@FindBy(xpath = "//li[@class='location_dropitem']")
	WebElement detectCurrentLocation;

	@FindBy(id = "main-auto")
	WebElement categorySearchBox;

	@FindBy(className = "search_button")
	WebElement searchButton;

	@FindBy(xpath = "//div[contains(text(),'Gym')]")
	WebElement gym;

	public void closePopup() {
		maybeLater.isEnabled();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		maybeLater.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clickLocationSearchBox() {
		locationSearchBox.click();
	}

	public void enterLocation(String location) {
		locationSearchBox.sendKeys(location);
	}
	
	public void clickDetectLocation() {
		detectCurrentLocation.click();
	}

	public WebElement getCategorySearchBox() {
		return categorySearchBox;
	}

	public void clickSearchButton() {
		searchButton.click();
	}
	
	public WebElement getGym() {
		return gym;
	}

}
