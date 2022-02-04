package sprint2;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateAndDeleteWorkout {

	public static void main(String[] args) throws InterruptedException {
		
	//1.Login to https://login.salesforce.com
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
		
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver,30);
		
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
		
	//2. Click on toggle menu button from the left corner		
		driver.findElement(By.xpath("//div[@class=\"slds-icon-waffle\"]")).click();
	
	//3. Click view All 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()=\"View All\"]")));
		driver.findElement(By.xpath("//button[text()='View All']")).click();
	
	//4. Click Service Console from App Launcher
		driver.findElement(By.xpath("//p[text()='Service Console']")).click();
		
	//5. Select Home from the DropDown
		Thread.sleep(3000);
		driver.findElementByXPath("//button[@class=\"slds-button slds-button_icon slds-p-horizontal__xxx-small slds-button_icon-small slds-button_icon-container\"]").click();
		driver.findElementByXPath("//span[text()=\"Home\"]").click();		
		
	//6. Add CLOSED + OPEN values and result should set as the GOAL (If the result is less than 10000 then set the goal as 10000)
		
		//Get Closed value
		String closed = driver.findElementByXPath("(//span[@class=\"metricAmount uiOutputText\"])[1]").getText();
		closed=closed.replace("$", "");
		int c=Integer.parseInt(closed);  
		
		//Get Open value
		String open = driver.findElementByXPath("(//span[@class=\"metricAmount uiOutputText\"])[2]").getText();
		open=open.replace("$", "");
		int o=Integer.parseInt(open);
		
		//Sum Closed and Open value
		int newGoal=c+o;
		
		//Get Goal value
		String goal = driver.findElementByXPath("(//span[@class=\"metricAmount uiOutputText\"])[3]").getText();
		goal=goal.replaceAll("[$,]", "");
		int g=Integer.parseInt(goal);
		
		//Set the new goal value if sum is <10000 and the goal is already not set as 10000	
		if(newGoal<1000 && g!=10000)
		{
		driver.findElementByXPath("//button[@title=\"Edit Goal\"]").click();
		WebElement setGoal = driver.findElementByXPath("//input[@class=\"input uiInputSmartNumber uiInput uiInput--default uiInput--input\"]");
		setGoal.clear();
		setGoal.sendKeys("10000");
		driver.findElementByXPath("//button[@class=\"slds-button slds-button--neutral uiButton--default uiButton--brand uiButton\"]//span").click();
		System.out.println("Previously goal was set as "+g+" Goal is set to 10000");
		}
		else
		{
			System.out.println("Goal is already set as 10000, so no change done");
		}
	
	//7. Select Dashboards from DropDown
		driver.findElementByXPath("//button[@class=\"slds-button slds-button_icon slds-p-horizontal__xxx-small slds-button_icon-small slds-button_icon-container\"]").click();
		driver.findElementByXPath("//span[text()=\"Dashboards\"]").click();		
		
	//8. Click on New Dashboard
		driver.findElementByXPath("//div[@title=\"New Dashboard\"]").click();
		
		Thread.sleep(5000);
		driver.switchTo().frame(0);
		// create instance of Random class
        Random rand = new Random();
        // Generate random integers in range 0 to 999
        int randNo = rand.nextInt(1000);
		String workOutName="Balaji Workout"+randNo;
		driver.findElementById("dashboardNameInput").sendKeys(workOutName);
		driver.findElementById("dashboardDescriptionInput").sendKeys("Test Description");
		driver.findElementByXPath("//button[text()=\"Create\"]").click();
		driver.switchTo().parentFrame();
		
		Thread.sleep(2000);
		driver.switchTo().frame(0);
		driver.findElementByXPath("//button[text()=\"Done\"]").click();
		driver.switchTo().parentFrame();
		
		
		Thread.sleep(5000);
		driver.switchTo().frame(0);
		
		String createdWorkoutName = driver.findElementByXPath("//span[@class='slds-page-header__title slds-truncate']").getText();
		
		if(createdWorkoutName.contains("Balaji Workout"))
		{
			System.out.println("workout created");
		}
		else
		{
			System.out.println("workout not created");
		}
		
		driver.findElementByXPath("//button[text()='Subscribe']").click();
		
		driver.switchTo().parentFrame();
		
		//driver.switchTo().frame(0);
		
		Thread.sleep(2000);
		
		String subscriptionMaxed = driver.findElementByXPath("//h2[text()='All subscriptions used']").getText();
		
		if(subscriptionMaxed.contains("All subscriptions used"))
			
		{
			driver.findElementByXPath("//span[text()='OK']").click();
		}
		//PriavteDashboard driver.findElementByXPath("(//div[@class=\"sidebarContainer\"]//ul//li)[3]").click();
		
		Thread.sleep(3000);
		driver.findElementByXPath("//button[@title= 'Close "+ workOutName+"']").click();
		
		Thread.sleep(3000);
		driver.findElementByXPath("//div[@class=\"folderSidebar\"]//a[@title=\"Private Dashboards\"]").click();
		driver.findElementByXPath("//input[@class=\"search-text-field slds-input input uiInput uiInputText uiInput--default uiInput--input\"]").sendKeys(workOutName);
		String createdWorkout = driver.findElementByXPath("(//table[contains(@class,\"slds-table slds-table_header\")]//tbody//tr//th//span)[1]//a").getText();
		if(workOutName.contains(createdWorkout)) {
			System.out.println("workout created and displayed correctly");
		}
		else
		{
			System.out.println("Workout not created");
		}
		Thread.sleep(1000);
		driver.findElementByXPath("//button[contains(@class,\"slds-button slds-button_icon-border\")]").click();
		Thread.sleep(3000);
		driver.findElementByXPath("//span[text()=\"Delete\"]").click();
		
		driver.findElementByXPath("//button[@class=\"slds-button slds-button--neutral uiButton--default uiButton--brand uiButton forceActionButton\"]//span").click();
		
		String deletedText = driver.findElementByXPath("//span[@class=\"toastMessage slds-text-heading--small forceActionsText\"]").getText();
		if(deletedText.contains("Dashboard was deleted"))
		{
			System.out.println("Dashboard deleted");
		}
		else
		{
			System.out.println("Not Deleted");
		}
		
		Thread.sleep(1000);
		String text = driver.findElementByXPath("//span[@class=\"emptyMessageTitle\"]").getText();	
		System.out.println(text);
		driver.quit();

	}

}
