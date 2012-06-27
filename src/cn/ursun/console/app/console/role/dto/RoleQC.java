package cn.ursun.console.app.console.role.dto;


public class RoleQC {
	private String roleId;
	private String loginRId;
	private String userId;
	private String unitId ;
	
	public String getRoleId() {
		return roleId;
	}
	
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public String getLoginRId() {
		return loginRId;
	}
	
	public void setLoginRId(String loginRId) {
		this.loginRId = loginRId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUnitId() {
		return unitId;
	}
	
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
}
