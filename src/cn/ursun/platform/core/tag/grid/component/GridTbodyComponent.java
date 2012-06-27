package cn.ursun.platform.core.tag.grid.component;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.views.jsp.IteratorStatus;

import com.opensymphony.xwork2.util.ValueStack;

public class GridTbodyComponent extends SupportComponent {

	private String cssClass = null;

	private String onClick = null;

	public final static String GRID_COL_INDEX_KEY = "GRID_COL_INDEX_KEY";

	public GridTbodyComponent(ValueStack stack) {
		super(stack);
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public boolean start(Writer writer) {
		ValueStack stack = getStack();
		GridComponent.GridState gridCurrentState = (GridComponent.GridState) stack.getContext().get(
				GridComponent.GRID_STATE_KEY);
		IteratorStatus status = (IteratorStatus) stack.getContext().get(GridComponent.STATUS_STATE_KEY);
		String gridId = (String) stack.getContext().get(GridComponent.GRID_ID_KEY);
		String groupId = (String) stack.getContext().get(GridGroupComponent.GRID_CURRENT_GROUP_ID_KEY);
		String trClass = "grid_group_tr_" + groupId;
		StringBuffer bf = new StringBuffer();
		stack.getContext().put(GRID_COL_INDEX_KEY, 0);
		if (gridCurrentState.equals(GridComponent.GridState.DRAW_HEADER)) {
			bf.append("<tr CLASS=\"").append(StringUtils.defaultString(cssClass));
			bf.append(" tr_template");
			if (groupId != null) {
				bf.append(trClass);
			}
			bf.append(" \" style='display:none'>");
			stack.getContext().put(GridComponent.GRID_ONCLICK_KEY, onClick);
		} else if (gridCurrentState.equals(GridComponent.GridState.DRAW_BODY)) {
			bf.append("<tr CLASS=\"").append(StringUtils.defaultString(cssClass));
			if ((status.getIndex() % 2) != 0) {
				bf.append(" gridRowEven ");

			} else {
				bf.append(" gridRowOdd ");
			}
			if (groupId != null) {
				bf.append(trClass);
			}
			if(onClick!=null){
				bf.append(" \" onClick=\"" + onClick + "("+status.getIndex()+")");
			}
			
			bf.append(" \">");

		}
		try {
			writer.write(bf.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean end(Writer writer, String body) {
		ValueStack stack = getStack();
		GridComponent.GridState gridCurrentState = (GridComponent.GridState) stack.getContext().get(
				GridComponent.GRID_STATE_KEY);
		if (stack.getContext().containsKey(GRID_COL_INDEX_KEY)) {
			stack.getContext().remove(GRID_COL_INDEX_KEY);
		}

		if (gridCurrentState.equals(GridComponent.GridState.DRAW_HEADER)) {
			StringBuffer bf = new StringBuffer();
			IteratorStatus state = (IteratorStatus) stack.getContext().get(GridComponent.STATUS_STATE_KEY);
			bf.append("</tr>");
			String colCount = (String) stack.getContext().get(GridComponent.COLUMN_COUNT_KEY);
			bf.append("<tr class=\"grid_empty_tr\" ");
			bf.append("style=\"display:none\"");
			bf.append("><td colspan=\"" + colCount + "\" >");
			bf.append("<table BORDER=0 CELLSPACING=0 CELLPADDING=0 class=\"grid_empty\" ");
			bf.append("WIDTH=\"100%\">\r\n");
			bf.append("<tr >\r\n");
			bf.append("<td ALIGN=\"center\" WIDTH=\"30%\" ");
			bf.append("CLASS=\"gridPageOfPage\">\r\n");
			bf.append(findValue("getText('grid.norecord')"));
			bf.append("</td>\r\n");
			bf.append("</tr>\r\n");
			bf.append("</table>\r\n");
			bf.append("</td>");
			bf.append("</tr>");
			super.end(writer, bf.toString());
			return false;

		} else if (gridCurrentState.equals(GridComponent.GridState.DRAW_HEADER)) {
			super.end(writer, "</tr>");
			return false;
		}
		return false;
	}

}
