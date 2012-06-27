<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript" src="<n:path path="/console/page/org/user/js/user_detail.js"/>"></script>
<s:hidden id="pagination_start" name="%{pagination.start}" />
<div class="add_user">
	<h3><s:text name="userAccount.accountMsg" /></h3>
	<table class="reg_table1" width="100%">
		<tr>
			<td align="right" width="30%">
				<s:text name="userAccount.userName" />：
			</td>
			<td align="left" style="word-break:break-all;word-wrap:break-word">
				<s:property id="uname" value="userAccount.account.username" />
			</td>
		</tr>
	</table>
</div>
<div class="add_user">
	<h3>
		<s:text name="userAccount.baseMsg" />
	</h3>
	<table class="reg_table1" width="100%">
		<s:hidden id="id" name="userAccount.user.id" />

		<tr class=" ">
			<td align="right" width="30%">
				<s:text name="userAccount.personName" />
				：
			</td>
			<td align="left" style="word-break:break-all;word-wrap:break-word">
				<s:property id="empno" value="userAccount.user.fullName" />
			</td>
		</tr>
		<tr class=" ">
			<td align="right">
				<s:text name="userAccount.personSex" />
				：
			</td>
			<td align="left">
				<s:hidden id="sex" name="userAccount.user.sex" />
				<span id="userAccount_user_sex"></span>
			</td>
		</tr>
		<tr class=" ">
			<td align="right">
				<s:text name="userAccount.personTel" />
				：
			</td>
			<td align="left" style="word-break:break-all;word-wrap:break-word">
				<s:property id="empno" value="userAccount.user.tel" />
			</td>
		</tr>
		<tr class=" ">
			<td align="right">
				<s:text name="userAccount.personEmail" />
				：
			</td>
			<td align="left" style="word-break:break-all;word-wrap:break-word">
				<s:property id="empno" value="userAccount.user.email" />
			</td>
		</tr>
		<!-- 迭代输出用户的扩展信息 start-->
		<s:iterator value="columnKeyList">
			<s:set name="columnName" />
			<s:iterator value="columnMap">
				<s:if test="key==#columnName">
					<s:set name="column" value="value" />
					<tr>
						<td align="right">
							<s:property value="#column.label" />
							：
						</td>
						<td align="left" style="word-break:break-all;word-wrap:break-word">
							<s:iterator value="userAccount.user.extendInfo">
								<s:if test="key==#columnName">
									<s:property value="value" />
								</s:if>
							</s:iterator>
						</td>
					</tr>
				</s:if>
			</s:iterator>
		</s:iterator>
		<!-- 迭代输出用户的扩展信息 end-->
		<tr class=" ">
			<td align="right">
				<s:text name="userAccount.personDes" />：
			</td>
			<td align="left" style="word-break:break-all;word-wrap:break-word">
				<s:property id="empno" value="userAccount.user.description" />
			</td>
		</tr>
	</table>
</div>
<div class="add_user">
	<h3>
		<s:text name="userAccount.roleMsg" />
	</h3>
	<table class="reg_table1" width="100%">
		<tr class=" ">
			<td align="right" width="30%">
				<s:text name="userAccount.roles" />：
			</td>
			<td align="left">
				<div id="rolesName"></div>
			</td>
		</tr>
	</table>
</div>
<div class="add_user">
	<h3>
		<s:text name="userAccount.orgMsg" />
	</h3>
	<table class="reg_table1" width="100%">
		<tr class=" ">
			<td align="right" width="30%" >
				<s:text name="userAccount.org" />：
			</td>
			<td align="left">
				<div id="unitName"></div>
			</td>
		</tr>
	</table>
</div>
<div class="clear"></div>
