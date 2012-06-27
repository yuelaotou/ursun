package cn.ursun.platform.ps.framework.menu.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.ursun.platform.core.action.WeeAction;
import cn.ursun.platform.ps.domain.TreeNode;

public class MenuAC extends WeeAction {

	private String key;

	private String title;
	
	private boolean isFolder;

	private List<TreeNode> children;

	public String queryMenu() {
		String contextPath=ServletActionContext.getRequest().getContextPath();
		this.key = "root";
		this.title = this.getText("menu.root");
		this.isFolder=true;
		this.children = new ArrayList<TreeNode>();
		TreeNode demo = new TreeNode();
		demo.setKey("demo");
		demo.setTitle(this.getText("menu.demo"));
		demo.setUrl(contextPath+"/wee/demo/framework.do");
		children.add(demo);
		TreeNode example = new TreeNode();
		example.setKey("example");
		example.setTitle(this.getText("menu.example"));
		example.setUrl(contextPath+"/example/framework/framework.jsp");
		children.add(example);
		TreeNode systemManager = new TreeNode();
		systemManager.setKey("systemmanage");
		systemManager.setTitle(this.getText("menu.systemmanage"));
		systemManager.setIsFolder(true);
		children.add(systemManager);
		TreeNode childrenNode = new TreeNode();
		childrenNode.setKey("person");
		childrenNode.setTitle(this.getText("menu.systemmanage.person"));
		childrenNode.setUrl("javascript:alert('尚在开发中')");
		systemManager.addChildren(childrenNode);
		childrenNode = new TreeNode();
		childrenNode.setKey("rose");
		childrenNode.setTitle(this.getText("menu.systemmanage.rose"));
		childrenNode.setUrl("javascript:alert('尚在开发中')");
		systemManager.addChildren(childrenNode);
		
//		TreeNode jQueryDemo = new TreeNode();
//		jQueryDemo.setKey("jQueryDemo");
//		jQueryDemo.setTitle("jQueryDemo");
//		jQueryDemo.setIsFolder(true);
//		children.add(jQueryDemo);
//		TreeNode childrenNode = new TreeNode();
//		childrenNode.setKey("dialog");
//		childrenNode.setTitle("Dialog");
//		childrenNode.setUrl(contextPath+"/example/jqueryDemo/dialog-modal-form.jsp");
//		jQueryDemo.addChildren(childrenNode);
//		childrenNode = new TreeNode();
//		childrenNode.setKey("accordion");
//		childrenNode.setTitle("Accordion");
//		childrenNode.setUrl(contextPath+"/example/jqueryDemo/accordion-default.jsp");
//		jQueryDemo.addChildren(childrenNode);
//		childrenNode = new TreeNode();
//		childrenNode.setKey("tab");
//		childrenNode.setTitle("Tab");
//		childrenNode.setUrl(contextPath+"/example/jqueryDemo/tab-default.jsp");
//		jQueryDemo.addChildren(childrenNode);
//		childrenNode = new TreeNode();
//		childrenNode.setKey("tree");
//		childrenNode.setTitle("Tree");
//		childrenNode.setUrl(contextPath+"/example/jqueryDemo/tree-contextmenu.jsp");
//		jQueryDemo.addChildren(childrenNode);
//		TreeNode weeTagDemo = new TreeNode();
//		weeTagDemo.setKey("weeTagDemo");
//		weeTagDemo.setTitle("weeTagDemo");
//		weeTagDemo.setIsFolder(true);
//		children.add(weeTagDemo);
//		childrenNode = new TreeNode();
//		childrenNode.setKey("JspTagGrid");
//		childrenNode.setTitle("JspTagGrid");
//		childrenNode.setUrl(contextPath+"/example/employeeAC!queryEmployeeList.do?gridType=1");
//		weeTagDemo.addChildren(childrenNode);
//		childrenNode = new TreeNode();
//		childrenNode.setKey("CustomStyleGrid");
//		childrenNode.setTitle("CustomStyleGrid");
//		childrenNode.setUrl(contextPath+"/example/employeeAC!queryEmployeeList.do?gridType=2");
//		weeTagDemo.addChildren(childrenNode);
		return JSON;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public boolean getIsFolder() {
		return isFolder;
	}

	
	public void setIsFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}

}
