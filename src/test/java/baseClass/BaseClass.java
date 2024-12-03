package baseClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestNG;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import driverManager.DriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import utils.CommonUtils;
import utils.ExtentReportListener;
import utils.ExtentReportManager;

public class BaseClass extends DriverManager{
	private static final Logger logger=LogManager.getLogger(BaseClass.class);
	protected MultiPartEmail email;
	protected  ExtentReportListener extentReportListener;
    ExtentReports extent = ExtentReportManager.getInstance();

	@BeforeSuite(alwaysRun = true)
	public void initialisation() throws MalformedURLException, URISyntaxException {
		CommonUtils.cleanAllureResults();
	}
	@Parameters("browser")
	@BeforeClass(alwaysRun = true)
	public void initialSetupInBeforeClass(String browser) throws IOException, URISyntaxException {
        extentReportListener=new ExtentReportListener();
		logger.info("Before Class is running");
		CommonUtils.loadProperties(browser);
		DriverManager.setDeviceName(browser);
	}
	@AfterSuite(alwaysRun = true)
	public void sendEmail() throws EmailException {
	    StringBuilder emailContent = new StringBuilder();
	    emailContent.append("Test Results: ");
	    // Attach Extent report
	    EmailAttachment  extentAttachment = new EmailAttachment();
	    extentAttachment.setPath(System.getProperty("user.dir") + File.separator + "extentSpark.html");
	    extentAttachment.setDisposition(EmailAttachment.ATTACHMENT);
	    extentAttachment.setDescription("Selenium Extent Report");
	    extentAttachment.setName("ExtentReport.html");

	    email = new MultiPartEmail();
	    email.setHostName("smtp.zoho.in");
	    email.setSmtpPort(465);
	    email.setSSLOnConnect(true);
	    email.setAuthentication("ayyappansg1@zohomail.in", "FArvYhxi2Emk");
	    email.setFrom("ayyappansg1@zohomail.in");
	    email.addTo("ayyappangunasekaran5@gmail.com");
	    email.setMsg("Hi sir, Automated Mail");
	    email.setSubject("HerokuApp automation result-" + emailContent.toString() + "- " + dateAndTime());
	    email.setSocketConnectionTimeout(60000); 
	    email.setSocketTimeout(60000);

	    email.attach(extentAttachment);
	       // email.send();
	       // logger.info("Email sent successfully");
	    extent.flush();
	}


	public String dateAndTime() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMMMyyyy h.mma");
		String formattedDateTime = currentDateTime.format(formatter);
		return formattedDateTime;
	}
	@Attachment(value = "{0}", type = "text/plain")
    public static String attachLog(String message) {
        logger.info(message);
        return message;
    }
	@AfterMethod(alwaysRun = true)
	public void tearDownAfterMethod(ITestResult result) throws IOException, URISyntaxException {
		if(result.getStatus()==ITestResult.FAILURE||result.getStatus()==ITestResult.SKIP) {
//			attachScreenshot();
            attachLog(String.valueOf(result.getMethod()).replaceAll("[()]",""));
            String screenshotPath = CommonUtils.screenshot(result.getName());
            logger.info("screenshot absolute path is :"+screenshotPath);
            FileInputStream stream = new FileInputStream(new File(screenshotPath));
			Allure.addAttachment("Screenshot", stream);
//			String base64Screenshot  = CommonUtils.screenshotAsBase64(getDriver());
//			ExtentReportListener.getTest().addScreenCaptureFromBase64String(base64Screenshot, "Screenshot on failure");
		}
	}
	@AfterSuite(alwaysRun = true)
	public void finaltearAfterSuite() {
		DriverManager.quitDriver();
	}

}
