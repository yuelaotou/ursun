package cn.ursun.platform.core.tag.grid.component;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

public class SupportComponent extends Component {

	protected String contextPath = ServletActionContext.getRequest().getContextPath();

	public SupportComponent(ValueStack stack) {
		super(stack);
	}

}
