package cn.ursun.platform.ps.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.Validate;

import sun.misc.BASE64Encoder;

public class SimpleTemplate {

	public static final String DYNAMIC_CONTENT_START = "<w:dynamic>";

	public static final String DYNAMIC_CONTENT_END = "</w:dynamic>";

	/** ���� */
	private List<Node> Nodes = null;

	/** ���� */
	public static final int PartType_String = 0;

	/** ���� */
	public static final int PartType_User = 1;

	/** ���� */
	public static final int PartType_System = 2;

	/** ���� */
	public static final int PartType_Field = 3;

	/** ���� */
	public static final int PartType_Entity = 4;

	public SimpleTemplate(InputStream fis, String DeCode) {
		String template = load(fis, DeCode);
		Nodes = phase(template);
	}

	public SimpleTemplate(InputStream fis, List<List<Map>> dynamicInfo, String DeCode) {
		String template = load(fis, DeCode);
		Nodes = batchPhase(template, dynamicInfo);
	}

	/**
	 * <p>装载模板</p>
	 * @param in
	 * @param DeCode
	 * @return
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-8 下午11:26:25
	 */
	private String load(InputStream in, String DeCode) {

		StringBuffer Template = new StringBuffer();
		if (DeCode == null || DeCode.equals("")) {
			DeCode = "GBK";
		}
		Charset charset = Charset.forName(DeCode);
		InputStreamReader inr = new InputStreamReader(in, charset);

		char buf[] = new char[1024];
		int len = 0;
		try {
			while ((len = inr.read(buf)) > 0) {
				Template.append(buf, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Template.toString();
	}

	private List batchPhase(String Template, List<List<Map>> dynamicInfo) {

		int tagstart = 0;
		int tagend = 0;
		tagstart = Template.indexOf(DYNAMIC_CONTENT_START);
		if (tagstart == -1) {
			return phase(Template);
		}
		StringBuffer copiedTemplate = new StringBuffer();
		String dynamicTemplate = null;
		String dynamicPartContent = null;
		List<Node> dynamicPartNode = null;
		int dynamicTemplateCount = 0;
		while (tagstart >= 0) {
			if (tagstart > tagend) {
				copiedTemplate.append(Template.substring(tagend, tagstart));
			}
			tagend = Template.indexOf(DYNAMIC_CONTENT_END, tagstart);
			if (tagend > 0) {
				dynamicTemplate = Template.substring(tagstart + DYNAMIC_CONTENT_START.length(), tagend);
				dynamicPartNode = phase(dynamicTemplate);
				if (dynamicInfo.size() > dynamicTemplateCount) {
					for (Map m : dynamicInfo.get(dynamicTemplateCount)) {
						dynamicPartContent = translate(dynamicPartNode, m);
						copiedTemplate.append(dynamicPartContent);
					}
				}
				tagend += DYNAMIC_CONTENT_END.length();
				tagstart = Template.indexOf(DYNAMIC_CONTENT_START, tagend);
				dynamicTemplateCount++;
			} else {
				tagend = tagstart;
			}
		}
		if (Template.length() > tagend) {
			copiedTemplate.append(Template.substring(tagend));
		}

		return phase(copiedTemplate.toString());
	}

	private List phase(String Template) {
		ArrayList nodes = new ArrayList();
		int tagstart = 0, tagend = 0;
		String Content = "";
		int Type = 0;
		tagstart = Template.indexOf("${");
		while (tagstart >= 0) {
			if (tagstart > tagend) {
				Content = Template.substring(tagend, tagstart);
				nodes.add(new Node(Content, SimpleTemplate.PartType_String));
			}
			tagend = Template.indexOf('}', tagstart);
			if (tagend > 0) {
				Content = Template.substring(tagstart + 2, tagend).trim();
				Type = SimpleTemplate.PartType_Field;
				if (Content.length() > 4 && Content.substring(0, 4).equalsIgnoreCase("SYS.")) {
					Type = SimpleTemplate.PartType_System;
					Content = Content.substring(4);
				} else if (Content.length() > 5 && Content.substring(0, 5).equalsIgnoreCase("USER.")) {
					Type = SimpleTemplate.PartType_User;
					Content = Content.substring(5);
				} else if (Content.length() > 7 && Content.substring(0, 7).equalsIgnoreCase("ENTITY.")) {
					Type = SimpleTemplate.PartType_Entity;
					Content = Content.substring(7);
				}
				nodes.add(new Node(Content, Type));
				tagend++;
				tagstart = Template.indexOf("${", tagend);
			} else {
				tagend = tagstart;
			}
		}
		if (Template.length() > tagend) {
			Content = Template.substring(tagend);
			nodes.add(new Node(Content, SimpleTemplate.PartType_String));
		}
		return nodes;
	}

	/**
	 * <p>生成word</p>
	 * @param nodes
	 * @param Info
	 * @return
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-8 下午11:32:50
	 */
	public String translate(Properties Info) {
		return translate(this.Nodes, Info);
	}

	/**
	 * <p>生成word</p>
	 * @param nodes
	 * @param Info
	 * @return
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-8 下午11:32:50
	 */
	private String translate(List<Node> nodes, Map Info) {
		StringBuffer sb = new StringBuffer(4096);
		for (Node node : nodes) {
			switch (node.Type) {
				case SimpleTemplate.PartType_String:
					sb.append(node.Content);
					break;
				case SimpleTemplate.PartType_Field:
					// todo add HTML Convert
					Validate.notNull(Info.get(node.Content), node.Content + " not exists !");
					sb.append(convertXMLEntity(Info.get(node.Content).toString()));
					break;
				case SimpleTemplate.PartType_Entity:
					// todo add HTML Convert
					Validate.notNull(Info.get(node.Content), node.Content + " not exists !");
					sb.append(Info.get(node.Content).toString());
					break;
				case SimpleTemplate.PartType_System:
					break;
				case SimpleTemplate.PartType_User:
					break;
			}
		}
		return sb.toString();
	}

	private class Node {

		String Content = "";

		int Type = SimpleTemplate.PartType_String;

		/**
		 * 
		 * <p>
		 * Discription:�������
		 * </p>
		 * 
		 * @coustructor ����.
		 */
		public Node(String Content, int Type) {
			this.Content = Content;
			this.Type = Type;
		}
	}

	static public class PicField {

		InputStream stream = null;

		File file = null;

		byte[] data = null;

		public PicField(File file) {
			this.file = file;
		}

		public PicField(InputStream in) {
			this.stream = in;
		}

		public PicField(byte[] data) {
			this.data = data;
		}

		public String encode() {
			StringBuffer picData = new StringBuffer();
			BASE64Encoder b = new BASE64Encoder();
			if (data != null) {
				picData.append(b.encodeBuffer(data));
			} else {
				try {
					if (file != null) {
						stream = new FileInputStream(file);
					}
					byte[] buffer = new byte[1024];
					int length = 0;
					while ((length = stream.read(buffer, 0, buffer.length)) != -1) {
						byte[] temp = new byte[length];
						System.arraycopy(buffer, 0, temp, 0, length);
						picData.append(b.encodeBuffer(temp));
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return picData.toString();
		}

		public String toString() {
			return encode();
		}

	}

	/**
	 * ת��XML�е�ʵ��
	 * 
	 * @param srcStr
	 * @param destSrc
	 * @return
	 */
	public static String convertXMLEntity(String srcStr) {

		srcStr = srcStr != null ? srcStr.toString() : "";
		return srcStr.replaceAll("&", "&amp;").replaceAll("'", "&apos;").replaceAll("\"", "&quot;").replaceAll("<",
				"&lt;").replaceAll(">", "&gt;");
	}
}