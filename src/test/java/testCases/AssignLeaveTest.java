package testCases;

import java.io.IOException;
import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AssignLeavePage;
import pageObjects.DashBoardPage;
import pageObjects.LoginPage;

public class AssignLeaveTest extends TestBase{
	LoginPage LP;
	DashBoardPage DP;
	AssignLeavePage ALP;
	

	@Test(priority=1,groups= {"Sanity","Master"})
	public void verifyAssignLeavebtn() throws InterruptedException, IOException {
		LP=new LoginPage(driver); //adding groups in before class doesn't work
		LP.usernameAct("Admin");
		LP.passwordAct("admin123");
		LP.loginBtnAct();
		readProp();
		DP=new DashBoardPage(driver);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		DP.leaveBtnAct();
		String actual_url=DP.currentUrl();
		String expected_url =prop.getProperty("assignleaveUrl");
		Assert.assertEquals(expected_url,actual_url );
		
		}
	@Test(priority=2,dependsOnMethods = {"verifyAssignLeavebtn"},groups= {"Master"})
	public void verifyInvalidEmployeename() throws InterruptedException, IOException {

		readProp();
		ALP=new AssignLeavePage(driver);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		ALP.employeeSearch(prop.getProperty("employeeSearch"));
		Thread.sleep(2000);
		String actual_option=ALP.autoSuggest();
		String expected_option=prop.getProperty("noRecordfound");
		Assert.assertEquals(expected_option,actual_option);
		
		}
	
	@Test(priority=3,dependsOnMethods = {"verifyAssignLeavebtn"},groups= {"Master"})
	public void verifyLeaveTypeOption() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		ALP.leaveType();
	   String actual_option= ALP.leaveTypeSelect(prop.getProperty("leaveType"));
	   Assert.assertEquals(prop.getProperty("leaveType"), actual_option);
	    
	}

}
