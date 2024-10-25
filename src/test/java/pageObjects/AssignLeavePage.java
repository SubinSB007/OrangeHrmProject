package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AssignLeavePage {
	WebDriver driver;
	
	public AssignLeavePage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//input[@placeholder=\"Type for hints...\"]") WebElement employeeSearch_loc;
	@FindBy(xpath="//div[@class=\"oxd-autocomplete-option\"]") WebElement employeeAutoSugg_loc;
	@FindBy(xpath="//div[text()=\"-- Select --\"]") WebElement leaveType_loc;
	@FindBy(xpath="//div[@class=\"oxd-select-option\"]//span") List<WebElement> leavetypeOptions_loc;
	@FindBy(xpath = "//div[@class=\"oxd-select-text-input\"]") WebElement selectedOption_loc;
	
	public void employeeSearch(String name) throws InterruptedException {
		
		employeeSearch_loc.sendKeys(name);
	}
	public String autoSuggest() throws InterruptedException {
		
		String option=employeeAutoSugg_loc.getText();
		return option;
	}
	public void leaveType() {
		leaveType_loc.click();
	}
	public String leaveTypeSelect(String option) throws InterruptedException {
		for(WebElement opt:leavetypeOptions_loc) {
			if(opt.getText().equals(option)) {
				opt.click();
				break;
			}
		}
		
		try{ 
			return selectedOption_loc.getText(); //rrtutn the option if it is thers
		}catch(Exception e) {
			return e.getMessage(); //this will retun exceptionm
		}
		
	}

}
