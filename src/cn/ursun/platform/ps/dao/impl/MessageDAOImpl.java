package cn.ursun.platform.ps.dao.impl;

/**
 * 文件名：MessageDAOImpl.java
 * 
 * 创建人：陈乃明 - chennm@neusoft.com
 * 
 * 创建时间：Mar 31, 2009 9:38:54 AM
 * 
 * 版权所有：东软集团股份有限公司
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;

import cn.ursun.console.app.domain.Role;
import cn.ursun.console.app.domain.User;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.core.jdbc.WeeJdbcDAO;
import cn.ursun.platform.ps.dao.MessageDAO;
import cn.ursun.platform.ps.domain.Code;
import cn.ursun.platform.ps.domain.Message;
import cn.ursun.platform.ps.domain.MessageConfig;
import cn.ursun.platform.ps.security.WeeSecurityInfo;

/**
 * <p>消息DAO操作</p>
 *
 * @author 创建人：陈乃明 - chennm@neusoft.com
 * @version 1.0 Created on Mar 31, 2009 9:38:54 AM
 */
public class MessageDAOImpl extends WeeJdbcDAO implements MessageDAO {

	/**
	 * 发件箱消息
	 */
	private static final String SQL_QUERY_MSG_SEND = "select PM.ID, PM.TITLE, PMT.TYPE_DESC, PM.FLAG, PM.UPDATE_DATE,PM.START_DATE"
			+ "  from PS_MWEEAGE PM, PS_MWEEAGE_TYPE PMT" + " where PMT.TYPE_ID = PM.TYPE_ID and PM.FLAG <> 2 ";

	/**
	 * 发件箱信息总数
	 */
	private static final String SQL_QUERY_MSG_SEND_TOTA = "select count(1) from PS_MWEEAGE PM, PS_MWEEAGE_TYPE PMT "
			+ " where PMT.TYPE_ID = PM.TYPE_ID and PM.FLAG <> 2 ";

	/**
	 * 实时消息总数
	 */
	private static final String SQL_QUERY_MEG_INACTIVE_TOTAL = "select count(1) from PS_MWEEAGE PM, PS_MWEEAGE_POLL PMP"
			+ " where PMP.MWEEAGE_ID = PM.ID and PM.START_DATE < sysdate and PMP.USER_ID = ? and PMP.STATE = 0";

	/**
	 * 实时消息
	 */
	private static final String SQL_QUERY_MEG_INACTIVE = "select PM.ID, PM.TITLE, PMT.TYPE_DESC,PM.START_DATE, SYSDATE CURR_DATE"
			+ "  from PS_MWEEAGE PM, PS_MWEEAGE_POLL PMP, PS_MWEEAGE_TYPE PMT, VIEW_PS_USER VPU"
			+ " where PMP.MWEEAGE_ID = PM.ID and PMT.TYPE_ID = PM.TYPE_ID and"
			+ "       PMP.USER_ID = VPU.USER_ID and PM.START_DATE <= sysdate and"
			+ "       PMP.USER_ID = ? and PMP.STATE = 0";

	/**
	 * 收件箱消息的详细信息
	 */
	private static final String SQL_QUERY_MSG_REV_DETAIL = "select PM.ID, PM.TITLE, PMT.TYPE_DESC,PMT.TYPE_NAME, V.USER_NAME, PMD.CONTENT, PM.START_DATE,  PM.CALLBACK_PARAMS "
		+ "  from PS_MWEEAGE_POLL PMP, PS_MWEEAGE PM, PS_MWEEAGE_DETAIL PMD, PS_MWEEAGE_TYPE PMT, VIEW_PS_USER V "
		+ " where PMP.MWEEAGE_ID = PM.ID and PMD.MWEEAGE_ID = PM.ID and "
		+ "       PMT.TYPE_ID = PM.TYPE_ID and V.USER_ID = PM.PUBLISHER_ID "
		+ "       and pm.ID=? and pmp.USER_ID=? ";
	/**
	 * 收件箱消息的详细信息-公告
	 */
	private static final String SQL_QUERY_MSG_REV_DETAIL_BULLETIN = "select PM.ID, PM.TITLE, PMT.TYPE_DESC,PMT.TYPE_NAME, V.USER_NAME, PMD.CONTENT, PM.START_DATE,  PM.CALLBACK_PARAMS "
			+ "  from PS_MWEEAGE PM, PS_MWEEAGE_DETAIL PMD, PS_MWEEAGE_TYPE PMT, VIEW_PS_USER V "
			+ " where PM.ID = PMD.MWEEAGE_ID and PMT.TYPE_ID = PM.TYPE_ID and V.USER_ID = PM.PUBLISHER_ID "
			+ "       and pm.ID=?";

	/**
	 * 发件箱消息的详细信息
	 */
	private static final String SQL_QUERY_MSG_SEND_DETAIL = "select PM.ID, PM.TITLE, PMT.TYPE_DESC, PMT.TYPE_NAME,PMT.TYPE_ID,PMD.CONTENT, PM.START_DATE"
			+ "  from PS_MWEEAGE PM, PS_MWEEAGE_DETAIL PMD, PS_MWEEAGE_TYPE PMT"
			+ " where PMD.MWEEAGE_ID = PM.ID and PMT.TYPE_ID = PM.TYPE_ID and PM.ID = ? and"
			+ "       PM.PUBLISHER_ID = ?";

	/**
	 * 更新消息状态
	 */
	private static final String SQL_UPDATE_MEG_STATE = "update PS_MWEEAGE_poll pp set PP.STATE = 1 where  PP.USER_ID = ? and pp.MWEEAGE_ID in (select PM.ID"
			+ "  from PS_MWEEAGE PM, PS_MWEEAGE_POLL PMP "
			+ " where PMP.MWEEAGE_ID = PM.ID and PM.START_DATE <= ? and PMP.USER_ID = ?)";

	/**
	 * 收件箱消息(不含公告)
	 */
	private static final String SQL_QUERY_MSG_REV = "select PM.ID, PM.TITLE, PMT.TYPE_DESC, PMP.STATE, V.USER_NAME, PM.START_DATE"
			+ "  from PS_MWEEAGE PM, PS_MWEEAGE_POLL PMP, PS_MWEEAGE_TYPE PMT, VIEW_PS_USER V"
			+ " where PMP.MWEEAGE_ID = PM.ID and PMT.TYPE_ID = PM.TYPE_ID and"
			+ "       PM.PUBLISHER_ID = V.USER_ID and PM.START_DATE <= sysdate ";
	/**
	 * 收件箱消息(仅公告)
	 */
	private static final String SQL_QUERY_MSG_REV_BULLETIN = "select pm.id,pm.title,pmt.type_desc,(select count(*) from ps_message_poll pmp where pmp.message_id=pm.id and pmp.user_id=? ) state,v.user_name,pm.start_date from ps_message pm,ps_message_type pmt,VIEW_PS_USER V where PMT.TYPE_ID = PM.TYPE_ID and PM.PUBLISHER_ID = V.USER_ID and pm.type_id=100 order by start_date desc";
	/**
	 * 收件箱消息(仅数据更新)
	 */
	private static final String SQL_QUERY_MSG_REV_EMAIL = "select pm.id,pm.title,pmt.type_desc,(select count(*) from ps_message_poll pmp where pmp.message_id=pm.id and pmp.user_id=? ) state,v.user_name,pm.start_date from ps_message pm,ps_message_type pmt,VIEW_PS_USER V where PMT.TYPE_ID = PM.TYPE_ID and PM.PUBLISHER_ID = V.USER_ID and pm.type_id=101 order by start_date desc";
	/**
	 * 收件箱消息(仅系统更新)
	 */
	private static final String SQL_QUERY_MSG_REV_SYSTEM = "select pm.id,pm.title,pmt.type_desc,(select count(*) from ps_message_poll pmp where pmp.message_id=pm.id and pmp.user_id=? ) state,v.user_name,pm.start_date from ps_message pm,ps_message_type pmt,VIEW_PS_USER V where PMT.TYPE_ID = PM.TYPE_ID and PM.PUBLISHER_ID = V.USER_ID and pm.type_id=102 order by start_date desc";
	/**
	 * 收件箱消息(仅公告和数据更新不排序)
	 */
	private static final String SQL_QUERY_MSG_REV_BULLETIN_NO_ORDER = "select pm.id,pm.title,pmt.type_desc,(select count(*) from ps_message_poll pmp where pmp.message_id=pm.id and pmp.user_id=? ) state,v.user_name,pm.start_date from ps_message pm,ps_message_type pmt,VIEW_PS_USER V where PMT.TYPE_ID = PM.TYPE_ID and PM.PUBLISHER_ID = V.USER_ID and (pm.type_id=100 or pm.type_id=101 or pm.type_id=102)";
	/**
	 * 收件箱消息总数
	 */
	private static final String SQL_QUERY_MSG_REV_TOTAL = "select count(1) from PS_MWEEAGE PM, PS_MWEEAGE_POLL PMP, PS_MWEEAGE_TYPE PMT"
			+ " where PMP.MWEEAGE_ID = PM.ID and PM.TYPE_ID = PMT.TYPE_ID and PM.START_DATE <= sysdate ";

