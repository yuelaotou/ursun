/*------------------------------------------------------------------------------
 * PACKAGE: com.freeware.gridtag
 * FILE   : TextColumn.java
 * CREATED: Jul 22, 2004
 * AUTHOR : Prasad P. Khandekar
 *------------------------------------------------------------------------------
 * Change Log:
 *-----------------------------------------------------------------------------*/
package cn.ursun.platform.core.tag.grid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;

import cn.ursun.platform.core.tag.grid.component.DateComponent;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * <p> Title: [名称]</p>
 * <p> Description: [描述]</p>
 * <p> Created on 2009-9-11</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author 宋成山 songchengshan@neusoft.com
 * @version 1.0
 */
public final class DateColumnTag extends ColumnTagSupport {


	private String dataFormat;

	public DateColumnTag() {
		super();
	}

	public Component getBean(ValueStack valuestack, HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse) {
		return new DateComponent(valuestack);
	}

	protected void populateParams() {
		super.populateParams();
		DateComponent textColumnCmp = (DateComponent) getComponent();
		textColumnCmp.setDataFormat(dataFormat);
	}


	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

}
