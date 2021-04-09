package pages;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.xml.LaunchSuite.ExistingSuite;

public class Login extends Base {

	public Login(WebDriver driver) {
		super(driver);
	}

	// start login
	public boolean doLoginFacebook(String user, String password, String name) throws InterruptedException {

		String baseHandle = driver.getWindowHandle();
		
		//Click facebook image
		click(By.xpath("//img[@type='facebook']"));
		
		//switch to Facebook login window
		
		Thread.sleep(2000);
		Set<String> handels = driver.getWindowHandles();

		for (String h : handels) {
			if (!h.equals(baseHandle))
				driver.switchTo().window(h);
		}

		//type user/password
		driver.findElement(By.id("email")).sendKeys(user);
		Thread.sleep(1000);

		driver.findElement(By.id("pass")).sendKeys(password);
		Thread.sleep(1000);
		click(By.name("login"));

		Thread.sleep(4000);
//		if (driver!=null)
//			driver.close();
		
		driver.switchTo().window(baseHandle);

		click(By.xpath("//img[@type='facebook']"));
		Thread.sleep(2000);

		
		String personalMsg = getText(By.cssSelector(".styled__PrimaryText-zzhidz-4.cfoTPh"));
		
		if (personalMsg.contains(name))
			return true;
		else
			return false;

	}

}
