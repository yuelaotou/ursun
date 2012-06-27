/*
 * ---------------------进入role_user_list 显示页面之后，将焦点设置在第一个查询输入框中
---------------------------
 */
$(document).ready(function(){
 $("#input_userName").focus();
 })
var roleId = null;
var userName=null;
var fullName=null;
// 获取参数
$(function() {
	userName=$("#userName").val();
	fullName=$("#fullName").val();
	$("#input_userName").val(userName);
	$("#input_fullName").val(fullName);
	roleId = $("#roleId").val();
});
// 查询按钮事件
$(function() {
	$("#queryButton").click(function() {
		$('#queryForm').attr('action',
				contextPath + '/console/showRoleAC!init.do?ftype=query&role.roleId=' + roleId+"&wee.bizlog.modulelevel="+'0400110');
		var input_userName = $("#input_userName").val();
		var input_fullName = $("#input_fullName").val();
		if (input_userName.match(/^[0-9a-zA-Z\u4E00-\u9FA5]*$/) == null) {
			popMessage(sipo.sysmgr.role.usernamecheck,null,null,true);
			return;
		}
		if (input_fullName.match(/^[0-9a-zA-Z\u4E00-\u9FA5]*$/) == null) {
			popMessage(sipo.sysmgr.role.fullnamecheck,null,null,true);
			return;
		}
		$('#queryForm').submit();
	});
	$("#clearButton").click(function() {
		$("#input_userName").val('');
		$("#input_fullName").val('');
	});
});
// 删除用户与角色映射关系
function deleteUser() {
	var dtnode = window.parent.dtnode_all
	var userIds = $("#grid").getSelectData().rowid;
	if (userIds.length > 0) {
		if (confirmMessage(sipo.sysmgr.role.confirmuserdel)) {
			if (roleId) {
				$
						.post(
								contextPath + "/console/maintainRoleAC!deleteUsersForRole.do",
								{
									'userIds' : userIds,
									'role.roleId' : roleId,
									'wee.bizlog.modulelevel' : '0400207'
								},
								function() {
									popMessage(sipo.sysmgr.role.roleuserdelsuccess,null,function(){
										window.location.href = contextPath
											+ "/console/showRoleAC!init.do?ftype=query&role.roleId="
											+ roleId
											+ "&wee.bizlog.modulelevel=0400205";
									},true);
									
								}, "JSON");
			}
		}
	} else {
		popMessage(sipo.sysmgr.role.pleasechooseoneormore,null,null,true);
		return;
	}
}
// 跳转到为本角色添加用户页面
function gotoAdd() {
	var role_id = $("#roleId").val();
	if(role_id==""){
		popMessage(sipo.sysmgr.role.pleasechooseonlyonerole,null,null,true);
	}
	if (role_id) {
		if (role_id!= "root") {
			if (role_id == "sysUserRole") {
				popMessage(sipo.sysmgr.role.anonyrolecannotadduser,null,null,true);
				return;
			} else {
				window.location.href = contextPath
						+ "/console/showRoleAC!init.do?ftype=add&time="
						+ new Date() + "&role.roleId="
						+ role_id+"&wee.bizlog.modulelevel="+'0400206';
			}
		} else {
			popMessage(sipo.sysmgr.role.cannotchooseroot,null,null,true);
			return;
		}
	} else if (roleId) {
		window.location.href = contextPath
				+ "/console/showRoleAC!init.do?ftype=add&time=" + new Date()
				+ "&role.roleId=" + roleId+"&wee.bizlog.modulelevel="+'0400206';
	}
}
