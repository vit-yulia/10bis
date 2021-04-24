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
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;


public class SanityTest {

	// Global variables 
	// Add extent reports
	private ExtentReports extent;
	private ExtentTest myTest;
	private static String reportPaht = System.getProperty("user.dir") + "\\test-output\\reportSanity.html";

	private WebDriver driver;

	//pages
	private Main main;
	private Login login;
	private SearchRestaurant searchRestaurant;
	private Order orders;
	
	private static final Logger logger = LogManager.getLogger(SanityTest.class);
	private static String userName;
	private static String password;
	private static String browser;
	private static String baseUrl;
	private static String hebrewName;
	private static String restaurantName;


	@BeforeClass
	public void beforeClass() throws ParserConfigurationException, SAXException, IOException {
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/log4j.properties");

		extent = new ExtentReports(reportPaht);
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\resources\\extent-config.xml"));
		
		baseUrl = Utilities.getDataFromXML("info.xml", "website", 0);
		browser = Utilities.getDataFromXML("info.xml", "browser", 0);
		userName = Utilities.getDataFromXML("info.xml", "userName", 0);
		password = Utilities.getDataFromXML("info.xml", "password", 0);
		hebrewName = Utilities.getDataFromXML("info.xml", "Hebrewname", 0);
		restaurantName = Utilities.getDataFromXML("info.xml", "restaurantName", 0);
		
		driver = GetDriver.getDriver(browser, baseUrl);
		
		main = new Main(driver);
		login = new Login(driver);
		searchRestaurant = new SearchRestaurant(driver);
		orders = new Order(driver);
		
	}

	
	
	@BeforeMethod
	public void beforeMethod(Method method) throws IOException {
		myTest = extent.startTest(method.getName());
		myTest.log(LogStatus.INFO, "Starting test", "Start test");
	}
	

	
	/*  Prerequisite: getting into https://www.10bis.co.il/
	 * 		Given: Client click connection and  
	 * 		When: give Facebook login details and click login
	 *  	Then: Getting into 10bis as registered user
	 */
	
	@Test(priority = 1, enabled = true, description = "Login 10bis using Facebook")
	public void LoginUsingFacebook() throws InterruptedException, IOException, ParserConfigurationException, SAXException {

		logger.info("Going to connection page");
		
		Assert.assertTrue(main.login(), "could not find login popup");
		logger.info("Going to login with facebook detailse");
		Assert.assertTrue(login.doLoginFacebook(userName, password, hebrewName), "could not login with Facebook account, check logs");
		
		logger.info("Successfully Get into 10bis as registred user page (facebook login)");

	}
	
	
	/*  Prerequisite: Find Restaurant
	 * 		Given: Valid logged in user in 10bis web-site
	 * 		When: The search field filled with restaurant name and search button pressed 
	 *  	Then: Getting into restaurant page
	 */
	
	@Test(dependsOnMethods = { "LoginUsingFacebook" }, priority = 2, enabled = true, description = "find restaurant  as logged in user")
	public void findRestaurant() throws InterruptedException, IOException, ParserConfigurationException, SAXException {

		logger.info("Check If search field exists");
		Assert.assertTrue(searchRestaurant.checkIfSearchFieldExists(), "could not find search field");
		logger.info("Search field exists");
		Assert.assertTrue(searchRestaurant.findRestaurant(restaurantName), "could not find restaraunt");
		logger.info("Successfully found Restaurant: " + restaurantName + " and redirected to Restaurant page");

	}
	
	
	/*  Prerequisite: Create Order 
	 * 		Given: Valid logged in user in 10bis web-site
	 * 		When: User a located on restaurant Page
	 *  	Then: Add 3 dishes and go to checkout page
	 */
	
	@Test(dependsOnMethods = { "findRestaurant" }, priority = 3, enabled = true, description = "create an order")
	public void createOrder() throws InterruptedException, IOException {
		boolean dishes;
		logger.info("Check if dishes exists");
		dishes = orders.checkIfDishesExists();
		Assert.assertTrue(dishes, "Cant create order, dishes doesn't exists");
		if (dishes) {
			logger.info("Choose 3 dishes");
			Assert.assertTrue(orders.createOrder(3), "Cant create order, dishes doesn't exists");
			logger.info("Add 3 dishes to order");
		}

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
