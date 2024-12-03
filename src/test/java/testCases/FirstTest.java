package testCases;

import java.awt.AWTException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import baseClass.BaseClass;
import constants.Constants;
import driverManager.DriverManager;
import helper.LocalHelper;
import io.appium.java_client.AppiumBy;
import pages.HomePage;
import utils.CommonUtils;

public class FirstTest extends BaseClass {
	private static final Logger logger=LogManager.getLogger(FirstTest.class);
	private HomePage homePage;
	private CommonUtils commonUtils=CommonUtils.getInstance();

	@BeforeClass(alwaysRun = true)
	public void launchApplication() throws MalformedURLException, URISyntaxException {
		homePage = new HomePage(getDriver());
	}
	@BeforeMethod()
	public void loginBro() throws IOException, ParseException {
		homePage.clickLoginButtonBeforeLoginPageReached();
		homePage.clickSettingsButton();
		homePage.selectRandomUserFromUsersList(commonUtils.readJsonValue("userDetail"));
		homePage.clickLoginButtonAfterLoginPageReached();
	}

	@Test(groups = "smoke")
	public void verifyAppHomeScreenBeforeLogin() throws URISyntaxException, IOException, ParseException{
		logger.info("TestCase name:verifyAppHomeScreenBeforeLogin");
		Assert.assertTrue(homePage.verifyFirstSlideText(commonUtils.readJsonValue("firstSlide")),"First Slide Text mismatches");
		logger.info("FirstSlide Text Validated");
		Assert.assertTrue(homePage.verifySecondSlideText(commonUtils.readJsonValue("secondSlide")),"Second Slide Text mismatches");
		logger.info("SecondSlide Text Validated");
		Assert.assertTrue(homePage.verifyThirdSlideText(commonUtils.readJsonValue("thirdSlide")),"third Slide Text mismatches");
		logger.info("thirdSlide Text Validated");
		homePage.clickLoginButtonBeforeLoginPageReached();
		Assert.assertTrue(homePage.verifyLoginPageText(commonUtils.readJsonValue("loginPage")),"Login page dont have welcome text mismatches");
		homePage.clickLoginButtonAfterLoginPageReached();
		Assert.assertTrue(homePage.verifyLoginErrorMessage(commonUtils.readJsonValue("errorMessageForLogin")),"Error message is incorrect");
		homePage.clickSettingsButton();
		homePage.selectRandomUserFromUsersList(commonUtils.readJsonValue("userDetail"));
		homePage.clickLoginButtonAfterLoginPageReached();
		Assert.assertTrue(homePage.verifyLoginHomePageLoginSuccessText(commonUtils.readJsonValue("homePageSuccessMessage")),"Login success message mismatch or wrong");
	}
	@Test()
	public void verifyTimesheet() {
		homePage.clickTimesheetTab();
		homePage.clickFirstFrequency();
		homePage.clickFifteenthDate();
		homePage.enterStartTime();
		homePage.enterEndTime();
		homePage.enterBreaktime();
		Assert.assertTrue(homePage.verifyTotalHours("13:00"),"Mismatch in total hours");
		homePage.clickUpdateButton();
		Assert.assertTrue(homePage.verifyToastMessage("Timesheet for 15 Nov 2024 has been updated successfully!"),"Toast message not appears or incorrect");
	}
	
}
