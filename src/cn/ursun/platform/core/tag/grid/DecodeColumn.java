/*------------------------------------------------------------------------------
 * PACKAGE: com.freeware.gridtag
 * FILE   : DecodeColumn.java
 * CREATED: Jul 26, 2004
 * AUTHOR : Prasad P. Khandekar
 *------------------------------------------------------------------------------
 * Change Log:
 *-----------------------------------------------------------------------------*/
package cn.ursun.platform.core.tag.grid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.struts2.components.Component;

import cn.ursun.platform.core.tag.grid.component.GridComponent;
import cn.ursun.platform.core.tag.grid.component.TextComponent;
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
public final class DecodeColumn extends ColumnTagSupport {

	private static final long serialVersionUID = -6868221442424930015L;

	private String decodeValues;

	private String displayValues;

	private String valueSeperator;

	public DecodeColumn() {
		super();
	}

	/*------------------------------------------------------------------------------
	 * Getters 
	 *----------------------------------------------------------------------------*/
	public String getDecodeValues() {
		return this.decodeValues;
	}

	public String getDisplayValues() {
		return this.displayValues;
	}

	public String getValueSeperator() {
		return this.valueSeperator;
	}

	/*------------------------------------------------------------------------------
	 * Setters 
	 *----------------------------------------------------------------------------*/
	public void setDecodeValues(String mstrValues) {
		this.decodeValues = mstrValues;
	}

	public void setDisplayValues(String mstrValues) {
		this.displayValues = mstrValues;
	}

	public void setValueSeperator(String pstrSeperator) {
		this.valueSeperator = pstrSeperator;
	}

	/*------------------------------------------------------------------------------
	 * Overridden methods
	 * @see javax.servlet.jsp.tagext.Tag
	 *----------------------------------------------------------------------------*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException {
		int result = super.doEndTag();
		GridTag objTmp = null;

		try {
			objTmp = (GridTag) getParent();
		//	objTmp.addColumn(getCopy());
		} catch (ClassCastException CCEx) {
			throw new JspException("Error: DecodeColumn is not a child of DBGrid", CCEx);
		} finally {
			if (objTmp != null)
				objTmp = null;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException {
		if (!(this.getParent() instanceof GridTag))
			throw new JspException("Error: Column tag needs to be a child of DBGrid!");

		if (!checkValues())
			throw new JspException("Error: For every decode value a display value must be specified!");

		// This tag does not have body contents.
		return super.doStartTag();
	}

	/*------------------------------------------------------------------------------
	 * Methods 
	 *----------------------------------------------------------------------------*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.freeware.gridtag.IColumnTag#renderDetail()
	 */
	public void renderDetail(StringBuffer objBuf, Object pobjValue) {

		objBuf.append("<td");
		if (this.width > 0)
			objBuf.append(" WIDTH=\"" + this.width + "%\"");
		if (this.height > 0)
			objBuf.append(" HEIGHT=\"" + this.height + "\"");
		if (this.cssClass != null)
			objBuf.append(" CLASS=\"" + this.cssClass + "\"");
		else
			objBuf.append(" CLASS=\"gridColumn\"");
		if (this.hAlign != null)
			objBuf.append(" ALIGN=\"" + this.hAlign.toLowerCase() + "\"");
		if (this.vAlign != null)
			objBuf.append(" VALIGN=\"" + this.vAlign.toLowerCase() + "\"");

		if (this.bgColor != null)
			objBuf.append(" BGCOLOR=\"" + this.bgColor + "\"");
		if (this.foreColor != null)
			objBuf.append(" COLOR=\"" + this.foreColor + "\"");

		objBuf.append(">");
		objBuf.append(formatField(pobjValue));
		objBuf.append("</td>");
	}

	private String formatField(Object pobjVal) throws ClassCastException {
		int intCnt = 0;
		String strRet = null;

		String[] arrDecode = null;
		String[] arrDisplay = null;

		arrDecode = this.decodeValues.split(this.valueSeperator);
		arrDisplay = this.displayValues.split(this.valueSeperator);
		for (intCnt = 0; intCnt < arrDecode.length; intCnt++) {
			if (pobjVal.equals(arrDecode[intCnt])) {
				strRet = arrDisplay[intCnt];
				break;
			} else if (pobjVal.toString().equals(arrDecode[intCnt])) {
				strRet = arrDisplay[intCnt];
				break;
			}
		}

		if (arrDecode != null)
			arrDecode = null;
		if (arrDisplay != null)
			arrDisplay = null;

		if (strRet == null)
			strRet = GridComponent.DEFAULT_NULLTEXT;
		return strRet;
	}

	private DecodeColumn getCopy() {
		DecodeColumn objRet = null;

		objRet = new DecodeColumn();
		//super.copyAttributesTo(objRet);
		objRet.setDecodeValues(this.decodeValues);
		objRet.setDisplayValues(this.displayValues);
		objRet.setId(this.getId());
		objRet.setPageContext(this.pageContext);
		objRet.setParent(this.getParent());
		objRet.setValueSeperator(this.valueSeperator);
		return objRet;
	}

	private boolean checkValues() {
		boolean blnRet = false;
		String[] arrDecode = null;
		String[] arrDisplay = null;

		arrDecode = this.decodeValues.split(this.valueSeperator);
		arrDisplay = this.displayValues.split(this.valueSeperator);

		if (arrDecode.length == arrDisplay.length)
			blnRet = true;
		else if (arrDecode.length < arrDisplay.length)
			blnRet = true;

		arrDecode = null;
		arrDisplay = null;
		return blnRet;
	}

	public Component getBean(ValueStack valuestack, HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse) {
		return new TextComponent(valuestack);
	}

	protected void populateParams() {
		super.populateParams();
		TextComponent textColumnCmp = (TextComponent) getComponent();
	}
}
