package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashBoardPage {
	WebDriver driver;
	
	public DashBoardPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//button[@title=\"Assign Leave\"]") WebElement Assignleavebtn_loc;
	@FindBy(xpath = "//p[@class=\"oxd-userdropdown-name\"]") WebElement userDropdown_loc;
	@FindBy(xpath="//a[text()=\"Logout\"]") WebElement logout_loc;
	@FindBy(xpath="//h6[text()=\"Dashboard\"]") WebElement dashboardTxt_loc;
	
	public void leaveBtnAct() throws InterruptedException {
		
		Assignleavebtn_loc.click();
	}
	public String currentUrl() {
		String url=driver.getCurrentUrl();
		return url;
	}
	public void userDropDown() {
		userDropdown_loc.click();
	}
	public void logout() {
		logout_loc.click();
	}
	public boolean isDashboardExist() {
		boolean status=dashboardTxt_loc.isDisplayed();
		return status;
	}
}

