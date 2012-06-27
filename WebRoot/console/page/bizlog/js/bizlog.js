/*
 * ---------------------进入biglog.jsp 显示页面之后，将焦点设置在第一个查询输入框中
---------------------------
 */
$(document).ready(function(){
 $("#login_name").focus();
 })
var export_dialog;
var bizlogdetail_dialog;
$(function(){

	export_dialog = $("#export_dialog").createWindow({
			title:sipo.sysmgr.bizlog.exportbizlog,
			width:400,
			height:180,
			modal:true,
			close:function(){$("#export_dialog").clearValidationPrompt(); }
	});
	
	bizlogdetail_dialog = $("#bizlogdetail_dialog").createWindow({
			title:sipo.sysmgr.bizlog.bizlogdetail,
			width:362,
			height:241,
			modal:true,
			close:function(){ }
	});

	$('#submitButton').click(function(){
		if($("#exportForm").validation()){
			if(submit_()){
				//$("#export_dialog").clearValidationPrompt();		
				export_dialog.closeWindow();//提交信息后，关闭窗口
			}
		}	
	});
	
	//确定(关闭)按钮
	$("#closeBizLog").click(function(){
		bizlogdetail_dialog.closeWindow();//关闭窗口
	});
	
	$('#windowClose').click(function(){
		$("#export_dialog").clearValidationPrompt();
		export_dialog.closeWindow();//提交信息后，关闭窗口
	});

	$('#selectAllbizlog').each(function(){
		$(this).bind('click',{str:"rowid"},checkAll);
	});	

	$("#startdate").datepicker();//查询日期条件
	$("#enddate").datepicker();//查询日期条件
	//导出日期条件
	$("#start").datepicker();
	//导出日期条件
	$("#end").datepicker();

	//导出日志按钮
	$("#exportlog").click(function(){
		$("#start").val("");
		$("#end").val("");
		export_dialog.showWindow();
	});
				
	//查询按钮
	$("#queryBizlog").click(function(){
		var login_name=$('#login_name').val();
		if(login_name.match(/^[0-9a-zA-Z]*$/)== null)
		{
			popMessage(sipo.sysmgr.bizlog.usernameonlyen);
			$('#login_name').focus();
			return;		
		}
		var ip=$('#ip').val();
		if(ip.match(/^[0-9.]*$/)== null)
		{
			popMessage(sipo.sysmgr.bizlog.iponlynum);
			$('#ip').focus();
			return;		
		}
		var role_name=$('#role_name').val();
		if(role_name.match(/^[0-9a-zA-Z\u4E00-\u9FA5]*$/)== null)
		{
			popMessage(sipo.sysmgr.bizlog.rolenameonlyecn);
			$('#role_name').focus();
			return;		
		}
		var begindate = $("#startdate").val();
		var enddate = $("#enddate").val();
		var regxDate=/^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/ 
		if(begindate&&!regxDate.test(begindate)){
			popMessage(sipo.sysmgr.bizlog.begindateerror);
			return;
		}
		if(enddate&&!regxDate.test(enddate)){
			popMessage(sipo.sysmgr.bizlog.enddateerror);
			return;
		}
		if(begindate&&enddate){
			if(begindate>enddate){
				popMessage(sipo.sysmgr.bizlog.dateerror);
				return;
			}
		}
		$("#query").attr("action",contextPath+"/console/showBizLogAC!queryBizLogList.do?wee.bizlog.modulelevel="+'0400603');
		$("#query").submit();
	});
	
	//重置按钮
	$("#resetBizlog").click(function(){
		$("#login_name").val("");
		$("#ip").val("");
		$("#role_name").val("");
		$("#startdate").val("");
		$("#enddate").val("");
	});
	
	//删除全部日志按钮 王敏20100422 已无此按钮
	$("#deleteAllLog").click(function(){			
		if(confirmMessage(sipo.sysmgr.bizlog.areyousuretodeleteallbizlogs)){
			//if(confirmMessage(sipo.sysmgr.bizlog.bizlogdeletedcannotreply)){
				$.post(contextPath+"/console/showBizLogAC!deleteAllBizLog.do",
				{'wee.bizlog.modulelevel':'0400604'}
				,function(data){
					if (data.flag == "success"){
						popMessage(sipo.sysmgr.bizlog.deletebizlogsuccess,null,function(){
							window.location.href=contextPath+"/console/showBizLogAC!queryBizLogList.do?wee.bizlog.modulelevel=0400602";
						});							
					}else{
						popMessage(sipo.sysmgr.bizlog.deletebizlogerror,null,function(){
							window.location.href=contextPath+"/console/showBizLogAC!queryBizLogList.do?wee.bizlog.modulelevel=0400602";
						});
					}
					
				},'json');
			//}
    	}

	});
	
})	

	function showInfo(rowid) {		
		$.getJSON(
					contextPath
							+ "/console/showBizLogAC!forwardShowBizLogDetailPage.do",
					{
						"id" : rowid,
						'wee.bizlog.modulelevel' : '0400605'
					},
					function(data) {
						$("#loginNameDetail").html(data.bizlogrow.loginName);
						$("#roleNameDetail").html(data.bizlogrow.roleName);						
						$("#ipDetail").html(data.bizlogrow.ip);
						$("#operationDateDetail").html(data.bizlogrow.operationDate);
						$("#moduleLevel1Detail").html(data.bizlogrow.moduleLevel1);
						$("#detailDetail").html(data.bizlogrow.detail);
						bizlogdetail_dialog.showWindow();
					}, "json");
	}
		

	function submit_(){
		var start = $("#start").val();
		var end = $("#end").val();
		var regxDate=/^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/ 
		if(start&&!regxDate.test(start)){
			popMessage(sipo.sysmgr.bizlog.begindateerror);
			return;
		}
		if(end&&!regxDate.test(end)){
			popMessage(sipo.sysmgr.bizlog.enddateerror);
			return;
		}
		//if(start&&end){
		//	if(start>end){
		//		popMessage(sipo.sysmgr.bizlog.dateerror);
		//		return;
		//	}
		//}
		
		$("#exportForm").attr("action",contextPath+"/console/exportBizLogAC!exportLog.do");
		$("#exportForm").submit();		
		return true;
	}
	
	// 全选--begin
	function checkAll(param) {	
		var a = document.getElementsByName(param.data.str);
		var n = a.length;
		for (var i = 0; i < n; i++)
			a[i].checked = window.event.srcElement.checked;
	}
	// 全选--end
	
	
