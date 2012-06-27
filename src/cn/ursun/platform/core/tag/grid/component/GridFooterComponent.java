package cn.ursun.platform.core.tag.grid.component;

import java.io.IOException;
import java.io.Writer;

import com.opensymphony.xwork2.util.ValueStack;

public class GridFooterComponent extends SupportComponent {

	private String cssClass = null;

	public GridFooterComponent(ValueStack stack) {
		super(stack);
	}

	public boolean start(Writer writer) {
		ValueStack stack = getStack();
		GridComponent.GridState gridCurrentState = (GridComponent.GridState) stack.getContext().get(
				GridComponent.GRID_STATE_KEY);
		if (!gridCurrentState.equals(GridComponent.GridState.DRAW_FOOTER)) {
			return false;
		}
		
		String columnCount = (String) stack.getContext().get(
				GridComponent.COLUMN_COUNT_KEY);
		
		try {
			if (cssClass != null) {
				writer.write("</tbody><tfoot><tr class='" + cssClass + " ><td class='grid_footer' colspan='"+columnCount+"'>");
			} else {
				writer.write("</tbody><tfoot><tr><td class='grid_footer' colspan='"+columnCount+"'>");
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
		if (!gridCurrentState.equals(GridComponent.GridState.DRAW_FOOTER)) {
			return false;
		}

		super.end(writer,  "</td></tr></tfoot>");
		return false;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
}
