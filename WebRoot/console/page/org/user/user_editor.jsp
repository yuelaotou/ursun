<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript" src="<n:path path="/console/page/org/user/js/user_edit.js"/>"></script>
<s:form id="editForm">
	<div class="add_user">
		<h3>
			<s:text name="userAccount.accountMsg" />
		</h3>
		<table class="reg_table1" width="100%">
			<s:hidden id="userId" name="userAccount.user.id" />
			<s:hidden id="accountId" name="userAccount.account.id" />
			<tr>
				<td align="right"  width="22%">
					<s:text name="userAccount.userName" />：
				</td>
				<td align="left">
					<s:textfield id="username" name="userAccount.account.username"
						cssClass="validate[required,length[1,40]],userDefined[checkUserName] login_input1 "
						theme="simple">
					</s:textfield>
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td align="right">
					<s:text name="userAccount.password.reset" />：
				</td>
				<td align="left">
					<input type="checkbox" id="resetPwd" onclick="showPwdInput();">
				</td>
			</tr>

			<tr id="pwd" style="display: none">
				<td align="right">
					<s:text name="userAccount.password" />：
				</td>
				<td align="left">
					<s:password id="password" name="userAccount.account.password"
						theme="simple"
						cssClass="validate[required,length[1,40]] login_input1">
					</s:password>
					<font color="red">*</font>
				</td>
			</tr>
			<tr id="pwd2" style="display: none">
				<td align="right">
					<s:text name="userAccount.password2" />：
				</td>
				<td align="left">
					<s:password id="password2" name="password2" theme="simple"
						cssClass="validate[required,confirm[password],length[1,40]] login_input1">
					</s:password>
					<font color="red">*</font>
				</td>
			</tr>
		</table>
	</div>
	<div class="add_user">
		<h3>
			<s:text name="userAccount.baseMsg" />
		</h3>
		<table class="reg_table1" width="100%">
			<tr class="">
				<td align="right"  width="22%">
					<s:text name="userAccount.personName"></s:text>：
				</td>
				<td>
					<s:textfield id="fullName" name="userAccount.user.fullName"
						cssClass="validate[required,length[1,40]],userDefined[checkFullName] login_input1"
						theme="simple">
					</s:textfield>
					<font color="red">*</font>
				</td>
			</tr>
			<tr class="">
				<td align="right">
					<s:text name="userAccount.personSex" />：
				</td>
				<td align="left">
					<s:select id="sex" label="Lsex" name="userAccount.user.sex"
						list="@cn.ursun.console.app.domain.Sex@values()" listKey="value"
						listValue="label" value="userAccount.user.sex" required="true"
						theme="simple" />
				</td>
			</tr>
			<tr class="">
				<td align="right">
					<s:text name="userAccount.personTel" />：
				</td>
				<td align="left">
					<s:textfield id="tel" name="userAccount.user.tel"
						cssClass="validate[custom[telephone],length[0,20]] login_input1 "
						theme="simple">
					</s:textfield>
				</td>
			</tr>
			<tr class="">
				<td align="right">
					<s:text name="userAccount.personEmail" />：
				</td>
				<td align="left">
					<s:textfield id="email" name="userAccount.user.email"
						cssClass="validate[required,custom[email]],length[1,100]] login_input1 "
						theme="simple">
					</s:textfield>
					<font color="red">*</font>
				</td>
			</tr>
			<!-- 迭代输出用户配置的扩展信息输入域 start-->
			<s:iterator value="columnKeyList">
				<tr>
					<s:set name="columnName" />
					<s:iterator value="columnMap">
						<s:if test="key==#columnName">
							<s:set name="column" value="value" />
							<td align="right">
								<s:property value="#column.label" />：
							</td>
							<td align="left">
								<s:if test="#column.type=='text'">
									<s:textfield id="%{#columnName}"
										name="%{'userAccount.user.extendInfo.'+#columnName}"
										theme="simple"
										cssClass="validate[%{#column.require=='true'?'required':''},length[%{#column.minlength},%{#column.maxlength}]] login_input1" />
								</s:if>
								<s:if test="#column.type=='number'">
									<s:textfield id="%{#columnName}"
										name="%{'userAccount.user.extendInfo.'+#columnName}"
										theme="simple"
										cssClass="validate[%{#column.require=='true'?'required':''},custom[onlyNumber],length[%{#column.minlength},%{#column.maxlength}]] login_input1" />
								</s:if>
								<s:if test="#column.type=='date'">
									<s:textfield id="%{#columnName}"
										name="%{'userAccount.user.extendInfo.'+#columnName}"
										theme="simple"
										cssClass="validate[%{#column.require=='true'?'required':''},custom[date],length[%{#column.minlength},%{#column.maxlength}]] login_input1" />
									<script>
										$(function() {
											$('#<s:property value="key"/>').datepicker();
										});
										</script>
								</s:if>
								<s:if test="#column.type=='textarea'">
									<s:textarea id="%{#columnName}"
										name="%{'userAccount.user.extendInfo.'+#columnName}"
										theme="simple" cols="27" rows="3"
										cssClass="validate[%{#column.require=='true'?'required':''},length[%{#column.minlength},%{#column.maxlength}]] textarea_b"></s:textarea>
								</s:if>
								<s:if test="#column.type=='dict'">
									<s:iterator value="dictMap">
										<s:if test="key==#columnName">
											<s:select id="%{#columnName}_%{#column.relateColumn}"
												name="%{'userAccount.user.extendInfo.'+#columnName}"
												theme="simple" list="value" listKey="code"
												listValue="codeName" multiple="%{#column.multiple}"
												onchange="getValueList('%{#column.name}','%{#column.relateColumn}');"
												cssClass="validate[%{#column.require=='true'?'required':''}]" />
										</s:if>
									</s:iterator>
								</s:if>
								<s:if test="#column.type=='radio'">
									<s:iterator value="dictMap">
										<s:if test="key==#columnName">
											<s:radio name="%{'userAccount.user.extendInfo.'+#columnName}"
												theme="simple" list="value" listKey="code"
												listValue="codeName"
												cssClass="validate[%{#column.require=='true'?'required':''}] radio" />
										</s:if>
									</s:iterator>
								</s:if>
								<s:if test="#column.type=='checkbox'">
									<s:iterator value="dictMap">
										<s:if test="key==#columnName">
											<s:checkboxlist
												name="%{'userAccount.user.extendInfo.'+#columnName}"
												theme="simple" list="value" listKey="code"
												listValue="codeName"
												cssClass="validate[%{#column.require=='true'?'minCheckbox[1]':''}] checkbox" />
										</s:if>
									</s:iterator>
								</s:if>
								<s:if test="#column.type=='telephone'">
									<s:textfield id="%{#columnName}"
										name="%{'userAccount.user.extendInfo.'+#columnName}"
										theme="simple"
										cssClass="validate[%{#column.require=='true'?'required':''},custom[telephone],length[%{#column.minlength},%{#column.maxlength}]] login_input1" />
								</s:if>
								<s:if test="#column.type=='icard'">
									<s:textfield id="%{#columnName}"
										name="%{'userAccount.user.extendInfo.'+#columnName}"
										theme="simple"
										cssClass="validate[identityCard,%{#column.require=='true'?'required':''},length[%{#column.minlength},%{#column.maxlength}]] login_input1" />
								</s:if>
								<s:if test="#column.require=='true'">
										&nbsp;<font color="red">*</font>
								</s:if>
							</td>
						</s:if>
					</s:iterator>
				</tr>
			</s:iterator>
			<!-- 迭代输出用户配置的扩展信息输入域 end-->
			<tr class="">
				<td align="right">
					<s:text name="userAccount.personDes" />：
				</td>
				<td align="left">
					<s:textarea id="description" name="userAccount.user.description"
						cssClass="validate[length[0,100]],userDefined[checkOther] textarea_b" theme="simple"
						cols="27" rows="3">
					</s:textarea>
				</td>
			</tr>
		</table>
	</div>
	<div class="add_user">
		<h3>
			<s:text name="userAccount.roleMsg" />
		</h3>
		<table  width="100%">
			<tr class="">
				<td  width="22%" align="right">
					<s:text name="userAccount.roles" />：
				</td>
				<td>
					<s:textfield id="rolesName" readonly="true" theme="simple" cssClass="login_input1"/>
					<s:hidden id="rolesId" />
				</td>
				<td width="25%" >
					<n:button id="chooserole"
						value="%{getText('userAccount.chooserole')}"
						buttonClass="border-blue" />
				</td>
			</tr>
		</table>
	</div>
	<div class="add_user">
		<h3>
			<s:text name="userAccount.orgMsg" />
		</h3>
		<table  width="100%">
			<tr>
				<td  width="22%" align="right">
					<s:text name="userAccount.org" />：
				</td>
				<td>
					<s:textfield id="unitName" name="unitName" readonly="true"
						cssClass="validate[required] login_input1" theme="simple"/>
					<s:hidden id="unitId" />
				</td>
				<td width="25%" >
					<n:button id="chooseunit"
						value="%{getText('userAccount.chooseunit')}"
						buttonClass="border-blue" />
				</td>
			</tr>
		</table>
	</div>
	<table align="center" width="120"><tr><td>
	<div style="padding-top: 10px; text-align: center;">
		<n:button id="submit" value="%{getText('userAccount.submit')}"
			onclick="save();" buttonClass="border-blue" />
		<n:button value="%{getText('userAccount.close')}"
			onclick="refresh();" buttonClass="gray" />
	</div>
	</td></tr></table>
<div class="clear"></div>
</s:form>

<div id="dialog" style="display: none" class="tranlate_two">
	<p class="tranlate_two_left"></p>
	<div class="tranlate_two_conter">
		<div id="roletree"  style="padding-top:20px;"></div>
	</div>
    <p class="tranlate_two_di"></p>
</div>
<div id="unit_dialog" style="display: none" class="tranlate_two">
	<p class="tranlate_two_left"></p>
	<div class="tranlate_two_conter">
		<div id="unit_tree"  style="padding-top:20px;"></div>
	</div>
    <p class="tranlate_two_di"></p>
</div>
