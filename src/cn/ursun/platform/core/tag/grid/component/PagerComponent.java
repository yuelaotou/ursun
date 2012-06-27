/*------------------------------------------------------------------------------
 * PACKAGE: com.freeware.gridtag
 * FILE   : GridPager.java
 * CREATED: Jul 24, 2004
 * AUTHOR : Prasad P. Khandekar
 *------------------------------------------------------------------------------
 * Change Log:
 *-----------------------------------------------------------------------------*/
package cn.ursun.platform.core.tag.grid.component;

import java.io.Writer;

import org.apache.commons.lang.StringUtils;

import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.tag.grid.domain.PageConfig;
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
public final class PagerComponent extends SupportComponent {

	private String imgFirst = null;

	private String imgPrevious = null;

	private String imgNext = null;

	private String imgLast = null;

	private String disableImgFirst = null;

	private String disableImgPrevious = null;

	private String disableImgNext = null;

	private String disableImgLast = null;

	private int intPageSize = 10;

	private int intStart = 0;

	private int intTotalRecords = -1;

	/**
	 * 分页样式
	 */
	private String theme;

	public PagerComponent(ValueStack stack) {
		super(stack);
	}

	public boolean start(Writer writer) {
		ValueStack stack = getStack();
		GridComponent.GridState gridCurrentState = (GridComponent.GridState) stack.getContext().get(
				GridComponent.GRID_STATE_KEY);
		if (!gridCurrentState.equals(GridComponent.GridState.DRAW_FOOTER)) {
			return false;
		}
		String pagination = (String) stack.getContext().get(GridComponent.PAGINATION_KEY);
		this.imgFirst = StringUtils.defaultIfEmpty(this.imgFirst, contextPath
				+ "/platform/common/images/grid/First.gif");
		this.imgPrevious = StringUtils.defaultIfEmpty(this.imgPrevious, contextPath
				+ "/platform/common/images/grid/Previous.gif");
		this.imgNext = StringUtils.defaultIfEmpty(this.imgNext, contextPath
				+ "/platform/common/images/grid/Next.gif");
		this.imgLast = StringUtils.defaultIfEmpty(this.imgLast, contextPath
				+ "/platform/common/images/grid/Last.gif");
		this.disableImgFirst = StringUtils.defaultIfEmpty(this.disableImgFirst, contextPath
				+ "/platform/common/images/grid/disableFirst.gif");
		this.disableImgPrevious = StringUtils.defaultIfEmpty(this.disableImgPrevious, contextPath
				+ "/platform/common/images/grid/disablePrevious.gif");
		this.disableImgNext = StringUtils.defaultIfEmpty(this.disableImgNext, contextPath
				+ "/platform/common/images/grid/disableNext.gif");
		this.disableImgLast = StringUtils.defaultIfEmpty(this.disableImgLast, contextPath
				+ "/platform/common/images/grid/disableLast.gif");
		this.theme = StringUtils.defaultIfEmpty(this.theme, "text");
		intStart = ((Integer) findValue(pagination + ".start", Integer.class)).intValue();
		intTotalRecords = ((Integer) findValue(pagination + ".totalCount", Integer.class)).intValue();
		intPageSize = ((Integer) findValue(pagination + ".limit", Integer.class)).intValue();
		if (intPageSize <= 0) {
			intPageSize = Pagination.PAGE_SIZE;
		}
		PageConfig pageConfig = new PageConfig();
		pageConfig.setStart(pagination + ".start");
		pageConfig.setTotalRecords(pagination + ".totalCount");
		pageConfig.setPageSize(pagination + ".limit");
		pageConfig.setTheme(theme);
		stack.getContext().put(GridComponent.GRID_PAGE_CONFIG_KEY, pageConfig);
		return true;

	}

	public boolean end(Writer writer, String body) {
		ValueStack stack = getStack();
		GridComponent.GridState gridCurrentState = (GridComponent.GridState) stack.getContext().get(
				GridComponent.GRID_STATE_KEY);
		if (!gridCurrentState.equals(GridComponent.GridState.DRAW_FOOTER)) {
			return false;
		}
		super.end(writer, renderPager());
		return true;
	}

