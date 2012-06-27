<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <n:base />
	<n:jquery components="form" />
    <title><s:text name="bizlog.title"></s:text></title>
    <script type="text/javascript">
	$(function(){
		
		//删除按钮
		$("#deleteBizLog").click(function(){
			var id = $("#id").val();
			if(confirmMessage(sipo.sysmgr.bizlog.areyousuretodeletethisbizlog)){
				$.post(contextPath+"/platform/auth/showBizLogAC!deleteBizLog.do",
				{'logIds':id,'wee.bizlog.modulelevel':'0400604'}
				,function(data){
					if (data.flag == "success"){
						popMessage(sipo.sysmgr.bizlog.deletebizlogsuccess,null,function(){
							window.location.href=contextPath+"/platform/auth/showBizLogAC!queryBizLogList.do?wee.bizlog.modulelevel=0400602";
						});				
					}else{						
						popMessage(sipo.sysmgr.bizlog.deletebizlogerror,null,function(){
							window.location.href=contextPath+"/platform/auth/showBizLogAC!queryBizLogList.do?wee.bizlog.modulelevel=0400602";
						});
					}					
				},'json');
    		}
		});
		
		//关闭按钮
		$("#closeBizLog").click(function(){
			window.location.href=contextPath+"/platform/auth/showBizLogAC!queryBizLogList.do?wee.bizlog.modulelevel=0400602";
		});
	})
		
	</script>
  </head>
  
  <body>
  	<div id="full"><!--full start-->
		<div id="container_middle"><!--container_middle start-->
  		<div class="Fenxi_center"><!--Fenxi_center start-->
  			<s:hidden id="id" value="%{bizlogrow.id}"></s:hidden>
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
                           	 	<div class="cognate_name"><s:text name="bizlog.detail"></s:text></div>	
                            </div>
                        </div>
                        <div class="cognate_conter">
                        	<div class="cognate_box1">
                        		<div class="reg">
                        			<table class="reg_table1">
		                                <tr>
		                                    <td style="text-align:right;"><s:text name="bizlog.operatorname"></s:text>：</td>
		                                    <td><s:property value="bizlogrow.loginName"/></td>
		                                </tr>
		                                <tr>
		                                    <td style="text-align:right;"><s:text name="bizlog.operatorrole"></s:text>：</td>
		                                    <td><s:property value="bizlogrow.roleName"/></td>
		                                </tr>
		                                <tr>
		                                    <td style="text-align:right;"><s:text name="bizlog.operatorip"></s:text>：</td>
		                                    <td><s:property value="bizlogrow.ip"/></td>
		                                </tr>
		                                <tr>
		                                    <td style="text-align:right;"><s:text name="bizlog.operatordate"></s:text>：</td>
		                                    <td><s:date name="bizlogrow.operationDate" format="yyyy-MM-dd HH:mm:ss ms"/></td>
		                                </tr>
		                                <tr>
		                                    <td style="text-align:right;"><s:text name="bizlog.function"></s:text>：</td>
		                                    <td><s:property value="bizlogrow.moduleLevel1"/></td>
		                                </tr>
		                                <tr>
		                                    <td style="text-align:right;"><s:text name="bizlog.description"></s:text>：</td>
		                                    <td><s:property value="bizlogrow.detail" escape="false"/></td>
		                                </tr>
		                            </table>
                        			<div style="text-align:center; padding-bottom:14px;">
			                         	<table align="center" width="120"><tr><td>
											<n:button id="deleteBizLog" buttonClass="border-blue" value="%{getText('bizlog.deletelog')}"/>
											<n:button id="closeBizLog" buttonClass="gray" value="%{getText('bizlog.closelog')}"/>
										</td></tr></table>
			                         </div>
                        		
								</div>
					</div>	
                        </div>
                        <p class="cognate_di"></p>		
                    </div>
                </div>
                <div id="con_two_11" style="display:none"></div>
            </div>
        </div><!--Fenxi_center end-->
       <n:footer/>  
	</div>	
	</div><!--full end-->
  </body>
</html>
