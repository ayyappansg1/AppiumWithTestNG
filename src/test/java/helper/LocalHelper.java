package helper;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.ImmutableMap;

import driverManager.DriverManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumBy.ByAndroidUIAutomator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.remote.SupportsRotation;

/**
 * Local helper class contains all Reusable action able methods with Wait
 * mechanism added
 */
public class LocalHelper {
	private AppiumDriver driver;
	private static final Logger logger = LogManager.getLogger(LocalHelper.class);

	public LocalHelper(AppiumDriver driver) {
		this.driver = driver;
	}

	long waitSeconds = 60;

	public void clickElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
		wait.until(ExpectedConditions.visibilityOf(element));
//		JavascriptExecutor ex = (JavascriptExecutor) driver;
//		ex.executeScript("arguments[0].scrollIntoView(true)", element);
		try {
			element.click();
		} catch (StaleElementReferenceException e) {
			element.click();
		} catch (TimeoutException m) {
			// ex.executeScript("arguments[0].click();", element);
		}
	}

	public void clickElement(By element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
		wait.until(ExpectedConditions.presenceOfElementLocated(element));
//		JavascriptExecutor ex = (JavascriptExecutor) driver;
//		ex.executeScript("arguments[0].scrollIntoView(true)", element);
		try {
			driver.findElement(element).click();
		} catch (StaleElementReferenceException e) {
			driver.findElement(element).click();
		} catch (TimeoutException m) {
			// ex.executeScript("arguments[0].click();", element);
		}
	}

	public void clickElementFirstElement(By element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
		wait.until(ExpectedConditions.presenceOfElementLocated(element));
//		JavascriptExecutor ex = (JavascriptExecutor) driver;
//		ex.executeScript("arguments[0].scrollIntoView(true)", element);
		try {
			driver.findElements(element).get(0).click();
		} catch (StaleElementReferenceException e) {
			driver.findElements(element).get(0).click();
		} catch (TimeoutException m) {
			// ex.executeScript("arguments[0].click();", element);
		}
	}

	public void clickElementSecondElement(By element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
		wait.until(ExpectedConditions.presenceOfElementLocated(element));
//		JavascriptExecutor ex = (JavascriptExecutor) driver;
//		ex.executeScript("arguments[0].scrollIntoView(true)", element);
		try {
			driver.findElements(element).get(1).click();
		} catch (StaleElementReferenceException e) {
			driver.findElements(element).get(0).click();
		} catch (TimeoutException m) {
			// ex.executeScript("arguments[0].click();", element);
		}
	}

	public String getTextFromElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			return element.getText();
		} catch (StaleElementReferenceException e) {
			return element.getText();
		}
	}

	public String getTextFromElement(By element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
		wait.until(ExpectedConditions.presenceOfElementLocated(element));
		try {
			return driver.findElement(element).getText();
		} catch (StaleElementReferenceException e) {
			return driver.findElement(element).getText();
		}
	}

	public boolean checkElementDisabled(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
		wait.until(ExpectedConditions.visibilityOf(element));
		return element.isDisplayed();
	}

	public boolean checkElementDisabled(By element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		return driver.findElement(element).isEnabled();
	}

	public void selectParticularElementFromListUsingParticularText(List<WebElement> elements, String findText) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
		wait.until(ExpectedConditions.visibilityOfAllElements(elements));
		for (WebElement webElement : elements) {
			if (webElement.getText().contains(findText)) {
				webElement.click();
				break;
			}
		}
	}

	public void swipeGesture(WebElement element, String direction) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		if (direction.equals("left"))
			js.executeScript("mobile:swipeGesture", ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(),
					"direction", "left", "percent", "0.5"));
		else if (direction.equals("right"))
			js.executeScript("mobile:swipeGesture", ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(),
					"direction", "left", "percent", "0.75"));
	}

	public void scrollGestureUsingKnownText(WebElement element, String text) {
		driver.findElement(AppiumBy
				.androidUIAutomator("new UiScrollable(new UiSelector().scrollIntoView(text(\"" + text + "\"));"));
	}

	public void scrollGestureUsingJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("mobile:scroll",
				ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "direction", "down"));
	}

	public void longClickGesture(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("mobile:longClickGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "duration", "2000"));
	}

	public void clickOrTapGesture(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("mobile:clickGesture", ImmutableMap.of("elementId", ((RemoteWebElement) element).getId()));
	}

	public void dragGesture(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("mobile:dragGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "endX", 200, "endY", 350));
	}

	public void landOnParticularPageOnApp(String packageName) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("mobile:startActivity", ImmutableMap.of("intent", packageName));
	}

	public void rotateDevice90Degree() {
		try {
			// Cast to Rotatable and rotate
			AndroidDriver driverAndroid = (AndroidDriver) driver;
			driverAndroid.rotate(new DeviceRotation(0, 0, 90));
		} catch (ClassCastException e) {
			// Fallback if rotate is not directly available
			Map<String, Object> rotationParams = new HashMap<>();
			rotationParams.put("x", 0);
			rotationParams.put("y", 0);
			rotationParams.put("z", 90);
			driver.executeScript("mobile: rotate", rotationParams);
		}
	}

	public AndroidDriver setClipBoard(String content) {
		AndroidDriver driverAndroid = (AndroidDriver) driver;
		driverAndroid.setClipboardText(content);
		return driverAndroid;
	}

	public void pressEnterInKeyboard() {
		AndroidDriver driverAndroid = (AndroidDriver) driver;
		driverAndroid.pressKey(new io.appium.java_client.android.nativekey.KeyEvent(AndroidKey.ENTER));
	}

	public void pressBackInKeyboard() {
		AndroidDriver driverAndroid = (AndroidDriver) driver;
		driverAndroid.pressKey(new io.appium.java_client.android.nativekey.KeyEvent(AndroidKey.BACK));
	}

	public void hideKeyBoard() {
		AndroidDriver driverAndroid = (AndroidDriver) driver;
		driverAndroid.hideKeyboard();
	}

	public void switchToHybridBrowser() {
		AndroidDriver drivers = (AndroidDriver) driver;
		Set<String> contextHandles = drivers.getContextHandles();
		for (String string : contextHandles) {
			if (!string.contains("native"))
				drivers.context(string);
			break;
		}
	}

	public void switchToNativeApp() {
		AndroidDriver drivers = (AndroidDriver) driver;
		drivers.context("NATIVEAPP");
	}

	public void doubleClickFirstElement(By element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
		wait.until(ExpectedConditions.presenceOfElementLocated(element));
		WebElement webElement = driver.findElements(element).get(0);
		Actions actions = new Actions(driver);
		actions.doubleClick(webElement).build().perform();
	}

	public void doubleClickSecondElement(By element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
		wait.until(ExpectedConditions.presenceOfElementLocated(element));
		WebElement webElement = driver.findElements(element).get(1);
		Actions actions = new Actions(driver);
		actions.doubleClick(webElement).build().perform();
	}

	public void sendValueToFirstTextBox(By element, String text) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
		wait.until(ExpectedConditions.presenceOfElementLocated(element));
		WebElement webElement = driver.findElements(element).get(0);
		webElement.sendKeys(text);
	}

	public void typeCharacters(By element, String text) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
		wait.until(ExpectedConditions.presenceOfElementLocated(element));
		WebElement webElement = driver.findElements(element).get(0);
		Actions actions = new Actions(driver);
		char[] charArray = text.toCharArray();
		for (char c : charArray) {
			//actions.keyDown(new io.appium.java_client.android.nativekey.KeyEvent(AndroidKey.dig));
		}
		webElement.sendKeys(text);
	}

	public void pressKeys(String text) {
		Map<Character, AndroidKey> map = new LinkedHashMap<Character, AndroidKey>();
		map.put('a', AndroidKey.A);
		map.put('b', AndroidKey.B);
		map.put('c', AndroidKey.C);
		map.put('d', AndroidKey.D);
		map.put('e', AndroidKey.E);
		map.put('f', AndroidKey.F);
		map.put('g', AndroidKey.G);
		map.put('h', AndroidKey.H);
		map.put('i', AndroidKey.I);
		map.put('j', AndroidKey.J);
		map.put('k', AndroidKey.K);
		map.put('l', AndroidKey.L);
		map.put('m', AndroidKey.M);
		map.put('n', AndroidKey.N);
		map.put('o', AndroidKey.O);
		map.put('p', AndroidKey.P);
		map.put('r', AndroidKey.Q);
		map.put('s', AndroidKey.R);
		map.put('t', AndroidKey.S);
		map.put('u', AndroidKey.T);
		map.put('v', AndroidKey.U);
		map.put('w', AndroidKey.V);
		map.put('x', AndroidKey.W);
		map.put('y', AndroidKey.X);
		map.put('z', AndroidKey.Y);
		map.put('0', AndroidKey.DIGIT_0);
		map.put('1', AndroidKey.DIGIT_1);
		map.put('2', AndroidKey.DIGIT_2);
		map.put('3', AndroidKey.DIGIT_3);
		map.put('4', AndroidKey.DIGIT_4);
		map.put('5', AndroidKey.DIGIT_5);
		map.put('6', AndroidKey.DIGIT_6);
		map.put('7', AndroidKey.DIGIT_7);
		map.put('8', AndroidKey.DIGIT_8);
		map.put('9', AndroidKey.DIGIT_9);
		Set<Entry<Character, AndroidKey>> entrySet = map.entrySet();
		char[] charArray = text.toCharArray();
		for (char c : charArray) {
			AndroidKey androidKey = map.get(c);
			if (androidKey != null) {
	            ((AndroidDriver)driver).pressKey(new io.appium.java_client.android.nativekey.KeyEvent(androidKey));
	        } else {
	            throw new IllegalArgumentException("Unsupported character: " + c);
	        }
		}

	}

	public void sendValueToSecondTextBox(By element, String text) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
		wait.until(ExpectedConditions.presenceOfElementLocated(element));
		WebElement webElement = driver.findElements(element).get(1);
		webElement.sendKeys(text);
	}
}
