package utils;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ExtentReportListener extends TestListenerAdapter{
	private static final Logger logger=LogManager.getLogger(ExtentReportListener.class);
	public static ExtentTest test;
	private int passedTests = 0;
    private int failedTests = 0;
    private int skippedTests = 0;
    @Override
    public void onTestStart(ITestResult result) {
        test = ExtentReportManager.createTest(result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result){
    	failedTests++;
        test.log(Status.FAIL, "Test failed");
//        String screenshot = null;
//		try {
//			screenshot = CommonUtils.screenshot(result.getName());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        test.addScreenCaptureFromPath(screenshot,"This is listener Bro");
    }
    public static ExtentTest getTest() {
    	return test;
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test passed");
        passedTests++;
    }
    @Override
    public void onTestSkipped(ITestResult result) {
        skippedTests++;
    }
    @Override
    public void onFinish(ITestContext context) {
    	logger.info("Execution Summary:");
    	logger.info("Total Passed Tests: " + passedTests);
    	logger.info("Total Failed Tests: " + failedTests);
    	logger.info("Total Skipped Tests: " + skippedTests);
    }
}
