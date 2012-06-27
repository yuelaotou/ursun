/**
 * 文件名：Chart.java
 *
 * 创建人：【周洋】 - 【zhouyang@neusoft.com】
 *
 * 创建时间：Dec 12, 2009 2:05:02 PM
 *
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.ps.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.struts2.components.Component;

import cn.ursun.platform.core.tag.Pages;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * <p> Title: 报表展现Bean  </p>
 * <p> Description: [描述]</p>
 * <p> Created on 2009-12-11</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author 周洋 zhouyang@neusoft.com
 * @version 1.0
 */
public class Chart extends Component {

	private String chartType;
	
	private int width;
	
	private int height;
	
	private String id;
	
	private String dataXML = "<chart></chart>";
	
	private String divString;
	
	private String url = "\"../wee/report/swf/";
	
	public String getDivString() {
		return divString;
	}

	public void setDivString(String divString) {
		this.divString = divString;
	}

	public Chart(ValueStack valueStack) {
		super(valueStack);
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataXML() {
		return dataXML;
	}

	public void setDataXML(String dataXML) {
		this.dataXML = dataXML;
	}

	@Override
	public boolean start(Writer writer) {
		boolean result = super.start(writer);
		String swf_url;
		boolean isValid = true;
		// 从ValueStack中取出数值
		if (isValid) {
			if (dataXML.startsWith("%{") && dataXML.endsWith("}")) {
				dataXML = dataXML.substring(2, dataXML.length() - 1);
				dataXML = this.getStack().findValue(dataXML, String.class).toString();
				isValid = dataXML == null ? false : true;
			}
		}
		
		try {
			StringBuilder str = new StringBuilder();
			str.append("<script type=\"text/javascript\"  charset=\"UTF-8\">");
			str.append("var var_"+id+" = new FusionCharts(");
			if(chartType.equals("COLUMN_2D")){
				swf_url = url+"Column2D.swf\"";//2D单柱状图
			}else if(chartType.equals("BAR_2D")){
				swf_url	= url+"Bar2D.swf\"";//2DBAR状图
			}else if(chartType.equals("MSBAR_2D")){
				swf_url = url+"MSBar2D.swf\"";
			}else if(chartType.equals("COLUMN_3D")){
				swf_url = url+"Column3D.swf\"";//3D柱状图
			}else if(chartType.equals("MSCOLUMN_3D")){
				swf_url = url+"MSColumn3D.swf\"";//3D多维多柱状图
			}else if(chartType.equals("MSBAR_3D")){
				swf_url = url+"MSBar3D.swf\"";//3D多BAR柱状图
			}else if(chartType.equals("COMBIN_BAR")){
				swf_url = url+"MSCombi3D.swf\"";//三维柱状图
			}else if(chartType.equals("PIE_2D")){
				swf_url = url+"Pie2D.swf\"";//2D饼图
			}else if(chartType.equals("PIE_3D")){
				swf_url = url+"Pie3D.swf\"";//3D饼图
			}else if(chartType.equals("LINE_2D")){
				swf_url = url+"Line.swf\"";//折线图
			}else if(chartType.equals("MSLINE_2D")){
				swf_url = url+"MSLine.swf\"";//二维多线折线图
			}else if(chartType.equals("LINE_3D")){
				swf_url = url+"MSCombi3D.swf\"";//3D折线图
			}else if(chartType.equals("STACKEDCOLUMN_2D")){
				swf_url = url+"StackedColumn2D.swf\"";//二维堆积Bar图
			}else if(chartType.equals("STACKEDBAR_2D")){
				swf_url = url+"StackedBar2D.swf\"";//2D堆积Bar图
			}else if(chartType.equals("STACKEDCOLUMN_3D")){
				swf_url = url+"StackedColumn3D.swf\"";//二维堆积Bar图
			}else if(chartType.equals("STACKEDBAR_3D")){
				swf_url = url+"StackedBar3D.swf\"";//3D堆积Bar图
			}else{
				swf_url = null;
			}
				
			str.append(swf_url);
			str.append(",\""+id+"\"");
			str.append(",\""+width+"\",\""+height+"\",\"0\",\"1\"); ");
			if (isValid) {
				if(dataXML!=null&&dataXML!=""){
					str.append("var_"+id+".setDataXML(\""+dataXML+"\"); ");
				}
			}
			if(divString!=null&&divString!=""){
				str.append("var_"+id+".render(\""+divString+"\");");
			}
			str.append("</script>");
			
			writer.write(str.toString());
		} catch (IOException ex) {
			Logger.getLogger(Pages.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return result;
	}	

}
