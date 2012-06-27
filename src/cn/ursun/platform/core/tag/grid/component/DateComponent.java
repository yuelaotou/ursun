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

import org.apache.struts2.views.jsp.IteratorStatus;

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
public final class DateComponent extends ColumnComponentSupport {

	private String dataFormat;

	public DateComponent(ValueStack stack) {
		super(stack);
	}

	public void addColumnModel(List<ColumnModel> columnModels) {
		ColumnModel col = new ColumnModel();
		col.setColIndex(colIndex);
		col.setDataField(dataField);
		col.setType(ColumnModel.ColumeType.DATE);
		columnModels.add(col);
	}

	public String renderDetail(Object value) {
		StringBuffer strBuf = new StringBuffer();
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

		strBuf.append(">");
		strBuf.append("<span rowIndex='" + status.getIndex() + "' colIndex='" + this.colIndex + "' dataField='"
				+ this.dataField + "'>");
		strBuf.append(formatField(value, this.dataFormat));
		strBuf.append("</span>");

		strBuf.append("</td>");
		return strBuf.toString();
	}

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

}
