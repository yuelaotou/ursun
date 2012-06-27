<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<n:base />
		<n:jquery components="tree,form,calendar" />
		<script type="text/javascript" src="<n:path path="/console/page/org/user/js/unit_user.js"/>"></script>
		<link rel="stylesheet" type="text/css" href='<n:path path="/platform/common/css/component.css"/>' />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><s:text name="user.title" />
		</title>
	</head>
	<body>
		<div id="full">
			<!--full start-->
			<div id="container_middle">
				<!--container_middle start-->
				<s:hidden id="admin" name="admin" />
				<s:hidden id="currUnitId" name="currentUnitId" />
				<div id="add_dialog" style="display: none" class="tranlate">
					<!--tranlate start-->
					<div class="tranlate_conter">
						<div class="tranlate_box">
							<s:hidden id="parentId" name="parentId" value=""></s:hidden>
							<form id="newForm">
								<table width="100%">
									<tr>
										<td style="text-align:right;">
											<s:text name="org.orgName" />
											：
										</td>
										<td>
											<input type="text" name="name" id="add_name"
												class="validate[required,length[1,20]],userDefined[checkName],userDefined[checkNameDuplicateNew] input_a" />
											<font color="red">*</font>
										</td>
									</tr>
									<tr>
										<td style="text-align:right;">
											<s:text name="org.description" />
											：
										</td>
										<td>
											<input type="text" name="description" id="add_description"
												class="validate[length[0,40]],userDefined[checkDescription] input_a" />
										</td>
									</tr>
								</table>
							</form>

							<div style="padding-top:10px; text-align:center;">
								<table align="center" width="120">
									<tr>
										<td>
											<n:button id="newSubmit" value="%{getText('userAccount.submit')}" buttonClass="border-blue" />
											<n:button id="newCancel" value="%{getText('userAccount.close')}" buttonClass="gray" />
										</td>
									</tr>
								</table>
							</div>

						</div>
					</div>
					<p class="tranlate_di"></p>
				</div>
				<!--tranlate end-->

				<div id="modify_dialog" style="display: none" class="tranlate">
					<!--tranlate start-->
					<div class="tranlate_conter">
						<div class="tranlate_box">
							<s:hidden id="unitId" name="unitId" value=""></s:hidden>
							<form id="editForm">
								<table width="100%">
									<tr>
										<td align="right">
											<s:text name="org.orgName" />
											：
										</td>
										<td>
											<input type="text" name="name" id="modify_name"
												class="validate[required,length[0,20]],userDefined[checkName],userDefined[checkNameDuplicateEdit] input_a" />
											<font color="red">*</font>
										</td>
									</tr>
									<tr>
										<td align="right">
											<s:text name="org.description" />
											：
										</td>
										<td>
											<input type="text" name="description" id="modify_description"
												class="validate[length[0,40]],userDefined[checkDescription] input_a" />
										</td>
									</tr>
								</table>
							</form>
							<table align="center" width="120">
								<tr>
									<td>
										<div style="padding-top:10px; text-align:center;">
											<n:button id="editSubmit" value="%{getText('userAccount.submit')}" buttonClass="border-blue" />
											<n:button id="editCancel" value="%{getText('userAccount.close')}" buttonClass="gray" />
										</div>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<p class="tranlate_di"></p>
				</div>
				<!--tranlate end-->

				<ul id="myMenu" class="contextMenu" style="font-size:12px;text-align:left">
					<li class="add">
						<a href="#add"><s:text name="org.add" />
						</a>
					</li>
					<li class="modify">
						<a href="#modify"><s:text name="org.edit" />
						</a>
					</li>
					<li class="delete">
						<a href="#delete"><s:text name="org.del" />
						</a>
					</li>
					<li class="move">
						<a href="#move"><s:text name="org.move" />
						</a>
					</li>
				</ul>

				<div class="Fenxi_center">
					<!--Fenxi_center start-->
					<div class="fenxi_Contentbox_sys">
						<div id="con_two_7">
							<div class="column_left align-l">
								<h3 class="column_title">
									<s:text name="org.orgMsg" />
								</h3>
								<div class="shuxing1" id="tree">
								</div>
							</div>
							<div class="column_right" style="padding-bottom:10px;">
								<h3 class="column_title column_title_two">
									<div id="title"></div>
								</h3>
								<s:action name="showUserAC!forward" id="userlist" namespace="/console" executeResult="true" />
							</div>
							<div class="clear"></div>
						</div>
					</div>
					<!--Fenxi_center end-->
				</div>
				<div class="Fenxi_di"></div>

				<div id="treeDialog" style="display: none" class="tranlate_two">
					<!--tranlate start-->
					<p class="tranlate_two_left"></p>
					<div class="tranlate_two_conter">
						<div id="unitTree" style="padding-top:20px;"></div>
					</div>
					<p class="tranlate_two_di"></p>
					<s:hidden id="newParentId" />
				</div>
				<!--tranlate end-->
				<div class="clear"></div>

				<s:hidden id="unit_Id" name="unitId"></s:hidden>
			</div>
			<!--container_middle end-->
			<n:footer />
		</div>
	</body>
</html>
