
// --- Implement Cut/Copy/Paste --------------------------------------------
//var clipboardNode = null;
//var pasteMode = null;

var resourceId = null;
var type = null;
var isresource = null;

	function submit_(){		
		if (type == "add"){
			addConfirmation();
		}else if (type == "modify"){
			modifyConfirmation();
		}
	}
// --- Init dynatree during startup ----------------------------------------
$(function() {
	$("#resource-tree").dynatree( {
		rootVisible: false,
		minExpandLevel: 2,
		persist : false,
		initAjax : {
			root : "rootRes",
			url : contextPath + "/console/showResourceAC!queryResourceTree.do",
			data:{"wee.bizlog.modulelevel":"0400301"} //0400301对应功能点业务日志代码 "显示资源树"
		},
		onActivate : function(dtnode) {
			
		},		
		onClick : function(dtnode, event) {
			// Eat keyboard events, while a menu is open			
			if(dtnode.data.key){
				contentDetail(dtnode.data.key);		
			}	
		},
		onPostInit : function(dtnode, event) {
			bindContextMenu("#resource-tree");
			if($("#activeResourceId").val()){
		   	  var node=this.getNodeByKey($("#activeResourceId").val());
			  if(node){
				  node.activate();
				  node.expand();
			  }
		   	}
		}
	});
	$.getJSON(contextPath + "/console/showResourceAC!queryResourceById.do?resourceId=root&wee.bizlog.modulelevel=0400309", function(data){		
		var info="<ul class='fujian'><li><p class='fujian_tu'>"+sipo.sysmgr.resource.resourcename
				+"</p><div class='fujian_zi'>"+data.resourceparent.title
				+"</div><div class='clear'></div></li><li><p class='fujian_tu'>"
				+sipo.sysmgr.resource.resourcedescribe+"</p><div class='fujian_zi'>"+data.resourceparent.description
				+"</div><div class='clear'></div></li></ul>";		
		$("#contentFrame").append(info);
	});
	
	//资源树禁止右键
	$("#resource-tree").mousedown(function(e){
		if(e.button==2){
			document.oncontextmenu=stop; 
		}else{
			document.oncontextmenu=start;
		}
		
	});
});

function stop(){
return false;
}
function start(){
return true;
}
// --- Contextmenu helper --------------------------------------------------

function bindContextMenu(objId) {
	// Add context menu to document nodes:
	$(objId + "  .ui-dynatree-document," + objId + " .ui-dynatree-folder")
			.destroyContextMenu().contextMenu( {
				menu : "myMenu"
			}, function(action, el, pos) {
				var dtnode = el.attr("dtnode");				
				switch (action) {
					case "add" :
						type="add";
						resourceId = dtnode.data.key;
						//判断节点类型，如果是页面元素，则不能添加子节点
						$.getJSON(contextPath + '/console/showResourceAC!queryResourceById.do?resourceId='+resourceId+'&wee.bizlog.modulelevel=0400309', function(data) {		
							var isLeaf = data.resourceparent.isLeaf;
							var resouType = data.resourceparent.resourceType;
							if (isLeaf == "1"){//点击目录响应
								popMessage(sipo.sysmgr.resource.leafresourcenoadd);
								return;
							}
							else{//点击资源响应
								$("#resleaf").show();
								$("#resrid").hide();
								$("#resurl").hide();
								$("form input[type='radio']").get(1).checked  = true;
								$('#is_leaf0').attr('disabled',false);
								$('#is_leaf1').attr('disabled',false);
								
								addRow();
							}
						});	
						break;
					case "edit" :	
						type="modify";
						resourceId = dtnode.data.key;
						$("#resleaf").hide();
						$("#resrid").hide();
						editRow();
						break;
					case "delete" :						
						resourceId = dtnode.data.key;
						deleteRow();
						break;
					default :
						alert("Todo: appply action '" + action + "' to node "
							+ dtnode);
				}
		}
		);
};
var editResource;//资源右键窗口
var selectUrlList;//url列表窗口
$(function(){
	editResource = $("#create").createWindow({			
			width:380,
			height:460,
			modal:true,
			close:function(){$("#create").clearValidationPrompt() }
	});
	WindowX = (screen.width - 380) / 2;
	selectUrlList = $("#selectUrlList").createWindow({
			title:sipo.sysmgr.resource.canbindurllist,
			left:WindowX+200,
			width:699,
			height:420,
			"z-index":1003,
			close:function(){$("#selectUrlList").clearValidationPrompt()}
	});
	
	$('#submitButton').click(function(){
		if($("#resourceForm").validation()){
			submit_();
		}
	});
	
	$('#windowClose').click(function(){
		$("#create").clearValidationPrompt();
		editResource.closeWindow();//提交信息后，关闭窗口
		selectUrlList.closeWindow();
	});	
	
	$("#bindurl").click(function(){
		if (this.checked){
			$("#bindurllist").show();
		}
		else{
			$("#bindurllist").hide();
		}
	});
	isLeaf();//是否为叶子节点：如果点击是则显示资源类型，否则隐藏
})

