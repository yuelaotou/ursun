<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<!doctype html>
<html>
<head>
	<n:base />
	<n:jquery components="progressbar"/>
	<link type="text/css" href="./css/jquery-ui-demo.css" rel="stylesheet" />
	<style type="text/css">
		.ui-progressbar-value { background-image: url(images/pbar-ani.gif); }
	</style>
	<script type="text/javascript">
		var i=1;
		
		function createProgressbar(){
			$("#progressbar").progressbar({
				value: 0,
				change: function(event, ui) {
					var value = $('#progressbar').progressbar('option', 'value');
					if(value==100){
						$('#progressbar').progressbar("disable");
					}
				}
				
			});
		}
		
		var obj=null;
		function start(){
			i=1;
			createProgressbar();
			$("#pause").show() ;
			if(obj==null){
				obj=window.setInterval(function () {
	 			$("#progressbar").progressbar('value' ,[i++]);
				},100);
			}
		}
		
		function pause(){
			if(obj!=null){
				window.clearInterval(obj);
				obj=null;
			}
			if($("#pause").text()=="停止"){
				$("#pause").text("继续");
			}else{
				$("#pause").text("停止");
				if(obj==null){	
					obj=window.setInterval(function () {
		 			$("#progressbar").progressbar('value' ,[i++]);
					},100);
				}
			}
		}
		
		function stop(){
			if(obj!=null){
				window.clearInterval(obj);
				obj=null;
			}
			$("#pause").hide() ;
			$("#progressbar").progressbar("destroy");
		}
		
		$(function() {
			$("#pause").hide() ;
		});
	</script>
</head>
<body>

<h1>进度条</h1>
<div class="demo">
<button id="start" class="ui-button ui-state-default ui-corner-all" onclick="start()">开始</button>
<button id="pause" class="ui-button ui-state-default ui-corner-all" onclick="pause()">停止</button>
<button id="stop" class="ui-button ui-state-default ui-corner-all" onclick="stop()">终止</button>
<div id="progressbar"></div>
<div class="demo-description">
<p>
This progressbar has an animated fill by setting the
<code>background-image</code>
on the
<code>.ui-progressbar-value</code>
element, using css.
</p>
</div>
</div>

</body>
</html>

