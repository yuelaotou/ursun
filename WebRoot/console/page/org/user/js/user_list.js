/*
 * ---------------------进入user_list.jsp 页面，将焦点设置在查询条件第一个输入框中
 * ---------------------------
 */
var pwdWindow;
var roleWindow;
var detailWindow;
$(document).ready(function() {
	$("#userName").focus();
})
/*
 * function refresh() { window.location.href = contextPath +
 * "/console/showUserAC!queryUserListOfUnit.do"; }
 */
var enableIds;
function showInfo(rowid) {
	$.ajax({
		  url: contextPath + "/console/showUserAC!queryAccountById.do?userAccount.user.id=" + rowid
			+ "&wee.bizlog.modulelevel=0400111",
		  cache: false,
		  async: false,
		  dataType:"html",
		  success: function(html){
		    $("#results").html(html);
		  }
		});
	detailWindow.showWindow();
}

$(function() {
	$("#title").text(sipo.sysmgr.user.text_userlist);
	var unit = $("#unit_Id").val();
	// 添加按钮事件
	$("#addUser").click(function() {
		window.location.href = contextPath
				+ "/console/showUnitAC!init.do?ftype=add&unitId=" + unit
				+ "&unit=" + unit+"&wee.bizlog.modulelevel=0400107";
	});
	// 修改按钮事件
	$("#eidtUser").click(function() {
		var id = $("#grid").getSelectData().rowid;
		if (id.length != 1) {
			popMessage(sipo.sysmgr.user.pleasechooseonlyone, null, null, true);
			return false;
		}
		window.location.href = contextPath
				+ "/console/showUnitAC!init.do?ftype=edit&userAccount.user.id="
				+ id + "&wee.bizlog.modulelevel=0400108" + "&unitId=" + unit
				+ "&unit=" + unit;
	});
	// 删除按钮事件
	$("#deleteUser")
			.click(function() {
				var id = $("#grid").getSelectData().rowid;
				if (id.length < 1) {
					popMessage(sipo.sysmgr.user.pleasechooseoneormore, null,
							null, true);
					return false;
				}
				if (!confirm(sipo.sysmgr.user.confirmdeluser)) {
					return false;
				}
				$
						.ajax( {
							type : "POST",
							url : contextPath + "/console/maintainUserAC!deleteUserById.do?userAccount.user.id="
									+ id + "&wee.bizlog.modulelevel=0400109",
							dataType : "JSON",
							success : function(data) {
								popMessage(
										sipo.sysmgr.user.userdelsuccess,
										null,
										function() {
											window.location.href = contextPath
													+ "/console/showUnitAC!init.do?ftype=query&unit="
													+ unit + "&unitId=" + unit
											+"&wee.bizlog.modulelevel=0400110";
										}, true);
							}
						});
			});
	// 查询按钮事件
	$("#queryButton")
			.click(function() {
				var userName = $('#userName').val();
				if (userName.match(/^[0-9a-zA-Z_]*$/) == null) {
					popMessage(sipo.sysmgr.user.usernamespecialcharacter, null,
							null, true);
					$('#userName').focus();
					return;
				}
				var fullName = $('#fullName').val();
				if (fullName.match(/^[a-zA-Z\u4e00-\u9fa5·]*$/) == null) {
					popMessage(sipo.sysmgr.user.fullnamespecialcharacter, null,
							null, true);
					$('#fullName').focus();
					return;
				}
				var begindate = $('#begin').val();
				var enddate = $('#end').val();
				var regxDate = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/
				if (begindate && !regxDate.test(begindate)) {
					popMessage(sipo.sysmgr.user.startdateerror, null, null,
							true);
					return;
				}
				if (enddate && !regxDate.test(enddate)) {
					popMessage(sipo.sysmgr.user.enddateerror, null, null, true);
					return;
				}
				if (begindate && enddate) {
					if (begindate > enddate) {
						popMessage(sipo.sysmgr.user.comparedate, null, null,
								true);
						return;
					}
				}
				var comKey = $('#comKey').val();
				if (comKey.match(/^[0-9a-zA-Z\u4e00-\u9fa5]*$/) == null) {
					popMessage(sipo.sysmgr.user.companynamespecialcharacter,
							null, null, true);
					$('#comKey').focus();
					return;
				}
				$('#queryForm').attr(
						'action',
						contextPath + '/console/showUnitAC!init.do?ftype=queryc&unitId=' + unit
								+ '&unit=' + unit
								+ "&wee.bizlog.modulelevel=0400110");
				$('#queryForm').submit();
			});

	// 重置按钮事件
	$("#clearButton").click(function() {
		$("#queryForm input[type=text]").val('');
		$("#queryForm select").val('');
		$("#queryForm select").change();
		$("#queryForm input[type=radio]").attr("checked", '');
	});

	// 启用按钮事件
	$("#enableUser")
			.click(function() {
				var id = $("#grid").getSelectData().rowid;
				if (id.length < 1) {
					popMessage(sipo.sysmgr.user.pleasechooseoneormore, null,
							null, true);
					return false;
				}
				if (!confirm(sipo.sysmgr.user.confirmuserenable)) {
					return false;
				}
				$
						.ajax( {
							type : "POST",
							url : contextPath + "/console/maintainUserAC!enableUser.do?userAccount.user.id="
									+ id + "&wee.bizlog.modulelevel=0400115",
							dataType : "JSON",
							success : function(data) {
								popMessage(
										sipo.sysmgr.user.userenablesuccess,
										null,
										function() {
											window.location.href = contextPath
													+ "/console/showUnitAC!init.do?ftype=query&unit="
													+ unit + "&unitId=" + unit
											+"&wee.bizlog.modulelevel=0400110";
										}, true);
							}
						});
			});

	// 禁用按钮事件
	$("#disableUser")
			.click(function() {
				var id = $("#grid").getSelectData().rowid;
				if (id.length < 1) {
					popMessage(sipo.sysmgr.user.pleasechooseoneormore, null,
							null, true);
					return false;
				}
				if (!confirm(sipo.sysmgr.user.confirmuserdisable)) {
					return false;
				}
				$
						.ajax( {
							type : "POST",
							url : contextPath + "/console/maintainUserAC!disableUser.do?userAccount.user.id="
									+ id + "&wee.bizlog.modulelevel=0400114",
							dataType : "JSON",
							success : function(data) {
								popMessage(
										sipo.sysmgr.user.userdisablesuccess,
										null,
										function() {
											window.location.href = contextPath
													+ "/console/showUnitAC!init.do?ftype=query&unit="
													+ unit + "&unitId=" + unit
											+"&wee.bizlog.modulelevel=0400110";
										}, true);
							}
						});
			});

	pwdWindow = $("#pwd_dialog").createWindow( {
		title : sipo.sysmgr.user.text_resetpwd,
		height : 180,
		modal : true
	});

	$("#submit").click(function() {
		if ($("#resetForm").validation()) {
			var id = $("#grid").getSelectData().rowid;
			var pwd = $("#p1").val();
			$.post(contextPath + "/console/maintainUserAC!resetPassword.do", {
				'userAccount.user.id' : id,
				'userAccount.account.password' : pwd,
				'wee.bizlog.modulelevel' : '0400117'
			}, function(data) {
				popMessage(sipo.sysmgr.user.pwdresetsuccess, null, function() {
					window.location.href = contextPath
							+ "/console/showUnitAC!init.do?ftype=query&unit="
							+ unit + "&unitId=" + unit
					+"&wee.bizlog.modulelevel=0400110";
				}, true);
			}, 'json');
			pwdWindow.closeWindow();
		}
	});
	$("#cancel").click(function() {
		$("#pwd_dialog").clearValidationPrompt();
		pwdWindow.closeWindow();
	});
	// 密码重置按钮事件
	$("#resetPwd").click(function() {
		var id = $("#grid").getSelectData().rowid;
		if (id.length != 1) {
			popMessage(sipo.sysmgr.user.pleasechooseonlyone, null, null, true);
			return false;
		}
		$("#p1").val("");
		$("#p2").val("");
		pwdWindow.showWindow();
		$("#p1").focus();
	});

	roleWindow = $("#dialog")
			.createWindow( {
				title : sipo.sysmgr.user.text_changerole,
				width : 270,
				height : 350,
				modal : true,
				buttons : [{
					html : '<table align="center" width="120"><tr><td>'
							+ '<div id="rcSubmit" class="button_pale color_Black"><strong></strong><div><a href="#">&nbsp;'
							+ sipo.sysmgr.submit
							+ '&nbsp;</a></div><span></span></div>'
							+ '<div  id="rcCancel" class="button_white color_Gray"><strong></strong><div><a href="#">&nbsp;'
							+ sipo.sysmgr.cancel
							+ '&nbsp;</a></div><span></span></div>'
							+ '</td></tr></table>'
				}]
			});
	$("#rcSubmit").click(function() {
		var id = $("#grid").getSelectData().rowid;
		var myTree = $("#roletree").dynatree("getTree");
		var str = makePars(myTree);
		var strName = makeParsName(myTree);
		$.post(contextPath + "/console/maintainUserAC!updateRoleUser.do", {
			'ids' : id,
			'roleids' : str,
			'enableRoleIds' : enableIds,
			'wee.bizlog.modulelevel' : '0400116'
		}, function(data) {
			popMessage(sipo.sysmgr.user.rolechangesuccess, null, function() {
				window.location.href = contextPath
						+ "/console/showUnitAC!init.do?ftype=query&unit=" + unit
						+ "&unitId=" + unit
				+"&wee.bizlog.modulelevel=0400110";
			}, true);
		}, 'json');
		roleWindow.closeWindow();
	});
	$("#rcCancel").click(function() {
		roleWindow.closeWindow();
	});
	// 修改角色按钮事件
	$("#changeRole").click(function() {
		var id = $("#grid").getSelectData().rowid;
		if (id.length != 1) {
			popMessage(sipo.sysmgr.user.pleasechooseonlyone, null, null, true);
			return false;
		}
		$("#roletree").dynatree("getTree").reload();
		roleWindow.showWindow();
	});
	// 初始化角色树
	$("#roletree").dynatree( {
		idPrefix : "ui-dynatree-id-role-",
		persist : false,
		rootVisible : false,
		minExpandLevel : 3,
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
			// 突出显示已经被关联的角色
			$.getJSON(contextPath + "/console/showUserAC!queryRolesByUserId.do?userAccount.user.id="
					+ $("#grid").getSelectData().rowid
					+ "&wee.bizlog.modulelevel=0400119", function(data) {
				var role = data.roles;
				enableIds = data.enableRoleIds;
				if (role != null && role.length > 0) {
					for (var i = 0;i < role.length; i++) {
						$("#roletree").dynatree("getTree").selectKey(
								role[i].id, true);
					}
				}
			});
		}
	});

	detailWindow = $("#detaildialog")
			.createWindow( {
				title : sipo.sysmgr.user.text_userdetail,
				width : 550,
				height : 450,
				modal : true,
				buttons : [{
					html : '<table align="center" width="60"><tr><td>'
							+ '<div id="detailclose" class="button_pale color_Black"><strong></strong><div><a href="#">&nbsp;'
							+ sipo.sysmgr.close
							+ '&nbsp;</a></div><span></span></div>'
							+ '</td></tr></table>'
				}]
			});
	$("#detailclose").click(function() {
		detailWindow.closeWindow();
	});
});

