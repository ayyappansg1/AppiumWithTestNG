package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
	 private static ExtentReports extent;
	 private static ExtentTest test;
	 public static ExtentReports getInstance() {
	        if (extent == null) {
	            extent = new ExtentReports();
	           ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extentSpark.html");
	           // ExtentSparkReporter spark = new ExtentSparkReporter("Spark.html");

	            extent.attachReporter(sparkReporter);
	        }
	        return extent;
	    }
	 public static ExtentTest createTest(String testName) {
	        test = extent.createTest(testName);
	        return test;
	    }
	    
}
