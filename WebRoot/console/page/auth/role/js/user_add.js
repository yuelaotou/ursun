var roleId = null;
var unitId = null;
$(function() {
	unitId = $("#unitId").val();
	roleId = $("#roleId").val();
	$("#queryButton").click(function() {
		$('#queryForm').attr(
				'action',
				'showRoleAC!init.do?ftype=add&role.roleId=' + roleId
						+ '&unitId=' + unitId+"&wee.bizlog.modulelevel"+'0400205');
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
function add() {
	var userIds = $("#grid").getSelectData().rowid;
	var droleId = roleId;
	var dunitId = unitId;
	if (userIds.length > 0) {
		$.post(contextPath + "/console/maintainRoleAC!addUsersForRole.do", {
			'userIds' : userIds,
			'role.roleId' : droleId,
			'wee.bizlog.modulelevel' : '0400206'
		}, function() {
			popMessage(sipo.sysmgr.role.roleuseraddsuccess,null,function(){
				window.location.href = contextPath
						+ "/console/showRoleAC!init.do?ftype=query&unitId="
						+ dunitId + "&role.roleId=" + droleId
						+ "&wee.bizlog.modulelevel=0400205";
			},true);
		}, "JSON");
	} else {
		popMessage(sipo.sysmgr.role.pleasechooseoneormore,null,null,true);
	}
}

function resume() {
	window.location.href = contextPath 
			+ "/console/showRoleAC!init.do?ftype=query&role.roleId=" + roleId
			+"&wee.bizlog.modulelevel="+'0400205';
}