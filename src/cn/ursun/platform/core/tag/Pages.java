package cn.ursun.platform.core.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.Component;

import cn.ursun.platform.core.dto.Pagination;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * <p>
 * Title: 分页逻辑Bean
 * </p>
 * <p>
 * Description: [描述]
 * </p>
 * <p>
 * Created on 2009-9-8
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
public class Pages extends Component {

	/**
	 * 起始记录号
	 */
	private String start;

	/**
	 * 总记录数
	 */
	private String totalCount;

	/**
	 * 分页样式css
	 */
	private String styleClass;

	/**
	 * 分页样式
	 */
	private String theme;

	/**
	 * 当前页
	 */
	private int cpage;

	private boolean ajax;

	private String target;

	private String pagination;

	private Pagination paginationObj;

	/**
	 * 是否显示总页码数
	 */
	private boolean showTotalRecord;

	/**
	 * 总页数
	 */
	private int totalPage;

	private String id;

	private String action;

	private String queryParamName;

	private String contextPath = ServletActionContext.getRequest().getContextPath();

	/**
	 * 每页显示数
	 */
	private String limit;

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public Pages(ValueStack valueStack) {
		super(valueStack);
	}

	@Override
	public boolean start(Writer writer) {
		boolean result = super.start(writer);
		try {
			StringBuilder str = new StringBuilder();
			Validate.notNull(pagination, "pagination required");
			// 从ValueStack中取出数值
			if (result) {
				if (pagination.startsWith("%{") && pagination.endsWith("}")) {
					pagination = pagination.substring(2, pagination.length() - 1);
					paginationObj = (Pagination) this.getStack().findValue(pagination, Pagination.class);

					totalCount = String.valueOf((paginationObj).getTotalCount());
					start = String.valueOf((paginationObj).getStart());
					limit = String.valueOf((paginationObj).getLimit());
					result = totalCount == null || Integer.parseInt(totalCount) <= 0 ? false : true;
					if (result) {
						result = start == null ? false : true;

					}
					if (result) {
						result = limit == null || Integer.parseInt(limit) <= 0 ? false : true;
					}
				} else {
					result = false;
				}
			}

			if (result) {
				if (ajax && StringUtils.isEmpty(target)) {
					result = false;
				} else {
					result = true;
				}
			}
			if (result) {
				cpage = Integer.parseInt(start) / Integer.parseInt(limit) + 1;

				totalPage = Math.round(Integer.parseInt(totalCount) / Integer.parseInt(limit));
				if (Integer.parseInt(totalCount) % Integer.parseInt(limit) > 0) {
					totalPage++;
				}
				str.append("<div ");
				if (styleClass != null) {
					str.append(" class='" + styleClass + "'>");
				} else {
					str.append(">");
				}

				// 文本样式
				if (theme == null || "text".equals(theme)) { // theme="text"样式
					// 当前页与总页数相等
					if (cpage == totalPage) {
						// 如果total = 1，则无需分页，显示“[第1页] [共1页]”
						if (totalPage == 1) {
							str.append("[第 " + cpage + " 页]");
							str.append(" [共 " + totalPage + " 页]");
						} else {
							// 到达最后一页,显示“[首页] [上一页] [末页]”
							str.append("<a href=\"javascript:gotoPage('" + id + "',0," + ajax + ",'" + target
									+ "')\">[首页]</a> <a href=\"javascript:gotoPage('" + id + "',");
							str.append(Integer.parseInt(start) - Integer.parseInt(limit));
							str.append("," + ajax + ",'" + target + "')\">[上一页]</a> <a href=\"javascript:gotoPage('"
									+ id + "',");
							str.append((totalPage - 1) * Integer.parseInt(limit));
							str.append("," + ajax + ",'" + target + "')\">[末页]</a>");
						}
					} else {
						// 当前页与总页数不相同
						if (cpage == 1) {
							// 第一页，显示“[首页] [下一页] [末页]”
							str.append("<a href=\"javascript:gotoPage('" + id + "',");
							str.append(0);
							str.append("," + ajax + ",'" + target + "')\">[首页]</a> <a href=\"javascript:gotoPage('"
									+ id + "',");
							str.append(Integer.parseInt(start) + Integer.parseInt(limit));
							str.append("," + ajax + ",'" + target + "')\">[下一页]</a> <a href=\"javascript:gotoPage('"
									+ id + "',");
							str.append((totalPage - 1) * Integer.parseInt(limit));
							str.append("," + ajax + ",'" + target + "')\">[末页]</a>");
						} else {
							// 不是第一页，显示“[首页] [上一页] [下一页] [末页]”
							str.append("<a href=\"javascript:gotoPage('" + id + "',");
							str.append(0);
							str.append("," + ajax + ",'" + target + "')\">[首页]</a> <a href=\"javascript:gotoPage('"
									+ id + "',");
							str.append(Integer.parseInt(start) - Integer.parseInt(limit));
							str.append("," + ajax + ",'" + target + "')\">[上一页]</a> <a href=\"javascript:gotoPage('"
									+ id + "',");
							str.append(Integer.parseInt(start) + Integer.parseInt(limit));
							str.append("," + ajax + ",'" + target + "')\">[下一页]</a> <a href=\"javascript:gotoPage('"
									+ id + "',");
							str.append((totalPage - 1) * Integer.parseInt(limit));
							str.append("," + ajax + ",'" + target + "')\">[末页]</a>");
						}
					}
					str.append("&nbsp;共" + totalPage + "页&nbsp;");
					str.append(totalCount + "条数据");
				}// 向前向后按钮翻页标签
				else if ("picText".equals(theme)) {
					// 当前页与总页数相等
					if (totalPage == 1) {
						str.append("共&nbsp;" + cpage + "&nbsp;页");
						str.append("   " + totalCount + "条记录");
					} else if (cpage == totalPage) {
						// 到达最后一页,显示到最前，上一页按钮”
						str
								.append("<a href=\"javascript:gotoPage('"
										+ id
										+ "',0,"
										+ ajax
										+ ",'"
										+ target
										+ "')\"><img src=\""
										+ contextPath
										+ "/platform/common/images/page/page_1.gif\" /></a> <a href=\"javascript:gotoPage('"
										+ id + "',");
						str.append(Integer.parseInt(start) - Integer.parseInt(limit));
						str.append("," + ajax + ",'" + target + "')\"><img src=\"" + contextPath
								+ "/platform/common/images/page/page_2.gif\" /></a><a href=\"#\"><img src=\""
								+ contextPath + "/platform/common/images/page/page_7.gif\" /></a><a href=\"");
						str.append("#\"><img src=\"" + contextPath
								+ "/platform/common/images/page/page_8.gif\" /></a>");

					} else {
						// 当前页与总页数不相同
						if (cpage == 1) {
							// 到达最后一页,显示到最后，下一页按钮”
							str
									.append("<a href=\"#\"><img src=\""
											+ contextPath
											+ "/platform/common/images/page/page_5.gif\" /></a> <a href=\"#\"><img src=\""
											+ contextPath
											+ "/platform/common/images/page/page_6.gif\" /></a> <a href=\"javascript:gotoPage('"
											+ id + "',");
							str.append(Integer.parseInt(start) + Integer.parseInt(limit));
							str
									.append(","
											+ ajax
											+ ",'"
											+ target
											+ "')\"><img src=\""
											+ contextPath
											+ "/platform/common/images/page/page_3.gif\" /></a> <a href=\"javascript:gotoPage('"
											+ id + "',");
							str.append((totalPage - 1) * Integer.parseInt(limit));
							str.append("," + ajax + ",'" + target + "')\"><img src=\"" + contextPath
									+ "/platform/common/images/page/page_4.gif\" /></a>");

						} else {
							// 不是第一页，显示“最前 上一页，下一页,到最后按钮
							str.append("<a href=\"javascript:gotoPage('" + id + "',");
							str.append(0);
							str
									.append(","
											+ ajax
											+ ",'"
											+ target
											+ "')\"><img src=\""
											+ contextPath
											+ "/platform/common/images/page/page_1.gif\" /></a> <a href=\"javascript:gotoPage('"
											+ id + "',");
							str.append(Integer.parseInt(start) - Integer.parseInt(limit));
							str
									.append(","
											+ ajax
											+ ",'"
											+ target
											+ "')\"><img src=\""
											+ contextPath
											+ "/platform/common/images/page/page_2.gif\" /></a> <a href=\"javascript:gotoPage('"
											+ id + "',");
							str.append(Integer.parseInt(start) + Integer.parseInt(limit));
							str
									.append(","
											+ ajax
											+ ",'"
											+ target
											+ "')\"><img src=\""
											+ contextPath
											+ "/platform/common/images/page/page_3.gif\" /></a> <a href=\"javascript:gotoPage('"
											+ id + "',");
							str.append((totalPage - 1) * Integer.parseInt(limit));
							str.append("," + ajax + ",'" + target + "')\"><img src=\"" + contextPath
									+ "/platform/common/images/page/page_4.gif\" /></a>");

						}
					}
					// 添加总页数标签
					if (totalPage != 1 && showTotalRecord) {
						str.append("第&nbsp;<input type=\"text\" style=\"height:14px; width:25px;\" value = '" + cpage
								+ "'  onkeydown = \"if(event.keyCode == 13){gotoPageNum('" + id + "',this.value,'" + limit + "','"
								+ totalPage + "','" + cpage + "'," + ajax + ",'" + target + "')}\" />&nbsp;页");
						str.append("&nbsp;共" + totalPage + "页&nbsp;");
						str.append(totalCount + "条数据");
					}
				} else if ("number".equals(theme)) { // theme="number"的数字样式 [1 2 3 4 5 6 7 8 9 10 > >>]
					// 如果只有一页，则无需分页

					if (totalPage == 1) {
						str.append("<strong>1</strong> ");
					} else {
						if (cpage > 1) {
							// 当前不是第一组，要显示“<< <”
							// <<：返回前一组第一页
							// <：返回前一页
							str.append("<a href=\"javascript:gotoPage('" + id + "',");
							str.append(0);
							str.append("," + ajax + ",'" + target + "')\">首页</a> ");
							str.append("<a href=\"javascript:gotoPage('" + id + "',");
							str.append((cpage - 2) * Integer.parseInt(limit));
							str.append("," + ajax + ",'" + target + "')\">上一页</a> ");
						}

						if (totalPage <= 10) {
							for (int i = 1; i <= totalPage; i++) {
								if (cpage == i) { // 当前页要加粗显示
									str.append("<strong>");
									str.append(i);
									str.append("</strong>");
								} else {
									str.append("<a href=\"javascript:gotoPage('" + id + "',");
									str.append((i - 1) * Integer.parseInt(limit));
									str.append("," + ajax + ",'" + target + "')\">[" + i + "]</a> ");
								}
							}
						} else {

							// 10个为一组显示
							if (cpage > 6 && cpage < totalPage - 4) {
								for (int i = (cpage - 6) + 1; i <= totalPage && i <= cpage + 4; i++) {
									if (cpage == i) { // 当前页要加粗显示
										str.append("<strong>");
										str.append(i);
										str.append("</strong>");
									} else {
										str.append("<a href=\"javascript:gotoPage('" + id + "',");
										str.append((i - 1) * Integer.parseInt(limit));
										str.append("," + ajax + ",'" + target + "')\">[" + i + "]</a> ");
									}
								}
							} else if (cpage <= 6) {
								for (int i = 1; i <= totalPage && i <= 10; i++) {
									if (cpage == i) { // 当前页要加粗显示
										str.append("<strong>");
										str.append(i);
										str.append("</strong>");
									} else {
										str.append("<a href=\"javascript:gotoPage('" + id + "',");
										str.append((i - 1) * Integer.parseInt(limit));
										str.append("," + ajax + ",'" + target + "')\">[" + i + "]</a> ");
									}
								}
							} else {
								for (int i = totalPage - 10; i <= totalPage; i++) {
									if (cpage == i) { // 当前页要加粗显示
										str.append("<strong>");
										str.append(i);
										str.append("</strong>");
									} else {
										str.append("<a href=\"javascript:gotoPage('" + id + "',");
										str.append((i - 1) * Integer.parseInt(limit));
										str.append("," + ajax + ",'" + target + "')\">[" + i + "]</a> ");
									}
								}
							}

						}
						// 如果多于1组并且不是最后一组，显示小一页, >>”
						if (cpage != totalPage) {
							// >>：返回下一组最后一页
							// >：返回下一页
							str.append("<a href=\"javascript:gotoPage('" + id + "',");
							str.append((cpage) * Integer.parseInt(limit));
							str.append("," + ajax + ",'" + target + "')\">下一页</a> ");
							str.append("<a href=\"javascript:gotoPage('" + id + "',");
							str.append((totalPage - 1) * Integer.parseInt(limit));
							str.append("," + ajax + ",'" + target + "')\">最后一页</a> ");
							str.toString();
						}
					}
					if (showTotalRecord) {
						str.append("&nbsp;共" + totalPage + "页&nbsp;");
						str.append(totalCount + "条数据");
					}
				}
				str.append("</div>");
				// str.append(this.findValue(
				// "getText('grid.afterPageText',{'<span class=\"totalPage\">" + totalPage
				// + "</span>','<span class=\"totalRecords\">" + totalCount + "</span>'})").toString());
				str.append("<form name='").append(id).append("'");
				str.append(" id='").append(id).append("' ");
				str.append(" action='").append(action).append("'  method='post'>");
				str.append("<input type=\"hidden\" name=\"").append(pagination).append(".limit\" value=\"").append(
						limit).append("\" id=\"").append(pagination).append(".limit\"/>");
				str.append("<input type=\"hidden\" name=\"").append(pagination).append(".start\" value=\"").append(
						start).append("\" id=\"start\"/>");
				str.append("<input type=\"hidden\" name=\"").append(pagination).append(".totalCount\" value=\"")
						.append(totalCount).append("\" id=\"").append(pagination).append(".totalCount\"/>");
				Map parameters = ActionContext.getContext().getParameters();
				Set parameterSet = parameters.keySet();
				for (Iterator it = parameterSet.iterator(); it.hasNext();) {
					String key = (String) it.next();

					if (parameters.get(key) != null && !key.endsWith("limit") && !key.endsWith("start")
							&& !key.endsWith("totalCount")) {
						StringBuffer value = new StringBuffer();

						if (parameters.get(key) instanceof String[]) {
							String[] values = (String[]) parameters.get(key);
							for (int i = 0; i < values.length; i++) {
								value.append(values[i]);
								if (i != values.length - 1) {
									value.append(",");
								}
							}

						} else {
							value.append(parameters.get(key));
						}

						str.append("<input type=\"hidden\" name=\"" + key.toString().replace("\"", "&quot;") + "\" value=\"").append(
								value.toString().replace("\"", "&quot;")).append("\"/>");
					}
				}

				str.append("</form>");
				writer.write(str.toString());
			}
		} catch (IOException ex) {
			Logger.getLogger(Pages.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getQueryParamName() {
		return queryParamName;
	}

	public void setQueryParamName(String queryParamName) {
		this.queryParamName = queryParamName;
	}

	public boolean getAjax() {
		return ajax;
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	public Pagination getPaginationObj() {
		return paginationObj;
	}

	public void setPaginationObj(Pagination paginationObj) {
		this.paginationObj = paginationObj;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public boolean getShowTotalRecord() {
		return showTotalRecord;
	}

	public void setShowTotalRecord(boolean showTotalRecord) {
		this.showTotalRecord = showTotalRecord;
	}

	public static String convertXMLEntity(String srcStr) {

		srcStr = srcStr != null ? srcStr.toString() : "";
		return srcStr.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replace("&", "&amp;");
	}

}
