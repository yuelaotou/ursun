/**
 * 文件名：Element2D.java
 *
 * 创建人：【周洋】 - 【zhouyang@neusoft.com】
 *
 * 创建时间：Dec 10, 2009 7:43:58 PM
 *
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.ps.report.element;


/**
 * <p>【报表二维元素数据：有两个坐标，X,Y】</p>
 * @author 【周洋】 - 【zhouyang@neusoft.com】
 * @version 1.0
 */
public class Element2D{
	/**
	 * 二维元素对应的X坐标值
	 */
	private String xValue = null;
	/**
	 * 二维元素对应的Y坐标值
	 */
	private Double yValue = 0.0;
	public String getXValue() {
		return xValue;
	}
	public void setXValue(String value) {
		xValue = value;
	}
	public Double getYValue() {
		return yValue;
	}
	public void setYValue(Double value) {
		yValue = value;
	}
	
    public Element2D(String xValue)
    {
        this.xValue = xValue;
    }
    
	public Element2D(String xValue,double yValue){
		this.xValue = xValue;
		this.yValue = yValue;
	}
	
	public String toString()
    {
        StringBuffer body = new StringBuffer(20);
        if (this.xValue != null)
        {
            body.append("xValue:" + this.xValue);
        }
        body.append("yValue:" + this.yValue);
        return body.toString();
    }
}
