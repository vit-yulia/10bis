package pages;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.xml.LaunchSuite.ExistingSuite;

public class Main extends Base {

	public Main(WebDriver driver) {
		super(driver);
	}

	// start registration
	public boolean register() throws InterruptedException {

		click(By.xpath("//button[text()='הרשמה']"));
		if (isExist(By.id("modal-title")))
			return true;
		else
			return false;

	}
	
	// start login
		public boolean login() throws InterruptedException {
			
			//Click connection
			click(By.xpath("//button[text()='התחברות']"));
			
			if (isExist(By.xpath("//*[@id=\"modal-title\"][text()='איזה כיף שחזרת אלינו!']")))
				return true;
			else
				return false;

		}

	// select account
	public boolean selectAccount() throws InterruptedException {

		click(By.cssSelector("#other > div.question.first-question > div"));
		click(By.xpath(
				"//div[@class='dropdown custom-select step-one open']//li[@data-value='freelancer-or-smb'][@class='option']"));
		return true;
	}
	
	public boolean verifyCareerPage() throws InterruptedException {
		if (!isExist(By.xpath("(//*[@class=\"FooterLink__Text-sc-1ywqpjc-0 cXhlIB\"])[6]"))) {
			return false;
		}
		click(By.xpath("(//*[@class=\"FooterLink__Text-sc-1ywqpjc-0 cXhlIB\"])[6]"));
		Thread.sleep(1000);
		String baseHandle = driver.getWindowHandle();
		Thread.sleep(3000);
		Set<String> handels = driver.getWindowHandles();

		for (String h : handels) {
			if (!h.equals(baseHandle))
				driver.switchTo().window(h);
		}
		//return isExist(By.xpath("//*[@id=\"typehead\"]"));
		return driver.getCurrentUrl().contains("careers.takeaway");
		
	}

	
}
