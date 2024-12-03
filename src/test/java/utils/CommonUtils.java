package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import constants.Constants;
import driverManager.DriverManager;

public class CommonUtils {
	private static final Logger logger = LogManager.getLogger(CommonUtils.class);
	private volatile static CommonUtils instance;

	private CommonUtils() {
	}

	public static CommonUtils getInstance() {
		if (instance == null) {
			synchronized (CommonUtils.class) {
				if (instance == null) {
					instance = new CommonUtils();
				}
			}
		}
		return instance;
	}

	public static void loadProperties(String browserName) throws IOException {
		try {
			FileReader reader = new FileReader(new File("src\\test\\resources\\Config.properties"));
			Properties properties = new Properties();
			properties.load(reader);
			Constants.application_url = properties.getProperty("application_url");
			logger.info("browserName is :"+browserName);
			logger.info("ApplicationUrl is :"+Constants.application_url );
			Constants.Device = properties.getProperty("Device");
			Constants.appiumServerURL = properties.getProperty("appiumServerURL");
			Constants.appiumServerPort = properties.getProperty("appiumServerPort");
			Constants.appLocation = properties.getProperty("appLocation");
			Constants.androidDeviceName = properties.getProperty("androidDeviceName");
			Constants.iosDeviceName = properties.getProperty("iosDeviceName");
			//Constants.browserName=browserName;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static String screenshot(String fileName) throws IOException, URISyntaxException {
		TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
	    File screenshotAs = ts.getScreenshotAs(OutputType.FILE);
	    String screenshotPath = System.getProperty("user.dir") + File.separator + "target" + File.separator + "screenshots";
	    File directory = new File(screenshotPath);
	    if (!directory.exists()) {
	        directory.mkdirs();
	    }
	    File destination = new File(directory, fileName + ".png");
	    FileHandler.copy(screenshotAs, destination);
	    return destination.getAbsolutePath();
	}

	public static String screenshotAsBase64(WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
	    return ts.getScreenshotAs(OutputType.BASE64);
	}

	public static String todayDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH-mm");
		return sdf.format(date);
	}
	public static void cleanAllureResults() {
		File allureResultsDir = new File(System.getProperty("user.dir") + "/allure-results");
	    if (allureResultsDir.exists()) {
	        for (File file : Objects.requireNonNull(allureResultsDir.listFiles())) {
	            file.delete();
	        }
	    }
    }
	public String readJsonValue(String key) throws IOException, ParseException {
		File file=new File(System.getProperty("user.dir")+File.separator+"src//test//resources"+File.separator+"input.json");
		FileReader reader=new FileReader(file);
		JSONParser parser=new JSONParser();
		Object parse = parser.parse(reader);
		JSONObject obj=(JSONObject)parse;
	    for (Object entryKey : obj.keySet()) {
	        Object value = obj.get(entryKey);
	        if (value instanceof JSONObject) {
	            JSONObject nestedObj = (JSONObject) value;
	            if (nestedObj.containsKey(key)) {
	                return nestedObj.get(key).toString();
	            }
	        } else if (key.equals(entryKey)) {
	            return value.toString();
	        }
		}
		return null;
	}

}
