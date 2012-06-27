package cn.ursun.console.app.console.user.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.ursun.console.app.console.configloader.AuthConfigHolder;
import cn.ursun.console.app.console.user.bizservice.UserBS;
import cn.ursun.console.app.domain.Unit;
import cn.ursun.console.app.domain.UserAccount;
import cn.ursun.console.app.domain.UserExtendColumn;
import cn.ursun.platform.core.action.WeePaginationSupportAction;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.core.util.IDGenerator;
import cn.ursun.platform.ps.domain.Code;

/**
 * <p>用户管理-维护</p>
 * @author 李志伟 - li.zhw@neusoft.com
 * @version 1.0
 */
public class MaintainUserAC extends WeePaginationSupportAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户列表
	 */
	private List<UserAccount> userList = null;

	/**
	 * 组织机构对象
	 */
	private Unit unit = null;

	/**
	 * 用户管理业务操作对象
	 */
	private UserBS userBS = null;

	/**
	 * 角色ID字符串
	 */
	private String roleids = null;

	/**
	 * 可管理的用户角色ID
	 */
	private String enableRoleIds = null;

	/**
	 * 角色ID数组
	 */
	private String[] roleIds = null;

	/**
	 * 用户帐户信息
	 */
	private UserAccount userAccount = null;

	/**
	 * 用户ID字符串
	 */

	private String ids = null;

	/**
	 * 重名标识
	 */
	private boolean exist = false;

	/**
	 * 用户扩展信息自定义字段集合
	 */
	private Map<String, UserExtendColumn> columnMap = null;

	/**
	 * 字典表集合
	 */
	private Map<String, List<Code>> dictMap = null;

	/**
	 * 字段对象
	 */
	private UserExtendColumn userExtendColumn = null;

	/**
	 * 代码表
	 */
	private List<Code> codeList = null;

	/**
	 * 排序的key集合
	 */
	private List<String> columnKeyList = null;

	/**
	 * <p>
	 * 添加用户
	 * </p>
	 * 
	 * @return
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午04:47:23
	 */
	public String addUser() throws Exception {
		if (StringUtils.isNotEmpty(roleids)) {
			roleIds = roleids.split(",");
		}
		userAccount.setRoleIds(roleIds);
		userAccount.getUser().setId(IDGenerator.generateId());
		userAccount.getAccount().setAccountId(IDGenerator.generateId());
		userAccount.getUser().setUnitId(unit.getId());
		userBS.addUser(userAccount);
		return JSON;
	}

	/**
	 * <p>
	 * 修改用户信息
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午04:48:01
	 */
	public String updateUser() throws Exception {
		roleIds = roleids.split(",");
		userAccount.setRoleIds(roleIds);
		userAccount.getUser().setUnitId(unit.getId());
		userBS.updateUser(userAccount, enableRoleIds);
		return JSON;
	}

	/**
	 * <p>
	 * 修改个人信息
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午04:48:42
	 */
	public String updatePersonnalInfo() throws Exception {
		return "";
	}

	/**
	 * <p>
	 * 修改用户密码
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午04:48:57
	 */
	public String updatePassword() throws Exception {
		return "";
	}

	/**
	 * <p>
	 * 删除用户(批量删除)
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午04:49:06
	 */
	public String deleteUserById() throws Exception {
		String[] userIds = userAccount.getUser().getId().split(",");
		userBS.deleteUserById(userIds);
		return NONE;
	}

	/**
	 * <p>
	 * 为用户添加角色 通过userAccount和userId获取用户和角色信息
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午04:49:19
	 */
	public void addRolesForUser() throws Exception {
	}

	/**
	 * <p>
	 * 为用户删除角色 通过userAccount和userId获取用户和角色信息
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午04:49:19
	 */
	public String delRolesForUser() throws Exception {
		return "";
	}

	/**
	 * <p>
	 * 禁用帐户 通过userIds获取用户ID
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:03:40
	 */
	public String disableUser() throws Exception {
		String[] userIds = userAccount.getUser().getId().split(",");
		userBS.disableUser(userIds);
		return NONE;
	}

	/**
	 * <p>
	 * 启用帐户 通过userIds获取用户ID
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:04:08
	 */
	public String enableUser() throws Exception {
		String[] userIds = userAccount.getUser().getId().split(",");
		userBS.enableUser(userIds);
		return NONE;
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
	 * 跳转到增加用户页面
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
	 * 为用户添加角色 1.先清除原有用户与角色关系UserDAO.deleteUserRoleMapping()
	 * 2.添加新的用户与角色关系UserDAO.createUserRoleMapping()
	 * </p>
	 * 
	 * @param userId
	 * @param roleId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:31:11
	 */
	public String updateRoleUser() throws Exception {
		if (roleids != null) {
			roleIds = roleids.split(",");
		}
		userBS.updateRoleForUser(ids, roleIds, enableRoleIds);
		return JSON;
	}

	/**
	 * <p>检查帐户是否存在</p>
	 * @return
	 * @throws Exception
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 25, 2009 12:58:43 PM
	 */
	public String checkAccountByUserName() throws Exception {
		exist = userBS.isAccountExistedByUsername(userAccount.getAccount().getUsername(), userAccount.getAccount()
				.getId());
		return JSON;
	}

	/**
	 * <p>查询关联的代码表</p>
	 * @return
	 * @throws Exception
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 11, 2010 9:15:35 AM
	 */
	public String queryCodeList() throws Exception {
		codeList = userBS.queryRelateCodeList(userExtendColumn.getName(), userExtendColumn.getFilterValue());
		return JSON;
	}

	/**
	 * <p>重置用户密码（系统管理）</p>
	 * @return
	 * @throws Exception
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 28, 2009 10:31:27 AM
	 */
	public String resetPassword() throws Exception {
		userBS.resetPassword(userAccount.getUser().getId(), userAccount.getAccount().getPassword());
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

	public void setUserBS(UserBS userBS) {
		this.userBS = userBS;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<UserAccount> getUserList() {
		return userList;
	}

	public void setUserList(List<UserAccount> userList) {
		this.userList = userList;
	}

	public String getRoleids() {
		return roleids;
	}

	public void setRoleids(String roleids) {
		this.roleids = roleids;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
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

	public UserExtendColumn getUserExtendColumn() {
		return userExtendColumn;
	}

	public void setUserExtendColumn(UserExtendColumn userExtendColumn) {
		this.userExtendColumn = userExtendColumn;
	}

	public List<Code> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<Code> codeList) {
		this.codeList = codeList;
	}

	public List<String> getColumnKeyList() {
		return columnKeyList;
	}

	public void setColumnKeyList(List<String> columnKeyList) {
		this.columnKeyList = columnKeyList;
	}
}
