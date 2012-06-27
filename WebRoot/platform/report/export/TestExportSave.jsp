<HTML>
	<HEAD>
<script type="text/javascript">
   //Callback handler method which is invoked after the chart has saved image on server.
   function FC_Exported(objRtn){ 
      if (objRtn.statusCode=="1"){
         alert("The chart was successfully saved on server. The file can be accessed from " + objRtn.fileName);
      }else{
         alert("The chart could not be saved on server. There was an error. Description : " + objRtn.statusMessage);
      }
   }
</script> 
		<TITLE>FusionCharts - Export Example</TITLE>
		<%
			/*You need to include the following JS file, if you intend to embed the chart using JavaScript.
			Embedding using JavaScripts avoids the "Click to Activate..." issue in Internet Explorer
			When you make your own charts, make sure that the path to this JS file is correct. Else, you would get JavaScript errors.
			*/
			%>
		<SCRIPT LANGUAGE="Javascript" SRC="FusionCharts/FusionCharts.js"></SCRIPT>
		<style type="text/css">
			<!--
			body {
				font-family: Arial, Helvetica, sans-serif;
				font-size: 12px;
			}
			-->
		</style>
	</HEAD>
	<BODY>
		<CENTER>
			<h2>FusionCharts Export Example</h2>
			<h4>Exporting as Image or PDF</h4>
			<%
			

				//Create the chart - Column 2D Chart with data from Data/Data.xml
				
			%> 
			<jsp:include page="../Includes/FusionChartsRenderer.jsp" flush="true"> 
				<jsp:param name="chartSWF" value="FusionCharts/Column2D.swf" /> 
				<jsp:param name="strURL" value="Data/SaveData.xml" /> 
				<jsp:param name="strXML" value="" /> 
				<jsp:param name="chartId" value="myFirst" /> 
				<jsp:param name="chartWidth" value="600" /> 
				<jsp:param name="chartHeight" value="300" /> 
				<jsp:param name="debugMode" value="false" /> 	
				<jsp:param name="registerWithJS" value="true" /> 				
			</jsp:include>
			<BR>
			
		</CENTER>
	</BODY>
</HTML>
