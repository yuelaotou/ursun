$(function(){
    $("#openDate").datepicker({dateFormat:"yy-mm-dd"});
	
	$("#queryButton").click(function() {
	    var orgId = $("#orgId").val();
	    var orgName = $("#orgName").val();
	    var office = $("#office").val();
	    var openDate = $("#openDate").val();
    	$('#queryForm').submit();
	});

	$("#resetButton").click(function(){
		$("#orgId").val("");
		$("#orgName").val("");
		$("#office").val("");
		$("#openDate").val("");
		$("#orgId").focus();
	});
	
	$("#createButton").click(function(){
		window.location.href = "categoryAC!showCreateCategory.do";
	});
	
	$("#updateButton").click(function(){
	    var categoryId = $("#grid").getSelectData().rowid;
	    if (categoryId == '') {
	       alert("请选择一条记录！");
	    }else {
	       window.location.href = "categoryAC!showUpdateCategory.do?categoryId="+categoryId;
	    }
	});
	
	$("#deleteButton").click(function(){
	    var categoryId = $("#grid").getSelectData().rowid;
	    if (categoryId == '') {
	         alert("请选择一条记录！");
	    }else {
		     var categoryName = $("#grid").getSelectRowData().rowData[0]['categoryName'];
		     var categoryStat = $("#grid").getSelectRowData().rowData[0]['categoryStatus'];
		     if (categoryStat == "已发布") {
		        alert("已发布的专题不能直接删除，如要删除请先进行【取消发布】操作后再进行删除！");
		     }else {
		     	$.getJSON("categoryAC!isDelete.do?categoryId="+categoryId, function (data) {
			        if (data.msg == "success") {
			           if (confirm("确定要删除【"+categoryName+"】吗？")) {
					        window.location.href = "categoryAC!deleteCategory.do?categoryId="+categoryId;
					        window.parent.frames.left.location.reload();
					   }
			        }else {
			           alert("专题【"+categoryName+"】下有对应主题，请先删除主题！");
			        }
		     	});
		     }
		}
	});
	
	$("#releaseButton").click(function(){
	    var categoryId = $("#grid").getSelectData().rowid;
	    if (categoryId == '') {
	       alert("请选择一条记录！");
	    }else {
	     var categoryName = $("#grid").getSelectRowData().rowData[0]['categoryName'];
	     var categoryStat = $("#grid").getSelectRowData().rowData[0]['categoryStatus'];
	     if (categoryStat == "已发布") {
	       alert("该专题已发布。");
	     }else {
	       if (confirm("确定要发布【"+categoryName+"】吗？")) {
	          window.location.href = "categoryAC!releaseCategory.do?categoryId="+categoryId;
	          window.parent.left.location.reload();
	       }
	     }
	    }
	});
	
	$("#donotRelease").click(function(){
	    var categoryId = $("#grid").getSelectData().rowid;
	    if (categoryId == '') {
	       alert("请选择一条记录！");
	    }else {
	     var categoryName = $("#grid").getSelectRowData().rowData[0]['categoryName'];
	     var categoryStat = $("#grid").getSelectRowData().rowData[0]['categoryStatus'];
	     if (categoryStat == "未发布") {
	       alert("该专题未发布。");
	     }else {
	       if (confirm("确定要取消发布【"+categoryName+"】吗？")) {
	          window.location.href = "categoryAC!donotReleaseCategory.do?categoryId="+categoryId;
	          window.parent.left.location.reload();
	       }
	     }
	    }
	});
});
function showInfo(categoryId) {
	window.parent.detail.location.href = "categoryAC!queryByCategoryID.do?categoryId="+categoryId;
}