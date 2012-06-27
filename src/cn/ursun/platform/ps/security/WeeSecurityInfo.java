/**
 * 文件名：SecurityUserInfo.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Aug 31, 2008 4:28:57 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.security;

import java.util.List;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;

import cn.ursun.console.app.console.facade.AuthFacade;
import cn.ursun.console.app.domain.Role;
import cn.ursun.console.app.domain.Sex;
import cn.ursun.console.app.domain.Unit;
import cn.ursun.platform.core.context.RequestContext;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.interceptor.ClientInfoContextInterceptor;

/**
 * <p>
 * 获取用户相关信息
 * </p>
 * 
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Aug 31, 2008 4:28:57 PM
 */
public class WeeSecurityInfo {

	private static WeeSecurityInfo instance = null;

	AuthFacade authFacade = null;

	private WeeSecurityInfo() {
	}

	public static WeeSecurityInfo getInstance() {
		if (instance == null)
			instance = new WeeSecurityInfo();
		return instance;
	}

	/**
	 * <p>
	 * Discription:获取用户ID
	 * </p>
	 * Created on 2009-10-29
	 * 
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public String getUserId() {
		Object principal = getAuthentication().getPrincipal();
		Object details = getAuthentication().getDetails();
		if (principal instanceof WeeUserDetails) {
			WeeUserDetails d = (WeeUserDetails) principal;
			return d.getUserAccount().getUser().getId();
		}
		return null;
	}

	/**
	 * <p>
	 * 查询用户全名
	 * </p>
	 * 
	 * @return
	 * @throws BizException
	 * @author: 段鹏 - duanp@neusoft.com
	 * @date: Created on 2009-3-10 上午11:39:12
	 */
	public String getUserFullName() throws BizException {
		Object principal = getAuthentication().getPrincipal();
		Object details = getAuthentication().getDetails();
		if (principal instanceof WeeUserDetails) {
			WeeUserDetails d = (WeeUserDetails) principal;
			return d.getUserAccount().getUser().getFullName();
		}
		return null;
	}

	/**
	 * <p>
	 * Discription:获取用户名
	 * </p>
	 * Created on 2009-10-29
	 * 
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public String getUserName() {
		Authentication auth = getAuthentication();
		return auth.getName();
	}

	/**
	 * <p>
	 * 获取用户所属机构
	 * </p>
	 * 
	 * @return
	 * @throws BizException
	 * @author: 段鹏 - duanp@neusoft.com
	 * @date: Created on 2009-3-10 上午11:39:46
	 */
	public String getUserDeptName() throws BizException {
		Object principal = getAuthentication().getPrincipal();
		Object details = getAuthentication().getDetails();
		if (principal instanceof WeeUserDetails) {
			WeeUserDetails d = (WeeUserDetails) principal;
			List<Unit> units = authFacade.queryUnitByUserId(d.getUserAccount()
					.getUser().getId());
			StringBuffer unitNames = new StringBuffer();
			for (Unit u : units) {
				unitNames.append(u.getUnitName()).append(",");
			}
			return unitNames.length() > 0 ? unitNames.substring(0, unitNames
					.length() - 1) : "";
		}
		return null;
	}

	/**
	 * <p>
	 * [描述方法实现的功能]
	 * </p>
	 * 
	 * @return
	 * @throws BizException
	 * @author: 段鹏 - duanp@neusoft.com
	 * @date: Created on 2009-3-10 上午11:39:46
	 */
	public String getUserDeptId() throws BizException {
		Object principal = getAuthentication().getPrincipal();
		Object details = getAuthentication().getDetails();
		if (principal instanceof WeeUserDetails) {
			WeeUserDetails d = (WeeUserDetails) principal;
			List<Unit> units = authFacade.queryUnitByUserId(d.getUserAccount()
					.getUser().getId());
			StringBuffer unitIds = new StringBuffer();
			for (Unit u : units) {
				unitIds.append(u.getId()).append(",");
			}
			return unitIds.length() > 0 ? unitIds.substring(0,
					unitIds.length() - 1) : "";
		}
		return null;
	}

	/**
	 * <p>
	 * 获得用户性别
	 * </p>
	 * 
	 * @return
	 * @throws BizException
	 * @author: 段鹏 - duanp@neusoft.com
	 * @date: Created on 2009-3-10 上午11:45:07
	 */
	public Sex getUserSex() throws BizException {
		Object principal = getAuthentication().getPrincipal();
		if (principal instanceof WeeUserDetails) {
			WeeUserDetails d = (WeeUserDetails) principal;
			return Sex.parse(d.getUserAccount().getUser().getSex());
		}
		return null;
	}

