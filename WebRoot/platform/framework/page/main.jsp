<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<n:base />
		<style type="text/css">
		<!--
		body {
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
		}
		-->
</style>
	</head>
		<frameset rows="127,*,11" frameborder="no" border="0" framespacing="0">
			<frame src="<n:path path="platform/header!init.do"/>" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" />
			<frame src="<n:path path="platform/framework/page/center.jsp"/>" name="mainFrame" id="mainFrame" />
			<frame src="<n:path path="platform/framework/page/bottom.jsp"/>" name="bottomFrame" scrolling="No" noresize="noresize" id="bottomFrame" />
		</frameset>
		<noframes>
	<body>
<%--		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">--%>
<%--			<tr height="127">--%>
<%--				<td height="127">--%>
<%--					<iframe height="100%" width="100%" border="0" frameborder="0" scrolling="no" name="topFrame" id="topFrame"--%>
<%--						src="<n:path path="platform/header!init.do"/>"></iframe>--%>
<%--				</td>--%>
<%--			</tr>--%>
<%--			<tr height="100%">--%>
<%--				<td height="100%">--%>
<%--					<iframe height="100%" width="100%" border="0" frameborder="0" scrolling="auto" name="mainFrame" id="mainFrame"--%>
<%--						 marginWidth="0" marginheight="0" frameborder="0" src="<n:path path="platform/framework/page/center.jsp"/>"></iframe>--%>
<%--				</td>--%>
<%--			</tr>--%>
<%--			<tr height="11">--%>
<%--				<td height="11">--%>
<%--					<iframe height="100%" width="100%" border="0" frameborder="0" scrolling="no" name="bottomFrame" id="bottomFrame"--%>
<%--						src="<n:path path="platform/framework/page/bottom.jsp"/>"></iframe>--%>
<%--				</td>--%>
<%--			</tr>--%>
<%--		</table>--%>
	</body>
		</noframes>
</html>
