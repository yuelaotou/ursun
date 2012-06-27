package cn.ursun.platform.core.configloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import cn.ursun.platform.core.util.FileUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResource;

import cn.ursun.platform.core.util.UnJar;

public class LoadAppProgram {

	protected static final Log logger = LogFactory.getLog(LoadAppProgram.class);

	/**
	 * 应用程序jar文件名规则参数
	 */
	public static final String APP_JAR_PATTERN_PARAM = "appJarPattern";

	/**
	 * 默认应用程序jar文件名规则
	 */
	public static final String APP_JAR_FILENAME_PATTERN = "wee-(app-)?\\w+\\.jar";

	public void setUpAppProgram(ServletContext servletContext) {
		// 获取应用程序存放路径
		String appJarPattern = servletContext.getInitParameter(APP_JAR_PATTERN_PARAM);
		if (null == appJarPattern || "".equals(appJarPattern)) {
			appJarPattern = APP_JAR_FILENAME_PATTERN;
		}
		Resource root = new ServletContextResource(servletContext, "/WEB-INF/lib");
		Properties properties = new Properties();
		InputStream is = null;
		Resource jarLoadTimeResource = new ServletContextResource(servletContext, "WEB-INF/jar-loadingtime.properties");

		try {
			File jarFilePath = root.getFile();
			File jarLoadTimeFile = jarLoadTimeResource.getFile();
			if (!jarLoadTimeFile.exists()) {
				jarLoadTimeFile.createNewFile();
			}
			is = new FileInputStream(jarLoadTimeFile);
			properties.load(is);
			final Pattern p = Pattern.compile(appJarPattern);
			File[] files = jarFilePath.listFiles(new FilenameFilter() {

				public boolean accept(File dir, String name) {
					return p.matcher(name).matches();
				}
			});
			// 从jar中取出应用程序页面文件 (应用程序页面文件必须存放在jar的WebRoot目录中)
			for (File file : files) {
				if (properties.containsKey(file.getName())) {
					long time = Long.parseLong(properties.getProperty(file.getName()));
					if (time >= file.lastModified()) {
						continue;
					}
				}
				logger.info("This is decompressing \"" + file.getName() + "\".");
				// 删除页面资源文件
				JarFile jarFile = new JarFile(file);
				Enumeration<JarEntry> en = jarFile.entries();
				while (en.hasMoreElements()) {
					JarEntry je = en.nextElement();
					String name = je.getName();
					if (name.indexOf("WebRoot/") != -1) {
						String temp = name.replaceFirst("WebRoot/", "");
						if (temp.length() > 0 && temp.indexOf("WEB-INF") == -1) {
							File webResFile = new File(servletContext.getRealPath("/")
									+ temp.substring(0, temp.indexOf("/")));
							if (webResFile.exists()) {
								if (!FileUtil.deleteQuietly(webResFile)) {
									logger.error("Delete web resource file [" + webResFile.toURI() + "] fail!");
								}
							}
							break;
						}
					}
				}
				UnJar.unJarFile(file, servletContext.getRealPath("/"), "WebRoot");
				properties.setProperty(file.getName(), String.valueOf(file.lastModified()));

			}

		} catch (IOException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		// 保存jar加载时间
		OutputStream out = null;
		try {
			File jarLoadTimeFile = jarLoadTimeResource.getFile();
			out = new FileOutputStream(jarLoadTimeFile);
			properties.store(out, null);
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}

	}

	// /**
	// * <p>Discription:删除目录</p>
	// * Created on 2009-9-11
	// * @author: 宋成山 songchengshan@neusoft.com
	// * @update: [日期YYYY-MM-DD] [更改人姓名]
	// */
	// private boolean deleteFile(File file) {
	// if (file.exists()) {
	// if (file.isDirectory()) {
	// for (File tmp : file.listFiles()) {
	// if (tmp.isDirectory()) {
	// deleteFile(tmp);
	// } else {
	// if (!tmp.delete()) {
	// return false;
	// }
	// }
	// }
	// }
	// if (!file.delete()) {
	// return false;
	// }
	// }
	// return true;
	// }
}
