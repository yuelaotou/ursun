/**
 * 文件名：ColumnBS.java
 *
 * 创建人：【周洋】 - 【zhouyang@neusoft.com】
 *
 * 创建时间：Dec 15, 2009 8:38:01 PM
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
 * <p>【二维柱状图实现类】</p>
 * @author 【周洋】 - 【zhouyang@neusoft.com】
 * @version 1.0
 */
public class Column extends Chart {
	public String getDataConversion()throws BizException{

		Document doc =  DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("chart");
		doc.setRootElement(root);
		//设置图表的表头，X，Y轴标题
		root.addAttribute("caption",chartTitle);
		root.addAttribute("xAxisName",chartXtitle);
		root.addAttribute("yAxisName",chartYtitle);
		//设置属性
		root.addAttribute("showValues", "0");
		root.addAttribute("decimals", "2");
		root.addAttribute("unescapeLinks", "0");
		
		//设置报表数字标签前缀
		root.addAttribute("numberPrefix", numberPrefix);
		root.addAttribute("numberSuffix", numberSuffix);

		//导出图片设置
		root.addAttribute("imageSave", "1");
		if(this.isYAxisToInteger)
			root.addAttribute("yAxisMaxValue", Chart.doubleToStr(get2DMaxValue(chart2DData), 0));
		root.addAttribute("exportFileName", "myChart");
		root.addAttribute("exportAction", "download");
		root.addAttribute("exportAtClient", "0");
		root.addAttribute("exportShowMenuItem", "0");
		root.addAttribute("exportHandler", exportHandler);
		root.addAttribute("exportDialogColor", "e1f5ff");
		root.addAttribute("exportDialogBorderColor", "0372ab");
		root.addAttribute("exportDialogFontColor", "0372ab");
		root.addAttribute("exportDialogPBColor", "0372ab");
		//设置其他属性
		root.addAttribute("smartLineColor", "FFFFFF");
		root.addAttribute("formatNumberScale", "0");
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
			}		}
		Element styles = DocumentHelper.createElement("styles");
		root.add(styles);
		Element definition = DocumentHelper.createElement("definition");
		styles.add(definition);
		Element style_1 = definition.addElement("style");
		style_1.addAttribute("name", "captionFont");
		style_1.addAttribute("type", "font");
		style_1.addAttribute("font", "Tahoma");
		style_1.addAttribute("size", "20");
		Element style_2 = definition.addElement("style");
		style_2.addAttribute("name", "SubCaptionFont");
		style_2.addAttribute("type", "font");
		style_2.addAttribute("font", "Tahoma");
		style_2.addAttribute("size", "12");
		Element style_3 = definition.addElement("style");
		style_3.addAttribute("name", "AxisTitlesFont");
		style_3.addAttribute("type", "font");
		style_3.addAttribute("font", "Tahoma");
		style_3.addAttribute("size", "12");
		Element style_4 = definition.addElement("style");
		style_4.addAttribute("name", "LegendFont");
		style_4.addAttribute("type", "font");
		style_4.addAttribute("font", "Tahoma");
		style_4.addAttribute("size", "12");
		Element style_5 = definition.addElement("style");
		style_5.addAttribute("name", "ToolTipFont");
		style_5.addAttribute("type", "font");
		style_5.addAttribute("font", "Tahoma");
		style_5.addAttribute("size", "12");
		Element style_6 = definition.addElement("style");
		style_6.addAttribute("name", "LabelsFont");
		style_6.addAttribute("type", "font");
		style_6.addAttribute("font", "Tahoma");
		style_6.addAttribute("size", "11");
		
		Element application = DocumentHelper.createElement("application");
		styles.add(application);
		Element apply_1 = application.addElement("apply");
		apply_1.addAttribute("toObject", "caption");
		apply_1.addAttribute("styles", "captionfont");
		Element apply_2 = application.addElement("apply");
		apply_2.addAttribute("toObject", "SubCaption");
		apply_2.addAttribute("styles", "SubCaptionFont");
		Element apply_3 = application.addElement("apply");
		apply_3.addAttribute("toObject", "XAxisName");
		apply_3.addAttribute("styles", "AxisTitlesFont");
		Element apply_4 = application.addElement("apply");
		apply_4.addAttribute("toObject", "YAxisName");
		apply_4.addAttribute("styles", "AxisTitlesFont");
		Element apply_5 = application.addElement("apply");
		apply_5.addAttribute("toObject", "Legend");
		apply_5.addAttribute("styles", "LegendFont");
		Element apply_6 = application.addElement("apply");
		apply_6.addAttribute("toObject", "ToolTip");
		apply_6.addAttribute("styles", "LegendFont");
		Element apply_7 = application.addElement("apply");
		apply_7.addAttribute("toObject", "DataLabels");
		apply_7.addAttribute("styles", "LabelsFont");
		
		return JavaScriptUtils.javaScriptEscape(doc.asXML());
	}
}
