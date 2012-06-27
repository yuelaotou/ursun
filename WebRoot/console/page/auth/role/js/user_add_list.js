/*
 * ---------------------进入user_add_list 显示页面之后，将焦点设置在第一个查询输入框中
---------------------------
 */
$(document).ready(function(){
 $("#input_userName").focus();
 })



var roleId=null;
var unitId=null;
// $.myInitDynatree();
// ---- 自定义扩展方法myInitDynatree，用于构建组织机构树  ---------------------------------
  
$(function() {
    //重新创建树结构
	    $("#unit_tree").dynatree( {
		     persist : false, //将结点展开结构图放在cookie中
		     selectMode : 2,
		     autoFocus: false,
		     minExpandLevel : 3,
		     initAjax : {
		      url : contextPath+"/console/showUnitAC!queryUnitTree.do",
		      root:"rootUnit",
		      data:{'wee.bizlog.modulelevel':'0400101'}
		     },
		     onClick: function(dtnode,event) {
		     	if(dtnode.getEventTargetType(event)=="expander"){
					dtnode.toggleExpand();
					return false;
				}
		     	unitId=dtnode.data.key;
		     	roleId=$("#roleId").val();
				var url=contextPath+"/console/showRoleAC!init.do?ftype=add&unitId="+unitId+"&role.roleId="+roleId+"&wee.bizlog.modulelevel=0400205";
				window.location.href=url;
				},
		
			onPostInit : function(dtnode, event) {
				$("#unit_tree").dynatree("getTree").selectKey($("#unitId").val(),true);
		 	}													
	    });
  });