/**
 *  The ChromeDriverDemo script demonstrates what a basic Selenium chromedriver test looks like.
 *  This script will go to the DemoBlaze site, add a specific phone to the shopping cart,
 *  fill out an order form, then check out with its new purchase.
 *  (Don't worry -- the demo site is fake and no real purchases can be made!)
 *  
 *  @author James Panetti
 *  @version 1.0
 *  @since 2019-04-03
 */

package basicdemos;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChromeDriverDemo {

	public static void main(String[] args) {
		String baseURL = "http://www.demoblaze.com";
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		WebDriver driver = new ChromeDriver();

		driver.get(baseURL);
		
		// Verify that an element with the id 'nava' exists and contains the text 'PRODUCT STORE':
		try {
			driver.findElement(By.xpath("//*[@id='nava'][contains(text(), 'PRODUCT STORE')]"));
		} catch (Exception e) {
			System.out.println("Title text of \"Product Store\" not found on page.");
			driver.close();
			System.exit(1);
		}
		
		// Implicit wait in place so that page will load before clicks are attempted:
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		// Click the HTC One M9 link:
		try {
			driver.findElement(By.linkText("HTC One M9")).click();
		} catch (Exception e) {
			System.out.println("The link text \"HTC One M9\" could not be found.");
			driver.close();
			System.exit(1);
		}
		
		// Verify the next shows "HTC One M9" in its h2 header:
		try {
			driver.findElement(By.xpath("//*[@id='tbodyid']/h2[contains(text(), 'HTC One M9')]"));
		} catch (Exception e) {
			System.out.println("Text \"HTC One M9\" not found on next page.");
			driver.close();
			System.exit(1);
		}
		
		// Add HTC One M9 to cart:
		try {
			driver.findElement(By.linkText("Add to cart")).click();
		} catch (Exception e) {
			System.out.println("Could not find or click \"Add to cart\" button.");
			driver.close();
			System.exit(1);
		}
		
		// Wait for then click the resulting pop-up message prompt:
		try {
			Alert alert = new WebDriverWait(driver, 20).until(ExpectedConditions.alertIsPresent());
			alert.accept();
		} catch (Exception e) {
			System.out.println("Could not find or click pop-up prompt.");
			driver.close();
			System.exit(1);
		}
		
		// Click the Cart link:
		try {
			driver.findElement(By.xpath("//*[@id='cartur']")).click();
		} catch (Exception e) {
			System.out.println("Could not find the Cart link.");
			driver.close();
			System.exit(1);
		}

		// Click the "Place Order" button:
		try {
			driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/button")).click();
		} catch (Exception e) {
			System.out.println("Could not find the \"Place Order\" button.");
			driver.close();
			System.exit(1);
		}
		
		// Fill out then submit the order form:
		try {
			WebElement input_name = driver.findElement(By.xpath("//*[@id=\"name\"]"));
			WebElement input_country = driver.findElement(By.xpath("//*[@id=\"country\"]"));
			WebElement input_city = driver.findElement(By.xpath("//*[@id=\"city\"]"));
			WebElement input_cc = driver.findElement(By.xpath("//*[@id=\"card\"]"));
			WebElement input_month = driver.findElement(By.xpath("//*[@id=\"month\"]"));
			WebElement input_year = driver.findElement(By.xpath("//*[@id=\"year\"]"));
			input_name.sendKeys("President Skroob");
			input_country.sendKeys("Planet Spaceball");
			input_city.sendKeys("Spaceball 1");
			input_cc.sendKeys("12345");
			input_month.sendKeys("June");
			input_year.sendKeys("1987");
			driver.findElement(By.xpath("//*[@id=\"orderModal\"]/div/div/div[3]/button[2]")).click();
		} catch (Exception e) {
			System.out.println("Could not fill out or submit the order form.");
			driver.close();
			System.exit(1);
		}
		
		// Verify "Thank you for your purchase!" pop-up message, then click "OK":
		try {
			driver.findElement(By.xpath("/html/body/div[11]/h2[contains(text(), 'Thank you for your purchase!')]"));
			driver.findElement(By.xpath("/html/body/div[11]/div[7]/div/button")).click();
		} catch (Exception e) {
			System.out.println("Could not find the confirmation message or the \"OK\" button.");
			driver.close();
			System.exit(1);
		}
		
		System.out.println("Test completed successfully!");
		driver.close();
		System.exit(0);
	}

}
