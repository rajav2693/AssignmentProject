package com.lsac.pom.WebappTest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utill.ExtentManager;


public class WEB_BROWSER_ASSIGNMENT_TEST {
	
	ExtentReports rep = ExtentManager.getInstance();
	ExtentTest test;
	WebDriver driver = null;

	@Test
	public void webBrowserAppTest() throws InterruptedException, MalformedURLException {
		
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Rajesh.Veeranna\\Downloads\\chromedriver.exe");
        driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		Thread.sleep(2000); 
		
		test=rep.startTest("webBrowserAppTest");
		test.log(LogStatus.INFO, "Strarting the Test");	
		
		driver.get("https://www.lsac.org/lsat-prep-get-familiar");
		takeScreenshot();
		test.log(LogStatus.INFO, "Application Launched Successfully");	
		
		WebDriverWait wait = new WebDriverWait(driver,1000);		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("CybotCookiebotDialogBodyLevelButtonAccept")));		
		test.log(LogStatus.INFO, "Click on \"OK Button\" to accept Cookies ");	
		driver.findElement(By.id("CybotCookiebotDialogBodyLevelButtonAccept")).click();
		
		driver.findElement(By.xpath("//*[@id=\"block-lsac-aboutlsacmenu\"]/ul/li[6]")).click();
		test.log(LogStatus.INFO, " Click on \"Search\" ");	
		
		wait = new WebDriverWait(driver,1000);		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='edit-search']")));
		takeScreenshot();
		test.log(LogStatus.INFO, " Enter Keyword on  Search Text field");	
		driver.findElement(By.xpath("//*[@id='edit-search']")).sendKeys("Law");
		driver.findElement(By.xpath("//*[@id='edit-submit-sitewide-search']")).click();
		Thread.sleep(2000); 
		takeScreenshot();
		
		test.log(LogStatus.INFO, " Search Results get displayed based on search criteria");	
		List<WebElement> searchResult = driver.findElements(By.xpath("//div[@class='views-field views-field-title']"));
		for (WebElement items:searchResult){
		System.out.println(items.getText());
		Thread.sleep(200);                                        
		test.log(LogStatus.INFO, items.getText().toUpperCase());	

		}
		
		test.log(LogStatus.PASS, "Test Passed");
	}
	
	
	
	
		private void takeScreenshot() {
			
			Date d = new Date();
			String screenshotFile=d.toString().replace(":", "_").replace(" ","_")+".png";
			String path="E:\\AutomationReports\\"+"screenshots\\"+screenshotFile;
			// take screenshot
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(scrFile, new File(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
			//add screenshot to report
			test.log(LogStatus.INFO,"Snapshot below: ("+screenshotFile+")"+
			test.addScreenCapture(path));
		}
		
		@AfterMethod
		public void quit() {
			if (rep!=null) {
				rep.endTest(test);
				rep.flush();	
			}
			if (driver!=null)
				driver.quit();
		}  
		
			
		}