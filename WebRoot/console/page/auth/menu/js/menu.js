
// 新建提交校验,成功后执行success函数
function addsubmit_() {
	var parentId = $("#parentId").val();
	var folder = $('#type').val() == 1 ? true : false;
	var menuName = $.trim($('#newName').val());
	var url = $.trim($('#newUrl').val());
	var description = $.trim($('#newDescription').val());
	var sequence = $.trim($('#newSequence').val());
	/*
	 * if (menuName.match(/^[0-9a-zA-Z\u4e00-\u9fa5]*$/) == null) {
	 * popMessage(sipo.sysmgr.menu.namespecialcharacter, null, function() {
	 * $('#newName').focus(); }, true); return; } if
	 * (description.match(/^[0-9a-zA-Z\u4e00-\u9fa5]*$/) == null) {
	 * popMessage(sipo.sysmgr.menu.descriptionspecialcharacter, null, function() {
	 * $('#newDescription').focus(); }, true); return; }
	 */
	$.post(contextPath+"/console/maintainMenuMgrAC!addMenu.do", {
		'wee.bizlog.modulelevel' : '0400402',
		'menu.parentId' : parentId,
		'menu.name' : menuName,
		'menu.url' : url,
		'menu.description' : description,
		'menu.folder' : folder,
		'menu.sequence' : sequence
	}, function(data) {
		if (data.flag == '1') {
			popMessage(sipo.sysmgr.menu.addsuccess, null, function() {
				$("#menu_id").val(data.newMenuId);
				$("#tree").dynatree("getTree").reload();
				showMenuInfo(data.newMenuId);
			}, true);
		}
	}, 'json');
}
// 修改提交校验,成功后执行success函数
function editsubmit_() {
	var menuId = $('#menuId').val();
	var menuName = $.trim($('#editName').val());
	var url = $.trim($('#editUrl').val());
	var description = $.trim($('#editDescription').val());
	var sequence = $.trim($('#editSequence').val());
	/*
	 * if (menuName.match(/^[0-9a-zA-Z\u4e00-\u9fa5]*$/) == null) {
	 * popMessage(sipo.sysmgr.menu.namespecialcharacter, null, function() {
	 * $('#editName').focus(); }, true); return; } if
	 * (description.match(/^[0-9a-zA-Z\u4e00-\u9fa5]*$/) == null) {
	 * popMessage(sipo.sysmgr.menu.descriptionspecialcharacter, null, function() {
	 * $('#editDescription').focus(); }, true); return; }
	 */
	$.post(contextPath+"/console/maintainMenuMgrAC!updateMenu.do", {
		'wee.bizlog.modulelevel' : '0400403',
		'menu.id' : menuId,
		'menu.name' : menuName,
		'menu.url' : url,
		'menu.description' : description,
		'menu.sequence' : sequence
	}, function(data) {
		if (data.flag == '1') {
			popMessage(sipo.sysmgr.menu.modifysuccess, null, function() {
				$("#tree").dynatree("getTree").reload();
				showMenuInfo(menuId);
			}, true);
		}
	}, 'json');
}
/*
 * --------------------页面加载执行函数------------------------
 */
