var type = null;
$(function(){
	$("#createUrl").click(function(){	
		var url=contextPath+'/console/showResourceAC!forwardUrlListPage.do?wee.bizlog.modulelevel=0400306';
		window.open(url,'_self', 'height=500, width=600');
	});
	
	$("#closeUrl").click(function(){
		var url=contextPath+'/console/showResourceAC!forwardListPage.do?wee.bizlog.modulelevel=0400301';
		window.open(url,'_self', 'height=500, width=600');		
	});

	//批量删除
	$("#deleteUrls").click(function(){
		var urlIds = $("#grid").getSelectData().rowid;		
		if(urlIds==""){
		   popMessage(sipo.sysmgr.selectonemore);
			return;
		}		
		if(confirmMessage(sipo.sysmgr.resource.areyousuretodeleteurl)){
			$.post(contextPath + "/console/maintainResourceAC!deleteUrlById.do",
			{'urlIds':urlIds,
			'wee.bizlog.modulelevel':'0400308'}
			,function(data){
				if (data.flag == "success"){					
					popMessage(sipo.sysmgr.resource.deleteurlsuccess,null,function(){
						window.location.href=contextPath+"/console/showResourceAC!queryUrlList.do?wee.bizlog.modulelevel=0400305";
					});
				}				
			},'json');			
    	}
	});
})

////////////////////////////////////////////////////////////////////////
	function showDetailInfo(index ,value,rowData){
		var canDel = '';
		canDel = '<a href="#" onclick=deleteUrl("'+rowData["urlId"]+'")>'+sipo.sysmgr.resource.deleteurl+'</a> '
		var aa = '<center><a href="#" onclick=eidtUrl("'+rowData["urlId"]+'")>'+sipo.sysmgr.resource.editurl+'</a> '+canDel+'</center>';		
		return aa;
	}
	function eidtUrl(urlId)
	{		
		var url=contextPath+'/console/maintainResourceAC!queryUrlById.do?urlId='+urlId+'&wee.bizlog.modulelevel=0400310';
		window.open(url,'_self', 'height=500, width=600');		
	}
//++++++++++++++++++删除 begin+++++++++++++++++++++++++++++++++++++++++++++
	function deleteUrl(urlId){
		if(confirmMessage(sipo.sysmgr.resource.areyousuretodeleteurl)){
			$.post(contextPath + "/console/maintainResourceAC!deleteUrlById.do",
			{'urlIds':urlId,
			'wee.bizlog.modulelevel':'0400308'}
			,function(data){
				if (data.flag == "success"){				
					popMessage(sipo.sysmgr.resource.deleteurlsuccess,null,function(){	
						window.location.href=contextPath+"/console/showResourceAC!queryUrlList.do?wee.bizlog.modulelevel=0400305";			
					},true);
				}				
			},'json');			
    	}		
	}
	
	// 全选--begin
	function checkAll(param) {
		var a = document.getElementsByName(param.data.str);
		var n = a.length;
		for (var i = 0; i < n; i++)
			a[i].checked = window.event.srcElement.checked;
	}
	// 全选--end
	
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
	
//++++++++++++++++++删除 end+++++++++++++++++++++++++++++++++++++++++++++
	