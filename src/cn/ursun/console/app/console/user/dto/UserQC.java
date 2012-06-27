package cn.ursun.console.app.console.user.dto;

import java.util.Map;


public class UserQC {

	private String userId = null;

	private String userName = null;

	private String enabled = null;
	
	private String unit= null;

	private String fullName = null;
	
	private String registeDateBegin=null;
	
	private String registeDateEnd=null;
	
	private Map<String,String> extendCondition=null;

	public String getUserId() {
		return userId;
	}

	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	public String getUserName() {
		return userName;
	}

	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public String getEnabled() {
		return enabled;
	}

	
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	
	public String getFullName() {
		return fullName;
	}

	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}

	
	public Map<String, String> getExtendCondition() {
		return extendCondition;
	}


	
	public void setExtendCondition(Map<String, String> extendCondition) {
		this.extendCondition = extendCondition;
	}


	
	public String getRegisteDateBegin() {
		return registeDateBegin;
	}


	
	public void setRegisteDateBegin(String registeDateBegin) {
		this.registeDateBegin = registeDateBegin;
	}


	
	public String getRegisteDateEnd() {
		return registeDateEnd;
	}


	
	public void setRegisteDateEnd(String registeDateEnd) {
		this.registeDateEnd = registeDateEnd;
	}

}
