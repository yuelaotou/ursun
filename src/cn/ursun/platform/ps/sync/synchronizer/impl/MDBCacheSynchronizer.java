/**
 * 文件名：MemoryDBCacheRefreshSynchronizer.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：Aug 7, 2009 11:35:57 AM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.sync.synchronizer.impl;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.security.WeeAuthenticationHolder;
import cn.ursun.platform.ps.security.WeeSecurityInfo;

/**
 * <p> Title: 内存数据库缓存刷新同步器(全量同步)。</p>
 * <p> Description: [描述]</p>
 * <p> Created on 2009-9-16</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author 宋成山 songchengshan@neusoft.com
 * @version 1.0
 */
public class MDBCacheSynchronizer extends MemoryDBCacheSynchronizer {

	public void process() {
		super.process();
		// 更新WeeAuthenticationHolder中的权限信息
		if ("ORG_REFRESH".equals(databaseSynchronizer.getSynchronizerType())) {
			try {
				WeeSecurityInfo.getInstance().refresh();
			} catch (BizException e) {
				e.printStackTrace();
			}
		}
	}

}
