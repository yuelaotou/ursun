/**
 * 文件名：ChartTag.java
 *
 * 创建人：【周洋】 - 【zhouyang@neusoft.com】
 *
 * 创建时间：Dec 12, 2009 1:43:07 PM
 *
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.ps.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * <p>【报表展示标签】</p>
 * @author 【周洋】 - 【zhouyang@neusoft.com】
 * @version 1.0
 */
public class ChartTag extends ComponentTagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 619735141701574998L;

	private String chartType;
	
	private int width = 400;
	
	private int height = 300;
	
	private String id;
	
	private String dataXML = "<chart></chart>";
	
	private String divName;
	
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
	
	public ChartTag() {
		super();
		this.height = 100;
		this.width = 100;
	}

	/**
	 * <p>【报表标签】</p>
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return
	 * @author: 【周洋】 - 【zhouyang@neusoft.com】
	 * @data: Create on Dec 12, 2009 1:43:08 PM
	 */
	@Override
	public Component getBean(ValueStack valueStack, HttpServletRequest req, HttpServletResponse res) {
		return new Chart(valueStack);
	}

	public String getDivName() {
		return divName;
	}

	public void setDivName(String divName) {
		this.divName = divName;
	}
	
	protected void populateParams() {
		super.populateParams();

		Chart chart = (Chart) component;
		chart.setChartType(chartType);
		chart.setDataXML(dataXML);
		chart.setDivString(divName);
		chart.setHeight(height);
		chart.setId(id);
		chart.setWidth(width);
		chart.setId(this.id);
	}

}
