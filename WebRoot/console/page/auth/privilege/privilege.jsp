<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><s:text name="auth.title"/></title> 
		<n:base />
		<n:jquery components="tree" />
		<script type="text/javascript" src="<n:path path="/console/page/auth/privilege/js/privilege.js"/>"></script>
		<link rel="stylesheet" type="text/css" href='<n:path path="/platform/common/css/component.css"/>' />
	</head>

	<body>
		<div id="full"><!--full start-->
		<div id="container_middle"><!--container_middle start-->
			
	
		<div class="Fenxi_center"><!--Fenxi_center start-->
        	<div class="fenxi_Contentbox_sys">
                <div id="con_two_1"></div>
                <div id="con_two_2" style="display:none"></div>
                <div id="con_two_3" style="display:none"></div>
                <div id="con_two_4">
                	<div class="colume_left_one">
                    	<h3 class="column_title_sys column_title1"><s:text name="auth.rolelabel"/></h3>
                        <div class="shuxing1 shuxing2" id="tree">
                        	
                        </div>	
                    </div>
                    
                    <div class="column_left1">
                    	<h3 id="mytreebefore" class="column_title_sys"><s:text name="auth.resourcelabel"/></h3>
                        <div class="shuxing_sys" id="mytree" />
                        	<h1><s:text name="auth.chooseleftnode"/></h1>
                        </div>	
                    </div>
                    
                    <div class="column_left1 column_left_two1">
                    	<h3 class="column_title2 column_title_three1"><s:text name="auth.roleinfo"/></h3>
                        <div class="user_info">
                        	<p><strong><s:text name="auth.rolelabelname"/>：</strong><font id="roleNameDIV"></font></p>
                            <p style="line-height:24px; padding-top:20px;"><strong><s:text name="auth.rolelabeldesc"/>：</strong><font id="roleDescDIV"></font></p>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <div class="button_a">
                    	<table align="center"><tr><td>							
							<n:button id="modify" buttonClass="border-blue" value="%{getText('auth.savebutton.name')}"/>
						</td></tr></table>                       
						<s:hidden id="hidkey" name="hidkey" value=""></s:hidden>
                    </div>
                </div>
                <div id="con_two_5" style="display:none"></div>
                <div id="con_two_6" style="display:none"></div>
                <div id="con_two_7" style="display:none"></div>
                <div id="con_two_8" style="display:none"></div>
                <div id="con_two_9" style="display:none"></div>
                <div id="con_two_10" style="display:none"></div>
                <div id="con_two_11" style="display:none"></div>
            </div>
        </div><!--Fenxi_center end-->
		</div><!--container_middle end-->			
		<n:footer/>
	</body>
</html>