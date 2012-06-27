/**
 * 文件名：BizException.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Jun 11, 2008 7:32:51 PM
 * 
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.core.exception;

/**
 * 业务异常。用于描述系统发生的业务逻辑异常，用户可以通过主动修改的方式更正这种异常。
 * 
 * @author 兰硕 - lans@neusoft.com
 */
public class BizException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 引擎操作错误代码
	 */
	public static final String ERROR_CODE_ENGINE = "00";

	/**
	 * 检索准备错误代码
	 */
	public static final String ERROR_CODE_PREPARATION = "01";

	/**
	 * 检索与修正错误代码
	 */
	public static final String ERROR_CODE_SEARCH = "02";

	/**
	 * 文献浏览错误代码
	 */
	public static final String ERROR_CODE_VIEW = "03";

	/**
	 * 检索报告错误代码
	 */
	public static final String ERROR_CODE_REPORT = "04";

	/**
	 * 统计分析错误代码
	 */
	public static final String ERROR_CODE_STATISTIC = "05";

	/**
	 * 系统管理错误代码
	 */
	public static final String ERROR_CODE_SYS_MANAGE = "06";

	/**
	 * 数据管理错误代码
	 */
	public static final String ERROR_CODE_DATA_MANAGE = "07";

	private String[] messageParameters = null;

	public BizException(String errorCode) {
		super(errorCode);
	}

	public BizException(String errorCode, String[] msgParams) {
		super(errorCode);
		setMessageParameters(msgParams);
	}

	public BizException(String errorCode, Throwable ex) {
		super(errorCode, ex);
	}

	public BizException(String errorCode, String[] msgParams, Throwable ex) {
		super(errorCode, ex);
		setMessageParameters(msgParams);
	}

	public String[] getMessageParameters() {
		return messageParameters;
	}

	public BizException setMessageParameters(String[] msgParams) {
		this.messageParameters = msgParams;
		return this;
	}
}