	/**
	 * 消息类型
	 */
	private static final String SQL_QUERY_MWEEAGE_TYPE = "select type_name ,type_desc,TYPE_ID from ps_message_type";

	/**
	 * 删除消息轮询表数据
	 */
	private static final String SQL_DELETE_MWEEAGE_POLL_BY_USER_ID_AND_MWEEAGE_ID = "delete PS_MWEEAGE_POLL where USER_ID=? and MWEEAGE_ID=?";

	/**
	 * 删除消息详细信息
	 */
	private static final String SQL_DELETE_PS_MWEEAGE_DETAIL_BY_ID = "delete PS_MWEEAGE_DETAIL where MWEEAGE_ID=?";

	/**
	 * 删除消息接收者表
	 */
	private static final String SQL_DELETE_PS_MWEEAGE_RECEIVER_BY_ID = "delete PS_MWEEAGE_RECEIVER where MWEEAGE_ID=?";

	/**
	 * 删除消息轮询表
	 */
	private static final String SQL_DELETE_MWEEAGE_POLL_BY_ID = "delete PS_MWEEAGE_POLL where MWEEAGE_ID=?";

	/**
	 * 消息消息主表
	 */
	private static final String SQL_DELETE_MWEEAGE_BY_ID = "delete PS_MWEEAGE where ID=?";

	/**
	 * 审批登录--用户
	 */
	private static final String SQL_QUERY_REV_22 = " select t.USER_ID id,t.USER_NAME name from ps_interface_user t where upper(USER_NAME) like ?  ORDER BY NAME ";

	/**
	 * 审批登录--部门
	 */
	private static final String SQL_QUERY_REV_21 = "  select piu.dept_id id,piu.dept_name name from ps_interface_user piu where upper(dept_name) like ?  group by piu.dept_id,piu.dept_name order by name ";

	/**
	 * 审批登录--角色
	 */
	private static final String SQL_QUERY_REV_20 = "  SELECT ROLE_ID id ,  ROLE_NAME name FROM WEE_AUTH_ROLE where ROLE_ID='easRole' and upper(ROLE_NAME) like ?";

	/**
	 * 直接登录--用户
	 */
	private static final String SQL_QUERY_REV_12 = "SELECT FULL_NAME name, USER_ID id FROM WEE_ORG_USER where upper(FULL_NAME) like ? order by name ";

	/**
	 * 直接登录--部门
	 */
	private static final String SQL_QUERY_REV_11 = "SELECT UNIT_ID id, UNIT_NAME name FROM WEE_ORG_UNIT where upper(UNIT_NAME) like ? ";

	/**
	 * 直接登录--角色
	 */
	private static final String SQL_QUERY_REV_10 = "SELECT ROLE_ID id , ROLE_NAME name FROM WEE_AUTH_ROLE where ROLE_ID <>'easRole' and upper(ROLE_NAME) like ?  ORDER BY ROLE_NAME";

	/**
	 * 收件人列表
	 */
	private static final String SQL_QUERY_MSG_REVS = "select t.MWEEAGE_ID,t.RECEIVER_ID,t.RECEIVER_TYPE from ps_message_receiver t where message_id=?";

	/**
	 * 直接登录-所有角色
	 */
	private static final String SQL_QUERY_ROLE_WEE = "select U.USER_ID id,U.FULL_NAME name from WEE_ORG_USER U, WEE_AUTH_M_USER_ROLE M, WEE_ORG_ACCOUNT A where U.USER_ID = A.USER_ID and A.ENABLED = '1' and U.USER_ID = M.USER_ID and M.ROLE_ID in (";

	/**
	 * 直接登录-所有机构
	 */
	private static final String SQL_QUERY_UNIT_WEE = "select U.USER_ID id,U.FULL_NAME name from WEE_ORG_USER U, WEE_ORG_M_USER_UNIT M, WEE_ORG_ACCOUNT A	 where U.USER_ID = A.USER_ID and U.USER_ID = M.USER_ID and M.UNIT_ID in(";

	/**
	 * 审批登录-所有角色
	 */
	private static final String SQL_QUERY_ROLE_EAS = "select t.USER_ID id,t.USER_NAME name from ps_interface_user t";

	/**
	 * 审批登录-所有机构
	 */
	private static final String SQL_QUERY_UNIT_EAS = "select  t.USER_ID id,t.USER_NAME name from ps_interface_user t   where t.dept_id in (";

	/**
	 * 更新消息标志
	 */
	private static final String SQL_UPDATE_MWEEAGE_STATE = "update PS_MWEEAGE pm set pm.FLAG=? where pm.id=?";

	/**
	 * 编辑消息
	 */
	private static final String SQL_QUERY_EDIT_MSG = "select PM.ID, PM.TITLE, PMT.TYPE_DESC,PMT.TYPE_NAME,PMT.TYPE_ID, PMD.CONTENT, PM.START_DATE"
			+ "  from PS_MWEEAGE PM, PS_MWEEAGE_DETAIL PMD, PS_MWEEAGE_TYPE PMT"
			+ " where PMD.MWEEAGE_ID = PM.ID and PMT.TYPE_ID = PM.TYPE_ID and PM.ID = ? and"
			+ "       PM.PUBLISHER_ID = ?";

	/**
	 * 消息接收者的详细信息
	 */
	private static final String SQL_QUERY_ALL_REVS = "select PMR.MWEEAGE_ID, PMR.RECEIVER_TYPE, PMR.RECEIVER_ID, EOU.FULL_NAME "
			+ "  from PS_MWEEAGE_RECEIVER PMR, WEE_ORG_USER EOU "
			+ " where PMR.RECEIVER_ID = EOU.USER_ID and PMR.RECEIVER_TYPE = 12  and pmr.MWEEAGE_ID=? "
			+ " union all  select PMR.MWEEAGE_ID, PMR.RECEIVER_TYPE, PMR.RECEIVER_ID, EOR.ROLE_NAME "
			+ "  from PS_MWEEAGE_RECEIVER PMR, WEE_AUTH_ROLE EOR "
			+ " where PMR.RECEIVER_ID = EOR.ROLE_ID and PMR.RECEIVER_TYPE = 10 and pmr.MWEEAGE_ID=? "
			+ " union all select PMR.MWEEAGE_ID, PMR.RECEIVER_TYPE, PMR.RECEIVER_ID, EUN.UNIT_NAME "
			+ "  from PS_MWEEAGE_RECEIVER PMR, WEE_ORG_UNIT EUN "
			+ " where PMR.RECEIVER_ID = EUN.UNIT_ID and PMR.RECEIVER_TYPE = 11 and pmr.MWEEAGE_ID=? "
			+ " union all select PMR.MWEEAGE_ID, PMR.RECEIVER_TYPE, PMR.RECEIVER_ID, PIU.USER_NAME "
			+ "  from PS_MWEEAGE_RECEIVER PMR, PS_INTERFACE_USER PIU "
			+ " where PMR.RECEIVER_ID = PIU.USER_ID and PMR.RECEIVER_TYPE = 22 and pmr.MWEEAGE_ID=? "
			+ " union all select PMR.MWEEAGE_ID, PMR.RECEIVER_TYPE, PMR.RECEIVER_ID, EOR.ROLE_NAME "
			+ "  from PS_MWEEAGE_RECEIVER PMR, WEE_AUTH_ROLE EOR "
			+ " where PMR.RECEIVER_ID = EOR.ROLE_ID and PMR.RECEIVER_TYPE = 20 and "
			+ " EOR.ROLE_ID = 'easRole' and pmr.MWEEAGE_ID=?  "
			+ " union all select PMR.MWEEAGE_ID, PMR.RECEIVER_TYPE, PMR.RECEIVER_ID, PIU.DEPT_NAME "
			+ "  from PS_MWEEAGE_RECEIVER PMR, PS_INTERFACE_USER PIU "
			+ " where PMR.RECEIVER_ID = PIU.DEPT_ID and PMR.RECEIVER_TYPE = 21 and pmr.MWEEAGE_ID=?";

