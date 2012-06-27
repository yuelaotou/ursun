var nameDuplicateFlag;
var modifyParentId;
// 新建提交校验,成功后执行success函数
function addsubmit_() {
	var name_add = $("#add_name").val();
	var description_add = $("#add_description").val();
	var parentID_add = dtnode.data.key;
	
	nameDuplicateFlag=false;
	checkNameDuplicateNew(name_add);
	if (!nameDuplicateFlag) {
		$.post(contextPath + "/console/maintainUnitAC!addUnit.do", {
			'unit.unitName' : name_add,
			'unit.description' : description_add,
			'unit.parentId' : parentID_add,
			'wee.bizlog.modulelevel' : '0400102'
		}, function(data) {
			if (data.temp == '1') {
				$("#unit_Id").val(data.newUnitId);
				newWindow.closeWindow();
				popMessage(sipo.sysmgr.user.orgaddsuccess, null, function() {
					window.location.href = contextPath
							+ "/console/showUnitAC!init.do?ftype=query&unitId="
							+ data.newUnitId + "&unit=" + data.newUnitId+"&wee.bizlog.modulelevel="+'0400101';
				}, true);
			}
		}, "json");
	}
}

// 修改提交校验,成功后执行success函数
function editsubmit_() {
	var id_modify = $("#unitId").val();
	var name_modify = $("#modify_name").val();
	var description_modify = $("#modify_description").val();
	var parentId_modify = dtnode.data.parentId;
	
	nameDuplicateFlag=false;
	checkNameDuplicateEdit(name_modify);
	if (!nameDuplicateFlag) {
		$.post(contextPath + "/console/maintainUnitAC!updateUnit.do", {
			'unit.id' : id_modify,
			'unit.unitName' : name_modify,
			'unit.description' : description_modify,
			'wee.bizlog.modulelevel' : '0400103'
		}, function(data) {
			if (data.temp == '1') {
				editWindow.closeWindow();
				popMessage(sipo.sysmgr.user.orgmodifysuccess, null, function() {
					window.location.href = contextPath
							+ "/console/showUnitAC!init.do?ftype=query&unitId="
							+ id_modify + "&unit=" + id_modify+"&wee.bizlog.modulelevel="+'0400101';
				}, true);
			}
		}, 'json');
	}
}

/*
 * ---------------页面加载初始化函数-------------------------------------
 */
