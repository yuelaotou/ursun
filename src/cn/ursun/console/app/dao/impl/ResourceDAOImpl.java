package cn.ursun.console.app.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import cn.ursun.console.app.dao.ResourceDAO;
import cn.ursun.console.app.domain.Resource;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.core.jdbc.WeeJdbcDAO;
import cn.ursun.platform.core.util.IDGenerator;
import cn.ursun.platform.ps.domain.TreeNode;
import cn.ursun.platform.ps.domain.Url;

/**
 * <p>【资源DAO类实现类】</p>
 * @author 王敏 - wang.min@neusoft.com
 * @version 1.0
 */
public class ResourceDAOImpl extends WeeJdbcDAO implements ResourceDAO {

	// 查询资源列表
	private static final String QUERY_RESOURCE = "SELECT RESOURCE_ID,TITLE,PARENT_ID,IS_LEAF,DESCRIPTION,RID FROM WEE_AUTH_RESOURCE";

	// 根据资源id查找资源(包括资源与URL的绑定信息)
	private static final String QUERY_RESOURCE_AND_URL_BY_RESOURCEID = "SELECT a.RESOURCE_ID,a.TITLE,a.PARENT_ID,a.IS_LEAF,a.DESCRIPTION,a.RID,b.URL_ID,b.CONTENT,b.DESCRIPTION "
			+ "FROM WEE_AUTH_RESOURCE a, WEE_AUTH_URL b, WEE_AUTH_M_RESOURCE_URL c "
			+ "WHERE a.RESOURCE_ID = c.RESOURCE_ID and b.URL_ID = c.URL_ID and a.RESOURCE_ID = ?";

	// 根据资源id查找资源(不包括资源与URL的绑定信息)
	private static final String QUERY_RESOURCE_BY_RESOURCEID = QUERY_RESOURCE + " WHERE RESOURCE_ID = ?";

	// 根据资源id查询资源是否绑定URL
	private static final String QUERY_RESOURCE_URL_COUNT_BY_RESOURCEID = "SELECT count(1) FROM WEE_AUTH_M_RESOURCE_URL WHERE RESOURCE_ID = ?";

	// 根据资源type查找资源列表
	//private static final String QUERY_RESOURCE_TREE_BY_RESOURCETYPE = QUERY_RESOURCE
	//		+ " WHERE RESOURCE_TYPE = ? or RESOURCE_TYPE is null";

	private static final String QUERY_URL_SQL = "SELECT URL_ID,CONTENT,DESCRIPTION FROM WEE_AUTH_URL";
	
	// 查询url列表
	private static final String QUERY_URL_LIST_SQL = QUERY_URL_SQL + " ORDER BY CONTENT";

	// 根据URLid查询URL
	private static final String QUERY_URL_BY_URLID = QUERY_URL_SQL + " where URL_ID = ?";

	// 添加一条资源
	private static final String INSERT_RESOURCE_SQL = "INSERT INTO WEE_AUTH_RESOURCE(RESOURCE_ID,TITLE,PARENT_ID,IS_LEAF,DESCRIPTION,RID) VALUES(?,?,?,?,?,?)";

	// 添加一条URL信息
	private static final String INSERT_URL_SQL = "INSERT INTO WEE_AUTH_URL(URL_ID,CONTENT,DESCRIPTION) VALUES(?,?,?)";

	// 添加一条资源URL绑定信息
	private static final String INSERT_RESOURCE_URL_SQL = "INSERT INTO WEE_AUTH_M_RESOURCE_URL(RESOURCE_ID,URL_ID) VALUES(?,?)";


	// 根据resourceid更新资源表资源信息（更新资源时使用）
	private static final String UPDATE_RESOURCE_BY_RESOURCEID = "UPDATE WEE_AUTH_RESOURCE SET TITLE = ?, DESCRIPTION = ?,RID = ? where RESOURCE_ID=?";

	// 根据资源id更新URLid（更新资源时使用）
	private static final String UPDATE_RESOURCE_URL_SQL = "UPDATE WEE_AUTH_M_RESOURCE_URL SET URL_ID = ?  WHERE RESOURCE_ID=?";

	// 根据URL id 更新URL
	private static final String UPDATE_URL_SQL = "UPDATE WEE_AUTH_URL SET CONTENT=?,DESCRIPTION=? WHERE URL_ID=?";

	// 根据URL id 删除URL
	private static final String DELETE_URL_BY_URLID = "DELETE WEE_AUTH_URL WHERE URL_ID = ?";

