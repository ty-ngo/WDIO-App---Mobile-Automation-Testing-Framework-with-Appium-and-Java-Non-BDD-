package core.report;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class HtmlReporter {

	private static ExtentReports _report;

	private static final HashMap<String, ExtentTest> extentTestMap = new HashMap<>();
	
	private static final Boolean IS_RELATIVE_SCREENSHOT = true;
	
	public static String currentTest;

	public static ExtentReports setReporter(String filename) throws IOException {

		if (_report == null) {
			_report = createInstance(filename);
		}
		_report.setAnalysisStrategy(AnalysisStrategy.CLASS);
		return _report;
	}

	public static ExtentReports createInstance(String fileName) throws IOException {

		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(false);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle(fileName);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(fileName);
		//adding CSS and Javascript for API Request Step
		// InputStream jsFile = HtmlReporter.class.getClassLoader().getResourceAsStream("config/extent-report/api-step-javascript-code.js");
		// InputStream cssFile = HtmlReporter.class.getClassLoader().getResourceAsStream("config/extent-report/api-step-stylesheet.css");
		// String jsCode = IOUtils.toString(jsFile, StandardCharsets.UTF_8);
		// String cssCode = IOUtils.toString(cssFile, StandardCharsets.UTF_8);
		// htmlReporter.config().setJS(jsCode);
		// htmlReporter.config().setCSS(cssCode);
		//end
		
		htmlReporter.setAppendExisting(true);

		ExtentReports report = new ExtentReports();
		report.attachReporter(htmlReporter);
		report.setSystemInfo("Application", "Franki Automation Tests");
		//Log.info("report path:" + fileName);
		return report;
	}

	public static void flush() {
		if (_report != null) {
			_report.flush();
			_report = null;
		}
	}

	public static ExtentTest createTest(String strTestMethodName, String strTestMethodDesc) {
		
		ExtentTest test = _report.createTest(strTestMethodName, strTestMethodDesc);
		extentTestMap.put("test_" + currentTest, test);
		return test;
	}

	public static ExtentTest createTest(String strTestClassName) {

		ExtentTest test = _report.createTest(strTestClassName);
		extentTestMap.put("test_" + currentTest, test);
		return test;
	}

	public static ExtentTest createNode(String strClassName, String strTestMethodName,
			String strTestMethodDesc) {
		ExtentTest test = getParent();
		if (test == null) {
			test = createTest(strClassName);
		}

		ExtentTest node = test.createNode(strTestMethodName, strTestMethodDesc);
		extentTestMap.put("node_" + Thread.currentThread().getId(), node);
		return node;
	}

	public static ExtentTest getParent() {
		ExtentTest node = extentTestMap.get("test_" + currentTest);
		if(node == null) {
			node = createTest("Test Setup");
		}
		return node;
	}

	public static ExtentTest getNode() {
		return extentTestMap.get("node_" + Thread.currentThread().getId());
	}

	public static ExtentTest getTest() {
		if (getNode() == null) {
			return getParent();
		} else {
			if (!getNode().getModel().getParent().getName().equalsIgnoreCase(currentTest)) {
				return getParent();
			}
			return getNode();
		}
	}

	public static void pass(String strDescription) {
		getTest().pass(strDescription);
		//Log.info(strDescription);
	}

	public static void pass(Markup m) {
		getTest().pass(m);
	}

	public static void warning(String strDescription) {
		getTest().warning(strDescription);
		//Log.warn(strDescription);
	}

	public static void info(String strDescription) {
		getTest().info(strDescription);
		//Log.info(strDescription);
	}
	
	public static void info(Markup markup) {
		getTest().info(markup);
	}

	public static void pass(String strDescription, String strScreenshotPath) throws Exception {

		if (strScreenshotPath.equalsIgnoreCase("")) {
			getTest().pass(strDescription);
		} else {
			strScreenshotPath = getScreenshotPath(strScreenshotPath);
			getTest().pass(strDescription).addScreenCaptureFromPath(strScreenshotPath);
		}

		// Log.info(strDescription);

	}

	public static void fail(String strDescription)  {
		getTest().fail(strDescription);
		// Log.error(strDescription);
	}

	public static void fail(Markup m)  {
		getTest().fail(m);
	}
	
	public static void fail(String strDescription,Throwable e)  {
		getTest().fail(strDescription).fail(e);
		// Log.error(strDescription + "\n");
	}

	public static void fail(String strDescription, String strScreenshotPath) throws IOException  {

		if (strScreenshotPath.equalsIgnoreCase("")) {
			getTest().fail(strDescription);
		} else {
			strScreenshotPath = getScreenshotPath(strScreenshotPath);
			getTest().fail(strDescription).addScreenCaptureFromPath(strScreenshotPath);
		}

		// Log.error(strDescription);

	}

	public static void fail(String strDescription, Throwable e, String strScreenshotPath) throws IOException  {

		if (strScreenshotPath.equalsIgnoreCase("")) {
			getTest().fail(strDescription).fail(e);
		} else {
			strScreenshotPath = getScreenshotPath(strScreenshotPath);
			getTest().fail(strDescription).fail(e).addScreenCaptureFromPath(strScreenshotPath);
		}
		// Log.error(strDescription + "\n" + e.getMessage());
	}

	public static void skip(String strDescription) throws IOException  {
		getTest().skip(strDescription);
	}

	public static void skip(String strDescription, Throwable e) throws IOException  {
		getTest().skip(strDescription).fail(e);
	}

	public static void skip(String strDescription, String strScreenshotPath) throws IOException  {

		if (strDescription.equalsIgnoreCase("")) {
			getTest().skip(strDescription);
		} else {
			strScreenshotPath = getScreenshotPath(strScreenshotPath);
			getTest().skip(strDescription).addScreenCaptureFromPath(strScreenshotPath);
		}

		// Log.info(strDescription);
	}

	public static void skip(String strDescription, Throwable e, String strScreenshotPath) throws IOException {

			if (strDescription.equalsIgnoreCase("")) {
				getTest().skip(strDescription).skip(e);
			} else {
				strScreenshotPath = getScreenshotPath(strScreenshotPath);
				getTest().skip(strDescription).skip(e).addScreenCaptureFromPath(strScreenshotPath);
			}
		
		// Log.info(strDescription);
	}

	public static void label(String strDescription)  {

			getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.BLUE));

	}

	public static void labelFailed(String strDescription)  {

			getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.RED));

	}

	public static void labelSkiped(String strDescription) {

			getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.AMBER));
			
	}

	public static void labelWarning(String strDescription)  {

			getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.ORANGE));

	}

	public static void pass(String[] data) throws Exception {
		if (data.length == 2) {
			pass(data[1]);
		} else if (data.length == 3) {
			pass(data[1], data[2]);
		}
	}

	public static void fail(String[] data) throws Exception {
		if (data.length == 3) {
			HtmlReporter.fail(data[1], data[2]);
		} else if (data.length == 4) {
			HtmlReporter.fail(data[1], data[3]);
		}
	}

	public static void description(String data) throws Exception {
		try {
			getTest().info(MarkupHelper.createCodeBlock(data));
		} catch (Exception ex) {
			// Log.info("Can't write to htm report, initialize it first");
		}
	}
	
	private static String getScreenshotPath(String strAbsolutePath) {
		if(IS_RELATIVE_SCREENSHOT) {
			return new File(HtmlReportDirectory.getReportFolder()).toPath().relativize(new File(strAbsolutePath).toPath()).toString();
		}
		else {
			return "file:///" + strAbsolutePath;
		}
	}
}
