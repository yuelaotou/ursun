/**
 * 文件名：LogAspect.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：Nov 20, 2008 10:52:17 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.bizlog.aop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.io.Resource;

import cn.ursun.platform.core.aop.aspect.WeeBaseAspect;
import cn.ursun.platform.core.context.RequestContext;
import cn.ursun.platform.core.event.WeeEventPublisher;
import cn.ursun.platform.ps.bizlog.BizLogger;
import cn.ursun.platform.ps.bizlog.IBizLog;
import cn.ursun.platform.ps.interceptor.ClientInfoContextInterceptor;
import cn.ursun.platform.ps.security.WeeSecurityInfo;

/**
 * <p>
 * 记录日志拦截器
 * </p>
 * 
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on Nov 20, 2008 10:52:17 PM
 */
@Aspect
public class BizLogAspect extends WeeBaseAspect {

	private Log logger = LogFactory.getLog(BizLogAspect.class);

	private Properties properties = null;

	private Resource propertiesResource = null;

	public void setPropertiesResource(Resource propertiesResource) {
		this.propertiesResource = propertiesResource;
	}

	/**
	 * <p>
	 * 记录日志信息
	 * </p>
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 * @author: 段鹏 - duanp@neusoft.com
	 * @date: Created on Nov 21, 2008 12:58:18 PM
	 */
	@Around(value = "@annotation(cn.ursun.platform.ps.bizlog.aop.annotation.BizExecution)")
	public Object recordBizLog(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		try {
			BizLogger bizLogger = BizLogger.getInstance();
			// BizExecution annotation = (BizExecution)
			// getAnnotation(pjp.getSignature(), BizExecution.class);

			// 中文姓名
			String userFullName = WeeSecurityInfo.getInstance()
					.getUserFullName();
			// 用户ID
			String userId = WeeSecurityInfo.getInstance().getUserId();

			String userName = WeeSecurityInfo.getInstance().getUserName();

			// 业务代码
			String bizCode = this.getMethod(pjp.getSignature())
					.getDeclaringClass().getName();
			// 功能代码
			String funcCode = this.getMethod(pjp.getSignature()).getName();
			// 描述
			// String description = null;
			// IP地址
			String ip = (String) RequestContext.getContext().get(
					ClientInfoContextInterceptor.CLIENT_IP);
			// 模块级别
			String moduleLevels = (String) RequestContext.getContext().get(
					ClientInfoContextInterceptor.MODULE_LEVEL);

			boolean isAnonymousUser = WeeSecurityInfo.getInstance()
					.isAnonymousUser();

			// 操作角色名
			String roleNames[] = WeeSecurityInfo.getInstance()
					.getUserRoleName();
			StringBuffer roleNameStr = new StringBuffer();

			for (String rolename : roleNames) {
				roleNameStr.append(rolename).append(",");
			}

			List<String> moduleLevel = new ArrayList<String>();
			if (moduleLevels == null) {
				logger.warn("parameter[source_flag] doesn't configure");
			} else {
				if (moduleLevels.length() >= 2) {
					moduleLevel.add(moduleLevels.substring(0, 2));
				}
				if (moduleLevels.length() >= 5) {
					moduleLevel.add(moduleLevels.substring(0, 5));
				}
				if (moduleLevels.length() >= 7) {
					moduleLevel.add(moduleLevels.substring(0, 7));
				}
				// description = getDescription(moduleLevels);
			}
			// 操作类型
			// String type = annotation.type();
			// 部门
			String department = WeeSecurityInfo.getInstance().getUserDeptName();
			// 性别
			String sex = WeeSecurityInfo.getInstance().getUserSex() == null ? ""
					: WeeSecurityInfo.getInstance().getUserSex().name();

			bizLogger.setAccessIP(ip);
			bizLogger.setBizCode(bizCode);
			bizLogger.setFuncCode(funcCode);
			bizLogger.setModuleLevel(moduleLevel.toArray(new String[] {}));
			// bizLogger.setDescription(description);
			bizLogger.setOperationDate(new Date());
			bizLogger.setDepartment(department);
			bizLogger.setOperator(userId);
			bizLogger.setOperatorName(userFullName);
			bizLogger.setLoginName(userName);
			bizLogger.setSex(sex);
			bizLogger.setIsAnonymousUser(isAnonymousUser);
			if (roleNameStr.toString().length() > 1) {
				bizLogger.setRoleName(roleNameStr.toString().substring(0,
						roleNameStr.toString().length() - 1));
			}
			bizLogger.setXmlData();
			// bizLogger.setOperationType(type);

			recordBizLogToDB(bizLogger.getBizLog());
			bizLogger.commit();
		} catch (Throwable ex) {
			logger.debug("BizLog is Error...");
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * <p>
	 * 将日志信息记录到数据库中
	 * </p>
	 * 
	 * @author: 段鹏 - duanp@neusoft.com
	 * @date: Created on Nov 21, 2008 12:10:18 AM
	 */
	private void recordBizLogToDB(IBizLog log) {
		WeeEventPublisher.getInstance().fireAyncEvent("BIZLOG_RECORDS", log);
	}

	private String getDescription(String key) {
		if (properties == null) {
			properties = new Properties();
			try {
				properties.load(propertiesResource.getInputStream());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return properties.getProperty(key, "执行操作");
	}
}
