package com.prototype.vaadin.web.ui.component.grid;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.prototype.vaadin.web.util.UIUtils;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

/**
 * 客製 grid layout, 可使用 rowspan, colspan 做版面調整,
 * default columns = 2
 * 
 * @author neil.lin
 *
 */
public class CustomGridLayout {

	private boolean DEBUG = false;
	
	private Map<AbstractComponent, GridComponent> componentMp = 
			new LinkedHashMap<AbstractComponent, CustomGridLayout.GridComponent>();

	private int columns = 2;			// 請提供 2 的倍數, 通常就 2 4 6
	private int ratioColumns = 0;
	private int lableRatio = 1;
	private boolean isSizeFull;			// 是否展到最大
	
	private int rows = 0;
	private GridLayout gridLayout;
	private List<String> xyPointLs = new ArrayList<String>();
	
	public GridLayout create() {
		try {
			lableRatio = 12 / columns;	
			ratioColumns = (1 + 1 * lableRatio) * columns / 2; // new columns

			debug("columns = " + columns);
			debug("ratioColumns = " + ratioColumns);
			debug("lableRatio = " + lableRatio);
			debug("=================================================");
			
			gridLayout = new GridLayout();
			gridLayout.setSizeFull();
			gridLayout.setColumns(ratioColumns);
			gridLayout.setSpacing(true);
			
			gridLayoutAddRow();
			
			for (GridComponent gridComponent : componentMp.values()) {
				String[] firstXY = xyPointLs.get(0).split(",");
				int currentX = Integer.parseInt(firstXY[0]);
				int currentY = Integer.parseInt(firstXY[1]);

				debug("currentXY = " + currentX + "," + currentY);
				
				Component label = gridComponent.getLabel();
				Component component = gridComponent.getComponent();
				int colspan = gridComponent.getColspan();
				int rowspan = gridComponent.getRowspan();

				debug("colspan = " + colspan);
				debug("rowspan = " + rowspan);

				int labelX = currentX;
				int labelY = currentY;
				int labelX2 = currentX;
				int labelY2 = labelY + rowspan - 1;
				int componentX = labelX + 1;
				int componentY = labelY;
				
				int componentX2 = componentX + (1 + 1 * lableRatio) * (colspan + 1) / 2 - 2;
				int componentY2 = componentY + rowspan - 1;
				
				if ((rowspan - 1) > 0) {
					gridLayoutAddRow(rowspan - 1);	
				}

				debug(labelX + "," + labelY + "," + labelX2 + "," + labelY2);
				debug(componentX + "," + componentY + "," + componentX2 + "," + componentY2);
				
				gridLayout.addComponent(label, labelX, labelY, labelX2, labelY2);
				gridLayout.addComponent(component, componentX, componentY, componentX2, componentY2);
				
				removeXY(labelX, labelY, labelX2, labelY2);
				removeXY(componentX, componentY, componentX2, componentY2);
				
				currentX = componentX2 + 1; //
				if ((rowspan - 1) == 0 && currentX % ratioColumns == 0) { // 換行
					debug("換行");
					currentX = 0;
					currentY += 1;
					gridLayoutAddRow();
				}
				debug("next currentXY = " + currentX + "," + currentY);
			}
		} catch (Exception e) {
			e.printStackTrace();
			UIUtils.showException(e);
		}

		return gridLayout;
	}
	
	private void removeXY(int x1, int y1, int x2, int y2) {
		for (int x = x1; x <= x2; x++) {
			for (int y = y1; y <= y2; y++) {
				removeXY(x, y);
			}	
		}
	}
	
	public void addComponent(String caption, AbstractComponent component) {
		Label label = new Label();
		if (!StringUtils.isBlank(caption)) {
			label = new Label(caption + " :&nbsp;&nbsp;&nbsp;", ContentMode.HTML);
		}
		label.addStyleName("v-align-right"); // 靠右

		if (isSizeFull) {
			label.setSizeFull();
			component.setSizeFull();	
		} else {
//			label.setSizeUndefined();
//			component.setSizeUndefined();
		}
		
		GridComponent gridComponent = new GridComponent(label, component);

		componentMp.put(component, gridComponent);
	}
	
	public void setColspan(AbstractComponent component, int colspan) {
		componentMp.get(component).setColspan(colspan);
	};
	
	public void setRowspan(AbstractComponent component, int rowspan) {
		componentMp.get(component).setRowspan(rowspan);
	};

	public void setColumns(Object columns) {
		this.columns = Integer.parseInt(columns + "");
	}
	
	public void setComponentSizeFull(Object isSizeFull) {
		this.isSizeFull = new Boolean(isSizeFull + "");
	}
	
	// ====================================================================================

	class GridComponent {
		Component label;
		Component component;
		int colspan = 1;
		int rowspan = 1;
		
		public GridComponent(Component label, Component component) {
			super();
			this.label = label;
			this.component = component;
		}

		public Component getLabel() {
			return label;
		}

		public void setLabel(Component label) {
			this.label = label;
		}

		public Component getComponent() {
			return component;
		}

		public void setComponent(Component component) {
			this.component = component;
		}

		public int getColspan() {
			return colspan;
		}

		public void setColspan(int colspan) {
			this.colspan = colspan;
		}

		public int getRowspan() {
			return rowspan;
		}

		public void setRowspan(int rowspan) {
			this.rowspan = rowspan;
		}
	}

	private void debug(Object msg) {
		if (DEBUG) {
			System.out.println(msg);
		}
	}
	
	private void removeXY(int x, int y) {
		String key = String.format("%s,%s", x, y);
		xyPointLs.remove(key);
		
		debug(xyPointLs);
	}
	
	private void gridLayoutAddRow() {
		gridLayoutAddRow(1);
	}
	
	private void gridLayoutAddRow(int addRows) {
		for (int i = 0; i < addRows; i++) {
			for (int x = 0; x < ratioColumns; x++) {
				String xy = String.format("%s,%s", x, rows);
				xyPointLs.add(xy);
			}
			rows += 1;
			gridLayout.setRows(rows);			
		}
		
		debug("xyMp = " + xyPointLs);
	}
}
