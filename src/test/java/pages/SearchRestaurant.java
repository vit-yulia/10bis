package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.xml.LaunchSuite.ExistingSuite;

public class SearchRestaurant extends Base {

	public SearchRestaurant(WebDriver driver) {
		super(driver);
	}
	
	public boolean checkIfSearchFieldExists() throws InterruptedException  {
		return isExist(By.xpath("//*[@class='SearchDropdown__SearchInput-p71rkk-6 kesEwl']"));
	}

	// start Registration
	public boolean findRestaurant(String restourantName) throws InterruptedException {

		
		driver.findElement(By.xpath("//*[@class='SearchDropdown__SearchInput-p71rkk-6 kesEwl']")).sendKeys(restourantName);
		
		Thread.sleep(1000);
		
		click(By.xpath("//*[@class='highlight-search-value']"));
		
		Thread.sleep(1000);
			
		return driver.findElement(By.xpath("//*[@class='RestaurantInfo__RestaurantName-kd3wwa-2 lcTqrG']")).getText().contains(restourantName);

	}

	
}
