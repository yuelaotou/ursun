package cn.ursun.platform.core.tag.grid.component;

import java.io.Writer;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.util.ValueStack;

public class GridSelectAllComponent extends SupportComponent {

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

	private boolean isSingle;

	public GridSelectAllComponent(ValueStack stack) {
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
		stack.getContext().put(GridComponent.GRID_SELECT_MODE_KEY, String.valueOf(isSingle));
		if (key != null) {
			header = (new StringBuilder()).append("%{getText('").append(key).append("')}").toString();
		}
		if (header != null) {
			header = findString(header);
		}
		return true;
	}

	public boolean end(Writer writer, String body) {
		ValueStack stack = getStack();
		GridComponent.GridState gridCurrentState = (GridComponent.GridState) stack.getContext().get(
				GridComponent.GRID_STATE_KEY);
		if (gridCurrentState.equals(GridComponent.GridState.DRAW_HEADER)) {
			super.end(writer, renderHeader());
			return false;
		} else if (!gridCurrentState.equals(GridComponent.GridState.DRAW_FOOTER)) {
			stack.getContext().remove(GridComponent.GRID_SELECT_MODE_KEY);
			return false;
		}
		return false;
	}

	public String renderHeader() {
		ValueStack stack = getStack();
		String columnCount = (String) stack.getContext().get(GridComponent.COLUMN_COUNT_KEY);
		int colCount = StringUtils.isEmpty(columnCount) ? 0 : Integer.parseInt(columnCount);
		stack.getContext().put(GridComponent.COLUMN_COUNT_KEY, String.valueOf(++colCount));
		String gridId = (String) stack.getContext().get(GridComponent.GRID_ID_KEY);
		StringBuffer strBuf = new StringBuffer();
		String strTxt = null;
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
		else {
			strBuf.append(" ALIGN=\"center\"");
		}
		if (this.vAlign != null)
			strBuf.append(" VALIGN=\"" + this.vAlign.toLowerCase() + "\"");

		if (this.bgColor != null)
			strBuf.append(" BGCOLOR=\"" + this.bgColor + "\"");
		if (this.foreColor != null)
			strBuf.append(" COLOR=\"" + this.foreColor + "\"");

		strBuf.append(">");
		strBuf.append("<div CLASS='gt-inner' >");
		if (!isSingle) {
			if (this.header == null) {
				this.header = this.findValue("getText('grid.selectall')").toString();
			}
			strBuf.append(
					"<input type='checkbox' id='selectAll' onclick=\"$('#" + gridId + "').selectAllRecord(this)\"/>")
					.append("<strong>").append(this.header).append("</strong>");
		} else {
			if (this.header == null) {
				this.header = this.findValue("getText('grid.select')").toString();
			}
			strBuf.append("<strong>").append(this.header).append("</strong>");
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

	public boolean isSingle() {
		return isSingle;
	}

	public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}

}