///////////////////////////////////////////////////////////////////////////////////
	function showDetailInfo(index ,value,rowData){
		var canDel = '';
		//<s:if test="#session.isAppUser || #session.isAdmin">
			canDel = '<a href="#" onclick=showInfo("'+rowData["id"]+'")>'+sipo.sysmgr.bizlog.look+'</a> '
		//</s:if>
		return '<center><a href="#" onclick=deleteLogOne("'+rowData["id"]+'")>'+sipo.sysmgr.bizlog.dele+'</a> '+canDel;

	}
//+++++++++++++++++++++++++删除 begin+++++++++++++++++++++++++++++++++++++++++++++
	function validate() {
		if ($('input[name=rowid]:checked').length == 0) {
			popMessage(sipo.sysmgr.selectonemore);
			return false;
		}
		return true;
	}
	function checkbox(obj) {
		var str = document.getElementsByName(obj);
		var objarray = str.length;
		var selectstr = "";
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				selectstr += str[i].value + ",";
			}
		}
		return selectstr.substring(0, selectstr.length - 1);
	}
	
	//删除日志按钮
	function deleteLog(){		
		var logIds = $("#grid").getSelectData().rowid;		
		if(logIds==""){
		   popMessage(sipo.sysmgr.selectonemore);
			return;
		}	
		if (!window.confirm(sipo.sysmgr.bizlog.areyousuretodeletebizlogs)) {
			return;
		}		
		$.post(contextPath+"/console/showBizLogAC!deleteBizLog.do",
		{'logIds':logIds,'wee.bizlog.modulelevel':'0400604'}
		,function(data){
			if (data.flag == "success"){								
				popMessage(sipo.sysmgr.bizlog.deletebizlogsuccess,null,function(){
					window.location.href=contextPath+"/console/showBizLogAC!queryBizLogList.do?wee.bizlog.modulelevel=0400602";
				});	
			}else{				
				popMessage(sipo.sysmgr.bizlog.deletebizlogerror,null,function(){
					window.location.href=contextPath+"/console/showBizLogAC!queryBizLogList.do?wee.bizlog.modulelevel=0400602";
				});	
			}
			
		},'json');
	}
	
	function deleteLogOne(log){		
		if (!window.confirm(sipo.sysmgr.bizlog.areyousuretodeletebizlogs)) {
			return;
		}		
		$.post(contextPath+"/console/showBizLogAC!deleteBizLog.do",
		{'logIds':log,'wee.bizlog.modulelevel':'0400604'}
		,function(data){
			if (data.flag == "success"){				
				popMessage(sipo.sysmgr.bizlog.deletebizlogsuccess,null,function(){
					window.location.href=contextPath+"/console/showBizLogAC!queryBizLogList.do?wee.bizlog.modulelevel=0400602";
				});	
			}else{
				popMessage(sipo.sysmgr.bizlog.deletebizlogerror,null,function(){
					window.location.href=contextPath+"/console/showBizLogAC!queryBizLogList.do?wee.bizlog.modulelevel=0400602";
				});
			}
			
		},'json');    
	}
	
	

//+++++++++++++++++++++++++删除 end+++++++++++++++++++++++++++++++++++++++++++++

//校验日期
function checkValue(val) {
	var result = {};	
	var start = $("#start").val();
	var end = $("#end").val();	
	if(start&&end){
		if(start>end){
			result.value = true;
			result.alertText = sipo.sysmgr.bizlog.dateerror;
			//popMessage(sipo.sysmgr.bizlog.dateerror);
			//return;
		}
	}
	return result;
}