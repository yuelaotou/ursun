/**
 * 文件名：ProfilingTimerBean.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Nov 12, 2008 9:57:37 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.aop.debug;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Nov 12, 2008 9:57:37 PM
 */
public class ProfilingTimerBean implements Serializable {

	private static final long serialVersionUID = -6180672043920208784L;

	private List<ProfilingTimerBean> children = new ArrayList<ProfilingTimerBean>();

	private ProfilingTimerBean parent = null;

	private String resource;

	private long startTime;

	private long totalTime;

	public ProfilingTimerBean(String resource) {
		this.resource = resource;
	}

	protected void addParent(ProfilingTimerBean parentBean) {
		this.parent = parentBean;
	}

	public ProfilingTimerBean getParent() {
		return parent;
	}

	public void addChild(ProfilingTimerBean child) {
		children.add(child);
		child.addParent(this);
	}

	public void setStartTime() {
		this.startTime = System.currentTimeMillis();
	}

	public void setEndTime() {
		this.totalTime = System.currentTimeMillis() - startTime;
	}

	public String getResource() {
		return resource;
	}

	/**
	 * Get a formatted string representing all the methods that took longer than a specified time.
	 */

	public String getPrintable(long minTime) {
		return getPrintable("", minTime);
	}

	protected String getPrintable(String indent, long minTime) {
		// only print the value if we are larger or equal to the min time.
		if (totalTime >= minTime) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\n");
			buffer.append(indent);
			buffer.append("[" + totalTime + "ms] - " + resource);
			Iterator childrenIt = children.iterator();
			while (childrenIt.hasNext()) {
				buffer.append(((ProfilingTimerBean) childrenIt.next()).getPrintable(indent + "|----", minTime));
			}
			return buffer.toString();
		} else
			return "";
	}
}
