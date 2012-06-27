/**
 * 文件名：MSCombiBS.java
 *
 * 创建人：【周洋】 - 【zhouyang@neusoft.com】
 *
 * 创建时间：Dec 15, 2009 9:00:39 PM
 *
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.ps.report.chart.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.util.JavaScriptUtils;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.report.chart.Chart;
import cn.ursun.platform.ps.report.element.Element3D;

/**
 * <p>【三维柱状图实现类】</p>
 * @author 【周洋】 - 【zhouyang@neusoft.com】
 * @version 1.0
 */
public class MSCombi extends Chart {
	@SuppressWarnings("unchecked")
	public String getDataConversion()throws BizException{
		Document doc =  DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("chart");
		doc.setRootElement(root);
		//设置图表的表头，X，Y轴标题
		root.addAttribute("caption",chartTitle);
		root.addAttribute("xAxisName",chartXtitle);
		root.addAttribute("yAxisName",chartYtitle);
		//设置报表数字标签前缀
		root.addAttribute("numberPrefix", numberPrefix);
		root.addAttribute("numberSuffix", numberSuffix);

		//设置属性
		if(this.isYAxisToInteger)
			root.addAttribute("yAxisMaxValue", Chart.doubleToStr(get3DMaxValue(chart3DData), 0));
		root.addAttribute("showValues", "0");
		root.addAttribute("decimals", "2");
		root.addAttribute("unescapeLinks", "0");
		//导出图片设置
		root.addAttribute("imageSave", "1");
		root.addAttribute("exportFileName", "myChart");
		root.addAttribute("exportAction", "download");
		root.addAttribute("exportAtClient", "0");
		root.addAttribute("exportShowMenuItem", "0");
		root.addAttribute("exportHandler",  exportHandler);
		root.addAttribute("exportDialogColor", "e1f5ff");
		root.addAttribute("exportDialogBorderColor", "0372ab");
		root.addAttribute("exportDialogFontColor", "0372ab");
		root.addAttribute("exportDialogPBColor", "0372ab");
		//设置其他属性
		root.addAttribute("smartLineColor", "FFFFFF");
		root.addAttribute("formatNumberScale", "0");
		root.addAttribute("palette", "2");
		root.addAttribute("shownames", "1");
		root.addAttribute("showvalues", "0");
		root.addAttribute("clustered", "0");
		root.addAttribute("exeTime", "1.5");
		root.addAttribute("showPlotBorder", "0");
		root.addAttribute("divLineEffect", "emboss");
		root.addAttribute("zGapPlot", "30");
		root.addAttribute("zDepth", "90");
		String xValue,yValue;
		double zValue;
		if (chart3DData == null)
	    {
	        throw new BizException("");
	    }
		List<String> xValueList = new ArrayList<String>();
		int i = 0;
		Element categories = DocumentHelper.createElement("categories");
		root.add(categories);
		for (Element3D element_xValue : chart3DData){
			xValue = element_xValue.getXValue();
			if(!xValueList.contains(xValue)){
				xValueList.add(xValue);
				Element category=categories.addElement("category");
				category.addAttribute("label", xValue);
			}
		}
		    
		HashMap<String, String> yValueMap = new HashMap<String, String>();
		for (Element3D element_yValue : chart3DData){
			yValue = element_yValue.getYValue();
			if(!yValueMap.containsValue(yValue)){
				yValueMap.put(String.valueOf(i++), yValue);
			}
		}
		//按照Z轴进行拼装
		Iterator<?> iter = yValueMap.entrySet().iterator();
	    iter = yValueMap.entrySet().iterator();
	     while (iter.hasNext()) { 
	        Map.Entry entry = (Map.Entry) iter.next(); 
	        yValue = (String) entry.getValue();
	        Element dataset = DocumentHelper.createElement("dataset");
	        dataset.addAttribute("seriesName", yValue);
	        dataset.addAttribute("showValues", "0");
	        root.add(dataset);
	        for(int temp = 0;temp<xValueList.size();temp++){
        		xValue = xValueList.get(temp);
        		i = 0;
        		for (Element3D element_zValue : chart3DData){
        			i++;
        			if(element_zValue.getYValue().equals(yValue)&&element_zValue.getXValue().equals(xValue)){
		            	zValue = element_zValue.getZValue();
		            	Element set=dataset.addElement("set");
		            	set.addAttribute("value", Chart.doubleToStr(zValue,1));
		            	try {
		            		if(clickRegional != null && !clickRegional.equals("")){
		    					set.addAttribute("link","JavaScript:"+clickRegional+"('"+xValue+"','"+yValue+"','"+zValue+"')");
		    				}
		    				break;
		            	} catch (Exception e) {
		    				e.printStackTrace();
		    			}	           
		    		}else{
		    			if((chart3DData.size() == i)&&!(element_zValue.getYValue().equals(yValue)&&element_zValue.getXValue().equals(xValue)) ){
		    				Element set=dataset.addElement("set");
		    				set.addAttribute("value", "");
		    			}
		    		}
	        	}
	        }
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