var isAdmin;
var unit;
var newWindow;
var editWindow;
var unitWindow;
var unitName;
$(function() {
	isAdmin = $("#admin").val();
	// 初始化组织机构树
	$("#tree")
			.dynatree( {
				rootVisible : false,
				minExpandLevel : 2,
				persist : false,
				idPrefix : "ui-dynatree-id-unit-",
				initAjax : {
					root : "rootUnit",
					url : contextPath + "/console/showUnitAC!queryUnitTree.do",
					data : {
						'wee.bizlog.modulelevel' : '0400101',
						'unitId' : $("#unit_Id").val()
					}
				},
				onPostInit : function(isReloading, isError) {
					bindContextMenu("#tree");
					$("#tree").dynatree("getTree").selectKey(
							$("#unit_Id").val(), true);
					if ($("#unit_Id").val()) {
						var node = this.getNodeByKey($("#unit_Id").val());
						node.activate();
						node.expand(true);
					}
				},
				onClick : function(dtnode, event) {
					if (dtnode.getEventTargetType(event) == "expander") {
						dtnode.toggleExpand();
						$('#myMenu').hide();
						return false;
					}
					if (dtnode.data.key) {
						$("#info").text(sipo.sysmgr.user.text_userlist);
						if (dtnode.data.key == 'root') {
							window.location.href = contextPath
									+ "/console/showUnitAC!init.do?ftype=query&unitId=&unit="+"&wee.bizlog.modulelevel="+'0400110';
						} else {
							window.location.href = contextPath
									+ "/console/showUnitAC!init.do?ftype=query&unitId="
									+ dtnode.data.key + "&unit="
									+ dtnode.data.key
									+"&wee.bizlog.modulelevel=0400110";
						}
					}
				}
			});
	// 组织机构树禁止右键
	$("#tree").mousedown(function(e) {
		if (e.button == 2) {
			document.oncontextmenu = stop;
		} else {
			document.oncontextmenu = start;
		}

	});

	newWindow = $('#add_dialog').createWindow( {
		title : sipo.sysmgr.user.text_addunit,
		height : 180,
		width : 400,
		modal : true,
		close:function(){$('#add_dialog').clearValidationPrompt();}
	});
	$("#newSubmit").click(function() {
		$("#newSubmit").attr("disabled", "true");
		if ($("#newForm").validation()) {
			addsubmit_();
		}else{
			$("#newSubmit").removeAttr("disabled");
		}
	});
	$("#newCancel").click(function() {
		newWindow.closeWindow();
	});

	editWindow = $('#modify_dialog').createWindow( {
		title : sipo.sysmgr.user.text_editunit,
		height : 180,
		width : 400,
		modal : true,
		close:function(){$('#modify_dialog').clearValidationPrompt();}
	});
	$("#editSubmit").click(function() {
		$("#editSubmit").attr("disabled", "true");
		if ($("#editForm").validation()) {
			editsubmit_();

		}else{
			$("#editSubmit").removeAttr("disabled");
		}
	});
	$("#editCancel").click(function() {
		editWindow.closeWindow();
	});

	unitWindow = $("#treeDialog")
			.createWindow( {
				title : sipo.sysmgr.user.text_chooseunit,
				width : 260,
				height : 350,
				modal : true,
				"z-index":1003,
				buttons : [{
					html : '<table align="center" width="120"><tr><td>'
							+ '<div id="uuSubmit" class="button_pale color_Black"><strong></strong><div><a href="#">&nbsp;'
							+ sipo.sysmgr.submit
							+ '&nbsp;</a></div><span></span></div>'
							+ '<div  id="uuCancel" class="button_white color_Gray"><strong></strong><div><a href="#">&nbsp;'
							+ sipo.sysmgr.cancel
							+ '&nbsp;</a></div><span></span></div>'
							+ '</td></tr></table>'
				}]
			});
	$("#uuSubmit")
			.click(function() {
				var actNode = $("#unitTree").dynatree("getTree")
						.getActiveNode();
				var selNode = $("#unitTree").dynatree("getTree")
						.getSelectedNodes();
				if (!selNode[0]) {
					popMessage(sipo.sysmgr.user.pleasechooseorg, null, null,
							true);
					return;
				}
				$("#newParentId").val(actNode
						? actNode.data.key
						: selNode[0].data.key);
				unitWindow.closeWindow();

				$
						.post(
								contextPath + "/console/maintainUnitAC!isExist.do",
								{
									'unit.id' : '',
									'unit.unitName' : unitName,
									'unit.parentId' : selNode[0].data.key,
									'wee.bizlog.modulelevel' : '0400105'
								},
								function(data) {
									if (data.temp == '1') {
										popMessage(sipo.sysmgr.user.orgnameexist,null,null,true,null,null,1100);
										return;
									}
									$
											.post(
													contextPath + "/console/maintainUnitAC!updateUnitParent.do",
													{
														'unit.id' : $("#unit_Id")
																.val(),
														'unit.parentId' : selNode[0].data.key,
														'wee.bizlog.modulelevel' : '0400103'
													},
													function(data) {
														if (data.temp == '1') {
															popMessage(
																	sipo.sysmgr.user.orgmovesuccess,
																	null,
																	function() {
																		window.location.href = contextPath
																				+ "/console/showUnitAC!init.do?ftype=query&unitId="
																				+ $("#unit_Id")
																						.val()
																				+ "&unit="
																				+ $("#unit_Id")
																						.val();
																	}, true);
														}
													}, "json");
								}, 'json');

			});
	$("#uuCancel").click(function() {
		unitWindow.closeWindow();
	});

});

function stop() {
	return false;
}
function start() {
	return true;
}
/*
 * -------------右键菜单--------------------------------
 */
function bindContextMenu(objId) {
	$(objId + "  .ui-dynatree-document," + objId + " .ui-dynatree-folder")
			.destroyContextMenu()
			// unbind
			.contextMenu(
					{
						menu : "myMenu"
					},
					function(action, el, pos) {
						dtnode = el.attr("dtnode");
						switch (action) {
							case "add" :
								if ($("#admin").val() == "false"
										&& dtnode.data.key == "root") {
									popMessage(sipo.sysmgr.user.adminoperate,
											null, null, true);
									return;
								}
								$("#parentId").attr("value", dtnode.data.key);
								$("#add_name").attr("value", "");
								$("#add_description").attr("value", "");
								newWindow.showWindow();
								$("#add_name").focus();
								break;
							case "modify" :
								modifyParentId=dtnode.data.parentId;
								if ($("#admin").val() == "false"
										&& dtnode.data.key == "root") {
									popMessage(sipo.sysmgr.user.adminoperate,
											null, null, true);
									return;
								}
								if ($("#currUnitId").val() == dtnode.data.key) {
									popMessage(
											sipo.sysmgr.user.cannoteditownorg,
											null, null, true);
									return;
								}
								$
										.getJSON(
												contextPath + '/console/showUnitAC!queryUnitByIdForModify.do?unitId='
														+ dtnode.data.key
														+ "&wee.bizlog.modulelevel=0400106",
												function(data) {
													$("#unitId").attr("value",
															dtnode.data.key);
													$('#modify_name')
															.val(data.unit.unitName);
													if (data.unit.description == null) {
														$("#modify_description")
																.val("");
													} else {
														$("#modify_description")
																.val(data.unit.description);
													}

												});

								editWindow.showWindow();
								$("#modify_name").focus();
								break;
							case "delete" :
								if ($("#admin").val() == "false"
										&& dtnode.data.key == "root") {
									popMessage(sipo.sysmgr.user.adminoperate,
											null, null, true);
									return;
								}
								if ($("#currUnitId").val() == dtnode.data.key) {
									popMessage(
											sipo.sysmgr.user.cannotdelownorg,
											null, null, true);
									return;
								}
								if (dtnode.data.key == "registerUsers") {
									popMessage(
											sipo.sysmgr.user.cannotdeldefaultorg,
											null, null, true);
									return;
								}
								var id_delete = dtnode.data.key;
								if (id_delete == 'root') {
									popMessage(
											sipo.sysmgr.user.cannotdelorgroot,
											null, null, true);
									return;
								} else {
									if (confirmMessage(sipo.sysmgr.user.confirmdelorg)) {
										$
												.getJSON(
														contextPath + "/console/maintainUnitAC!deleteUnit.do",
														{
															'unit.id' : id_delete,
															'wee.bizlog.modulelevel' : '0400104'
														},
														function(data) {
															if (data.temp == '1') {
																popMessage(
																		sipo.sysmgr.user.orgdelsuccess,
																		null,
																		function() {
																			$("#tree")
																					.dynatree("getTree")
																					.reload();
																			window.location.href = contextPath
																					+ "/console/showUnitAC!init.do?wee.bizlog.modulelevel=0400110&ftype=query&unitId=&unit=";
																		}, true);
															}
														});
									}
								}
								break;
							case "move" :
								unitName = dtnode.data.title;
								showUnitTreeDialog(dtnode.data.key);
								break;
							default :
								popMessage("Todo: appply action '" + action
										+ "' to node " + dtnode, null, null,
										true);
						}
					});
};

