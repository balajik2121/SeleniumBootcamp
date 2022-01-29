package sprint1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DeleteOpportunity {

	public static void main(String[] args) throws InterruptedException {
		
		//Get the ChromeDriver 
		WebDriverManager.chromedriver().setup();
		
		//Ignore Browser level popups
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver= new ChromeDriver(options);	
				
		//Implicit wait
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Open Sales Force website and Maximize the window
		driver.get("https://login.salesforce.com");
		driver.manage().window().maximize();
		
		//Get UserName field and enter
		WebElement userName=driver.findElement(By.id("username"));
		userName.clear();
		userName.sendKeys("mars@testleaf.com");
		
		//Get Password field and enter
		WebElement password=driver.findElement(By.id("password"));
		password.clear();
		password.sendKeys("SelBootcamp$123");
		
		//Click on Submit button
		driver.findElement(By.id("Login")).click();
				
		driver.findElement(By.xpath("//div[@data-aura-rendered-by='448:82;a']")).click();
		
		driver.findElement(By.xpath("//button[text()='View All']")).click();
		
		driver.findElement(By.xpath("//p[text()='Sales']")).click();
		
		//Navigate to Oppurtunities
		//JavaScript Executor
		JavascriptExecutor executor = (JavascriptExecutor)driver;  
		executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//a[@title='Opportunities']")));
		
		//Search for the newly created opportunity
		String oppName="New Opportunity by Balaji";
		WebElement searchOpp = driver.findElement(By.xpath("//label[text()='Search this list...']/following::input[1]"));
		searchOpp.sendKeys(oppName);
		Thread.sleep(3000);
		searchOpp.sendKeys(Keys.RETURN);
		
		//Click on Delete in the opportunity
		driver.findElement(By.xpath("//div[@class='uiScroller scroller-wrapper scroll-bidirectional native']")).click();
		driver.findElement(By.xpath("(//a[contains(@class,'slds-button slds-button--icon-x-small')])[1]")).click();
		driver.findElement(By.xpath("//a[@title=\"Delete\"]")).click();
		
		//Click on Delete button
		driver.findElement(By.xpath("//span[text()='Delete']")).click();
		
		Thread.sleep(3000);
		
		//Get toast msg
		String toastMsg = driver.findElement(By.xpath("//span[@class='toastMessage slds-text-heading--small forceActionsText']")).getText();
		System.out.println(toastMsg);
			
		System.out.println(oppName);
		System.out.println(toastMsg.replace("\"",""));
		
		
		if(toastMsg.replace("\"","").contains(oppName))
		{
			System.out.println("Deleted");
		}
		else
		{
			System.out.println("Not deleted");
		}
		
		//Quit Driver
				driver.quit();
				
	}

}
