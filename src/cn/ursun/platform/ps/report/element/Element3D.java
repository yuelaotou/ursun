/**
 * 文件名：Element3D.java
 *
 * 创建人：【周洋】 - 【zhouyang@neusoft.com】
 *
 * 创建时间：Dec 10, 2009 7:51:42 PM
 *
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.ps.report.element;

/**
 * <p>【报表二维元素数据：有三个坐标，X,Y，Z】</p>
 * @author 【周洋】 - 【zhouyang@neusoft.com】
 * @version 1.0
 */
public class Element3D {

	private String xValue = null;
	
	private String yValue = null;
	
	private Double zValue = 0.0;

	/**
	 *
	 * @return 
	 */
	public String getXValue() {
		return xValue;
	}

	/**
	 *
	 * @param value
	 */
	public void setXValue(String value) {
		xValue = value;
	}

	/**
	 *
	 * @return 
	 */
	public String getYValue() {
		return yValue;
	}

	/**
	 *
	 * @param value
	 */
	public void setYValue(String value) {
		yValue = value;
	}

	/**
	 *
	 * @return 
	 */
	public double getZValue() {
		return zValue;
	}

	/**
	 *
	 * @param value
	 */
	public void setZValue(double value) {
		zValue = value;
	}
	
	public Element3D(String xValue,String yValue,double zValue){
		this.xValue = xValue;
		this.yValue = yValue;
		this.zValue = zValue;
	}
	
	public String toString()
    {
        StringBuffer body = new StringBuffer(20);
        if (this.xValue != null)
        {
            body.append("xValue:" + this.xValue);
        }
        if (this.yValue != null)
        {
            body.append("yValue:" + this.yValue);
        }
        body.append("zValue:" + this.zValue);
        return body.toString();
    }
}
