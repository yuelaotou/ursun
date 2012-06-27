<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <n:base />
	<n:jquery components="form" />
    <title><s:text name="resource.maintainurl"></s:text></title>
	<script type="text/javascript" src="<n:path path="/console/page/auth/resource/js/url_editor.js"/>"></script>
	<link rel="stylesheet" type="text/css" href='<n:path path="/platform/common/css/component.css"/>' />
  </head>
  
  <body>
  	<div id="full"><!--full start-->
		<div id="container_middle"><!--container_middle start-->
			
		
		
		<div class="Fenxi_center"><!--Fenxi_center start-->
        	<div class="fenxi_Contentbox">
                <div id="con_two_1"></div>
                <div id="con_two_2" style="display:none"></div>
                <div id="con_two_3" style="display:none"></div>
                <div id="con_two_4" style="display:none"></div>
                <div id="con_two_5" style="display:none"></div>
                <div id="con_two_6" style="display:none"></div>
                <div id="con_two_7" style="display:none"></div>
                <div id="con_two_8" style="display:none"></div>
                <div id="con_two_9" style="display:none"></div>
                <div id="con_two_10">
                	<div class="cognate">
                    	<div class="cognate_top">
                        	<div class="cognate_one">
                           	 	<div class="cognate_name align-l" id="info"></div>	
                            </div>
                        </div>
                        <div class="cognate_conter">
                        	<div class="cognate_box1">
                        		<div class="reg">
								<s:form id="urlForm" name="urlForm" action="">
			  						<s:hidden id="type" name="urlrow.type" value="%{urlrow.type}"></s:hidden>
									<s:hidden id="url_id" name="urlrow.urlId" value="%{urlrow.urlId}"></s:hidden>
									<br>               
			                       	<div class="notice">                      	
			                           <s:text name="url.content"></s:text>：                            
			                           	<s:textfield id="content" name="urlrow.content" value="%{urlrow.content}" theme="simple"
									 			key="url.content"  cssClass="validate[required,length[1,150]],userDefined[checkValue] form_input_d" >
									 	</s:textfield><font color="red">*</font>
			                        </div>
			                        <br>
			                        <div class="notice">                       
			                            <div style="width:365px;" class="align-l"><s:text name="url.description"></s:text>：</div>                      
			                            <div style="float:left; width:598px;">
			                            	<s:textarea id="description" name="urlrow.description" value="%{urlrow.description}"  theme="simple"
													key="url.description"  cssClass="validate[length[0,100]] textarea_a1">
											</s:textarea></div>
			                            <div class="clear"></div>
			                         </div>
			                         <div style="text-align:center; padding-bottom:14px;">
			                         	<table align="center" width="120"><tr><td>                   
			                        		<n:button id="url_submit" buttonClass="border-blue" value="%{getText('resource.save')}"/>
			                            	<n:button id="url_return" buttonClass="gray" value="%{getText('resource.cancel')}"/>
			                            </td></tr></table>
			                         </div>
			                    </s:form>      
								</div>
							</div>
                        </div>
                        <p class="cognate_di"></p>		
                    </div>
                </div>
                <div id="con_two_11" style="display:none"></div>
            </div>
        </div><!--Fenxi_center end-->
		
		
  
	</div>	
	<n:footer/>
	</div><!--full end-->
  </body>
</html>