	public String renderPager() {

		String gridId = (String) stack.getContext().get(GridComponent.GRID_ID_KEY);
		String pagination = (String) stack.getContext().get(GridComponent.PAGINATION_KEY);
		StringBuffer objBuf = new StringBuffer();
		int totalPage = 0;
		int currPage = intStart / intPageSize + 1;
		totalPage = Math.round(intTotalRecords / intPageSize);
		if (intTotalRecords % intPageSize > 0) {
			totalPage++;
		}
		objBuf.append("<div id='pager' class='gridPager'> ");
		if (PageConfig.PAGE_THEME.TEXT.equals(this.theme)) {
			
			objBuf.append("<p style='padding-top: 3px;height:20px'><span onclick=\"$('#" + gridId + "').gotoPageGrid('first')\" class=\"page_first ");
			if (currPage > 1 && totalPage > 0) {
				objBuf.append("page_first_enable");
			} else {
				objBuf.append("page_first_disable");
			}
			objBuf.append("\"   ></span>");

			objBuf.append("<span onclick=\"$('#" + gridId + "').gotoPageGrid('prev')\" class=\"page_previous ");
			if (currPage > 1) {
				objBuf.append("page_previous_enable");
			} else {
				objBuf.append("page_previous_disable");
			}
			objBuf.append("\"  ></span>");
			objBuf.append("<span onclick=\"$('#" + gridId + "').gotoPageGrid('next')\" class=\"page_next ");
			if (totalPage >= currPage + 1) {
				objBuf.append("page_next_enable");
			} else {
				objBuf.append("page_next_disable");
			}
			objBuf.append("\"  ></span>");
			objBuf.append("<span onclick=\"$('#" + gridId + "').gotoPageGrid('last')\" class=\"page_last ");
			if (currPage < totalPage) {
				objBuf.append("page_last_enable");
			} else {
				objBuf.append("page_last_disable");
			}
			objBuf.append("\"  ></span>");
			objBuf.append(this.findValue("getText('grid.beforePageText')").toString());
			objBuf.append("</p>");
			objBuf.append("<p style='height:20px'>");
			objBuf.append("<input name=\"currPage\" id=\"currPage\" style='width: 25px; height: 14px;' type=\"text\" value=\"").append(currPage).append(
					"\" size=\"2\" onkeydown=\"if(event.keyCode == 13){$('#" + gridId
							+ "').gotoPageGrid('gotoPage'); return false;}\">");
			objBuf.append("</p><p style='padding-top: 3px;height:20px'>");
			objBuf.append(this.findValue("getText('grid.afterPageText')")).append("&nbsp;");
			objBuf.append(this.findValue(
					"getText('grid.recordText',{'<span class=\"totalPage\">" + totalPage
							+ "</span>','<span class=\"totalRecords\">" + intTotalRecords + "</span>'})").toString());

			objBuf.append("</p></div>");
		} else {
			int startPage = currPage - 3 > 0 ? currPage - 3 : 1;
			if (startPage + 10 > totalPage) {
				startPage = totalPage - 9;
			}
			if (startPage < 1) {
				startPage = 1;
			}
			if (startPage > 1) {
				objBuf.append("<a HREF=\"javascript:$('#" + gridId + "').gotoPageGrid('gotoPage',1)\">[" + 1
						+ "]</a>...");
			}
			int i = startPage;
			for (; i < startPage + 10 && i <= totalPage; i++) {
				if (i == currPage) {
					objBuf.append("<strong>" + i + "</strong>");
				} else {
					objBuf.append("<a HREF=\"javascript:$('#" + gridId + "').gotoPageGrid('gotoPage'," + (i - 1)
							* intPageSize + ")\">[" + i + "]</a>\r\n");
				}
			}
			if (i < totalPage) {
				objBuf.append("...<a HREF=\"javascript:$('#" + gridId + "').gotoPageGrid('gotoPage')\">[" + totalPage
						+ "]</a>\r\n");
			}
		}

		objBuf.append("<input id='" + pagination + ".start" + "' type='hidden' name='" + pagination + ".start"
				+ "' value='" + intStart + "'>");
		objBuf.append("<input id='" + pagination + ".limit" + "' type='hidden' name='" + pagination + ".limit"
				+ "' value='" + intPageSize + "'>");
		objBuf.append("<input id='" + pagination + ".totalCount" + "' type='hidden' name='" + pagination
				+ ".totalCount" + "' value='" + intTotalRecords + "'>");
		objBuf.append("<input id='totalPage' type='hidden' name='totalPage' value='" + totalPage + "'>");
		return objBuf.toString();

	}

	public String getImgFirst() {
		return imgFirst;
	}

	public void setImgFirst(String imgFirst) {
		this.imgFirst = imgFirst;
	}

	public String getImgPrevious() {
		return imgPrevious;
	}

	public void setImgPrevious(String imgPrevious) {
		this.imgPrevious = imgPrevious;
	}

	public String getImgNext() {
		return imgNext;
	}

	public void setImgNext(String imgNext) {
		this.imgNext = imgNext;
	}

	public String getImgLast() {
		return imgLast;
	}

	public void setImgLast(String imgLast) {
		this.imgLast = imgLast;
	}

	public String getDisableImgFirst() {
		return disableImgFirst;
	}

	public void setDisableImgFirst(String disableImgFirst) {
		this.disableImgFirst = disableImgFirst;
	}

	public String getDisableImgPrevious() {
		return disableImgPrevious;
	}

	public void setDisableImgPrevious(String disableImgPrevious) {
		this.disableImgPrevious = disableImgPrevious;
	}

	public String getDisableImgNext() {
		return disableImgNext;
	}

	public void setDisableImgNext(String disableImgNext) {
		this.disableImgNext = disableImgNext;
	}

	public String getDisableImgLast() {
		return disableImgLast;
	}

	public void setDisableImgLast(String disableImgLast) {
		this.disableImgLast = disableImgLast;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

}
