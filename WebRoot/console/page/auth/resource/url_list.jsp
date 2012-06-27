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
    <script type="text/javascript" src="<n:path path="/console/page/auth/resource/js/url_list.js"/>"></script>
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
                           	 	<div class="cognate_name align-l"><s:text name="resource.maintainurl"></s:text></div>	
                            </div>
                        </div>
                        <div class="cognate_conter">
                        	<div class="cognate_box1">
                        		<div class="cognate_input">                              	
                                    <div>
                                    	<table align="right"><tr><td>																					
											<n:button id="closeUrl" value="%{getText('url.close')}" buttonClass="gray" />
										</td></tr></table>										
                                    </div>
                                    <div class="clear"></div>
                                </div>
                        		                           	
                                <div class="cognate_talbe">
                                    <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
                                    	<tr>
                                            <td  colspan="3" class="tabel_two"><strong><a href="#"><img src="<n:path path="/platform/common/images/auth/jianhao.gif"/>" id="createUrl"/></a> <s:text name="url.add"></s:text></strong></td>                                     
                                       	</tr>
                                    </table>
                                     <n:grid dataSource="urlList" id="grid" name="grid" width="100" 
											cellSpacing="1" cellPadding="0" cssClass="gridTable" theme="ajax"
											action="maintainResourceAC!queryUrlList.do"
											pagination="pagination"
											>
											<n:gridheader>
												<n:selectall isSingle="false" width="10"/>												
												<n:columnmodel key="url.content" width="40" sortable="true" sortColumn="content"/>
												<n:columnmodel key="url.description" width="40" sortable="false" sortColumn="description"  />												
												<n:columnmodel key="url.operation" width="12" ></n:columnmodel>
											</n:gridheader>
											<!-- 定义列表数据项 -->
											<n:gridtbody>
												<n:selectcolumn dataField="urlId" />
												<n:textcolumn dataField="content"  cssClass="grid_wordbreak"/>
												<n:textcolumn dataField="description" cssClass="grid_wordbreak" />
												<n:customcolumn dataField="urlId" width="12"
																renderTo="showDetailInfo" />
											</n:gridtbody>
											<n:gridfooter>
												<n:button id="deleteUrls" buttonClass="gray" value="%{getText('url.delete')}"/>
												<!-- 定义列表分页 -->
												<n:gridpager />
											</n:gridfooter>
										</n:grid>
                            	</div>
                        	</div>	
                        </div>
                        <p class="cognate_di"></p>		
                    </div>
                </div>
                <div id="con_two_11" style="display:none"></div>
            </div>
        </div><!--Fenxi_center end-->
  		
		</div><!--container_middle end-->			
		<n:footer/>
	</div><!--full end-->
  </body>
</html>
