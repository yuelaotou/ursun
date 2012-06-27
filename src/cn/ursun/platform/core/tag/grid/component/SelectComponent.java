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
public final class SelectComponent extends ColumnComponentSupport {

	public SelectComponent(ValueStack stack) {
		super(stack);
	}

	public void addColumnModel(List<ColumnModel> columnModels) {
		String isSingle = (String) stack.getContext().get(GridComponent.GRID_SELECT_MODE_KEY);
		ColumnModel col = new ColumnModel();
		col.setColIndex(colIndex);
		col.setDataField(dataField);
		col.setType(ColumnModel.ColumeType.SELECT);
		col.setIsSingle(isSingle);
		columnModels.add(col);
	}

	public String renderDetail(Object value) {
		ValueStack stack = getStack();
		String strValue = (value == null) ? "" : value.toString();
		String groupId = (String) stack.getContext().get(GridGroupComponent.GRID_CURRENT_GROUP_ID_KEY);
		String isSingle = (String) stack.getContext().get(GridComponent.GRID_SELECT_MODE_KEY);
		StringBuffer objBuf = new StringBuffer();
		objBuf.append("<td");
		if (this.width > 0)
			objBuf.append(" WIDTH=\"" + this.width + "%\"");
		if (this.height > 0)
			objBuf.append(" HEIGHT=\"" + this.height + "\"");
		if (this.cssClass != null)
			objBuf.append(" CLASS=\"gridColumn " + this.cssClass + "\"");
		else
			objBuf.append(" CLASS=\"gridColumn\"");
		if (this.hAlign != null)
			objBuf.append(" ALIGN=\"" + this.hAlign.toLowerCase() + "\"");
		else
			objBuf.append(" ALIGN=\"center\"");
		if (this.vAlign != null)
			objBuf.append(" VALIGN=\"" + this.vAlign.toLowerCase() + "\"");

		if (this.bgColor != null)
			objBuf.append(" BGCOLOR=\"" + this.bgColor + "\"");
		if (this.foreColor != null)
			objBuf.append(" COLOR=\"" + this.foreColor + "\"");
		objBuf.append(">");
		objBuf.append("<input type='");
		if ("true".equals(isSingle)) {
			objBuf.append("radio");
		} else {
			objBuf.append("checkbox");
		}
		if (!StringUtils.isEmpty(groupId)) {
			objBuf.append("' class='");
			objBuf.append("grid_group_chx_" + groupId);
		}
		objBuf.append("' name='rowid' value='");
		objBuf.append(strValue);
		objBuf.append("'>");
		objBuf.append("<span style='display:none' rowIndex='" + status.getIndex() + "' colIndex='" + this.colIndex
				+ "' dataField='" + this.dataField + "'>");
		objBuf.append(strValue);
		objBuf.append("</span>");
		objBuf.append("</td>");
		return objBuf.toString();
	}

	public String drawTemplateCell() {
		StringBuffer objBuf = new StringBuffer();
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
		else {
			objBuf.append(" ALIGN=\"center\"");
		}
		if (this.bgColor != null)
			objBuf.append(" BGCOLOR=\"" + this.bgColor + "\"");
		if (this.foreColor != null)
			objBuf.append(" COLOR=\"" + this.foreColor + "\"");

		objBuf.append("><span style='display:none'  rowIndex='-1' colIndex='" + this.colIndex + "' dataField='"
				+ this.dataField + "'>");
		objBuf.append("</span></td>");
		return objBuf.toString();
	}
}
