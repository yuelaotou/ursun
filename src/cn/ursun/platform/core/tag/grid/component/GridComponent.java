package cn.ursun.platform.core.tag.grid.component;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.MakeIterator;
import org.apache.struts2.views.jsp.IteratorStatus;

import cn.ursun.platform.core.tag.grid.domain.ColumnModel;
import cn.ursun.platform.core.tag.grid.domain.GroupConfig;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * <p>
 * Title: [名称]
 * </p>
 * <p>
 * Description: [描述]
 * </p>
 * <p>
 * Created on 2009-9-11
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: 东软软件股份有限公司
 * </p>
 * 
 * @author 宋成山 songchengshan@neusoft.com
 * @version 1.0
 */
public class GridComponent extends SupportComponent {

	private int border ;

	private int cellPadding ;

	private int cellSpacing ;

	private int width ;

	private String cssClass = null;

	private String bgColor = null;

	private String foreColor = null;

	private String id = null;

	private String name = null;

	private String dataSource = null;

	private String action = null;

	private String theme;

	private String pagination;

	public static final String DEFAULT_NULLTEXT = "&nbsp";

	public static final String GRID_ID_KEY = "GRID_ID";

	public static final String CURRENT_SORT_COLUMN_KEY = "CURRENT_SORT_COLUMN";

	public static final String CURRENT_SORT_MODE_KEY = "CURRENT_SORT_MODE";

	public static final String PAGINATION_KEY = "PAGINATION_KEY";

	public static final String COLUMN_COUNT_KEY = "COLUMN_COUNT";

	public static final String STATUS_STATE_KEY = "STATUS_STATE";

	public static final String GRID_STATE_KEY = "GRID_STATE";

	public static final String GRID_COLUMN_MODEL_KEY = "GRID_COLUMN_MODEL_KEY";

	public static final String GRID_PAGE_CONFIG_KEY = "GRID_PAGE_CONFIG_KEY";

	public final static String GRID_SELECT_MODE_KEY = "GRID_SELECT_MODE_KEY";

	public final static String GRID_GROUP_CONFIG_KEY = "GRID_GROUP_CONFIG_KEY";

	public final static String GRID_ONCLICK_KEY = "GRID_ONCLICK_KEY";

	public static enum GridState {
		DRAW_HEADER, DRAW_BODY, DRAW_FOOTER;
	}

	protected Iterator<?> iterator;

	protected IteratorStatus.StatusState statusState;

	protected IteratorStatus status;

	protected List<ColumnModel> columnModels = null;

	public GridComponent(ValueStack stack) {
		super(stack);
	}

