package core.report;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import core.utilities.DirectoryUtils;

public class HtmlReportDirectory {

	private static String REPORT_ROOT_FOLDER;
	private static String REPORT_FOLDER;
	private static String SCREENSHOT_FOLDER;
	private static String REPORT_FILE_PATH;

	public static String getRootProject() {
		return System.getProperty("user.dir");
	}

	public static String getResourcePath(String filename) throws Exception {
			URL rsc = HtmlReportDirectory.class.getResource(filename);
			return Paths.get(rsc.toURI()).toFile().getAbsolutePath();
	}

	public static void initReportDirectory() throws IOException {
		REPORT_ROOT_FOLDER = getRootProject() + File.separator + "Reports";
		REPORT_FOLDER = REPORT_ROOT_FOLDER + File.separator + "Latest Report";
		checkExistReportAndReName(REPORT_FOLDER);
		SCREENSHOT_FOLDER = REPORT_FOLDER + File.separator + "Screenshots";
		REPORT_FILE_PATH = REPORT_FOLDER + File.separator + "Report.html";
		DirectoryUtils.createDirectory(REPORT_ROOT_FOLDER);
		DirectoryUtils.createDirectory(REPORT_FOLDER);
		DirectoryUtils.createDirectory(SCREENSHOT_FOLDER);
	}

	private static void checkExistReportAndReName(String path) throws IOException {
		if (Files.exists(Paths.get(path))) {
			File oldReport = new File(path);
			BasicFileAttributes oldReportAttribute = Files.readAttributes(oldReport.toPath(),
					BasicFileAttributes.class);
			File renameOldReport = new File(getRootProject() + File.separator + "Reports" + File.separator + "Report_"
					+ oldReportAttribute.creationTime().toString().replace(":", "."));
			oldReport.renameTo(renameOldReport);
		}
	}

	public static String getReportFolder() {
		return REPORT_FOLDER;
	}

	public static String getScreenshotFolder() {
		return SCREENSHOT_FOLDER;
	}

	public static String getReportFilePath() {
		return REPORT_FILE_PATH;
	}

}
