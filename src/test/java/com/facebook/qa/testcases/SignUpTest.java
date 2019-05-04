package com.facebook.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import com.facebook.qa.base.testbase;
import com.facebook.qa.pages.SignUpPage;
import com.facebook.qa.util.TestUtil;
public class SignUpTest extends testbase {

	//Object Reference of SignUpPage class
	static SignUpPage signup;
	public SignUpTest()
	{
		//Initialize testbase class variables.
		super();
	}
	
	@BeforeMethod
	public void setUp()
	{
		//Initializing driver.
		initialization();
		signup = new SignUpPage(); //Allocating heap memory to signup object.
	}
	//Verifying Title of SignUp Page
	@Test(priority = 1)
	public static void Title()
	{
		String title = signup.getTile();
		Assert.assertEquals(title, "Sign up for Facebook | Facebook");
	}
    //DataProvider to read data from excel sheet and store in Object multidimensional array.
	@DataProvider
	public Object[][] getSignUpTestData(){
		Object data[][] = TestUtil.getTestData("signup");
		return data;
	}
    //Test Case to enter data on signup page using DataProvider.
	@Test(priority = 2, dataProvider="getSignUpTestData")
	public static void CreateNewUser(String firstname, String lastname, String MobileOrEmail,String password, String day, String month, String year, String sex)
	{
		signup.NewUserSignUp(firstname,lastname,MobileOrEmail,password,day,month,year,sex);				
	}
    //Close the driver.
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
}
