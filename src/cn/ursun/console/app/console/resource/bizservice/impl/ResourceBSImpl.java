package cn.ursun.console.app.console.resource.bizservice.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ursun.console.app.console.resource.bizservice.ResourceBS;
import cn.ursun.console.app.dao.ResourceDAO;
import cn.ursun.console.app.domain.Resource;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.bizlog.aop.annotation.BizExecution;
import cn.ursun.platform.ps.bizservice.WeeBizService;
import cn.ursun.platform.ps.domain.TreeNode;
import cn.ursun.platform.ps.domain.Url;
import cn.ursun.platform.ps.security.WeeSecurityInfo;
import cn.ursun.platform.ps.util.TreeUtil;


public class ResourceBSImpl extends WeeBizService implements ResourceBS{
	
	ResourceDAO resourceDao = null;
	
	
	/**
	 * 验证url输入内容是否是正则表达式
	 * @throws BizException 
	 */
	public boolean cofirmRightUrl(String url){		
		 try {
				Pattern p = Pattern.compile(url);
				Matcher m = p.matcher("/aaaaab");
				boolean b = m.matches();
				return true;
			} catch (RuntimeException e) {
				e.printStackTrace();
				return false;						
			}	
	}
	
	/**
	 * <p>Discription:[新增资源]</p>
	 * <p>应用场景：资源管理</p>
	 * <p>调用步骤：调用resourceDao.createResource(res)方法新增资源</p>
	 * Created on 2010-6-2
	 * @author:　[杨博] - [yangb@neusoft.com] 
	 * @update:
	 */
	@BizExecution
	public String addResource(Resource res) throws BizException {
		try{
			String newResourceId = resourceDao.createResource(res);
			WeeSecurityInfo.getInstance().refresh();
			return newResourceId;
		} catch (Exception e) {
			throw new BizException("0400302A", e);
		}
	}

	/**
	 * <p>Discription:[插入URL]</p>
	 * <p>应用场景：资源管理</p>
	 * <p>调用步骤：调用resourceDao.createUrl(url)方法插入URL</p>
	 * Created on 2010-6-2
	 * @author:　[杨博] - [yangb@neusoft.com] 
	 * @update:
	 */
	@BizExecution
	public void addUrl(Url url) throws BizException {
		try{
			resourceDao.createUrl(url);
			WeeSecurityInfo.getInstance().refresh();
		} catch (Exception e) {
			throw new BizException("0400306A", e);
		}
	}

	/**
	 * <p>Discription:[根据资源ID删除资源]</p>
	 * <p>应用场景：资源管理</p>
	 * <p>调用步骤：调用方法resourceDao.deleteResourceById(resId)删除资源</p>
	 * Created on 2010-6-2
	 * @author:　[杨博] - [yangb@neusoft.com] 
	 * @update:
	 */
	@BizExecution
	public void deleteResourceById(String resId) throws BizException {
		try{
			resourceDao.deleteResourceById(resId);
			WeeSecurityInfo.getInstance().refresh();
		} catch (Exception e) {
			throw new BizException("0400304A", e);
		}
	}

	/**
	 * <p>Discription:[根据URL ID删除URL]</p>
	 * <p>应用场景：资源管理</p>
	 * <p>调用步骤：调用resourceDao.deleteUrlByUrlId(urlIds)方法删除URL</p>
	 * Created on 2010-6-2
	 * @author:　[杨博] - [yangb@neusoft.com] 
	 * @update:
	 */
	@BizExecution
	public void deleteUrlByUrlId(String[] urlIds) throws BizException {
		try{
			resourceDao.deleteUrlByUrlId(urlIds);
			WeeSecurityInfo.getInstance().refresh();
		} catch (Exception e) {
			throw new BizException("0400308A", e);
		}
	}

