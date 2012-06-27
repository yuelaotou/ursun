/**
 * 文件名：ShowWeeResourceAC.java
 * 
 * 创建人：[郭铜彬] - [guotb@neusoft.com]
 * 
 * 创建时间：Nov 26, 2009 10:55:32 AM
 * 
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.console.app.console.resource.action;

import java.util.List;

import cn.ursun.console.app.console.resource.bizservice.ResourceBS;
import cn.ursun.console.app.domain.Resource;
import cn.ursun.platform.core.action.WeeAction;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.domain.TreeNode;
import cn.ursun.platform.ps.domain.Url;

/**
 * <p> ShowWeeResourceAC</p>
 * <p> 资源信息显示Action</p>
 * <p> Created on Nov 26, 2009</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author [郭铜彬] - [guotb@neusoft.com]
 * @version 1.0
 */
public class ShowResourceAC extends WeeAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Pagination pagination = null;
	/**
	 * 业务操作对象
	 */
	private ResourceBS resourceBS = null;

	/**
	 * 树信息对象
	 */
	private TreeNode rootRes = null;

	/**
	 * 资源id(传入)
	 */
	private String resourceId = null;
	
	/**
	 * 记录资源信息(传入)
	 */
	private Resource resourcerow = null;
	
	/**
	 * 记录资源信息(传入)新建和修改时用于保存父资源信息
	 */
	private Resource resourceparent = null;
	
	/**
	 * Url信息对象
	 */
	private List<Url> urlList = null;
	
	/**
	 * URL实体对象
	 */
	private Url urlrow = null;
	/**
	 * <p>跳转主页面</p>
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:27:35
	 */
	public String forwardListPage() throws BizException {
		urlList = resourceBS.queryUrlList();
		return "resource";
	}

	/**
	 * <p>跳转主页面，跳转到URL维护页面</p>
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:27:35
	 */
	public String forwardUrlListPage() throws BizException {
		return "url_editor";
	}
	
	/**
	 * <p>查询资源树信息</p>
	 * <p>应用场景：系统资源信息维护</p>
	 * <p>调用步骤：调用ResourceBS.queryResourceTree()方法，返回TreeNode对象</p>
	 * Created on Nov 26, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String queryResourceTree() throws BizException {
		rootRes = resourceBS.queryResourceTree();		
		return JSON;
	}

	/**
	 * <p>根据资源id查询某节点资源信息，添加新资源时调用，取得资源的父资源信息</p>
	 * <p>应用场景：系统资源信息维护</p>
	 * <p>调用步骤：调用ResourceBS.queryResourceById()方法</p>
	 * Created on Nov 26, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String queryResourceById() throws BizException {
		if (resourceId == null || resourceId.equals("null")){			
		}
		else{
			resourceparent = resourceBS.queryResourceById(resourceId);
			resourceparent.setType("add");
		}
		return JSON;
	}
	
	
	/**
	 * <p>根据资源id查询某节点资源信息，修改某资源时调用，取得该资源的信息及其父资源信息</p>
	 * <p>应用场景：系统资源信息维护</p>
	 * <p>调用步骤：调用ResourceBS.queryResourceById()方法</p>
	 * Created on Nov 26, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String queryResourceByIdForModify() throws BizException {
		if (resourceId == null || resourceId.equals("null")){			
		}
		else{
			resourcerow = resourceBS.queryResourceById(resourceId);
			resourceparent = resourceBS.queryResourceById(resourcerow.getParentId());
			resourceparent.setType("modify");
		}
		return JSON;
	}

	/**
	 * <p>获取URL信息列表</p>
	 * <p>应用场景：系统资源信息维护-URL维护</p>
	 * <p>调用步骤：调用ResourceBS.queryUrlList()方法，返回List<Url>对象</p>
	 * Created on Nov 26, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String queryUrlList() throws Exception {
		//Pagination pagination = this.getPagination();
		if (pagination == null) {
			pagination = new Pagination();
		}
		urlList = resourceBS.queryUrlList(pagination);
		//this.setPagination(pagination);
		return "url_list";
		//return "urllistgrid";
	}
	
	
	public void setResourceBS(ResourceBS resourceBS) {
		this.resourceBS = resourceBS;
	}


	
	
	public String getResourceId() {
		return resourceId;
	}

	
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public Resource getResourcerow() {
		return resourcerow;
	}

	
	public void setResourcerow(Resource resourcerow) {
		this.resourcerow = resourcerow;
	}

	
	public Resource getResourceparent() {
		return resourceparent;
	}

	
	public void setResourceparent(Resource resourceparent) {
		this.resourceparent = resourceparent;
	}

	
	public TreeNode getRootRes() {
		return rootRes;
	}

	
	public void setRootRes(TreeNode rootRes) {
		this.rootRes = rootRes;
	}

	public List<Url> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<Url> urlList) {
		this.urlList = urlList;
	}

	
	public Url getUrlrow() {
		return urlrow;
	}

	
	public void setUrlrow(Url urlrow) {
		this.urlrow = urlrow;
	}

	
	public Pagination getPagination() {
		return pagination;
	}

	
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	

	
}
