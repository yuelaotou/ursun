package cn.ursun.console.app.dao;

import java.util.List;

import cn.ursun.console.app.domain.Resource;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.domain.TreeNode;
import cn.ursun.platform.ps.domain.Url;

public interface ResourceDAO {

	/**
	 * <p>获取资源信息树</p>
	 * <p>查询表：WEE_AUTH_RESOURCE</p>
	 * <p>应用场景：系统资源维护<p>
	 * @return List<TreeNode> 树集合对象
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:36:14
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public List<TreeNode> queryResourceTree();

	/**
	 * <p>获取指定类型资源树信息</p>
	 * <p>查询表：WEE_AUTH_RESOURCE</p>
	 * @param resourceType 资源类型
	 * @return List<TreeNode> 树集合对象
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:36:14
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	//public List<TreeNode> queryResourceTreeByType(String resourceType);

	/**
	 * <p>[描述方法实现的功能]</p>
	 * 
	 * @return
	 * @throws BizException
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Oct 7, 2008 4:32:36 PM
	 */
	public List<Resource> queryResourceList() throws BizException;

	/**
	 * <p>精确查询资源信息</p>
	 * <p>查询表：WEE_AUTH_RESOURCE</p>
	 * <p>应用场景：系统资源维护<p>
	 * @param resId 资源id
	 * @return Resource 资源实体对象
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:36:28
	 */
	public Resource queryResourceById(String resId);

	/**
	 * <p>查询全部URL</p>
	 * <p>查询表：WEE_AUTH_URL</p>
	 * <p>应用场景：系统资源维护-维护URL信息<p>
	 * @return List<Url> URL信息List
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:36:28
	 */
	public List<Url> queryUrlList(Pagination pagination);

	public List<Url> queryUrlList();

	/**
	 * <p>根据urlId精确查询url</p>
	 * <p>查询表：WEE_AUTH_URL</p>
	 * @param urlId
	 * @return Url URL实体对象
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:37:28
	 */
	public Url queryUrlById(String urlId);

	/**
	 * <p>新增资源</p>
	 * <p>操作表：WEE_AUTH_RESOURCE</p>
	 * @param  资源实体对象
	 * @return String 主键值
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:37:59
	 */
	public String createResource(Resource res);

	/**
	 * <p>修改资源</p>
	 * <p>操作表：WEE_AUTH_RESOURCE</p>
	 * @param res 资源实体对象
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:38:16
	 */
	public void updateResource(Resource res);

	/**
	 * <p>删除资源</p>
	 * <p>操作表：WEE_AUTH_RESOURCE</p>
	 * <p>说明：删除的资源包括该资源的子节点</p>
	 * @param resId 资源id
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:38:34
	 */
	public void deleteResourceById(String resId);


	/**
	 * <p>插入url</p>
	 * <p>操作表：WEE_AUTH_URL</p>
	 * @param url 实体对象
	 * @return String 主键值
	 * @author: 刘君琦 - liujq@neusoft.com 
	 * @date: Created on Mar 10, 2009 12:44:27 PM
	 */
	public void createUrl(Url url);

	/**
	 * <p>修改url</p>
	 * <p>操作表：WEE_AUTH_URL</p> 
	 * @param url 实体对象
	 * @author: 刘君琦 - liujq@neusoft.com 
	 * @date: Created on Mar 10, 2009 12:44:29 PM
	 */
	public void updateUrl(Url url);

	/**
	 * <p>批量删除url</p>
	 * <p>操作表：WEE_AUTH_URL</p> 
	 * @param urlIdsO
	 * @author: 刘君琦 - liujq@neusoft.com 
	 * @date: Created on Mar 10, 2009 12:44:24 PM
	 */
	public void deleteUrlByUrlId(String[] urlIds);
	
	/**
	 * 
	 * <p>验证重名</p>
	 * @param resourcerow
	 * @return
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on Feb 2, 2010 4:51:20 PM
	 */
	public boolean confirmName(Resource resourcerow);

}
