package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;

public class SearchResultPage extends BasePage {

	public SearchResultPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[@class='jsx-3349e7cd87e12d75 title_anchor_tag']")
	List<WebElement> titleAnchor;

	@FindBy(xpath = "//div[contains(text(),'Sort By')]")
	WebElement sortBy;

	@FindBy(xpath = "//span[normalize-space()='Rating']")
	WebElement sortByRating;

	@FindBy(xpath = "//div[contains(text(),'Ratings')]")
	WebElement filterRatings;

	@FindBy(xpath = "//span[normalize-space()='4.0+']")
	WebElement setRatingLimit;

	@FindBy(xpath = "//div[contains(text(),'Top Rated')]")
	WebElement topRated;

	@FindBy(xpath = "//div[@class='jsx-3349e7cd87e12d75 resultbox_title_anchor  line_clamp_1']")
	List<WebElement> results;

	@FindBy(xpath = "//div[@class='jsx-3349e7cd87e12d75 resultbox_countrate ml-12 mr-12 font14 fw400 color777']")
	List<WebElement> ratings;

	@FindBy(xpath = "//div[@class='jsx-3349e7cd87e12d75 button_flare']")
	List<WebElement> showNums;

	@FindBy(xpath = "//span[@class='jsx-3349e7cd87e12d75 callcontent']")
	List<WebElement> phoneNums;

	@FindBy(xpath = "//li[@id='header_freelisting']")
	WebElement freeListing;

	@FindBy(xpath = "//div[@class='jsx-6ab5af3a8693e5db font15 fw500 mr-6']")
	List<WebElement> listItems;

	public List<WebElement> getTitleAnchor() {
		return titleAnchor;
	}
	
	public void clickSortBy() {
		sortBy.click();
	}

	public void clickSortByRating() {
		sortByRating.click();
	}

	public void clickFilterRatings() {
		filterRatings.click();
	}

	public void clickSetRatingLimit() {
		setRatingLimit.click();
	}

	public void clickTopRated() {
		topRated.click();
	}

	public List<WebElement> getResults(int n) {
		return results;
	}

	public List<WebElement> getRatings(int n) {
		return ratings;
	}

	public void showNumber(int n) {
		showNums.get(n).click();
	}

	public String getNumber(int n) {
		return phoneNums.get(n).getText();
	}

	public void clickFreeListing() {
		freeListing.click();
	}

	public List<WebElement> getListItems() {
		return listItems;
	}
}