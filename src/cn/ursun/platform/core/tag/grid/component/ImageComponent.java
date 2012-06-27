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
public final class ImageComponent extends ColumnComponentSupport {

	private int imageWidth;

	private int imageHeight;

	private int imageBorder;

	private String imageSrc;

	private String linkUrl;

	private String alterText;

	private String target;

	public ImageComponent(ValueStack stack) {
		super(stack);
	}

	public void addColumnModel(List<ColumnModel> columnModels) {
		ColumnModel col = new ColumnModel();
		col.setColIndex(colIndex);
		col.setImageBorder(imageBorder);
		col.setImageHeight(imageHeight);
		col.setImageWidth(imageWidth);
		col.setImageSrc(imageSrc);
		col.setAlterText(alterText);
		col.setTarget(target);
		col.setLinkUrl(linkUrl);
		col.setDataField(dataField);
		col.setType(ColumnModel.ColumeType.IMAGE);
		columnModels.add(col);
	}

	public String renderDetail(Object value) {
		StringBuffer objBuf = new StringBuffer();
		String strValue = (value == null) ? "" : value.toString();
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
		objBuf.append("<a HREF=");
		objBuf.append(resolveFields(this.linkUrl));
		objBuf.append(" ");
		if (this.target != null)
			objBuf.append(" TARGET=\"" + this.target + "\"");
		objBuf.append(">");
		objBuf.append("<img SRC=\"");
		objBuf.append(this.imageSrc);
		objBuf.append("\"");
		if (this.imageWidth != -1)
			objBuf.append(" WIDTH=" + this.imageWidth);
		if (this.imageHeight != -1)
			objBuf.append(" HEIGHT=" + this.imageHeight);
		if (this.imageBorder != -1)
			objBuf.append(" BORDER=" + this.imageBorder);
		objBuf.append(" ALTER=\"" + this.alterText + "\"");

		objBuf.append(">");
		objBuf.append("</a>");
		objBuf.append("<span style='display:none' rowIndex='" + status.getIndex() + "' colIndex='" + this.colIndex
				+ "' dataField='" + this.dataField + "'>");
		objBuf.append(strValue);
		objBuf.append("</span>");
		objBuf.append("</td>");
		return objBuf.toString();
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public int getImageBorder() {
		return imageBorder;
	}

	public void setImageBorder(int imageBorder) {
		this.imageBorder = imageBorder;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getAlterText() {
		return alterText;
	}

	public void setAlterText(String alterText) {
		this.alterText = alterText;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
