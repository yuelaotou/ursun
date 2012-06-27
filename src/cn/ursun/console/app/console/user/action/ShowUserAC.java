package cn.ursun.console.app.console.user.action;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.ursun.console.app.console.configloader.AuthConfigHolder;
import cn.ursun.console.app.console.role.bizservice.RoleBS;
import cn.ursun.console.app.console.user.bizservice.UserBS;
import cn.ursun.console.app.console.user.dto.UserQC;
import cn.ursun.console.app.domain.Role;
import cn.ursun.console.app.domain.Unit;
import cn.ursun.console.app.domain.UserAccount;
import cn.ursun.console.app.domain.UserExtendColumn;
import cn.ursun.platform.core.action.WeeAction;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.ps.domain.Code;
import cn.ursun.platform.ps.domain.TreeNode;
import cn.ursun.platform.ps.security.WeeSecurityInfo;
import cn.ursun.platform.ps.util.TreeUtil;

/**
 * <p>用户查询展示</p>
 * @author 李志伟 - li.zhw@neusoft.com
 * @version 1.0
 */
public class ShowUserAC extends WeeAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色业务操作对象
	 */
	private RoleBS roleBS;

	/**
	 * 用户业务操作对象
	 */
	private UserBS userBS = null;

	/**
	 * 查询条件对象
	 */
	private UserQC userQC = null;

	/**
	 * 用户帐户信息对象
	 */
	private UserAccount userAccount = null;

	/**
	 * 用户列表
	 */
	private List<UserAccount> userList = null;

	/**
	 * 组织机构ID
	 */
	private String unit = null;

	/**
	 * 组织机构对象
	 */
	private Unit unitInfo = null;

	/**
	 * grid定制对象
	 */
	private Pagination pagination = null;

	/**
	 * 角色树对象
	 */
	private TreeNode root;

	/**
	 * 用户状态标识
	 */
	private String op = null;

	/**
	 * 角色对象数组
	 */
	private Role[] roles = null;

	/**
	 * 当前用户可管理的角色ID
	 */
	private String enableRoleIds = null;

	/**
	 * 登录用户信息对象
	 */
	private WeeSecurityInfo wsi = WeeSecurityInfo.getInstance();

	/**
	 * 用户扩展信息自定义字段集合
	 */
	private Map<String, UserExtendColumn> columnMap = null;

	/**
	 * 字典表集合
	 */
	private Map<String, List<Code>> dictMap = null;

	/**
	 * 排序的key集合
	 */
	private List<String> columnKeyList = null;

	/**
	 * 作为查询条件的字段的集合
	 */
	private List<UserExtendColumn> queryColumnList = null;

	/**
	 * 跳转类型
	 */
	private String ftype = null;

	public String forward() throws Exception{
		if(StringUtils.isEmpty(ftype)){
			ftype="query";
		}
		if("query".equals(ftype)){
			return queryUserListOfUnit();
		}		
		if("queryc".equals(ftype)){
			return queryUserList();
		}
		if("add".equals(ftype)){
			return showAddPage();
		}
		if("edit".equals(ftype)){
			return showEditPage();
		}
		if("detail".equals(ftype)){
			return queryAccountById();
		}
		return "";
	}

	/**
	 * <p>
	 * 跳转到增加用户页面
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author: 张猛 - zhang_meng@neusoft.com
	 * @data: Create on 2009年12月9日9:14:15
	 */
	public String showAddPage() throws Exception {
		columnMap = AuthConfigHolder.getInstance().getAuthConfig().getUserExpandInfo();
		columnKeyList = getSortKeySet(columnMap);
		dictMap = userBS.queryCodeListForInit(columnMap);
		return "content_add";
	}
	
	/**
	 * <p>
	 * 跳转到修改用户页面
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author: 张猛 - zhang_meng@neusoft.com
	 * @data: Create on 2009年12月9日9:14:15
	 */
	public String showEditPage() throws Exception {
		String userId = userAccount.getUser().getId();
		userAccount = userBS.queryAccountByUserId(userId);
		columnMap = AuthConfigHolder.getInstance().getAuthConfig().getUserExpandInfo();
		dictMap = userBS.queryCodeListForInit(columnMap);
		UserExtendColumn column = null;
		String filterValue = null;
		// 取出未配置'需要初始化'的字典信息,这部分字典在新增时不需要,利用ajax请求获取,但是修改时需要,所以在这里获取
		for (String key : columnMap.keySet()) {
			column = columnMap.get(key);
			if (StringUtils.isNotEmpty(column.getRelateColumn())) {
				if (userAccount.getUser().getExtendInfo() != null) {
					filterValue = userAccount.getUser().getExtendInfo().get(column.getRelateColumn())[0];
				}
				if (StringUtils.isNotEmpty(filterValue)) {
					dictMap.put(key, userBS.queryRelateCodeList(key, filterValue));
				}
			}
		}
		columnKeyList = getSortKeySet(columnMap);
		return "content_edit";
	}
	/**
	 * <p>
	 * 根据用户名、用户真实姓名查询用户列表
	 * </p>
	 * 
	 * @return
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午04:43:48
	 */
	public String queryUserList() throws Exception {
		if (userQC == null) {
			userQC = new UserQC();
		}
		queryColumnList = new ArrayList<UserExtendColumn>();
		// 查询条件Map
		Map<String, String> qcMap = userQC.getExtendCondition();
		UserExtendColumn column = null;
		String filterValue = null;
		// 取出配置的column对象信息
		columnMap = AuthConfigHolder.getInstance().getAuthConfig().getUserExpandInfo();
		// 取出已配置'需要初始化'的字典表信息
		dictMap = userBS.queryCodeListForInit(columnMap);
		// 取出未配置'需要初始化'的字典表信息,这部分字典表在新增时不需要,利用ajax请求获取,但是作为查询条件并进行查询后需要,所以在这里获取
		for (String key : columnMap.keySet()) {
			column = columnMap.get(key);
			if (StringUtils.isNotEmpty(column.getRelateColumn())) {
				if (qcMap != null) {
					filterValue = qcMap.get(column.getRelateColumn());
				}
				if (StringUtils.isNotEmpty(filterValue)) {
					dictMap.put(key, userBS.queryRelateCodeList(key, filterValue));
				}
			}
		}
		// 获取按column对象index属性排序后的keySet
		columnKeyList = getSortKeySet(columnMap);
		// 获取排序后的需要作为列表查询条件的column对象集合
		for (String key : columnKeyList) {
			// 如果此字段配置为作为列表查询条件,需要校验配置是否正确,并且设置到queryColumnList
			if ("true".equals(columnMap.get(key).getIsquery())) {
				// 如果未配置operate字段,即查询类型,则抛出异常
				if (StringUtils.isEmpty(columnMap.get(key).getOperate())) {
					throw new RuntimeException("File 'auth-config.xml' config error!");
				}
				// 如果此字段依赖于其他字段,但是被依赖的字段未配置为查询条件,则抛出异常
				String relate = columnMap.get(key).getRelateColumn();
				if (StringUtils.isNotEmpty(relate) && (!"true".equals(columnMap.get(relate).getIsquery()))) {
					throw new RuntimeException("File 'auth-config.xml' config error!");
				}
				queryColumnList.add(columnMap.get(key));
			}
		}

		userQC.setUnit(unit);
		if (pagination == null) {
			pagination = new Pagination();
		}
		userQC.setEnabled(op);
		userQC.setUserId(wsi.getUserId());
		userList = userBS.queryUserList(userQC, pagination);
		this.setPagination(pagination);
		return "userList";
	}

	/**
	 * <p>
	 * 查询组织机构下的用户列表，并且是在当前用户所属角色下的用户
	 * </p>
	 * 
	 * @return
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午04:44:15
	 */
	public String queryUserListOfUnit() throws Exception {
		if (userQC == null) {
			userQC = new UserQC();
		}
		queryColumnList = new ArrayList<UserExtendColumn>();
		// 取出配置的column对象信息
		columnMap = AuthConfigHolder.getInstance().getAuthConfig().getUserExpandInfo();
		// 取出已配置'需要初始化'的字典表信息
		dictMap = userBS.queryCodeListForInit(columnMap);
		// 获取按column对象index属性排序后的keySet
		columnKeyList = getSortKeySet(columnMap);
		// 获取排序后的需要作为列表查询条件的column对象集合
		for (String key : columnKeyList) {
			if ("true".equals(columnMap.get(key).getIsquery())) {
				if (StringUtils.isEmpty(columnMap.get(key).getOperate())) {
					throw new RuntimeException("File 'auth-config.xml' config error!");
				}
				String relate = columnMap.get(key).getRelateColumn();
				if (StringUtils.isNotEmpty(relate) && (!"true".equals(columnMap.get(relate).getIsquery()))) {
					throw new RuntimeException("File 'auth-config.xml' config error!");
				}
				queryColumnList.add(columnMap.get(key));
			}
		}
		if (pagination == null) {
			pagination = new Pagination();
		}

		userQC.setUserId(wsi.getUserId());
		userList = userBS.queryUserListOfUnit(unit, userQC, pagination);
		this.setPagination(pagination);
		return "userList";
	}
	
	public String queryUserListOfUnitJSON() throws Exception {
		if (userQC == null) {
			userQC = new UserQC();
		}
		queryColumnList = new ArrayList<UserExtendColumn>();
		// 取出配置的column对象信息
		columnMap = AuthConfigHolder.getInstance().getAuthConfig().getUserExpandInfo();
		// 取出已配置'需要初始化'的字典表信息
		dictMap = userBS.queryCodeListForInit(columnMap);
		// 获取按column对象index属性排序后的keySet
		columnKeyList = getSortKeySet(columnMap);
		// 获取排序后的需要作为列表查询条件的column对象集合
		for (String key : columnKeyList) {
			if ("true".equals(columnMap.get(key).getIsquery())) {
				if (StringUtils.isEmpty(columnMap.get(key).getOperate())) {
					throw new RuntimeException("File 'auth-config.xml' config error!");
				}
				String relate = columnMap.get(key).getRelateColumn();
				if (StringUtils.isNotEmpty(relate) && (!"true".equals(columnMap.get(relate).getIsquery()))) {
					throw new RuntimeException("File 'auth-config.xml' config error!");
				}
				queryColumnList.add(columnMap.get(key));
			}
		}
		if (pagination == null) {
			pagination = new Pagination();
		}

		userQC.setUserId(wsi.getUserId());
		userList = userBS.queryUserListOfUnit(unit, userQC, pagination);
		this.setPagination(pagination);
		return JSON;
	}

	/**
	 * <p>
	 * 根据userId查询用户账户信息
	 * </p>
	 * 
	 * @return
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午04:46:53
	 */
	public String queryAccountById() throws Exception {
		List<Code> codeList = null;
		StringBuffer buffer = null;
		UserExtendColumn column = null;
		String filterValue = null;

		// 获取用户所有信息
		userAccount = userBS.queryAccountByUserId(userAccount.getUser().getId());
		// 获取数据库中保存的扩展信息
		Map<String, String[]> infoMap = userAccount.getUser().getExtendInfo();
		// 取出配置的column对象信息
		columnMap = AuthConfigHolder.getInstance().getAuthConfig().getUserExpandInfo();
		// 取出已配置'需要初始化'的字典表信息
		dictMap = userBS.queryCodeListForInit(columnMap);
		// 取出未配置'需要初始化'的字典信息,这部分字典在新增时不需要,利用ajax请求获取,但是修改时需要,所以在这里获取
		for (String key : columnMap.keySet()) {
			// 取得column对象
			column = columnMap.get(key);
			// 如果此column对象的内容依赖于其他column(一般均为下拉列表),则需要获取内容数据
			if (StringUtils.isNotEmpty(column.getRelateColumn())) {
				if (infoMap != null) {
					// 取得此column对象所依赖的column的值
					filterValue = infoMap.get(column.getRelateColumn())[0];
				}
				if (StringUtils.isNotEmpty(filterValue)) {
					// 获取内容数据并设置到字典Map中
					dictMap.put(key, userBS.queryRelateCodeList(key, filterValue));
				}
			}
		}
		// 将属性值转换为字典中的名称,显示详细信息时需要直接显示字典中的名称
		for (String key : columnMap.keySet()) {
			column = columnMap.get(key);
			if ("dict".equalsIgnoreCase(column.getType()) || "radio".equalsIgnoreCase(column.getType())
					|| "checkbox".equals(column.getType())) {
				// 根据字段名获取对应的字典集合
				codeList = dictMap.get(key);
				buffer = new StringBuffer();
				if (infoMap != null) {
					// 如果用户扩展信息中不包含此字段,不进行转换
					if (!infoMap.containsKey(key)) {
						continue;
					}
					for (String code : infoMap.get(key)) {
						for (Code codeEntry : codeList) {
							// 如果用户扩展信息属性值不为空,取得字典中对应的名称
							if (!"".equals(code) && codeEntry.getCode().equals(code)) {
								buffer.append(codeEntry.getCodeName()).append(" ");
							}
						}
						// 在扩展信息中移除该字段
						infoMap.remove(key);
						// 在扩展信息中加入字段名及属性值对应的字典名称
						infoMap.put(key, new String[] { buffer.toString() });
					}
				}
			}
		}
		// 设置用户扩展信息
		userAccount.getUser().setExtendInfo(infoMap);
		// 获取按column对象index属性排序后的keySet
		columnKeyList = getSortKeySet(columnMap);
		return "content_show";
	}

	/**
	 * <p>
	 * 查询当前用户信息
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午04:47:07
	 */
	public String queryCurrentUserInfo() throws Exception {
		return "";
	}

	/**
	 * <p>查询角色树
	 * 调用bs的queryUnitTree方法 赋值给rootUnit
	 * </p>
	 * @return 
	 * @throws Exception
	 * @author: 张猛 - zhang_meng@neusoft.com
	 * @data: Create on 2009年12月15日10:26:33
	 */
	public String queryRoleTree() throws Exception {
		root = roleBS.queryRoleTreeWithoutAnoRole();
		return JSON;
	}

	/**
	 * <p>通过用户ID查询关联的组织机构信息</p>
	 * @return
	 * @throws Exception
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 25, 2009 12:45:49 PM
	 */
	public String queryUnitByUserId() throws Exception {
		unitInfo = userBS.queryUnitByUserId(userAccount.getUser().getId());
		return JSON;
	}

	/**
	 * <p>通过用户ID查询关联的角色信息</p>
	 * @return
	 * @throws Exception
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 25, 2009 12:46:09 PM
	 */
	public String queryRolesByUserId() throws Exception {
		List<TreeNode> list = userBS.queryRolesOfUser(wsi.getUserId());
		StringBuffer temp = new StringBuffer();
		for (TreeNode node : list) {
			temp.append(node.getKey()).append(",");
		}
		if (temp.length() != 0) {
			enableRoleIds = temp.toString().substring(0, temp.length() - 1);
		}
		roles = userBS.queryRolesByUserId(userAccount.getUser().getId());
		return JSON;
	}

	/**
	 * 
	 * <p>查询角色树</p>
	 * @return
	 * @throws Exception
	 * @author: 张猛 - zhang_meng@neusoft.com
	 * @data: Create on Dec 10, 2009 10:37:53 AM
	 */
	public String queryRolesOfUser() throws Exception {
		// 查询当前选中角色的父角色下的所有资源，形成树结构。然后将当前选中角色的所有资源作为被选中资源树节点。
		List<TreeNode> list = userBS.queryRolesOfUser(userAccount.getUser().getId());
		root = TreeUtil.createTreeRelation(list);
		return JSON;
	}

	/**
	 * <p>将map按规则排序后返回keyset</p>
	 * @param columnMap
	 * @return
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 11, 2010 6:18:44 PM
	 */
	private List<String> getSortKeySet(Map<String, UserExtendColumn> columnMap) {
		List<UserExtendColumn> list = new ArrayList<UserExtendColumn>(columnMap.values());
		Collections.sort(list, new Comparator<UserExtendColumn>() {

			public int compare(UserExtendColumn o1, UserExtendColumn o2) {
				return (o1.getIndex() - o2.getIndex());
			}
		});
		List<String> keyList = new ArrayList<String>();
		for (UserExtendColumn column : list) {
			keyList.add(column.getName());
		}
		return keyList;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public List<UserAccount> getUserList() {
		return userList;
	}

	public void setUserList(List<UserAccount> userList) {
		this.userList = userList;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public UserQC getUserQC() {
		return userQC;
	}

	public void setUserQC(UserQC userQC) {
		this.userQC = userQC;
	}

	public void setUserBS(UserBS userBS) {
		this.userBS = userBS;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoleBS(RoleBS roleBS) {
		this.roleBS = roleBS;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public Unit getUnitInfo() {
		return unitInfo;
	}

	public void setUnitInfo(Unit unitInfo) {
		this.unitInfo = unitInfo;
	}

	public Role[] getRoles() {
		return roles;
	}

	public void setRoles(Role[] roles) {
		this.roles = roles;
	}

	public String getEnableRoleIds() {
		return enableRoleIds;
	}

	public void setEnableRoleIds(String enableRoleIds) {
		this.enableRoleIds = enableRoleIds;
	}

	public Map<String, UserExtendColumn> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map<String, UserExtendColumn> columnMap) {
		this.columnMap = columnMap;
	}

	public Map<String, List<Code>> getDictMap() {
		return dictMap;
	}

	public void setDictMap(Map<String, List<Code>> dictMap) {
		this.dictMap = dictMap;
	}

	public List<String> getColumnKeyList() {
		return columnKeyList;
	}

	public void setColumnKeyList(List<String> columnKeyList) {
		this.columnKeyList = columnKeyList;
	}

	public List<UserExtendColumn> getQueryColumnList() {
		return queryColumnList;
	}

	public void setQueryColumnList(List<UserExtendColumn> queryColumnList) {
		this.queryColumnList = queryColumnList;
	}

	
	public String getFtype() {
		return ftype;
	}

	
	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

}
