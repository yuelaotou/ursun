/**
 * 文件名：Chart.java
 *
 * 创建人：【周洋】 - 【zhouyang@neusoft.com】
 *
 * 创建时间：Dec 15, 2009 8:14:23 PM
 *
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.ps.report.chart;

import java.text.DecimalFormat;
import java.util.List;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.report.ChartType;
import cn.ursun.platform.ps.report.element.Element2D;
import cn.ursun.platform.ps.report.element.Element3D;

/**
 * <p>【报表展现抽象类】</p>
 * @author 【周洋】 - 【zhouyang@neusoft.com】
 * @version 1.0
 */
public abstract class Chart {
	protected List<Element2D> chart2DData;
	
	protected List<Element3D> chart3DData;
	
	protected ChartType chartType;
	
	protected String chartTitle;
	
	protected String chartXtitle;
	
	protected boolean isYAxisToInteger = true; 
	
	protected String chartYtitle;
	/**
	 * 数字标签前缀
	 */
	protected String numberPrefix = "";
	/**
	 * 数字标签后缀
	 */
	protected String numberSuffix = "";
	/**
	 * 图表导出手柄
	 */
	protected String exportHandler = "";
	/**
	 * 图表点击时间回调函数名
	 */
	protected String clickRegional;
	
	public String getClickRegional() {
		return clickRegional;
	}

	public void setClickRegional(String clickRegional) {
		this.clickRegional = clickRegional;
	}

	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public String getChartXtitle() {
		return chartXtitle;
	}

	public void setChartXtitle(String chartXtitle) {
		this.chartXtitle = chartXtitle;
	}

	public String getChartYtitle() {
		return chartYtitle;
	}

	public void setChartYtitle(String chartYtitle) {
		this.chartYtitle = chartYtitle;
	}
    
    /**
	 * <p>数据转换方法</p>
	 * 
	 * @param e
	 * @param message
	 * @throws Exception
	 * @author: 周洋 - zhouyang@neusoft.com 
	 * @date: Created on Dec 10, 2009 5:13:54 PM
	 */
    public abstract String getDataConversion()throws BizException;

	public ChartType getChartType() {
		return chartType;
	}

	public void setChartType(ChartType chartType) {
		this.chartType = chartType;
	}
	
    /**
	 * <p>将double转换成String类型</p>
	 * 
	 * @param d
	 * @param fNumber
	 * @author: 周洋 - zhouyang@neusoft.com 
	 * @date: Created on Dec 10, 2009 5:13:54 PM
	 */
    protected static String doubleToStr(double d, int fNumber) {
        if (fNumber < 0)
             fNumber = 0;

        String pattern = null;
        switch (fNumber) {
        case 0:
            pattern = "#0"; //$NON-NLS-1$
            break;
        default:
            pattern = "#0."; //$NON-NLS-1$

           StringBuffer b = new StringBuffer(pattern);
            for (int i = 0; i < fNumber; i++) {
                b.append('#');
            }
            pattern = b.toString();
            break;
       }
       DecimalFormat formatter = new DecimalFormat();
       formatter.applyPattern(pattern);
       String value = formatter.format(d);
       return value;
    }
    /**
     * <p>获取到当前报表的最大Y轴标签的值</p>
     * @author: 【周洋】 - 【zhouyang@neusoft.com】
     * @data: Create on Mar 1, 2010 2:19:49 PM
     */
    protected static double get2DMaxValue(List<Element2D> chartData){
    	double maxValue = 0;
    	for(int i = 0;i<chartData.size();i++){
    		if(maxValue<chartData.get(i).getYValue()){
    			maxValue = chartData.get(i).getYValue();
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
    
    /**
     * <p>获取到当前报表的最大Y轴标签的值</p>
     * @author: 【周洋】 - 【zhouyang@neusoft.com】
     * @data: Create on Mar 1, 2010 2:19:49 PM
     */
    protected static double get3DMaxValue(List<Element3D> chartData){
    	double maxValue = 0;
    	for(int i = 0;i<chartData.size();i++){
    		if(maxValue<chartData.get(i).getZValue()){
    			maxValue = chartData.get(i).getZValue();
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

	public List<Element2D> getChart2DData() {
		return chart2DData;
	}

	public List<Element3D> getChart3DData() {
		return chart3DData;
	}

	public String getExportHandler() {
		return exportHandler;
	}

	public void setChart2DData(List<Element2D> chart2DData) {
		this.chart2DData = chart2DData;
	}

	public void setChart3DData(List<Element3D> chart3DData) {
		this.chart3DData = chart3DData;
	}

	public void setExportHandler(String exportHandler) {
		this.exportHandler = exportHandler;
	}

	public String getNumberPrefix() {
		return numberPrefix;
	}

	public void setNumberPrefix(String numberPrefix) {
		this.numberPrefix = numberPrefix;
	}

	public String getNumberSuffix() {
		return numberSuffix;
	}

	public void setNumberSuffix(String numberSuffix) {
		this.numberSuffix = numberSuffix;
	}

	public boolean isYAxisToInteger() {
		return isYAxisToInteger;
	}

	public void setYAxisToInteger(boolean isYAxisToInteger) {
		this.isYAxisToInteger = isYAxisToInteger;
	}
}
