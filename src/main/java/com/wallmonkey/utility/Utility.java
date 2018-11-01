package com.wallmonkey.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Driver;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.wallmonkey.page.Login;

import net.bytebuddy.implementation.bytecode.Duplication;

public class Utility 
{
	public WebDriver driver;
	public Login login_page;
	
	//Passing broweser to Script through testNg.
	@Parameters({ "browser" })
	@BeforeMethod
	public void setup(String browser)
	{
		launchbrowser(browser);
		
		//Passing driver refrences to class thorugh class constructor.
		login_page = new Login(driver);
		 
	}

	public void launchbrowser(String browser)
	{
		//Setting firefox browser.
		if(browser.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "C:\\Users\\jogendras\\eclipse-workspace\\Wallmonkey\\Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
		}
		else if(browser.equalsIgnoreCase("chrome"))
		{
			//Setting Chrome browser.
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\jogendras\\eclipse-workspace\\Wallmonkey\\Drivers\\chromedriver.exe");
			
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-extensions");
			options.addArguments("disable-infobars");
			options.addArguments("--disable-web-security");
			options.addArguments("--no-proxy-server");
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			
		}
	}
	
	@AfterMethod
	public void teardown()
	{
		driver.close();
	}
	
	//**************************Setting Properties file.*****************************
	public String getproperty(String Key)
	{
		File fp = new File("C:\\Users\\jogendras\\eclipse-workspace\\Wallmonkey\\src\\main\\resources\\config.properties");
		Properties pro = new Properties();
		try
		{
			FileInputStream fis = new FileInputStream(fp);
			pro.load(fis);
			fis.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return pro.getProperty(Key);
	}
	//Fluent wait for the visibility of the Element.
	public void fluentwaittillvisibilityofelement(WebElement element)
	{
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(300, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
								.ignoring(NoSuchElementException.class);
		
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	
	//******************Read from Excel file using the key********************************************
	
	public String readvaluefromexcel(String path, String sheet, String key)
	{
		String result = null;
		try
		{
			System.out.println("Inside the readexcel method");
			System.out.println("Key : " +key);
			System.out.println("sheet : " +sheet);
		
		//Create an object of File class to open xlsx file
		File file = new File(path);
		
		//Create an object of FileInputStream class to read excel file.
		FileInputStream fis = new FileInputStream(file);
		
		//create object of XSSFWorkbook class
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		
		//Read excel sheet by sheet name    
		XSSFSheet sh = wb.getSheet(sheet);
		int noofrow = sh.getPhysicalNumberOfRows();
		
		for(int i=0; i<noofrow;i++)
		{
			Row rw = sh.getRow(i);
			int column = rw.getPhysicalNumberOfCells();
			
			for(int j=0;j<column;j++)
			{
				
				Cell cl =rw.getCell(j);
				
				
				if(cl.toString().equalsIgnoreCase(key))
				{
					int k=j+1;
					result=sh.getRow(i).getCell(k).toString();
					break;
				}
			}
		}
		
		
		}
		catch(FileNotFoundException e)
		{
		System.out.println("File Not Found" +e);
		e.printStackTrace();
		}
		catch(IOException e)
		{
			System.out.println("Input output Exception" +e);
			e.printStackTrace();
		}
		catch(EncryptedDocumentException e)
		{
			System.out.println("Document is in Encrypted form" +e);
			e.printStackTrace();
		}
		return result;
	}
	
	//***********************************Write to Excel file using the key****************************************************
	
	public void writetoexcel(String path,String sheet, String key, String value)
	{
		try
		{
		//Create an object of File class to open xlsx file
		File fp = new File(path);
		//Create an object of FileInputStream class to read excel file.
		FileInputStream fis = new FileInputStream(fp);
		//create object of XSSFWorkbook class
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		//Create objetc of XSSFSheet class
		XSSFSheet sh = wb.getSheet(sheet);
		}
		catch(IOException e)
		{
			System.out.println("Input Output Exception" +e);
			e.printStackTrace();
		}
	}
	
		
	
	
	
	
}
