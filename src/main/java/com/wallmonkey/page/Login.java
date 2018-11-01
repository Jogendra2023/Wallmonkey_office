package com.wallmonkey.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.wallmonkey.utility.Utility;

public class Login extends Utility
{
	//Login button at home screen.
	@FindBy(how = How.XPATH, using="//a[@class='site-header__account']")
	WebElement login;
	
	//Login Screen email.
	@FindBy(how = How.ID, using="CustomerEmail")
	WebElement email;
	
	//Login screen pasword.
	@FindBy(how= How.ID, using="CustomerPassword")
	WebElement password;
	
	//Login Screen Sign In button.
	@FindBy(how = How.XPATH, using="//input[@value='Sign In']")
	WebElement SignIn;
	
	//My Account Logout button.
	@FindBy(how = How.ID, using="customer_logout_link")
	WebElement Logout;
	
	
	public Login(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public void loginapp() throws Exception
	{
		System.out.println("Inside the Login Page Class");
		driver.get(getproperty("URL"));
		login.click();
		fluentwaittillvisibilityofelement(email);
		email.clear();
		email.sendKeys(readvaluefromexcel("C:\\Users\\jogendras\\eclipse-workspace\\Wallmonkey\\src\\main\\resources\\TestData.xlsx", "TestData", "username"));
		password.clear();
		password.sendKeys(getproperty("password"));
		SignIn.click();
		fluentwaittillvisibilityofelement(Logout);
		Thread.sleep(5000);
		
		
		
		
	}
}
