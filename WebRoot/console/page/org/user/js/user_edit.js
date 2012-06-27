/*
 * ---------------------进入增加页面之后，将焦点设置在第一个输入框中 ---------------------------
 */
$(document).ready(function() {
	$("#username").focus();

})
var enableIds;
function save() {
	if ($("#editForm").validation()) {
		submit_();
	};
}
// ////////////////////////////////////////////////////////////////////
// form提交前校验,成功执行success方法
function submit_() {
	$("#submit").attr("disabled", "true");
	// ///modifyed by 王敏 2010-01-30/////
	var sex = $("#sex").val();
	$('#userSex').val(sex);
	// /////////////////////////////////
	$
			.getJSON(
					contextPath + "/console/maintainUserAC!checkAccountByUserName.do?userAccount.account.username="
							+ $.trim($("#username").val())
							+ "&userAccount.account.id="
							+ $("#accountId").val()
							+ "&wee.bizlog.modulelevel=0400112",
					function(data) {
						if (data.exist) {
							popMessage(sipo.sysmgr.user.usernameexist, null,
									function() {
										$("#submit").removeAttr("disabled");
									}, true);
							return;
						}
						var uaername = $("#username").val();
						var fullname = $("#fullName").val();
						var comKey = $("#comKey").val();// 单位公司名称
						var addressKey = $("#addressKey").val();// 详细地址
						var description = $("#description").val();// 备注

						/*if (uaername.match(/^[0-9a-zA-Z_]+$/) == null) {
							popMessage(
									sipo.sysmgr.user.usernamespecialcharacter,
									null, function() {
										$("#username").focus();
										$("#submit").removeAttr("disabled");
									}, true);
							return;
						}
						if (fullname
								.match(/^[a-zA-Z\u4e00-\u9fa5]+[a-zA-Z\u4e00-\u9fa5·]*[a-zA-Z\u4e00-\u9fa5]*$/) == null) {
							popMessage(sipo.sysmgr.user.fullnamecheck, null,
									function() {
										$("#fullName").focus();
										$("#submit").removeAttr("disabled");
									}, true);
							return;
						}*/
						if (comKey.match(/^[0-9a-zA-Z\u4e00-\u9fa5]*$/) == null) {
							popMessage(
									sipo.sysmgr.user.companynamespecialcharacter,
									null, function() {
										$("#comKey").focus();
										$("#submit").removeAttr("disabled");
									}, true);
							return;
						}
						if (addressKey.match(/^[0-9a-zA-Z\u4e00-\u9fa5]*$/) == null) {
							popMessage(sipo.sysmgr.user.addrspecialcharacter,
									null, function() {
										$("#addressKey").focus();
										$("#submit").removeAttr("disabled");
									}, true);
							return;
						}
						/*if (description.match(/^[0-9a-zA-Z\u4e00-\u9fa5]*$/) == null) {
							popMessage(sipo.sysmgr.user.remarkspecialcharacter,
									null, function() {
										$("#description").focus();
										$("#submit").removeAttr("disabled");
									}, true);
							return;
						}*/
						var unit = $("#unitId").val();
						var rolesId = $("#rolesId").val();

						$
								.ajax( {
									type : "POST",
									url : contextPath + "/console/maintainUserAC!updateUser.do?roleids="
											+ rolesId
											+ "&unit.id="
											+ unit
											+ "&enableRoleIds="
											+ enableIds
											+ "&wee.bizlog.modulelevel=0400108",
									data : $("#editForm").formSerialize(),
									dataType : "JSON",
									success : function(data) {
										popMessage(
												sipo.sysmgr.user.usermodifysuccess,
												null,
												function() {
													window.location.href = contextPath
															+ "/console/showUnitAC!init.do?ftype=query&unit="
															+ $("#unitId")
																	.val()
															+ "&unitId="
															+ $("#unitId")
																	.val()
															+ "&wee.bizlog.modulelevel=0400110";
												}, true);
									}
								});
					});

}
// //////////////////////////////////////////////////////////////////////
function refresh() {
	window.location.href = contextPath
			+ "/console/showUnitAC!init.do?ftype=query&unit="
			+ $("#unit_Id").val() + "&unitId=" + $("#unit_Id").val()
	+"&wee.bizlog.modulelevel=0400110";
}
var roleWindow;
var unitWindow;
$(function() {
	showPwdInput();
	$("#title").text(sipo.sysmgr.user.text_useredit);
	var sexValue = $("#userSex").val();
	if (sexValue == "M") {
		$("#sex").val("MALE");
	} else if (sexValue == "F") {
		$("#sex").val("FEMALE");
	} else if (sexValue == "U") {
		$("#sex").val("UNKNOWN");
	}

	// 初始化角色树
	$("#roletree").dynatree( {
		persist : true,
		rootVisible : false,
		minExpandLevel : 3,
		persist : false,
		checkbox : true,
		selectMode : 2,
		initAjax : {
			root : "root",
			url : contextPath + "/console/showUserAC!queryRoleTree.do",
			data : {
				'wee.bizlog.modulelevel' : '0400201'
			}
		},
		onClick : function(dtnode) {
			if (dtnode.data.key == "root") {
				popMessage(sipo.sysmgr.user.cannotchooseroleroot, null, null,
						true);
				return false;
			}
		},
		onPostInit : function(dtnode, event) {
			// 获取用户对应角色并赋值给页面
			$.getJSON(contextPath + "/console/showUserAC!queryRolesByUserId.do?userAccount.user.id="
					+ $("#userId").val() + "&wee.bizlog.modulelevel=0400119",
					function(data) {
						var strId = "";
						var strName = "";
						var role = data.roles;
						enableIds = data.enableRoleIds;
						if (!enableIds || enableIds.length == 0) {
							return;
						}
						var arr = enableIds.split(",");
						if (role != null && role.length > 0) {
							for (var i = 0;i < role.length; i++) {
								for (var j = 0;j < arr.length; j++) {
									if (role[i].id == arr[j]) {
										strId += role[i].id + ",";
										strName += role[i].roleName + ",";
										$("#roletree").dynatree("getTree")
												.selectKey(role[i].id, true);
									}
								}

							}
						}
						$("#rolesId").val(strId.length == 0 ? strId : strId
								.substr(0, strId.length - 1));
						$("#rolesName").val(strName.length == 0
								? strName
								: strName.substr(0, strName.length - 1));
					});
		}
	});
	// 初始化组织结构树
	$("#unit_tree").dynatree( {
		selectMode : 1,
		persist : false,
		rootVisible : false,
		minExpandLevel : 2,
		initAjax : {
			root : "rootUnit",
			url : contextPath + "/console/showUnitAC!queryUnitTree.do",
			data : {
				'wee.bizlog.modulelevel' : '0400101'
			}
		},
		onPostInit : function(dtnode, event) {
			// 获取用户对应组织机构并赋值给页面
			$.getJSON(contextPath + "/console/showUserAC!queryUnitByUserId.do?userAccount.user.id="
					+ $("#userId").val() + "&wee.bizlog.modulelevel=0400106",
					function(data) {
						if (data.unitInfo) {
							$("#unitId").val(data.unitInfo.id);
							$("#unitName").val(data.unitInfo.unitName);
							$("#unit_tree").dynatree("getTree").selectKey(
									data.unitInfo.id, true);
						}
					});
		},
		onClick : function(dtnode) {
		}
	});

	// ----------------------------------------------------------------------------------------------
	roleWindow = $("#dialog")
			.createWindow( {
				title : sipo.sysmgr.user.text_chooserole,
				width : 260,
				height : 350,
				modal : true,
				buttons : [{
					html : '<table align="center" width="120"><tr><td>'
							+ '<div id="rSubmit" class="button_pale color_Black"><strong></strong><div><a href="#">&nbsp;'
							+ sipo.sysmgr.submit
							+ '&nbsp;</a></div><span></span></div>'
							+ '<div  id="rCancel" class="button_white color_Gray"><strong></strong><div><a href="#">&nbsp;'
							+ sipo.sysmgr.cancel
							+ '&nbsp;</a></div><span></span></div>'
							+ '</td></tr></table>'
				}]
			});
	$("#rSubmit").click(function() {
		var myTree = $("#roletree").dynatree("getTree");
		var str = makePars(myTree);
		var strName = makeParsName(myTree);
		$('#rolesName').val(strName);
		$('#rolesId').val(str);
		roleWindow.closeWindow();
	});
	$("#rCancel").click(function() {
		roleWindow.closeWindow();
	});

	unitWindow = $("#unit_dialog")
			.createWindow( {
				title : sipo.sysmgr.user.text_chooseunit,
				width : 260,
				height : 350,
				modal : true,
				buttons : [{
					html : '<table align="center" width="120"><tr><td>'
							+ '<div id="uSubmit" class="button_pale color_Black"><strong></strong><div><a href="#">&nbsp;'
							+ sipo.sysmgr.submit
							+ '&nbsp;</a></div><span></span></div>'
							+ '<div  id="uCancel" class="button_white color_Gray"><strong></strong><div><a href="#">&nbsp;'
							+ sipo.sysmgr.cancel
							+ '&nbsp;</a></div><span></span></div>'
							+ '</td></tr></table>'
				}]
			});
	$("#uSubmit").click(function() {
		var node = $("#unit_tree").dynatree("getTree").getActiveNode();
		if (!node) {
			popMessage(sipo.sysmgr.user.pleasechooseorg, null, null, true);
			return;
		}
		if (node.data.key == "root") {
			popMessage(sipo.sysmgr.user.cannotchooseorgroot, null, null, true);
			return;
		}
		$("#unitName").val(node.data.title);
		$("#unitId").val(node.data.key);
		$("#unit_tree").dynatree("getTree").selectKey(node.data.key);
		unitWindow.closeWindow();
	});
	$("#uCancel").click(function() {
		unitWindow.closeWindow();
	});
	// 选择角色按钮事件
	$("#chooserole").click(function() {
		roleWindow.showWindow();
	});
	// 选择分类按钮事件
	$("#chooseunit").click(function() {
		unitWindow.showWindow();
	});

	// ------------------------------------------------------------------------------------------------

});

