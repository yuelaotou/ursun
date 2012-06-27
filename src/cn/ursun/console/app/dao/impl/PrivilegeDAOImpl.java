/**
 * 文件名：PrivilegeDAOImpl.java
 * 
 * 创建人：于宝淇 - yu-bq@neusoft.com
 * 
 * 创建时间：Dec 8, 2009 4:12:22 PM
 * 
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.console.app.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import cn.ursun.console.app.dao.PrivilegeDAO;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.core.jdbc.WeeJdbcDAO;
import cn.ursun.platform.ps.domain.TreeNode;

/**
 * <p>权限DAO类实现类</p>
 * @author 于宝淇 - yu-bq@neusoft.com
 * @version 1.0
 */
public class PrivilegeDAOImpl extends WeeJdbcDAO implements PrivilegeDAO {

	/**
	 * 查询角色和资源信息
	 */

	private static final String QUERY_LIST_SQL = "SELECT T.ROLE_ID,M.RESOURCE_ID,TITLE,PARENT_ID,IS_LEAF,DESCRIPTION "
			+ "FROM WEE_AUTH_M_ROLE_RESOURCE T,WEE_AUTH_RESOURCE M "
			+ "WHERE M.RESOURCE_ID=T.RESOURCE_ID AND ROLE_ID=?";

	/**
	 * 根据userid查询资源信息
	 */
	private static final String QUERY_LIST_BYUSERID = "SELECT T.ROLE_ID,M.RESOURCE_ID,TITLE,PARENT_ID,IS_LEAF,DESCRIPTION "
			+ "FROM WEE_AUTH_M_ROLE_RESOURCE T,WEE_AUTH_RESOURCE M,WEE_AUTH_M_USER_ROLE N "
			+ "WHERE M.RESOURCE_ID=T.RESOURCE_ID AND N.ROLE_ID=T.ROLE_ID AND N.USER_ID=?";

	/**
	 * 删除角色和资源的对应关系
	 */
	private static final String DELETE_ROLE_RESOURCE_RES = "DELETE WEE_AUTH_M_ROLE_RESOURCE WHERE ROLE_ID=?";

	private static final String DELETE_ROLE_RESOURCE_By_ROLE = "DELETE WEE_AUTH_M_ROLE_RESOURCE WHERE ROLE_ID=? AND RESOURCE_ID=?";

	/**
	 * 新增角色和资源的对应关系
	 */
	private static final String INSERT_ROLE_RESOURCE_RES = "INSERT INTO WEE_AUTH_M_ROLE_RESOURCE VALUES(?,?)";

	private static final String SQL_QUERY_PRIVILEGE_FOR_RESOOURCE = "SELECT COUNT(1) FROM WEE_AUTH_M_ROLE_RESOURCE R,WEE_AUTH_RESOURCE RES WHERE R.RESOURCE_ID=RES.RESOURCE_ID AND RES.RID = ? AND ROLE_ID IN  ( ROLE_IDS )";

	/**
	 * 查询url和角色的映射
	 */
	private static final String SQL_QUERY_URL_ROLE_MAPPING = "SELECT DISTINCT U.CONTENT, MRR.ROLE_ID FROM WEE_AUTH_URL U , WEE_AUTH_M_RESOURCE_URL MRU, WEE_AUTH_M_ROLE_RESOURCE MRR "
			+ " WHERE U.URL_ID = MRU.URL_ID  AND MRR.RESOURCE_ID = MRU.RESOURCE_ID " + " ORDER BY U.CONTENT ";

	/**
	 * 获取指定用户的角色id
	 */
	private static final String IS_ME_SQL = "SELECT ROLE_ID FROM WEE_AUTH_M_USER_ROLE T WHERE USER_ID=?";

	/**
	 * 得到某个父节点下的所有子节点
	 */
	private static final String GET_SON_TREECODES = "SELECT T.ROLE_ID FROM WEE_AUTH_ROLE T START WITH T.ROLE_ID=? CONNECT BY PRIOR T.ROLE_ID = T.PARENT_ID";

	/**
	 * 获取资源树的节点
	 */
	private static final String GET_RESOURCE_NODE = "SELECT RESOURCE_ID,TITLE,PARENT_ID,IS_LEAF,DESCRIPTION FROM WEE_AUTH_RESOURCE WHERE RESOURCE_ID=?";

