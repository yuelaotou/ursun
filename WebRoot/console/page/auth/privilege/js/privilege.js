$(function(){
	$("#tree").dynatree({
		minExpandLevel: 3,			
		persist: false,
		initAjax : {
			root:"rootRole",
			url : contextPath+"/console/showPrivilegeAC!queryRoleList.do",
			data:{'wee.bizlog.modulelevel':'0400501'}
		},
		onClick: function(dtnode) {
			    var tips = dtnode.data.tooltip;
			    var titles = dtnode.data.title;
			    if(dtnode.data.key=="root"){
			    	tips="";
			    	titles="";
			    	popMessage(sipo.sysmgr.privilege.novalidrole);//"该角色为无效角色,请选择其他角色！"
		   			return;
			    }

				$("#roleNameDIV").html(titles);
				
				$("#roleDescDIV").html(tips);
				
				$("#hidkey").val(dtnode.data.key);//设置隐藏域的值
				
				//如果点击的角色节点是当前登录用户的角色，则保存按钮不可用
				setButtonStatus(dtnode.data.key);
				
				//设置动态资源树
				setDenamicTree(dtnode);
			
		}
	});
	
	//判断是否选择的节点是登录用户自己
	function isSelf(dtnode){
		var tmp = "";
		$.ajax({
				async:false,
				type:"get",
				url: contextPath+"/console/showPrivilegeAC!isMe.do",
				data:{roleId:dtnode.data.key,'wee.bizlog.modulelevel':'0400501'},
				dataType:"json",
				success:function(data,textStatus){
					if(data.flag){//自己角色
						tmp= true;
					}else{
						tmp= false;
					}
				}
			
		});
		
		return tmp;
	}
	
	//设置动态资源树
	function setDenamicTree(dtnode){
		//重新加载树结构之前先清空原树的结点
		$("#mytree").remove();
		$("#mytreebefore").after("<div id='mytree' class='shuxing_sys'></div>");
		var parentRoleId="";			
		if(isSelf(dtnode)){
			if(dtnode.data.parentId=="root"){
				parentRoleId=dtnode.data.key;
			}else{
				parentRoleId=dtnode.data.parentId;
				$("#modify").attr("disabled",false);
				
			}

		}else{
			parentRoleId=dtnode.data.parentId;
		}
		$("#mytree").dynatree({
		    minExpandLevel: 2,
			selectMode:3,
			persist: false,
			checkbox:true,
			initAjax : {
				root:"rootRes",
				url : contextPath+"/console/showPrivilegeAC!queryResourceListOfRole.do?parentRoleId="+parentRoleId+"&roleId="+dtnode.data.key,
				data:{'wee.bizlog.modulelevel':'0400501'}
			},
			onPostInit: function(isReloading, isError) {
			   //this.reactivate();
				 /*$.ajax({
						type:"get",
						url: contextPath+"/console/showPrivilegeAC!queryResourceListTreeOfRole.do",
						data:{roleId:dtnode.data.key},
						dataType:"json",
						success:function(data,textStatus){
							var myTree = $("#mytree").dynatree("getTree");
							if(data.resourceIds!=null){							    							    							    
							    
							    var len = data.resourceIds.length;
							    for(var i = 0;i<len;i++){
							    	myTree.selectKey((data.resourceIds)[i],true);
							    }
								
							}
						}
					
				});*/
			}
		});	
	}
	
	//设置点击按钮状态
	function setButtonStatus(varRoleId){	
		$.ajax({
			    async:false,
				type:"get",
				url: contextPath+"/console/showPrivilegeAC!isMe.do",
				data:{roleId:varRoleId,'wee.bizlog.modulelevel':'0400501'},
				dataType:"json",
				success:function(data,textStatus){
					if(data.flag){//自己角色，按钮不可用
						$("#modify").attr("disabled",true);
					}else{
						$("#modify").attr("disabled",false);
					}
				}
			
		});
	}

	//保存修改按钮
	$("#modify").click(
		function(){
		   var myTree = $("#mytree").dynatree("getTree");

		   if($("#hidkey").val()==""){
		   		popMessage(sipo.sysmgr.privilege.pleasechooserole);//"请先选择某个角色,然后再选择相应的资源！"
		   		return;
		   }else if($("#hidkey").val()=="root"){
		        popMessage(sipo.sysmgr.privilege.novalidrole);//"该角色为无效角色,请选择其他角色！"
		   		return;
		   }
		   
		   if(myTree.getNodeByKey("nores")!=null){
		   		popMessage(sipo.sysmgr.privilege.cannotsave);//该角色没有相关资源不能保存！
		   		return;
		   }
		   
		   var str = makePars(myTree);
		   $.ajax({
					type:"post",
					url: contextPath+"/console/showPrivilegeAC!updatePrivilege.do",
					data:{roleId:$("#hidkey").val(),resourceIds:[str],'wee.bizlog.modulelevel':'0400501'},
					dataType:"json",
					success:function(data,textStatus){
						popMessage(sipo.sysmgr.privilege.saveprivilegesuccess);//"保存权限成功!"
					},
					error:function(data,textStatus){
						popMessage(sipo.sysmgr.privilege.saveprivilegefailure);//"保存权限失败!"
					}
				
			});
		}
	);
});

//取消上次选中项
function unSelectMyTree(obj){		
    var arr = obj.getSelectedNodes(false);	    
    if(arr!=null){
	    if(arr.length>0){
	    	for(var j = 0;j<arr.length;j++){
	    		obj.selectKey(arr[j].data.key,false);
	    	}
	    	
	    }	
    }
}

//组合请求参数
function makePars(obj){
   var str="";
   var arr = obj.getSelectedNodes(false);
   if(arr==null){
   		return str;
   }else{
   		if(arr.length>0){
   			for(var i=0;i<arr.length;i++){
   				str=str+arr[i].data.key;
   				if(i!=arr.length-1)
   					str=str+",";
   			}
   			return str;
   		}
   		
   		return str;
   }	    
}

//屏蔽ie右键菜单
//function document.oncontextmenu() 
//{ 
//	return false; 
//} 
