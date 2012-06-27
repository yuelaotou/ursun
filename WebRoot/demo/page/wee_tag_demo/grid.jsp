<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<n:base />
		<script type="text/javascript"
			src="/wee_platform/wee/jquery/plugin/editable/jquery.editable-1.3.3.js"></script>
		<script>
		function showDetail(empno,empname){
			alert("<s:text name="empno"/>： "+empno+" <s:text name="ename"/>："+empname);
		}
		function showDetailInfo(index ,value,rowData){
			return rowData.empno;
		}
		
		$(function() {
			$("#queryButton").click(function() {
			 			
						$("#grid").reloadGrid("&empno="+$("#empno").val());
				});
				
		}
		);
		</script>

	</head>
	<body>
		<table width="100%" border="1">
			<s:textfield id="empno" name="empno" value="%{empno}" key="empno"></s:textfield>
			<tr>
				<td colspan="2" align="center">
					<input type="button" id="queryButton"
						value="<s:text name="query"/>" />
				</td>
			</tr>
		</table>
		<input id="gridType" name="gridType" type="hidden" value="1" />
		<n:grid dataSource="employeeList" id="grid" name="grid" width="100"
			cellSpacing="1" cellPadding="2" cssClass="gridTable" theme="ajax"
			action="demoShowAC!queryEmployeeListJson.do" pagination="pagination">
			<!-- 定义列表字段头 -->
			<n:gridheader>
				<n:selectall isSingle="false" width="5" />
				<n:columnmodel key="number" width="5"></n:columnmodel>
				<n:columnmodel key="empno" width="10" sortable="true"
					sortColumn="empno"></n:columnmodel>
				<n:columnmodel key="ename" width="10" sortable="true"
					sortColumn="ename"></n:columnmodel>
				<n:columnmodel key="sal" width="10" sortable="true" sortColumn="sal"></n:columnmodel>
				<n:columnmodel header="自定义列" width="5"></n:columnmodel>
				<n:columnmodel header="详细" width="5"></n:columnmodel>
			</n:gridheader>
			<!-- 定义列表分组 -->
			<n:gridgroup dataField="deptno" lable="部门：{deptno} "
				sortMode="asc" />
			<!-- 定义列表数据项 -->
			<n:gridtbody>
				<n:selectcolumn dataField="empno" />
				<n:rownumcolumn />
				<n:anchorcolumn dataField="empno"
					linkUrl="javascript:showDetail('{empno}','{ename}')" />
				<n:textcolumn dataField="ename" maxLength="1"/>
				<n:textcolumn dataField="sal" />
				<n:customcolumn dataField="sal" renderTo="showDetailInfo" />
				<n:imagecolumn
					imageSrc='<%=request.getContextPath() + "/platform/common/images/grid/Edit.gif"%>'
					linkUrl="javascript:showDetail('{empno}','{ename}')"
					imageBorder="0" imageWidth="16" imageHeight="16" />
			</n:gridtbody>
			<n:gridfooter>
				<!-- 定义列表分页 -->
				<n:gridpager />
			</n:gridfooter>
		</n:grid>
		<script>
		function querySelectData(){
			alert(getSelectData('grid').rowid);
		}
		</script>
		<input type="button" value="获取选择数据" onclick="querySelectData();">
		<n:footer/>
	</body>
</html>
