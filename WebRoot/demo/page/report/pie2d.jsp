<%@ page language="java" contentType="text/html charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<HTML>
    <HEAD>
        <META http-equiv="Content-Type" content="text/html;charset=UTF-8"/> 
        <TITLE>测试图表</TITLE>
          <SCRIPT LANGUAGE="Javascript" SRC="<n:path path="/wee/report/jsclass/FusionCharts.js"/>"></SCRIPT>
    </HEAD>
    <BODY>
    <script type="text/javascript">
		   function ExportMyChart() {
		        var chartObject = getChartFromId('myChart3');
		        if( chartObject.hasRendered() ) chartObject.exportChart();
		   }
	</script>      
        <CENTER>
           <%
            String strXML  = (String)request.getAttribute("ReportXML");
	        //Create an XML data document in a string variable
	        //String strXML  = JavaScriptUtils.javaScriptEscape((String)request.getAttribute("ReportXML"));
			//Create the chart - Column 3D Chart with data from strXML variable using dataXML method
        %>
        <div id="chartdiv3" align="center">测试三维图表</div>
        <n:chart chartType= "PIE_2D" width="600" height="400" id="myChartId3" dataXML="<%=strXML%>" divName="chartdiv3" />　　
　　       
         <input type="button" value="Export My Chart" onclick="ExportMyChart()" />   　
        </CENTER>
    </BODY>
</HTML> 
