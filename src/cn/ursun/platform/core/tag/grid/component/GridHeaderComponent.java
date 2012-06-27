package cn.ursun.platform.core.tag.grid.component;

import java.io.IOException;
import java.io.Writer;

import com.opensymphony.xwork2.util.ValueStack;

public class GridHeaderComponent extends SupportComponent {

	private String cssClass = null;

	public final static String GRID_HEADER_COL_INDEX_KEY = "GRID_HEADER_COL_INDEX_KEY";

	public GridHeaderComponent(ValueStack stack) {
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
		stack.getContext().put(GRID_HEADER_COL_INDEX_KEY, 0);
		try {
			if (cssClass != null) {
				writer.write("<tr CLASS='" + cssClass + "'>");
			} else {
				writer.write("<tr>");
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
		if (stack.getContext().containsKey(GRID_HEADER_COL_INDEX_KEY)) {
			stack.getContext().remove(GRID_HEADER_COL_INDEX_KEY);
		}
		super.end(writer, "</tr></thead><tbody class='grid_tbody'>");
		return true;
	}

}
