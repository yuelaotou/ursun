package cn.ursun.demo.app.demo.action;

import org.apache.struts2.ServletActionContext;

import cn.ursun.platform.core.action.WeeAction;
import cn.ursun.platform.ps.domain.TreeNode;

public class MenuAC extends WeeAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TreeNode root;

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public String queryMenu() {
		String contextPath = ServletActionContext.getRequest().getContextPath();
		root = new TreeNode();
		root.setKey("root");
		root.setTitle("示例");
		root.setIsFolder(true);
		TreeNode weeUI = new TreeNode();
		weeUI.setKey("weeui");
		weeUI.setTitle(this.getText("menu.ui"));
		weeUI.setIsFolder(true);
		root.addChildren(weeUI);
		TreeNode childrenNode = new TreeNode();
		childrenNode.setKey("dialog");
		childrenNode.setTitle(this.getText("menu.dialog"));
		childrenNode.setUrl(contextPath
				+ "/demo/page/jquery_ui_demo/dialog.jsp");
		weeUI.addChildren(childrenNode);
		childrenNode = new TreeNode();
		childrenNode.setKey("accordion");
		childrenNode.setTitle(this.getText("menu.accordion"));
		childrenNode.setUrl(contextPath
				+ "/demo/page/jquery_ui_demo/accordion.jsp");
		weeUI.addChildren(childrenNode);
		childrenNode = new TreeNode();
		childrenNode.setKey("tab");
		childrenNode.setTitle(this.getText("menu.tab"));
		childrenNode.setUrl(contextPath + "/demo/page/jquery_ui_demo/tab.jsp");
		weeUI.addChildren(childrenNode);
		childrenNode = new TreeNode();
		childrenNode.setKey("tree");
		childrenNode.setTitle(this.getText("menu.tree"));
		childrenNode.setUrl(contextPath + "/demo/page/jquery_ui_demo/tree.jsp");
		weeUI.addChildren(childrenNode);
		childrenNode = new TreeNode();
		childrenNode.setKey("calendar");
		childrenNode.setTitle(this.getText("menu.calendar"));
		childrenNode.setUrl(contextPath
				+ "/demo/page/jquery_ui_demo/calendar.jsp");
		weeUI.addChildren(childrenNode);
		childrenNode = new TreeNode();
		childrenNode.setKey("form");
		childrenNode.setTitle(this.getText("menu.form"));
		childrenNode.setUrl(contextPath + "/demo/page/jquery_ui_demo/form.jsp");
		weeUI.addChildren(childrenNode);
		childrenNode = new TreeNode();
		childrenNode.setKey("progressbar");
		childrenNode.setTitle(this.getText("menu.progressbar"));
		childrenNode.setUrl(contextPath
				+ "/demo/page/jquery_ui_demo/progressbar.jsp");
		weeUI.addChildren(childrenNode);
		childrenNode = new TreeNode();
		childrenNode.setKey("slider");
		childrenNode.setTitle(this.getText("menu.slider"));
		childrenNode.setUrl(contextPath
				+ "/demo/page/jquery_ui_demo/slider.jsp");
		weeUI.addChildren(childrenNode);
		childrenNode = new TreeNode();
		childrenNode.setKey("xheditor");
		childrenNode.setTitle(this.getText("menu.xheditor"));
		childrenNode.setUrl(contextPath
				+ "/demo/page/jquery_ui_demo/xheditor.jsp");
		weeUI.addChildren(childrenNode);
		childrenNode = new TreeNode();
		childrenNode.setKey("autocomplete");
		childrenNode.setTitle(this.getText("menu.autocomplete"));
		childrenNode.setUrl(contextPath
				+ "/demo/page/autocomplete/autocomplete.jsp");
		weeUI.addChildren(childrenNode);
		childrenNode = new TreeNode();
		childrenNode.setKey("window");
		childrenNode.setTitle(this.getText("menu.window"));
		childrenNode.setUrl(contextPath + "/demo/page/window/window.jsp");
		weeUI.addChildren(childrenNode);
		TreeNode weeTagDemo = new TreeNode();
		weeTagDemo.setKey("tag");
		weeTagDemo.setTitle(this.getText("menu.tag"));
		weeTagDemo.setIsFolder(true);
		root.addChildren(weeTagDemo);
		childrenNode = new TreeNode();
		childrenNode.setKey("grid");
		childrenNode.setTitle(this.getText("menu.grid"));
		childrenNode.setUrl(contextPath
				+ "/demo/demoShowAC!queryEmployeeList.do");
		weeTagDemo.addChildren(childrenNode);
		childrenNode = new TreeNode();
		childrenNode.setKey("page");
		childrenNode.setTitle(this.getText("menu.page"));
		childrenNode.setUrl(contextPath + "/demo/pageAC!forwardMainPage.do");
		weeTagDemo.addChildren(childrenNode);
		childrenNode = new TreeNode();
		childrenNode.setKey("page");
		childrenNode.setTitle("主框架");
		childrenNode.setUrl(contextPath + "/console/framework/framework.jsp");
		weeTagDemo.addChildren(childrenNode);
		return JSON;
	}

}
