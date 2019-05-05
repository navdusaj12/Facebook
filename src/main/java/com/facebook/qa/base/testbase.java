package com.facebook.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.facebook.qa.util.TestUtil;

public class testbase {
	
	public static WebDriver driver;
	public static Properties prop;
	
	public testbase() 
	{
	// Read Properties configuration file.
		try {
			prop = new Properties();
			FileInputStream file = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/com/facebook"
					+ "/qa/config/conf.properties");
			prop.load(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	public static void initialization()
	{
		//Driver initialization based on browser input value from properties file. This can be expanded for more drivers.
		String Browser = prop.getProperty("browser");
		if(Browser.equals("firefox"))
		{
				System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+ "/src/main/resources"
					+ "/geckodriver.exe");
				FirefoxOptions options = new FirefoxOptions();
				options.setProfile(new FirefoxProfile());
				//Using firefox options to disable notifications during sign up process.
				options.addPreference("dom.webnotifications.enabled", false);
				driver = new FirefoxDriver(options);     
		}
		else if(Browser.equals("chrome")){
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+ "/src/main/resources"
					+ "/chromedriver.exe");
				Map<String, Object> prefs = new HashMap<String, Object>();
				//Using chrome options to disable notifications during sign up process.
				prefs.put("profile.default_content_setting_values.notifications", 2);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", prefs);
				driver = new ChromeDriver(options);
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		//Adding synchronization point to make sure Ajax and other components load successfully.
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT,TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));		
	}
}
