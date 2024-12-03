package pages;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.google.common.base.Optional;

import driverManager.DriverManager;
import helper.LocalHelper;
import io.appium.java_client.AppiumDriver;

public class SecondPage extends LocalHelper {
	private static final Logger logger = LogManager.getLogger(SecondPage.class);
	//private volatile static SecondPage instance;
	List<String> pressKeysAndReturnTextFromChangeOccured;
	String parentWindow;
	String tableValue;
	String firstFlash;
	String beforeClickCssValue;
	WebDriver driver;
	public SecondPage(AppiumDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);

	}

//	public static SecondPage getInstance() {
//		if (instance == null) {
//			//synchronized (HomePage.class) {
//				if (instance == null) {
//					instance = new SecondPage();
//				}
//		//	}
//		}
//		return instance;
//	}

	@FindBy(xpath = "//button[text()='Where am I?']")
	private WebElement whereAmIButton;
}
