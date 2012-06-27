<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	//String s = request.getSession().getId();
	//System.out.println(s);
%>
<html>
	<head>
		<n:base />
		<title>SUN平台登录页面</title>
		<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.Font9Blue {
	font-family: "宋体";
	font-size: 9pt;
	color: 6081B5;
}

.ALink {
	font-family: "宋体", "Arial Narrow", "Times New Roman";
	font-size: 10pt;
	font-weight: bold;
}

.ALink:link {
	text-decoration: none;
	font-size: 10pt;
	font-weight: bold;
	color: #4573A6
}

.ALink:alink {
	text-decoration: none;
	font-size: 10pt;
	font-weight: bold;
	color: #4573A6
}

.ALink:visited {
	text-decoration: none;
	font-size: 10pt;
	font-weight: bold;
	color: #4573A6
}

.ALink:active {
	text-decoration: none;
	font-size: 10pt;
	font-weight: bold;
	color: #4573A6
}

.ALink:hover {
	text-decoration: underline;
	font-size: 10pt;
	l font-weight: bold;
	color: #4573A6
}

.input {
	padding: 1px 4px 1px 2px;
	font-size: 9pt;
	font-weight: bold;
	color: #333;
	text-decoration: none;
	height: 20px;
	width: 97%;
	border: 1px solid #426CAB;
	background: #ffffff;
}
</style>
		<script type="text/javascript" src="<n:path path="/platform/common/js/weeEncoder.js"/>"></script>
		<script type="text/javascript">
			function init() {
				if('0' == '<s:property value="type"/>') {
					alert("用户名或密码有误，请重新输入！");
				}
				document.getElementById("j_username").focus();
				var username = getCookie('wee_username');
				var password = getCookie('wee_password');
				if( username != null && username !== "" &&  password != null &&  password !== ""){
					document.getElementById("j_username").value  = wee.encoder.decode(username);
					document.getElementById("j_password").value  = wee.encoder.decode(password);
					document.getElementById('_acegi_security_remember_me').checked = true;
				}
			}
			
			function checking() {
				var username = document.getElementById("j_username");
				var pwd = document.getElementById("j_password");
				if(jsTrim(username.value) == "") {
					alert("用户名不能为空，请重新输入");
					username.focus();
					return;
				} else if (jsTrim(pwd.value) == "") {
					alert("密码不能为空，请重新输入");
					pwd.focus();
					return;
				}
				if(document.getElementById('_acegi_security_remember_me').checked){
					addCookie('wee_username',wee.encoder.encode(jsTrim(username.value)),24*15);
					addCookie('wee_password',wee.encoder.encode(jsTrim(pwd.value)),24*15);
				}else{
					delCookie("wee_username");
					delCookie("wee_password");
				}
				var loginForm = document.getElementById("loginForm");
				loginForm.submit();
			}
			function reset(){
				$("#loginForm input[type=text]").val('');
				$("#loginForm input[type=password]").val('');
				$("#loginForm input[type=checkbox]").attr("checked", '');
				$("#j_username").focus();
<%--				$("#loginForm select").val('');--%>
<%--				$("#loginForm select").change();--%>
<%--				$("#loginForm input[type=radio]").attr("checked", '');--%>
			}
			function pwdkeydown() {
				if(event.keyCode == 13)
					checking();
			}
			function resizeWindow(){
				var dh=document.body.clientHeight;
				var dw=document.body.clientWidth;
				var h=1050;
				var w=1680;
				var x=(dw-w)/2;
				var y=(dh-h)/2;
				document.getElementById("loginTable").style.marginTop=y;
				document.getElementById("loginTable").style.marginLeft =x;
			}
			window.onresize = function() {
				resizeWindow();
			};
		</script>
	</head>
	<body onLoad="MM_preloadImages('<n:path path="/platform/common/images/login/login_butB.gif"/>');init();">
		<form id="loginForm" action="<n:path path="/platform/wee_security_check"/>" method="post">

			<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td bgcolor="#1075b1">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td height="608" background="<n:path path="/platform/framework/common/images/login_03.gif"/>">
						<table width="847" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td height="318" background="<n:path path="/platform/framework/common/images/login_04.gif"/>">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td height="84">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="381" height="84" background="<n:path path="/platform/framework/common/images/login_06.gif"/>">
												&nbsp;
											</td>
											<td width="162" valign="middle" background="<n:path path="/platform/framework/common/images/login_07.gif"/>">
												<table width="100%" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td width="34" height="28" valign="center">
															<div align="right">
																<span class="STYLE3">用户</span>
															</div>
														</td>
														<td width="10" valign="center">
															&nbsp;
														</td>
														<td width="124" height="28" colspan="2" valign="center">
															<div align="left">
																<input type="text" name="j_username" id="j_username" size="25" tabindex="1" onkeydown="enterToTab();"
																	style="width:110px; height:17px; background-color:#87adbf; border:solid 1px #153966; font-size:12px; color:#283439; ">
															</div>
														</td>
													</tr>
													<tr>
														<td width="34" height="28" valign="center">
															<div align="right">
																<span class="STYLE3">密码</span>
															</div>
														</td>
														<td width="10" valign="center">
															&nbsp;
														</td>
														<td height="28" colspan="2" valign="center">
															<input type="password" name="j_password" id="j_password" size="25" tabindex="2" onkeydown="pwdkeydown();"
																style="width:110px; height:17px; background-color:#87adbf; border:solid 1px #153966; font-size:12px; color:#283439; ">
														</td>
													</tr>
													<tr>
														<td height="24" colspan="2" align="right" valign="center">
															<input type="checkbox" id="_acegi_security_remember_me" name="_acegi_security_remember_me">
														</td>
														<td height="24" valign="center">
															<div align="left">
																<span class="STYLE3">记住密码</span>
															</div>
														</td>
													</tr>
													<tr></tr>
												</table>
											</td>
											<td width="26">
												<img src="<n:path path="/platform/framework/common/images/login_08.gif"/>" width="26" height="84">
											</td>
											<td width="67" background="<n:path path="/platform/framework/common/images/login_09.gif"/>">
												<table width="100%" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td height="35">
															<div align="center" style="cursor:hand;cursor:pointer;">
																<img src="<n:path path="/platform/framework/common/images/dl.gif"/>" onclick="checking();return false;"
																	width="57" height="20">
															</div>
														</td>
													</tr>
													<tr>
														<td height="35">
															<div align="center" style="cursor:hand;cursor:pointer;">
																<img src="<n:path path="/platform/framework/common/images/cz.gif"/>" onclick="reset();return false;"
																	width="57" height="20">
															</div>
														</td>
													</tr>
												</table>
											</td>
											<td width="211" background="<n:path path="/platform/framework/common/images/login_10.gif"/>">
												&nbsp;
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td height="206" background="<n:path path="/platform/framework/common/images/login_11.gif"/>">
									&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td bgcolor="#152753">
						&nbsp;
					</td>
				</tr>
			</table>
			<script type="text/javascript">
				//resizeWindow();
			</script>
		</form>
	</body>
</html>