function showUnitTreeDialog(unitId) {
	$("#unit_Id").val(unitId);
	$("#unitTree").dynatree( {
		selectMode : 1,
		persist : false,
		rootVisible : false,
		minExpandLevel : 2,
		initAjax : {
			root : "unitRoot",
			url : contextPath
					+ "/console/showUnitAC!queryUnitTreeForUpdateParent.do",
			data : {
				'unitId' : unitId,
				'wee.bizlog.modulelevel' : '0400101'
			}
		},
		onPostInit : function(dtnode, event) {
		},
		onClick : function(dtnode) {
			dtnode.select(true);
		}
	});
	unitWindow.showWindow();
}
function checkName(val) {
	var result = {};
	if (val.match(/^[a-zA-Z\u4e00-\u9fa5]+$/) == null) {
		result.value = true;
		result.alertText = sipo.sysmgr.user.orgnamespecialcharacter;
	}
	return result;
}
function checkDescription(val) {
	var result = {};
	if (val.match(/^[0-9a-zA-Z\u4e00-\u9fa5]*$/) == null) {
		result.value = true;
		result.alertText = sipo.sysmgr.user.orgdescriptionspecialcharacter;
	}
	return result;
}

function checkNameDuplicateNew(val) {
	var result = {};
	$.ajax( {
		async : false,
		type : "post",
		url : contextPath + "/console/maintainUnitAC!isExist.do",
		data : {
			'unit.id' : '',
			'unit.unitName' : val,
			'unit.parentId' : $("#parentId").val(),
			'wee.bizlog.modulelevel' : '0400105'
		},
		dataType : "json",
		success : function(data) {
			if (data.temp == '1') {
				nameDuplicateFlag = true;
				result.value = true;
				result.alertText = sipo.sysmgr.user.orgnameexist;
			}
		}

	});
	return result;
}

function checkNameDuplicateEdit(val) {
	var result = {};
	$.ajax( {
		async : false,
		type : "post",
		url : contextPath + "/console/maintainUnitAC!isExist.do",
		data : {
			'unit.id' : $("#unitId").val(),
			'unit.unitName' : val,
			'unit.parentId' : dtnode.data.parentId,
			'wee.bizlog.modulelevel' : '0400105'
		},
		dataType : "json",
		success : function(data) {
			if (data.temp == '1') {
				nameDuplicateFlag = true;
				result.value = true;
				result.alertText = sipo.sysmgr.user.orgnameexist;
			}
		}

	});
	return result;
}
function checkUserName(val){
	var result = {};
	if (val.match(/^[0-9a-zA-Z_]+$/) == null) {
		result.value = true;
		result.alertText = sipo.sysmgr.user.usernamespecialcharacter;
	}
	return result;
}
function checkFullName(val){
	var result = {};
	if (val.match(/^[a-zA-Z\u4e00-\u9fa5]+[a-zA-Z\u4e00-\u9fa5·]*[a-zA-Z\u4e00-\u9fa5]*$/) == null) {
		result.value = true;
		result.alertText = sipo.sysmgr.user.fullnamecheck;
	}
	return result;
}
function checkOther(val){
	var result = {};
	if (val.match(/^[0-9a-zA-Z\u4e00-\u9fa5]*$/) == null) {
		result.value = true;
		result.alertText = sipo.sysmgr.user.specialcharacter;
	}
	return result;
}