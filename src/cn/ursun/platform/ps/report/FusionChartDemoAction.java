package cn.ursun.platform.ps.report;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import cn.ursun.platform.ps.report.chart.Chart;
import cn.ursun.platform.ps.report.chart.ChartFactory;
import cn.ursun.platform.ps.report.element.Element2D;
import cn.ursun.platform.ps.report.element.Element3D;
import com.opensymphony.xwork2.ModelDriven;



public class FusionChartDemoAction implements ModelDriven<Object>, ServletRequestAware 
{	

	private HttpServletRequest request;

    public String testPie() throws Exception  
    {
    	Chart chart=ChartFactory.getInstance().getChart(ChartType.PIE_3D);
    	
        //ReportVOExtendAttr dVOEA=new ReportVOExtendAttr();
        chart.setChartTitle("营祝公eee");
    	
        //ReportVO.setExtendAttr(dVOEA);
        
        LinkedList<Element2D> aList = new LinkedList<Element2D>();
        aList.add(new Element2D("法国",30));
        aList.add(new Element2D("中国",10.42));
        aList.add(new Element2D("爱尔兰",2));
        aList.add(new Element2D("日本",10));
        aList.add(new Element2D("韩国",8));
        aList.add(new Element2D("瑞典",5));
        aList.add(new Element2D("英国",11.41));
        chart.setChart2DData(aList);
       // Pie3DToXMLBS xmlbs = new Pie3DToXMLBS();
        String doc = chart.getDataConversion();
        request.setAttribute("ReportXML",doc);
        return "charts";
    }

    public String testBar() throws Exception 
    {
		Chart chart=ChartFactory.getInstance().getChart(ChartType.MSCOLUMN_3D);
        chart.setChartTitle("营祝公2");
        chart.setChartXtitle("单位2");
        chart.setChartYtitle("人数2");
    	chart.setChartType(ChartType.MSCOLUMN_3D);
               //chart.setValue(ChartConst.CHART_TYPE,ChartConst.CHART_TYPE_BAR);
        //ReportVO.setExtendAttr(dVOEA);
        
        LinkedList<Element3D> aList = new LinkedList<Element3D>();
       
        aList.add(new Element3D("kb","马涛",30));
        aList.add(new Element3D("kb","方军",18));
        aList.add(new Element3D("kb","刘宏发",12));
        aList.add(new Element3D("mc","马涛",20));
        aList.add(new Element3D("mc","方军",14));
        aList.add(new Element3D("mc","刘宏发",10));
        aList.add(new Element3D("fh","马涛",15));
        aList.add(new Element3D("fh","方军",17));
        aList.add(new Element3D("fh","刘宏发",9));
        chart.setChart3DData(aList);
//        MSCombi3DToXMLBS xmlbs = new MSCombi3DToXMLBS();
        String doc = chart.getDataConversion();
        request.setAttribute("ReportXML",doc);
        return "charts";
    }

    public String testLine() throws Exception
    {
    	Chart chart=ChartFactory.getInstance().getChart(ChartType.MSLINE_2D);
        chart.setChartTitle("营祝公11");
        chart.setChartXtitle("单位");
        chart.setChartYtitle("人数");
    	
      
        LinkedList<Element3D> aList = new LinkedList<Element3D>();
        
        aList.add(new Element3D("2008","马涛",10));
        aList.add(new Element3D("2007","马涛",15));
        aList.add(new Element3D("2006","马涛",20));
        aList.add(new Element3D("2005","马涛",30));
        aList.add(new Element3D("2004","马涛",10.42));
        aList.add(new Element3D("2003","马涛",15));
        aList.add(new Element3D("2008","方军",12));
        aList.add(new Element3D("2007","方军",15));
        aList.add(new Element3D("2006","方军",18));
        aList.add(new Element3D("2005","方军",17));
        aList.add(new Element3D("2004","方军",10));
        aList.add(new Element3D("2003","方军",1));
        aList.add(new Element3D("2008","刘红发",14));
        aList.add(new Element3D("2007","刘红发",13));
        aList.add(new Element3D("2006","刘红发",18));
        aList.add(new Element3D("2005","刘红发",10));
        aList.add(new Element3D("2004","刘红发",5));
        aList.add(new Element3D("2003","刘红发",6));
        chart.setChart3DData(aList);
        //MSLineToXMLBS xmlbs = new MSLineToXMLBS();
        String doc = chart.getDataConversion();
        request.setAttribute("ReportXML",doc);
        return "charts";
    }

