package cn.ursun.platform.ps.login.action;

import cn.ursun.platform.core.action.WeeAction;

/**
 * 
 * <p> Title: [登陆ACTION]</p>
 * <p> Description: [登陆入口]</p>
 * <p> Created on 2010-12-26</p>
 * <p> Copyright: Copyright (c) 2010</p>
 * <p> Company: 杨光软件股份有限公司</p>
 * @author 杨光 - admin@ursun.cn
 * @version 1.0
 */
public class LoginAC extends WeeAction {

	private static final long serialVersionUID = 1L;

	private String type = "1";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String success() throws Exception {
		return SUCCESS;
	}

	public String error() throws Exception {
		return "error";
	}

}
