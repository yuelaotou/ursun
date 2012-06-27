package cn.ursun.console.app.console.role.util;

import cn.ursun.platform.ps.domain.TreeNode;

import java.util.*;

public class RoleTreeUtil {
    
	private RoleTreeUtil() {
		super();
	}
	/**
	 * 
	 * <p>对树的节点进行组装</p>
	 * @param list
	 * @return
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on 2010-1-18 上午09:40:49
	 */
	public static Collection<TreeNode> createTreeRelation(List<TreeNode> list) {
		Map<String, TreeNode> map = new HashMap<String, TreeNode>();
		for (TreeNode node : list) {
			map.put(node.getKey(), node);
		}
		return createTreeRelation(map);
	}

	public static Collection<TreeNode> createTreeRelation(Map<String, TreeNode> map) {
		Iterator<String> it = map.keySet().iterator();
		// 存放非根节点的元素
		List<String> removedNodes = new ArrayList<String>();
		while (it.hasNext()) {
			String key = (String) it.next();
			TreeNode node = (TreeNode) map.get(key);
			TreeNode parentNode = (TreeNode) map.get(node.getParentId());
			if (node.getParentId() != null && parentNode != null) {
				parentNode.addChildren(node);
				removedNodes.add(key);
			}
		}
		for (String key : removedNodes) {
			map.remove(key);
		}
		Collection<TreeNode> roots = map.values();
		return roots;
	}
}