	public boolean start(Writer writer) {
		ValueStack stack = getStack();
		if (dataSource == null) {
			dataSource = "top";
		}
		statusState = new IteratorStatus.StatusState();
		status = new IteratorStatus(statusState);
		columnModels = new ArrayList<ColumnModel>();
		iterator = MakeIterator.convert(findValue(dataSource));
		stack.getContext().put(GRID_STATE_KEY, GridState.DRAW_HEADER);
		stack.getContext().put(GRID_ID_KEY, id);
		stack.getContext().put(CURRENT_SORT_COLUMN_KEY, pagination + ".sortColumn");
		stack.getContext().put(CURRENT_SORT_MODE_KEY, pagination + ".sortMode");
		stack.getContext().put(PAGINATION_KEY, pagination);
		stack.getContext().put(GRID_COLUMN_MODEL_KEY, columnModels);
		if ((iterator != null) && iterator.hasNext()) {
			statusState.setLast(!iterator.hasNext());
			stack.getContext().put(STATUS_STATE_KEY, status);

		}
		StringBuffer objBuf = new StringBuffer();
		objBuf.append("<div id='");
		objBuf.append(id);
		objBuf.append("'>");

		objBuf.append("<form name='").append(id).append("_form'").append(" id='").append(id).append("_form' ");
		objBuf.append(" action='").append(action).append("'  method='post'>");
		objBuf.append("<table");
		if (this.cssClass != null)
			objBuf.append(" CLASS=\"gridTable " + this.cssClass + "\"");
		else{
			objBuf.append(" CLASS=\"gridTable" + this.cssClass + "\"");
		}
		objBuf.append(" BORDER=\"" + this.border + "\"");
		objBuf.append(" WIDTH=\"" + this.width + "%\"");
		objBuf.append(" CELLSPACING=" + this.cellSpacing);
		objBuf.append(" CELLPADDING=" + this.cellPadding);
		if (this.id != null)
			objBuf.append(" ID=\"" + this.id + "\"");
		if (this.name != null)
			objBuf.append(" NAME=\"" + this.name + "\"");
		if (this.foreColor != null)
			objBuf.append(" COLOR=\"" + this.foreColor + "\"");
		if (this.bgColor != null)
			objBuf.append(" BGCOLOR=\"" + this.bgColor + "\"");
		objBuf.append("><thead>\r\n");
		try {
			writer.write(objBuf.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;

	}

	public boolean end(Writer writer, String body) {
		ValueStack stack = getStack();
		GridComponent.GridState gridCurrentState = (GridComponent.GridState) stack.getContext().get(GRID_STATE_KEY);
		if (iterator != null && gridCurrentState.equals(GridState.DRAW_BODY)) {
			stack.pop();
			// Update status
			if (status != null) {
				if (!iterator.hasNext()) {
					statusState.setLast(true);
				} else {
					statusState.setLast(false);
					statusState.next(); // Increase counter
				}
			}
		}
		if (iterator != null && iterator.hasNext()) {
			stack.getContext().put(GRID_STATE_KEY, GridState.DRAW_BODY);
			Object currentValue = iterator.next();
			stack.push(currentValue);
			String id = getId();
			if ((id != null) && (currentValue != null)) {
				stack.getContext().put(id, currentValue);
			}
			return true;
		} else if (gridCurrentState.equals(GridState.DRAW_HEADER) || gridCurrentState.equals(GridState.DRAW_BODY)) {
			// 没有记录的情况
			stack.getContext().put(GRID_STATE_KEY, GridState.DRAW_FOOTER);
			return true;
		} else if (gridCurrentState.equals(GridState.DRAW_FOOTER)) {

			stack.getContext().remove(GRID_ID_KEY);
			stack.getContext().remove(CURRENT_SORT_COLUMN_KEY);
			stack.getContext().remove(CURRENT_SORT_MODE_KEY);

			StringBuffer objBuf = new StringBuffer();

			objBuf.append("</table>");
			objBuf.append("<input id='" + pagination + ".sortColumn" + "' type='hidden' name='" + pagination
					+ ".sortColumn" + "' value='"
					+ StringUtils.defaultString((String) this.findValue(pagination + ".sortColumn")) + "'>");
			objBuf.append("<input id='" + pagination + ".sortMode" + "' type='hidden' name='" + pagination
					+ ".sortMode" + "' value='"
					+ StringUtils.defaultString((String) this.findValue(pagination + ".sortMode")) + "'>");
			String sortColumnIndex = ServletActionContext.getRequest().getParameter("sortColumnIndex");
			objBuf.append("<input id='sortColumnIndex" + "' type='hidden' name='sortColumnIndex" + "' value='"
					+ sortColumnIndex + "'>");
			objBuf.append("<input id='" + pagination + ".groupField" + "' type='hidden' name='" + pagination
					+ ".groupField" + "' value='"
					+ StringUtils.defaultString((String) this.findValue(pagination + ".groupField")) + "'>");
			objBuf.append("<input id='" + pagination + ".groupSort" + "' type='hidden' name='" + pagination
					+ ".groupSort" + "' value='"
					+ StringUtils.defaultString((String) this.findValue(pagination + ".groupSort")) + "'>");
			Map parameters = ActionContext.getContext().getParameters();
			Set parameterSet = parameters.keySet();
			for (Iterator it = parameterSet.iterator(); it.hasNext();) {
				String key = (String) it.next();
				if (parameters.get(key) != null && !key.endsWith("limit") && !key.endsWith("start")
						&& !key.endsWith("totalCount") && !key.endsWith("sortColumn") && !key.endsWith("sortMode")
						&& !key.endsWith("groupField") && !key.endsWith("groupSort")
						&& !key.endsWith("sortColumnIndex")) {
					StringBuffer value = new StringBuffer();
					if (parameters.get(key) instanceof String[]) {
						String values[] = (String[]) parameters.get(key);
						for (int i = 0; i < values.length; i++) {
							value.append(values[i]);
							if (i != values.length - 1)
								value.append(",");
						}
					} else {
						value.append(parameters.get(key));
					}

					objBuf.append(
							(new StringBuilder("<input type=\"hidden\" name=\"")).append(key.toString().replace("\"", "&quot;")).append("\" value=\"")
									.toString()).append(value.toString().replace("\"", "&quot;")).append("\"/>");
				}
			}
			objBuf.append("</form></div>");
			if (columnModels != null) {
				objBuf.append("<script>");
				objBuf.append("$(function() {");
				objBuf.append("$('#").append(this.id).append("').initGrid({columeModel:[");
				int i = 0;
				for (Object o : columnModels) {
					ColumnModel columnModel = (ColumnModel) o;
					if (i > 0) {
						objBuf.append(",");
					}
					objBuf.append("{colIndex:'").append(columnModel.getColIndex());
					objBuf.append("',type:'").append(columnModel.getType());
					if (columnModel.getDataField() != null) {
						objBuf.append("',dataField:'").append(columnModel.getDataField());
					}
					if (columnModel.getDataFormat() != null) {
						objBuf.append("',dataFormat:'").append(columnModel.getDataFormat().replaceAll("'", "\\\\'"));
					}
					if (columnModel.getRenderTo() != null) {
						objBuf.append("',renderTo:'").append(columnModel.getRenderTo().replaceAll("'", "\\\\'"));
					}
					if (columnModel.getLinkText() != null) {
						objBuf.append("',linkText:'").append(columnModel.getLinkText().replaceAll("'", "\\\\'"));
					}
					if (columnModel.getLinkUrl() != null) {
						objBuf.append("',linkUrl:'").append(columnModel.getLinkUrl().replaceAll("'", "\\\\'"));
					}
					if (columnModel.getTarget() != null) {
						objBuf.append("',target:'").append(columnModel.getTarget().replaceAll("'", "\\\\'"));
					}
					if (columnModel.getImageSrc() != null) {
						objBuf.append("',imageSrc:'").append(columnModel.getImageSrc().replaceAll("'", "\\\\'"));
					}
					if (columnModel.getAlterText() != null) {
						objBuf.append("',alterText:'").append(columnModel.getAlterText().replaceAll("'", "\\\\'"));
					}
					if (columnModel.getMaxLength() >0) {
						objBuf.append("',maxLength:'").append(columnModel.getMaxLength());
					}
					objBuf.append("',imageBorder:'").append(columnModel.getImageBorder());
					objBuf.append("',imageHeight:'").append(columnModel.getImageWidth());
					objBuf.append("'}");
					i++;
				}

				objBuf.append("],recNum:'").append(status.getCount()).append("'");
				if (pagination != null) {
					objBuf.append(",pageConfig:'" + pagination + "'");
				}
				if (stack.getContext().containsKey(GRID_SELECT_MODE_KEY)) {
					String isSingle = (String) stack.getContext().get(GRID_SELECT_MODE_KEY);
					objBuf.append(",isSingle:'").append(isSingle).append("'");
					stack.getContext().remove(GRID_SELECT_MODE_KEY);
				}
				if (stack.getContext().containsKey(GRID_GROUP_CONFIG_KEY)) {
					GroupConfig groupConfig = (GroupConfig) stack.getContext().get(GRID_GROUP_CONFIG_KEY);
					objBuf.append(",groupConfig:{dataField:'").append(groupConfig.getDataField()).append("',lable:'")
							.append(groupConfig.getLable()).append("',sortMode:'").append(groupConfig.getSortMode())
							.append("'}");
					stack.getContext().remove(GRID_SELECT_MODE_KEY);
				}
				objBuf.append(",dataSource:'").append(dataSource).append("'");
				objBuf.append(",theme:'").append(StringUtils.defaultIfEmpty(theme, "JSP")).append("'");
				if (stack.getContext().containsKey(GRID_ONCLICK_KEY)) {
					String onclick = (String) stack.getContext().get(GRID_ONCLICK_KEY);
					objBuf.append(",onclick:'").append(StringUtils.defaultString(onclick)).append("'");
					stack.getContext().remove(GRID_ONCLICK_KEY);
				}

				objBuf.append("});	})");
				objBuf.append("</script>");
				stack.getContext().remove(GRID_COLUMN_MODEL_KEY);

			}

			if (status != null) {
				stack.getContext().remove(STATUS_STATE_KEY);
			}
			stack.getContext().remove(PAGINATION_KEY);
			super.end(writer, objBuf.toString());
			return false;
		}
		return false;
	}

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public int getCellPadding() {
		return cellPadding;
	}

	public void setCellPadding(int cellPadding) {
		this.cellPadding = cellPadding;
	}

	public int getCellSpacing() {
		return cellSpacing;
	}

	public void setCellSpacing(int cellSpacing) {
		this.cellSpacing = cellSpacing;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getForeColor() {
		return foreColor;
	}

	public void setForeColor(String foreColor) {
		this.foreColor = foreColor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

}
