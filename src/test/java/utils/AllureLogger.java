package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AllureLogger {
	private static final Logger logger=LogManager.getLogger(AllureLogger.class);

//	@Attachment(value = "Logs", type = "text/plain")
//    public static byte[] attachLogFile() {
//        try {
//        	File logDirectory = new File("log");
//
//            // Get the latest log file based on the last modified time
//            File latestLogFile = Arrays.stream(logDirectory.listFiles())
//                .filter(File::isFile)
//                .max(Comparator.comparingLong(File::lastModified))
//                .orElseThrow(() -> new IOException("No log files found in the log directory."));
//
//            Path logPath = Paths.get(latestLogFile.getAbsolutePath());  // Your log file path
//            return Files.readAllBytes(logPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
	public static void attachLogFile() throws IOException {
		logger.info("Trying to Attach Log file into Allure");
      	File logDirectory = new File(System.getProperty("user.dir")+File.separator+"log");
          // Get the latest log file based on the last modified time
          File latestLogFile = Arrays.stream(logDirectory.listFiles())
              .filter(File::isFile)
              .max(Comparator.comparingLong(File::lastModified))
              .orElseThrow(() -> new IOException("No log files found in the log directory."));
          if (latestLogFile != null && latestLogFile.exists()) {
              FileInputStream logStream = new FileInputStream(latestLogFile);
              logger.info("File name is :"+latestLogFile);
          } else {
              logger.warn("No log file found to attach.");
          }

  }
}
