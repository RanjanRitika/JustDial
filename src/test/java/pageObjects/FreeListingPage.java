package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FreeListingPage extends BasePage{
	public FreeListingPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//input[@class='entermobilenumber_input__eCrdc input fw500']")
    WebElement mobileNum;   

    @FindBy(xpath="//button[@class='primarybutton undefined']")
    WebElement startNow;

    @FindBy(xpath="//span[@class='undefined entermobilenumber_error__text__uPM09']")
    WebElement errorMsg;

    @FindBy(xpath="//img[@alt='justdial logo']")
    WebElement justDial;
    
    @FindBy(xpath="//div[@class='otp_modal__header__right__SZXbm color111']")
    WebElement registrationSuccessDialog;
    
    public WebElement getMobileNum() {
    	return mobileNum;
    }
    
    public void clickStartNow() {
    	startNow.click();
    }

    public String getErrorMsg() {
    	return errorMsg.getText();
    }    

    public void clickJustDial() {
        justDial.click();
    }
    
    public WebElement getRegistrationSuccessDialog() {
    	return registrationSuccessDialog;
    }
}