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

import cn.ursun.platform.core.tag.grid.component.CustomComponent;
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
public final class CustomColumnTag extends ColumnTagSupport {

	private String renderTo = null;

	public CustomColumnTag() {
		super();
	}

	public Component getBean(ValueStack valuestack, HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse) {
		return new CustomComponent(valuestack);
	}

	protected void populateParams() {
		super.populateParams();
		CustomComponent bean = (CustomComponent) getComponent();
		bean.setRenderTo(renderTo);
	}

	public String getRenderTo() {
		return renderTo;
	}

	public void setRenderTo(String renderTo) {
		this.renderTo = renderTo;
	}

}
