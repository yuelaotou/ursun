package cn.ursun.platform.core.tag.grid.component;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.lang.StringUtils;

import cn.ursun.platform.core.tag.grid.domain.GroupConfig;
import com.opensymphony.xwork2.util.ValueStack;

public class GridGroupComponent extends SupportComponent {

	private String dataField = null;

	private String lable = null;

	private String cssClass = null;

	private boolean isDraw = false;

	private String sortMode = null;

	public GridGroupComponent(ValueStack stack) {
		super(stack);
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public static final String GRID_CURRENT_GROUP_KEY = "GRID_CURRENT_GROUP_KEY";

	public static final String GRID_CURRENT_GROUP_ID_KEY = "GRID_CURRENT_GROUP_ID_KEY";

	public boolean start(Writer writer) {
		ValueStack stack = getStack();
		GridComponent.GridState gridCurrentState = (GridComponent.GridState) stack.getContext().get(
				GridComponent.GRID_STATE_KEY);
		if (gridCurrentState.equals(GridComponent.GridState.DRAW_HEADER)) {
			GroupConfig croupConfig = new GroupConfig();
			croupConfig.setDataField(dataField);
			croupConfig.setLable(lable);
			croupConfig.setSortMode(sortMode);
			stack.getContext().put(GridComponent.GRID_GROUP_CONFIG_KEY, croupConfig);
			try {
				writer.write(drawTemplate());
			} catch (IOException e) {
				e.printStackTrace();
			}

			return true;
		} else if (gridCurrentState.equals(GridComponent.GridState.DRAW_BODY)) {
			String gridCurrentGroup = (String) stack.getContext().get(GRID_CURRENT_GROUP_KEY);
			String groupColumn = StringUtils.defaultString((String) getStack().findValue(this.dataField));
			if (!StringUtils.equals(gridCurrentGroup, groupColumn)) {
				stack.getContext().put(GRID_CURRENT_GROUP_KEY, groupColumn);
				String groupId = (String) stack.getContext().get(GRID_CURRENT_GROUP_ID_KEY);
				int group = (StringUtils.isEmpty(groupId)) ? 0 : Integer.parseInt(groupId);
				stack.getContext().put(GRID_CURRENT_GROUP_ID_KEY, String.valueOf(++group));
				try {
					if (cssClass != null) {
						writer.write("<tr CLASS='" + cssClass + "'>");
					} else {
						writer.write("<tr CLASS='gridGroup' >");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				isDraw = true;
			} else {
				isDraw = false;
			}

			return true;
		} else {
			return true;
		}
	}

	public boolean end(Writer writer, String body) {
		ValueStack stack = getStack();
		GridComponent.GridState gridCurrentState = (GridComponent.GridState) stack.getContext().get(
				GridComponent.GRID_STATE_KEY);
		if (gridCurrentState.equals(GridComponent.GridState.DRAW_BODY)) {
			if (isDraw) {
				String groupId = (String) stack.getContext().get(GRID_CURRENT_GROUP_ID_KEY);
				int group = (StringUtils.isEmpty(groupId)) ? 0 : Integer.parseInt(groupId);
				stack.getContext().put(GRID_CURRENT_GROUP_ID_KEY, String.valueOf(++group));
				super.end(writer, renderDetail() + "</tr>");
			}
			return false;
		}
		if (gridCurrentState.equals(GridComponent.GridState.DRAW_FOOTER)) {
			stack.getContext().remove(GRID_CURRENT_GROUP_KEY);
			stack.getContext().remove(GRID_CURRENT_GROUP_ID_KEY);
			return false;
		}
		return false;
	}

	public String drawTemplate() {
		ValueStack stack = getStack();
		String columnCount = (String) stack.getContext().get(GridComponent.COLUMN_COUNT_KEY);
		String gridId = (String) stack.getContext().get(GridComponent.GRID_ID_KEY);
		String isSingle = (String) stack.getContext().get(GridComponent.GRID_SELECT_MODE_KEY);
		StringBuffer objBuf = new StringBuffer();
		objBuf.append("<tr style='display:none' CLASS='grid_group_template ");
		if (cssClass != null)
			objBuf.append(cssClass);
		else
			objBuf.append("gridGroup");
		objBuf.append("'>");
		if (isSingle != null) {
			if ("false".equals(isSingle)) {
				String chxClass = "grid_group_chx";
				objBuf.append("<td align='center' >");
				objBuf.append("<input type='checkbox' id='groupChx' name='groupChx' class='" + chxClass
						+ "'  value=''></td>");
			} else {
				objBuf.append("<td ></td>");
			}
		}
		objBuf.append("<td");
		objBuf.append(" COLSPAN=\"" + columnCount + "\"");
		objBuf.append(">");
		objBuf
				.append("<div class='grid_group'><div class='group_expand group_expand_open_icon'></div><span class='grid_group_lable'>");
		objBuf.append("</span></div></td></tr>");
		return objBuf.toString();
	}

	public String renderDetail() {
		ValueStack stack = getStack();
		String groupId = (String) stack.getContext().get(GRID_CURRENT_GROUP_ID_KEY);
		String columnCount = (String) stack.getContext().get(GridComponent.COLUMN_COUNT_KEY);
		String gridId = (String) stack.getContext().get(GridComponent.GRID_ID_KEY);
		String isSingle = (String) stack.getContext().get(GridComponent.GRID_SELECT_MODE_KEY);
		String strValue = (getStack().findValue(dataField) == null) ? "" : getStack().findValue(dataField).toString();
		StringBuffer objBuf = new StringBuffer();
		if (isSingle != null) {
			if ("false".equals(isSingle)) {
				String chxClass = "grid_group_chx";
				objBuf.append("<td align='center' >");
				objBuf.append("<input type='checkbox' id='groupChx' name='groupChx' class='" + chxClass
						+ "' onClick='$(\"#" + gridId + "\").selectGroup(this)' value='" + strValue + "\' groupId='"
						+ groupId + "'></td>");
			} else {
				objBuf.append("<td ></td>");
			}
		}
		objBuf.append("<td");
		objBuf.append(" COLSPAN=\"" + columnCount + "\"");
		objBuf.append(">");
		objBuf.append("<div class='grid_group'><div class='group_expand_open_icon'");
		objBuf.append(" onclick=\"$('#" + gridId + "').expandGroup(this)\" groupId='" + groupId
				+ "'></div><span class='grid_group_lable'>");
		objBuf.append(resolveFields(this.lable));
		objBuf.append("</span></div></td>");
		return objBuf.toString();
	}

	private String resolveFields(String pstrUrl) throws ClassCastException {
		int intPos = 0;
		int intEnd = 0;
		String strCol = null;
		String strRet = null;
		strRet = pstrUrl;
		intPos = strRet.indexOf("{");
		while (intPos >= 0) {
			intEnd = strRet.indexOf("}", intPos + 1);
			if (intEnd != -1) {
				strCol = strRet.substring(intPos + 1, intEnd);
				String strValue = (getStack().findValue(strCol) == null) ? "" : getStack().findValue(strCol).toString();
				strRet = strRet.substring(0, intPos) + strValue + strRet.substring(intEnd + 1);
			}
			intPos = strRet.indexOf("{", intPos + 1);
		}
		return strRet;
	}

	public String getDataField() {
		return dataField;
	}

	public void setDataField(String dataField) {
		this.dataField = dataField;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public String getSortMode() {
		return sortMode;
	}

	public void setSortMode(String sortMode) {
		this.sortMode = sortMode;
	}

}
