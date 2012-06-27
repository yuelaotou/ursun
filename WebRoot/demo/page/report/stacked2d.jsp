<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<HTML>
    <HEAD>
        <META http-equiv="Content-Type" content="text/html;charset=UTF-8"/> 
        <TITLE>测试图表</TITLE>
          <SCRIPT LANGUAGE="Javascript" SRC="<n:path path="/wee/report/jsclass/FusionCharts.js"/>"></SCRIPT>
    </HEAD>
    <BODY>
        <CENTER>
           <%

           String strXML  = (String)request.getAttribute("ReportXML");
        %>
        <div id="chartdiv3" align="center">测试三维图表</div>　　
　　       <n:chart chartType="STACKEDCOLUMN_2D" width="600" height="400" id="myChartId3" dataXML="<%=strXML%>" divName="chartdiv3" />　　
　　 	 </CENTER>
    </BODY>
</HTML> 
