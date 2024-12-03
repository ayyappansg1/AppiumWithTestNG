package driverManager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.Constants;
import driverManager.DriverManager.DeviceType;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class DriverFactory {
	private static final Logger logger=LogManager.getLogger(DriverFactory.class);
    public static AppiumDriverLocalService service;
    
	 public static AppiumDriver getDriver(DeviceType deviceType) throws MalformedURLException, URISyntaxException {
	        AppiumDriver driver = null;
	        if (service == null || !service.isRunning()) {
	            logger.info("Starting Appium service");
	            service = new AppiumServiceBuilder()
	                .withAppiumJS(new File("C:\\Users\\ayyappan.g.DC\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
	                .withIPAddress(Constants.appiumServerURL)
	                .usingPort(Integer.parseInt(Constants.appiumServerPort))
	                .build();
	            service.start();
	        }
	        switch (deviceType) {
			case ANDROID:
				logger.info("Launching Android Emulator Device");
				UiAutomator2Options options=new UiAutomator2Options();
				options.setDeviceName(Constants.androidDeviceName);
				options.setNewCommandTimeout(Duration.ofSeconds(50000));
				String appLocation=System.getProperty("user.dir")+File.separator+"src\\test\\resources"+File.separator+"app-UAT-debug.apk";
				logger.info("App location is "+appLocation);
				options.setApp(appLocation);
				driver=new AndroidDriver(options);
				logger.info("inside getDriver Actual");
				break;
			case IOS:
				logger.info("Launching Chrome Browser");
				XCUITestOptions optionsIos=new XCUITestOptions();
				optionsIos.setDeviceName(Constants.iosDeviceName );
				optionsIos.setApp(Constants.iosAppLocation);
				optionsIos.setPlatformVersion("15.5");
				optionsIos.setWdaLaunchTimeout(Duration.ofSeconds(30));
				driver=new IOSDriver(optionsIos);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
				break;
			default:
				logger.info("Launching Android Emulator Device");
				UiAutomator2Options optionss=new UiAutomator2Options();
				optionss.setDeviceName(Constants.androidDeviceName);
				optionss.setNewCommandTimeout(Duration.ofSeconds(3000));
				String appLocatiosn=System.getProperty("user.dir")+File.separator+"src\\test\\resources"+File.separator+"app-UAT-debug.apk";
				logger.info("App location is "+appLocatiosn);
				optionss.setApp(appLocatiosn);
				driver=new AndroidDriver(optionss);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
				break;
			}
	        if (driver != null) {
	            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
	        }
	        return driver;
	 }
	 

}
