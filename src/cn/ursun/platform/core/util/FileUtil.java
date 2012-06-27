package cn.ursun.platform.core.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileUtil {

	public static boolean createNewFile(File f, boolean mkdirs) throws IOException {
		File parent = f.getParentFile();
		if (mkdirs && !parent.exists())
			parent.mkdirs();
		return f.createNewFile();
	}

	public static boolean deleteQuietly(File file) {
		return FileUtils.deleteQuietly(file);
	}

	public static void deleteDirectory(File directory) throws IOException {
		FileUtils.deleteDirectory(directory);
	}

}
