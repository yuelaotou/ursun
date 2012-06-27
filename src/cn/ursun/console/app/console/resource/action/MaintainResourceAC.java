/**
 * 文件名：MaintainWeeResourceAC.java
 * 
 * 创建人：[郭铜彬] - [guotb@neusoft.com]
 * 
 * 创建时间：Nov 26, 2009 2:40:19 PM
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
import cn.ursun.platform.ps.domain.Url;

/**
 * <p> MaintainResourceAC</p>
 * <p> 资源信息维护Action</p>
 * <p> Created on Nov 26, 2009</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author [郭铜彬] - [guotb@neusoft.com]
 * @version 1.0
 */
public class MaintainResourceAC extends WeeAction {

	
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
	 * 资源实体对象
	 */
	private Resource resource = null;
	
	/**
	 * 资源id(传入)标示唯一资源
	 */
	private String resourceId = null;
	
	/**
	 * 资源实体对象
	 */
	private Resource resourcerow = null;

	/**
	 * URL实体对象
	 */
	private Url urlrow = null;
	
	/**
	 * URL id 标示唯一URL
	 */
	private String urlId = null;
	
	/**
	 * Url信息对象，返回列表使用
	 */
	private List<Url> urlList = null;

	/**
	 * UrlId数组对象，批量删除URL传值使用
	 */
	private String[] urlIds = null;

	/**
	 *判断数据库执行成功标志
	 */
	private String flag = null;
	
	/**
	 * 验证资源ID是否重名标志
	 */
	private boolean exist = false;
	
	/**
	 * 新建资源的ID
	 */
	private String activeResourceId = null;
	
	
	/**
	 * 验证url输入内容是否是正则表达式
	 */
	public String cofirmRightUrl(){		
		if(resourceBS.cofirmRightUrl(urlrow.getContent())){
			this.setFlag("success");
		}else{
			this.setFlag("error");
		}		
		return JSON;
	}
	
	/**
	 * 
	 * <p>验证资源ID重名</p>
	 * @return
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on Feb 2, 2010 8:07:58 PM
	 */
	public String confirmName(){
		exist = resourceBS.confirmName(resourcerow);
		return JSON;
	}
	
	/**
	 * <p>添加资源</p>
	 * <p>应用场景：系统资源信息维护</p>
	 * <p>调用步骤：调用ResourceBS.addResource()方法</p>
	 * Created on Nov 26, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String addResource() throws BizException {
		activeResourceId = resourceBS.addResource(resourcerow);
		this.setFlag("success");
		//初始化URL列表
		urlList = resourceBS.queryUrlList();
		return JSON;
	}
	
	/**
	 * <p>修改资源</p>
	 * <p>应用场景：系统资源信息维护</p>
	 * <p>调用步骤：调用ResourceBS.updateResource()方法</p>
	 * Created on Nov 26, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String updateResource() throws BizException {
		resourceBS.updateResource(resourcerow);
		this.setFlag("success");
		//初始化URL列表
		urlList = resourceBS.queryUrlList();
		return JSON;
	}

	/**
	 * <p>删除资源</p>
	 * <p>应用场景：系统资源信息维护</p>
	 * <p>调用步骤：调用ResourceBS.deleteResource()方法</p>
	 * Created on Nov 26, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String deleteResourceById() throws BizException {
		resourceBS.deleteResourceById(resourceId);
		this.setFlag("success");
		//初始化URL列表
		urlList = resourceBS.queryUrlList();
		return JSON;
	}

	/**
	 * <p>添加URL</p>
	 * <p>应用场景：系统资源信息维护-维护URL信息</p>
	 * <p>调用步骤：调用ResourceBS.addUrl()方法</p>
	 * Created on Nov 26, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @throws Exception 
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String addUrl() throws Exception {
		resourceBS.addUrl(urlrow);
		this.setFlag("success");
		return JSON;
	}
	
	
	/**
	 * <p>修改URL</p>
	 * <p>应用场景：系统资源信息维护-维护URL信息</p>
	 * <p>调用步骤：调用ResourceBS.updateUrl()方法</p>
	 * Created on Nov 26, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @throws Exception 
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String updateUrl() throws Exception {
		resourceBS.updateUrl(urlrow);
		this.setFlag("success");
		return JSON;
	}
	
	/**
	 * <p>批量删除URL</p>
	 * <p>应用场景：系统资源信息维护-维护URL信息</p>
	 * <p>调用步骤：调用ResourceBS.deleteResourceById()方法</p>
	 * Created on Nov 26, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @throws Exception 
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String deleteUrlById() throws Exception {
		resourceBS.deleteUrlByUrlId(urlIds);
		this.setFlag("success");
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
		if (pagination == null) {
			pagination = new Pagination();
		}
		urlList = resourceBS.queryUrlList(pagination);
		return JSON;	
	}
	
	/**
	 * <p>根据url_id查询对应的url信息</p>
	 * <p>应用场景：URL信息维护</p>
	 * <p>调用步骤：调用ResourceBS.queryUrlById()方法，返回Url对象</p>
	 * Created on Nov 26, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String queryUrlById() throws BizException {
		if (urlId == null || urlId.equals("null")){			
		}
		else{
			urlrow = resourceBS.queryUrlById(urlId);
			urlrow.setType("modify");
		}
		return "url_editor";
	}
	
	/**
	 * 资源绑定url时调用
	 * @return
	 * @throws BizException
	 */
	public String queryUrlByIdforSelect() throws BizException {
		if (urlId == null || urlId.equals("null")){			
		}
		else{
			urlrow = resourceBS.queryUrlById(urlId);			
		}
		return JSON;
	}
	
	/**
	 * <p>精确查询资源信息</p>
	 * <p>应用场景：系统资源信息维护</p>
	 * <p>调用步骤：调用ResourceBS.queryResourceById()方法，返回Resource对象</p>
	 * Created on Nov 26, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String queryResourceById() throws BizException {
		return null;
	}
	
	/**
	 * <p>根据resourceId查询对应的urls</p>
	 * <p>应用场景：系统资源信息维护</p>
	 * <p>调用步骤：调用ResourceBS.queryUrlByResourceId()方法，返回Url对象</p>
	 * Created on Nov 26, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String queryUrlByResourceId() throws BizException {
		return null;
	}

	public void setResourceBS(ResourceBS resourceBS) {
		this.resourceBS = resourceBS;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	
	public Url getUrlrow() {
		return urlrow;
	}

	
	public void setUrlrow(Url urlrow) {
		this.urlrow = urlrow;
	}

	public String[] getUrlIds() {
		return urlIds;
	}

	public void setUrlIds(String[] urlIds) {
		this.urlIds = urlIds;
	}

	
	public Resource getResourcerow() {
		return resourcerow;
	}

	
	public void setResourcerow(Resource resourcerow) {
		this.resourcerow = resourcerow;
	}


	
	public List<Url> getUrlList() {
		return urlList;
	}

	
	public void setUrlList(List<Url> urlList) {
		this.urlList = urlList;
	}


	
	
	public String getResourceId() {
		return resourceId;
	}

	
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	
	public String getUrlId() {
		return urlId;
	}

	
	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

	
	
	
	public String getFlag() {
		return flag;
	}

	
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Pagination getPagination() {
		return pagination;
	}

	
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
	public boolean isExist() {
		return exist;
	}
	
	public void setExist(boolean exist) {
		this.exist = exist;
	}

	public String getActiveResourceId() {
		return activeResourceId;
	}

	public void setActiveResourceId(String activeResourceId) {
		this.activeResourceId = activeResourceId;
	}


	
}
