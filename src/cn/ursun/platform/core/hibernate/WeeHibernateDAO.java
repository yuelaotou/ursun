/**
 * 文件名: WeeHibernateDAOSupport.java
 * 创建时间：Aug 15, 2009 12:11:53 PM
 * 创建人：兰硕
 * 版权：东软集团股份有限公司
 */
package cn.ursun.platform.core.hibernate;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 *
 * @author 兰硕
 */
public class WeeHibernateDAO extends HibernateDaoSupport {
	
	protected HibernateTemplate ht = null;

	public WeeHibernateDAO() {
		ht = getHibernateTemplate();
	}
}