// 取消上次选中项
function unSelectMyTree(obj) {
	var arr = obj.getSelectedNodes(false);
	if (arr != null) {
		if (arr.length > 0) {
			for (var j = 0;j < arr.length; j++) {
				obj.selectKey(arr[j].data.key, false);
			}

		}
	}
}

// 组合请求参数
function makePars(obj) {
	var str = "";
	var arr = obj.getSelectedNodes(false);
	if (arr == null) {
		return str;
	} else {
		if (arr.length > 0) {
			for (var i = 0;i < arr.length; i++) {
				str = str + arr[i].data.key;
				if (i != arr.length - 1)
					str = str + ",";
			}
			return str;
		}

		return str;
	}
}

function makeParsName(obj) {
	var strName = "";
	var arr = obj.getSelectedNodes(false);
	if (arr == null) {
		return strName;
	} else {
		if (arr.length > 0) {
			for (var i = 0;i < arr.length; i++) {
				strName = strName + arr[i].data.title;
				if (i != arr.length - 1)
					strName = strName + ",";
			}
			return strName;
		}

		return strName;
	}
}
function showPwdInput() {
	if ($("#resetPwd").attr("checked")) {
		$("#pwd").attr("style", "display:block");
		$("#pwd2").attr("style", "display:block");
		$("#password").attr("class",
				"validate[required,length[1,40]] login_input1");
		$("#password2")
				.attr("class",
						"validate[required,confirm[password],length[1,40]] login_input1");
	} else {
		$("#pwd").attr("style", "display:none");
		$("#pwd2").attr("style", "display:none");
		$("#password").removeAttr("class");
		$("#password2").removeAttr("class");
		$("#password").val("");
		$("#password2").val("");
		$(this).clearValidationPrompt();
	}
}
/*
 * -------------获取下拉列表的值------------------
 */
function getValueList(thisId, relateID) {
	var relateValue = $("#" + thisId + "_" + relateID).val();
	$.each($("select[id$='" + thisId + "']"), function(i, n) {
		var id = this.id;
		if (!relateValue) {
			$("#" + id).empty();
			$("#" + id).append("<option value=''>"
					+ sipo.sysmgr.user.text_select + "</option>");
			$("#" + id).change();
			return;
		}
		$.getJSON(contextPath + "/console/maintainUserAC!queryCodeList.do?userExtendColumn.name="
				+ thisId + "&userExtendColumn.filterValue=" + relateValue
				+ "&wee.bizlog.modulelevel=0400118", function(data) {
			var list = data.codeList;
			$("#" + id).empty();
			for (var j = 0;j < list.length; j++) {
				$("#" + id).append("<option value=" + list[j].code + ">"
						+ list[j].codeName + "</option>");
			}
		});
	});

}
