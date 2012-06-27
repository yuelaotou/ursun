<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<n:base />
		<n:jquery components="form,calendar" />
		<link rel="stylesheet"
			href="<n:path path="yue/common/css/style.css"/>" type="text/css"></link>
		<script type="text/javascript"
			src="<n:path path="/platform/common/js/weeGrid.min.js" />"></script>
		<script type="text/javascript"
			src="<n:path path="yue/common/js/common.js" />"></script>
		<script type="text/javascript"
			src="<n:path path="yue/app/org/js/org_list.js"/>"></script>
	</head>
	<body>
		<div id="maintain_main">
			<span class="maintain_h1">单位查询</span>
			<s:form id="queryForm" action="orgAC!queryOrgList.do">
				<input type="hidden" name="wee.bizlog.modulelevel" value="0500101">
				<div>
					<span>单位编号：</span>
					<s:textfield id="orgId" name="orgQC.orgId" size="20" maxlength="20"
						theme="simple"></s:textfield>
					<span>单位名称：</span>
					<s:textfield id="orgName" name="orgQC.orgName" size="20"
						maxlength="36" theme="simple"></s:textfield>
					<div class="clear" style="height:10px"></div>
					<span>办事处：</span>
					<s:select id="officeCode" name="orgQC.officeCode"
						list="officeCodeList" listKey="officeCode" listValue="officeName"
						headerKey="" headerValue="请选择..." emptyOption="false">
					</s:select>
					<span>开户日期：</span>
					<s:textfield id="openDate" name="orgQC.openDate" maxlength="10"
						theme="simple" />
				</div>
				<div style="padding-top:10px;height:40px">
					<table align="right" width="120">
						<tr>
							<td>
								<button id="queryButton"
									class="ui-button ui-state-default ui-corner-all">
									查 询
								</button>
								<button id="resetButton"
									class="ui-button ui-state-default ui-corner-all"
									onclick="__reset('queryForm')">
									重 置
								</button>
							</td>
						</tr>
					</table>
				</div>
			</s:form>
			<div class="clear"></div>
			<div id=grid_div>
				<n:grid dataSource="orgList" id="grid" name="grid" width="50"
					cellSpacing="1" cellPadding="1" cssClass="gridTable"
					action="orgAC!queryOrgList.do" pagination="pagination">
					<!-- 定义列表字段头 -->
					<n:gridheader>
						<n:selectall isSingle="true" width="5" />
						<n:columnmodel key="单位编号" width="15" sortable="true"
							sortColumn="orgId"></n:columnmodel>
						<n:columnmodel key="单位名称" width="15" sortable="true"
							sortColumn="orgName"></n:columnmodel>
						<n:columnmodel key="办事处" width="10" sortable="true"
							sortColumn="officeCode"></n:columnmodel>
						<n:columnmodel key="归集银行" width="10" sortable="true"
							sortColumn="collBank"></n:columnmodel>
						<n:columnmodel key="开户时间" width="10" sortable="true"
							sortColumn="openDate"></n:columnmodel>
					</n:gridheader>
					<!-- 定义列表数据项 -->
					<n:gridtbody>
						<n:selectcolumn dataField="orgId" />
						<n:textcolumn dataField="orgId" />
						<n:anchorcolumn dataField="orgName"
							linkUrl="javascript:showInfo(${orgId})" />
						<n:textcolumn dataField="officeCode" />
						<n:textcolumn dataField="collBank" />
						<n:datecolumn dataField="openDate" dataFormat="yyyy-MM-dd" />
					</n:gridtbody>
					<n:gridfooter>
						<!-- 定义列表分页 -->
						<n:gridpager />
					</n:gridfooter>
				</n:grid>
				<div style="height:20px;margin-top:5px;">
					<n:button id="createButton" value="创 建" buttonClass="border-blue" />
					<n:button id="updateButton" value="修 改" buttonClass="border-blue" />
					<n:button id="deleteButton" value="删 除" buttonClass="border-blue" />
					<n:button id="releaseButton" value="发 布" buttonClass="border-blue" />
					<n:button id="donotRelease" value="取消发布" buttonClass="border-blue" />
				</div>
			</div>
		</div>
	</body>
</html>
