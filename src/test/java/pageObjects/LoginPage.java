package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath ="//input[@name=\"username\"]") WebElement username_loc;
	@FindBy(xpath="//input[@name=\"password\"]") WebElement password_loc;
	@FindBy(xpath="//button[@type=\"submit\"]") WebElement loginbtn_loc;
	@FindBy(xpath="//p[@class=\"oxd-text oxd-text--p oxd-alert-content-text\"]") WebElement inavlidCred_loc;
	
	public void usernameAct(String username) {
		username_loc.sendKeys(username);
		
	}
	public void passwordAct(String password) {
		password_loc.sendKeys(password);
	}
	public void loginBtnAct() {
		loginbtn_loc.click();
	}
	public String invalidCredentials() {
		try {
			return inavlidCred_loc.getText(); //return invalid cred message if it displays
		}catch(Exception e) {
			return "An error occured"+e.getMessage(); //return the exception if in valid cred not shows
			
		}
	
	}
	

}

