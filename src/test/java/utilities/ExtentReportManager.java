package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testCases.TestBase;

	public class ExtentReportManager implements ITestListener {
		public ExtentSparkReporter sparkReporter;

		public ExtentReports extent;
		public ExtentTest test;

		String repName;

		public void onStart(ITestContext testContext) {
//			SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
//			Date dt = new Date();
//			String currentdatetimestamp = df.format(dt);
			
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // time stamp

			repName = "Test-Report-" + timeStamp + ".html";

			sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); // specify location of the report

			sparkReporter.config().setDocumentTitle("OrangeHRM automation Report"); // Title of report
			sparkReporter.config().setReportName("OrangeHRM functional Testing"); // name of the report
			sparkReporter.config().setTheme(Theme.DARK);

			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);
			extent.setSystemInfo("Application", "OrangeHRM");
			extent.setSystemInfo("Module", "Login");
			extent.setSystemInfo("Sub Module", "Assign Leave");
			extent.setSystemInfo("User Name", System.getProperty("user.name"));
			extent.setSystemInfo("Environment", "QA");

			String os = testContext.getCurrentXmlTest().getParameter("OS");
			extent.setSystemInfo("Operating System", os);

			String browser = testContext.getCurrentXmlTest().getParameter("Browser");
			extent.setSystemInfo("Browser", browser);

			List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups(); //get the groups of test method
			if (!includedGroups.isEmpty()) {
				extent.setSystemInfo("Groups", includedGroups.toString()); 
			}
		}
		
		public void onTestSuccess(ITestResult result) {
			test = extent.createTest(result.getTestClass().getName());
			test.assignCategory(result.getMethod().getGroups()); // to display groups in report
			test.log(Status.PASS, result.getName() + " got successfully executed");
		}
		
		public void onTestFailure(ITestResult result) {
			test = extent.createTest(result.getTestClass().getName());
			test.assignCategory(result.getMethod().getGroups());

			test.log(Status.FAIL, result.getName() + " got failed");
			test.log(Status.INFO, result.getThrowable().getMessage());
			
			//Capture and store the screenshots of failure
//			try {
//				String imgPath = new TestBase().captureScreen(result.getName()); //creating the object of test base 
//																					//calls the method to capture scrrenshot
//				test.addScreenCaptureFromPath(imgPath);
//			} catch(Exception e1) {
//				e1.printStackTrace();
//			}
		}
	
		public void onTestSkipped(ITestResult result) {
			test = extent.createTest(result.getTestClass().getName());
			test.assignCategory(result.getMethod().getGroups());
			test.log(Status.SKIP, result.getName() + " got skipped");
			test.log(Status.INFO, result.getThrowable().getMessage());
		}
		
		public void onFinish(ITestContext testContext) {
			extent.flush();
			
			//To open the report at the end of test execution dynamically
			String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
			File extentReport = new File(pathOfExtentReport);

			try {
				Desktop.getDesktop().browse(extentReport.toURI());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

