package cn.ursun.platform.ps.memorydb;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.security.WeeSecurityInfo;


public class WeeMemoryDBComponent extends MemoryDBComponent{
	public void execute() {
		super.execute();
		//初始化安全信息全局变量
		try {
			WeeSecurityInfo.getInstance().refresh();
		} catch (BizException e) {
			e.printStackTrace();
		}
	}
}
