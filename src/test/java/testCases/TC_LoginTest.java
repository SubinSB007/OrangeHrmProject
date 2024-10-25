package testCases;

import java.io.IOException;
import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.DashBoardPage;
import pageObjects.LoginPage;
import utilities.DataProviderDP;

public class TC_LoginTest extends TestBase{
	LoginPage LP;
	DashBoardPage DP;
	
	
	@Test(priority=1,groups= {"Master","Sanity","Regression"})
	public void testLogin() throws InterruptedException, IOException {
		logger.info("***Starting positive login test case***");
		readProp();
		LP=new LoginPage(driver);
		DP=new DashBoardPage(driver);
		   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		   logger.info("Entering  valid username and password");
		LP.usernameAct(prop.getProperty("username"));
		LP.passwordAct(prop.getProperty("password"));
		logger.info("Clicked on login button");
		LP.loginBtnAct();
		DP.userDropDown();
		DP.logout();
		
		 
	}
	
   @Test(priority=2,dataProvider="dp",dataProviderClass =DataProviderDP.class,groups= {"Master","Datadriven"} )
   public void testLoginNegative(String username,String password) throws InterruptedException, IOException {
	  logger.info("***Starting negative test case***");
	   readProp();
	   LP=new LoginPage(driver);
	   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	   logger.info("Entering  invalid username and password");
	   LP.usernameAct(username);
	   LP.passwordAct(password);
	   logger.info("Clicking login button");
	   LP.loginBtnAct();
	   String invalidcred=LP.invalidCredentials();
	   Assert.assertEquals(prop.getProperty("invalidcred"), invalidcred);
   }

}
