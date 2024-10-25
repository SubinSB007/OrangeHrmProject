package testCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

public class TestBase {
//	public static WebDriver driver; //use it when in extent report we use screenshot while failing
									//while crossbrowser testing and parallel testing static webdriver cause problems 
									//so we don't use it
	public  WebDriver driver; 
	public Properties prop;
	public Logger logger;
	
	public void readProp() throws IOException {
		prop=new Properties();
		FileInputStream fileIn=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\config.properties");
	   prop.load(fileIn);
	}
	
	@BeforeClass(groups= {"Sanity","Master","Datadriven","Regression"})
	@Parameters({"Browser","OS"})
//	public void testSetUp(String br,String os) throws InterruptedException, IOException {
	public void testSetUp(String br,String os) throws InterruptedException, IOException { //get the browser from testng xml file
		logger=LogManager.getLogger(this.getClass());
		readProp();
		//String browser=prop.getProperty("browser");
		
		//Remote execution
		if (prop.getProperty("execution_env").equalsIgnoreCase("remote")) {
		    DesiredCapabilities capabilities = new DesiredCapabilities();

		    // os
		    if (os.equalsIgnoreCase("windows")) {
		        capabilities.setPlatform(Platform.WIN10);
		    } else if (os.equalsIgnoreCase("mac")) {
		        capabilities.setPlatform(Platform.MAC);
		    } else if(os.equalsIgnoreCase("Linux")) {
		    	capabilities.setPlatform(Platform.LINUX);
		    }else {
		        System.out.println("No matching os");
		        return;
		    }

		    // browser
		    switch (br.toLowerCase()) {
		        case "chrome":
		            capabilities.setBrowserName("chrome");
		            break;
		        case "edge":
		            capabilities.setBrowserName("MicrosoftEdge");
		            break;
		        case "firefox":
		        	capabilities.setBrowserName("firefox");
		        break;
		        default:
		            System.out.println("No matching browser");
		            return;
		    }

		    driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
		}
		
		//Local Execution
		if (prop.getProperty("execution_env").equalsIgnoreCase("local")) {

		switch(br.toLowerCase()){
			case "chrome":
				driver=new ChromeDriver();
				break;
			case "firefox":
				driver=new FirefoxDriver();
				break;
			case "edge":
				driver=new EdgeDriver();
				break;
			default:
				System.out.println("Invalid browser name");
		}
		}
		driver.manage().window().maximize();
		driver.get(prop.getProperty("url"));
		Thread.sleep(5000);
	}
	
	
	@AfterClass(groups= {"Sanity","Master","Datadriven","Regression"})
	public void tearDown() {
		driver.quit();
	}

	public String captureScreen(String tname) {
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;

		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp+ ".png";

		File targetFile = new File(targetFilePath);
		sourceFile.renameTo(targetFile);

	 return targetFilePath;
	}

}
