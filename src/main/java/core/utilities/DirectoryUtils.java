package core.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;

public class DirectoryUtils {

	public static String getRootProject() {
		return System.getProperty("user.dir");
	}

	// public static String getResourcePath(String filename) throws Exception {
	// 	try {
	// 		URL rsc = FilePaths.class.getResource(filename);
	// 		return Paths.get(rsc.toURI()).toFile().getAbsolutePath();
	// 	} catch (Exception e) {
	// 		//Log.info("Cannot get resource file path");
	// 		throw e;
	// 	}
	// }

	public static String correctPath(String path) {

		return path.replaceAll("\\\\|/", "\\" + System.getProperty("file.separator"));

	}

	public static boolean pathExist(String path) {

		return Files.exists(new File(path).toPath());

	}

	public static void deletePath(String path) throws IOException {

		Files.delete(new File(path).toPath());

	}

	public static void createFile(String path) throws Exception {

		if (!pathExist(path))
			Files.createFile(new File(path).toPath());
	}

	public static void createDirectory(String path) throws IOException {

		if (!pathExist(path))
			Files.createDirectory(new File(path).toPath());
	}

	public static void copyFile(String src, String dest) throws IOException {

		File sourceFile = new File(src);
		File destFile = new File(dest);
		Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

	}

	public static void copyDirectory(String src, String dest) throws IOException {

		File sourceFile = new File(src);
		File destFile = new File(dest);
		createDirectory(dest);
		FileUtils.copyDirectory(sourceFile, destFile, true);
	}
}
