package com.lsac.pom.MobileAppTest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import utill.ExtentManager;
import utill.LASC_Constants;

public class MOBILE_BROWSER_ASSIGNMENT_TEST {
		
	ExtentReports rep = ExtentManager.getInstance();
	ExtentTest test;
	AndroidDriver<AndroidElement> driver = null;

	@Test
	public void mobileBrowserAppTest() throws InterruptedException, MalformedURLException {
	
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("chromedriverExecutable","E:\\chromedriver.exe");
		cap.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
		cap.setCapability("deviceName",LASC_Constants.DEVICE_NAME );
		cap.setCapability("platformVersion",LASC_Constants.PLATFORM_VERSION);
		cap.setCapability("platformName",LASC_Constants.PLATFORM_NAME );
		cap.setCapability("autoGrantPermissions",LASC_Constants.AUTOGRANT_PERMISSION);
		cap.setCapability("automationName",LASC_Constants.AUTO_NAME);
		cap.setCapability("appium:chromeOptions",ImmutableMap.of("w3c", false));
		driver = new AndroidDriver<AndroidElement> (new URL(LASC_Constants.APPIUM_URL), cap);
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		Thread.sleep(2000);    
		
		test=rep.startTest("mobileBrowserAppTest");
		test.log(LogStatus.INFO, "Strarting the Test");	
		
		driver.get("https://www.lsac.org/lsat-prep-get-familiar");
		takeScreenshot();
		test.log(LogStatus.INFO, "Application Launched Successfully");	

		WebDriverWait wait = new WebDriverWait(driver,1000);		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("CybotCookiebotDialogBodyLevelButtonAccept")));
		test.log(LogStatus.INFO, "Click on \"OK Button\" to accept Cookies ");	
		driver.findElementById("CybotCookiebotDialogBodyLevelButtonAccept").click();		
		takeScreenshot();

		wait = new WebDriverWait(driver,2000);		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='js-menu-open wrapper-header__menu-toggle -opener text-small lg-hide']")));
		test.log(LogStatus.INFO, "Tap on \"Menu Bar\" ");	

		driver.findElement(By.xpath("//button[@class='js-menu-open wrapper-header__menu-toggle -opener text-small lg-hide']")).click();
		test.log(LogStatus.INFO, "\"Menu Items\" displayed Successfully ");	
		takeScreenshot();
		
		test.log(LogStatus.INFO, " Click on \"Search\" ");	
		driver.findElement(By.xpath("//*[@id=\"block-lsac-aboutlsacmenu\"]/ul/li[6]")).click();
		test.log(LogStatus.INFO, " Enter Keyword on  Search Text field");	
		driver.findElementById("edit-search").sendKeys("Law");
		driver.findElementById("edit-submit-sitewide-search").click();
		takeScreenshot();
		test.log(LogStatus.INFO, " Search Results get displayed based on search criteria");	

		List<AndroidElement> searchResult = driver.findElements(By.xpath("//div[@class='views-field views-field-title']"));
		for (WebElement items:searchResult){
		System.out.println(items.getText());
		Thread.sleep(1000);
		
		test.log(LogStatus.INFO, items.getText().toUpperCase());	

}	
		test.log(LogStatus.PASS, "Test Passed");	


	}
	
	

	
	private void takeScreenshot() {
		
		// decide the file name
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
