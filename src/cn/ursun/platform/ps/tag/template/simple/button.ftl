<#if (parameters.buttonClass!?exists) >
<#if (parameters.buttonClass=="gray") >
	<#assign buttonClass="button_white color_Gray">
<#elseif (parameters.buttonClass=="blue") >
	<#assign buttonClass="button_blue color_Black">
<#elseif (parameters.buttonClass=="white-blue") >
	<#assign buttonClass="button_white_blue color_Gray">
<#elseif (parameters.buttonClass=="fukei") >
	<#assign buttonClass="button_fukei color_Gray">
<#elseif (parameters.buttonClass=="border-blue")>
	<#assign buttonClass="button_pale color_Black">	
</#if>
<#else>
	<#assign buttonClass="button_white">
</#if>

<div <#if parameters.id?exists>id="${parameters.id?html}"</#if><#if parameters.name?exists>name="${parameters.name?html}"</#if><#if buttonClass?exists>class="${buttonClass?html}"</#if><#if parameters.cssStyle?exists>style="${parameters.cssStyle?html}"</#if><#if parameters.title?exists>title="${parameters.title?html}"<#rt/></#if>
<#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
<#include "/${parameters.templateDir}/simple/common-attributes.ftl" />>
	<strong></strong>
    <div><a href="javascript:void(0)">&nbsp;<#if parameters.nameValue?exists>${parameters.nameValue?html}</#if>&nbsp;</a></div>
    <span></span>	
</div>