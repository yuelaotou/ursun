package cn.ursun.platform.core.tag.grid.domain;

public class ColumnModel {

	public enum ColumeType {
		TEXT, NUMBER, DATE, ANCHOR, CUSTOM, IMAGE, ROWNUM, SELECT;

		public String toString() {
			if (this == TEXT)
				return "TEXT";
			else if (this == NUMBER)
				return "NUMBER";
			else if (this == DATE)
				return "DATE";
			else if (this == ANCHOR)
				return "ANCHOR";
			else if (this == CUSTOM)
				return "CUSTOM";
			else if (this == IMAGE)
				return "IMAGE";
			else if (this == ROWNUM)
				return "ROWNUM";
			else if (this == SELECT)
				return "SELECT";
			else
				return "";
		}
	};

	private int colIndex;

	private ColumeType type = null;

	private String dataFormat = null;

	private String renderTo = null;

	private String dataField = null;

	private String linkUrl = null;

	private String linkText = null;

	private String target = null;

	private int imageBorder;

	private String imageSrc = null;

	private int imageWidth;

	private int imageHeight;

	private String isSingle;

	private String alterText = null;

	private int maxLength ;

	public int getColIndex() {
		return colIndex;
	}

	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	public ColumeType getType() {
		return type;
	}

	public void setType(ColumeType type) {
		this.type = type;
	}

	public String getRenderTo() {
		return renderTo;
	}

	public void setRenderTo(String renderTo) {
		this.renderTo = renderTo;
	}

	public String getDataField() {
		return dataField;
	}

	public void setDataField(String dataField) {
		this.dataField = dataField;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
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

	public String getAlterText() {
		return alterText;
	}

	public void setAlterText(String alterText) {
		this.alterText = alterText;
	}

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public String getLinkText() {
		return linkText;
	}

	public void setLinkText(String linkText) {
		this.linkText = linkText;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIsSingle() {
		return isSingle;
	}

	public void setIsSingle(String isSingle) {
		this.isSingle = isSingle;
	}

	
	public int getMaxLength() {
		return maxLength;
	}

	
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

}
