/**
 * 文件名：XmlUtils.java
 *
 * 创建人：李志伟 - li.zhw@neusoft.com
 *
 * 创建时间：Jan 7, 2010 9:27:24 AM
 *
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.console.app.console.user.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * <p>xml转换工具类</p>
 * @author 李志伟 - li.zhw@neusoft.com
 * @version 1.0
 */
public class XmlUtils {
	
	/**
	 * xml根节点名称
	 */
	private static final String XMLROOT="columns"; 
		
	/**
	 * <p>map转换为xml格式字符串</p>
	 * @param map
	 * @return
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 7, 2010 9:29:10 AM
	 */
	public static String parseMapToXml(Map<String,String[]> map){
		Document doc=DocumentHelper.createDocument();
		doc.clearContent();
		Element element=doc.addElement(XMLROOT);
		for(String key:map.keySet()){
			element.addElement(key).addText(ArrayToString(map.get(key)));
		}
		return doc.asXML();
	}
	
	/**
	 * <p>xml格式字符串保存为map</p>
	 * @param xml
	 * @return
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 7, 2010 9:30:31 AM
	 */
	public static Map<String,String[]> parseXmlToMap(String xml){
		Map<String,String[]> map=new HashMap<String,String[]>();
		Document doc=null;
		if(StringUtils.isEmpty(xml)){
			return null;
		}
		try{
			doc=DocumentHelper.parseText(xml);
		}catch(DocumentException e){
			e.printStackTrace();
		}
		Element root=doc.getRootElement();
		for(Object object:root.elements()){
			Element element=(Element)object;
			map.put(element.getName(), element.getText().split(","));
		}
		return map;
	}
	/**
	 * <p>字符串数组转换为字符串,用逗号分割</p>
	 * @param array
	 * @return
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 8, 2010 4:50:08 PM
	 */
	public static String ArrayToString(String[] array){
		StringBuffer buffer=new StringBuffer();
		for(String str:array){
			buffer.append(str).append(",");
		}
		String result=buffer.toString();
		return result.length()==0?"":result.substring(0, result.length()-1);
	}
	
	public static void main(String[] args){
		Map<String,String[]> map=new HashMap<String,String[]>();
		map.put("extend1", new String[]{"11"});
		map.put("extend2", new String[]{"22"});
		map.put("extend3", new String[]{"33"});
		map.put("extend4", new String[]{"44"});
		map.put("extend5", new String[]{"55","66","77"});
		System.out.println(parseMapToXml(map));
		Map<String,String[]> temp=parseXmlToMap(parseMapToXml(map));
		for(String key:temp.keySet()){
			System.out.println(Arrays.deepToString(temp.get(key)));
		}
	}
}
