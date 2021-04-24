package pages;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.xml.LaunchSuite.ExistingSuite;

public class Order extends Base {

	public Order(WebDriver driver) {
		super(driver);
	}
	
	public boolean checkIfDishesExists() throws InterruptedException  {
		return isExist(By.xpath("//*[@class='MenuListstyled__Item-sc-5xujek-1 feTyxL']"));
	}

	// start Registration
	public boolean createOrder(int numOfOrders) throws InterruptedException {
		
		List<WebElement> numOfExistingDishes;
		
		numOfExistingDishes = driver.findElements(By.xpath("//*[@class='MenuListstyled__Item-sc-5xujek-1 feTyxL']"));
		
		if (numOfExistingDishes.size() < numOfOrders) {
			numOfOrders = numOfExistingDishes.size();
		}
		
		for (int i = 1; i < numOfOrders + 1; i++) {
			click(By.xpath("(//*[@class='MenuListstyled__Item-sc-5xujek-1 feTyxL'])["+ i +"]"));
			click(By.xpath("//*[@id='modals']//button[contains(@class, 'CartButton__CartActionButton-sc-1tb7y5t-0')]"));
			Thread.sleep(1000);
		}
		Thread.sleep(1000);
		
		click(By.xpath("(//*[@class='ShoppingCartDishesstyled__PaymentContainer-sc-1ifkpzb-1 dbdeaC'])[1]"));
		Thread.sleep(1000);
		
		return driver.findElement(By.xpath("//*[@id='modal-title']")).getText().contains("תשלום");

	}

	
}
