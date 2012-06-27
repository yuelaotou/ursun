/*------------------------------------------------------------------------------
 * PACKAGE: com.freeware.gridtag
 * FILE   : TextColumn.java
 * CREATED: Jul 22, 2004
 * AUTHOR : Prasad P. Khandekar
 *------------------------------------------------------------------------------
 * Change Log:
 *-----------------------------------------------------------------------------*/
package cn.ursun.platform.core.tag.grid.component;

import java.util.List;

import cn.ursun.platform.core.tag.grid.domain.ColumnModel;
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
public final class AnchorComponent extends ColumnComponentSupport {

	private String linkText;

	private String linkUrl;

	private String target;

	private String dataFormat;

	public AnchorComponent(ValueStack stack) {
		super(stack);
	}

	public void addColumnModel(List<ColumnModel> columnModels) {
		ColumnModel col = new ColumnModel();
		col.setColIndex(colIndex);
		col.setDataField(dataField);
		col.setDataFormat(dataFormat);
		col.setLinkUrl(linkUrl);
		col.setLinkText(linkText);
		col.setType(ColumnModel.ColumeType.ANCHOR);
		col.setTarget(target);
		columnModels.add(col);
	}

	public String renderDetail(Object value) {
		StringBuffer strBuf = new StringBuffer();
		String strValue = (value == null) ? "" : value.toString();
		strBuf.append("<td");
		if (this.width > 0)
			strBuf.append(" WIDTH=\"" + this.width + "%\"");
		if (this.height > 0)
			strBuf.append(" HEIGHT=\"" + this.height + "\"");
		if (this.cssClass != null)
			strBuf.append(" CLASS=\"" + this.cssClass + "\"");
		else
			strBuf.append(" CLASS=\"gridColumn\"");
		if (this.hAlign != null)
			strBuf.append(" ALIGN=\"" + this.hAlign.toLowerCase() + "\"");
		if (this.vAlign != null)
			strBuf.append(" VALIGN=\"" + this.vAlign.toLowerCase() + "\"");

		if (this.bgColor != null)
			strBuf.append(" BGCOLOR=\"" + this.bgColor + "\"");
		if (this.foreColor != null)
			strBuf.append(" COLOR=\"" + this.foreColor + "\"");

		strBuf.append("><span>");
		strBuf.append("<a HREF=");
		strBuf.append(resolveFields(this.linkUrl));
		strBuf.append(" ");
		if (this.target != null)
			strBuf.append(" TARGET=\"" + this.target + "\"");
		strBuf.append(">");
		strBuf.append("<span rowIndex='" + status.getIndex()+ "' colIndex='" + this.colIndex + "' dataField='"
				+ this.dataField + "'>");
		if (this.linkText != null)
			strBuf.append(this.linkText);
		else if (this.dataFormat != null)
			strBuf.append(formatField(strValue, this.dataFormat));
		else {
			strBuf.append(strValue);
		}
		strBuf.append("</span>");
		strBuf.append("</a>");
		strBuf.append("</span>");
		strBuf.append("</td>");
		return strBuf.toString();
	}

	public String getLinkText() {
		return linkText;
	}

	public void setLinkText(String linkText) {
		this.linkText = linkText;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

}
