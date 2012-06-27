package cn.ursun.console.app.dao;

import java.util.Date;
import java.util.List;

import cn.ursun.console.app.domain.Account;
import cn.ursun.console.app.domain.UserAccount;
import cn.ursun.platform.core.exception.BizException;

public interface AccountDAO {

	/**
	 * <p>根据accountId查询用户账户信息</p>
	 * @param accountId 账户ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:02:22
	 */
	public Account queryAccountById(String accountId) throws BizException;
	
	/**
	 * <p>根据帐户名获取帐户信息</p>
	 * 
	 * @param username 需要获取信息的帐户名称。
	 * @return 帐户信息。如果帐户不存在，则返回null。
	 * @throws BizException 
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Sep 2, 2008 7:35:00 PM
	 */
	public Account queryAccountByUsername(String username) throws BizException;


	/**
	 * <p>根据userId查询用户账户信息</p>
	 * @param userId 用户ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:02:22
	 */
	public UserAccount queryUserAccountByUserId(String userId) throws BizException;

	/**
	 * <p>根据userName查询用户账户信息</p>
	 * @param userName 用户名
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:02:22
	 */
	public UserAccount queryUserAccountByUserName(String userName) throws BizException;
	
	/**
	 * <p>根据userName查询用户账户信息(模糊查询)</p>
	 * @param userName 用户名
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:02:22
	 */
	public List<UserAccount> queryUserAccountListByUserName(String userName) throws BizException;

	/**
	 * <p>新增用户账户</p>
	 * @param account 需要添加的帐户信息。
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:00
	 */

	public void createAccount(UserAccount useraccount) throws BizException;

	/**
	 * <p>新增用户账户</p>
	 * @param account 需要添加的帐户信息。
	 * @throws BizException
	 * @author: 张猛 - zhang_meng@neusoft.com
	 * @data: Create on 2009年12月11日10:54:31
	 */
	public void updateAccount(UserAccount useraccount) throws BizException;

	/**
	 * <p>验证密码是否正确</p>
	 * @param accountId
	 * @param pwd
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:57
	 */
	public boolean validatePwd(String accountId, String pwd) throws BizException;

	/**
	 * <p>修改用户密码</p>
	 * @param accountId 账户ID
	 * @param newPwd    新密码
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:06:14
	 */
	public boolean updatePassword(String accountId, String newPwd) throws BizException;

	/**
	 * <p>是否帐户存在。</p>
	 * 
	 * @param accountId 帐户Id。
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Created on Sep 6, 2008 12:28:48 PM
	 */
	public boolean isAccountExistedById(String accountId) throws BizException;

	/**
	 * <p>是否帐户存在。</p>
	 * 
	 * @param userId 用户id。
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Created on Sep 6, 2008 12:28:54 PM
	 */
	public boolean isAccountExistedByUserId(String userId) throws BizException;

	/**
	 * <p>检查账户是否存在</p>
	 * @param username 用户名
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:04:39
	 */
	public boolean isAccountExistedByUsername(String username,String accountId) throws BizException;

	/**
	 * <p>使帐户无效</p>
	 * 
	 * @param accountId 需要管理的帐户Id。
	 * @throws BizException 如果帐户Id不存在或已经被删除。
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Created on Sep 2, 2008 7:34:53 PM
	 */
	public void disableAccount(String[] accountId) throws BizException;

	/**
	 * <p>使帐户有效</p>
	 * 
	 * @param accountId 需要管理的帐户Id。
	 * @throws BizException 如果帐户Id不存在或已经被删除。
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Created on Sep 2, 2008 7:34:53 PM
	 */
	public void enableAccount(String[] accountId) throws BizException;

	/**
	 * <p>锁定帐户</p>
	 * 
	 * @param accountId 需要管理的帐户Id。
	 * @param locked 是否锁定。
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Created on Sep 4, 2008 10:15:59 AM
	 */
	public void lockAccount(String accountId, boolean locked) throws BizException;

	/**
	 * <p>设置帐户过期时间</p>
	 * 
	 * @param accountId 帐户Id。
	 * @param expiredDate 过期时间。
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Created on Sep 4, 2008 10:16:03 AM
	 */
	public void assignExpiredDate(String accountId, Date expiredDate) throws BizException;

}
