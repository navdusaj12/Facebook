package com.facebook.qa.pages;
import org.openqa.selenium.Alert;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.facebook.qa.base.testbase;
import com.facebook.qa.util.TestUtil;

public class SignUpPage extends testbase {
	//Object Repository
	@FindBy(name="firstname")
	WebElement firstname;
	
	@FindBy(name="lastname")
	WebElement lastname;
	
	@FindBy(xpath="//input[contains(@name,'reg_email')]")
	WebElement emailOrMobile;
	
	@FindBy(xpath="//input[contains(@name,'reg_email_confirmation')]")
	WebElement confirmEmail;
	
	@FindBy(xpath="//input[contains(@name,'reg_passwd')]")
	WebElement password;
	
	@FindBy(id="day")
	WebElement day;
	
	@FindBy(id="month")
	WebElement month;
	
	@FindBy(id="year")
	WebElement year;
	
	@FindBy(xpath = "//input[(@name='sex' and @value = '1')]")
	WebElement female;
	
	@FindBy(xpath = "//input[(@name='sex' and @value = '2')]")
	WebElement male;
	
	@FindBy(xpath="//button[contains(text(),'Sign Up')]")
	WebElement signUpBtn;
	
	@FindBy(xpath="//div[@class='linkWrap noCount']")
	WebElement signupUser;
	
	WebDriverWait wait = new WebDriverWait(driver, 30);
	//Object Repository known as PageFactory in POM - Initialization.
	public SignUpPage()
	{
		PageFactory.initElements(driver, this);
	}
    //Get the title of the signup page.
	public String getTile()
	{
		return driver.getTitle();
	}
	//Function to add values in the fields of Signup page.	
	public void NewUserSignUp(String FirstName, String LastName, String MobileOrEmail,String Password, String Day, String Month, String Year, String Sex)
	{
		String username = FirstName+" "+LastName;
		wait.until(ExpectedConditions.visibilityOf(firstname));
		firstname.sendKeys(FirstName);
		lastname.sendKeys(LastName);
		emailOrMobile.sendKeys(MobileOrEmail);
        //Condition to check if user has entered email address or mobile number. An additional field appears on signup page for email field.
		if(emailOrMobile.getAttribute("value").contains("@"))
		{	
			wait.until(ExpectedConditions.visibilityOf(confirmEmail));
			confirmEmail.sendKeys(MobileOrEmail);
		}
		password.sendKeys(Password);
		Select objDay = new Select(day); 
		objDay.selectByVisibleText(Day);
		
		Select objMonth = new Select(month); 
		objMonth.selectByVisibleText(Month);
		
		Select objYear = new Select(year); 
		objYear.selectByVisibleText(Year);
	    //Using JavaScript executor to select Male or Female radio buttons as sent from test data sheet.
		if(Sex.equalsIgnoreCase("Male"))
		{
			JavascriptExecutor js = (JavascriptExecutor)driver;
	    	js.executeScript("arguments[0].click();", male);
		}
		else
		{
			JavascriptExecutor js = (JavascriptExecutor)driver;
	    	js.executeScript("arguments[0].click();", female);
		}
		
		signUpBtn.click();
        //Alert dialog box appears intermittently during signup process.
		Boolean alert = TestUtil.isAlertPresent();
    	if(alert.equals(true))
    	{
    		Alert confirmDOBAlert = driver.switchTo().alert();
            confirmDOBAlert.accept();        	      	
    	}
    	//Verify newly signedup user has logged in successfully.
    	wait.until(ExpectedConditions.visibilityOf(signupUser));
    	if(signupUser.getText().equalsIgnoreCase(username))
    	{
    		System.out.println("New Signd up user has successfully logged in.");
    	}
    	else
    	{
    		System.out.println("New Signd up user has NOT successfully logged in.");
    	}
 }
	
}
