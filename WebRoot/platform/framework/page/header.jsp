<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<n:base />
		<title>系统header</title>
		<script type="text/javascript" src="<n:path path="platform/common/jquery/jquery.jclock.js"/>"></script>
<%--		<script type="text/javascript" src="<n:path path="platform/common/jquery/plugin/jtip/jtip.js"/>"></script>--%>
<%--		<link rel="stylesheet" type="text/css" href='<n:path path="platform/common/jquery/plugin/jtip/css/global.css"/>' />--%>
		<link rel="stylesheet" type="text/css" href='<n:path path="platform/common/css/component.css"/>' />
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {
	font-size: 12px;
	color: #000000;
}
.STYLE5 {font-size: 12}
.STYLE7 {font-size: 12px; color: #FFFFFF;}
.STYLE7 a{font-size: 12px; color: #FFFFFF;}
-->
</style>
		<script type="text/javascript">
			$(function(){
				$('#now').jclock({withDate:true});
			});
			
			function changeImg(img, src) {
                img.src = src;
            }
            //注销
            function logoutSystem(){
                if(confirm("<s:text name='header.login_out_message' />")){
                	parent.location.href = contextPath + "/platform/wee_logout";
                }
            }
            function changeLanguage(url){
             	parent.location.href = url;
            }
            function refresh(){
             	parent.location.reload();
            }
		</script>
	</head>
	<body>
		<s:set name="current_locale" value="#session['WW_TRANS_I18N_LOCALE']==null?locale:#session['WW_TRANS_I18N_LOCALE']"></s:set>
		<s:url id="chinese_url" value="/platform/main!console.do">
			<s:param name="request_locale" value="@java.util.Locale@CHINA"></s:param>
		</s:url>
		<s:url id="english_url" value="/platform/main!console.do">
			<s:param name="request_locale" value="@java.util.Locale@ENGLISH"></s:param>
		</s:url>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="57" background="<n:path path="platform/framework/common/images/main_03.gif" />">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="378" height="57" background="<n:path path="platform/framework/common/images/main_01.gif"/>">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
							<td width="281" valign="bottom">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="33" height="27">
											<img src="<n:path path="platform/framework/common/images/main_05.gif"/>" width="33" height="27" />
										</td>
										<td width="248" background="<n:path path="platform/framework/common/images/main_06.gif"/>">
											<table width="225" border="0" align="center" cellpadding="0" cellspacing="0">
												<tr>
													<td height="17">
														<div align="right">
															<img src="<n:path path="platform/framework/common/images/pass.gif"/>" width="69" height="17" />
														</div>
													</td>
													<td>
														<div align="right">
<%--															<a href="<n:path path="console/showUserAC!queryAccountById.do?userAccount.user.id=" /><s:property value="userAccount.account.userId" />" class="jTip" id="one" name="Password must follow these rules:">--%>
																<img src="<n:path path="platform/framework/common/images/user.gif"/>" width="69" height="17"/>
<%--															</a>--%>
														</div>
													</td>
													<td>
														<div align="right">
															<img src="<n:path path="platform/framework/common/images/quit.gif"/>" width="69" height="17"
																style="cursor: hand;cursor: pointer;" onclick="logoutSystem();" />
														</div>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="40" background="<n:path path="platform/framework/common/images/main_10.gif"/>">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="194" height="40" background="<n:path path="platform/framework/common/images/main_07.gif"/>">
								&nbsp;
							</td>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="21" class="STYLE7">
											<img src="<n:path path="platform/framework/common/images/main_19.gif"/>" width="19" height="14" />
										</td>
										<td width="35" class="STYLE7">
											<div align="center">
												<s:a href="javascript:" onclick="refresh();">
													<s:text name="grid.refreshText"></s:text>
												</s:a>
											</div>
										</td>
										<td width="21" class="STYLE7">
											<img src="<n:path path="platform/framework/common/images/main_21.gif"/>" width="19" height="14" />
										</td>
										<td width="35" class="STYLE7">
											<div align="center">
												<s:text name="header.help"></s:text>
											</div>
										</td>
										<td width="21" class="STYLE7">
											<img src="<n:path path="platform/framework/common/images/main_17.gif"/>" width="19" height="14" />
										</td>
										<s:if test="#current_locale.equals(@java.util.Locale@CHINA)">
											<td width="35" class="STYLE7">
												<div align="center">
													<s:a href="javascript:" onclick="changeLanguage('%{#chinese_url}');"
														title="%{getText('header.chinese.title')}">
														<strong><s:text name="header.chinese" /> </strong>
													</s:a>
												</div>
											</td>
											<td width="5" class="STYLE7">
												<div align="center">
													|
												</div>
											</td>
											<td width="35" class="STYLE7">
												<div align="center">
													<s:a href="javascript:" onclick="changeLanguage('%{#english_url}');"
														title="%{getText('header.english.title')}">
														<s:text name="header.english" />
													</s:a>
												</div>
											</td>
										</s:if>
										<s:else>
											<td width="35" class="STYLE7">
												<div align="center">
													<s:a href="javascript:" onclick="changeLanguage('%{#chinese_url}');"
														title="%{getText('header.chinese.title')}">
														<s:text name="header.chinese" />
													</s:a>
												</div>
											</td>
											<td width="5" class="STYLE7">
												<div align="center">
													|
												</div>
											</td>
											<td width="35" class="STYLE7">
												<div align="center">
													<s:a href="javascript:" onclick="changeLanguage('%{#english_url}');"
														title="%{getText('header.english.title')}">
														<strong><s:text name="header.english" /> </strong>
													</s:a>
												</div>
											</td>
										</s:else>
										<td>
											&nbsp;
										</td>
									</tr>
								</table>
							</td>
							<td width="248" background="<n:path path="platform/framework/common/images/main_11.gif"/>">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="16%">
											<span class="STYLE5"></span>
										</td>
										<td width="75%">
											<div align="center">
												<span class="STYLE7">当前时间：<span id="now"></span> </span>
											</div>
										</td>
										<td width="9%">
											&nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="30" background="<n:path path="platform/framework/common/images/main_31.gif"/>">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="8" height="30">
								<img src="<n:path path="platform/framework/common/images/main_28.gif"/>" width="8" height="30" />
							</td>
							<td width="145" background="<n:path path="platform/framework/common/images/main_29.gif"/>">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="24%">
											&nbsp;
										</td>
										<td width="43%" height="20" valign="bottom" class="STYLE1">
											管理菜单
										</td>
										<td width="33%">
											&nbsp;
										</td>
									</tr>
								</table>
							</td>
							<td width="39">
								<img src="<n:path path="platform/framework/common/images/main_30.gif"/>" width="39" height="30" />
							</td>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td height="20" valign="bottom">
											<span class="STYLE1">当前登录用户：<s:property value="userAccount.account.username" />/<s:property
													value="userAccount.user.fullName" /> </span>
										</td>
										<td valign="bottom" class="STYLE1">
											<div align="right">
												<img src="<n:path path="platform/framework/common/images/sj.gif"/>" width="6" height="7" />
												IP：
												<s:property value="userIP" />
											</div>
										</td>
									</tr>
								</table>
							</td>
							<td width="17">
								<img src="<n:path path="platform/framework/common/images/main_32.gif"/>" width="17" height="30" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
