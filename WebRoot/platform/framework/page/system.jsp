<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<n:base />
		<title>系统选择</title>
		<script type="text/javascript">
			function go(type){
				window.location.href = contextPath + "/platform/main!"+type+".do";
			}
		</script>
	</head>
	<body>
		<table id="select_system" width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td bgcolor="#1075b1">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td height="613" background="<n:path path="platform/framework/common/images/bg.jpg"/>">
					<table width="654" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td background="<n:path path="platform/framework/common/images/title.jpg"/>" width="654" height="243"></td>
						</tr>
						<tr>
							<td>
								<img src="<n:path path="platform/framework/common/images/system.jpg"/>" width="654" height="152" border="0" usemap="#Map" />
							</td>
						</tr>
						<tr>
							<td background="<n:path path="platform/framework/common/images/bottom.jpg"/>" width="654" height="218"></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td bgcolor="#152753">
					&nbsp;
				</td>
			</tr>
		</table>
		<map name="Map">
			<area shape="rect" coords="35,30,225,126" alt="业务管理" href="#" onclick="go('collection')"
				onmouseover="javascript:window.status='业务管理'">
			<area shape="rect" coords="235,30,425,126" alt="样例管理" href="#" onclick="go('demo')"
				onmouseover="javascript:window.status='样例管理'">
			<area shape="rect" coords="434,30,624,126" alt="系统管理" href="#" onclick="go('console')"
				onmouseover="javascript:window.status='系统管理'">
		</map>
	</body>
</html>
