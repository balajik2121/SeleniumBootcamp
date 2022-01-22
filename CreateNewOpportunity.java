package sprint1;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.TimeUnit;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateNewOpportunity {

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
		
		//JavaScript Executor
		JavascriptExecutor executor = (JavascriptExecutor)driver;  
		executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//a[@title='Opportunities']")));

		//Click on New Opportunity
		driver.findElement(By.xpath("//div[@title='New']")).click();

		//Enter all fields
		String oppName="New Oppurtunity by Balaji";
		driver.findElement(By.xpath("//label[text()='Opportunity Name']/following::input")).sendKeys(oppName);
		
		//Enter current date
		driver.findElement(By.xpath("(//input[@class='slds-input'])[3]")).click();
		driver.findElement(By.xpath("//button[text()='Today']")).click();
		
		//Select Dropdown
		driver.findElement(By.xpath("//label[text()='Stage']/following::input[1]")).click();
		driver.findElement(By.xpath("//span[@title=\"Needs Analysis\"]")).click();
		
		//Click on Save
		driver.findElement(By.xpath("//button[text()=\"Save\"]")).click();
		
		//Get Title of the oppurtunity created
		String verifyOppName = driver.findElement(By.xpath("//lightning-formatted-text[text()='"+oppName+"']")).getText();
		
		//Final Verification		
		if(oppName.equals(verifyOppName))
		{
			System.out.println("New Oppurturnity created and verified");
		}
		else
		{
			System.out.println("New Oppurturnity created but not verified");
		}
	
		//Quit Driver
		driver.quit();
		
		
		/*
		 driver.findElement(By.xpath("//a[@class='datePicker-openIcon display']")).click();
		driver.findElement(By.xpath("//button[text()='Today']")).click();
		 */
		
	}

}
