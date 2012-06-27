<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<n:base />
		<n:jquery components="tree,form" />
		<title><s:text name="resource.index"></s:text></title>
		<script type="text/javascript" src="<n:path path="/console/page/auth/resource/js/resource_tree.js"/>"></script>
		<link rel="stylesheet" type="text/css" href='<n:path path="/platform/common/css/component.css"/>' />
	</head>
	<body>
		<div id="full"><!--full start-->
		<div id="container_middle"><!--container_middle start-->
		<s:hidden id="activeResourceId"></s:hidden>
<!--新建-->
	<div class="tranlate" id="create" style="display:none"><!--tranlate start-->
		<form action="" id="resourceForm">
			<s:hidden id="type" name="resourceparent.type" value="%{resourceparent.type}"></s:hidden>	
			<s:hidden id="parent_id" name="resourcerow.parentId" value="%{resourceparent.parentId}"></s:hidden>
			<s:hidden id="resource_id" name="resourcerow.resourceId" value="%{resourcerow.resourceId}"></s:hidden>
        
        <div class="tranlate_conter" style="border-top:1px solid #CCC;">
            <div class="tranlate_box" style="padding:0px 10px;">
            	<div style="border-bottom:1px solid #CCC; padding-bottom:10px;">
                    <table width="100%" class="tranlate_box_b">
                        <tr>
                            <td colspan="2" ><strong><s:text name="resource.parent"/></strong></td>
                        </tr>
                        <tr>
                        	<td style="text-align:right;"><s:text name="resource.title"></s:text>：</td>                           
                            <td>
                            	<s:textfield id="p_title" name="resourceparent.title" 
                            			cssClass="input_a"  theme="simple" disabled="true"></s:textfield>
							</td>
                        </tr>
                        <tr>
                        	<td style="text-align:right;"><s:text name="resource.describe"></s:text>：</td>
                        	<td>
                        		<s:textfield id="p_description" name="resourceparent.description" 
                        				cssClass="input_a" key="resource.describe" disabled="true"></s:textfield>
							</td>                          
                        </tr>
                    </table>
                </div>
                <div style="padding-top:10px;">
                    <table width="100%" class="tranlate_box_b">
                         <tr>
                            <td colspan="2" ><strong><s:text name="resource.detail"/></strong></td>
                        </tr>
                        <tr>
                        	<td style="text-align:right;"><s:text name="resource.title"></s:text>：</td>						
							<td><s:textfield id="title" name="resourcerow.title" value="%{resourcerow.title}" theme="simple"
									key="resource.title" cssClass="validate[required,length[1,20]],userDefined[checkValue] input_a" 
									></s:textfield><font color="red">*</font>
							</td>
                        </tr>
                        <tr id="resrid">
							<td style="text-align:right;"><s:text name="resource.rid"></s:text>：</td>						
							<td><s:textfield id="rid" name="resourcerow.rid" value="%{resourcerow.rid}" theme="simple"
										key="resource.rid" cssClass="validate[length[0,20]],userDefined[checkRidValue],userDefined[checkRidRename] input_a" 
										></s:textfield>
							</td>
						</tr>
						<tr id="resleaf" >
							<td style="text-align:right;"><s:text name="resource.isleaf"></s:text>：</td>	
							<td>
								<s:radio id="is_leaf" name="resourcerow.isLeaf"  onclick="isLeaf()" theme="simple"
						  				list="#{'1':getText('resource.yes'),'0':getText('resource.no')}" value="'0'"></s:radio>								
							</td>
						</tr>
                        <tr id="resurl" style="display:none">
							<td style="text-align:right;"><s:text name="resource.urlbind"></s:text>：</td>
							<td>
								<s:checkbox id="bindurl" name="bindurl" theme="simple"></s:checkbox>
							</td>
						</tr>
						<tr id="bindurllist" style="display:none" >
							<td style="text-align:right;"><s:text name="resource.urllist"></s:text>：</td>	
							<td onclick="selectUrl()">
								<s:textfield id="chooseurl" name="resourcerow.url" value="%{resourcerow.url}" theme="simple"
										 cssClass="input_a" 
										onkeydown="javascript:return false;"></s:textfield>
								<s:hidden id="chooseurlid" name="chooseurlid" />
							</td>
						</tr>
						<tr>
							<td style="text-align:right;"><s:text name="resource.describe"></s:text>：</td>	
							<td>
								<s:textarea id="describe" name="resourcerow.description" 
									value="%{resourcerow.description}" key="resource.describe" 
									cssClass="validate[length[0,40]] textarea_a"></s:textarea>
							</td>
						</tr>                      
                    </table>
                </div>
				<div style="padding-top:15px; text-align:center;">
					<table align="center" width="120"><tr><td>
						<n:button id="submitButton" buttonClass="border-blue" value="%{getText('resource.save')}"/>
						<n:button id="windowClose" buttonClass="gray" value="%{getText('resource.cancel')}"/>
					</td></tr></table>
                </div>
            </div>
        </div>
        <p class="tranlate_di"></p>
       </form>
    </div><!--tranlate end-->
    
    <div id="selectUrlList" style="display:none" class="tranlate"><!--tranlate start-->
    	<div class="abstract_two" >
            <div class="abstract_sysurl">    	
		    	<table width="628px" border="0" cellpadding="0" cellspacing="0" bgcolor="#ffffff" class="tranlate_box_b">
		    		<tr class="a_left_title"><td width="7%"><s:text name="url.number"></s:text></td><td><s:text name="url.content"></s:text></td><td><s:text name="url.description"></s:text></td>
		    		</tr>
		    		<s:iterator value="urlList" status="st">
		    			<s:if test="#st.odd">
				            <tr onclick="clickRow('<s:property value='urlId'/>')">
				               	<td bgcolor="#f3f3f3"  style="padding-left:10px;width:20px" >
				               		<input type="radio" name="radiourl" value="<s:property value='urlId'/>"/>${st.index+1 }
				               	</td>
				               	<td bgcolor="#f3f3f3" style="padding-left:10px;word-wrap: break-word;word-break:break-all;width:60%"><s:property value='content'/></td>
				               	<td bgcolor="#f3f3f3" style="padding-left:10px;word-wrap: break-word;word-break:break-all;"><s:property value='description'/></td>		               	
				           	</tr>
			            </s:if>
			            <s:else>
			               	<tr onclick="clickRow('<s:property value='urlId'/>')">
			                     <td bgcolor="#ffffff" style="padding-left:10px;width:20px">
			                     	<input type="radio" name="radiourl" value="<s:property value='urlId'/>"/>${st.index+1 }
			                     </td>
			                     <td bgcolor="#ffffff" style="padding-left:10px;word-wrap: break-word;word-break:break-all;width:60%"><s:property value='content'/></td>
			                     <td bgcolor="#ffffff" style="padding-left:10px;word-wrap: break-word;word-break:break-all;"><s:property value='description'/></td>
			                 </tr>
			           	</s:else>
		    		</s:iterator>
		    	</table>
		    </div>
        </div>		    
    	<p class="tranlate_di"></p>
    </div><!--tranlate end-->


		<ul id="myMenu" class="contextMenu">
			<li class="add">
				<a href="#add"><s:text name="resource.add"></s:text></a>
			</li>
			<li class="edit">
				<a href="#edit"><s:text name="resource.edit"></s:text></a>
			</li>
			<li class="delete">
				<a href="#delete"><s:text name="resource.delete"></s:text></a>
			</li>			
		</ul>
		
		
		<div class="Fenxi_center"><!--Fenxi_center start-->
        	<div class="fenxi_Contentbox_sys">
                <div id="con_two_1">
                </div>
                <div id="con_two_2" style="display:none"></div>
                <div id="con_two_3" style="display:none"></div>
                <div id="con_two_4" style="display:none"></div>
                <div id="con_two_5" style="display:none"></div>
                <div id="con_two_6">
                	<div class="column_left align-l">
                    	<h3 class="column_title"><s:text name="resource.tree"></s:text></h3>
                        <div class="shuxing1 align-l" id="resource-tree" >
                        	
                        </div>	
                    </div>
                    <div class="column_right">
                    	<h3 class="column_title column_title_two align-l"><s:text name="resource.detail"></s:text></h3>
                        <div id="contentFrame">
						</div>
                        
                        <div class="sc_button">
                        	<table align="center"><tr><td>
                        		<n:button id="maintainUrl" buttonClass="gray" value="%{getText('resource.maintainurl')}"/>
                        	</td></tr></table>                   	
                        </div>
                    </div>
                    <div class="clear"></div>	
                </div>
                <div id="con_two_7" style="display:none"></div>
                <div id="con_two_8" style="display:none"></div>
                <div id="con_two_9" style="display:none"></div>
                <div id="con_two_10" style="display:none"></div>
                <div id="con_two_11" style="display:none"></div>
            </div>
        </div><!--Fenxi_center end-->
		
		</div><!--container_middle end-->
		<n:footer/>
		</div><!--full end-->
	</body>
</html>