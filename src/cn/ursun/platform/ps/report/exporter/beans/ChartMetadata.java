/**
 * 文件名：ChartMetadata.java
 *
 * 创建人：【周洋】 - 【zhouyang@neusoft.com】
 *
 * 创建时间：Dec 23, 2009 1:49:32 PM
 *
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.ps.report.exporter.beans;

/**
 * <p>【报表导出图形样式数据】</p>
 * @author 【周洋】 - 【zhouyang@neusoft.com】
 * @version 1.0
 */
public class ChartMetadata {
	   public ChartMetadata()
	    {
	        width = -1;
	        height = -1;
	        DOMId = "";
	    }

	    public int getWidth()
	    {
	        return width;
	    }

	    public void setWidth(int width)
	    {
	        this.width = width;
	    }

	    public int getHeight()
	    {
	        return height;
	    }

	    public void setHeight(int height)
	    {
	        this.height = height;
	    }

	    public String getBgColor()
	    {
	        return bgColor;
	    }

	    public void setBgColor(String bgColor)
	    {
	        this.bgColor = bgColor;
	    }

	    public String getDOMId()
	    {
	        return DOMId;
	    }

	    public void setDOMId(String id)
	    {
	        DOMId = id;
	    }

	    private int width;
	    private int height;
	    private String bgColor;
	    private String DOMId;
}
