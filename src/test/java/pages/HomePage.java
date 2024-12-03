package pages;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import helper.LocalHelper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomePage extends LocalHelper{
	private static final Logger logger = LogManager.getLogger(HomePage.class);
	private AppiumDriver driver;

	public HomePage(AppiumDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.atlashxm.uat:id/intro_textView']")
	private WebElement welcomeTextBeforeLogin;
	@AndroidFindBy(id = "com.atlashxm.uat:id/intro_subtextView")
	private WebElement welcomePageTextForSwipe;
	@AndroidFindBy(id = "com.atlashxm.uat:id/iv1")
	private WebElement firstButton;
	@AndroidFindBy(id = "com.atlashxm.uat:id/iv2")
	private WebElement nextButton;
	@AndroidFindBy(id = "com.atlashxm.uat:id/intro_textView")
	private WebElement secondSlideText;
	@AndroidFindBy(id = "com.atlashxm.uat:id/iv3")
	private WebElement lastNextButton;
	@AndroidFindBy(id = "com.atlashxm.uat:id/intro_textView")
	private WebElement lastSlideText;
	@AndroidFindBy(id = "com.atlashxm.uat:id/btnContinue")
	private WebElement loginButton;
	@AndroidFindBy(id = "com.atlashxm.uat:id/settingsButton")
	private WebElement settingsButton;
	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.atlashxm.uat:id/email\"]")
	private List<WebElement> allUsersList;
	//@AndroidFindBy(id = "com.atlashxm.uat:id/main_heading")
	private By hiText=By.id("com.atlashxm.uat:id/main_heading");
	private By errorMEssage=By.id("com.atlashxm.uat:id/error_message");
	//@AndroidFindBy(id = "com.atlashxm.uat:id/txtWelcome")
	private By welcomeTextOnLoginPage=By.xpath("//android.widget.TextView[@resource-id='com.atlashxm.uat:id/txtWelcome']");
	//@AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id=\"com.atlashxm.uat:id/btnCard\"]/android.view.ViewGroup")
	private By loginButtonInLoginPage=By.xpath("//android.widget.FrameLayout[@resource-id=\"com.atlashxm.uat:id/btnCard\"]/android.view.ViewGroup");
	private By timesheetTab=By.xpath("//android.widget.TextView[@resource-id=\"com.atlashxm.uat:id/navigation_bar_item_small_label_view\" and @text=\"Timesheet\"]");
	private By firstFrequency=By.xpath("(//android.widget.ImageView[@resource-id=\"com.atlashxm.uat:id/iv_drop_down\"])[2]");
	private By fifteenthNovember=By.xpath("(//android.widget.ImageView[@resource-id=\"com.atlashxm.uat:id/iv_edit_icon\"])[1]");
	private By startTime=By.id("com.atlashxm.uat:id/start_time_text");
	private By endTime=By.id("com.atlashxm.uat:id/end_time_text");
	private By breakTime=By.id("com.atlashxm.uat:id/totalBreakTime");
	private By updateButton=By.id("com.atlashxm.uat:id/button_save");
	private By startTimeHourAndMinute=By.xpath("//android.widget.EditText[@resource-id=\"android:id/numberpicker_input\"]");
	private By okButton=By.id("android:id/button1");
	private By totalHours=By.id("com.atlashxm.uat:id/totalHours");
	private By toastMessage=By.xpath("//android.widget.toast");
	
	public boolean verifyFirstSlideText(String text) {
		logger.info("Expected Text is from json file:"+text);
		//swipeGesture(welcomePageTextForSwipe,"left");
		//clickElement(firstButton);
		String textFromElement = getTextFromElement(welcomeTextBeforeLogin);
		logger.info("First Slide Text is:"+textFromElement);
		return textFromElement.equalsIgnoreCase(text);
	}
	public boolean verifySecondSlideText(String text) {
		logger.info("Expected Text is from json file:"+text);
		swipeGesture(welcomePageTextForSwipe,"right");
		String textFromElement = getTextFromElement(secondSlideText);
		logger.info("Second Slide Text is:"+textFromElement);
		return textFromElement.equalsIgnoreCase(text);
	}
	public boolean verifyThirdSlideText(String text) {
		logger.info("Expected Text is from json file:"+text);
		swipeGesture(welcomePageTextForSwipe,"right");
		String textFromElement = getTextFromElement(lastSlideText);
		logger.info("Third Slide Text is:"+textFromElement);
		return textFromElement.equalsIgnoreCase(text);
	}
//	public void swipeToSecondSlide() {
//		swipeGesture(secondSlideText,"");
//		logger.info("Next slide button clicked");
//	}
//	public void clickThirdSlide() {
//		clickElement(lastNextButton);
//		logger.info("Next slide button clicked");
//	}
	public void clickLoginButtonBeforeLoginPageReached() {
		clickElement(loginButton);
		logger.info("Login button clicked");
	}
	public boolean verifyLoginPageText(String text) {
		String textFromElement = getTextFromElement(welcomeTextOnLoginPage);
		logger.info("Text grabbed from Welcome PAge:"+textFromElement);
		logger.info("Text from JSON file:"+text);
		return textFromElement.contains(text);
	}
	public boolean verifyLoginButtonDisabled() {
		return checkElementDisabled(loginButtonInLoginPage);
	}
	public void clickLoginButtonAfterLoginPageReached() {
		clickElement(loginButtonInLoginPage);
		logger.info("Login button clicked");
	}
	public void clickSettingsButton() {
		clickElement(settingsButton);
		logger.info("Settings button clicked");
	}
	public void selectRandomUserFromUsersList(String user) {
		selectParticularElementFromListUsingParticularText(allUsersList, user);
	}
	public boolean verifyLoginHomePageLoginSuccessText(String text) {
		String textFromElement = getTextFromElement(hiText);
		textFromElement=textFromElement.replaceAll(" ","");
		textFromElement=textFromElement.replaceAll(" ","");
		textFromElement=textFromElement.replaceAll(" ","");
		logger.info("Web After Login Success text:"+textFromElement.trim());
		logger.info("From json file:"+text);
		return textFromElement.contains(text);
	}
	public boolean verifyLoginErrorMessage(String text) {
		return getTextFromElement(errorMEssage).contains(text);
	}
	public void clickTimesheetTab() {
		clickElement(timesheetTab);
	}
	public void clickFirstFrequency() {
		clickElement(firstFrequency);
	}
	public void clickFifteenthDate() {
		clickElement(fifteenthNovember);
	}
	public void clickStartTimeBox() {
		clickElement(startTime);
	}
	public void enterStartTime() {
		clickElement(startTime);
		clickElementFirstElement(startTimeHourAndMinute);
		pressKeys("08");
		//sendValueToFirstTextBox(startTimeHourAndMinute,"08");
		clickElementSecondElement(startTimeHourAndMinute);
		//sendValueToFirstTextBox(startTimeHourAndMinute,"00");
		pressKeys("00");
		clickElement(okButton);
	}
	public void enterEndTime() {
		clickElement(endTime);
		clickElementFirstElement(startTimeHourAndMinute);
		pressKeys("22");
		clickElementSecondElement(startTimeHourAndMinute);
		pressKeys("00");
		clickElement(okButton);
	}
	public void enterBreaktime() {
		clickElement(breakTime);
		clickElementFirstElement(startTimeHourAndMinute);
		pressKeys("01");
		clickElement(okButton);
	}
	public boolean verifyTotalHours(String text) {
		String textFromElement = getTextFromElement(totalHours);
		logger.info("Total Hours is:"+textFromElement);
		return textFromElement.equalsIgnoreCase(text);
	}
	public void clickUpdateButton() {
		clickElement(updateButton);
	}
	public boolean verifyToastMessage(String text) {
		String textFromElement = getTextFromElement(toastMessage);
		return textFromElement.contains(text);
	}
}
