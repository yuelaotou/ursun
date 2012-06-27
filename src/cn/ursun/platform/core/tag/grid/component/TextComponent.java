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

import org.apache.commons.lang.StringUtils;

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
public final class TextComponent extends ColumnComponentSupport {

	private int maxLength;

	private String dataFormat;

	public TextComponent(ValueStack stack) {
		super(stack);
	}

	public void addColumnModel(List<ColumnModel> columnModels) {
		ColumnModel col = new ColumnModel();
		col.setColIndex(colIndex);
		col.setDataFormat(dataFormat);
		col.setDataField(dataField);
		col.setType(ColumnModel.ColumeType.TEXT);
		col.setMaxLength(maxLength);
		columnModels.add(col);
	}

	public String renderDetail(Object value) {
		StringBuffer objBuf = new StringBuffer();
		String strVal = (value == null) ? "" : value.toString();
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
		strVal = formatField(strVal, this.dataFormat);
		objBuf.append("<span rowIndex='" +status.getIndex() + "' colIndex='" + this.colIndex + "' dataField='"
				+ this.dataField + "'><p ");
		if (strVal != null && this.maxLength > 0)
			if (strVal.length() > this.maxLength)
			{
				objBuf.append("title='"+strVal+"'");
				strVal = strVal.substring(0, this.maxLength) + "...";
			}
		objBuf.append(">");
		objBuf.append(strVal);
		objBuf.append("</p></span></td>");
		return objBuf.toString();
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

}
