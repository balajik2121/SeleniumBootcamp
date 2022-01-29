package sprint1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateNewOppError {

	public static void main(String[] args) {
		
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
				
				//Get tomorrow date
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				LocalDate tomorrow = LocalDate.now().plusDays(1);
				String tomDate = dtf.format(tomorrow);
				
				//Enter it in the closed date field
				WebElement closeDate = driver.findElement(By.xpath("//label[text()='Close Date']/following::input[1]"));
				closeDate.clear();
				closeDate.sendKeys(tomDate);
				
				//Click on Save
				driver.findElement(By.xpath("//button[text()=\"Save\"]")).click();
				
				//Error messages
				String errorText = driver.findElement(By.xpath("//h2[@title=\"We hit a snag.\"]")).getText();
				System.out.println(errorText);
				
				String oppError = driver.findElement(By.xpath("//div[@class='panel-content scrollable']//ul//li[1]/a")).getText();
				System.out.println(oppError+" is a mandatory fields and need to be filled");
				
				String stagError = driver.findElement(By.xpath("//div[@class='panel-content scrollable']//ul//li[2]/a")).getText();
				System.out.println(stagError+" is a mandatory fields and need to be filled");
				
				driver.quit();
				


	}

}
