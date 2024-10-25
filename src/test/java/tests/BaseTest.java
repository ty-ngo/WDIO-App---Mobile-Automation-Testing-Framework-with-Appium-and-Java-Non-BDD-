package tests;

import java.io.File;
import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import core.AppiumDriver.DriverHelper;
import core.AppiumDriver.DriverManager;
import core.report.HtmlReportDirectory;
import core.report.HtmlReporter;

public class BaseTest {

    @BeforeSuite
    public void beforeSuite() throws Exception {
        HtmlReportDirectory.initReportDirectory();
    }

    @BeforeTest
    @Parameters({"platform", "platformVersion", "deviceName"})
    public void beforeTest(@Optional("") String platform, @Optional("") String platformVersion, @Optional("") String deviceName) throws Exception {
        String reportFilePath = HtmlReportDirectory.getReportFolder() + File.separator + "Report.html";
        HtmlReporter.setReporter(reportFilePath);

        HtmlReporter.currentTest = "Test Setup";
        HtmlReporter.createTest("Test Setup", "");
    }

    @BeforeClass
    public void beforeClass() throws Exception {
        HtmlReporter.currentTest = this.getClass().getSimpleName();
        HtmlReporter.createTest(this.getClass().getSimpleName(), "");
    }

    @BeforeMethod
    @Parameters({"platform", "platformVersion", "deviceName"})
    public void beforeMethod(@Optional("") String platform, @Optional("") String platformVersion,
            @Optional("") String deviceName, Method method, Object[] listParameter) throws Exception {
        String methodName = listParameter.length != 0 ? method.getName() + " - " + ArrayUtils.toString(listParameter)
                : method.getName();
        HtmlReporter.createNode(this.getClass().getSimpleName(), methodName, "");
        DriverManager.initDriver("local");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) throws Exception {
        String msg;
        try {
            switch (result.getStatus()) {
                case ITestResult.SUCCESS -> {
                    msg = String.format("The test [%s] is PASSED", result.getName());
                    HtmlReporter.pass(msg, DriverHelper.takeScreenshot());
                }
                case ITestResult.SKIP -> {
                    msg = String.format("The test [%s] is SKIPPED - n%s", result.getName(),
                            result.getThrowable().getMessage());
                    HtmlReporter.skip(msg, result.getThrowable());
                }
                case ITestResult.FAILURE -> {
                    msg = String.format("The test [%s] is FAILED - %s", result.getName(),
                            result.getThrowable().getMessage());
                    HtmlReporter.fail(msg, result.getThrowable(), DriverHelper.takeScreenshot());
                }
                default -> {
                }
            }
        } catch (Exception e) {
        } finally {
            DriverManager.quitDriver();
        }

    }

    @AfterClass(alwaysRun = true)
    public void afterClass() throws Exception {
    }

    @AfterTest(alwaysRun = true)
    public void afterTest() throws Exception {
        HtmlReporter.flush();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() throws Exception {
    }
}