    public String testLine3D() throws Exception 
    {
    	Chart chart=ChartFactory.getInstance().getChart(ChartType.LINE_3D);
        chart.setChartTitle("营祝公3D");
        chart.setChartXtitle("单位");
        chart.setChartYtitle("人数");
    	chart.setChartType(ChartType.LINE_3D);
      
        LinkedList<Element3D> aList = new LinkedList<Element3D>();
        
        aList.add(new Element3D("2008","马涛",10));
        aList.add(new Element3D("2007","马涛",15));
        aList.add(new Element3D("2006","马涛",20));
        aList.add(new Element3D("2005","马涛",30));
        aList.add(new Element3D("2004","马涛",10.42));
        aList.add(new Element3D("2003","马涛",15));
        aList.add(new Element3D("2008","方军",12));
        aList.add(new Element3D("2007","方军",15));
        aList.add(new Element3D("2006","方军",18));
        aList.add(new Element3D("2005","方军",17));
        aList.add(new Element3D("2004","方军",10));
        aList.add(new Element3D("2003","方军",1));
        aList.add(new Element3D("2008","刘红发",14));
        aList.add(new Element3D("2007","刘红发",13));
        aList.add(new Element3D("2006","刘红发",18));
        aList.add(new Element3D("2005","刘红发",10));
        aList.add(new Element3D("2004","刘红发",5));
        aList.add(new Element3D("2003","刘红发",6));
        chart.setChart3DData(aList);
        //MSLineToXMLBS xmlbs = new MSLineToXMLBS();
        String doc = chart.getDataConversion();
        request.setAttribute("ReportXML",doc);
        return "charts";
    }
    
    public String testStack() throws Exception 
    {
    	Chart chart=ChartFactory.getInstance().getChart(ChartType.STACKEDCOLUMN_3D);
        chart.setChartTitle("营祝专利统计图公");
        chart.setChartXtitle("单位");
        chart.setChartYtitle("人数");
    	chart.setChartType(ChartType.STACKEDCOLUMN_3D);
      
        LinkedList<Element3D> aList = new LinkedList<Element3D>();
        
        aList.add(new Element3D("2008","马涛",10));
        aList.add(new Element3D("2007","马涛",15));
        aList.add(new Element3D("2006","马涛",20));
        aList.add(new Element3D("2005","马涛",30));
        aList.add(new Element3D("2004","马涛",10.42));
        aList.add(new Element3D("2003","马涛",15));
        aList.add(new Element3D("2008","方军",12));
        aList.add(new Element3D("2007","方军",15));
        aList.add(new Element3D("2006","方军",18));
        aList.add(new Element3D("2005","方军",17));
        aList.add(new Element3D("2004","方军",10));
        aList.add(new Element3D("2003","方军",1));
        aList.add(new Element3D("2008","刘红发",14));
        aList.add(new Element3D("2007","刘红发",13));
        aList.add(new Element3D("2006","刘红发",18));
        aList.add(new Element3D("2005","刘红发",10));
        aList.add(new Element3D("2004","刘红发",5));
        aList.add(new Element3D("2003","刘红发",6));
        chart.setChart3DData(aList);
        //MSLineToXMLBS xmlbs = new MSLineToXMLBS();
        String doc = chart.getDataConversion();
        request.setAttribute("ReportXML",doc);
        return "charts";
    }
    
    public String testCombin() throws Exception 
    {
    	Chart chart=ChartFactory.getInstance().getChart(ChartType.COMBIN_BAR);
        chart.setChartTitle("testCombin专利统计图");
        chart.setChartXtitle("testCombin申请人");
        chart.setChartYtitle("testCombin专利数");
    	chart.setChartType(ChartType.COMBIN_BAR);
      
        LinkedList<Element3D> aList = new LinkedList<Element3D>();
        
        aList.add(new Element3D("2008","马涛",10));
        aList.add(new Element3D("2007","马涛",15));
        aList.add(new Element3D("2006","马涛",20));
        aList.add(new Element3D("2005","马涛",30));
        aList.add(new Element3D("2004","马涛",10.42));
        aList.add(new Element3D("2003","马涛",15));
        aList.add(new Element3D("2008","方军",12));
        aList.add(new Element3D("2007","方军",15));
        aList.add(new Element3D("2006","方军",18));
        aList.add(new Element3D("2005","方军",17));
        aList.add(new Element3D("2004","方军",10));
        aList.add(new Element3D("2003","方军",1));
        aList.add(new Element3D("2008","刘红发",14));
        aList.add(new Element3D("2007","刘红发",13));
        aList.add(new Element3D("2006","刘红发",18));
        aList.add(new Element3D("2005","刘红发",10));
        aList.add(new Element3D("2004","刘红发",5));
        aList.add(new Element3D("2003","刘红发",6));
        chart.setChart3DData(aList);
        //MSLineToXMLBS xmlbs = new MSLineToXMLBS();
        String doc = chart.getDataConversion();
        request.setAttribute("ReportXML",doc);
        return "charts";
    }
 

	public void setServletRequest(HttpServletRequest httpServletRequest) {
		 this.request = httpServletRequest;
		
	}
	public Object getModel() {
		return null;
	}

}
