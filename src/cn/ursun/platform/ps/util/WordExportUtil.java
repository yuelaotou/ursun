package cn.ursun.platform.ps.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ursun.platform.ps.util.SimpleTemplate.PicField;

public class WordExportUtil {

	/**
	 * <p>生成word</p>
	 * @param properties 模板参数,键值对格式
	 * @param fileName  模板文件路径 (WEBROOT的相对路径)
	 * @param newFileName 生成的文件名
	 * @param dynamicInfo 动态列表参数：当生成多条数据时使用，list 中存放map,对应一行参数，图片数据使用PicField存放
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-9 下午02:22:42
	 */
	public static InputStream exportDocFile(Properties properties, String fileName, List<Properties> dynamicInfo)
			throws Exception {
		try {
			List list = new ArrayList();
			list.add(dynamicInfo);
			InputStream fis = new FileInputStream(fileName);
			SimpleTemplate st = new SimpleTemplate(fis, list, "UTF-8");
			fis.close();
			return new ByteArrayInputStream(st.translate(properties).getBytes("UTF-8"));
		} catch (FileNotFoundException e) {
			throw new Exception("�޷��无法找到文件：" + fileName, e);
		} catch (IOException e) {
			throw new Exception("�޷��无法找到文件：" + fileName, e);
		}
	}

	/**
	 * <p>生成word</p>
	 * @param properties 模板参数,键值对格式
	 * @param fileName  模板文件路径 (WEBROOT的相对路径)
	 * @param newFileName 生成的文件名
	 * @param dynamicInfo  <p>多个动态列表参数：当生成多个列表时使用，list 中存放列表参数，列表参数也存放在list中，对应一行参数，图片数据使用PicField存放；List<List<Map>></p>
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-9 下午02:22:42
	 */
	public static InputStream exportDocFileBatch(Properties properties, String fileName, List<List<Map>> dynamicInfo)
			throws Exception {
		try {
			InputStream fis = new FileInputStream(fileName);
			SimpleTemplate st = new SimpleTemplate(fis, dynamicInfo, "UTF-8");
			fis.close();
			return new ByteArrayInputStream(st.translate(properties).getBytes("UTF-8"));
		} catch (FileNotFoundException e) {
			throw new Exception("�޷��无法找到文件：" + fileName, e);
		} catch (IOException e) {
			throw new Exception("�޷��无法找到文件：" + fileName, e);
		}
	}

	/**
	 * <p>生成word</p>
	 * @param properties 模板参数,键值对格式
	 * @param fileName  模板文件路径 (WEBROOT的相对路径)
	 * @param newFileName 生成的文件名
	 * @param dynamicInfo  <p>多个动态列表参数：当生成多个列表时使用，list 中存放列表参数，列表参数也存放在list中，对应一行参数，图片数据使用PicField存放；List<List<Map>></p>
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-9 下午02:22:42
	 */
	public static void exportDocFileBatch(Properties properties, String fileName, String newFileName,
			List<List<Map>> dynamicInfo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer sb = new StringBuffer(50);
		sb.append("filename=");
		sb.append(newFileName);
		try {
			String filePath = request.getRealPath("/") + "/WEB-INF/conf/clf/template/" + fileName;
			InputStream fis = new FileInputStream(filePath);
			response.setContentType("application/msword; charset=UTF-8");
			response.setHeader("Content-Disposition", new String(sb.toString().getBytes("GBK"), "ISO8859-1"));
			SimpleTemplate st = new SimpleTemplate(fis, dynamicInfo, "UTF-8");
			response.getWriter().write(st.translate(properties));
			fis.close();
		} catch (FileNotFoundException e) {
			throw new Exception("�޷��无法找到文件：" + fileName, e);
		} catch (IOException e) {
			throw new Exception("�޷��无法找到文件：��ļ���" + fileName, e);
		}
	}

