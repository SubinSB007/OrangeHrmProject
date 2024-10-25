package testCases;

import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.DashBoardPage;
import pageObjects.LoginPage;
import utilities.DataProviderDP;

public class TC_001_DataProviderLoginTest extends TestBase{
	LoginPage LP;
	DashBoardPage DP;
	
	@Test(dataProvider="LoginData",dataProviderClass =DataProviderDP.class ,groups={"Master","Regression"} ) //getting data from different class.no need to import package
	public void testLoginData(String username,String password,String type) {
		logger.info("***data driven Login test started***");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		try {
		LP=new LoginPage(driver);
		LP.usernameAct(username);
		LP.passwordAct(password);
		LP.loginBtnAct();
		
		DP=new DashBoardPage(driver);
		String actual_url=DP.currentUrl();
		String expected_url =prop.getProperty("dashboardUrl");
		
		/* Data is valid - login success- pass
		 * 					login failed- fail
		 * 
		 * Data is invalid - login success- fail
		 * 					login failed- pass
		 * */
		
		if(type.equalsIgnoreCase("Valid")) {
			if(expected_url.equals(actual_url)) {
				DP.userDropDown();
				DP.logout();
				Assert.assertTrue(true);
			}else {
				Assert.assertTrue(false);
			}
		}
		if(type.equalsIgnoreCase("Invalid")) {
			if(expected_url.equals(actual_url)) {
				DP.userDropDown();
				DP.logout();
				Assert.assertTrue(false);
			}else {
				Assert.assertEquals(prop.getProperty("invalidcred"),LP.invalidCredentials());
			}
		}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			Assert.fail();
			
		}
		logger.info("***Data drivern login test ended***");
		
	}

}
