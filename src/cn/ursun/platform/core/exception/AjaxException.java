/**
 * 文件名：SystemException.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Jun 11, 2008 7:31:47 PM
 * 
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.core.exception;

/**
 * 系统异常。用于描述系统发生的用户无法更成的异常。
 * 
 * @author 兰硕 - lans@neusoft.com
 */
public class AjaxException extends SystemException {

	/**
	 * 序列化代码
	 */
	private static final long serialVersionUID = 1L;

	private String[] textPrarams = null;

	public AjaxException() {
	}

	public AjaxException(String msg) {
		super(msg);
	}

	public AjaxException(Throwable ex) {
		super(ex);
	}

	public AjaxException(String msg, Throwable ex) {
		super(msg, ex);
	}

	/**
	 *
	 * @return 
	 */
	public String[] getTextPrarams() {
		return textPrarams;
	}

	/**
	 *
	 * @param textPrarams
	 */
	public void setTextPrarams(String[] textPrarams) {
		this.textPrarams = textPrarams;
	}
}
