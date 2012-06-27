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

import java.util.Date;

import cn.ursun.platform.core.domain.WeeDomain;
import cn.ursun.platform.ps.security.WeeSecurityInfo;

/**
 * <p>消息实体</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2009-3-30 上午10:27:18
 */
public class Message extends WeeDomain {

	/**
	 * <p>消息类型</p>
	 * 注意：MWEEAGE_TYPE要在表PS_MWEEAGE_TYPE中保存，同时要设定中文描述，当新增一种类型时，要向表中手动增加数据
	 * 例如：类型--类型id--类型描述
	 * NOTICE--101--通知  REMIND--102--提醒 WEBSPIDER--201--网页抓取
	 * @author  段鹏- duanp@neusoft.com
	 * @version  1.0 Created on 2009-3-30 上午10:27:18
	 */
	public static enum MWEEAGE_TYPE {
		NOTICE, REMIND, WEBSPIDER
	}

	/**
	 * 消息代码
	 * 规则：1开头为通知 提醒类型，是管理员发布的消息类型  2开头为业务操作如网页抓取，是程序发布的
	 * 与表PS_MWEEAGE_TYPE中的id一致
	 */
	private String typeId;

	/**
	 * 消息类型描述
	 */
	private String typeDesc;

	/**
	 * 消息状态 0--未发布 1--已发布 2-程序发布的业务操作消息
	 */
	private int flag;

	/**
	 * 消息状态 0-未读 1-已读 
	 */
	private int state;
	/**
	 * 立即发布标识 0-非 1-是
	 */
	private int immediately;

	/**
	 * 发布开始时间
	 */
	private Date startDate;

	/**
	 * 开始结束时间
	 */
	private Date endDate;

	/**
	 * 消息创建或者更新时间
	 */
	private Date updateDate;

	/**
	 * 消息类别,默认BIZMWEEAGE
	 */
	private String type;

	/**
	 * 发布者用户标识ID，默认当前登录的用户
	 */
	private String publisherId = WeeSecurityInfo.getInstance().getUserId();

	/**
	 * 发布者姓名
	 */
	private String publisherName;

	/**
	 * 发布时间
	 */
	private Date publishDate;

	// private Date publishDate = new Date(System.currentTimeMillis());

	// private Date publishDate = new Date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System
	// .currentTimeMillis())));

	/**
	 * 接收者类型对应的标识
	 */
	private String receiverId = WeeSecurityInfo.getInstance().getUserId();

	/**
	*  登录模式 WEE--直接登录 EAS--审批系统登
	*/
	private String loginMode;

	/**
	 * 接收者类型 10--(直接登录)面向用户发布  11-(直接登录)面向角色发布  12-(直接登录)面向组织结构发布 13-(直接登录)用户自己发送的业务操作  20--(审批系统登录)面向用户发布  21-(审批系统登录)面向角色发布  22-(审批系统登录)面向组织结构发布 
	 */
	private int receiverType;

	/**
	 * 消息内容
	 */
	private String content;

	/**
	* 消息标题
	*/
	private String title;

	/**
	 * 处理器名称
	 */
	private String handlerName;

	/**
	 * 回调函数需要的参数，默认为空字符串
	 */
	private String callbackParams = "";

	/**
	 * 消息在前台的编辑模式：0--新建  1 --修改
	 */
	private int editMode;

	/**
	 * 发件箱和收件箱标志 0--收件箱 1--发件箱
	 */
	private int boxFlag;

	public String getCallbackParams() {
		return callbackParams;
	}

	public void setCallbackParams(String callbackParams) {
		this.callbackParams = callbackParams;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		// setTypeCode(type);
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	// public String getTypeCode() {
	// return typeCode;
	// }
	//
	// public void setTypeCode(String typeCode) {
	// // if (typeCode == null) {
	// // if (getType().equals("NOTICE")) {
	// // typeCode = "101";
	// // } else if (getType().equals("REMIND")) {
	// // typeCode = "102";
	// // } else if (getType().equals("WEBSPIDER")) {
	// // typeCode = "201";
	// // }
	// // }
	// this.typeCode = typeCode;
	// }

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(int receiverType) {
		this.receiverType = receiverType;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void transferTypeToTypeId(String type) {
		if (type.equals("NOTICE")) {
			typeId = "101";
		} else if (type.equals("REMIND")) {
			typeId = "102";
		} else if (type.equals("WEBSPIDER")) {
			typeId = "201";
		} else if (type.equals("efsfsd")) {
			typeId = "301";
		}
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public int getEditMode() {
		return editMode;
	}

	public void setEditMode(int editMode) {
		this.editMode = editMode;
	}

	public int getBoxFlag() {
		return boxFlag;
	}

	public void setBoxFlag(int boxFlag) {
		this.boxFlag = boxFlag;
	}

	public String toString() {
		// System.out.println("message:id:" + super.getId() + " title" + this.title + " type" + type);
		return "";
	}

	public String getLoginMode() {
		return loginMode;
	}

	public void setLoginMode(String loginMode) {
		this.loginMode = loginMode;
	}

	
	/**
	 *
	 * @return 
	 */
	public int getImmediately() {
		return immediately;
	}

	
	/**
	 *
	 * @param immediately
	 */
	public void setImmediately(int immediately) {
		this.immediately = immediately;
	}

}