	// 根据资源id删除一条资源
	private static final String DELETE_RESOURCE_BY_RESOURCEID = "DELETE WEE_AUTH_RESOURCE WHERE RESOURCE_ID = ?";

	//根据资源的id删除跟资源绑定的URL
	private static final String DELETE_RESOURCE_URL_SQL = "DELETE WEE_AUTH_M_RESOURCE_URL WHERE RESOURCE_ID = ?";

	//
	private static final String SQL_QUERY_RESOURCE = "SELECT RESOURCE_ID, URL, TITLE, PARENT_ID, IS_LEAF, DESCRIPTION,RID FROM WEE_AUTH_RESOURCE ORDER BY TITLE";

	//
	private static final String SQL_QUERY_RESOURCE_URL = "SELECT R.RESOURCE_ID ,U.URL_ID , U.CONTENT URL FROM WEE_AUTH_URL U, WEE_AUTH_M_RESOURCE_URL R WHERE U.URL_ID = R.URL_ID AND R.RESOURCE_ID = ?";
	
	//修改时验证重名
	private static final String QUERY_CONFIRMNAME_FOR_UPDATE = "SELECT COUNT(1) FROM WEE_AUTH_RESOURCE WHERE RID =? AND RESOURCE_ID != ?";
	
	//新增时验证重名
	private static final String QUERY_CONFIRMNAME_FOR_INSERT = "SELECT COUNT(1) FROM WEE_AUTH_RESOURCE WHERE RID =?";

	
	private static class ResourceTreeMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			TreeNode node = new TreeNode();
			node.setKey(rs.getString("RESOURCE_ID"));
			String name = rs.getString("TITLE");
			if (name.length() > 7){
				name = name.substring(0, 7) + "...";
				node.setTitle(name);
			}else{
				node.setTitle(rs.getString("TITLE"));
			}
			node.setParentId(rs.getString("PARENT_ID"));
			if (rs.getString("IS_LEAF").equals("0")) {
				node.setIsFolder(true);
			} else {
				node.setIsFolder(false);
			}
			node.setTooltip(rs.getString("TITLE"));
			//node.setTooltip(rs.getString("DESCRIPTION") == null ? "" : rs.getString("DESCRIPTION"));
			node.setUrl("");
			return node;
		}
	}

	private static class ResourceRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Resource resource = new Resource();
			resource.setResourceId(rs.getString("RESOURCE_ID"));
			resource.setParentId(rs.getString("PARENT_ID"));
			resource.setTitle(rs.getString("TITLE"));
			resource.setIsLeaf(rs.getString("IS_LEAF"));
			resource.setRid(rs.getString("RID"));
			resource.setDescription(rs.getString("DESCRIPTION"));
			return resource;
		}
	}

	private static class ResourceURLRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Resource resource = new Resource();
			resource.setResourceId(rs.getString("RESOURCE_ID"));
			resource.setParentId(rs.getString("PARENT_ID"));
			resource.setTitle(rs.getString("TITLE"));
			resource.setIsLeaf(rs.getString("IS_LEAF"));
			resource.setRid(rs.getString("RID"));
			resource.setDescription(rs.getString("DESCRIPTION"));
			// 若资源已绑定URL则查询URL信息
			List<String> url = new ArrayList<String>();
			url.add(rs.getString("CONTENT"));
			url.add(rs.getString("URL_ID"));
			resource.setUrl(url);
			return resource;
		}
	}

	private static class UrlRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Url url = new Url();
			url.setUrlId(rs.getString("URL_ID"));
			url.setContent(rs.getString("CONTENT"));
			url.setDescription(rs.getString("DESCRIPTION") == null ? "" : rs.getString("DESCRIPTION"));		
			return url;
		}
	}

	/**
	 * 
	 * <p>验证资源ID是否重名</p>
	 * @param resourcerow
	 * @return
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on Feb 2, 2010 4:52:30 PM
	 */
	public boolean confirmName(Resource resourcerow){
		String rid = resourcerow.getRid();
		String type = resourcerow.getType();
		int count = 0;
		if(rid != null){//填写了资源ID的情况
			if(type.equals("add")){//新加
				count = this.queryForInt(QUERY_CONFIRMNAME_FOR_INSERT, new Object[] { rid.trim() });
				
			}else if(type.equals("modify")){//修改
				count = this.queryForInt(QUERY_CONFIRMNAME_FOR_UPDATE, new Object[] { rid.trim(), resourcerow.getResourceId().trim()});
			}
			if (count == 0){
				return true;
			}else{
				return false;
			}
		}else{//没有填写资源ID的情况，现在不做处理，点击提交时再处理。。。
			return true;
		}
		
	}
	/**
	 * 
	 * <p>查询某条URL，修改URL调用</p>
	 * @param urlId
	 * @return
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2009-12-11 上午10:32:33
	 */
	public Url queryUrlById(String urlId) {
		return (Url) query(QUERY_URL_BY_URLID, new Object[] { urlId }, new UrlRowMapper()).get(0);
	}

	/**
	 * 
	 * <p>显示URL列表，带翻页</p>
	 * @param pagination
	 * @return
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2009-12-11 上午10:20:26
	 */
	@SuppressWarnings("unchecked")
	public List<Url> queryUrlList(Pagination pagination) {
		return query(QUERY_URL_LIST_SQL, new UrlRowMapper(), pagination);
	}

	/**
	 * 
	 * <p>显示URL列表，不带翻页，资源绑定URL时下拉列表使用</p>
	 * @return
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2009-12-11 上午10:20:50
	 */
	@SuppressWarnings("unchecked")
	public List<Url> queryUrlList() {
		return query(QUERY_URL_LIST_SQL, new UrlRowMapper());
	}

	/**
	 * 
	 * <p>批量删除URL</p>
	 * @param urlIds
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2009-12-11 上午10:30:45
	 */
	public void deleteUrlByUrlId(String[] urlIds) {
		// 根据URLid删除URL
		List<Object[]> params = new ArrayList<Object[]>();
		for (int i = 0; i < urlIds.length; i++) {
			params.add(new Object[] { urlIds[i] });
		}
		batchUpdate(DELETE_URL_BY_URLID, params);
	}

	/**
	 * 
	 * <p>更新URL</p>
	 * @param url
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2009-12-11 上午10:17:36
	 */
	public void updateUrl(Url url) {
		this.update(UPDATE_URL_SQL, new Object[] { url.getContent().trim(), url.getDescription().trim(),
				url.getUrlId().trim() });
	}

	/**
	 * 
	 * <p>新建URL</p>
	 * @param url
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2009-12-11 上午10:16:43
	 */
	public void createUrl(Url url) {
		this.update(INSERT_URL_SQL, new Object[] { IDGenerator.generateId(), url.getContent().trim(),
				url.getDescription().trim() });
	}

	/**
	 * 
	 * <p>生成动态资源树，只包括页面类型的资源，供菜单模块调用</p>
	 * @param resourceType
	 * @return
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2009-12-10 上午10:55:44
	 */
	/*@SuppressWarnings("unchecked")
	public List<TreeNode> queryResourceTreeByType(String resourceType) {
		return query(QUERY_RESOURCE_TREE_BY_RESOURCETYPE, new Object[] { resourceType }, new ResourceTreeMapper());
	}*/

	/**
	 * 
	 * <p>动态生成资源树</p>
	 * @return
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2009-12-4 下午02:42:29
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryResourceTree() {
		return query(QUERY_RESOURCE, new ResourceTreeMapper());
	}

	/**
	 * 
	 * <p>资源维护：新建资源信息</p>
	 * @param res
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2009-12-9 下午04:11:47
	 */
	@SuppressWarnings("unchecked")
	public String createResource(Resource res) {
		String key = cn.ursun.platform.core.util.IDGenerator.generateId();
		StringBuffer ires_param = new StringBuffer();
		ires_param.append(key + ",");// 主键
		ires_param.append(res.getTitle().trim() + ",");
		ires_param.append(res.getParentId().trim() + ",");
		ires_param.append(res.getIsLeaf() + ",");
		/*if (res.getIsLeaf().equals("0")) {// 不是叶子节点
			ires_param.append(",");
		} else {// 是叶子节点
			ires_param.append(res.getResourceType().trim() + ",");
		}*/
		String description = res.getDescription().trim();
		if (description.equals("") || description == null) {
			description = "";
		}
		ires_param.append(description+",");
		String rid = res.getRid().trim();
		if (rid.equals("") || rid == null) {
			rid = "";
		}
		ires_param.append(rid);//资源id
		String[] ires = ires_param.toString().split(",", 6);

		List params = new ArrayList();
		params.add(ires);

		List<String> sqls = new ArrayList();
		sqls.add(INSERT_RESOURCE_SQL);
		String urlid = res.getUrl().get(0);
		// 在添加资源的同时绑定URL
		if(!StringUtils.isEmpty(urlid)){
			sqls.add(INSERT_RESOURCE_URL_SQL);
			// 添加资源和URL关系表
			StringBuffer ires_url_param = new StringBuffer();
			ires_url_param.append(key + ",");
			ires_url_param.append(res.getUrl().get(0));
			String[] ires_url = ires_url_param.toString().split(",");
			params.add(ires_url);
		}
		this.batchUpdate(sqls, params);
		return key;
	}

	
	/**
	 * 
	 * <p>资源维护：更新资源信息</p>
	 * @param res
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2009-12-10 上午09:18:25
	 */
	@SuppressWarnings("unchecked")
	public void updateResource(Resource res) {
		String key = res.getResourceId().trim();
		StringBuffer ures_param = new StringBuffer();
		ures_param.append(res.getTitle().trim() + ",");
		//ures_param.append(res.getResourceType().trim() + ",");
		String description = res.getDescription().trim();
		if (description.equals("") || description == null) {
			description = "";
		}
		ures_param.append(description + ",");
		String rid = res.getRid().trim();
		if (rid.equals("") || rid == null) {
			rid = "";
		}
		ures_param.append(rid + ",");
		ures_param.append(key);
		String[] ures = ures_param.toString().split(",", 4);
		List params = new ArrayList();
		params.add(ures);

		List<String> sqls = new ArrayList<String>();
		sqls.add(UPDATE_RESOURCE_BY_RESOURCEID);
		String urlid = res.getUrl().get(0);
		// 在更新资源的同时绑定URL
		if(!StringUtils.isEmpty(urlid)){
			// 资源已绑定URL
			if (isBindUrl(key)) {
				sqls.add(UPDATE_RESOURCE_URL_SQL);
				// 更新资源和URL关系表
				StringBuffer ures_url_param = new StringBuffer();
				ures_url_param.append(res.getUrl().get(0) + ",");
				ures_url_param.append(key);
				String[] ures_url = ures_url_param.toString().split(",");
				params.add(ures_url);
			} else {
				sqls.add(INSERT_RESOURCE_URL_SQL);
				// 添加资源和URL关系表
				StringBuffer ires_url_param = new StringBuffer();
				ires_url_param.append(key + ",");
				ires_url_param.append(res.getUrl().get(0));
				String[] ires_url = ires_url_param.toString().split(",");
				params.add(ires_url);
			}
		}
		// 不绑定URL
		else {
			sqls.add(DELETE_RESOURCE_URL_SQL);
			StringBuffer dres_url_param = new StringBuffer();
			dres_url_param.append(key + ",");
			String[] dres_url = dres_url_param.toString().split(",");
			params.add(dres_url);
		}
		this.batchUpdate(sqls, params);

	}

	/**
	 * 
	 * <p>删除资源</p>
	 * @param resId
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2009-12-11 上午10:27:22
	 */
	public void deleteResourceById(String resId) {
		update(DELETE_RESOURCE_BY_RESOURCEID, new String[] { resId });
	}

	/**
	 * 
	 * <p>根据资源id取得资源信息</p>
	 * @param resId
	 * @return
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2009-12-4 下午02:43:31
	 */
	public Resource queryResourceById(String resId) {
		// 资源已绑定URL
		if (isBindUrl(resId)) {
			return (Resource) query(QUERY_RESOURCE_AND_URL_BY_RESOURCEID, new Object[] { resId },
					new ResourceURLRowMapper()).get(0);
		}
		// 资源未绑定URL
		else {
			return (Resource) query(QUERY_RESOURCE_BY_RESOURCEID, new Object[] { resId }, new ResourceRowMapper()).get(
					0);
		}
	}

	/**
	 * 
	 * <p>判断资源是否绑定URL</p>
	 * @param resId
	 * @return
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2009-12-9 下午08:05:11
	 */
	private boolean isBindUrl(String resId) {
		int i = this.queryForInt(QUERY_RESOURCE_URL_COUNT_BY_RESOURCEID, new Object[] { resId });
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Resource> queryResourceList() throws BizException {
		List<Resource> resList = query(SQL_QUERY_RESOURCE, new ResourceRowMapper());
		setResourceUrl(resList);
		return resList;
	}

	private void setResourceUrl(List<Resource> resList) {
		for (Resource res : resList) {
			setResourceUrl(res);
		}
	}

	private void setResourceUrl(Resource res) {
		List<Map<String, Object>> list = queryForList(SQL_QUERY_RESOURCE_URL, new Object[] { res.getId() });
		List<String> urlList = new ArrayList<String>();
		for (Map<String, Object> m : list) {
			urlList.add((String) m.get("URL"));
		}
		res.setUrl(urlList);
	}

}
