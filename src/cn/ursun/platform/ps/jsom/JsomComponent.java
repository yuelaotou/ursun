package cn.ursun.platform.ps.jsom;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.Resource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cn.ursun.platform.core.component.AbstractComponent;

/**
 * 
 * @author 宋成山 - songchengshan@neusoft.com
 */
public class JsomComponent extends AbstractComponent {

	private static final Log logger = LogFactory.getLog(JsomComponent.class);

	private Resource configResources = null;

	private Resource xsltResources = null;

	private Resource jsconfigResources = null;

	public void destory() {

	}

	public void execute() {
		try {
			Document doc = null;
			InputStream inps = null;
			SAXReader saxReader = new SAXReader("org.apache.xerces.parsers.SAXParser", false);
			logger.info("Js initializing ...");
			try {
				inps = configResources.getInputStream();
				doc = saxReader.read(inps);
				List beanslist = (List) doc.selectNodes("/root/beans");
				Iterator it = beanslist.iterator();
				while (it.hasNext()) {// 遍历每个OM,一个OM对应一个页面
					Node beans = (Node) it.next();
					String omid = beans.selectSingleNode("./@omid").getText();// 获取OM的ID
					List beanlist = (List) beans.selectNodes("./bean");
					Iterator it1 = beanlist.iterator();
					while (it1.hasNext()) {// 遍历每个bean
						StringBuffer result = new StringBuffer();// 存放JS依赖关系
						Element node = (Element) it1.next();
						result.append("'").append(node.selectSingleNode("./@id").getText()).append("',");
						getBeanDependOn(node, result, omid);// 取JS依赖关系
						node.addElement("depends-on").addEntity("",
								"[" + result.toString().substring(0, result.toString().length() - 1) + "]");
					}
					xmlToJs("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + beans.asXML(), "JsConfig_" + omid + ".js");// 生成OM对应的JS,文件名规则JsConfig_+omid
				}
			} catch (FileNotFoundException e) {
				logger.error(e);
				return;
			} catch (DocumentException e) {
				logger.error(e);
				return;
			} finally {
				try {
					if (inps != null) {
						inps.close();
					}
				} catch (IOException e) {
					logger.error(e);
				}
			}
			logger.info("Js initializing ... success");

		} catch (IOException e) {
			logger.error("Js initializing ... fail", e);
		} catch (SAXException e) {
			logger.error("Js initializing ... fail", e);
		}
	}

	/**
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() {
	}

	/**
	 * 存放JS依赖关系
	 * 
	 * @param bean
	 * @param result
	 * @param omid
	 */
	private void getBeanDependOn(Node bean, StringBuffer result, String omid) {
		List list = (List) bean.selectNodes("./import/@bean");// 取所有引用的BEAN
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Node node = (Node) it.next();
			String nodeText;
			Node n_omid = node.selectSingleNode("../@omid");
			if (n_omid != null && StringUtils.isNotEmpty(n_omid.getText())) {// 如果引用BEAN为其它页面的BEAN,不再进行递归查询
				nodeText = "'" + node.getText() + ";" + n_omid.getText() + "'";
				if (result.toString().indexOf(nodeText) == -1) {// 如果引用的BEAN之前没有遍历过，则添加到result
					result.append(nodeText).append(",");
				}
				continue;
			} else {
				nodeText = "'" + node.getText() + "'";//
				if (result.toString().indexOf(nodeText) != -1) {// 如果引用的BEAN之前遍历过，在中已存在，则不再递归遍历，继续遍历下一个引用的BEAN
					continue;
				}
				result.append(nodeText).append(",");
			}

			Node n = bean.selectSingleNode("/root/beans[@omid='" + omid + "']/bean[@id='" + node.getText() + "']");// 递归遍历BEAN
			if (n == null) {// 引用的BEAN未定义
				logger.error("Error creating bean with name '" + node.getText()
						+ "' defined in ServletContext resource [/WEB-INF/conf/jsconfig.xml]:\n" + bean.asXML());
			} else {
				getBeanDependOn(n, result, omid);
			}
		}
	}

	/**
	 * 根据XSLT解释XML生成JS
	 * 
	 * @param xml
	 * @param outFile
	 */
	private void xmlToJs(String xml, String filename) {
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(jsconfigResources.createRelative(filename).getFile());
			InputSource is = new InputSource(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			org.w3c.dom.Document doc1 = (org.w3c.dom.Document) getDocumentBuilder().parse(is);
			Source source = new DOMSource(doc1);
			// 读取 xslt 文件并生成转换器
			StreamSource xsltSource = new StreamSource(xsltResources.getInputStream());
			TransformerFactory tf = TransformerFactory.newInstance();
			Templates transformation = tf.newTemplates(xsltSource);
			Transformer transformer = transformation.newTransformer();
			// 转化 xml 并写入输出流
			StreamResult result = new StreamResult(output);
			transformer.transform(source, result);
			output.flush();

		} catch (FileNotFoundException e1) {
			logger.error(e1);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (SAXException e1) {
			logger.error(e1);
		} catch (IOException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private DocumentBuilder getDocumentBuilder() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		DocumentBuilder db = factory.newDocumentBuilder();
		return db;
	}

	public Resource getConfigResources() {
		return configResources;
	}

	public void setConfigResources(Resource configResources) {
		this.configResources = configResources;
	}

	public Resource getJsconfigResources() {
		return jsconfigResources;
	}

	public void setJsconfigResources(Resource jsconfigResources) {
		this.jsconfigResources = jsconfigResources;
	}

	public Resource getXsltResources() {
		return xsltResources;
	}

	public void setXsltResources(Resource xsltResources) {
		this.xsltResources = xsltResources;
	}

	public static void main(String[] args) {
		JsomComponent js = new JsomComponent();
		js.execute();
	}

}
