<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><s:text name="menu.title"/></title>
		<n:base/>
		<n:jquery components="tree,form" />
		<script type="text/javascript" src="<n:path path="/console/page/auth/menu/js/menu.js"/>"></script>	
		<script type='text/javascript'>
		  var time=new Date();
    	</script>
		<link rel="stylesheet" type="text/css" href='<n:path path="/platform/common/css/component.css"/>' />
	</head>
	<body>
		<div id="full"><!--full start-->
		<div id="container_middle"><!--container_middle start-->
		<s:hidden id="menu_id"></s:hidden>  
		<!-- 菜单树及菜单信息 -->
		<div class="Fenxi_center">
			<!--Fenxi_center start-->
			<div class="fenxi_Contentbox_sys">
				<div id="con_two_7">
					<div class="column_left align-l">
						<h3 class="column_title">
							<s:text name="menu.name"/>
						</h3>
						<div class="shuxing1" id="tree">
						</div>
					</div>
					<div class="column_right">
						<h3 class="column_title column_title_two">
							<s:text name="menu.information"/>
						</h3>
						<div id="info" class="align-l"></div>
					</div>
					<div class="clear"></div>
				</div>
			</div><!--Fenxi_center end-->
		</div>
		
		<!-- 右键菜单信息 -->
		<ul id="treeMenu" class="contextMenu" style="font-size:12px;text-align:left">
			<li>
				<a href="#new"><s:text name="menu.add"></s:text></a>
			</li>
			<li>
				<a href="#edit"><s:text name="menu.edit"></s:text></a>
			</li>
			<li>
				<a href="#delete"><s:text name="menu.delete"></s:text></a>
			</li>
			<li>
				<a href="#bind"><s:text name="menu.resourcebind"></s:text></a>
			</li>
			<li>
				<a href="#unbind"><s:text name="menu.resourcedissovle"></s:text></a>
			</li>
		</ul>
		<!-- 新建菜单窗口 -->
		<div class="tranlate" id="new_dialog" style="display: none"><!--tranlate start-->
			<s:hidden id="parentId" name="menu.parentId" value=""></s:hidden>        
	        <div class="tranlate_conter" style="border-top:1px solid #CCC;">
	            <div class="tranlate_box" style="padding:0px 10px;">
	            	<div style="border-bottom:1px solid #CCC; padding-bottom:10px;">
	                    <table width="100%" class="tranlate_box_b">
	                        <tr>
	                            <td colspan="2" ><strong><s:text name="menu.parentinformation"/></strong></td>
	                        </tr>
	                        <tr>
	                            <td style="text-align:right;"><s:text name="menu.name"></s:text>：</td>
	                            <td><input id="parentName" disabled="true" class="input_a "></td>
	                        </tr>
	                        <tr>
	                            <td style="text-align:right;"><s:text name="menu.description"></s:text>：</td>
	                            <td><s:textarea id="parentDescription" rows="3" disabled="true" theme="simple" cssClass="textarea_a"></s:textarea></td>
	                        </tr>
	                    </table>
	                </div>
	                <form id="newForm" onsubmit="return true;" method="post">
	                <div style="padding-top:10px;">
	                    <table width="100%" class="tranlate_box_b">
	                         <tr>
	                            <td colspan="2" ><strong><s:text name="menu.menuinfo"/></strong></td>
	                        </tr>
	                        <tr>
	                            <td style="text-align:right;"><s:text name="menu.name"></s:text>：</td>
	                            <td><input type="text" id="newName" name="menu.id" class="validate[required,length[1,20]],userDefined[checkValue] input_a">&nbsp;<font color="red">*</font>
	                            </td>
	                        </tr>
	                        <tr>
								<s:hidden id="type" value="1"></s:hidden>
								<td style="text-align:right;"><s:text name="menu.nodetype"></s:text>：</td>
								<td>
									<input class="validate[required] radio" type="radio" id="folder"  name="menu.folder" value="1" checked onclick="hideUrl(this.value);">
									<s:text name="menu.nodefolder"></s:text>
									<input class="validate[required] radio" type="radio" id="leaf" name="menu.folder" value="0" onclick="hideUrl(this.value);">
									<s:text name="menu.nodeleaf"></s:text>
								</td>
							</tr>
							<tr id="newurl" style="display:none">
								<td style="text-align:right;"><s:text name="menu.guidance"></s:text>：</td>
								<td><input type="text" id="newUrl" name="menu.url" class="validate[required,length[1,80]] input_a">&nbsp;<font color="red">*</font></td>
							</tr>
							<tr>
								<td style="text-align:right;"><s:text name="menu.sort"></s:text>：</td>
								<td><input id="newSequence" name="menu.sequence" class="validate[required,custom[onlyNumber],length[1,10]] input_a">&nbsp;<font color="red">*</font></td>
							</tr>
							<tr>
								<td style="text-align:right;"><s:text name="menu.description"></s:text>：</td>
								<td><s:textarea id="newDescription" rows="3" name="menu.description" theme="simple" cssClass="validate[length[0,40]],userDefined[checkValue] textarea_a"></s:textarea>
								</td>
							</tr>
							
	                    </table>
	                </div>
					<div style="padding-top:10px; text-align:center;">
						<table align="center" width="120"><tr><td>
	                		<n:button id="newSubmit" value="%{getText('userAccount.submit')}" buttonClass="border-blue"/>
							<n:button id="newCancel" value="%{getText('userAccount.close')}" buttonClass="gray"/>
						</td></tr></table>
	                </div>
	                </form>
	            </div>
	        </div>
	        <p class="tranlate_di"></p>
	    </div><!--tranlate end--> 
		
		
		
		
		
		<!-- 修改菜单窗口 -->	
		<div class="tranlate" id="edit_dialog" style="display: none"><!--tranlate start--> 
			<s:hidden id="menuId" name="menu.id" value=""></s:hidden>
			<form id="editForm" onsubmit="return true;" method="post">      
	        <div class="tranlate_conter" style="border-top:1px solid #CCC;">
	            <div class="tranlate_box" style="padding:0px 10px;">
	            	
	                <div style="padding-top:10px;">
	                    <table width="100%" class="tranlate_box_b">
	                         <tr>
	                            <td colspan="2" ><strong><s:text name="menu.menuinfo"/></strong></td>
	                        </tr>
	                        <tr>
	                            <td style="text-align:right;"><s:text name="menu.name"></s:text>：</td>
	                            <td><input id="editName" name="menu.name" class="validate[required,length[1,20]],userDefined[checkValue] input_a"><font color="red">*</font></td>
	                        </tr>
	                        <tr id="editurl">
								<td style="text-align:right;"><s:text name="menu.guidance"></s:text>：</td>
								<td><input id="editUrl" name="menu.url" class="validate[required,length[1,80]] input_a"><font color="red">*</font></td>
							</tr>
							<tr>
								<td style="text-align:right;"><s:text name="menu.sort"></s:text>：</td>
								<td><input id="editSequence" name="menu.sequence" class="validate[required,custom[onlyNumber],length[1,10]] input_a"><font color="red">*</font></td>
							</tr>
							<tr>
								<td style="text-align:right;"><s:text name="menu.description"></s:text>：</td>
								<td><s:textarea id="editDescription" rows="3" name="menu.description" theme="simple" cssClass="validate[length[0,40]],userDefined[checkValue] textarea_a"></s:textarea></td>				
							</tr>
	                    </table>
	                </div>
					<div style="padding-top:10px; text-align:center;">
						<table align="center" width="120"><tr><td>
	                	<n:button id="editSubmit" value="%{getText('userAccount.submit')}" buttonClass="border-blue"/>
						<n:button id="editCancel" value="%{getText('userAccount.close')}" buttonClass="gray"/>
						</td></tr></table>
	                </div>
	                
	                
	                
	                
	            </div>
	        </div>
	        <p class="tranlate_di"></p>
	        </form>
	    </div><!--tranlate end-->
		
		
		<!-- 显示资源树窗口 -->
		<div class="tranlate_two" id="resource_dialog" style="display:none" >
			<s:hidden id="resId" name="resourceId" value=""></s:hidden>
	        <p class="tranlate_two_left"></p>
	        <div class="tranlate_two_conter1">
	 			<div id="resource"  style="padding-top:20px;"></div>
	        </div>
	        <p class="tranlate_two_di"></p>	
	    </div>
	    <div class="clear"></div>
		
		
		</div><!--container_middle end-->
		<n:footer/>
		</div>
	</body>
</html>