	/**
	 * 获取资源树的父亲节点
	 */
	private static final String GET_ALL_RESOURCE_NODE_ISLEAF = "SELECT RESOURCE_ID,TITLE,PARENT_ID,IS_LEAF,DESCRIPTION FROM WEE_AUTH_RESOURCE WHERE IS_LEAF='0'";

	
	/**
	 * 获取某个资源节点的所有父节点
	 */
	private static final String GET_FATHER_RESOURCE_CODE = "SELECT RESOURCE_ID FROM WEE_AUTH_RESOURCE START WITH RESOURCE_ID=? "
			+ "CONNECT BY PRIOR PARENT_ID = RESOURCE_ID";
	
	/**
	 * 获取某个资源节点的所有父节点
	 */
	/*private static final String GET_FATHER_RESOURCE_CODE_LEAF = "SELECT RESOURCE_ID,TITLE,PARENT_ID,IS_LEAF FROM WEE_AUTH_RESOURCE START WITH RESOURCE_ID=? "
			+ "CONNECT BY PRIOR PARENT_ID = RESOURCE_ID";*/
	
	//private static final String c = "SELECT RESOURCE_ID,TITLE,PARENT_ID,IS_LEAF,DESCRIPTION FROM WEE_AUTH_RESOURCE WHERE RESOURCE_ID=? and ";

	/**
	 * 
	 * <p>ResourceRowMapper</p>
	 * @author 于宝淇 - yu-bq@neusoft.com
	 * @version 1.0
	 */
	private static class PrivilegeRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			TreeNode node = new TreeNode();
			node.setKey(rs.getString("RESOURCE_ID"));
			node.setTitle(rs.getString("TITLE"));
			node.setParentId(rs.getString("PARENT_ID"));
			if (rs.getString("IS_LEAF").equals("0")) {
				node.setIsFolder(true);
			} else {
				node.setIsFolder(false);
			}
			node.setUrl("");
			return node;
		}
	}

	/**
	 * 
	 * <p>RoleRowMapper</p>
	 * @author 于宝淇 - yu-bq@neusoft.com
	 * @version 1.0
	 */
	private static class RoleRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			TreeNode node = new TreeNode();
			node.setKey(rs.getString("ROLE_ID"));
			return node;
		}
	}

	/**
	 * 
	 * <p>RoleRowMapper</p>
	 * @author 于宝淇 - yu-bq@neusoft.com
	 * @version 1.0
	 */
	private static class ResourceRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TreeNode node = new TreeNode();
			// node.setKey(rs.getString("RESOURCE_ID"));
			return rs.getString("RESOURCE_ID");
		}
	}

	/**
	 * 
	 * <p>得到父节点信息</p>
	 * @param parentId
	 * @return
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 5, 2010 2:38:57 PM
	 */
	public TreeNode getParentTreeNode(String parentId) throws BizException {
		return (TreeNode) (this.query(GET_RESOURCE_NODE, new Object[] { parentId }, new PrivilegeRowMapper())).get(0);
	}
	
	/**
	 * 
	 * <p>取所有父亲节点</p>
	 * @param parentId
	 * @return
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 5, 2010 2:38:57 PM
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> getAllParentTreeNode() throws BizException {		
		List<TreeNode> retList = (List<TreeNode>) (this.query(GET_ALL_RESOURCE_NODE_ISLEAF,  new PrivilegeRowMapper()));
		return retList;
	}

	/**
	 * <p>查询某个角色的资源树</p>
	 * @param roleId
	 * @return
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Dec 8, 2009 4:12:22 PM
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryResourceTreeOfRole(String roleId) throws BizException {
		List<TreeNode> retList = (List<TreeNode>) query(QUERY_LIST_SQL, new Object[] { roleId },
				new PrivilegeRowMapper());
		return retList;
	}

	/**
	 * <p>根据用户id查询资源树</p>
	 * @param userId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:51:19
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryResourceTreeByUserId(String userId) throws BizException {
		List<TreeNode> retList = (List<TreeNode>) query(QUERY_LIST_BYUSERID, new Object[] { userId },
				new PrivilegeRowMapper());
		return retList;
	}

	/**
	 * <p>更新角色和对应资源的关系</p>
	 * @param roleId
	 * @param resIds
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Dec 7, 2009 3:35:42 PM
	 */
	@SuppressWarnings("unchecked")
	public void updatePrivilege(String roleId, String[] resIds) throws BizException {		
		List<String> pageIds = getResIds(roleId, resIds);
		if (pageIds.size() > 0) {
			// 查询该选择的角色节点下的所有子节点
			List<TreeNode> sonList = this.query(GET_SON_TREECODES, new Object[] { roleId }, new RoleRowMapper());
			// 父节点删除的资源在所有的子节点里都级联删除
			if (sonList.size() > 0) {
				for (TreeNode tree : sonList) {
					for (String id : pageIds) {
						this.update(DELETE_ROLE_RESOURCE_By_ROLE, new Object[] { tree.getKey(), id });
					}
				}
			}
		}

		// 将选择角色节点原来的对应关系删除，然后再插入新的对应关系
		this.update(DELETE_ROLE_RESOURCE_RES, new Object[] { roleId });		
		
		String resIDStr = resIds[0];
		
		// 资源树有选中的节点
		if (!"".equals(resIDStr)) {
			//resIDStr = getAllLeafNode(resIDStr);
			//过滤非叶子节点
			List<TreeNode> leafNodes = filterNotLeafNode(resIDStr);
			List<Object[]> paras = new ArrayList<Object[]>();

			for(TreeNode node:leafNodes){
				Object[] reidObj = new Object[2];
				reidObj[0] = roleId;
				reidObj[1] = node.getKey();
				
				paras.add(reidObj);
			}

			// 批量插入新选中的资源
			this.batchUpdate(INSERT_ROLE_RESOURCE_RES, paras);

		}

	}

	/**
	 * 
	 * <p>得到父节点是叶子类型的节点</p>
	 * @param resIDStr
	 * @return
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 29, 2010 3:04:24 PM
	 */
	@SuppressWarnings("unchecked")
