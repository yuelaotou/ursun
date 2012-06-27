package cn.ursun.platform.core.tag.grid.component;

import java.io.IOException;
import java.io.Writer;

import com.opensymphony.xwork2.util.ValueStack;

public class GridToolBarComponent extends SupportComponent {

	private String cssClass = null;

	public GridToolBarComponent(ValueStack stack) {
		super(stack);
	}

	public boolean start(Writer writer) {
		ValueStack stack = getStack();
		GridComponent.GridState gridCurrentState = (GridComponent.GridState) stack.getContext().get(
				GridComponent.GRID_STATE_KEY);
		if (!gridCurrentState.equals(GridComponent.GridState.DRAW_FOOTER)
				&& !gridCurrentState.equals(GridComponent.GridState.DRAW_HEADER)) {
			return false;
		}

		String columnCount = (String) stack.getContext().get(GridComponent.COLUMN_COUNT_KEY);

		try {
			if (gridCurrentState.equals(GridComponent.GridState.DRAW_HEADER)) {
				writer.write("<td class='gridHeader' colspan='100'> ");
			}
			if (cssClass != null) {
				writer.write("<div class='grid-toolbar " + cssClass + " >");
			} else {
				writer.write("<div class='grid-toolbar'>");
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
		if (!gridCurrentState.equals(GridComponent.GridState.DRAW_FOOTER)
				&& !gridCurrentState.equals(GridComponent.GridState.DRAW_HEADER)) {
			return false;
		}
		StringBuffer str = new StringBuffer();
		str.append("</div>");
		if (gridCurrentState.equals(GridComponent.GridState.DRAW_HEADER)) {
			str.append("</td ></tr><tr>");
		}
		super.end(writer, str.toString());
		return false;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
}
