package cn.ursun.platform.ps.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.ursun.platform.ps.domain.TreeNode;

public class TreeUtil {

	public static TreeNode createTreeRelation(List<TreeNode> list) {
		TreeNode root = null;
		TreeNode tempRoot = new TreeNode();
		Map<String, TreeNode> map = new HashMap<String, TreeNode>();
		for (TreeNode node : list) {
			map.put(node.getKey(), node);
		}
		for (Object o : list) {
			TreeNode node = (TreeNode) o;
			if (StringUtils.isEmpty(node.getParentId())) {
				root = node;
			} else {
				if (map.containsKey(node.getParentId())) {
					map.get(node.getParentId()).addChildren(node);
				} else {
					if (root == null)
						tempRoot.addChildren(node);
					else
						root.addChildren(node);
				}
			}
		}
		if (root != null) {
			if (tempRoot.getChildren() != null)
				for (Object node : tempRoot.getChildren())
					root.addChildren((TreeNode) node);
		} else {
			root = tempRoot;
		}
		map.clear();
		return root;
	}

}
