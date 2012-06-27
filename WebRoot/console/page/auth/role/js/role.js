var roleID_before = null;
var name_before = null;
var description_before = null;
var result = false;
var dtnode_all = null;
var anonymousRoleIds = null;
// --------------浮动窗口-------------------------
// -----在角色树中添加新的节点--------------
var addWindow;
var editWindow;
var roleWindow;
$(function() {
	addWindow = $("#add_dialog").createWindow( {
		title : sipo.sysmgr.role.addrole,
		height : 380,
		modal : true,
		close:function(){$('#add_dialog').clearValidationPrompt();}
	});
	editWindow = $("#modify_dialog").createWindow( {
		title : sipo.sysmgr.role.editrole,
		height : 220,
		modal : true,
		close:function(){$('#modify_dialog').clearValidationPrompt();}
	});
	roleWindow = $("#treeDialog")
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

	$("#addSubmit")
			.click(function() {
				var add_name = $("#add_name").val();
				var desc = $("#add_description").val();
				if ($('#addForm').validation()) {
					/*if (add_name.match(/^[0-9a-zA-Z\u4E00-\u9FA5]*$/) == null) {
						popMessage(sipo.sysmgr.role.rolenamespecialcharacter,
								null, null, true);
						$("#add_name").focus();
						return;
					}
					if (desc != ""
							&& desc.match(/^[0-9a-zA-Z\u4E00-\u9FA5]*$/) == null) {
						popMessage(
								sipo.sysmgr.role.roledescriptionspecialcharacter,
								null, null, true);
						$("#add_description").focus();
						return;
					}*/
					var name_add = document.getElementById("add_name");
					var description_add = document
							.getElementById("add_description");
					var parentID_add = dtnode.data.key;
					var result = false;
					$
							.post(
									contextPath + "/console/maintainRoleAC!isExist.do",
									{
										'role.id' : '',
										'role.roleName' : name_add.value,
										'role.parentId' : parentID_add,
										'wee.bizlog.modulelevel':'0400211'
									},
									function(data) {
										if (data.flag) {
											popMessage(
													sipo.sysmgr.role.rolenameexist,
													null, null, true);
											return;
										}
										$
												.post(
														contextPath
																+ "/console/maintainRoleAC!addRole.do?time="
																+ new Date(),
														{
															'wee.bizlog.modulelevel' : '0400202',
															"role.roleName" : name_add.value,
															"role.description" : description_add.value,
															"role.parentId" : parentID_add
														},
														function(data) {
															addWindow
																	.closeWindow();
															$
																	.each(
																			data,
																			function(
																					i,
																					item) {
																				if (i == "success"
																						&& item != null) {
																					if (item == true)
																						result = true;
																				}
																			});
															if (result) {
																$("#roleId")
																		.val(data.newRoleId);
																popMessage(
																		sipo.sysmgr.role.roleaddsuccess,
																		null,
																		function() {
																			window.location.href = contextPath
																					+ "/console/showRoleAC!init.do?ftype=query&role.roleId="
																					+ data.newRoleId
																					+ "&wee.bizlog.modulelevel=0400205";
																		}, true);
															}
															if (!result) {
																popMessage(
																		sipo.sysmgr.role.roleaddfailure,
																		null,
																		null,
																		true);
																return;
															}
														}, "json");
									}, 'json')
				}
			});
	$("#addCancel").click(function() {
		addWindow.closeWindow();
	});
	$("#editSubmit")
			.click(function() {
				var modify_name = $("#modify_name").val();
				var desc = $("#modify_description").val();
				if ($('#modifyForm').validation()) {
					/*if (modify_name.match(/^[0-9a-zA-Z\u4E00-\u9FA5]*$/) == null) {
						popMessage(sipo.sysmgr.role.rolenamespecialcharacter,
								null, null, true);
						$("#modify_name").focus();
						return;
					}

					if (desc != ""
							&& desc.match(/^[0-9a-zA-Z\u4E00-\u9FA5]*$/) == null) {
						popMessage(
								sipo.sysmgr.role.roledescriptionspecialcharacter,
								null, null, true);
						$("#modify_description").focus();
						return;
					}*/
					var name_modify = document.getElementById("modify_name");
					var description_modify = document
							.getElementById("modify_description");
					var result = false;
					$
							.post(
									contextPath + "/console/maintainRoleAC!isExist.do",
									{
										'role.id' : roleID_before,
										'role.roleName' : name_modify.value,
										'role.parentId' : dtnode.data.parentId,
										'wee.bizlog.modulelevel':'0400211'
									},
									function(data) {
										if (data.flag) {
											popMessage(
													sipo.sysmgr.role.rolenameexist,
													null, null, true);
											return;
										}
										$
												.post(
														contextPath
																+ "/console/maintainRoleAC!updateRole.do?time="
																+ new Date(),
														{
															'wee.bizlog.modulelevel' : '0400203',
															"role.roleId" : roleID_before,
															"role.roleName" : name_modify.value,
															"role.description" : description_modify.value
														},
														function(data) {
															editWindow
																	.closeWindow();
															$
																	.each(
																			data,
																			function(
																					i,
																					item) {
																				if (i == "success"
																						&& item != null) {
																					if (item == true)
																						result = true;
																				}
																			});
															if (result) {
																popMessage(
																		sipo.sysmgr.role.rolemodifysuccess,
																		null,
																		function() {
																			window.location.href = contextPath
																					+ "/console/showRoleAC!init.do?ftype=query&role.roleId="
																					+ roleID_before
																					+ "&wee.bizlog.modulelevel=0400205";
																		}, true);

															}
															if (!result) {
																popMessage(
																		sipo.sysmgr.role.rolemodifyfailure,
																		null,
																		null,
																		true);
																return;
															}
														}, "json");
									}, "json");
				}
			});
	$("#editCancel").click(function() {
		editWindow.closeWindow();
	});

	$("#rcSubmit")
			.click(function() {
				var actNode = $("#roletree").dynatree("getTree")
						.getActiveNode();
				var selNode = $("#roletree").dynatree("getTree")
						.getSelectedNodes();
				if (!selNode[0]) {
					popMessage(sipo.sysmgr.role.pleasechooseonlyonerole, null,
							null, true);
					return;
				}
				$("#newParentId").val(actNode
						? actNode.data.key
						: selNode[0].data.key);
				roleWindow.closeWindow();

				$
						.post(
								contextPath + "/console/maintainRoleAC!isExist.do",
								{
									'role.id' : '',
									'role.roleName' : roleName,
									'role.parentId' : selNode[0].data.key
								},
								function(data) {
									if (data.flag) {
										popMessage(
												sipo.sysmgr.role.rolenameexist,
												null, null, true);
										return;
									}
									$
											.post(
													contextPath
															+ "/console/maintainRoleAC!updateRoleParent.do",
													{
														"wee.bizlog.modulelevel" : '0400203',
														"role.id" : $("#roleId")
																.val(),
														"role.parentId" : selNode[0].data.key
													},
													function(data) {
														popMessage(
																sipo.sysmgr.role.rolemovesuccess,
																null,
																function() {
																	window.location.href = contextPath
																			+ "/console/showRoleAC!init.do?ftype=query&role.roleId="
																			+ $("#roleId")
																					.val()
																			+ "&wee.bizlog.modulelevel=0400205";
																}, true);
													}, "json");
								}, 'json');
			});
	$("#rcCancel").click(function() {
		roleWindow.closeWindow();
	});

	$("#role_tree").dynatree( {
		persist : false,
		selectMode : 2,
		autoFocus : false,
		minExpandLevel : 3,
		checkbox : false,
		initAjax : {
			url : contextPath + "/console/showRoleAC!queryRoleTree.do",
			root : "root",
			data : {
				'wee.bizlog.modulelevel' : '0400201',
				'role.roleId' : $('#roleId').val()
			}
		},
		onPostInit : function(isReloading, isError) {
			bindContextMenu("#role_tree");
			$("#role_tree").dynatree("getTree").selectKey($("#roleId").val(),
					true);
			if ($("#roleId").val()) {
				var node = this.getNodeByKey($("#roleId").val());
				if(node){
					node.activate();
					node.expand();
				}
			}
		},
		onClick : function(dtnode, event) {
			if (dtnode.getEventTargetType(event) == "expander") {
				dtnode.toggleExpand();
				return false;
			}
			dtnode_all = dtnode;
			if (dtnode.data.key) {
				window.location.href = contextPath
						+ "/console/showRoleAC!init.do?ftype=query&role.roleId="
						+ dtnode.data.key + "&wee.bizlog.modulelevel=0400205";
			}
		}
	});

	// 树禁止右键
	$("#role_tree").mousedown(function(e) {
		if (e.button == 2) {
			document.oncontextmenu = stop;
		} else {
			document.oncontextmenu = start;
		}

	});

	// 获取匿名角色Id数组
	$.getJSON(contextPath + "/console/showRoleAC!queryAnonymousRoleIds.do?time="
			+ new Date(), {
		'wee.bizlog.modulelevel' : '0400210'
	}, function(data) {
		$.each(data, function(i, item) {
			if (i == "anonymousRoleIds" && item != null) {
				if (item.length > 0)
					anonymousRoleIds = item;
			}
		});

	});
});

