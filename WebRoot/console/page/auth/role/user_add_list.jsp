<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<n:path path="/console/page/auth/role/js/user_add_list.js"/>"></script>
		<div id="con_two_7">
			<div class="column_left align-l" style="margin:5px 0px 5px 5px">
				<h3 class="column_title">
					<s:text name="role.unitStructure"/>
				</h3>
				<div class="shuxing1" id="unit_tree">
				</div>
			</div>
			<div class="column_right" style="width:520px;margin:5px 5px 5px 0px">
				<h3 class="column_title column_title_two" style="width:510px">
					<s:text name="role.userList"/>
				</h3>
				<s:action name="showRoleAC!forward" id="userlist" namespace="/console" executeResult="true">
					<s:param name="ftype" value="'queryo'"></s:param>
				</s:action>
			</div>
			<div class="clear"></div>
		</div>

<s:hidden id="unitId" name="unitId"></s:hidden>	
<s:hidden id="roleId" name="role.roleId" value="%{role.roleId}"></s:hidden>