//	private String getAllLeafNode(String resIDStr)throws BizException{
//		List<String> retNodes = new ArrayList<String>();
//		StringBuffer sb = new StringBuffer();
//		String[] ids = resIDStr.split("\\,");
//		for (int i = 0, j = ids.length; i < j; i++) {
//			List<TreeNode> nodes = this.query(GET_FATHER_RESOURCE_CODE_LEAF, new Object[] { ids[i]}, new PrivilegeRowMapper());
//			retNodes.add(ids[i]);
//			for(TreeNode node:nodes){
//				if(!node.getIsFolder()){//叶子节点
//					if(!retNodes.contains(node.getKey())){
//						retNodes.add(node.getKey());
//					}
//				}
//			}
//		}
//		for(int loop=0;loop<retNodes.size();loop++){
//			sb.append(retNodes.get(loop));
//			if(loop!=retNodes.size()){
//				sb.append(",");
//			}
//		}
//		
//		return sb.toString();
//	}
	/**
	 * 
	 * <p>过滤非叶子节点</p>
	 * @param resIDStr
	 * @return
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 16, 2010 11:05:26 AM
	 */
	private List<TreeNode> filterNotLeafNode(String resIDStr)throws BizException{
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		
		String[] ids = resIDStr.split("\\,");
		for (int i = 0, j = ids.length; i < j; i++) {
			TreeNode queryNode = (TreeNode) this.query(GET_RESOURCE_NODE, new Object[] { ids[i]}, new PrivilegeRowMapper()).get(0);
			if(!queryNode.getIsFolder()){
				nodes.add(queryNode);
			}
		}		
		return nodes;
	}
	
	
	/**
	 * 
	 * <p>查询数据库里存在而在前台里没有的资源id</p>
	 * @param roleId 当前选择的角色
	 * @param resIds 前台资源树上选择的资源集合
	 * @return
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 7, 2010 2:37:40 PM
	 */
	@SuppressWarnings("unchecked")
	private List<String> getResIds(String roleId, String[] resIds) throws BizException {
		// 前台资源树选择的节点
		String resIDStr = resIds[0];
		List<String> pageIds = new ArrayList<String>();
		List<String> allResourceIds = new ArrayList<String>();
		// 数据库中已经保存的该角色和资源的对应关系
		List<TreeNode> dbList = queryResourceTreeOfRole(roleId);
		boolean flag = false;
		if (!"".equals(resIDStr)) {
			String[] ids = resIDStr.split("\\,");
			// 分别循环数据库里角色与资源的关系列表和前台选择的
			// 资源树节点，目的是找出数据库里存在而在resIDStr里没有的资源id
			// 这说明前台已经把这些资源节点删除了，找出这样的节点是为了把选择
			// 角色节点下的所有子角色节点的资源也一同级联删除.
			for (TreeNode tree : dbList) {
				flag = false;
				for (String id : ids) {
					if (id.equals(tree.getKey())) {
						flag = true;
						break;
					}
				}

				if (!flag) {
					pageIds.add(tree.getKey());
				}
			}

			for (String resourceId : pageIds) {
				allResourceIds.add(resourceId);
				List<String> allFatherIds = this.query(GET_FATHER_RESOURCE_CODE, new Object[] { resourceId },
						new ResourceRowMapper());
				if (allFatherIds.size() > 0) {
					for (String fatherIds : allFatherIds) {
						allResourceIds.add(fatherIds);
					}
				}
			}

		}
		// 返回数据库里存在而在resIDStr里没有的资源id的列表
		return allResourceIds;
	}

	/**
	 * 
	 * <p>判断是否有权限</p>
	 * @param roleIds
	 * @param resourceId
	 * @return
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Dec 19, 2009 9:27:25 AM
	 */
	public boolean hasPrivilege(String[] roleIds, String resourceId) throws BizException {
		StringBuffer sb = new StringBuffer();
		for (String roleId : roleIds) {
			sb.append("'").append(roleId).append("',");
		}
		int cnt = queryForInt(SQL_QUERY_PRIVILEGE_FOR_RESOOURCE.replaceAll("ROLE_IDS", sb.length() > 0 ? sb.substring(
				0, sb.length() - 1) : "''"), new Object[] { resourceId });
		return cnt > 0;
	}

	/**
	 * 
	 * <p>查询url和角色之间的映射关系</p>
	 * @return
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Dec 19, 2009 9:28:59 AM
	 */
	@SuppressWarnings( { "unchecked", "unchecked" })
	public Map<String, List<String>> queryUrlRoleMapping() throws BizException {
		List<Map<String, Object>> list = this.queryForList(SQL_QUERY_URL_ROLE_MAPPING);
		Map<String, List<String>> retMap = new HashMap<String, List<String>>();
		for (Map map : list) {
			String content = map.get("CONTENT").toString();
			if (!retMap.keySet().contains(content)) {
				retMap.put(content, new ArrayList());
			}
			retMap.get(content).add(map.get("ROLE_ID").toString());
		}
		return retMap;
	}

	/**
	 * <p>判断是否本角色点击的角色树节点</p>
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 * @throws BizException
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-4-26 下午03:44:12
	 */
	@SuppressWarnings("unchecked")
	public boolean isMe(String userId, String roleId) throws BizException {
		List<TreeNode> list = this.query(IS_ME_SQL, new Object[] { userId }, new RoleRowMapper());
		boolean flag = false;
		for (TreeNode tree : list) {
			if (roleId.equals(tree.getKey())) {
				flag = true;
				break;
			}
		}

		return flag;
	}

	/**
	 * 
	 * <p>本方法用于删除角色之前级联删除角色与资源的映射关系</p>
	 * @param roleId
	 * @author: 【徐冉】 - 【xu_r@neusoft.com】
	 * @data: Create on 2010-1-6 下午01:33:28
	 */
	public void deleteResourceRoleMapping(String roleId) throws BizException {
		update(DELETE_ROLE_RESOURCE_RES, new Object[] { roleId });
	}

	/**
	 * 
	 * <p>级联删除子节点的资源映射</p>
	 * @param roleId
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 6, 2010 4:47:17 PM
	 */
	@SuppressWarnings("unchecked")
	public void recDelResourceRoleMapping(String roleId) throws BizException {
		List<TreeNode> sonList = this.query(GET_SON_TREECODES, new Object[] { roleId }, new RoleRowMapper());
		if (sonList.size() > 0) {
			for (TreeNode tree : sonList) {
				this.update(DELETE_ROLE_RESOURCE_RES, new Object[] { tree.getKey() });
			}
		}

	}

	/**
	 * 
	 * <p>得到资源树的根节点</p>
	 * @return
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 6, 2010 7:33:38 PM
	 */
	public TreeNode getReourceRootNode() throws BizException {
		return (TreeNode) this.query(GET_RESOURCE_NODE, new Object[] { "root" }, new PrivilegeRowMapper()).get(0);
	}

}