//++++++++++++++验证资源ID重名问题++++++++++++++modifyed by 王敏 2010-02-02 begin
$(function() {
	$('#rid').blur(function(){
		if($('#rid').val()!=""){
			var ridCheck=$("#rid").val();		
			ridCheck = encodeURI(ridCheck);
			checkRidRename(ridCheck);
		}
	});
});
//++++++++++++++验证资源ID重名问题++++++++++++++modifyed by 王敏 2010-02-02 end

function contentDetail(resourceId){
	$.getJSON(contextPath + '/console/showResourceAC!queryResourceById.do?resourceId='+resourceId+'&wee.bizlog.modulelevel=0400309', function(data) {		
		var url = data.resourceparent.url;//资源绑定的URL内容
		var info= "";
		if (url == null){
			url = sipo.sysmgr.resource.resourcenourl;
		}else{
			url = url[0];
		}
		var rid = data.resourceparent.rid;
		if(rid == null){
			rid = "";
		}
		var des = data.resourceparent.description;
		if(des == null){
			des = "";
		}								
		var is_leaf = data.resourceparent.isLeaf;
		if(is_leaf == "0"){//目录节点
			//不是叶子节点
			res_type = sipo.sysmgr.resource.resourcenoleaf;
			info="<ul class='fujian' style='word-break:break-all'><li><p class='fujian_tu'>"
					+sipo.sysmgr.resource.resourcename+"</p><div class='fujian_zi'>"					
					+data.resourceparent.title
					+"</div><div class='clear'></div></li><li><p class='fujian_tu'>"
					+sipo.sysmgr.resource.resourcedescribe
					+"</p><div class='fujian_zi'>"								 								 								 		
					+des
					+"</div><div class='clear'></div></li></ul>";
		}
		else{
			info="<ul class='fujian' style='word-break:break-all'><li><p class='fujian_tu'>"
					+sipo.sysmgr.resource.resourcename+"</p><div class='fujian_zi'>"
					+data.resourceparent.title
					+"</div><div class='clear'></div></li><li><p class='fujian_tu'>"								 		
					+sipo.sysmgr.resource.resourceurl+"</p><div class='fujian_zi'>"
					+url
					+"</div><div class='clear'></div></li><li><p class='fujian_tu'>"
					+sipo.sysmgr.resource.resourceid+"</p><div class='fujian_zi'>"
					+rid
					+"</div><div class='clear'></div></li><li><p class='fujian_tu'>"
					+sipo.sysmgr.resource.resourcedescribe+"</p><div class='fujian_zi'>"
					+des
					+"</div><div class='clear'></div></li></ul>";
		}
		$("#contentFrame").empty();
		$("#contentFrame").append(info);
	});	
}

function isLeaf(){
	var value = $("form input[@id='is_leaf'][checked]").val();
	if (value==0){//是目录，不是叶子节点，隐藏资源类型和url
		$("#resrid").hide();//隐藏资源ID
		$("#resurl").hide();//隐藏url
		$("#bindurl").attr('checked',false);
		$("#bindurllist").hide();//隐藏url列表
		$("#chooseurl").val("");
	}else{
		$("#resrid").show();//显示资源ID
		$("#resurl").show();//显示url
	}
	
}

//******************新增验证 begin************************//
function addConfirmation(){
	var title = $('#title').val();
	var is_leaf = $("form input[@id='is_leaf'][checked]").val();
	var describe = $('#describe').val();
	if($("#bindurl").attr('checked')==false){//未打钩
		var url = "";
	}
	else{
		var url = $('#chooseurlid').val();
	}
	var rid = "";
	//添加时候首先判断是否是目录节点，是的话取资源ID，不是的话资源ID为“”
	if (is_leaf == "1"){
		rid = $("#rid").val();
		if(rid == "" || rid == null){
			rid = "";
			//alert(sipo.sysmgr.resource.resourceidneeded);
			//$("#rid").focus();
			//return;
		}		
	}	
	$.post(contextPath+"/console/maintainResourceAC!addResource.do"
		,{'resourcerow.title':title,
		'resourcerow.parentId':resourceId,'resourcerow.description':describe,
		'resourcerow.url':url,'resourcerow.isLeaf':is_leaf,'resourcerow.rid':rid,
		'wee.bizlog.modulelevel':'0400302'}
		,function(data){			
			if (data.flag == "success"){				
				popMessage(sipo.sysmgr.resource.addresourcesuccess,null,function(){
					$("#activeResourceId").val(data.activeResourceId);
					$("#resource-tree").dynatree("getTree").reload();					
					contentDetail(data.activeResourceId);
				},true);
			}else if (data.flag == "rename"){				
				popMessage(sipo.sysmgr.resource.renameresourceid,null,function(){
					return;
				},true);
			}
	},'json');
	$("#resurl").hide();//隐藏是否绑定url	
	$("#bindurllist").hide();//隐藏url列表
	$("#resrid").hide();//隐藏资源ID
	$("#chooseurl").val("");
	$("#chooseurlid").val("");
	$("#create").clearValidationPrompt();
	editResource.closeWindow();//提交信息后，关闭窗口
	selectUrlList.closeWindow();
}
//******************新增验证 end************************//

