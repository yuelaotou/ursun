<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>req_info</title>

		<link rel="stylesheet" type="text/css" href="/sipo/base.css">

    	
		<script type="text/javascript">
		</script>
	</head>
  
  <body>
  		<a href ="<s:url action="fusionBar"/>">测试柱状图</a>
		<a href ="<s:url action="fusionPie"/>">测试饼图</a>
		<a href ="<s:url action="fusionPie2d"/>">二维饼图</a>
		<a href ="<s:url action="fusionLine"/>">二维折线图</a>
		<a href ="<s:url action="line3D"/>">三维折线图</a>
		<a href ="<s:url action="fusionStack2D"/>">测试堆积图</a>
		<a href ="<s:url action="fusionStack"/>">测试三维堆积图</a>
		<a href ="<s:url action="fusion3DBar"/>">三维柱状图</a>
  </body>
</html>