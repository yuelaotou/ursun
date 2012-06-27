<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Window</title>
		<n:base/>
		<n:jquery components="form"/>
		<script type="text/javascript" src="<n:path path="common/js/common.js"/>"></script>
		<script type="text/javascript" language="javascript">
$(document).ready(
	function()
	{
		//定义普通窗口
		var testWindow=$("#content").createWindow({title:"<font color='red'>测试</font>",
		buttons:[{html:'<a href="#" id="submitButton1"><img src="images/2010-03-19_225711.jpg" /></a>'}],
		top:150,left:300,width:400,height:200});
		//打开窗口
		$('#windowOpen').click(function(){
				testWindow.showWindow(this)
				});	
		//提交
		$('#submitButton1').click(function(){
			testWindow.closeWindow(this);
		});
				
		//定义模态窗口
		var testWindow1=$("#content1").createWindow({title:"测试模态窗口",modal:true,"z-index":1003,
			close:function(){$("#content1").clearValidationPrompt() }	
		});
		//打开模态窗口
		$('#windowOpenModal').click(function(){
				testWindow1.showWindow(this)
				});		
		//提交
		$('#submitButton2').click(function(){
			if($("#content1").validation()){
				popMessage("sdfsdf",null,null,false,null,null,1000);
			}
		});
}
);
</script>

	</head>
	<body style="margin: 100px;">

		<a href="#" id="windowOpen">普通窗口测试中...</a>
		<a href="#" id="windowOpenModal">模态窗口测试中...</a>
		<div id="content" name="content" class="tranlate_list">
                <table width="100%">
                    <tr>
                        <td style="text-align:right;">检索文献库名称：</td>
                        <td><input id="patentDbName" name="patentDbName" type="text" class="validate[required]"/></td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">描述信息：</td>
                        <td><input type="text"/></td>
                    </tr>
                </table>
        </div>		
		
		<div id="content1" class="tranlate_list">
                <table width="100%">
                    <tr>
                        <td style="text-align:right;">检索文献库名称：</td>
                        <td><input type="text" id="patentDbName" name="patentDbName"  class="validate[required]"/></td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">描述信息：</td>
                        <td><select name="sport" id="sport"  class="validate[required]" >
					<option value="">Choose a sport</option>
					<option value="option1">Tennis</option>
					<option value="option2">Football</option>
					<option value="option3">Golf</option>
				</select></td>
                    </tr>
                </table>
                <div style="padding-top:10px;" ><a href="#" id="submitButton2"><img src="images/2010-03-19_225711.jpg" /></a>&nbsp;&nbsp;<a href="#" class="windowClose"><img src="images/2010-03-19_225722.jpg" /></a></div>
        </div>
       <n:footer/>
	</body>
</html>