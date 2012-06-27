var unit = $("#unit_Id").val();
$(function() {
	$("#title").text(sipo.sysmgr.user.text_userdetail);
	// 获取用户对应角色并赋值给页面
	$.getJSON(contextPath + "/console/showUserAC!queryRolesByUserId.do?userAccount.user.id="
			+ $("#id").val() + "&wee.bizlog.modulelevel=0400119",
			function(data) {
				var role = data.roles;
				var strName = "";
				enableIds = data.enableRoleIds;
				if (!enableIds || enableIds.length == 0) {
					return;
				}
				var arr = enableIds.split(",");
				if (role != null && role.length > 0) {
					for (var i = 0;i < role.length; i++) {
						for (var j = 0;j < arr.length; j++) {
							if (role[i].id == arr[j]) {
								strName += role[i].roleName + ",";
							}
						}

					}
				}
				$("#rolesName").text(strName.length == 0 ? strName : strName
						.substr(0, strName.length - 1));
			});
	// 获取用户对应组织机构并赋值给页面
	$.getJSON(contextPath + "/console/showUserAC!queryUnitByUserId.do?userAccount.user.id="
			+ $("#id").val() + "&wee.bizlog.modulelevel=0400106",
			function(data) {
				if (data.unitInfo) {
					$("#unitName").text(data.unitInfo.unitName);
				}
			});

	var sexValue = $("#sex").val();
	if (sexValue == "M") {
		$("#userAccount_user_sex").text(sipo.sysmgr.user.text_male);
	} else if (sexValue == "F") {
		$("#userAccount_user_sex").text(sipo.sysmgr.user.text_female);
	} else if (sexValue == "U") {
		$("#userAccount_user_sex").text("");
	}

});
