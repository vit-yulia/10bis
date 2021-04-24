package tests;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import pages.Login;
import pages.Main;
import pages.Order;
import pages.SearchRestaurant;
import utilites.GetDriver;
import utilites.Utilities;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;


public class ErrorMessageHandling {

	// Global variables 
	// Add extent reports
	private ExtentReports extent;
	private ExtentTest myTest;
	private static String reportPaht = System.getProperty("user.dir") + "\\test-output\\reportSanity.html";

	private WebDriver driver;

	//pages
	private Login login;
	
	private static final Logger logger = LogManager.getLogger(ErrorMessageHandling.class);
	private static String browser;
	private static String baseUrl;
	private static String errorMessage;

	@BeforeClass
	public void beforeClass() throws ParserConfigurationException, SAXException, IOException {
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/log4j.properties");

		extent = new ExtentReports(reportPaht);
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\resources\\extent-config.xml"));
		
		baseUrl = Utilities.getDataFromXML("info.xml", "website", 0);
		browser = Utilities.getDataFromXML("info.xml", "browser", 0);
		errorMessage = Utilities.getDataFromXML("info.xml", "errorMessage", 0);
		driver = GetDriver.getDriver(browser,baseUrl, true);
		login = new Login(driver);
		
	}

	@BeforeMethod
	public void beforeMethod(Method method) throws IOException {
		myTest = extent.startTest(method.getName());
		myTest.log(LogStatus.INFO, "Starting test", "Start test");
	}
	
	/*  Prerequisite: verify error message
	 * 		Given: Client open browser as incognito   
	 * 		When: click on Registration button and choose already have an account and press Login button
	 *  	Then: Verify error message is exist 
	 */
	
	@Test(priority = 1, enabled = true, description = "Verify Error Message ")
	public void verifyErrorMessage() throws InterruptedException, IOException, ParserConfigurationException, SAXException {

		logger.info("Going to connection page as incognito succesfully");
		
		logger.info("Click on Registration button and choose as already an account");
		logger.info("Verify error message is exists");
		Assert.assertTrue(login.verifyErrorMessage(errorMessage), "could not exists error message");
	}
	
	
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {
			myTest.log(LogStatus.FAIL, "Test failed: " + result.getName());
			myTest.log(LogStatus.FAIL, "Test failed reason: " + result.getThrowable());
			myTest.log(LogStatus.FAIL, myTest.addScreenCapture(Utilities.takeScreenShot(driver)));
		}
		else {
			myTest.log(LogStatus.PASS, result.getName(), "Verify successful ");
			myTest.log(LogStatus.PASS, myTest.addScreenCapture(Utilities.takeScreenShot(driver)));

		}

		myTest.log(LogStatus.INFO, "Finish test", "Finish test ");
		extent.endTest(myTest);

	}

	@AfterClass
	public void afterClass() {
		extent.flush();
		extent.close();
		driver.quit();

	}
	
}
