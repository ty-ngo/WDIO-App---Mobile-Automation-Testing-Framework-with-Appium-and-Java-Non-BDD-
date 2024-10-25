package core.AppiumDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.GregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import core.report.HtmlReportDirectory;

public class DriverHelper {

    public static void acceptAlert() {
        WebDriverWait wait = new WebDriverWait(DriverManager.appiumDriver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }

    public static void dismissAlert() {
        WebDriverWait wait = new WebDriverWait(DriverManager.appiumDriver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.dismiss();
    }

    public static String getAlertText() {
        WebDriverWait wait = new WebDriverWait(DriverManager.appiumDriver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        return alert.getText();
    }

    public static String takeScreenshot() {

        String failureImageFileName = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss.SSS")
                .format(new GregorianCalendar().getTime()) + ".jpg";
        try {
            if (DriverManager.appiumDriver != null) {
                File scrFile = ((TakesScreenshot) DriverManager.appiumDriver).getScreenshotAs(OutputType.FILE);
                String screenShotDirector = HtmlReportDirectory.getScreenshotFolder();
                FileUtils.copyFile(scrFile, new File(screenShotDirector + File.separator + failureImageFileName));
                return screenShotDirector + File.separator + failureImageFileName;
            }
            return "";
        } catch (IOException | WebDriverException e) {
            return "";
        }
    }
}
