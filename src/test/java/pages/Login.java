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
		Thread.sleep(1000);
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

		Thread.sleep(5000);
		
		driver.switchTo().window(baseHandle);

		Thread.sleep(1000);
		
		String personalMsg = getText(By.cssSelector(".styled__PrimaryText-zzhidz-4.cfoTPh"));
		
		if (personalMsg.contains(name))
			return true;
		else
			return false;

	}
	public boolean verifyErrorMessage (String ErrorMsg) throws InterruptedException {
		
		click(By.xpath("(//*[@class=\"Button-sc-60bpt4-0 styled__HeaderUserLink-sc-1vdg0d6-4 cmDWmk dAVwSX\"])[1]"));
		Thread.sleep(1000);
		click(By.xpath("//*[@id=\"login_tab_button\"]"));
		Thread.sleep(1000);
		click(By.xpath("//*[@data-test=\"login-submit\"]"));
		
		//to remove focus from e-mail input field
		click(By.xpath("//*[@id=\"login_tab_controls\"]/div/div[1]"));
		
		Thread.sleep(1000);
		String errText = getText(By.xpath("//*[@role=\"alert\"]"));
		
		
		if (errText.equals(ErrorMsg))
			return true;
		else
			return false;
		
		
	}
			
		
}
