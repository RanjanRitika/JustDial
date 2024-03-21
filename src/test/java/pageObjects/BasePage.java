package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

//Base Page to instantiate the constructor for driver
public class BasePage {
              public static  WebDriver driver;   

              public BasePage(WebDriver driver) {
                             BasePage.driver = driver;
                             PageFactory.initElements(driver,this); //initializes the web elements in POM
              }
}