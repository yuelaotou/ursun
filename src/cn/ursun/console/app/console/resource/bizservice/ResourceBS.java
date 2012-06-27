package cn.ursun.console.app.console.resource.bizservice;

import java.util.List;

import cn.ursun.console.app.domain.Resource;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.bizlog.aop.annotation.BizExecution;
import cn.ursun.platform.ps.domain.TreeNode;
import cn.ursun.platform.ps.domain.Url;

public interface ResourceBS {

	
	/**
	 * 验证url输入内容是否是正则表达式
	 */
	public boolean cofirmRightUrl(String url);
	
	/**
	 * <p>查询资源树信息</p>
	 * <p>调用步骤：调用ResourceDAO.queryResourceTree()方法获取树信息</p>
	 * @return TreeNode 资源树信息
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:31:17
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	@BizExecution
	public TreeNode queryResourceTree() throws BizException;

	/**
	 * <p>精确查询资源信息</p>
	 * <p>调用步骤：调用ResourceDAO.queryResourceyId()方法获取资源信息</p>
	 * @param resId 资源ID
	 * @return Resource 资源实体对象
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:31:36
	 */
	@BizExecution
	public Resource queryResourceById(String resId) throws BizException;

	/**
	 * <p>查询全部URL</p>
	 * <p>调用步骤：调用ResourceDAO.queryUrlList()方法获URL列表信息</p> 
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:31:58
	 */
	@BizExecution
	public List<Url> queryUrlList(Pagination pagination) throws BizException;
	
	/**
	 * 
	 * <p>查询全部URL,无分页</p>
	 * @return
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-15 下午02:08:11
	 */
	@BizExecution
	public List<Url> queryUrlList() throws BizException;

	/**
	 * <p>根据urlId精确查询url</p>
	 * <p>调用步骤：调用ResourceDAO.queryUrlById()方法获URL列表信息</p> 
	 * @param urlId urlid
	 * @return Url URL实体对象
	 * @throws BizException
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @date: Created on Mar 13, 2009 10:47:48 AM
	 */
	@BizExecution
	public Url queryUrlById(String urlId) throws BizException;

	/**
	 * <p>新增资源</p>
	 * <p>调用步骤：调用ResourceDAO.createResource()方法</p>
	 * @param res 资源实体对象
	 * @throws BizException
	 * Created on 2009-11-17
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	@BizExecution
	public String addResource(Resource res) throws BizException;

	/**
	 * <p>修改资源</p>
	 * <p>调用步骤：调用ResourceDAO.updateResource()方法</p>
	 * @param res 资源实体对象
	 * @throws BizException
	 * Created on 2009-11-17
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	@BizExecution
	public void updateResource(Resource res) throws BizException;

	/**
	 * <p>删除资源
	 * <p>调用步骤：
	 * 1.调用ResourceDAO.deleteResourceUrlMappingByResId(String resId)方法删除与资源绑定的url信息
	 * 2.调用ResourceDAO.deleteResourceMenuMappingByResId(String resId)方法删除与资源绑定的菜单信息
	 * 3.调用ResourceDAO.deleteResourceRoleMappingByResId(String resId)方法删除与资源绑定的角色信息
	 * 4.调用ResourceDAO.deleteResourceById()方法删除资源信息
	 * </p>
	 * @param resId 要删除的资源Id
	 * Created on 2009-11-17
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	@BizExecution
	public void deleteResourceById(String resId) throws BizException;

	/**
	 * <p>插入url</p>
	 * <p>调用步骤：调用ResourceDAO.createUrl()方法</p> 
	 * @param url 实体对象
	 * @throws BizBizException
	 * @author: 刘君琦 - liujq@neusoft.com 
	 * @date: Created on Mar 10, 2009 12:44:27 PM
	 */
	@BizExecution
	public void addUrl(Url url) throws BizException;

	/**
	 * <p>修改url</p>
	 * <p>调用步骤：调用ResourceDAO.updateUrl()方法</p> 
	 * @param url 实体对象
	 * @throws BizBizException
	 * @author: 刘君琦 - liujq@neusoft.com 
	 * @date: Created on Mar 10, 2009 12:44:29 PM
	 */
	@BizExecution
	public void updateUrl(Url url) throws BizException;

	/**
	 * <p>批量删除url</p>
	 * <p>调用步骤：
	 * 1.调用ResourceDAO.deleteUrlResourceMappingByUrl(String[] urlIds)方法删除与资源绑定的url信息
	 * 2.调用ResourceDAO.deleteUrlByUrlId()方法删除URL信息
	 * </p>
	 * @param urlIds 要删除url的id数组
	 * @throws BizBizException
	 * @author: 刘君琦 - liujq@neusoft.com 
	 * @date: Created on Mar 10, 2009 12:44:24 PM
	 */
	@BizExecution
	public void deleteUrlByUrlId(String []urlIds) throws BizException;
	
	/**
	 * 
	 * <p>验证重名</p>
	 * @param resourcerow
	 * @return
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on Feb 2, 2010 4:48:23 PM
	 */
	@BizExecution
	public boolean confirmName(Resource resourcerow);

}
