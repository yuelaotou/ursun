/**
 * 文件名：Message.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：2009-3-30 上午10:27:18
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.domain;

import cn.ursun.platform.core.domain.WeeDomain;
import cn.ursun.platform.ps.security.WeeSecurityInfo;

/**
 * <p>消息参数配置</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2009-3-30 上午10:27:18
 */
public class MessageConfig extends WeeDomain {

	/**
	 * 用户标志
	 */
	private String userId = WeeSecurityInfo.getInstance().getUserId();

	/**
	 * 消息窗口停留时间 单位：秒
	 */
	private int keepTime = 5;

	/**
	 * 消息轮询时间 单位：秒
	 */
	private int pollingTime = 10;

	/**
	 * 每类消息显示的条数
	 */
	private int pollingNum = 5;

	/**
	 * 过期消息提醒标志 0--不提醒 1--提醒
	 */
	// private int overdueShow = 1;
	/**
	 * 是否开启消息提醒 0--开启 1--停止
	 */
	private int startPoll;

	/**
	 * 消息窗口显示的条数
	 */
	private int showMsgNum = 3;

	public int getKeepTime() {
		return keepTime;
	}

	public void setKeepTime(int keepTime) {
		this.keepTime = keepTime;
	}

	public int getPollingTime() {
		return pollingTime;
	}

	public void setPollingTime(int pollingTime) {
		this.pollingTime = pollingTime;
	}

	// public int getOverdueShow() {
	// return overdueShow;
	// }
	//
	// public void setOverdueShow(int overdueShow) {
	// this.overdueShow = overdueShow;
	// }

	public int getShowMsgNum() {
		return showMsgNum;
	}

	public void setShowMsgNum(int showMsgNum) {
		this.showMsgNum = showMsgNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getPollingNum() {
		return pollingNum;
	}

	public void setPollingNum(int pollingNum) {
		this.pollingNum = pollingNum;
	}

	public int getStartPoll() {
		return startPoll;
	}

	public void setStartPoll(int startPoll) {
		this.startPoll = startPoll;
	}
}
