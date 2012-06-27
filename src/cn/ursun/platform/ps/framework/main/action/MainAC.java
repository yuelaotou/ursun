package cn.ursun.platform.ps.framework.main.action;

import cn.ursun.platform.core.action.WeeAction;

public class MainAC extends WeeAction {

	private static final long serialVersionUID = 1L;

	private String system;

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String console() throws Exception {
		system = "console";
		return "console";
	}

	public String collection() throws Exception {
		system = "collection";
		return "collection";
	}

	public String demo() throws Exception {
		system = "demo";
		return "demo";
	}

}