	/**
	 * 默认的消息配置id
	 */
	private static final String DEFAULT = "DEFAULT";

	/**
	 * 删除所有的接收者
	 */
	private static final String SQL_DELETE_PS_MWEEAGE_RECEIVER_ALL = "delete PS_MWEEAGE_RECEIVER where message_id=?";

	/**
	 * 新建状态
	 */
	private static final int EDIT_MODE_ADD_0 = 0;

	/**
	 * 编辑状态
	 * 
	 * */
	private static final int EDIT_MODE_EDIT_1 = 1;

	/**
	 * 收件箱标志
	 */
	private static final int BOX_RECEIVE = 0;

	/**
	 * 发件箱标志
	 */
	private static final int BOX_SEND = 1;

	/**
	 *  消息接收者类型--(直接登录)组织结构
	 */
	private static final int RECEIVER_TYPE_UNIT_11 = 11;

	/**
	 *  消息接收者类型--(直接登录)角色
	 */
	private static final int RECEIVER_TYPE_ROLE_10 = 10;

	/**
	 * 消息接收者类型--(直接登录)用户
	 */
	private static final int RECEIVER_TYPE_USER_12 = 12;

	/**
	 *  消息接收者类型--(审批系统登录)机构
	 */
	private static final int RECEIVER_TYPE_UNIT_21 = 21;

	/**
	 *  消息接收者类型--(审批系统登录)角色
	 */
	private static final int RECEIVER_TYPE_ROLE_20 = 20;

	/**
	 * 消息接收者类型--(审批系统登录)用户
	 */
	private static final int RECEIVER_TYPE_USER_22 = 22;

	/**
	 * 插入消息
	 */
	private static final String SQL_INSERT_MWEEAGE = "insert into PS_MWEEAGE   (ID,flag, TYPE_ID,title,CALLBACK_PARAMS,PUBLISHER_ID,UPDATE_date, start_date) values   (?, ?, ?,? ,?,?,?,?)";

	/**
	 * 插入消息接收对象
	 */
	private static final String SQL_INSERT_MWEEAGE_RECEIVER = "insert into PS_MWEEAGE_RECEIVER   (RECEIVER_ID, RECEIVER_TYPE,MWEEAGE_ID) values   (?, ?, ?)";

	/**
	 * 删除接收者记录
	 */
	private static final String SQL_DELETE_MWEEAGE_RECEIVER = "delete PS_MWEEAGE_RECEIVER PMR where PMR.RECEIVER_ID = ? and PMR.MWEEAGE_ID = ?";

	/**
	 * 向轮询表中添加数据
	 */
	private static final String SQL_INSERT_MWEEAGE_POLL = "insert into PS_MWEEAGE_POLL   (MWEEAGE_ID,USER_ID, STATE) values   (?, ?, ?)";

	/**
	 * 删除轮询表的记录
	 */
	private static final String SQL_DELETE_MWEEAGE_POLL = "delete PS_MWEEAGE_POLL pmp  where pmp.MWEEAGE_ID=? and pmp.USER_ID=?";

	/**
	 * 插入消息详细内容
	 */
	private static final String SQL_INSERT_MWEEAGE_DETAIL = "insert into PS_MWEEAGE_DETAIL(MWEEAGE_ID, CONTENT) values   (?, ? )";

	/**
	 * 更新消息
	 */
	private static final String SQL_UPDATE_MWEEAGE = "update PS_MWEEAGE pm set pm.START_DATE=?,pm.UPDATE_DATE=?,pm.TITLE=?,pm.type_id=? where pm.ID=?";

	/**
	 *  更新消息详细
	 */
	private static final String SQL_UPDATE_MWEEAGE_DETAIL = "update PS_MWEEAGE_DETAIL pmd set pmd.CONTENT=? where pmd.MWEEAGE_ID=?";

	/**
	 * 获得消息配置消息
	 */
	private static final String SQL_QUERY_MWEEAGE_CONFIG = "select PMC.POLLING_TIME, PMC.KEEP_TIME, PMC.SHOW_NUMBER, PMC.START_POLL  from PS_MWEEAGE_CONFIG PMC where pmc.USER_ID=?";

	/**
	 * 更新消息配置
	 */
	private static final String SQL_UPDATE_MWEEAGE_CONFIG = "update PS_MWEEAGE_CONFIG   set POLLING_TIME = ?, KEEP_TIME = ?, SHOW_NUMBER = ?, START_POLL = ? ,POLLING_NUM=? where USER_ID = ? ";

	/**
	 * 添加个人消息配置
	 */
	private static final String SQL_INSERT_MWEEAGE_CONFIG = "insert into PS_MWEEAGE_CONFIG  (POLLING_TIME, KEEP_TIME, SHOW_NUMBER, START_POLL,POLLING_NUM,USER_ID) values(?, ?, ?, ?,?,?)";

	/**
	 * 更新消息状态为已读
	 */
	private static final String SQL_UPDATE_MWEEAGE_STATE_BY_ID = "update PS_MWEEAGE_poll set state= ? where user_id=? and MWEEAGE_ID = ?  ";
	
	/**
	 * 更新消息状态为已读-公告
	 */
	private static final String SQL_INSERT_MWEEAGE_STATE_BY_ID = "insert into PS_MWEEAGE_poll (message_id,user_id,state) values (?,?,?)";

	
	/**
	 * 获得欢迎页面消息 （不包含公告和数据更新和系统更新）
	 */
	private static final String SQL_QUERY_INDEX_MWEEAGES = "select * from (select id,state,title,type_id,flag,start_date, rank() over(partition by type_id order by start_date desc, id) rk from (select message_id as id, state, title,type_id,flag,start_date from ps_message_poll p,ps_message m where p.message_id = m.id and user_id = ? and type_id<>100 and type_id<>101 and type_id<>102 and m.start_date<=sysdate)) where rk <= 5";
	/**
	 * 获得欢迎页面消息 （仅公告）
	 */
	private static final String SQL_QUERY_INDEX_MWEEAGES_BULLETIN = "select * from(select id,(select count(*) from ps_message_poll where message_id=id and user_id=? ) state,title,type_id,flag,start_date from ps_message where type_id =100 and start_date<=sysdate order by start_date desc) where rownum<=5";
	/**
	 * 获得欢迎页面消息 （仅数据更新）
	 */
	private static final String SQL_QUERY_INDEX_MWEEAGES_EMAIL = "select * from(select id,(select count(*) from ps_message_poll where message_id=id and user_id=? ) state,title,type_id,flag,start_date from ps_message where type_id =101 and start_date<=sysdate order by start_date desc) where rownum<=5";
	/**
	 * 获得欢迎页面消息 （仅系统更新）
	 */
	private static final String SQL_QUERY_INDEX_MWEEAGES_SYSTEM = "select * from(select id,(select count(*) from ps_message_poll where message_id=id and user_id=? ) state,title,type_id,flag,start_date from ps_message where type_id =102 and start_date<=sysdate order by start_date desc) where rownum<=5";
	/**
	 * 根据消息ID获取消息类型
	 */
	private static final String SQL_QUERY_MWEEAGE_TYPE_BY_MWEEAGE_ID = "select type_id from ps_message where id=?";
	/**
	 * <p>查询所有已经发送的消息</p>
	 * 
	 * @see cn.ursun.platform.dao.MessageDAO#querySendMsgs(cn.ursun.platform.domain.Message, cn.ursun.platform.core.dto.Pagination)
	 * @author: 开发人员姓名 付斌 - fu.bin@neusoft.com 
	 * @date: Created on Apr 16, 2009 12:16:38 PM
	 */
	public List<Message> querySendMsgs(Message message, Pagination pagination) throws BizException {
		Map map = createSendQuery(message);
		Object[] paras = (Object[]) map.get("params");
		List<Message> messages = query(SQL_QUERY_MSG_SEND + map.get("sql") + "  order by PM.START_DATE desc", paras,
				new MessageSendRowMapper(), pagination);
		return messages;
	}