function stop() {
	return false;
}
function start() {
	return true;
}

// --- 绑定右键菜单 --------------------------------------------------
function bindContextMenu(objId) {
	$(objId + "  .ui-dynatree-document," + objId + " .ui-dynatree-folder")
			.destroyContextMenu()
			.contextMenu(
					{
						menu : "myMenu"
					},
					function(action, el, pos) {
						dtnode = el.attr("dtnode");
						var key = dtnode.data.key;
						var flag = false;
						$.each(anonymousRoleIds, function(i, item) {
							if (key == item) {
								flag = true;
							}
						});
						var parentId = dtnode.data.parentId;
						var children = dtnode.data.children;
						switch (action) {
							case "add" :
								if (key == "root") {
									popMessage(
											sipo.sysmgr.role.rootcannotaddrole,
											null, null, true);
								} else if (flag) {
									popMessage(
											sipo.sysmgr.role.anonyrolecannotaddrole,
											null, null, true);
								} else {
									var name_add = document
											.getElementById("add_name");
									var description_add = document
											.getElementById("add_description");
									var parent_add_name = document
											.getElementById("add_parent_name");
									var parent_add_description = document
											.getElementById("add_parent_description");
									parent_add_name.value = dtnode.data.title;
									parent_add_description.value = dtnode.data.tooltip;
									name_add.value = "";
									description_add.value = "";
									addWindow.showWindow();
									$("#add_name").focus();
								}
								break;
							case "modify" :
								if (parentId == "root") {
									popMessage(
											sipo.sysmgr.role.cannotedithighestrole,
											null, null, true);
								} else if (key == "root") {
									popMessage(sipo.sysmgr.role.cannoteditroot,
											null, null, true);
								} else if (flag) {
									popMessage(
											sipo.sysmgr.role.cannoteditanonyrole,
											null, null, true);
								} else {
									var name_modify = document
											.getElementById("modify_name");
									var description_modify = document
											.getElementById("modify_description");
									roleID_before = dtnode.data.key;
									description_before = dtnode.data.tooltip;
									name_before = dtnode.data.title;
									name_modify.value = name_before;
									description_modify.value = description_before;
									editWindow.showWindow();
									$("#modify_name").focus();
								}
								break;
							case "delete" :
								var roleIds = $("#roleIds").val();
								var roleIds_delete = roleIds.split(", ");
								var roleID_delete = dtnode.data.key;
								if (parentId == "root") {
									popMessage(
											sipo.sysmgr.role.cannotdelhighestrole,
											null, null, true);
									return;
								} else if (key == "root") {
									popMessage(sipo.sysmgr.role.cannotdelroot,
											null, null, true);
									return;
								} else if (key == "basicRole"
										|| key == "advancedRole"
										|| key == "tradeRole") {
									popMessage(
											sipo.sysmgr.role.cannotdeldefaultrole,
											null, null, true);
									return;
								} else if (flag) {
									popMessage(
											sipo.sysmgr.role.cannotdelanonyrole,
											null, null, true);
									return;
								} else {
									if (children) {
										if (confirmMessage(sipo.sysmgr.role.confirmdelrolehaschild)) {
											$
													.getJSON(
															contextPath
																	+ "/console/maintainRoleAC!deleteRole.do?time="
																	+ new Date(),
															{
																"role.roleId" : roleID_delete,
																'wee.bizlog.modulelevel' : '0400204'
															},
															function(data) {
																$
																		.each(
																				data,
																				function(
																						i,
																						item) {
																					if (i == "success"
																							&& item != null) {
																						if (item == true)
																							result = true;
																					}
																				});
																if (result) {
																	popMessage(
																			sipo.sysmgr.role.roledelsuccess,
																			null,
																			function() {
																				$("#role_tree")
																						.dynatree("getTree")
																						.reload();
																			},
																			true);
																}
																if (!result) {
																	popMessage(
																			sipo.sysmgr.role.roledelfailure,
																			null,
																			null,
																			true);
																	return;
																}
															});
										}

									} else {
										if (confirmMessage(sipo.sysmgr.role.confirmdelrolenochild)) {
											$
													.getJSON(
															contextPath
																	+ "/console/maintainRoleAC!deleteRole.do?time="
																	+ new Date(),
															{
																"role.roleId" : roleID_delete,
																'wee.bizlog.modulelevel' : '0400204'
															},
															function(data) {
																$
																		.each(
																				data,
																				function(
																						i,
																						item) {
																					if (i == "success"
																							&& item != null) {
																						if (item == true)
																							result = true;
																					}
																				});
																if (result) {
																	popMessage(
																			sipo.sysmgr.role.roledelsuccess,
																			null,
																			function() {
																				$("#role_tree")
																						.dynatree("getTree")
																						.reload();
																			},
																			true);
																}
																if (!result) {
																	popMessage(
																			sipo.sysmgr.role.roledelfailure,
																			null,
																			null,
																			true);
																	return;
																}
															});
										}
									}
								}
								break;
							case "move" :
								roleName = dtnode.data.title;
								showRoleTreeDialog(dtnode.data.key);
								break;
							default :
								null;
						}
					});

};
var roleName;
function showRoleTreeDialog(roleid) {
	$("#roleId").val(roleid);
	$("#roletree").dynatree( {
		persist : false,
		selectMode : 1,
		autoFocus : false,
		minExpandLevel : 3,
		checkbox : false,
		initAjax : {
			url : contextPath
					+ "/console/showRoleAC!queryRoleTreeForUpdateParent.do",
			root : "roleTree",
			data : {
				'wee.bizlog.modulelevel' : '0400201',
				'role.id' : roleid
			}
		},
		onPostInit : function(isReloading, isError) {
		},
		onClick : function(dtnode, event) {
			dtnode.select(true);
		}
	});
	roleWindow.showWindow();
}
function checkValue(val){
	var result = {};
	if (val.match(/^[0-9a-zA-Z\u4E00-\u9FA5]*$/) == null) {
		result.value = true;
		result.alertText = sipo.sysmgr.role.specialcharacter;
	}
	return result;
}