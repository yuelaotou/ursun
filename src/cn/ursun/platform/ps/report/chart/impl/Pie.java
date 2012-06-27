/**
 * 文件名：PieBS.java
 *
 * 创建人：【周洋】 - 【zhouyang@neusoft.com】
 *
 * 创建时间：Dec 15, 2009 9:08:12 PM
 *
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.ps.report.chart.impl;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.util.JavaScriptUtils;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.report.chart.Chart;
import cn.ursun.platform.ps.report.element.Element2D;

/**
 * <p>【饼图数据转换类】</p>
 * @author 【周洋】 - 【zhouyang@neusoft.com】
 * @version 1.0
 */
public class Pie extends Chart {
	public String getDataConversion()throws BizException{
		Document doc =  DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("chart");
		doc.setRootElement(root);
		//设置图表的表头，X，Y轴标题
		root.addAttribute("caption",chartTitle);
		//设置属性
		//设置属性
		root.addAttribute("baseFontColor", "000000");
		root.addAttribute("bgColor", "FFFFFF, 666666");
		root.addAttribute("pieYScale", "30");
		root.addAttribute("pieSliceDepth", "10");
		root.addAttribute("unescapeLinks", "0");
		//导出图片设置
		root.addAttribute("imageSave", "1");
		root.addAttribute("exportFileName", "myChart");
		root.addAttribute("exportAction", "download");
		root.addAttribute("exportAtClient", "0");
		root.addAttribute("exportShowMenuItem", "0");
		root.addAttribute("exportHandler",exportHandler);
		root.addAttribute("exportDialogColor", "e1f5ff");
		root.addAttribute("exportDialogBorderColor", "0372ab");
		root.addAttribute("exportDialogFontColor", "0372ab");
		root.addAttribute("exportDialogPBColor", "0372ab");
		//设置其他属性
		root.addAttribute("smartLineColor", "FFFFFF");
		root.addAttribute("alternateHGridAlpha", "5");
		
		String xValue;
		double yValue;
		for (Element2D element : chart2DData){
			xValue = element.getXValue();
			yValue = element.getYValue();
			Element set=root.addElement("set");
			set.addAttribute("label", xValue);
			set.addAttribute("value", Chart.doubleToStr(yValue,1));
			try {
				if(clickRegional != null && !clickRegional.equals("")){
					set.addAttribute("link","JavaScript:"+clickRegional+"('"+xValue+"','"+yValue+"')");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Element styles = DocumentHelper.createElement("styles");
		//trendlines.setParent(root);
		root.add(styles);
		Element definition = DocumentHelper.createElement("definition");
		styles.add(definition);
		Element style_1 = definition.addElement("style");
		style_1.addAttribute("name", "CaptionFont");
		style_1.addAttribute("type", "font");
		style_1.addAttribute("font", "Tahoma");
		style_1.addAttribute("size", "20");
		Element style_2 = definition.addElement("style");
		style_2.addAttribute("name", "LabelFont");
		style_2.addAttribute("type", "font");
		style_2.addAttribute("font", "Tahoma");
		style_2.addAttribute("size", "12");
		Element style_3 = definition.addElement("style");
		style_3.addAttribute("name", "ToolTIpFont");
		style_3.addAttribute("type", "font");
		style_3.addAttribute("font", "Tahoma");
		style_3.addAttribute("size", "12");
		Element application = DocumentHelper.createElement("application");
		styles.add(application);
		Element apply_1 = application.addElement("apply");
		apply_1.addAttribute("toObject", "CAPTION");
		apply_1.addAttribute("styles", "CaptionFont");
		Element apply_2 = application.addElement("apply");
		apply_2.addAttribute("toObject", "DATALABELS");
		apply_2.addAttribute("styles", "LabelFont");
		Element apply_3 = application.addElement("apply");
		apply_3.addAttribute("toObject", "TOOLTIP");
		apply_3.addAttribute("styles", "ToolTIpFont");
		
		return JavaScriptUtils.javaScriptEscape(doc.asXML());
	}
}