	/**
	 * <p>查询已经发送消息总数</p>
	 * 
	 * @see cn.ursun.platform.dao.MessageDAO#querySendMsgTotal(cn.ursun.platform.domain.Message)
	 * @author: 开发人员姓名 付斌 - fu.bin@neusoft.com 
	 * @date: Created on Apr 16, 2009 12:16:38 PM
	 */
	public int querySendMsgTotal(Message message) throws BizException {
		Map map = createSendQuery(message);
		Object[] paras = (Object[]) map.get("params");
		return queryForInt(SQL_QUERY_MSG_SEND_TOTA + map.get("sql"), paras);
	}

	/**
	 * 
	 * <p>[获取欢迎页面消息]</p>
	 * 
	 * @see cn.ursun.platform.dao.MessageDAO#queryMessageIndex(cn.ursun.platform.domain.MessageConfig)
	 * @author: 开发人员姓名 付斌 - fu.bin@neusoft.com 
	 * @date: Created on Apr 13, 2009 11:37:22 AM
	 */
	public List<Message> queryMessageIndex(MessageConfig msgConfig) throws BizException {
		List<Message> messages = query(SQL_QUERY_INDEX_MWEEAGES, new Object[] { msgConfig.getUserId() },
				new MessageIndexRowMapper());
		List<Message> messagesBulletin = query(SQL_QUERY_INDEX_MWEEAGES_BULLETIN, new Object[] { msgConfig.getUserId() },
				new MessageIndexRowMapper());
		List<Message> messagesEmail = query(SQL_QUERY_INDEX_MWEEAGES_EMAIL, new Object[] { msgConfig.getUserId() },
				new MessageIndexRowMapper());
		List<Message> messagesSystem = query(SQL_QUERY_INDEX_MWEEAGES_SYSTEM, new Object[] { msgConfig.getUserId() },
				new MessageIndexRowMapper());
		messages.addAll(messagesBulletin);
		messages.addAll(messagesEmail);
		messages.addAll(messagesSystem);
		return messages;
	}

	/**
	 * <p>添加单条消息</p>
	 * 
	 * @see cn.ursun.platform.dao.MessageDAO#insertMessage(cn.ursun.platform.domain.Message)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Mar 31, 2009 9:38:54 AM
	 */
	public void insertMessage(Message m) throws BizException {
		String id = m.getId();
		Object[] insertParas = new Object[] { id, m.getFlag(), m.getTypeId(), m.getTitle(), m.getCallbackParams(),
				m.getPublisherId(), m.getUpdateDate(), m.getStartDate() };
		this.update(SQL_INSERT_MWEEAGE, insertParas);
		this.update(SQL_INSERT_MWEEAGE_RECEIVER, new Object[] { m.getReceiverId(), m.getReceiverType(), id });
		if (m.getFlag() == 2) {
			this.update(SQL_INSERT_MWEEAGE_POLL, new Object[] { id, m.getReceiverId(), 0 });
		}
		this.update(SQL_INSERT_MWEEAGE_DETAIL, new Object[] { id, m.getContent() });

	}

	/**
	 * <p>当前用户待提醒的消息总数</p>
	 * 
	 * @param user
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 1, 2009 12:47:44 PM
	 */
	public int queryInActiveTotal(String userId) {
		return queryForInt(SQL_QUERY_MEG_INACTIVE_TOTAL, new Object[] { userId });
	}

	/**
	 * <p>查询消息</p>
	 * 
	 * @see cn.ursun.platform.dao.MessageDAO#queryMessageInActive(cn.ursun.platform.domain.MessageConfig)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 7, 2009 5:24:31 PM
	 */
	public List<Message> queryMessageInActive(MessageConfig msgConfig) throws BizException {
		Pagination pagination = new Pagination();
		pagination.setStart(0);
		pagination.setLimit(msgConfig.getShowMsgNum());
		List<Message> messages = query(SQL_QUERY_MEG_INACTIVE, new Object[] { msgConfig.getUserId() },
				new MessageInActiveRowMapper(), pagination);
		return messages;
	}

	/**
	 * <p>读取参数配置</p>
	 * 
	 * @see cn.ursun.platform.dao.MessageDAO#readMsgConfig()
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 2, 2009 10:38:35 AM
	 */
	public MessageConfig readMsgConfig(String userId) throws BizException {
		MessageConfig cfg = (MessageConfig) queryForObject(SQL_QUERY_MWEEAGE_CONFIG, new Object[] { userId },
				new MessageConfigMapper());
		if (cfg == null) {
			cfg = (MessageConfig) queryForObject(SQL_QUERY_MWEEAGE_CONFIG, new Object[] { DEFAULT },
					new MessageConfigMapper());
		}
		return cfg;
	}

	/**
	 * <p>保存消息配置</p>
	 * 
	 * @see cn.ursun.platform.dao.MessageDAO#saveConfig(cn.ursun.platform.domain.MessageConfig)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 2, 2009 2:06:24 PM
	 */
	public void saveConfig(MessageConfig msgConfig) throws BizException {
		MessageConfig cfg = (MessageConfig) queryForObject(SQL_QUERY_MWEEAGE_CONFIG, new Object[] { msgConfig
				.getUserId() }, new MessageConfigMapper());
		Object[] params = new Object[] { msgConfig.getPollingTime(), msgConfig.getKeepTime(),
				msgConfig.getShowMsgNum(), msgConfig.getStartPoll(), msgConfig.getPollingNum(), msgConfig.getUserId() };
		if (cfg == null) {
			update(SQL_INSERT_MWEEAGE_CONFIG, params);
		} else {
			update(SQL_UPDATE_MWEEAGE_CONFIG, params);
		}
	}

	/**
	 * <p>获得消息详细</p>
	 * 
	 * @see cn.ursun.platform.dao.MessageDAO#queryMessageDetail(java.lang.String)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 7, 2009 8:08:01 PM
	 */
	public Message queryMessageDetail(String messageId, int isRead, int boxFlag) throws BizException {
		Message message = null;
		String userId = WeeSecurityInfo.getInstance().getUserId();
		if (boxFlag == BOX_RECEIVE) {
			int typeID = queryForInt(SQL_QUERY_MWEEAGE_TYPE_BY_MWEEAGE_ID,new Object[] { messageId});
			if(typeID==100 || typeID==101 || typeID==102){
				message = (Message) queryForObject(SQL_QUERY_MSG_REV_DETAIL_BULLETIN, new Object[] { messageId},
						new MessageRevDetailRowMapper());
				if (isRead == 1) {
					update(SQL_INSERT_MWEEAGE_STATE_BY_ID, new Object[] { messageId, userId, 1});
				}
			}else{
				message = (Message) queryForObject(SQL_QUERY_MSG_REV_DETAIL, new Object[] { messageId, userId },
						new MessageRevDetailRowMapper());
				if (isRead == 1) {
					update(SQL_UPDATE_MWEEAGE_STATE_BY_ID, new Object[] { 1, userId, messageId });
				}
			}
			
		} else if (boxFlag == BOX_SEND) {

			message = (Message) queryForObject(SQL_QUERY_MSG_SEND_DETAIL, new Object[] { messageId, userId },
					new MessageSendDetailRowMapper());
			List l = queryMsgReceiver(messageId);
			StringBuffer sb = new StringBuffer();
			for (Iterator iterator = l.iterator(); iterator.hasNext();) {
				Code object = (Code) iterator.next();
				sb.append(object.getTypeName()).append("<br/>");
			}
			
			message.setReceiverId(sb.length()==0?"":sb.substring(0, sb.length() - 1).toString());

		}
		return message;
	}