	/**
	 * <p>Discription:[根据资源ID查询取得资源详细信息]</p>
	 * <p>应用场景：资源管理</p>
	 * <p>调用步骤：调用resourceDao.queryResourceById(resId)方法获资源详细信息</p>
	 * Created on 2010-6-2
	 * @author:　[杨博] - [yangb@neusoft.com] 
	 * @update:
	 */
	@BizExecution
	public Resource queryResourceById(String resId) throws BizException {		
		try{
			return resourceDao.queryResourceById(resId);
		} catch (Exception e) {
			throw new BizException("0400309A", e);
		}
	}

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
	public TreeNode queryResourceTree() throws BizException {
		try{
			return TreeUtil.createTreeRelation(resourceDao.queryResourceTree());
		} catch (Exception e) {
			throw new BizException("0400301A", e);
		}
	}

	/**
	 * <p>Discription:[根据URL ID准确查找URL]</p>
	 * <p>调用步骤：调用resourceDao.queryUrlById(urlId)方法 查找URL</p>
	 * Created on 2010-6-2
	 * @author:　[杨博] - [yangb@neusoft.com] 
	 * @update:
	 */
	@BizExecution
	public Url queryUrlById(String urlId) throws BizException {
		try{
			return resourceDao.queryUrlById(urlId);
		} catch (Exception e) {
			throw new BizException("0400310A", e);
		}
	}

	/**
	 * <p>Discription:[查询全部URL列表 带分页功能]</p>
	 * Created on 2010-6-2
	 * <p>调用步骤：调用resourceDao.queryUrlList(pagination)方法查询URL列表</p>
	 * @author:　[杨博] - [yangb@neusoft.com]
	 * @update:
	 */
	@BizExecution
	public List<Url> queryUrlList(Pagination pagination) throws BizException {		
		try{
			return resourceDao.queryUrlList(pagination);
		} catch (Exception e) {
			throw new BizException("0400305A", e);
		}
	}
	/**
	 * <p>Discription:[查询全部URL列表 不带分页功能]</p>
	 * Created on 2010-6-2
	 * <p>调用步骤：调用resourceDao.queryUrlList()方法查询URL列表</p>
	 * @author:　[杨博] - [yangb@neusoft.com]
	 * @update:
	 */
	@BizExecution
	public List<Url> queryUrlList() throws BizException {		
		try{
			return resourceDao.queryUrlList();
		} catch (Exception e) {
			throw new BizException("0400305B", e);
		}
	}
	
	public ResourceDAO getResourceDao() {
		return resourceDao;
	}
	
	public void setResourceDao(ResourceDAO resourceDao) {
		this.resourceDao = resourceDao;
	}
	
	/**
	 * <p>Discription:[修改资源]</p>
	 * <p>调用步骤：调用resourceDao.updateResource(res)方法修改资源</p>
	 * Created on 2010-6-2
	 * @author:　[杨博] - [yangb@neusoft.com] 
	 * @update:
	 */
	@BizExecution
	public void updateResource(Resource res) throws BizException {
		try{
			resourceDao.updateResource(res);
			WeeSecurityInfo.getInstance().refresh();
		} catch (Exception e) {
			throw new BizException("0400303A", e);
		}
	}

	/**
	 * <p>Discription:[修改URL]</p>
	 * <p>调用步骤：调用resourceDao.updateUrl(url)方法修改URL</p>
	 * Created on 2010-6-2
	 * @author:　[杨博] - [yangb@neusoft.com] 
	 * @update:
	 */
	@BizExecution
	public void updateUrl(Url url) throws BizException {
		try{
			resourceDao.updateUrl(url);
			WeeSecurityInfo.getInstance().refresh();
		} catch (Exception e) {
			throw new BizException("0400307A", e);
		}
	}

	/**
	 * <p>Discription:[判断是否重名]</p>
	 * <p>调用步骤：调用方法resourceDao.confirmName(resourcerow)判断是否重名</p>
	 * Created on 2010-6-2
	 * @author:　[杨博] - [yangb@neusoft.com] 
	 * @update:
	 */
	@BizExecution
	public boolean confirmName(Resource resourcerow){
		return resourceDao.confirmName(resourcerow);
	}
}