var newWindow;
var editWindow;
var resWindow;
$(function() {
	// 初始化菜单树
	$("#tree").dynatree( {
		rootVisible : false,
		minExpandLevel : 2,
		persist : false,
		idPrefix : "ui-dynatree-id-menu-",
		initAjax : {
			root : "rootMenu",
			url : contextPath + "/console/showMenuMgrAC!queryMenuTree.do",
			data : {
				"wee.bizlog.modulelevel" : "0400401"
			}
		},
		onPostInit : function(isReloading, isError) {
			bindContextMenu("#tree");
			if ($("#menu_id").val()) {
				var node = this.getNodeByKey($("#menu_id").val());
				node.activate();
				node.expand();
			}
		},
		onClick : function(dtnode) {
			var menuId = dtnode.data.key;
			if (menuId) {
				showMenuInfo(menuId);
			}
		}
	});

	// 树禁止右键
	$("#tree").mousedown(function(e) {
		if (e.button == 2) {
			document.oncontextmenu = stop;
		} else {
			document.oncontextmenu = start;
		}

	});

	// 页面加载默认显示根节点信息
	showMenuInfo('root');
	// 初始化资源树
	$("#resource").dynatree( {
		rootVisible : false,
		minExpandLevel : 5,
		persist : false,
		selectMode : 1,
		idPrefix : "ui-dynatree-id-resource-",
		initAjax : {
			root : "rootRes",
			url : contextPath + "/console/showMenuMgrAC!queryResourceTree.do",
			data : {
				'wee.bizlog.modulelevel' : '0400408'
			}
		},
		onClick : function(dtnode, event) {
			if (dtnode.getEventTargetType(event) == "expander") {
				dtnode.toggleExpand();
				return false;
			}
			if (dtnode.data.isFolder) {
				popMessage(sipo.sysmgr.menu.onlyleafresource, null, function() {
					$("#resource").dynatree("getTree").reload();
					resWindow.showWindow();
				}, true);
			} else {
				$('#resId').val(dtnode.data.key);
			}
		},
		onPostInit : function() {
		}
	});
	newWindow = $('#new_dialog').createWindow( {
		title : sipo.sysmgr.menu.add,
		height : 460,
		modal : true,
		close : function() {
			$('#new_dialog').clearValidationPrompt();
		}
	});
	$("#newSubmit").click(function() {
		if ($("#newForm").validation()) {
			addsubmit_();
			newWindow.closeWindow();
		};
	});
	$("#newCancel").click(function() {
		newWindow.closeWindow();
	});

	editWindow = $('#edit_dialog').createWindow( {
		title : sipo.sysmgr.menu.edit,
		height : 300,
		modal : true,
		close : function() {
			$('#edit_dialog').clearValidationPrompt();
		}
	});
	$("#editSubmit").click(function() {
		if ($("#editForm").validation()) {
			editsubmit_();
			editWindow.closeWindow();
		};
	});
	$("#editCancel").click(function() {
		editWindow.closeWindow();
	});

	resWindow = $('#resource_dialog')
			.createWindow( {
				title : sipo.sysmgr.menu.resource,
				width : 300,
				height : 400,
				modal : true,
				buttons : [{
					html : '<table align="center" width="120"><tr><td>'
							+ '<div id="bindSubmit" class="button_pale color_Black"><strong></strong><div><a href="#">&nbsp;'
							+ sipo.sysmgr.submit
							+ '&nbsp;</a></div><span></span></div>'
							+ '<div  id="bindCancel" class="button_white color_Gray"><strong></strong><div><a href="#">&nbsp;'
							+ sipo.sysmgr.cancel
							+ '&nbsp;</a></div><span></span></div>'
							+ '</td></tr></table>'
				}]
			});
	$("#bindSubmit")
			.click(function() {
				var resId = $('#resId').val();
				if (resId == "") {
					popMessage(sipo.sysmgr.menu.pleasechooseresource, null,
							null, true);
					return;
				}
				$.post(contextPath+"/console/maintainMenuMgrAC!bindMenuResourceMapping.do", {
					'menuId' : menuId,
					'resourceId' : resId,
					'wee.bizlog.modulelevel' : '0400405'
				}, function(data) {
					if (data.flag == '1') {
						popMessage(sipo.sysmgr.menu.bindsuccess, null,
								function() {
									$("#menu_id").val(data.menuId);
									$("#tree").dynatree("getTree").reload();
									showMenuInfo(data.menuId);
									$('#resId').val('');
								}, true);
					}
				}, 'json');
				resWindow.closeWindow();
			});
	$("#bindCancel").click(function() {
		resWindow.closeWindow();
	});
});

function stop() {
	return false;
}
function start() {
	return true;
}
/*
 * --------------------显示菜单信息----------------------------
 */
function showMenuInfo(menuId) {
	$
			.getJSON(
					contextPath+"/console/maintainMenuMgrAC!queryMenuById.do?menuId=" + menuId
							+ "&wee.bizlog.modulelevel=0400407",
					function(data) {
						var info = "<ul class='fujian'><li><p class='fujian_tu'> "
								+ sipo.sysmgr.menu.menuname
								+ "：</p><div class='fujian_zi'>"
								+ data.menu.name
								+ "</div><div class='clear'></div></li><li><p class='fujian_tu'> "
								+ sipo.sysmgr.menu.menuurl
								+ "：</p><div class='fujian_zi'>"
								+ data.menu.url
								+ "</div><div class='clear'></div></li><li><p class='fujian_tu'> "
								+ sipo.sysmgr.menu.menudescription
								+ "：</p><div class='fujian_zi'>"
								+ data.menu.description
								+ "</div><div class='clear'></div></li><li><p class='fujian_tu'> "
								+ sipo.sysmgr.menu.menuresource
								+ "：</p><div class='fujian_zi'>"
								+ data.menu.resourceName
								+ "</div><div class='clear'></div></li></ul>";
						$("#info").empty();
						$("#info").append(info);
					});
}
/*
 * -------------------菜单树绑定右键菜单-----------------------
 */
function bindContextMenu(objId) {
	$(objId + "  .ui-dynatree-document," + objId + " .ui-dynatree-folder")
			.destroyContextMenu().contextMenu( {
				menu : "treeMenu"
			}, function(action, el, pos) {
				var dtnode = el.attr("dtnode");
				// 新建窗口属性

					switch (action) {
						case "new" :// 新建
						showNew(dtnode);
						break;
					case "edit" :// 修改
						showEdit(dtnode);
						break;
					case "delete" :// 删除
						deleteMenu(dtnode.data.key);
						break;
					case "bind" :// 绑定资源
						showBindResource(dtnode);
						break;
					case "unbind" :// 撤销资源
						unbindResource(dtnode.data.key);
						break;
					default :
						alert("Todo: appply action '" + action + "' to node "
								+ dtnode);
				}
			});

};

/*
 * ---------------------新建菜单项----------------------
 */