//******************修改验证 begin************************//
function modifyConfirmation(){
	var title = $('#title').val();
	var describe = $('#describe').val();
	var url = null;
	if($("#bindurl").attr('checked')==false){//未打钩
		url = "";
	}
	else{
		url = $('#chooseurlid').val();
	}
	if(isresource == "1"){
		var rid = $("#rid").val();
		if(rid == "" || rid == null){
			rid = "";
			//alert(sipo.sysmgr.resource.resourceidneeded);
			//$("#rid").focus();
			//return;
		}			
	}	
	$.post(contextPath + "/console/maintainResourceAC!updateResource.do"
		,{'resourcerow.resourceId':resourceId,'resourcerow.title':title,
		'resourcerow.description':describe,'resourcerow.rid':rid,
		'resourcerow.url':url,'resourcerow.isLeaf':isresource,
		'wee.bizlog.modulelevel':'0400303'}
		,function(data){			
			if (data.flag == "success"){				
				popMessage(sipo.sysmgr.resource.editresourcesuccess,null,function(){
					$("#activeResourceId").val(resourceId);
					$("#resource-tree").dynatree("getTree").reload();
					contentDetail(resourceId);
				},true);
				
			}else if (data.flag == "rename"){			
				popMessage(sipo.sysmgr.resource.renameresourceid,null,function(){
					return;
				},true);
			}												
		},'json');
	$("#resurl").hide();//隐藏是否绑定url	
	$("#bindurllist").hide();//隐藏url列表
	$("#resrid").hide();//隐藏资源ID
	$("#chooseurl").val("");
	$("#chooseurlid").val("");
	$("#create").clearValidationPrompt();
	editResource.closeWindow();//提交信息后，关闭窗口
	selectUrlList.closeWindow();
}
//******************修改验证 end************************//

//******************新增资源 begin************************//
function addRow() {
	var radios = document.getElementsByName("radiourl");
	for(var i=0;i<radios.length;i++){		
		radios[i].checked=false;		
	}			
	$.getJSON(contextPath + '/console/showResourceAC!queryResourceById.do?resourceId='+resourceId+'&wee.bizlog.modulelevel=0400309', function(data) {		 					
		$("#parent_id").val(data.resourceparent.resourceId);			
		$("#p_title").val(data.resourceparent.title);
		if(data.resourceparent.description == null || data.resourceparent.description == ""){
			$("#p_description").val("");
		}
		else{
			$("#p_description").val(data.resourceparent.description);
		}		
		$("#resource_id").val("");
		$("#title").val("");
		$("#rid").val("");
		$("#describe").val("");
		$("#bindurl").attr("checked",'');//未打钩
		$("#bindurllist").hide();//隐藏URL列表
	});
	editResource.find(".windowTopContent").html(sipo.sysmgr.resource.newresource);	
	editResource.showWindow();
	$("#title").focus();
}
//******************新增资源 end************************//