	/**
	 * <p>
	 * 获取当前用户所拥有的角色ID集合
	 * </p>
	 * 
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com
	 * @date: Created on 2009-4-27 下午07:19:45
	 */
	public String[] getUserRoleIds() {
		GrantedAuthority[] gs = SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		if (gs == null || gs.length == 0)
			return null;
		String[] roleIds = new String[gs.length];
		for (int i = 0; i < roleIds.length; i++) {
			roleIds[i] = gs[i].getAuthority();
		}
		return roleIds;
	}

	/**
	 * <p>
	 * 获取当前用户所拥有的角色名集合
	 * </p>
	 * 
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-13 下午03:21:02
	 */
	public String[] getUserRoleName() throws BizException {
		Role[] roles = authFacade.queryRoleById(getUserRoleIds());
		if (roles != null && roles.length > 0) {
			String[] roleNames = new String[roles.length];
			for (int i = 0; i < roleNames.length; i++) {
				roleNames[i] = roles[i].getRoleName();
			}
			return roleNames;

		} else {
			return null;
		}
	}

	/**
	 * <p>
	 * Discription:用户对指定资源号是否有访问权限
	 * </p>
	 * Created on 2009-10-29
	 * 
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean hasPermission(String resourceId) throws BizException {
		if (this.isAnonymousUser())
			return authFacade.hasPermission(this.getAnonymousRoleId(),
					resourceId);
		else
			return authFacade.hasPermission(this.getUserId(), resourceId);
	}

	/**
	 * <p>
	 * Discription:获取用户IP
	 * </p>
	 * Created on 2009-10-29
	 * 
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public String getUserIP() {
		String ip = (String) RequestContext.getContext().get(
				ClientInfoContextInterceptor.CLIENT_IP);
		return ip;
	}

	/**
	 * <p>
	 * Discription:获取用户所在地区
	 * </p>
	 * Created on 2009-10-29
	 * 
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public String getUserArea() {
		// TODO 接口定义,后续完成实现
		return "";
	}

	/**
	 * <p>
	 * 是否为匿名角色
	 * </p>
	 * 
	 * @return
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-12-18 下午02:58:32
	 */
	public boolean isAnonymousUser() {
		List anonymousRoleList = WeeAuthenticationHolder.getInstance()
				.getAnonymousRole();
		GrantedAuthority[] gs = SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		if (gs == null || gs.length == 0)
			return false;
		for (int i = 0; i < gs.length; i++) {
			for (Object o : anonymousRoleList) {
				if (gs[i].getAuthority().equals((String) o)) {
					return true;

				}
			}
		}
		return false;
	}

	/**
	 * <p>
	 * 获取匿名角色ID
	 * </p>
	 * 
	 * @return
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-6 上午11:24:40
	 */
	public String[] getAnonymousRoleId() {
		List anonymousRoleList = WeeAuthenticationHolder.getInstance()
				.getAnonymousRole();
		if (anonymousRoleList != null && anonymousRoleList.size() > 1) {
			return (String[]) anonymousRoleList.toArray(new String[] {});
		} else
			return null;
	}

	/**
	 * <p>
	 * 是否为超级管理员角色
	 * </p>
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-24 上午11:14:10
	 */
	public boolean isAdminRole(String userId) throws BizException {
		GrantedAuthority[] gs = SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		List adminRole = WeeAuthenticationHolder.getInstance().getAdminRole();
		if (adminRole == null || gs.length == 0)
			return false;
		for (int i = 0; i < gs.length; i++) {
			for (Object o : adminRole) {
				if (gs[i].getAuthority().equals((String) o)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <p>
	 * 获取超级管理员角色
	 * </p>
	 * 
	 * @return
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-6 上午11:25:09
	 */
	public List<String> getAdminRoles() {
		return WeeAuthenticationHolder.getInstance().getAdminRole();
	}

	/**
	 * <p>
	 * 刷新权限信息
	 * </p>
	 * 
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-4 下午02:38:22
	 */
	public void refresh() throws BizException {
		WeeAuthenticationHolder.getInstance().init();
	}

	private Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public AuthFacade getAuthFacade() {
		return authFacade;
	}

	public void setAuthFacade(AuthFacade authFacade) {
		this.authFacade = authFacade;
	}

}
