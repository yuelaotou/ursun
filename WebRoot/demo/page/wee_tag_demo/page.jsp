<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<n:base />
	<link type="text/css" href="./css/demo.css" rel="stylesheet" />
	<script>
		$(function() {
			$("#queryForm #queryButton").click(function() {
			 			if($("#queryForm #empno").val()==""){
			 				alert("员工号不能为空！");
			 					return;
			 			}
						if(!checkIsNum($("#queryForm #empno").val())){
							alert("员工号必须为数字！");
							return;
						}
						$("#queryForm").submit();
				})
		}
		);
		</script>
	</head>
	<body>
		<s:form id="queryForm"  action="pageAC!queryEmployeeByEmpno.do">
			<table width="100%" border="1">
				<s:textfield id="empno" name="empno" value="%{empno}" key="empno"></s:textfield>
				<tr>
					<td colspan="2" align="center">
						<input type="button" id="queryButton" value="<s:text name="query"/>" />
					</td>
				</tr>
			</table>
		</s:form>
		<br>
		<jsp:include page="pageSub.jsp"></jsp:include>

	</body>
</html>
