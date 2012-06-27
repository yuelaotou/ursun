<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<n:base />
		<n:jquery components="form,calendar" />
		<script type="text/javascript" src="<n:path path="/console/page/bizlog/js/bizlog.js"/>"></script>
		<link rel="stylesheet" type="text/css" href='<n:path path="/platform/common/css/component.css"/>' />
		<title><s:text name="bizlog.maintainbizlog"></s:text>
		</title>
	</head>

	<body>
		<div id="full">
			<!--full start-->
			<div id="container_middle">
				<!--container_middle start-->


				<div class="Fenxi_center">
					<!--Fenxi_center start-->
					<div id="bizlogdetail_dialog" style="display:none" class="tranlate">
						<!--tranlate start-->
						<div class="tranlate_conter" style="border-top:1px solid #CCC;">
							<div class="tranlate_box">
								<table align="center">
									<tr>
										<td style="text-align:right;width:65px">
											<s:text name="bizlog.operatorname"></s:text>
											：
										</td>
										<td id="loginNameDetail"></td>
									</tr>
									<tr>
										<td style="text-align:right;width:65px">
											<s:text name="bizlog.operatorrole"></s:text>
											：
										</td>
										<td id="roleNameDetail">
											<s:property value="bizlogrow.roleName" />
										</td>
									</tr>
									<tr>
										<td style="text-align:right;width:65px">
											<s:text name="bizlog.operatorip"></s:text>
											：
										</td>
										<td id="ipDetail">
											<s:property value="bizlogrow.ip" />
										</td>
									</tr>
									<tr>
										<td style="text-align:right;width:65px">
											<s:text name="bizlog.operatordate"></s:text>
											：
										</td>
										<td id="operationDateDetail">
											<s:date name="bizlogrow.operationDate" format="yyyy-MM-dd HH:mm:ss ms" />
										</td>
									</tr>
									<tr>
										<td style="text-align:right;width:65px">
											<s:text name="bizlog.function"></s:text>
											：
										</td>
										<td id="moduleLevel1Detail">
											<s:property value="bizlogrow.moduleLevel1" />
										</td>
									</tr>
									<tr>
										<td style="text-align:right;width:65px">
											<s:text name="bizlog.description"></s:text>
											：
										</td>
										<td id="detailDetail">
											<s:property value="bizlogrow.detail" escape="false" />
										</td>
									</tr>

								</table>
								<br />
								<br />
								<div style="text-align:center; padding-bottom:14px;">
									<table align="center" width="120">
										<tr>
											<td>
												<n:button id="closeBizLog" buttonClass="gray" value="%{getText('bizlog.closelog')}" />
											</td>
										</tr>
									</table>
								</div>
							</div>

						</div>
						<p class="tranlate_di"></p>
					</div>
					<!--tranlate end-->


					<div id="export_dialog" style="display:none" class="tranlate">
						<!--tranlate start-->
						<div class="tranlate_conter" style="border-top:1px solid #CCC;">
							<s:form id="exportForm" action="" name="exportForm">
								<input type="hidden" name="wee.bizlog.modulelevel" value="0400601" id="modulelevel1">
								<div class="tranlate_box">
									<table width="100%">
										<tr>
											<td style="text-align:right;">
												<s:text name="bizlog.startdate"></s:text>
											</td>
											<td>
												<s:textfield id="start" name="bizlogrow.startDate" value="%{bizlogrow.startDate}" theme="simple"
													cssClass="validate[required,custom[date]] input_a"></s:textfield>
												<font color="red">*</font>
											</td>
										</tr>
										<tr>
											<td style="text-align:right;">
												<s:text name="bizlog.enddate"></s:text>
											</td>
											<td>
												<s:textfield id="end" name="bizlogrow.endDate" value="%{bizlogrow.endDate}" theme="simple"
													cssClass="validate[required,custom[date]],userDefined[checkValue] input_a"></s:textfield>
												<font color="red">*</font>
											</td>
										</tr>

									</table>
									<div style="padding-top:10px; text-align:center;">
										<table align="center" width="140">
											<tr>
												<td>
													<n:button id="submitButton" buttonClass="border-blue" value="%{getText('bizlog.explort')}" />
													<n:button id="windowClose" buttonClass="gray" value="%{getText('bizlog.cancel')}" />
												</td>
											</tr>
										</table>
									</div>
								</div>
							</s:form>
						</div>
						<p class="tranlate_di"></p>
					</div>
					<!--tranlate end-->

					<div class="fenxi_Contentbox">
						<div id="con_two_1"></div>
						<div id="con_two_2" style="display:none"></div>
						<div id="con_two_3" style="display:none"></div>
						<div id="con_two_4" style="display:none"></div>
						<div id="con_two_5" style="display:none"></div>
						<div id="con_two_6" style="display:none"></div>
						<div id="con_two_7" style="display:none"></div>
						<div id="con_two_8" style="display:none"></div>
						<div id="con_two_9" style="display:none"></div>
						<div id="con_two_10">
							<div class="cognate">
								<div class="cognate_top">
									<div class="cognate_one">
										<div class="cognate_name">
											<s:text name="bizlog.maintainbizlog"></s:text>
										</div>
									</div>
								</div>
								<div class="cognate_conter">
									<div class="cognate_box1">
										<div class="cognate_input">
											<s:form id="query" action="">
												<input type="hidden" name="wee.bizlog.modulelevel" value="0400603" id="modulelevel2">
												<p style="padding-top:10px;">
													<s:text name="bizlog.operatorname"></s:text>
													：
												</p>
												<p style="width:110px;">
													<s:textfield id="login_name" name="bizlogrow.loginName" value="%{bizlogrow.loginName}" theme="simple"
														maxlength="40" cssClass="input_three"></s:textfield>
												</p>
												<p style="padding-top:10px;">
													<s:text name="bizlog.operatorrole"></s:text>
													：
												</p>
												<p style="width:110px;">
													<s:textfield id="role_name" name="bizlogrow.roleName" value="%{bizlogrow.roleName}" maxlength="20"
														theme="simple" cssClass="input_three"></s:textfield>
												</p>
												<p style="padding-top:10px;">
													<s:text name="bizlog.operatorip"></s:text>
													：
												</p>
												<p style="width:110px;">
													<s:textfield id="ip" name="bizlogrow.ip" value="%{bizlogrow.ip}" maxlength="15" theme="simple"
														cssClass="input_three"></s:textfield>
												</p>
												<p style="padding-top:10px;">
													<s:text name="bizlog.startdate"></s:text>
													：
												</p>
												<p style="width:90px;">
													<s:textfield id="startdate" name="bizlogrow.startDate" value="%{bizlogrow.startDate}" maxlength="20"
														theme="simple" cssClass="input_four"></s:textfield>
												</p>
												<p style="padding-top:10px;">
													<s:text name="bizlog.enddate"></s:text>
													：
												</p>
												<p style="width:90px;">
													<s:textfield id="enddate" name="bizlogrow.endDate" value="%{bizlogrow.endDate}" maxlength="20"
														theme="simple" cssClass="input_four"></s:textfield>
												</p>
												<div>
													<n:button id="queryBizlog" value="%{getText('bizlog.query')}" buttonClass="border-blue" />
													<n:button id="resetBizlog" value="%{getText('bizlog.reset')}" buttonClass="gray" />
												</div>
												<div class="clear"></div>
											</s:form>
										</div>

										<div class="cognate_talbe2">
											<n:grid dataSource="bizlogList" id="grid" name="grid" cellSpacing="1" cellPadding="2" cssClass="gridTable"
												theme="ajax" action="showBizLogAC!queryBizLogListJson.do" pagination="pagination">
												<n:gridheader>
													<n:selectall isSingle="false" width="8" />
													<n:columnmodel key="bizlog.operatorname" width="23" />
													<n:columnmodel key="bizlog.operatorrole" width="23" />
													<n:columnmodel key="bizlog.operatorip" width="14" />
													<n:columnmodel key="bizlog.operatordate" width="20" />
													<n:columnmodel key="bizlog.operation" width="12" />
												</n:gridheader>
												<!-- 定义列表数据项 -->
												<n:gridtbody>
													<n:selectcolumn dataField="id" />
													<n:textcolumn dataField="loginName" cssClass="grid_wordbreak" />
													<n:textcolumn dataField="roleName" cssClass="grid_wordbreak" />
													<n:textcolumn dataField="ip" />
													<n:textcolumn dataField="operationDate" />
													<n:customcolumn dataField="id" renderTo="showDetailInfo" />
												</n:gridtbody>
												<n:gridfooter>
													<n:button id="deleteLog" value="%{getText('bizlog.deletelog')}" onclick="deleteLog()" buttonClass="gray"></n:button>
													<n:button id="exportlog" value="%{getText('bizlog.explort')}" buttonClass="border-blue"></n:button>
													<n:button id="deleteAllLog" value="%{getText('bizlog.deletealllog')}" buttonClass="gray"></n:button>
													<!-- 定义列表分页 -->
													<n:gridpager />
												</n:gridfooter>

											</n:grid>
										</div>
									</div>
								</div>
								<p class="cognate_di"></p>
							</div>
						</div>
						<div id="con_two_11" style="display:none"></div>
					</div>
				</div>
				<!--Fenxi_center end-->
				<n:footer />

			</div>
		</div>
		<!--full end-->
	</body>
</html>
