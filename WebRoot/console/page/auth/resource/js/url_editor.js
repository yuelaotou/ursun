/*
 * ---------------------进入url_edtor 增加页面之后，将焦点设置在第一个输入框中
---------------------------
 */
$(document).ready(function(){
 $("#content").focus();
 })



$(function(){
	var type = $("#type").val();

	if (type == "modify"){
		$("#info").text("修改URL");
	}else{
		$("#info").text("新增URL");
	}
	$("#url_submit").click(function(){
		if($("#urlForm").validation()){
			submit_(type);
		};
		//$('#urlForm').submit();
	});
	$("#url_return").click(function(){		
			window.location.href=contextPath+"/console/showResourceAC!queryUrlList.do?wee.bizlog.modulelevel=0400305";
	});
});

	function submit_(type){		
		if (type == "modify"){
			var url_id = $('#url_id').val();
			var content = $('#content').val();
			var description = $('#description').val();			
			//if(description.match(/^[0-9a-zA-Z\u4e00-\u9fa5]*$/)== null)
			//{
			//	popMessage(sipo.sysmgr.resource.urldescribeonlyecn);	
			//	return;
			//}
			$.post("maintainResourceAC!updateUrl.do"
				,{'urlrow.urlId':url_id,'urlrow.content':content,
				'urlrow.description':description,
				'wee.bizlog.modulelevel':'0400307'}
				,function(data){
					if (data.flag == "success"){						
						popMessage(sipo.sysmgr.resource.editurlsuccess,null,function(){
							window.location.href=contextPath+"/console/showResourceAC!queryUrlList.do?wee.bizlog.modulelevel=0400305";
						});
					}
											
			},'json');
		}else{
			var content = $('#content').val();
			var description = $('#description').val();
			//if(description.match(/^[0-9a-zA-Z\u4e00-\u9fa5]*$/)== null)
			//{
			//	popMessage(sipo.sysmgr.resource.urldescribeonlyecn);	
			//	return;
			//}
			$.post("maintainResourceAC!addUrl.do"
				,{'urlrow.content':content,
				'urlrow.description':description,
				'wee.bizlog.modulelevel':'0400306'}
				,function(data){
					if (data.flag == "success"){				
						popMessage(sipo.sysmgr.resource.addurlsuccess,null,function(){
							window.location.href=contextPath+"/console/showResourceAC!queryUrlList.do?wee.bizlog.modulelevel=0400305";
						});
					}
											
			},'json');
		}
	}
	
	//校验URL输入合法性
function checkValue(val) {
	var result = {};
  	$.ajax({
     	type: "POST",
      	url: contextPath+"/console/maintainResourceAC!cofirmRightUrl.do?wee.bizlog.modulelevel=0400311",
      	//data: "urlrow.content="+encodeURI(val)+"&wee.bizlog.modulelevel=0400311",
      	data : $("#urlForm").formSerialize(),
      	async:false,
      	success: function(flag){
      	flag=eval("("+flag+")");
	      	if(flag.exist=="error"){
	      		result.value = true;
	      		result.alertText = sipo.sysmgr.resource.urliserror;	      		
	      		//$('#rid').focus();
	      	}
      	}
    });
	return result;
}