//******************修改资源 begin************************//
function editRow(){
	
	$.getJSON(contextPath + '/console/showResourceAC!queryResourceByIdForModify.do?resourceId='+resourceId+'&wee.bizlog.modulelevel=0400309', function(data) {		 					
		$("#parent_id").val(data.resourceparent.resourceId);		
		$("#p_title").val(data.resourceparent.title);
		if(data.resourceparent.description == null || data.resourceparent.description == ""){
			$("#p_description").val("");
		}
		else{
			$("#p_description").val(data.resourceparent.description);
		}		
		$("#resource_id").val(data.resourcerow.resourceId);
		$("#title").val(data.resourcerow.title);
		$("#resleaf").hide();//隐藏是否是叶子节点的radio
		if(data.resourcerow.description == null){
			$("#describe").val("");
		}
		else{
			$("#describe").val(data.resourcerow.description);
		}			
		if(data.resourcerow.isLeaf == "0"){//目录节点只显示资源标题和描述
			$("#resurl").hide();//目录节点隐藏是否绑定url	
			$("#resrid").hide();//隐藏资源ID
			$("#resurl").hide();//隐藏url
			$("#bindurl").attr('checked',false);
			$("#bindurllist").hide();//隐藏url列表
			$("#chooseurl").val("");
			$("#chooseurlid").val("");
			isresource = "0";//证明是目录节点
		}
		else{
			$("#resrid").show();//叶子节点显示资源ID
			if(data.resourcerow.rid == null){			
				$("#rid").val("");
			}
			else{			
				$("#rid").val(data.resourcerow.rid);
			}
			isresource = "1";//证明是叶子节点
			if(data.resourcerow.url == null){//叶子节点未绑定url
				$("#chooseurl").val("");
				$("#chooseurlid").val("");
				$("#resurl").show();
				$("#bindurl").attr("checked",'');//未打钩
				$("#bindurllist").hide();//隐藏URL列表
				
				/////////////add by wangmin 20100607 begin /////////////////
				var radios = document.getElementsByName("radiourl");
				for(var i=0;i<radios.length;i++){		
					radios[i].checked=false;		
				}
				////////////add by wangmin 20100607 end /////////////////
			}
			else{//叶子节点绑定url
				$("#chooseurl").val(data.resourcerow.url[0]);
				$("#resurl").show();				
				$("#bindurl").attr("checked",true);//打勾
				$("#bindurllist").show();//隐藏URL列表
				////////add by wangmin 20100607 begin /////////////////////////						
				$("#chooseurlid").val(data.resourcerow.url[1]);
				var urlid = $("#chooseurlid").val();
				var radios = document.getElementsByName("radiourl");
				for(var i=0;i<radios.length;i++){
					if(radios[i].value==urlid){
						radios[i].checked=true;
					}
				}
				/////////////add by wangmin 20100607 end /////////////////
			}
		}
		
	});
	editResource.find(".windowTopContent").html(sipo.sysmgr.resource.editresource);	
	editResource.showWindow();
	$("#title").focus();
}
//******************修改资源 end************************//

function deleteRow(){
	if(resourceId=="root"){
		popMessage(sipo.sysmgr.connotdeleteroot);
		return;
	}
	if(confirmMessage(sipo.sysmgr.resource.areyousuretodeleteresource)){
		$.post(contextPath + "/console/maintainResourceAC!deleteResourceById.do",
		{'resourceId':resourceId,'wee.bizlog.modulelevel':'0400304'}
		,function(data){		    
		    if (data.flag == "success"){			
				popMessage(sipo.sysmgr.resource.deleteresourcesuccess,null,function(){
					$("#resource-tree").dynatree("getTree").reload();
					contentDetail("root");
				});
			}				
		},'json');
    }	
}

$(function(){
	$("#maintainUrl").click(function(){
		var url=contextPath+'/console/showResourceAC!queryUrlList.do?wee.bizlog.modulelevel=0400305';
		window.open(url,'_self', 'height=500, width=600');
	});
})

//点击弹出url列表事件
function selectUrl(){
	selectUrlList.showWindow();
}

//选中url事件
function clickRow(urlid){
	var radios = document.getElementsByName("radiourl");
	for(var i=0;i<radios.length;i++){
		if(radios[i].value==urlid){
			radios[i].checked=true;
		}
	}
	$.getJSON(contextPath + '/console/maintainResourceAC!queryUrlByIdforSelect.do?urlId='+urlid+'&wee.bizlog.modulelevel=0400310', function(data) {
		$("#chooseurl").val(data.urlrow.content);					
	});
	$('#chooseurlid').val(urlid);		
	selectUrlList.closeWindow();
}

$("#clickRow").click(function(){
	
})

//校验中文、英文、数字的输入组合
function checkValue(val) {
	var result = {};
	if (val.match(/^[0-9a-zA-Z\u4e00-\u9fa5]*$/) == null) {
		result.value = true;
		result.alertText = sipo.sysmgr.resource.specialcharacter;
	}
	return result;
}

//校验英文、数字的输入组合
function checkRidValue(val) {
	var result = {};
	if (val.match(/^[0-9a-zA-Z_]*$/) == null) {
		result.value = true;
		result.alertText = sipo.sysmgr.resource.specialcharacteronlyennum;
	}
	return result;
}

//校验资源ID输入唯一性
function checkRidRename(ridCheck) {
	var result = {};
  	$.ajax({
     	type: "POST",
      	url: contextPath+"/console/maintainResourceAC!confirmName.do",
      	data: "resourcerow.rid="+ridCheck+"&resourcerow.type="+type+"&resourcerow.resourceId="+resourceId+"&wee.bizlog.modulelevel=0400305",//0400305对应功能点业务日志代码 "验证资源ID重名"
      	async:false,
      	success: function(mes){
      	mes=eval("("+mes+")");
	      	if(mes.exist==false){
	      		result.value = true;
	      		result.alertText = sipo.sysmgr.resource.resourceidexist;	      		
	      		$('#rid').focus();
	      	}
      	}      	
    });
	return result;
}