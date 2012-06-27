package cn.ursun.platform.core.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * <p>
 * Title: 在classpath中文件搜索工具
 * </p>
 * <p>
 * Description: [描述]
 * </p>
 * <p>
 * Created on 2009-8-28
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: 东软软件股份有限公司
 * </p>
 * 
 * @author 宋成山 songchengshan@neusoft.com
 * @version 1.0
 */
public class ClassPathScanner {

	private static final String PROTOCOL_FILE = "file";

	/** URL protocol for an entry from a jar file: "jar" */
	private static final String PROTOCOL_JAR = "jar";

	/** URL protocol for an entry from a zip file: "zip" */
	private static final String PROTOCOL_ZIP = "zip";

	/** URL protocol for an entry from a WebSphere jar file: "wsjar" */
	private static final String PROTOCOL_WSJAR = "wsjar";

	private static final String PREFIX_FILE = "file:";

	private static final String JAR_URL_SEPERATOR = "!/";

	private static final String CLASS_FILE = ".class";

	public ClassPathScanner() {
	}

	/**
	 * <p>
	 * Discription:在classpath中搜索class
	 * </p>
	 * Created on 2009-8-28
	 * 
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<Class<?>> scanClass(String packageName, ClassFilter filter) {
		List<Class<?>> list = new ArrayList<Class<?>>();
		Enumeration<URL> en = null;
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (cl == null) {
			cl = getClass().getClassLoader();
		}
		try {
			en = cl.getResources(dotToPath(packageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (en.hasMoreElements()) {
			URL url = en.nextElement();
			if (PROTOCOL_FILE.equals(url.getProtocol())) {
				File root = new File(url.getFile());
				findInDirectory(list, root, root, packageName, filter);
			} else if (PROTOCOL_JAR.equals(url.getProtocol())
					|| PROTOCOL_ZIP.equals(url.getProtocol())
					|| PROTOCOL_WSJAR.equals(url.getProtocol())) {
				findInJar(list, getJarFile(url), packageName, filter);
			}
		}
		return list;
	}

	/**
	 * <p>
	 * Discription:在classpath中搜索资源文件
	 * </p>
	 * Created on 2009-8-28
	 * 
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<Resource> scanFile(String packageName, FileFilter filter) {
		List<Resource> list = new ArrayList<Resource>();
		Enumeration<URL> en = null;
		try {
			en = getClass().getClassLoader().getResources(
					dotToPath(packageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (en.hasMoreElements()) {
			URL url = en.nextElement();
			if (PROTOCOL_FILE.equals(url.getProtocol())) {
				File root = new File(url.getFile());
				findFileInDirectory(list, root, root, packageName, filter);
			} else if (PROTOCOL_JAR.equals(url.getProtocol())
					|| PROTOCOL_ZIP.equals(url.getProtocol())
					|| PROTOCOL_WSJAR.equals(url.getProtocol())) {
				findFileInJar(list, getJarFile(url), packageName, filter);
			}
		}
		return list;
	}

	private File getJarFile(URL url) {
		String file = url.getFile();
		if (file.startsWith(PREFIX_FILE))
			file = file.substring(PREFIX_FILE.length());
		int end = file.indexOf(JAR_URL_SEPERATOR);
		if (end != (-1))
			file = file.substring(0, end);
		return new File(file);
	}

	private void findInJar(List<Class<?>> results, File file,
			String packageName, ClassFilter filter) {
		JarFile jarFile = null;
		String packagePath = dotToPath(packageName) + "/";
		try {
			jarFile = new JarFile(file);
			Enumeration<JarEntry> en = jarFile.entries();
			while (en.hasMoreElements()) {
				JarEntry je = en.nextElement();
				String name = je.getName();
				if (name.startsWith(packagePath) && name.endsWith(CLASS_FILE)) {
					String className = name.substring(0, name.length()
							- CLASS_FILE.length());
					add(results, pathToDot(className), filter);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (jarFile != null) {
				try {
					jarFile.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private void findInDirectory(List<Class<?>> results, File rootDir,
			File dir, String packageName, ClassFilter filter) {
		File[] files = dir.listFiles();
		String rootPath = rootDir.getPath();
		for (File file : files) {
			if (file.isFile()) {
				String classFileName = file.getPath();
				if (classFileName.endsWith(CLASS_FILE)) {
					String className = classFileName.substring(rootPath
							.length()
							- packageName.length(), classFileName.length()
							- CLASS_FILE.length());
					add(results, pathToDot(className), filter);
				}
			} else if (file.isDirectory()) {
				findInDirectory(results, rootDir, file, packageName, filter);
			}
		}
	}

	private void findFileInDirectory(List<Resource> results, File rootDir,
			File dir, String packageName, FileFilter filter) {
		File[] files = dir.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					if (filter == null || filter.accept(file.getName()))
						results.add(new FileSystemResource(file));
				} else if (file.isDirectory()) {
					findFileInDirectory(results, rootDir, file, packageName,
							filter);
				}
			}
		}
	}

	private void findFileInJar(List<Resource> list, File file,
			String packageName, FileFilter filter) {
		JarFile jarFile = null;
		String packagePath = dotToPath(packageName) + "/";
		try {
			jarFile = new JarFile(file);
			Enumeration<JarEntry> en = jarFile.entries();
			while (en.hasMoreElements()) {
				JarEntry je = en.nextElement();
				String name = je.getName();
				if (name.startsWith(packagePath) && !je.isDirectory()) {
					if (filter == null
							|| filter.accept(name.substring(name
									.lastIndexOf("/") + 1))) {
						list.add(new ClassPathResource(je.getName()));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (jarFile != null) {
				try {
					jarFile.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private void add(List<Class<?>> results, String className,
			ClassFilter filter) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			return;
		}
		if (filter == null || filter.accept(clazz))
			results.add(clazz);
	}

	private String dotToPath(String s) {
		return s.replace('\\', '/');
	}

	private String pathToDot(String s) {
		return s.replace('/', '.').replace('\\', '.');
	}

	public static void main(String[] args) {
		List<Class<?>> list = new ClassPathScanner().scanClass(
				"org.springframework.core.io", new ClassFilter() {

					public boolean accept(Class<?> clazz) {
						return clazz.isInterface();
					}

				});
		for (Class<?> clazz : list) {
			System.out.println(clazz);
		}
	}

}
