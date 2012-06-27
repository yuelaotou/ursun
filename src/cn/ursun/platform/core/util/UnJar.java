package cn.ursun.platform.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * <p> Title: 解压jar包</p>
 * <p> Description: [描述]</p>
 * <p> Created on 2009-8-28</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author 宋成山 songchengshan@neusoft.com
 * @version 1.0
 */
public class UnJar {

	/**
	 * <p>Discription:解压jar包中的文件</p>
	 * Created on 2009-8-28
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static boolean unJarFile(File jarFileName, String outputDirectory, String unDirectory) throws Exception {
		JarInputStream in = null;
		boolean returnValue = true;
		try {
			in = new JarInputStream(new FileInputStream(jarFileName));
			JarEntry jen;
			while ((jen = in.getNextJarEntry()) != null) {
				if (jen.getName().startsWith(unDirectory )&& !jen.isDirectory()) {
					String fname = outputDirectory
							+ jen.getName().substring(jen.getName().indexOf(unDirectory) + unDirectory.length() + 1);
					File file = new File(fname);
					FileOutputStream out = null;
					try {
						if (!file.exists()) {
							if (!FileUtil.createNewFile(file, true)) {
								returnValue = false;
								throw new Exception("create file [" + file.toString() + "] fail !");
							}
						}
						out = new FileOutputStream(file);
						int b;
						while ((b = in.read()) != -1) {
							out.write(b);
						}
						out.close();
					} catch (FileNotFoundException e) {
						returnValue = false;
						throw e;
					} catch (IOException e) {
						returnValue = false;
						throw e;
					} finally {
						try {
							if (out != null) {
								out.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			}
		} catch (FileNotFoundException e) {
			returnValue = false;
			throw e;

		} catch (IOException e) {
			returnValue = false;
			throw e;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return returnValue;
	}

	public static void main(String[] args) {
		try {
			unJarFile(new File("d:\\example.jar"), "d:\\unjar", "example");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
