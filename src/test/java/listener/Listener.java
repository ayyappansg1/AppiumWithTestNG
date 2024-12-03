package listener;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.devtools.v129.page.model.Screenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import utils.CommonUtils;

public class Listener implements ITestListener {
	private static final Logger logger = LogManager.getLogger(Listener.class);
	protected ExtentSparkReporter sparkReporter;
	protected ExtentReports reports;
	protected ExtentTest test;

	@Override
	public void onFinish(ITestContext context) {
		logger.info("Execution finished ");
	}

	@Override
	public void onStart(ITestContext context) {
		logger.info("Test case started : " + context.getName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		logger.error("Test case Skipped : " + result.getMethod());
	}
//	public void onTestStart(ITestResult result) {
//		sparkReporter=new ExtentSparkReporter(new File("extent-listener.html"));
//		reports.attachReporter(sparkReporter);
//		test = reports.createTest("Test started:"+result.getName());
//	  }
//	 public void onTestSuccess(ITestResult result) {
//		 test.pass("Test passed"+result.getName());
//		  }
//	
//	public void onTestFailure(ITestResult result) {
//		test.fail("Test got failed"+result.getName());
//		try {
//			test.addScreenCaptureFromPath(CommonUtils.getInstance().screenshot(result.getName()));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
