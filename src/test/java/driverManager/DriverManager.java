package driverManager;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import baseClass.BaseClass;
import constants.Constants;
import helper.LocalHelper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class DriverManager {
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<String> device = new ThreadLocal<>();

    public enum DeviceType {
        ANDROID, IOS
    }

    public static void setDeviceName(String deviceName) {
        device.set(deviceName);
    }

    public static AppiumDriver getDriver() throws MalformedURLException, URISyntaxException {
        if (driver.get() == null) {
            logger.info("Initializing driver for thread: " + Thread.currentThread().getId());
            AppiumDriver webDriver = DriverFactory.getDriver(DeviceType.valueOf(device.get().toUpperCase()));
            driver.set(webDriver);
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            logger.info("Quitting driver for thread: " + Thread.currentThread().getId());
            driver.get().quit(); // Quit the driver
            driver.remove(); // Remove the driver reference from ThreadLocal
            if (DriverFactory.service != null && DriverFactory.service.isRunning()) {
                DriverFactory.service.stop();
                logger.info("Service stopped");
            }
            logger.info("Driver quit and removed for thread: " + Thread.currentThread().getId());
        }
    }
}
