package cn.ursun.console.app.domain;

import java.util.Date;

import com.googlecode.jsonplugin.annotations.JSON;

public class BizLog {

	private String id = null;//
	
	private Date operationDate = null;//操作时间
	
	private String operationType = null;//操作类型
	
	private String operator = null;//操作人ID（userId）
	
	private String operatorName = null;//操作人姓名
	
	private String sex = null;//
	
	private String dept = null;//
	
	private String ip = null;//
	
	private String moduleLevel1 = null;//一级模块编码
	
	private String moduleLevel2 = null;//二级模块编码
	
	private String moduleLevel3 = null;//三级模块编码
	
	private String moduleLevel4 = null;//四级模块编码
	
	private String moduleLevel5 = null;//五级模块编码
	
	private String moduleLevel6 = null;//六级模块编码
	
	private String moduleLevel7 = null;//七级模块编码
	
	private String taskId = null;//任务代码
	
	private String bizCode = null;//业务代码
	
	private String funcCode = null;//功能代码
	
	private String loginName = null;//登录名称
	
	private String detail = null;//日志详细内容 CLOB字段

	private String startDate = null;//查询使用，起始时间
	
	private String endDate = null;//查询使用，结束时间
	
	private String roleId = null;//所属角色ID
	
	private String roleName = null;//所属角色名称
	


	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getOperationDate() {		
		return operationDate;
	}

	
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	
	public String getOperationType() {
		return operationType;
	}

	
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	
	public String getOperatorName() {
		return operatorName;
	}

	
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	
	public String getModuleLevel1() {
		return moduleLevel1;
	}

	
	public void setModuleLevel1(String moduleLevel1) {
		this.moduleLevel1 = moduleLevel1;
	}

	
	public String getModuleLevel2() {
		return moduleLevel2;
	}

	
	public void setModuleLevel2(String moduleLevel2) {
		this.moduleLevel2 = moduleLevel2;
	}

	
	public String getModuleLevel3() {
		return moduleLevel3;
	}

	
	public void setModuleLevel3(String moduleLevel3) {
		this.moduleLevel3 = moduleLevel3;
	}

	
	public String getModuleLevel4() {
		return moduleLevel4;
	}

	
	public void setModuleLevel4(String moduleLevel4) {
		this.moduleLevel4 = moduleLevel4;
	}

	
	public String getModuleLevel5() {
		return moduleLevel5;
	}

	
	public void setModuleLevel5(String moduleLevel5) {
		this.moduleLevel5 = moduleLevel5;
	}

	
	public String getModuleLevel6() {
		return moduleLevel6;
	}

	
	public void setModuleLevel6(String moduleLevel6) {
		this.moduleLevel6 = moduleLevel6;
	}

	
	public String getModuleLevel7() {
		return moduleLevel7;
	}

	
	public void setModuleLevel7(String moduleLevel7) {
		this.moduleLevel7 = moduleLevel7;
	}

	
	public String getTaskId() {
		return taskId;
	}

	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	
	public String getBizCode() {
		return bizCode;
	}

	
	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	
	public String getFuncCode() {
		return funcCode;
	}

	
	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	
	public String getLoginName() {
		return loginName;
	}

	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	
	public String getRoleId() {
		return roleId;
	}

	
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	
	public String getRoleName() {
		return roleName;
	}

	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
