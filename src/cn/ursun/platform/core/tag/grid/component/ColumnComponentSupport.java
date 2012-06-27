package cn.ursun.platform.core.tag.grid.component;

import java.io.Writer;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.views.jsp.IteratorStatus;

import cn.ursun.platform.core.tag.grid.domain.ColumnModel;
import com.opensymphony.xwork2.util.ValueStack;

public abstract class ColumnComponentSupport extends SupportComponent {

	protected int width;

	protected int height;

	protected int border;

	protected String name;

	protected String bgColor;

	protected String foreColor;

	protected String cssClass;

	protected String hAlign;

	protected String vAlign;

	protected String dataField;

	protected int colIndex;

	protected IteratorStatus status;

	public ColumnComponentSupport(ValueStack stack) {
		super(stack);
	}

	public boolean start(Writer writer) {
		ValueStack stack = getStack();
		if (stack.getContext().containsKey(GridTbodyComponent.GRID_COL_INDEX_KEY)) {
			colIndex = ((Integer) stack.getContext().get(GridTbodyComponent.GRID_COL_INDEX_KEY)).intValue();
			status = (IteratorStatus) stack.getContext().get(GridComponent.STATUS_STATE_KEY);
		}
		return true;
	}

	public boolean end(Writer writer, String body) {
		ValueStack stack = getStack();
		GridComponent.GridState gridCurrentState = (GridComponent.GridState) stack.getContext().get(
				GridComponent.GRID_STATE_KEY);
		if (gridCurrentState.equals(GridComponent.GridState.DRAW_HEADER)) {
			List<ColumnModel> columnModels = (List<ColumnModel>) stack.getContext().get(
					GridComponent.GRID_COLUMN_MODEL_KEY);
			addColumnModel(columnModels);
			super.end(writer, drawTemplateCell());
		} else if (gridCurrentState.equals(GridComponent.GridState.DRAW_BODY)) {
			Object value=getStack().findValue(this.dataField);
			if(value!=null&&value instanceof String){
				super.end(writer, renderDetail(convertXMLEntity(value.toString())));
			}else{
				super.end(writer, renderDetail(getStack().findValue(this.dataField)));
			}
		}
		if (stack.getContext().containsKey(GridTbodyComponent.GRID_COL_INDEX_KEY)) {
			stack.getContext().put(GridTbodyComponent.GRID_COL_INDEX_KEY, ++colIndex);
		}
		return true;
	}

	public abstract String renderDetail(Object value);

	public abstract void addColumnModel(List<ColumnModel> columnModels);

	public String drawTemplateCell() {
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
		if (this.vAlign != null)
			objBuf.append(" VALIGN=\"" + this.vAlign.toLowerCase() + "\"");

		if (this.bgColor != null)
			objBuf.append(" BGCOLOR=\"" + this.bgColor + "\"");
		if (this.foreColor != null)
			objBuf.append(" COLOR=\"" + this.foreColor + "\"");

		objBuf.append("><span rowIndex='-1' colIndex='" + this.colIndex + "' dataField='" + this.dataField + "'>");
		objBuf.append("</span></td>");
		return objBuf.toString();
	}

	protected String resolveFields(String pstrUrl) throws ClassCastException {
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
				strRet = strRet.substring(0, intPos)
						+ convertXMLEntity(StringUtils.defaultString((String) getStack().findValue(strCol))).replace("\"", "\\\"")
								.replace("\'", "\\\'") + strRet.substring(intEnd + 1);
			}
			intPos = strRet.indexOf("{", intPos + 1);
		}
		return strRet;
	}

	protected String formatField(Object pobjVal, String pstrFmt) throws ClassCastException {
		String strRet = null;
		Format objFmt = null;
		if (pobjVal == null) {
			return "";
		} else if (pstrFmt == null) {
			return pobjVal.toString();
		}
		try {
			if (pobjVal instanceof java.sql.Date || pobjVal instanceof java.util.Date) {
				objFmt = new SimpleDateFormat(pstrFmt);
				strRet = objFmt.format(pobjVal);
			} else if (pobjVal instanceof Number) {
				objFmt = new DecimalFormat(pstrFmt);
				strRet = objFmt.format(pobjVal);
			} else
				strRet = pobjVal.toString();
		} catch (NullPointerException NPExIgnore) {
			NPExIgnore.printStackTrace();
		} catch (IllegalArgumentException IArgExIgnore) {
			IArgExIgnore.printStackTrace();
		}
		if (strRet == null)
			strRet = GridComponent.DEFAULT_NULLTEXT;
		return strRet;
	}

	public static String convertXMLEntity(String srcStr) {

		srcStr = srcStr != null ? srcStr.toString() : "";
		return srcStr.replaceAll("&", "&amp;").replaceAll("<",
				"&lt;").replaceAll("\"", "&quot;").replaceAll(">", "&gt;");
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
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

	public String getDataField() {
		return dataField;
	}

	public void setDataField(String dataField) {
		this.dataField = dataField;
	}

}
