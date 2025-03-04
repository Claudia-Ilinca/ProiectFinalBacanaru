package Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Utility class for logging test execution details using Log4j2.
 * Logs to both console and files, with suite consolidation.
 */
public class LoggerUtility {

    private static final String SUITE_LOGS_DIR = "target/logs/suite/";
    private static final String REGRESSION_LOG_FILE = "target/logs/regressionLogs.log";
    private static final Logger logger = LogManager.getLogger(LoggerUtility.class);
    private static String currentTestName;

    public static synchronized void startTest(String testName) {
        currentTestName = testName;

        // Creează directorul dacă nu există
        File suiteDir = new File(SUITE_LOGS_DIR);
        if (!suiteDir.exists()) {
            suiteDir.mkdirs();
        }

        // Adaugă un appender dinamic pentru testul curent
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();
        PatternLayout layout = PatternLayout.newBuilder()
                .withPattern("%d{yyyy-MM-dd HH:mm:ss} [%t] %-5level %msg%n")
                .build();
        FileAppender appender = FileAppender.newBuilder()
                .withFileName(SUITE_LOGS_DIR + testName + ".log")
                .setName("TestFile-" + testName)
                .setLayout(layout)
                .withImmediateFlush(true)
                .withAppend(false)
                .build();
        appender.start();
        config.getRootLogger().addAppender(appender, null, null);
        context.updateLoggers();

        logger.info("=== Starting test: {} ===", testName);
    }

    public static synchronized void endTest(String testName) {
        logger.info("=== Ending test: {} ===", testName);

        // Elimină appender-ul specific testului
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();
        FileAppender appender = (FileAppender) config.getAppender("TestFile-" + testName);
        if (appender != null) {
            config.getRootLogger().removeAppender("TestFile-" + testName);
            appender.stop();
            context.updateLoggers();
        }
        currentTestName = null;
    }

    public static synchronized void info(String message) {
        logger.info(message);
    }

    public static synchronized void error(String message) {
        logger.error(message);
    }

    public static void mergeLogsIntoOne() {
        File suiteDir = new File(SUITE_LOGS_DIR);
        String[] logFiles = suiteDir.list();

        if (logFiles == null || logFiles.length == 0) {
            logger.warn("No log files found in {}", SUITE_LOGS_DIR);
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(REGRESSION_LOG_FILE, true))) { // Append = true pentru a păstra logurile existente
            for (String logFile : logFiles) {
                File file = new File(suiteDir, logFile);
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    writer.println("=== Content of " + logFile + " ===");
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.println(line);
                    }
                    writer.println();
                }
            }
            logger.info("Merged logs into {}", REGRESSION_LOG_FILE);
        } catch (IOException e) {
            logger.error("Failed to merge logs: {}", e.getMessage());
        }
    }
}