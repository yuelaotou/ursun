/*
 * ---------------------进入增加页面之后，将焦点设置在第一个输入框中 ---------------------------
 */
$(document).ready(function() {
	$("#username").focus();

})
/*
 * ---------------------保存按钮事件 begin---------------------------
 */

function add() {
	if ($("#addForm").validation()) {
		submit_();
	};
}
// form提交前校验
function submit_() {
	$("#submit").attr("disabled", "true");
	$
			.getJSON(
					contextPath + "/console/maintainUserAC!checkAccountByUserName.do?userAccount.account.username="
							+ $.trim($("#username").val())
							+ "&userAccount.account.id=*&wee.bizlog.modulelevel=0400112",
					function(data) {
						if (data.exist) {
							popMessage(sipo.sysmgr.user.usernameexist, null,
									function() {
										$("#submit").removeAttr("disabled");
									}, true);
							return;
						}

						var uaername = $("#username").val();// 用户名校验
						var fullname = $("#fullName").val();// 姓名校验
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
						var roles = $("#roleids").val();
						if (roles == "") {
							if (!confirmMessage(sipo.sysmgr.user.confirmaddnorole)) {
								$("#submit").removeAttr("disabled");
								return;
							}
						}
						var unit = $("#unit").val();
						$
								.ajax( {
									type : "POST",
									url : contextPath + "/console/maintainUserAC!addUser.do?wee.bizlog.modulelevel=0400107",
									data : $("#addForm").formSerialize(),
									dataType : "JSON",
									success : function(data) {
										popMessage(
												sipo.sysmgr.user.useraddsuccess,
												null,
												function() {
													window.location.href = contextPath
															+ "/console/showUnitAC!init.do?ftype=query&unit="
															+ $("#unit_Id")
																	.val()
															+ "&unitId="
															+ $("#unit_Id")
																	.val()
															+ "&wee.bizlog.modulelevel=0400110";
												}, true);
									}
								});
					});
}
/*
 * ---------------------保存按钮事件 end---------------------------
 */

/*
 * --------------------取消按钮事件 begin----------------------------
 */
function goBack() {
	window.location.href = contextPath
			+ "/console/showUnitAC!init.do?ftype=query&unit="
			+ $("#unit_Id").val() + "&unitId=" + $("#unit_Id").val()
	+"&wee.bizlog.modulelevel=0400110";
}
/*
 * --------------------取消按钮事件 end----------------------------
 */

/*
 * ------------------页面加载函数-----------------------------
 */
var roleWindow;
var unitWindow;
$(function() {
	var unit = $("#unit_Id").val();
	$("#title").text(sipo.sysmgr.user.text_useradd);
	$('#rolenames').val("");
	// 初始化角色树
	$("#role_tree").dynatree( {
		selectMode : 2,
		persist : false,
		checkbox : true,
		rootVisible : false,
		minExpandLevel : 3,
		initAjax : {
			root : "root",
			url : contextPath + "/console/showUserAC!queryRoleTree.do",
			data : {
				'wee.bizlog.modulelevel' : '0400201'
			}
		},
		onPostInit : function(dtnode, event) {
		},
		onClick : function(dtnode) {
			if (dtnode.data.key == "root") {
				popMessage(sipo.sysmgr.user.cannotchooseroleroot, null, null,
						true);
				return false;
			}
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
			// 初始显示分类为所选的组织机构
			if ('' != unit && 'root' != unit && 'undefined' != typeof(unit)) {
				$("#unit").val(unit);
				$("#unitName").val($("#unit_tree").dynatree("getTree")
						.getNodeByKey(unit).data.title);
			}
		},
		onClick : function(dtnode) {
			dtnode.select(true);
		}
	});

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
		var myTree = $("#role_tree").dynatree("getTree");
		var strId = makePars(myTree);
		var strName = makeParsName(myTree);
		$('#rolenames').val(strName);
		$('#roleids').val(strId);
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
		var actNode = $("#unit_tree").dynatree("getTree").getActiveNode();
		var selNode = $("#unit_tree").dynatree("getTree").getSelectedNodes();
		if (!actNode && !selNode[0]) {
			popMessage(sipo.sysmgr.user.pleasechooseorg, null, null, true);
			return;
		}
		if (actNode && actNode.data.key == "root") {
			popMessage(sipo.sysmgr.user.cannotchooseorgroot, null, null, true);
			return;
		}
		$("#unitName")
				.val(actNode ? actNode.data.title : selNode[0].data.title);
		$("#unit").val(actNode ? actNode.data.key : selNode[0].data.key);
		$("#unit_Id").val(actNode ? actNode.data.key : selNode[0].data.key);
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
		if ($("#unit_Id").val()) {
			$("#unit_tree").dynatree("getTree").selectKey($("#unit_Id").val(),
					true);
		}
		unitWindow.showWindow();
	});

});

/*
 * ------------- 取消上次选中项-----------------------------
 */
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

/*
 * ---------------组合请求参数------------------------------------
 */
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