	/**
	 * <p>更新消息状态</p>
	 * 
	 * @see cn.ursun.platform.dao.MessageDAO#updateMsgState(cn.ursun.platform.domain.Message)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 8, 2009 10:28:50 AM
	 */
	public int updateMsgState(Message message) throws BizException {
		String userId = WeeSecurityInfo.getInstance().getUserId();
		int updates = 0;
		int typeID;
		List<Object[]> params = new ArrayList<Object[]>();
		if (message.getId() != null) {// 批量更新
			String[] msgIds = message.getId().split(",");
			for (int i = 0; i < msgIds.length; i++) {
				typeID = queryForInt(SQL_QUERY_MWEEAGE_TYPE_BY_MWEEAGE_ID,new Object[] { msgIds[i].trim()});
				if(typeID==100 || typeID==101 || typeID==102){
					if(message.getState()==1){
						//置为已读
						update(SQL_INSERT_MWEEAGE_POLL, new Object[] { msgIds[i].trim(),userId,0});
					}else{
						//置为未读
						update("delete from ps_message_poll where message_id =　?", new Object[] { msgIds[i].trim()});
					}
					
				}else{
					Object[] para = new Object[] { message.getState(), userId, msgIds[i].trim() };
					params.add(para);
				}
				
				
			}
			this.batchUpdate(SQL_UPDATE_MWEEAGE_STATE_BY_ID, params);
		} else {// 在弹出窗口中单击“有新消息再提醒”更新状态
			String sql = SQL_UPDATE_MEG_STATE;
			updates = update(sql, new Object[] { userId, message.getPublishDate(), userId });
		}
		return updates;
	}

	/**
	 * <p>获得收件箱消息</p>
	 * 
	 * @see cn.ursun.platform.dao.MessageDAO#queryAllRevMsgs(cn.ursun.platform.domain.Message, cn.ursun.platform.core.dto.Pagination)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 9, 2009 12:07:21 PM
	 */
	public List<Message> queryAllRevMsgs(Message message, Pagination pagination) throws BizException {
		String userId = WeeSecurityInfo.getInstance().getUserId();
		Map map = createRevQuery(message);
		Object[] paras = (Object[]) map.get("params");
		List<Message> messages=new ArrayList<Message>();
		if("BULLETIN".equals(message.getType())){
			//公告更多
			messages = query(SQL_QUERY_MSG_REV_BULLETIN, new Object[]{userId},
					new MessageRowMapper(), pagination);
		}else if("EMAIL".equals(message.getType())){
			//数据更新更多
			messages = query(SQL_QUERY_MSG_REV_EMAIL, new Object[]{userId},
					new MessageRowMapper(), pagination);
		}else if("SYSTEM".equals(message.getType())){
			//系统更新更多
			messages = query(SQL_QUERY_MSG_REV_SYSTEM, new Object[]{userId},
					new MessageRowMapper(), pagination);
		}else if(message.getType()!=null){
			//其他更多
			messages = query(SQL_QUERY_MSG_REV + map.get("sql") + " and PMT.TYPE_ID<>100 and PMT.TYPE_ID<>101 and PMT.TYPE_ID<>102 order by PM.START_DATE DESC", paras,
					new MessageRowMapper(), pagination);
		}else{
			//收件箱全部
			Object[] parasCopy = new Object[paras.length+1];
			System.arraycopy(paras, 0, parasCopy, 1, paras.length);
			parasCopy[0]=userId;
			messages = query("("+SQL_QUERY_MSG_REV_BULLETIN_NO_ORDER+") union all ("+SQL_QUERY_MSG_REV + map.get("sql") + " and PMT.TYPE_ID<>100 and PMT.TYPE_ID<>101 and PMT.TYPE_ID<>102) order by START_DATE DESC", parasCopy,
					new MessageRowMapper(), pagination);
		}
		return messages;
	}

	/**
	 * <p>获得收件箱消息总数</p>
	 * 
	 * @see cn.ursun.platform.dao.MessageDAO#queryMsgRevTotal(cn.ursun.platform.domain.Message)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 9, 2009 12:07:21 PM
	 */
	public int queryMsgRevTotal(Message message) throws BizException {
		Map map = createRevQuery(message);
		String querySql = SQL_QUERY_MSG_REV_TOTAL + map.get("sql");
		Object[] paras = (Object[]) map.get("params");
		return queryForInt(querySql, paras);
	}

	/**
	 * <p>获得类型</p>
	 * 
	 * @see cn.ursun.platform.dao.MessageDAO#queryMsgType()
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 9, 2009 1:53:52 PM
	 */
	public List<Code> queryMsgType(String msgType) throws BizException {
		String sql = SQL_QUERY_MWEEAGE_TYPE;
		if (msgType != null && !"-1".equals(msgType)) {
			sql += " where substr(TYPE_ID,0,1 )=" + msgType;
		}
		List<Code> codeList = this.query(sql + " order by type_id asc", new CodeRowMaper());
		return codeList;
	}

