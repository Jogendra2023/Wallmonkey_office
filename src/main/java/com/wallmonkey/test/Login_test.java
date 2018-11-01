package com.wallmonkey.test;

import org.testng.annotations.Test;

import com.wallmonkey.utility.Utility;

public class Login_test extends Utility
{
	@Test(groups="Login" , description="Login to Application")
	
	public void logintoappwithvalid() throws Exception
	{
		System.out.println("Inside the Login Test Class");
		login_page.loginapp();
	}

}
