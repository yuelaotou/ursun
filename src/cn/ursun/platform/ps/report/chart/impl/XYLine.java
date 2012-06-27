/**
 * 文件名：XYLine.java
 *
 * 创建人：【周洋】 - 【zhouyang@neusoft.com】
 *
 * 创建时间：Mar 12, 2010 9:11:45 AM
 *
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.ps.report.chart.impl;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.util.JavaScriptUtils;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.report.chart.Chart;
import cn.ursun.platform.ps.report.element.Element3D;

/**
 * <p>螺旋图转换类</p>
 * @author 【周洋】 - 【zhouyang@neusoft.com】
 * @version 1.0
 */
public class XYLine extends Chart {

	/**
	 * <p>螺旋图转换类</p>
	 * @return
	 * @throws BizException
	 * @author: 【周洋】 - 【zhouyang@neusoft.com】
	 * @data: Create on Mar 12, 2010 9:11:45 AM
	 */
	@Override
	public String getDataConversion() throws BizException {
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
		String xValue,yValue;
		double zValue;
		if (chart3DData == null)
	    {
	        throw new BizException("");
	    }
		List<String> valueList = new ArrayList<String>();
		int i = 0;
		Element categories = DocumentHelper.createElement("categories");
		root.add(categories);
		double maxXValue = get3DMaxXValue(chart3DData);
		for(int temp = 0;temp<5;temp++){
			Element category=categories.addElement("category");
			category.addAttribute("label", Chart.doubleToStr((maxXValue/5)*(i+1),1));
			category.addAttribute("x", Chart.doubleToStr((maxXValue/5)*(i+1),1));
		}
		Element dataset = DocumentHelper.createElement("dataset");
        dataset.addAttribute("showValues", "0");
        dataset.addAttribute("color", "1D8BD1");
        for(Element3D value : chart3DData){
        	Element set=dataset.addElement("set");
        	set.addAttribute("x", value.getXValue());
        	set.addAttribute("y", value.getYValue());
        	set.addAttribute("z", Chart.doubleToStr(value.getZValue(),1));
        	set.addAttribute("color", "1F0DC8");
        }
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
		
		return JavaScriptUtils.javaScriptEscape(doc.asXML());
		
	}
	
    /**
     * <p>获取到当前报表的最大X轴标签的值</p>
     * @author: 【周洋】 - 【zhouyang@neusoft.com】
     * @data: Create on Mar 1, 2010 2:19:49 PM
     */
    private static double get3DMaxXValue(List<Element3D> chartData){
    	double maxValue = 0;
    	for(int i = 0;i<chartData.size();i++){
    		if(maxValue<Double.parseDouble(chartData.get(i).getXValue())){
    			maxValue = Double.parseDouble(chartData.get(i).getXValue());
    		}
    	}
    	if((Math.ceil(maxValue))%5==0){
    		maxValue = Math.ceil(maxValue);
    	}else{
    		maxValue = (Math.ceil(Math.ceil(maxValue)%5))*5;
    	}
    	return maxValue;
    }
    
    /**
     * <p>获取到当前报表的最大Y轴标签的值</p>
     * @author: 【周洋】 - 【zhouyang@neusoft.com】
     * @data: Create on Mar 1, 2010 2:19:49 PM
     */
    private static double get3DMaxYValue(List<Element3D> chartData){
    	double maxValue = 0;
    	for(int i = 0;i<chartData.size();i++){
    		if(maxValue<Double.parseDouble(chartData.get(i).getYValue())){
    			maxValue = Double.parseDouble(chartData.get(i).getYValue());
    		}
    	}
    	
    	//long temp = 0;
    	if((Math.ceil(maxValue))%5==0){
    		maxValue = Math.ceil(maxValue);
    	}else{
    		maxValue = (Math.ceil(Math.ceil(maxValue)%5))*5;
    	}
    	return maxValue;
    }

}
