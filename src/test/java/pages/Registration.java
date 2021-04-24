package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.xml.LaunchSuite.ExistingSuite;

public class Registration extends Base {

	public Registration(WebDriver driver) {
		super(driver);
	}
	
	// start Registration
	public boolean fill_registration(String fullName, String email, String phone) throws InterruptedException {

		
		driver.findElement(By.id("fullName")).sendKeys(fullName);
		
		driver.findElement(By.id("email")).sendKeys(email);
		
		driver.findElement(By.id("cellPhone")).sendKeys(phone);
		
		driver.findElement(By.id("agreeToTerms")).click();
		driver.findElement(By.xpath("//*[@type='submit']")).click();
		
		Thread.sleep(1000);
		
		// Verify that pop up with enter code from sms is open
		if (isExist(By.xpath("//*[@id='signup_body_wrapper_activation']//*[@class='PhoneVerificationSection__PhoneNumber-sc-1ahv967-3 eTRPpC']"))) {
			String mobileText = getText(By.xpath("//*[@id='signup_body_wrapper_activation']//*[@class='PhoneVerificationSection__PhoneNumber-sc-1ahv967-3 eTRPpC']"));
			return mobileText.equals(phone);			
		} else {
			return false;
		}

	}

	
}