	/**
	 * <p>获得当前时间</p>
	 * 
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on May 8, 2009 11:53:12 AM
	 */
	private Date currDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * <p>构造收件箱的查询语句</p>
	 * 
	 * @param message
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on May 8, 2009 11:53:21 AM
	 */
	private Map<String, Object> createRevQuery(Message message) {
		StringBuffer sqlSB = new StringBuffer();
		Date startDate = message.getStartDate();
		Date endDate = message.getEndDate();
		String content = message.getTitle();
		String type = message.getType();
		int state = message.getState();
		if (state == 3) {
			sqlSB.append(" and PMP.USER_ID=?");
		} else {
			sqlSB.append(" and PMP.USER_ID=? and PMP.STATE=? ");
		}
		List<Object> list = new ArrayList<Object>();
		// sqlSB.append(" where receiver=? and state=? ");
		if (content != null && content.trim().length() > 0) {
			list.add("%".concat(content.trim()).concat("%"));
			sqlSB.append(" and PM.TITLE  like ? ");
		}
		if (type != null && type.trim().length() > 0) {
			list.add(type);
			sqlSB.append(" and PMT.TYPE_NAME=? ");
		}

		if (startDate != null) {
			list.add(startDate);
			sqlSB.append(" and PM.START_DATE >=? ");
		}
		if (endDate != null) {
			list.add(endDate);
			sqlSB.append(" and PM.START_DATE <=? ");
		}
		Object[] paras = null;
		int startIndex = 0;
		if (state == 3) {
			paras = new Object[list.size() + 1];
			paras[0] = message.getReceiverId();
			startIndex = 1;
		} else {
			paras = new Object[list.size() + 2];
			paras[0] = message.getReceiverId();
			paras[1] = message.getState();
			startIndex = 2;
		}
		Iterator<Object> it = list.iterator();
		for (int i = startIndex; i < paras.length; i++) {
			if (it.hasNext()) {
				paras[i] = it.next();
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sql", sqlSB.toString());
		map.put("params", paras);
		return map;
	}

	/**
	 * <p>构造发件箱的查询语句</p>
	 * 
	 * @param message
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on May 8, 2009 11:53:55 AM
	 */
	private Map<String, Object> createSendQuery(Message message) {
		StringBuffer sqlSB = new StringBuffer();
		Date startDate = message.getStartDate();
		Date endDate = message.getEndDate();
		String content = message.getTitle();
		String type = message.getType();
		int state = message.getState();
		if (state == 3) {
			sqlSB.append(" and PM.PUBLISHER_ID=?");
		} else {
			sqlSB.append(" and PM.PUBLISHER_ID=? and PM.FLAG=? ");
		}
		List<Object> list = new ArrayList<Object>();
		if (content != null && content.trim().length() > 0) {
			list.add("%".concat(content.trim()).concat("%"));
			sqlSB.append(" and PM.TITLE  like ? ");
		}
		if (type != null && type.trim().length() > 0) {
			list.add(type);
			sqlSB.append(" and PMT.TYPE_NAME=? ");
		}

		if (startDate != null) {
			list.add(startDate);
			sqlSB.append(" and PM.START_DATE >=? ");
		}
		if (endDate != null) {
			list.add(endDate);
			sqlSB.append(" and PM.START_DATE <=? ");
		}

		Object[] paras = null;
		int startIndex = 1;
		if (state == 3) {
			paras = new Object[list.size() + 1];
			paras[0] = message.getReceiverId();
			startIndex = 1;
		} else {
			paras = new Object[list.size() + 2];
			paras[0] = message.getReceiverId();
			paras[1] = message.getState();
			startIndex = 2;
		}

		// paras = new Object[list.size() + startIndex];
		// paras[0] = message.getReceiverId();

		Iterator<Object> it = list.iterator();
		for (int i = startIndex; i < paras.length; i++) {
			if (it.hasNext()) {
				paras[i] = it.next();
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sql", sqlSB.toString());
		map.put("params", paras);
		return map;
	}

	/**
	 * <p>删除消息</p>
	 * 
	 * @see cn.ursun.platform.dao.MessageDAO#deleteMsgs(java.lang.String)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 9, 2009 7:58:50 PM
	 */
	public int deleteMsgs(String msgIds, int boxId) throws BizException {
		List<Object[]> params0 = new ArrayList<Object[]>();
		List<Object[]> params1 = new ArrayList<Object[]>();
		String[] msgId = msgIds.split(",");
		
		for (int i = 0; i < msgId.length; i++) {
			params0.add(new Object[] { WeeSecurityInfo.getInstance().getUserId(), msgId[i] });
			params1.add(new Object[] { msgId[i] });
			int typeID = queryForInt(SQL_QUERY_MWEEAGE_TYPE_BY_MWEEAGE_ID,new Object[] { msgId[i]});
			if((typeID==100 || typeID==101 || typeID==102) && boxId == 0){
				return -1;
			}
		}
		if (boxId == 0) {
			String sqlDel = SQL_DELETE_MWEEAGE_POLL_BY_USER_ID_AND_MWEEAGE_ID;
			this.batchUpdate(sqlDel, params0);
		} else {
			String sqlDelMessage = SQL_DELETE_MWEEAGE_BY_ID;
			String sqlDelPoll = SQL_DELETE_MWEEAGE_POLL_BY_ID;
			String sqlDelReceiver = SQL_DELETE_PS_MWEEAGE_RECEIVER_BY_ID;
			String sqlDelDetail = SQL_DELETE_PS_MWEEAGE_DETAIL_BY_ID;
			this.batchUpdate(sqlDelPoll, params1);
			this.batchUpdate(sqlDelReceiver, params1);
			this.batchUpdate(sqlDelDetail, params1);
			this.batchUpdate(sqlDelMessage, params1);
		}
		
		return 0;
	}

	/**
	 * <p>获得接收者详细信息</p>
	 * 
	 * @see cn.ursun.platform.dao.MessageDAO#queryAllReceiver(int)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 14, 2009 9:34:50 PM
	 */
	public List<Code> queryAllReceiver(int receiverType, String msgType) {
		// data : [['0', '角色'], ['1', '机构'], ['2', '用户']]
		String sql = null;
		if (receiverType == RECEIVER_TYPE_ROLE_10) {
			sql = SQL_QUERY_REV_10;
		} else if (receiverType == RECEIVER_TYPE_UNIT_11) {
			sql = SQL_QUERY_REV_11;
		} else if (receiverType == RECEIVER_TYPE_USER_12) {
			sql = SQL_QUERY_REV_12;
		} else if (receiverType == RECEIVER_TYPE_ROLE_20) {// 就一个角色
			sql = SQL_QUERY_REV_20;
		} else if (receiverType == RECEIVER_TYPE_UNIT_21) {//
			sql = SQL_QUERY_REV_21;
		} else if (receiverType == RECEIVER_TYPE_USER_22) {
			sql = SQL_QUERY_REV_22;
		}
		String query = "%".concat(msgType.toUpperCase()).concat("%");
		List<Code> codeList = this.query(sql, new Object[] { query }, new ReceriverRowMaper(receiverType));
		return codeList;
	}

	/**
	 * <p>向表ps_messgage_receriver插入记录</p>
	 * 
	 * @param lastRevId
	 * @param msgId
	 * @param editMode
	 * @param revType
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 17, 2009 6:31:51 PM
	 */
	private void insertToMsgReceiverTab(String lastRevId, String msgId, int editMode, int revType) {
		List<Object[]> params = new ArrayList<Object[]>();
		String[] revds = null;
		String sql = null;
		revds = lastRevId.split(",");

		Object[] object = null;
		// 如果是编辑状态，先删除记录
		if (editMode == EDIT_MODE_EDIT_1) {
			for (int i = 0, c = revds.length; i < c; i++) {
				if (!"".equals(revds[i].trim())) {
					object = new Object[] { revds[i].trim(), msgId };
					params.add(object);
				}
			}
			sql = SQL_DELETE_MWEEAGE_RECEIVER;
			batchUpdate(sql, params);
		}
		params.clear();
		for (int i = 0, c = revds.length; i < c; i++) {
			if (!"".equals(revds[i].trim())) {
				object = new Object[] { revds[i].trim(), revType, msgId };
				params.add(object);
			}
		}
		sql = SQL_INSERT_MWEEAGE_RECEIVER;
		batchUpdate(sql, params);
	}

	/**
	 * <p>向表ps_message_poll插入记录</p>
	 * 
	 * @param userIdSet
	 * @param msgId
	 * @param editMode
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 17, 2009 6:31:10 PM
	 */
	private void insertToMsgPollTab(Set<String> userIdSet, String msgId, int editMode) {
		List<Object[]> params = new ArrayList<Object[]>();
		Object[] object = null;
		String sql = null;
		// 如果是编辑状态，先删除记录
		if (editMode == EDIT_MODE_EDIT_1) {
			for (Iterator iterator = userIdSet.iterator(); iterator.hasNext();) {
				String userId = (String) iterator.next();
				object = new Object[] { msgId, userId };
				params.add(object);
			}
			sql = SQL_DELETE_MWEEAGE_POLL;
			batchUpdate(sql, params);
		}
		params.clear();
		for (Iterator iterator = userIdSet.iterator(); iterator.hasNext();) {
			String userId = (String) iterator.next();
			object = new Object[] { msgId, userId, 0 };
			params.add(object);
		}
		sql = SQL_INSERT_MWEEAGE_POLL;
		batchUpdate(sql, params);
	}

	/**
	 * <p>保存消息</p>
	 * 
	 * @see cn.ursun.platform.dao.MessageDAO#saveMessage(cn.ursun.platform.domain.Message)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 15, 2009 4:38:31 PM
	 */
	public int saveMessage(Message m) {
		// 解析receiverId 规则：type_id
		String[] ids = m.getReceiverId().split(",");
		int revType;
		String revId = null;
		String sqlInsert = null;
		String sqlUpdate = null;
		Object[] paramsInsert = null;
		Object[] paramsUpdate = null;

		m.setFlag(0);
		String msgId = m.getId();
		int editMode = m.getEditMode();
		// 1插入ps_message
		// 如果是新建
		if (editMode == EDIT_MODE_ADD_0) {
			sqlInsert = SQL_INSERT_MWEEAGE;
			paramsInsert = new Object[] { msgId, m.getFlag(), m.getTypeId(), m.getTitle(), m.getCallbackParams(),
					m.getPublisherId(), currDate(), m.getStartDate() };
			this.update(sqlInsert, paramsInsert);
			sqlInsert = SQL_INSERT_MWEEAGE_DETAIL;
			paramsInsert = new Object[] { msgId, m.getContent() };
			this.update(sqlInsert, paramsInsert);
		} else if (editMode == EDIT_MODE_EDIT_1) {// 如果是修改状态
			// update PS_MWEEAGE pm set pm.START_DATE=?,pm.UPDATE_DATE=?,pm.TITLE=? where pm.ID=?
			sqlUpdate = SQL_UPDATE_MWEEAGE;
			paramsUpdate = new Object[] { m.getStartDate(), currDate(), m.getTitle(), m.getTypeId(), msgId };
			this.update(sqlUpdate, paramsUpdate);
			sqlUpdate = SQL_UPDATE_MWEEAGE_DETAIL;
			paramsUpdate = new Object[] { m.getContent(), msgId };
			this.update(sqlUpdate, paramsUpdate);
		}
		// 2.0 先清除 ps_message_receiver中的数据
		if(!"100".equals(m.getTypeId()) && !"101".equals(m.getTypeId()) && !"102".equals(m.getTypeId())){
			update(SQL_DELETE_PS_MWEEAGE_RECEIVER_ALL, new Object[] { msgId });

			String lastRoleIdInsert = null;
			String lastUnitIdInsert = null;
			String lastUserId = null;
			StringBuffer sbRoleInsert = new StringBuffer("");
			StringBuffer sbUnitInsert = new StringBuffer("");
			StringBuffer sbUserInsert = new StringBuffer("");
			StringBuffer sbRoleInsertEAS = new StringBuffer("");
			StringBuffer sbUnitInsertEAS = new StringBuffer("");
			StringBuffer sbUserInsertEAS = new StringBuffer("");
			for (int i = 0, c = ids.length; i < c; i++) {
				revType = Integer.parseInt(ids[i].substring(0, 2));
				revId = ids[i].substring(3);
				if (revType == RECEIVER_TYPE_ROLE_10) {
					sbRoleInsert.append(revId).append(",");
				} else if (revType == RECEIVER_TYPE_UNIT_11) {
					sbUnitInsert.append(revId).append(",");
				} else if (revType == RECEIVER_TYPE_USER_12) {
					sbUserInsert.append(revId).append(",");
				} else if (revType == RECEIVER_TYPE_ROLE_20) {
					sbRoleInsertEAS.append(revId).append(",");
				} else if (revType == RECEIVER_TYPE_UNIT_21) {
					sbUnitInsertEAS.append(revId).append(",");
				} else if (revType == RECEIVER_TYPE_USER_22) {
					sbUserInsertEAS.append(revId).append(",");
				}
			}
			// 直接登录数据插入
			// 2.1 插入ps_message_receiver--role
			if (sbRoleInsert.length() > 2) {
				lastRoleIdInsert = sbRoleInsert.toString().substring(0, sbRoleInsert.length() - 1);
				insertToMsgReceiverTab(lastRoleIdInsert, msgId, editMode, RECEIVER_TYPE_ROLE_10);
			}
			// 2.2 插入ps_message_receiver--unit
			if (sbUnitInsert.length() > 2) {
				lastUnitIdInsert = sbUnitInsert.toString().substring(0, sbUnitInsert.length() - 1);
				insertToMsgReceiverTab(lastUnitIdInsert, msgId, editMode, RECEIVER_TYPE_UNIT_11);
			}
			// 2.3 插入ps_message_receiver--user
			if (sbUserInsert.length() > 2) {
				lastUserId = sbUserInsert.toString().substring(0, sbUserInsert.length() - 1);
				insertToMsgReceiverTab(lastUserId, msgId, editMode, RECEIVER_TYPE_USER_12);
			}
			// 审批系统登录数据插入
			// 2.4 插入ps_message_receiver--role
			if (sbRoleInsertEAS.length() > 2) {
				lastRoleIdInsert = sbRoleInsertEAS.toString().substring(0, sbRoleInsertEAS.length() - 1);
				insertToMsgReceiverTab(lastRoleIdInsert, msgId, editMode, RECEIVER_TYPE_ROLE_20);
			}
			// 2.5 插入ps_message_receiver--unit
			if (sbUnitInsertEAS.length() > 2) {
				lastUnitIdInsert = sbUnitInsertEAS.toString().substring(0, sbUnitInsertEAS.length() - 1);
				insertToMsgReceiverTab(lastUnitIdInsert, msgId, editMode, RECEIVER_TYPE_UNIT_21);
			}
			// 2.6 插入ps_message_receiver--user
			if (sbUserInsertEAS.length() > 2) {
				lastUserId = sbUserInsertEAS.toString().substring(0, sbUserInsertEAS.length() - 1);
				insertToMsgReceiverTab(lastUserId, msgId, editMode, RECEIVER_TYPE_USER_22);
			}
		}


		return 0;
	}

	/**
	 * <p>发布消息</p>
	 * 
	 * @see cn.ursun.platform.dao.MessageDAO#pulbishMessage(cn.ursun.platform.domain.Message)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 15, 2009 4:38:31 PM
	 */
	public int pulbishMessage(Message m) {
		String lastRoleId = null;
		String lastUnitId = null;
		String revId;
		int revType;
		List<User> userList;
		StringBuffer sbRoleQuery = new StringBuffer("'");
		StringBuffer sbUnitQuery = new StringBuffer("'");
		Set<String> userIdSet = new HashSet<String>();
		StringBuffer sbRoleQueryEAS = new StringBuffer("'");
		StringBuffer sbUnitQueryEAS = new StringBuffer("'");
		Date stDate = m.getStartDate();
		Date currDate = new Date();
		if(m.getImmediately()==0 && currDate.after(stDate)){
			return -1;
		}else{
			m.setStartDate(currDate);
		}
		if(!"100".equals(m.getTypeId()) || !"101".equals(m.getTypeId()) || !"102".equals(m.getTypeId())){
			// 1 根据message的id查询ps_message_receiver,获得所有接收者
			List<Message> revList = this.query(SQL_QUERY_MSG_REVS, new Object[] { m.getId() }, new ReceriverPubRowMaper());
			for (Iterator iterator = revList.iterator(); iterator.hasNext();) {
				Message message = (Message) iterator.next();
				revId = message.getReceiverId();
				revType = message.getReceiverType();
				if (revType == RECEIVER_TYPE_ROLE_10) {
					sbRoleQuery.append(revId).append("','");
				} else if (revType == RECEIVER_TYPE_UNIT_11) {
					sbUnitQuery.append(revId).append("','");
				} else if (revType == RECEIVER_TYPE_USER_12) {
					userIdSet.add(revId);
				} else if (revType == RECEIVER_TYPE_ROLE_20) {
					sbRoleQueryEAS.append(revId).append("','");
				} else if (revType == RECEIVER_TYPE_UNIT_21) {
					sbUnitQueryEAS.append(revId).append("','");
				} else if (revType == RECEIVER_TYPE_USER_22) {
					userIdSet.add(revId);
				}
	
			}
			// 直接登录信息处理
			// 2.1 获得所有角色下的所有用户
			if (sbRoleQuery.length() > 2) {
				lastRoleId = sbRoleQuery.toString().substring(0, sbRoleQuery.length() - 2);
				userList = this.query(SQL_QUERY_ROLE_WEE + lastRoleId + ")", new UserRowMaper());
				for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
					User user = (User) iterator.next();
					userIdSet.add(user.getId());
	
				}
			}
			// 2.1 获得所有机构下的所有用户
			if (sbUnitQuery.length() > 2) {
				lastUnitId = sbUnitQuery.toString().substring(0, sbUnitQuery.length() - 2);
				userList = this.query(SQL_QUERY_UNIT_WEE + lastUnitId + ")", new UserRowMaper());
				for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
					User user = (User) iterator.next();
					userIdSet.add(user.getId());
	
				}
			}
			// 审批登录信息处理
			// 2.1 获得所有角色下的所有用户
			if (sbRoleQueryEAS.length() > 2) {
				lastRoleId = sbRoleQueryEAS.toString().substring(0, sbRoleQueryEAS.length() - 2);
				userList = this.query(SQL_QUERY_ROLE_EAS, new UserRowMaper());
				for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
					User user = (User) iterator.next();
					userIdSet.add(user.getId());
	
				}
			}
			// 2.1 获得所有机构下的所有用户
			if (sbUnitQueryEAS.length() > 2) {
				lastUnitId = sbUnitQueryEAS.toString().substring(0, sbUnitQueryEAS.length() - 2);
				userList = this.query(SQL_QUERY_UNIT_EAS + lastUnitId + ")", new UserRowMaper());
				for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
					User user = (User) iterator.next();
					userIdSet.add(user.getId());
	
				}
			}
		
			String msgId = m.getId();
		// 2 插入 ps_message_poll
			insertToMsgPollTab(userIdSet, msgId, m.getEditMode());
		}
		
		// 1 更新 ps_message的发布标志
		this.update(SQL_UPDATE_MWEEAGE_STATE, new Object[] { 1, m.getId() });
		return userIdSet.size();

	}