	/**
	 * <p>生成word</p>
	 * @param properties 模板参数,键值对格式
	 * @param fileName  模板文件路径 (WEBROOT的相对路径)
	 * @param newFileName 生成的文件名
	 * @param dynamicInfo 动态列表参数：当生成多条数据时使用，list 中存放map,对应一行参数，图片数据使用PicField存放
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-9 下午02:22:42
	 */
	public static void exportDocFile(Properties properties, String fileName, String newFileName, List<Map> dynamicInfo,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer sb = new StringBuffer(50);
		sb.append("filename=");
		sb.append(newFileName);
		List list = new ArrayList();
		list.add(dynamicInfo);
		try {
			String filePath = request.getRealPath("/") + "/WEB-INF/conf/clf/template/" + fileName;
			InputStream fis = new FileInputStream(filePath);
			response.setContentType("application/msword; charset=UTF-8");
			response.setHeader("Content-Disposition", new String(sb.toString().getBytes("GBK"), "ISO8859-1"));
			SimpleTemplate st = new SimpleTemplate(fis, list, "UTF-8");
			response.getWriter().write(st.translate(properties));
			fis.close();
		} catch (FileNotFoundException e) {
			throw new Exception("�޷��无法找到文件：" + fileName, e);
		} catch (IOException e) {
			throw new Exception("�޷��无法找到文件：��ļ���" + fileName, e);
		}
	}

	/**
	 * <p>生成word</p>
	 * @param properties 模板参数,键值对格式
	 * @param fileName 模板文件路径 (WEBROOT的相对路径)
	 * @param newFileName 生成的文件名
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-9 下午02:30:48
	 */
	public static void exportDocFile(Properties properties, String fileName, String newFileName,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		StringBuffer sb = new StringBuffer(50);
		sb.append("filename=");
		sb.append(newFileName);
		try {
			String filePath = request.getRealPath("/") + fileName;
			InputStream fis = new FileInputStream(filePath);
			response.setContentType("application/msword; charset=UTF-8");
			response.setHeader("Content-Disposition", new String(sb.toString().getBytes("GBK"), "ISO8859-1"));
			SimpleTemplate st = new SimpleTemplate(fis, "UTF-8");
			response.getWriter().write(st.translate(properties));
			fis.close();
		} catch (FileNotFoundException e) {
			throw new Exception("�޷��无法找到文件：" + fileName, e);
		} catch (IOException e) {
			throw new Exception("�޷��无法找到文件：��ļ���" + fileName, e);
		}
	}

	public static void main(String[] args) {
		Properties info = new Properties();
		// 模板参数,键值对格式
		info.setProperty("title", "电子行业区域发展分析报告");
		info.setProperty("node", "<wx:font wx:val=\"Times New Roman\"/>");
		//构建列表数据
		List record = new ArrayList();
		//第一条
		Map map = new HashMap();
		//插入图片
		map.put("pic_data", new PicField(new File("G:\\Picture\\4_154727_1.jpg")));
		map.put("pubdate_start", "2009-4-21");
		map.put("pubdate_end", "2009-4-21");
		map.put("pic_id", "wordml://0001");
		map.put("select_area", "日本 土耳其 美国 韩国");
		//第二条
		Map map1 = new HashMap();
		//插入图片
		map1.put("pic_data", new PicField(new File("G:\\Picture\\4_154727_1.jpg")));
		map1.put("pubdate_start", "2009-4-21");
		map1.put("pubdate_end", "2009-4-21");
		map1.put("pic_id", "wordml://0002");
		map1.put("select_area", "日本 土耳其 美国 韩国");
		//第三条
		Map map2 = new HashMap();
		//插入图片
		map2.put("pic_data", new PicField(new File("G:\\Picture\\4_154727_1.jpg")));
		map2.put("pubdate_start", "2009-4-21");
		map2.put("pubdate_end", "2009-4-21");
		map2.put("pic_id", "wordml://0003");
		map2.put("select_area", "日本 土耳其 美国 韩国");
		record.add(map);
		record.add(map1);
		record.add(map2);

		InputStream in = null;
		OutputStream out = null;
		try {
			in = WordExportUtil.exportDocFile(info, "D:\\temp\\template.xml", record);
			out = new FileOutputStream("D:\\temp\\test.doc");
			int length = 0;
			byte[] b = new byte[1024];
			while ((length = in.read(b, 0, b.length)) != -1) {
				out.write(b, 0, length);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