// 组合请求参数
function makePars(obj) {
	var str = "";
	var arr = obj.getSelectedNodes(false);
	if (arr == null) {
		return str;
	}
	if (arr.length > 0) {
		for (var i = 0;i < arr.length; i++) {
			str = str + arr[i].data.key;
			if (i != arr.length - 1)
				str = str + ",";
		}
	}
	return str;
}

function makeParsName(obj) {
	var strName = "";
	var arr = obj.getSelectedNodes(false);
	if (arr == null) {
		return strName;
	}
	if (arr.length > 0) {
		for (var i = 0;i < arr.length; i++) {
			strName = strName + arr[i].data.title;
			if (i != arr.length - 1) {
				strName = strName + ",";
			}
		}
	}
	return strName;
}
/*
 * $(function() { $('#treespan').text(""); });
 */

function showSex(index, value, rowData) {
	if (value == "M") {
		return sipo.sysmgr.user.text_male;
	}
	if (value == "F") {
		return sipo.sysmgr.user.text_female;
	}
	if (value == "U") {
		return "";
	}
}

function showEnabled(index, value, rowData) {
	if (value == "true") {
		return sipo.sysmgr.user.text_enable;
	}
	if (value == "false") {
		return sipo.sysmgr.user.text_disable;
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
// 获得复选框中勾选的记录id
function getCheckData(checkboxName) {
	var id = new Array();
	for (var i = 0, j = 0;i < $("[name='" + checkboxName + "']").length; i++) {
		if ($("#" + checkboxName + i).attr("checked")) {
			id[j] = $("#" + checkboxName + i).val();
			j++;
		}
	}
	return id;
}
function arrayToString(array) {
	var str = "";
	for (var i = 0;i < array.length; i++) {
		str += array[i] + ',';
	}
	if (str) {
		str = str.substring(0, str.length - 1);
	}
	return str;
}
function checkAll() {
	if ($("#all").attr("checked")) {
		$("[name=uids]").attr("checked", "true");
	} else {
		$("[name=uids]").removeAttr("checked");
	}

}