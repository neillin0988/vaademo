package com.prototype.vaadin.web.ui.component.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table.HeaderClickEvent;
import com.vaadin.ui.Table.HeaderClickListener;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class GridView extends VerticalLayout {
	
	private XPagedTable table;
	private HorizontalLayout pager;
	private LinkedList<String> columns;
	
	private boolean addSerialColumn = false;
	private boolean addCheckboxColumn = false;
	private ArrayList<String> newVisibleColumnLs;
	private List<String> visibleColumnLs;

	private static final String SERIAL_COLUMN = "serial";		// 流水號
	private static final String CHECKBOX_COLUMN = "checkbox";	// 勾選
	 
	/**
	 * This map keeps track of all the created and attached checkboxes, by
	 * ItemId
	 */
	protected Map<Object, CheckBox> itemIdToCheckbox = new HashMap<Object, CheckBox>();
	protected Map<Object, CheckBox> itemIdToCheckedCheckbox = new HashMap<Object, CheckBox>();
	private GridViewHeaderClickListener gridViewHeaderClickListener;

	public GridView(List<String> visibleColumnLs) {
		this.visibleColumnLs = visibleColumnLs;
		
		if (CollectionUtils.isEmpty(visibleColumnLs)) {
			throw new IllegalArgumentException("visibleColumnLs not be empty");
		}
	}
	
	public void addItemClickListener(ItemClickListener itemClickListener) {
		table.addItemClickListener(itemClickListener);
	}
	
	public void create(List<Map<String, Object>> list) {
		newVisibleColumnLs = new ArrayList<String>();
		newVisibleColumnLs.addAll(visibleColumnLs);
		
		VerticalLayout gridViewLayout = new VerticalLayout();
		table = new XPagedTable();

		// Allow the user to collapse and uncollapse columns
		table.setColumnCollapsingAllowed(true);
		table.setWidth(100, Unit.PERCENTAGE);

	    table.setMultiSelect(true);
	    table.setSelectable(true);

	    if (addSerialColumn) {
	    	doAddSerialHeader();
	    }
	    if (addCheckboxColumn) {
	    	doAddCheckboxHeader();
	    }
	    
//	    sameple code
//	    table.addItemClickListener(new ItemClickListener() {
//			@Override
//			public void itemClick(ItemClickEvent event) {
//				// event.getPropertyId(); column key
//				Object itemId = event.getItemId();
//				
//				Map<String, Object> data = getRowData(itemId);
//				System.out.println(data.get("createdate").getClass());
//				System.out.println(data.get("sort").getClass());
//			}
//		});
	    
		gridViewLayout.addComponent(table);
		addComponent(gridViewLayout);
		
		if (CollectionUtils.isEmpty(list)) {
			// 空表格, 把visable column 畫出來就好
			for (String key : visibleColumnLs) {
				table.addContainerProperty(key, String.class, null);
			}
			table.setVisibleColumns(newVisibleColumnLs.toArray());
			
			return;
		}

		// ========================== list is not empty ======================
		Map<String, Object> row = list.get(0);
		columns = new LinkedList<String>();
		for (String key : row.keySet()) {
			columns.add(key);
			table.addContainerProperty(key, Object.class, null);
		}
		
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> data = list.get(i);

			final int itemId = i + 1;
			List<Object> objLs = new ArrayList<Object>();

		    if (addSerialColumn) {
		    	objLs.add(doAddSerialColumn(itemId));
		    }
		    if (addCheckboxColumn) {
		    	objLs.add(doAddCheckboxColumn(itemId));
		    }
			
			for (int j = 0; j < columns.size(); j++) {
				String column = columns.get(j);
				Object value = MapUtils.getObject(data, column);
				objLs.add(value);
			}
			
			table.addItem(objLs.toArray(), itemId);
		}
		
//		table.addValueChangeListener(new Property.ValueChangeListener() {
//			@Override
//			public void valueChange(Property.ValueChangeEvent event) {
//			}
//		});
		
		// setVisibleColumns
		table.setVisibleColumns(newVisibleColumnLs.toArray());
		
		pager = table.createControls();
		table.firePagedChangedEvent();
		pager.setWidth(100, Unit.PERCENTAGE);
		
		gridViewLayout.addComponent(pager);		
	}

	private Component doAddCheckboxColumn(final Object itemId) {
		// 加入row勾選
    	final CheckBox checkBox = new CheckBox();
    	Map<String, Object> checkBoxdata = new HashMap<String, Object>();
    	checkBoxdata.put(SERIAL_COLUMN, itemId);
    	checkBox.setData(checkBoxdata);
    	
    	checkBox.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				boolean selected = checkBox.getValue();
				
				// 阻止header 全選時觸發
				if (!triggerCheckbox) {
					return;
				}
				
				if (selected) {
					itemIdToCheckedCheckbox.put(itemId, checkBox);
				} else {
					itemIdToCheckedCheckbox.remove(itemId);
				}
				
				if (itemIdToCheckedCheckbox.size() == itemIdToCheckbox.size()) {
					gridViewHeaderClickListener.setHeadCheckBoxSelect(true);
				} else {
					gridViewHeaderClickListener.setHeadCheckBoxSelect(false);
				}
			}
		});

		itemIdToCheckbox.put(itemId, checkBox);
    	
    	return checkBox;
	}

	private Component doAddSerialColumn(Object itemId) {
		// 加入流水號
    	Label label = new Label(String.valueOf(itemId));
		
		return label;
	}

	// 包含全選功能
	private void doAddCheckboxHeader() {
		table.addContainerProperty(CHECKBOX_COLUMN, CheckBox.class, null);
		table.setColumnWidth(CHECKBOX_COLUMN, 20);	// column 寬度

		newVisibleColumnLs.add(0, CHECKBOX_COLUMN);

		gridViewHeaderClickListener = new GridViewHeaderClickListener();
		gridViewHeaderClickListener.setHeadCheckBoxSelect(false);
		table.addHeaderClickListener(gridViewHeaderClickListener);
	}

	private void doAddSerialHeader() {
		table.addContainerProperty(SERIAL_COLUMN, Label.class, null);
		table.setColumnWidth(SERIAL_COLUMN, 30);	// column 寬度

	    table.setColumnHeader(SERIAL_COLUMN, "No.");
		newVisibleColumnLs.add(0, SERIAL_COLUMN);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, Object>> getSelectRows() {
		List<Map<String, Object>> rowDataLs = new ArrayList<Map<String,Object>>();
		
		Set<Object> itemIdSet = (Set<Object>) table.getValue();
		for (Object itemId : itemIdSet) {
			Map<String, Object> dataMp = new HashMap<String, Object>();
			for (String columnKey : columns) {
				Property prop = table.getItem(itemId).getItemProperty(columnKey);
				dataMp.put(columnKey, prop.getValue());
			}
		}
		
		return rowDataLs;
	}
	
	@SuppressWarnings({"rawtypes" })
	public Map<String, Object> getRowData(Object itemId) {
		Map<String, Object> dataMp = new HashMap<String, Object>();
		for (String columnKey : columns) {
			Property prop = table.getItem(itemId).getItemProperty(columnKey);
			dataMp.put(columnKey, prop.getValue());
		}
		
		return dataMp;
	}

	public void setAddSerialColumn(boolean addSerialColumn) {
		this.addSerialColumn = addSerialColumn;
	}

	public void setAddCheckboxColumn(boolean addCheckboxColumn) {
		this.addCheckboxColumn = addCheckboxColumn;
	}

    private Boolean triggerCheckbox = true;
	class GridViewHeaderClickListener implements HeaderClickListener {
		// Starts unselected
	    private Boolean headCheckboxSelected = false;
	    
		@Override
		public void headerClick(HeaderClickEvent event) {
			if (CHECKBOX_COLUMN.equals(event.getPropertyId())) {
				fireChecked();
			}
		}
		
		private void fireChecked() {
			if (!headCheckboxSelected) {
				setAllCheckBoxSelect(true); // check CheckBoxes in each raw
				setHeadCheckBoxSelect(true);
			} else {
				setAllCheckBoxSelect(false); // check CheckBoxes in each raw
				setHeadCheckBoxSelect(false);
			}
		}

		public void setHeadCheckBoxSelect(boolean selected) {
			if (selected) {
				table.setColumnHeader(CHECKBOX_COLUMN,
			    		"<input type='checkbox' value='on' id='" + CHECKBOX_COLUMN + "' checked>");
			} else {
				table.setColumnHeader(CHECKBOX_COLUMN,
			    		"<input type='checkbox' value='on' id='" + CHECKBOX_COLUMN + "'>");
			}

			headCheckboxSelected = selected;
		}

		@SuppressWarnings("unchecked")
		private void setAllCheckBoxSelect(boolean selected) {
			triggerCheckbox = false;
			for (CheckBox checkBox : itemIdToCheckbox.values()) {
				checkBox.setValue(selected);
				
				Map<String, Object> checkBoxdata = (Map<String, Object>) checkBox.getData();
				Object serial = checkBoxdata.get(SERIAL_COLUMN);
				
				itemIdToCheckedCheckbox.put(serial, checkBox);
			}
			triggerCheckbox = true;
		}
		
	}
}
