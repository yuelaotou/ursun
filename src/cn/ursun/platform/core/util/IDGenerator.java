/**
 * 文件名：IDGenerator.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Aug 21, 2008 3:20:23 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.util;

import java.util.UUID;

/**
 * <p>ID生成工具。</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Aug 21, 2008 3:20:23 PM
 */
public class IDGenerator {

	public static String generateId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String generateId(String sequenceName) {
		return generateId();
	}

	/*
	 * public static void main(String[] args) { String id = null; Map map = new HashMap(); while (true) { id =
	 * IDGenerator.generateId(); if (map.keySet().contains(id.replaceAll("-", ""))) { System.out.println(id + " -------- " +
	 * id); System.out.println(map.get(id.replaceAll("-", "")) + " -------- " + id); break; } else {
	 * System.out.println(id + "长度：" + id.length() + " -------- " + id.replaceAll(":|-", "") + "长度：" +
	 * id.replaceAll(":|-", "").length()); map.put(id.replaceAll("-", ""), id); } } }
	 */

}
