<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
		<script type="text/javascript" src="<n:path path="/console/page/org/user/js/user_list.js"/>"></script>
		<s:hidden id="userQC.unit" name="userQC.unit" />
		<s:hidden id="pagination_start" name="pagination.start" value="%{pagination.start}" />
		<div class="column_content">
			<s:form id="queryForm" action="">
			<table>
				<tr>
					<td width="15%" align="right">
						<s:text name="userAccount.userName"/>：
					</td>
					<td width="15%">
						<s:textfield id="userName" name="userQC.userName"  maxlength="40" theme="simple" cssClass="login_input"></s:textfield>
					</td>
					<td width="12%" align="right">
						<s:text name="userAccount.fullName"/>：
					</td>
					<td width="15%">	
						<s:textfield id="fullName" name="userQC.fullName" maxlength="40" theme="simple" cssClass="login_input"></s:textfield>
					</td>
					<td width="18%" align="right">
						<s:text name="userAccount.status"/>：
					</td>
					<td width="25%" align="left">
						<s:radio name="op" list="#{'0':' '+getText('userAccount.disable'),'1':' '+getText('userAccount.enable')}" theme="simple"></s:radio>
					</td>
				</tr>
				<tr>
					<td align="right">
						<s:text name="userAccount.regDate"/><s:text name="userAccount.dateFrom"/>：
					</td>
					<td>
						<s:textfield id="begin" name="userQC.registeDateBegin"  maxlength="20" theme="simple" cssClass="login_input"/>
						<script>
						$(function() {
							$('#begin').datepicker({dateFormat: 'yy-mm-dd'});
						});
						</script>
					</td>
					<td align="right">
						<s:text name="userAccount.dateTo"/>：
					</td>
					<td>
						<s:textfield id="end" name="userQC.registeDateEnd" maxlength="20" theme="simple" cssClass="login_input"/>
						<script>
						$(function() {
							$('#end').datepicker({dateFormat: 'yy-mm-dd'});
						});
						</script>
					</td>
					<!-- 迭代输出用户配置的查询条件 start-->
					<s:iterator value="queryColumnList" status="status">
						<s:set name="column"/>
							<s:if test="(#status.index+2)%3==0">
								<tr>
							</s:if>
								<td align="right">
									<s:property value="%{#column.label}"/>：
								</td>
								<td align="left">
									<s:if test="#column.type=='dict'">
											<s:iterator value="dictMap">
												<s:if test="key==#column.name">
													<s:select id="%{#column.name}_%{#column.relateColumn}"
														name="%{'userQC.extendCondition.'+#column.name}"
														theme="simple" list="value" listKey="code"
														listValue="codeName"
														onchange="getValueList('%{#column.name}','%{#column.relateColumn}');"/>
												</s:if>
											</s:iterator>
									</s:if>
									<s:elseif test="#column.type=='radio'">
											<s:iterator value="dictMap">
												<s:if test="key==#column.name">
													<s:radio
														name="%{'userQC.extendCondition.'+#column.name}"
														theme="simple" list="value" listKey="code"
														listValue="codeName"/>
												</s:if>
											</s:iterator>
									</s:elseif>
									<s:elseif test="#column.type=='checkbox'">
											<s:iterator value="dictMap">
												<s:if test="key==#column.name">
													<s:checkboxlist
														name="%{'userQC.extendCondition.'+#column.name}"
														theme="simple" list="value" listKey="code"
														listValue="codeName"/>
												</s:if>
											</s:iterator>
									</s:elseif>
									<s:else>
											<s:textfield id="%{#column.name}"
												name="%{'userQC.extendCondition.'+#column.name}"
												maxlength="%{#column.maxlength}"
												theme="simple" cssClass="login_input"/>
									</s:else>
								</td>
							<s:if test="(#status.index+2)%3==2">
								</tr>
							</s:if>
					</s:iterator>
					<!-- 迭代输出用户配置的查询条件 end-->
			</table>
			
				<div style="padding-top:10px;height:30px">
				<table align="right" width="120"><tr><td>
					<n:button id="queryButton" value="%{getText('userAccount.query')}" buttonClass="border-blue"/>
					<n:button id="clearButton" value="%{getText('userAccount.clear')}" buttonClass="gray"/>
                	</td></tr></table>
                </div>
			
		</s:form>
		<div class="c_r_tabel">
        	<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
                <tr>
                    <td class="tabel_td">
                        <div class="join_nav">
                            <ul>
                                <li id="addUser"><a href="javascript:"><s:text name="userAccount.add"/></a></li>
                                <li id="eidtUser"><a href="javascript:"><s:text name="userAccount.edit"/></a></li>
                                <li id="deleteUser"><a href="javascript:"><s:text name="userAccount.delete"/></a></li>
                                <li id="enableUser"><a href="javascript:"><s:text name="userAccount.enable"/></a></li>
                                <li id="disableUser"><a href="javascript:"><s:text name="userAccount.disable"/></a></li>
                                <li id="changeRole"><a href="javascript:"><s:text name="userAccount.role.change"/></a></li>
                                <li id="resetPwd"><a href="javascript:"><s:text name="userAccount.password.reset"/></a></li>
                            </ul>
                        </div>
                    </td>                                 
                </tr>
            </table>
        
		<n:grid dataSource="userList"  id="grid" name="grid" width="100" cellSpacing="1" cellPadding="0"
			cssClass="gridTable" action="showUnitAC!init.do" pagination="pagination">
			<!-- 定义列表字段头 -->
			<n:gridheader>
				<n:selectall isSingle="false" width="7" />
				<n:columnmodel key="userAccount.username" width="10" sortable="true" sortColumn="username"/>
				<n:columnmodel key="userAccount.fullName" width="10" sortable="true" sortColumn="FULL_NAME"/>
				<n:columnmodel key="userAccount.sex" width="5" sortable="true" sortColumn="SEX"/>
				<n:columnmodel key="userAccount.tel" width="10" sortable="true" sortColumn="TEL"/>
				<n:columnmodel key="userAccount.regDate" width="10" sortable="true" sortColumn="REGISTE_DATE"/>
				<n:columnmodel key="userAccount.status" width="10" sortable="true" sortColumn="ENABLED"/>
				<n:columnmodel key="userAccount.detail" width="5"/>
			</n:gridheader>
			<!-- 定义列表数据项 -->
			<n:gridtbody>
				<n:selectcolumn dataField="user.id" />
				<n:textcolumn dataField="account.username" maxLength="6"/>
				<n:textcolumn dataField="user.fullName" maxLength="6"/>
				<n:customcolumn dataField="user.sex"  renderTo="showSex" />
				<n:textcolumn dataField="user.tel" maxLength="12"/>
				<n:datecolumn dataField="account.registerDate" />
				<n:customcolumn dataField="account.enabled"  renderTo="showEnabled" />
				<n:imagecolumn imageSrc='<%= request.getContextPath()+"/platform/common/images/auth/xiugai.gif"%>'
					linkUrl="javascript:showInfo('{user.id}')" imageBorder="0" imageWidth="16" imageHeight="16" />
			</n:gridtbody>
			<n:gridfooter>
				<!-- 定义列表分页 -->
				<n:gridpager />
			</n:gridfooter>
		</n:grid>
		</div>
		</div>
		<div class="clear"></div>
		
		<div id="dialog"  style="display: none" class="tranlate_two"><!--tranlate start-->
			<p class="tranlate_two_left"></p>
        	<div class="tranlate_two_conter">
            	<div id="roletree" style="padding-top:20px;"></div>
            </div>
        	<p class="tranlate_two_di"></p>	
    	</div><!--tranlate end-->
		<div class="clear"></div>
		
		<div id="detaildialog"  style="display: none;"><!--tranlate start-->
			<p class="tranlate_two_left"></p>
			<table align="center" width="80%"><tr><td>
        	<div style="padding-top:20px;padding-bottom:20px">
        		<div id="results"/>
        	</div>
        	</td></tr></table>
    	</div>
		<div class="clear"></div>
		
		<div id="pwd_dialog"  style="display: none"  class="tranlate"><!--tranlate start-->
			<div class="tranlate_conter" style="border-top:1px solid #CCC;">
            <div class="tranlate_box">
           	<s:form id="resetForm">
			<br>
			<table width="100%">
				<tr>
					<td align="right"><s:text name="userAccount.password"/>：</td>
					<td><s:password id="p1" name="p1"
							cssClass="validate[required,length[0,40]] input_a"
							theme="simple"></s:password>&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td align="right"><s:text name="userAccount.password2"/>：</td>
					<td><s:password id="p2" name="p2"
							cssClass="validate[required,confirm[p1],length[1,40]] input_a"
							theme="simple">
						</s:password>&nbsp;<font color="red">*</font>
					</td>
				</tr>
			</table>
			</s:form>
			<table align="center" width="120"><tr><td>
			<div style="padding-top:10px; text-align:center;">
				<n:button id="submit" value="%{getText('userAccount.submit')}" buttonClass="border-blue"/>
				<n:button id="cancel" value="%{getText('userAccount.close')}" buttonClass="gray"/>
            </div>
            </td></tr></table>
            </div>
            </div>
       	 	<p class="tranlate_di"></p>
    	</div><!--tranlate end-->
