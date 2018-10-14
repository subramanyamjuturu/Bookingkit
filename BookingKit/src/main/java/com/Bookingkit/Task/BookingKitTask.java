package com.Bookingkit.Task;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookingKitTask {
	// Getting path of the project folder
	public static String strAbsolutePath =new File("").getAbsolutePath();
	public static String ResourcePath = strAbsolutePath+"\\Resources\\";

	public static WebDriver driver;


	public static void main(String[] args) {
		//Code for getting driver details from resource folder in the project folder to avoid manual intervention for changing the path of driver in code
		File folder = new File(ResourcePath);
		File[] listOfFiles = folder.listFiles();

		//setting Chrome as browser
		System.setProperty("webdriver.chrome.driver",listOfFiles[0].toString());	

		// Launching chrome browser
		driver = new ChromeDriver();
		Actions actions = new Actions(driver);

		// Explicit wait using in code for specific code
		WebDriverWait wait = new WebDriverWait(driver, 15);

		//Launching URL
		driver.get("https://releasetest.bookingkit.de");

		//Maximizing window
		driver.manage().window().maximize();

		//Entering user name
		driver.findElement(By.id("LoginForm_username")).sendKeys("dantis+test@bookingkit.de");

		// Entering invalid password
		driver.findElement(By.id("LoginForm_password")).sendKeys("welcome");

		//Clicking on login button
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		// waiting for error message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("LoginForm_password_em_")));

		// checking whether error message is displaying or not using selenium java code
		if(driver.findElement(By.id("LoginForm_password_em_")).isDisplayed())
			System.out.println("Unable to login with invalid credentials");
		else
			System.out.println("Application is logining with invalid credentials");

		// clearing and entered user name and re entering again
		driver.findElement(By.id("LoginForm_username")).clear();
		driver.findElement(By.id("LoginForm_username")).sendKeys("dantis+test@bookingkit.de");

		// clearing and entered password and re entering again
		driver.findElement(By.id("LoginForm_password")).clear();
		driver.findElement(By.id("LoginForm_password")).sendKeys("welcome@2018");

		//Clicking on login button
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		// waiting for application load time
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("user-name")));

		// checking whether user logged to application or not using selenium java code
		if(driver.findElement(By.className("user-name")).isDisplayed())
			System.out.println("User logged to the application sucessfully with valid credentials");
		else
			System.out.println("User unable to logged to the application please check user details");

		// click on QA Assessment Account
		driver.findElement(By.className("user-name")).click();

		// Selecting edit profile from the drop down
		driver.findElement(By.xpath("//li//a[contains(@href,'setting/user')]")).click();

		// variables using for language checking and for the loops operations
		int count;
		boolean condition = false;
		boolean secondloop = false;
		String DefaultLanguage ;
		String ChangedLanguage ;

		//Getting default laguage of the user
		DefaultLanguage = driver.findElement(By.xpath("//div[@class='left']//div")).getText();

		do {
			// waiting till language selection page appearing
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("User[language]")));

			// getting all languages available in application and storing in list
			List<WebElement> ele = driver.findElements(By.name("User[language]"));

			// for loop for language selection and verification process
			for(int i=0;i<ele.size();i++)
			{
				if(ele.get(i).isSelected())
				{
					count=i+1;

					// executing only for the first time and printing default selected language
					if(!secondloop)
						System.out.println("Default Selected Language is: "+driver.findElement(By.xpath("(//input[@name='User[language]']//..//span)["+count+"]")).getText());

					// executing only for the second time and printing second selected language
					if(secondloop)
					{
						System.out.println("Language changed to: "+driver.findElement(By.xpath("(//input[@name='User[language]']//..//span)["+count+"]")).getText());
						condition= false;
						break;
					}

					// language changing code 
					if(i>0)
					{
						actions.moveToElement(ele.get(ele.size()-1));
						actions.perform();
						ele.get(i-1).click();

						ChangedLanguage= driver.findElement(By.xpath("//div[@class='left']//div")).getText();

						// checking whether language is changing or not before clicking on save button
						if(DefaultLanguage.equalsIgnoreCase(ChangedLanguage))
							System.out.println("Language did not changed before clicking on save button");
						else
							System.out.println("Language changed before clicking on save button and changed language is "+driver.findElement(By.xpath("(//input[@name='User[language]']//..//span)["+count--+"]")).getText());
					}
					else
					{
						actions.moveToElement(ele.get(ele.size()-1));
						actions.perform();
						ele.get(i+1).click();

						ChangedLanguage= driver.findElement(By.xpath("//div[@class='left']//div")).getText();

						// checking whether language is changing or not before clicking on save button
						if(DefaultLanguage.equalsIgnoreCase(ChangedLanguage))
							System.out.println("Language did not changed before clicking on save button");
						else
							System.out.println("Language changed before clicking on save button and changed language is "+driver.findElement(By.xpath("(//input[@name='User[language]']//..//span)["+count+++"]")).getText());

					}
					// saving language 
					driver.findElement(By.xpath("//input[@type='submit']")).click();

					secondloop= true;
					condition= true;
					break;

				}
			}
		}while(condition);

		ChangedLanguage= driver.findElement(By.xpath("//div[@class='left']//div")).getText();

		// checking whether language is changed or not
		if(!DefaultLanguage.equalsIgnoreCase(ChangedLanguage))
			System.out.println("Language Changed Sucessfully");
		else
			System.out.println("Language failed to Changed");

		//Selecting the Dashboard tab
		driver.findElement(By.xpath("//a[contains(@class,'dashboard-btn menuBtn')]")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("user-name")));

		driver.findElement(By.className("user-name")).click();

		//logouting from application
		driver.findElement(By.xpath("//li//a[contains(@href,'site/logout')]")).click();
	}

}
