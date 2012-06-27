package cn.ursun.platform.core.tag.grid.component;

import java.io.Writer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.util.ValueStack;

public class ColumnModelComponent extends SupportComponent {

	private String cssClass = null;

	private String header;

	private String key;

	private int width;

	private int height;

	private int border;

	private String bgColor;

	private String foreColor;

	private String hAlign;

	private String vAlign;

	private String imgAsc;

	private String imgDes;

	private boolean sortable;

	private String sortColumn;

	private int headerColIndex;

	public ColumnModelComponent(ValueStack stack) {
		super(stack);
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public boolean start(Writer writer) {
		ValueStack stack = getStack();
		GridComponent.GridState gridCurrentState = (GridComponent.GridState) stack.getContext().get(
				GridComponent.GRID_STATE_KEY);
		if (!gridCurrentState.equals(GridComponent.GridState.DRAW_HEADER)) {
			return false;
		}

		if (stack.getContext().containsKey(GridHeaderComponent.GRID_HEADER_COL_INDEX_KEY)) {
			headerColIndex = ((Integer) stack.getContext().get(GridHeaderComponent.GRID_HEADER_COL_INDEX_KEY))
					.intValue();
		}

		if (key != null) {
			header = (new StringBuilder()).append("%{getText('").append(key).append("')}").toString();
		}
		if (header != null) {
			header = findString(header);
		}
		this.imgAsc = StringUtils.defaultIfEmpty(this.imgAsc, super.contextPath
				+ "/platform/common/images/grid/ImgAsc.gif");
		this.imgDes = StringUtils.defaultIfEmpty(this.imgDes, super.contextPath
				+ "/platform/common/images/grid/ImgDesc.gif");
		return true;
	}

	public boolean end(Writer writer, String body) {
		ValueStack stack = getStack();
		GridComponent.GridState gridCurrentState = (GridComponent.GridState) stack.getContext().get(
				GridComponent.GRID_STATE_KEY);
		if (!gridCurrentState.equals(GridComponent.GridState.DRAW_HEADER)) {
			return false;
		}
		super.end(writer, renderHeader());
		if (stack.getContext().containsKey(GridHeaderComponent.GRID_HEADER_COL_INDEX_KEY)) {
			stack.getContext().put(GridHeaderComponent.GRID_HEADER_COL_INDEX_KEY, ++headerColIndex);
		}
		return true;
	}

	public String renderHeader() {
		ValueStack stack = getStack();
		String columnCount = (String) stack.getContext().get(GridComponent.COLUMN_COUNT_KEY);
		String curSortColumn = (String) stack.getContext().get(GridComponent.CURRENT_SORT_COLUMN_KEY);
		String curSortMode = (String) stack.getContext().get(GridComponent.CURRENT_SORT_MODE_KEY);
		String gridId = (String) stack.getContext().get(GridComponent.GRID_ID_KEY);
		int colCount = StringUtils.isEmpty(columnCount) ? 0 : Integer.parseInt(columnCount);
		stack.getContext().put(GridComponent.COLUMN_COUNT_KEY, String.valueOf(++colCount));
		StringBuffer strBuf = new StringBuffer();
		String strTxt = null;
		if (this.header == null) {
			strTxt = "&nbsp;";
		} else {
			strTxt = this.header;
		}

		strBuf.append("<td");
		if (this.width > 0)
			strBuf.append(" WIDTH=\"" + this.width + "%\"");
		if (this.height > 0)
			strBuf.append(" HEIGHT=\"" + this.height + "\"");
		if (this.cssClass != null)
			strBuf.append(" CLASS=\"gridHeader " + this.cssClass + "\"");
		else
			strBuf.append(" CLASS=\"gridHeader\"");
		if (this.hAlign != null)
			strBuf.append(" ALIGN=\"" + this.hAlign.toLowerCase() + "\"");
		// else if (this instanceof SelectColumn) {
		// strBuf.append(" ALIGN=\"center\"");
		// }
		if (this.vAlign != null)
			strBuf.append(" VALIGN=\"" + this.vAlign.toLowerCase() + "\"");

		if (this.bgColor != null)
			strBuf.append(" BGCOLOR=\"" + this.bgColor + "\"");
		if (this.foreColor != null)
			strBuf.append(" COLOR=\"" + this.foreColor + "\"");

		strBuf.append(">");
		strBuf.append("<div CLASS='grid-inner' ");
		if (this.sortable && this.sortColumn != null) {
			strBuf.append(" style=\"cursor: pointer;\" onClick=\"$('#" + gridId + "').doSortGrid(this,'"
					+ headerColIndex + "','");
			strBuf.append(this.sortColumn);
			strBuf.append("')\"><strong>").append(strTxt).append("</strong>");
			strBuf.append("<div class='grid-hd-tool'><span class='grid-hd-icon ");
			String sortColumnIndex = ServletActionContext.getRequest().getParameter("sortColumnIndex");
			if (String.valueOf(headerColIndex).equals(sortColumnIndex)) {
				if ("DESC".equals(findValue(curSortMode))) {
					strBuf.append("grid-hd-desc");
				} else {
					strBuf.append("grid-hd-asc");
				}
			}
			strBuf.append("'/></div>");
		} else {
			strBuf.append("/>");
			strBuf.append("<strong>").append(strTxt).append("</strong>");
		}
		strBuf.append("</div></td>");
		return strBuf.toString();
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
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

	public String getHAlign() {
		return hAlign;
	}

	public void setHAlign(String align) {
		hAlign = align;
	}

	public String getVAlign() {
		return vAlign;
	}

	public void setVAlign(String align) {
		vAlign = align;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public String getImgAsc() {
		return imgAsc;
	}

	public void setImgAsc(String imgAsc) {
		this.imgAsc = imgAsc;
	}

	public String getImgDes() {
		return imgDes;
	}

	public void setImgDes(String imgDes) {
		this.imgDes = imgDes;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

}
