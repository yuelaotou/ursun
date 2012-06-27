<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<title><s:text name="forbitAccess.title"/></title>
		<link rel="stylesheet" type="text/css" href="<n:path path="/platform/common/css/base.css"/>"/>
		<style type="text/css">
		<!-- 
			#error_tab{width:960px; margin:0px auto; padding-bottom:10px; margin-top:80px;}
			.error_title{padding:12px 0px 0px 20px; color:#2c6d9e;}
			.error_content{width:960px; margin:0px auto;}
			.error_top{width:960px; height:33px; background:url(<n:path path="platform/common/images/error/xtbc_top.gif"/>) no-repeat;}
			.error_center{width:960px; background:url(<n:path path="platform/common/images/error/xtbc_conter.gif"/>)}
			.error_center1{width:756px; background:url(<n:path path="platform/common/images/error/xtbc_content.gif"/>); margin:0px auto; padding:120px 0px 120px 200px;}
			.error_footer{width:960px; background:url(<n:path path="platform/common/images/error/xtbc_button.gif"/>) no-repeat; height:16px; }
			.error_center1 h3{color:#296d9c; font-weight:bold; font-size:14px; padding:0px 0px 4px 10px;}
			#error_msg{
				width:500px;
				float:left;
				padding-top:25px;
			}
			#error_img{
				width:150px;
				float:left;
				height:150px;
				background:url(<n:path path="platform/common/images/error/xtbc.gif"/>) no-repeat center  right
			}
			#error_msg_stack{
				padding:10px 30px;
				width:500px;
				overflow :auto
			}
			
			#error_msg_stack li{
				padding-left:10px;
				line-height:24px; 
				color:#666;
				background:url(<n:path path="platform/common/images/error/ico1.jpg"/>) 2px 10px no-repeat;
			}
			
			#error_stack{
				padding:10px ;
				width:500px;
				height:400px;
				overflow :auto;
				border:2px solid #f7f7f7;
				display:none;
			}
			
			#seedetail{
				margin:10px 0px 10px 20px;
				font-size:12px;
				float:left;
			}
			
			.clear{ clear:both;}
		-->
		</style>
	</head>
	<body>
		<s:set name="errorMsg"  value="exception.message"></s:set>
		<div id="error_tab">
	    <div class="error_content">
	        <div class="error_top">
	        <h3 class="error_title"><s:text name="wee.error.title"></s:text></h3>
	        </div>
	        <div class="error_center">
	        <div class="error_center1">
	        	<div id="error_img"></div>
	        	<div id="error_msg">
	        	<h3><s:text name="forbitAccess.message"></s:text></h3>
				</div>
				<div class="clear"/>
	        </div>
	        </div>
	        <div class="error_footer"></div>
	    </div>
	    </div>
	</body>
</html>