function showNew(dtnode) {
	if (!dtnode.data.isFolder) {
		popMessage(sipo.sysmgr.menu.cannotnewnode, null, null, true);
		return;
	}
	$("#folder").click();

	$("#parentId").attr("value", dtnode.data.key);
	$("#parentName").attr("value", dtnode.data.title);
	$("#parentUrl").attr("value", dtnode.data.url);
	$("#parentDescription").attr("value", dtnode.data.tooltip);
	$("#newName").attr("value", "");
	$("#newUrl").attr("value", "");
	$("#newSequence").attr("value", "0");
	$("#newDescription").attr("value", "");
	newWindow.showWindow();
	$("#newName").focus();
}
/*
 * ----------------------修改菜单项----------------------
 */
function showEdit(dtnode) {
	var menuId = dtnode.data.key;
	var folder = dtnode.data.isFolder;
	// 判断如果为目录节点则隐藏对应导航输入域,不校验导航非空，否则显示并校验导航非空
	if (folder) {
		$("#editurl").attr("style", "display:none");
		$("#editUrl").removeAttr("class");
	} else {
		$("#editurl").removeAttr("style");
		$("#editUrl").attr("class", "validate[required,length[1,80]] input_a");
	}
	editWindow.showWindow();
	$("#editName").focus();
	// 查询需要修改的菜单项信息,并赋值
	$.getJSON(contextPath+"/console/maintainMenuMgrAC!queryMenuById.do?menuId=" + menuId
			+ "&wee.bizlog.modulelevel=0400407", function(data) {
		$("#menuId").val(data.menu.id);
		$("#editName").val(data.menu.name);
		$("#editUrl").val(data.menu.url);
		$("#editDescription").val(data.menu.description);
		$("#editSequence").val(data.menu.sequence);
	});
}
/*
 * -----------------------删除菜单项-----------------------
 */
function deleteMenu(menuId) {
	if (menuId == "root") {
		popMessage(sipo.sysmgr.menu.cannotdeleteroot, null, null, true);
		return;
	}
	if (confirmMessage(sipo.sysmgr.menu.confirmdelete)) {
		$.post(contextPath+"/console/maintainMenuMgrAC!deleteMenuById.do", {
			'menuId' : menuId,
			'wee.bizlog.modulelevel' : '0400404'
		}, function(data) {
			if (data.flag == '1') {
				$("#menu_id").val('root');
				popMessage(sipo.sysmgr.menu.deletesuccess, null, function() {
					$("#tree").dynatree("getTree").reload();
					showMenuInfo('root');
				}, true);
			}
		}, 'json');
	}

}
/*
 * ----------------------绑定资源--------------------------
 */
var menuId;
function showBindResource(dtnode) {
	menuId = dtnode.data.key;
	if (dtnode.data.isFolder) {
		popMessage(sipo.sysmgr.menu.cannotbindresource, null, null, true);
		return;
	}
	resWindow.showWindow();
	// 突出显示已经被绑定的资源
	$.getJSON(contextPath+"/console/maintainMenuMgrAC!queryMenuById.do?menuId=" + menuId
			+ "&wee.bizlog.modulelevel=0400407", function(data) {
		if (data.menu.resourceId != null) {
			$("#resource_dialog #resource").dynatree("getTree").selectKey(
					data.menu.resourceId, true);
			$('#resId').val(data.menu.resourceId);
		}
	});
}
/*
 * --------------------撤销资源绑定------------------------
 */
function unbindResource(menuId) {
	$.getJSON(contextPath+"/console/maintainMenuMgrAC!queryMenuById.do?menuId=" + menuId
			+ "&wee.bizlog.modulelevel=0400407", function(data) {
		if (data.menu.resourceId == null || data.menu.resourceId == "") {
			popMessage(sipo.sysmgr.menu.notuseunbind, null, null, true);
			return;
		}
		if (confirmMessage(sipo.sysmgr.menu.confirmunbind)) {
			$.post(contextPath+"/console/maintainMenuMgrAC!unbindMenuResourceMapping.do", {
				'menuId' : menuId,
				'wee.bizlog.modulelevel' : '0400406'
			}, function(data) {
				if (data.flag == '1') {
					popMessage(sipo.sysmgr.menu.unbindsuccess, null,
							function() {
								$("#menu_id").val(menuId);
								$("#tree").dynatree("getTree").reload();
								showMenuInfo(menuId);
							}, true);
				}
			}, 'json');
		}
	});
}
/*
 * ---------------------radio点击事件-----------------------
 * 如果为目录节点则隐藏对应导航输入域,提交不校验导航非空，否则显示并校验导航非空
 */
function hideUrl(value) {
	if (value == 1) {
		$("#newurl").attr("style", "display:none");
		$("#newUrl").removeAttr("class");
		$("#type").val(1);
	} else {
		$("#newurl").removeAttr("style");
		$("#newUrl").attr("class", "validate[required,length[1,300]] input_a");
		$("#type").val(0);
	}
}

function checkValue(val) {
	var result = {};
	if (val.match(/^[0-9a-zA-Z\u4e00-\u9fa5]*$/) == null) {
		result.value = true;
		result.alertText = sipo.sysmgr.menu.specialcharacter;
	}
	return result;
}