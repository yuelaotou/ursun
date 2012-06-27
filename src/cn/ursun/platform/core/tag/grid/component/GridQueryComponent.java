package cn.ursun.platform.core.tag.grid.component;

import java.io.IOException;
import java.io.Writer;

import com.opensymphony.xwork2.util.ValueStack;

public class GridQueryComponent extends SupportComponent {

	private String cssClass = null;

	public GridQueryComponent(ValueStack stack) {
		super(stack);
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public boolean start(Writer writer) {
		ValueStack stack = getStack();
		GridComponent.GridState gridCurrentState = (GridComponent.GridState) stack.getContext().get(
				GridComponent.GRID_STATE_KEY);
		if (!gridCurrentState.equals(GridComponent.GridState.DRAW_HEADER)) {
			return false;
		}

		try {
			if (cssClass != null) {
				writer.write("<tr CLASS='" + cssClass + "'><td>");
			} else {
				writer.write("<tr CLASS=\"gridQuery\"><td>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean end(Writer writer, String body) {
		ValueStack stack = getStack();
		GridComponent.GridState gridCurrentState = (GridComponent.GridState) stack.getContext().get(
				GridComponent.GRID_STATE_KEY);
		if (!gridCurrentState.equals(GridComponent.GridState.DRAW_HEADER)) {
			return false;
		}
		super.end(writer, "</td></tr>");
		return true;
	}

}
