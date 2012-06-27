/**
 * 文件名：WeeAction.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Aug 11, 2008 10:26:23 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.action;

import java.io.File;
import java.math.BigDecimal;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.struts2.ServletActionContext;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.core.upload.WeeMultiPartRequest;

/**
 * Wee框架的文件上传的Action基类。
 * 
 * @author 兰硕 - lans@neusoft.com
 */
public class WeeUploadFileAction extends WeeAction {

	private static final String FILE_SIZE_EXCEED_ERROR_MSG = "1060401010201";

	private static final String FILE_NOT_EXISTENCE = "1060401010202";

	/**
	 * <p>校验上传文件的大小</p>
	 * 
	 * @throws Exception
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Nov 12, 2008 4:54:44 PM
	 */
	protected void validateUploadFile(String[] fileName, File[] files) throws Exception {
		BizException ae = null;
		Exception e = (Exception) ServletActionContext.getRequest()
				.getAttribute(WeeMultiPartRequest.FILE_EXCEPTION_KEY);
		if (e != null && e instanceof FileUploadBase.FileSizeLimitExceededException) {
			ServletActionContext.getRequest().setAttribute("submit-type", "ajax");
			FileUploadBase.FileSizeLimitExceededException se = (FileUploadBase.FileSizeLimitExceededException) e;
			ae = new BizException(FILE_SIZE_EXCEED_ERROR_MSG, new String[] { changeSizeToKB(se.getPermittedSize()) },
					se);
			throw ae;
		}
		int i = 0;
		if (files != null)
			for (File f : files) {
				if (!f.canRead()) {
					ae = new BizException(FILE_NOT_EXISTENCE, new String[] { fileName[i] });
					throw ae;
				}
				i++;
			}

	}

	/**
	 * <p>校验上传文件的大小</p>
	 * 
	 * @param fileName
	 * @param files
	 * @throws Exception
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Nov 20, 2008 1:34:21 PM
	 */
	protected void validateUploadFile(String fileName, File files) throws Exception {
		BizException ae = null;
		Exception e = (Exception) ServletActionContext.getRequest()
				.getAttribute(WeeMultiPartRequest.FILE_EXCEPTION_KEY);
		if (e != null && e instanceof FileUploadBase.FileSizeLimitExceededException) {
			ServletActionContext.getRequest().setAttribute("submit-type", "ajax");
			FileUploadBase.FileSizeLimitExceededException se = (FileUploadBase.FileSizeLimitExceededException) e;
			ae = new BizException(FILE_SIZE_EXCEED_ERROR_MSG, new String[] { changeSizeToKB(se.getPermittedSize()) },
					se);
			throw ae;
		}
		if (files != null)
			validateUploadFile(new String[] { fileName }, new File[] { files });
	}

	/**
	 * <p>转成KB为单位的文件大小</p>
	 * 
	 * @param s
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Nov 12, 2008 4:54:58 PM
	 */
	private String changeSizeToKB(long s) {
		return new BigDecimal(s).divide(new BigDecimal("1024"), 2, BigDecimal.ROUND_HALF_UP).toString();
	}
}
