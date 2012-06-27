<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="gridId">
<s:iterator value="employeeList">
	<div>
		<h2 class="recordCss">
			<font size="-1">[<s:property value="empno" />] </font>&nbsp;
			<s:text name="ename" />
			<a href="javascript:alert('<s:property value="ename" />');"><s:property
					value="ename" /> </a>
			<br />
		</h2>
		<span class="PatentAuthorBlock"> <font size="-1"><s:text
					name="job" />:<s:property value="job" /> </font>&nbsp; <font size="-1"><s:text
					name="hiredate" />:<s:property value="hiredate" /> </font>&nbsp; <font
			size="-1"><s:text name="sal" />:<s:property value="sal" /> </font> <br />
		</span>
	</div>
</s:iterator>
<table width="100%">
	<tr>
		<td>
			<div>
				<n:pages id="example_grid" pagination="%{pagination}" theme="picText" styleClass = "page" showTotalRecord="true"
				  action="pageAC!queryEmployeeList.do" target="gridId" ajax="true" />
			</div>
		</td>
	</tr>
</table>
</div>
