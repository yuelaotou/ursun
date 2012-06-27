<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>系统center</title>
		<n:base />
		<style type="text/css">
		<!--
		body {
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
			overflow:hidden;
		}
.navPoint { 
COLOR: white; CURSOR: hand; FONT-FAMILY: Webdings; FONT-SIZE: 9pt 
} 
		-->
</style>
		<script>
function switchSysBar(){
	if($("#frmTitle").css("display")!="none"){
		$("#frmTitle").hide('slow');
		$("#switchPoint").attr("title","打开左栏");
	}else{
		$("#frmTitle").show('slow');
		$("#switchPoint").attr("title","关闭左栏");
	}
}
</script>
	</head>
	<body>
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="8" bgcolor="#353c44">
					&nbsp;
				</td>
				<td width="147" id="frmTitle" nowrap="nowrap" name="frmTitle" align="center" valign="top">
					<%--					<s:property value="system" />sss--%>
					<%--					<s:if test="#system=='collection'">--%>
					<%--						collection--%>
					<%--					</s:if>--%>
					<%--					<s:if test="#system=='demo'">--%>
					<%--						demo--%>
					<%--					</s:if>--%>
					<%--					<s:if test="#system=='console'">--%>
					<%--						console--%>
					<%--					</s:if>--%>
					<iframe height="100%" width="100%" border="0" frameborder="0" scrolling="no"
						src="<n:path path="platform/framework/page/left.jsp"/>"></iframe>
				</td>
				<td width="9" valign="middle" bgcolor="#add2da" onclick="switchSysBar()">
					<SPAN class=navPoint id=switchPoint title="关闭左栏"><img
							src="<n:path path="platform/framework/common/images/main_41.gif"/>" name="img1" width=9 height=52 id=img1> </SPAN>
				</td>
				<td valign="top">
					<iframe name="contentFrame" height="100%" width="100%" border="0" frameborder="0"
						src="<n:path path="platform/framework/page/right.jsp"/>"></iframe>
				</td>
				<td width="8" bgcolor="#353c44">
					&nbsp;
				</td>
			</tr>
		</table>
	</body>
</html>
