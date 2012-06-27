<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title><s:text name="role.title" />
		</title>
		<n:base />
		<n:jquery components="tree,form" />
		<script type="text/javascript"
			src="<n:path path="/console/page/auth/role/js/role.js"/>"></script>
		<link rel="stylesheet" type="text/css"
			href='<n:path path="/platform/common/css/component.css"/>' />
	</head>
	<body>
		<div id="full">
			<div id="container_middle">
				<ul id="myMenu" class="contextMenu"
					style="font-size:12px;text-align:left">
					<li class="add">
						<a href="#add"><s:text name="role.createNewRole" />
						</a>
					</li>
					<li class="modify">
						<a href="#modify"><s:text name="role.modifyRole" />
						</a>
					</li>
					<li class="delete">
						<a href="#delete"><s:text name="role.deleteRole" />
						</a>
					</li>
					<li class="move">
						<a href="#move"><s:text name="role.moveRole" />
						</a>
					</li>
				</ul>

				<div id="add_dialog" title="<s:text name="role.createNewRole"/>"
					style="display:none" class="tranlate">
					<div class="tranlate_conter">
						<div class="tranlate_box" style="padding:0px 10px;">
							<div style="border-bottom:1px solid #CCC; padding-bottom:10px;">
								<table width="100%" class="tranlate_box_b">
									<tr>
										<td colspan="2">
											<strong><s:text name="role.parentRoleInfo" />
											</strong>
										</td>
									</tr>
									<tr>
										<td align="right">
											<s:text name="role.parentName" />
											：
										</td>
										<td align="left">
											<input type="text" name="name" id="add_parent_name"
												disabled="disabled" class="input_a" />
										</td>
									</tr>
									<tr>
										<td align="right">
											<s:text name="role.parentDescription" />
											：
										</td>
										<td align="left">
											<textarea name="name" id="add_parent_description"
												disabled="disabled" class="textarea_a" /></textarea>
										</td>
									</tr>
								</table>
							</div>
							<div style="padding-top:10px;">
								<form id="addForm">
									<table width="100%" class="tranlate_box_b">
										<tr>
											<td colspan="2">
												<strong><s:text name="role.roleInfo" />
												</strong>
											</td>
										</tr>
										<tr>
											<td align="right">
												<s:text name="role.roleName" />
												：
											</td>
											<td align="left">
												<input type="text" name="name" id="add_name"
													class="validate[required,length[1,20]],userDefined[checkValue] input_a" />
												<font color="red">*</font>
											</td>
										</tr>
										<tr>
											<td align="right">
												<s:text name="role.roleDescription" />
												：
											</td>
											<td align="left">
												<textarea name="description" id="add_description"
													class="validate[length[0,40]],userDefined[checkValue] textarea_a" /></textarea>
											</td>
										</tr>
									</table>
								</form>
							</div>
							<table align="center" width="120">
								<tr>
									<td>
										<div style="padding-top:10px; text-align:right;">
											<n:button id="addSubmit"
												value="%{getText('userAccount.submit')}"
												buttonClass="border-blue" />
											<n:button id="addCancel"
												value="%{getText('userAccount.close')}" buttonClass="gray" />
										</div>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<div id="modify_dialog" title="<s:text name="role.modifyRole"/>"
					style="display:none" class="tranlate">
					<div class="tranlate_conter">
						<div class="tranlate_box" style="padding:0px 10px;">
							<div style="padding-bottom:10px;">
								<form id="modifyForm">
									<table width="100%" class="tranlate_box_b">
										<tr>
											<td colspan="2">
												<strong><s:text name="role.roleInfo" />
												</strong>
											</td>
										</tr>
										<tr>
											<td align="right">
												<s:text name="role.roleName" />
												：
											</td>
											<td>
												<input type="text" name="name" id="modify_name"
													class="validate[required,length[1,20]],userDefined[checkValue] input_a"
													size="35" />
												<font color="red">*</font>
											</td>
										</tr>
										<tr>
											<td align="right">
												<s:text name="role.roleDescription" />
												：
											</td>
											<td>
												<textarea name="description" id="modify_description"
													class="validate[length[0,40]],userDefined[checkValue] textarea_a" /></textarea>
											</td>
										</tr>
									</table>
								</form>
							</div>
							<table align="center" width="120">
								<tr>
									<td>
										<div style="padding-top:10px; text-align:right;">
											<n:button id="editSubmit"
												value="%{getText('userAccount.submit')}"
												buttonClass="border-blue" />
											<n:button id="editCancel"
												value="%{getText('userAccount.close')}" buttonClass="gray" />
										</div>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>

				<div class="Fenxi_center">
					<!--Fenxi_center start-->
					<div class="fenxi_Contentbox_sys">
						<div id="con_two_7">
							<div class="column_left align-l">
								<h3 class="column_title">
									<s:text name="role.roleStructure" />
								</h3>
								<div class="shuxing1" id="role_tree">
								</div>
							</div>
							<div class="column_right">
								<h3 class="column_title column_title_two">
									<s:text name="role.userListOfRole" />
								</h3>
								<s:action name="showRoleAC!forward" id="rolelist"
									namespace="/console" executeResult="true" />
							</div>
							<div class="clear"></div>
						</div>
					</div>
					<!--Fenxi_center end-->
				</div>

				<div id="treeDialog" style="display: none" class="tranlate_two">
					<!--tranlate start-->
					<p class="tranlate_two_left"></p>
					<div class="tranlate_two_conter">
						<div id="roletree" style="padding-top:20px;"></div>
					</div>
					<p class="tranlate_two_di"></p>
				</div>
				<!--tranlate end-->
				<s:hidden id="newParentId" />
				<div class="clear"></div>

				<s:hidden id="roleId" value="%{role.roleId}"></s:hidden>
				<s:hidden id="roleIds" value="%{roleIds}"></s:hidden>
			</div>
			<!--container_middle end-->
			<n:footer />
		</div>
	</body>
</html>
