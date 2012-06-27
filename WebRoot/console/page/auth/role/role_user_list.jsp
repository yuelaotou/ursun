<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<n:path path="/console/page/auth/role/js/role_user_list.js"/>"></script>
<div class="column_content">
	<s:hidden id="userName" value="%{userQC.userName}"></s:hidden>
	<s:hidden id="fullName" value="%{userQC.fullName}"></s:hidden>
	<s:form id="queryForm">
		<input type="hidden" name="wee.bizlog.modulelevel" value="0400205">
		<table width="100%">
			<tr>
				<td width="40%" align="center">
					<s:text name="userAccount.userName"></s:text>
					：
					<input id="input_userName" name="userQC.userName" maxlength="40" class="login_input">
				</td>
				<td width="40%" align="center">
					<s:text name="userAccount.fullName"></s:text>
					：
					<input id="input_fullName" name="userQC.fullName" maxlength="40" class="login_input">
				</td>
				<td align="center" width="20%">
					<n:button id="queryButton" value="%{getText('userAccount.query')}" buttonClass="border-blue" />
					<n:button id="clearButton" value="%{getText('userAccount.clear')}" buttonClass="gray" />
				</td>
			</tr>
		</table>
	</s:form>
	<div class="c_r_tabel">
		<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
			<tr>
				<td class="tabel_td">
					<div class="join_nav">
						<ul>
							<li>
								<a href="#" onclick="gotoAdd()"><s:text name="role.addUsersForRole" /> </a>
							</li>
							<li>
								<a href="#" onclick="deleteUser()"><s:text name="role.deleteUsersForRole" /> </a>
							</li>
						</ul>
					</div>
				</td>
			</tr>
		</table>

		<n:grid dataSource="userList" id="grid" name="grid" width="100" cellSpacing="1" cellPadding="0" cssClass="gridTable"
			action="showRoleAC!init.do" pagination="pagination">
			<!-- 定义列表字段头 -->
			<n:gridheader>
				<n:selectall isSingle="false" width="10" />
				<n:columnmodel key="userAccount.username" width="10" sortable="true" sortColumn="USERNAME"></n:columnmodel>
				<n:columnmodel key="userAccount.fullName" width="20" sortable="true" sortColumn="FULL_NAME"></n:columnmodel>
				<n:columnmodel key="userAccount.tel" width="20" sortable="true" sortColumn="TEL"></n:columnmodel>
				<n:columnmodel key="userAccount.description" width="45"></n:columnmodel>
			</n:gridheader>
			<!-- 定义列表数据项 -->
			<n:gridtbody>
				<n:selectcolumn dataField="account.userId" />
				<n:textcolumn dataField="account.username" maxLength="6" />
				<n:textcolumn dataField="user.fullName" maxLength="6" />
				<n:textcolumn dataField="user.tel" />
				<n:textcolumn dataField="user.description" />
			</n:gridtbody>
			<n:gridfooter>
				<!-- 定义列表分页 -->
				<n:gridpager />
			</n:gridfooter>
		</n:grid>
	</div>
</div>
<br>
<div class="clear"></div>
<s:hidden id="roleId" name="role.roleId" value="%{role.roleId}"></s:hidden>
