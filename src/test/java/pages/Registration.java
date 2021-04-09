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
		
		
		if (isExist(By.id("modal-title")))
			return true;
		else
			return false;

	}

	
}
