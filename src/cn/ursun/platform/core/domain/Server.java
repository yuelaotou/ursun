/**
 * 文件名：Server.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Oct 14, 2008 7:05:03 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.domain;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>注册到系统中的服务器</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Oct 14, 2008 7:05:03 PM
 */
public class Server {

	private String serverId = null;

	private String serverName = null;

	private String IP = null;

	private String port = null;

	private String ipInner = null;

	private String serverNameInner = null;
	
	public static final String SERVER_IP = "currentServer.ip";

	public static final String SERVER_PORT = "currentServer.port";

	public static final String SERVER_NAME = "currentServer.name";

	public static final String SERVER_ID = "currentServer.id";
	

	public Server() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			ipInner = addr.getHostAddress().toString();// 获得本机IP
			serverNameInner = addr.getHostName().toString();// 获得本机名称
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String ip) {
		IP = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getServerId() {
		// return serverId;
		// TODO: 正式上线后，应该将InnerIP_ServerNameInner_ServerId的形式修改为ServerId的形式
		return new StringBuffer(this.ipInner).append("_").append(this.serverNameInner).append("_")
				.append(this.serverId).toString();
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
}