	/**
	 * <p>获得要编辑的消息</p>
	 * 
	 * @see cn.ursun.platform.dao.MessageDAO#queryEditSendMsg(java.lang.String)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 17, 2009 5:42:57 PM
	 */
	public Message queryEditSendMsg(String msgId) {
		Message message = null;
		String userId = WeeSecurityInfo.getInstance().getUserId();
		message = (Message) queryForObject(SQL_QUERY_EDIT_MSG, new Object[] { msgId, userId },
				new MessageSendDetailRowMapper());
		return message;
	}

	/**
	 * <p>获得信息的接收者列表</p>
	 * 
	 * @see cn.ursun.platform.dao.MessageDAO#queryMsgReceiver(java.lang.String)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 17, 2009 5:42:57 PM
	 */
	public List<Code> queryMsgReceiver(String msgId) {
		List<Code> revList = this.query(SQL_QUERY_ALL_REVS, new Object[] { msgId, msgId, msgId, msgId, msgId, msgId },
				new ReceriverSeldRowMaper());
		return revList;
	}

	static class RoleRowMaper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Role role = new Role();
			role.setId(rs.getString("id"));
			role.setDescription(rs.getString("DESCRIPTION"));
			role.setRoleName(rs.getString("name"));
			return role;
		}
	}

	static class UserRowMaper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setFullName(rs.getString("name"));
			return user;
		}
	}

	static class CodeRowMaper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Code code = new Code();
			code.setCode(rs.getString("type_name"));
			code.setCodeName(rs.getString("type_desc"));
			code.setType(rs.getString("TYPE_ID"));
			return code;
		}
	}

	static class ReceriverSeldRowMaper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			// t.MWEEAGE_ID,t.RECEIVER_ID,t.FULL_NAME ,t.RECEIVER_TYPE
			Code code = new Code();
			int receiverType = rs.getInt("RECEIVER_TYPE");
			String receiverTypeDesc = transferRevType(receiverType);
			code.setCode(rs.getString("RECEIVER_ID"));
			code.setCodeName(rs.getString("FULL_NAME"));
			code.setType(receiverType + "_" + rs.getString("RECEIVER_ID"));
			code.setTypeName(receiverTypeDesc + rs.getString("FULL_NAME"));
			return code;
		}

	}

	private static String transferRevType(int receiverType) {
		String receiverTypeDesc = null;
		if (receiverType == RECEIVER_TYPE_ROLE_10) {
			receiverTypeDesc = "<直接登录-角色>";
		} else if (receiverType == RECEIVER_TYPE_UNIT_11) {
			receiverTypeDesc = "<直接登录-机构>";
		} else if (receiverType == RECEIVER_TYPE_USER_12) {
			receiverTypeDesc = "<直接登录-用户>";
		} else if (receiverType == RECEIVER_TYPE_ROLE_20) {
			receiverTypeDesc = "<审批登录-角色>";
		} else if (receiverType == RECEIVER_TYPE_UNIT_21) {
			receiverTypeDesc = "<审批登录-机构>";
		} else if (receiverType == RECEIVER_TYPE_USER_22) {
			receiverTypeDesc = "<审批登录-用户>";
		}
		return receiverTypeDesc;
	}

	static class ReceriverPubRowMaper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Message m = new Message();
			m.setReceiverId(rs.getString("RECEIVER_ID"));
			m.setReceiverType(rs.getInt("RECEIVER_TYPE"));
			m.setId(rs.getString("MWEEAGE_ID"));
			return m;
		}
	}

	static class ReceriverRowMaper implements RowMapper {

		private int receiverType;

		private String receiverTypeDesc;

		/**
		 * @param receiverType
		 * // data : [['0', '角色'], ['1', '机构'], ['2', '用户']]
		 */
		public ReceriverRowMaper(int receiverType) {
			this.receiverType = receiverType;
			receiverTypeDesc = transferRevType(receiverType);
		}

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

			Code code = new Code();
			code.setCode(rs.getString("ID"));
			code.setCodeName(rs.getString("NAME"));
			code.setType(receiverType + "_" + rs.getString("ID"));
			code.setTypeName(receiverTypeDesc + rs.getString("NAME"));
			return code;
		}
	}

	/**
	 * 
	 * <p>发送消息映射</p>
	 *
	 * @author 开发人员姓名 付斌 - fu.bin@neusoft.com
	 * @version 1.0 Created on Apr 16, 2009 2:06:57 PM
	 */
	static class MessageSendRowMapper implements RowMapper {

		/**
		 * <p>首页记录映射</p>
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 * @author: 开发人员姓名 付斌 - fu.bin@neusoft.com 
		 * @date: Created on Apr 13, 2009 5:16:47 PM
		 */
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Message m = new Message();
			m.setId(rs.getString("ID"));
			m.setTypeDesc(rs.getString("TYPE_DESC"));
			m.setTitle(rs.getString("TITLE"));
			m.setFlag(rs.getInt("FLAG"));
			m.setPublishDate(rs.getTimestamp("UPDATE_DATE"));
			m.setStartDate(rs.getTimestamp("START_DATE"));
			return m;
		}
	}

	static class MessageIndexRowMapper implements RowMapper {

		/**
		 * <p>首页记录映射</p>
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 * @author: 开发人员姓名 付斌 - fu.bin@neusoft.com 
		 * @date: Created on Apr 13, 2009 5:16:47 PM
		 */
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Message m = new Message();
			m.setId(rs.getString("ID"));
			m.setTypeId(rs.getString("TYPE_ID"));
			m.setTitle(rs.getString("TITLE"));
			m.setPublishDate(rs.getTimestamp("START_DATE"));
			m.setState(rs.getInt("STATE"));
			return m;
		}
	}

	static class MessageRowMapper implements RowMapper {

		/**
		 * <p>列映射</p>
		 * 
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 * @author: 陈乃明 - chennm@neusoft.com
		 * @date: Created on Mar 31, 2009 1:24:41 PM
		 */
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Message m = new Message();
			m.setId(rs.getString("ID"));
			m.setTitle(rs.getString("TITLE"));
			m.setState(rs.getInt("STATE"));
			m.setTypeDesc(rs.getString("TYPE_DESC"));
			m.setPublisherName(rs.getString("USER_NAME"));
			m.setPublishDate(rs.getTimestamp("START_DATE"));
			return m;
		}
	}

	static class MessageInActiveRowMapper implements RowMapper {

		/**
		 * <p>列映射</p>
		 * 
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 * @author: 陈乃明 - chennm@neusoft.com
		 * @date: Created on Mar 31, 2009 1:24:41 PM
		 */
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Message m = new Message();
			m.setId(rs.getString("ID"));
			m.setTitle(rs.getString("TITLE"));
			m.setTypeDesc(rs.getString("TYPE_DESC"));
			m.setStartDate(rs.getTimestamp("START_DATE"));
			m.setPublishDate(rs.getTimestamp("CURR_DATE"));// 用于更新消息状态
			return m;
		}

	}

	static class MessageRevDetailRowMapper implements RowMapper {

		/**
		 * <p>列映射</p>
		 * 
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 * @author: 陈乃明 - chennm@neusoft.com
		 * @date: Created on Mar 31, 2009 1:24:41 PM
		 */
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Message m = new Message();
			m.setId(rs.getString("ID"));
			m.setTitle(rs.getString("TITLE"));
			m.setContent(rs.getString("CONTENT"));
			m.setCallbackParams(rs.getString("CALLBACK_PARAMS"));
			m.setStartDate(rs.getTimestamp("START_DATE"));
			m.setTypeDesc(rs.getString("TYPE_DESC"));
			m.setType(rs.getString("TYPE_NAME"));
			m.setPublisherName(rs.getString("USER_NAME"));
			return m;
		}
	}

	static class MessageSendDetailRowMapper implements RowMapper {

		/**
		 * <p>列映射</p>
		 * 
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 * @author: 陈乃明 - chennm@neusoft.com
		 * @date: Created on Mar 31, 2009 1:24:41 PM
		 */
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Message m = new Message();
			m.setId(rs.getString("ID"));
			m.setTitle(rs.getString("TITLE"));
			m.setContent(rs.getString("CONTENT"));
			m.setTypeDesc(rs.getString("TYPE_DESC"));
			m.setTypeId(rs.getString("TYPE_ID"));
			m.setType(rs.getString("TYPE_NAME"));
			m.setStartDate(rs.getTimestamp("START_DATE"));
			return m;
		}
	}

	static class MessageConfigMapper implements RowMapper {

		/**
		 * <p>数据映射</p>
		 * 
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 * @author: 陈乃明 - chennm@neusoft.com
		 * @date: Created on Apr 2, 2009 11:03:34 AM
		 */
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			MessageConfig config = new MessageConfig();
			config.setKeepTime(Integer.parseInt(rs.getString("KEEP_TIME")));
			config.setStartPoll(Integer.parseInt(rs.getString("START_POLL")));
			config.setPollingTime(Integer.parseInt(rs.getString("POLLING_TIME")));
			config.setShowMsgNum(Integer.parseInt(rs.getString("SHOW_NUMBER")));
			return config;
		}
	}

}
