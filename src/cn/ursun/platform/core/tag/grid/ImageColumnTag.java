/*------------------------------------------------------------------------------
 * PACKAGE: com.freeware.gridtag
 * FILE   : TextColumn.java
 * CREATED: Jul 22, 2004
 * AUTHOR : Prasad P. Khandekar
 *------------------------------------------------------------------------------
 * Change Log:
 *-----------------------------------------------------------------------------*/
package cn.ursun.platform.core.tag.grid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;

import cn.ursun.platform.core.tag.grid.component.ImageComponent;
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
public final class ImageColumnTag extends ColumnTagSupport {

	private int imageWidth;

	private int imageHeight;

	private int imageBorder;

	private String imageSrc;

	private String linkUrl;

	private String alterText;

	private String target;

	public ImageColumnTag() {
		super();
		this.imageWidth = -1;
		this.imageHeight = -1;
		this.imageBorder = -1;
	}

	public Component getBean(ValueStack valuestack, HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse) {
		return new ImageComponent(valuestack);
	}

	protected void populateParams() {
		super.populateParams();
		ImageComponent bean = (ImageComponent) getComponent();
		bean.setImageBorder(imageBorder);
		bean.setImageHeight(imageHeight);
		bean.setImageSrc(imageSrc);
		bean.setImageWidth(imageWidth);
		bean.setTarget(target);
		bean.setAlterText(alterText);
		bean.setLinkUrl(linkUrl);

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
