/**
 * 文件名：ChartFactory.java
 *
 * 创建人：【周洋】 - 【zhouyang@neusoft.com】
 *
 * 创建时间：Dec 15, 2009 9:16:03 PM
 *
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.ps.report.chart;

import java.util.HashMap;
import java.util.Map;

import cn.ursun.platform.ps.report.ChartType;

/**
 * <p>【报表工厂类】</p>
 * @author 【周洋】 - 【zhouyang@neusoft.com】
 * @version 1.0
 */
public class ChartFactory {
	public static boolean availiable = true;

	private static HashMap<String, Chart> maps = new HashMap<String, Chart>();
	
	private static Chart chart = null;

	private static ChartFactory chartFactory = null;

    /**
     * 
     * @return
     */
    public static ChartFactory getInstance()
    {
       if(chartFactory == null&&(availiable)){
    	   chartFactory = new ChartFactory();
       }
       return chartFactory;
    }
    
    public Chart getChart(ChartType chartType){
    	chart = maps.get(chartType.name());
    	return chart;
    }
    
	public HashMap<String, Chart> getMaps() {
		return maps;
	}

	public void setMaps(HashMap<String, Chart> maps) {
		this.maps = maps;
	}
}
