package com.prototype.vaadin.web.ui.function.sys.sys005;

import java.util.Arrays;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prototype.vaadin.core.Context.DataSource;
import com.prototype.vaadin.web.ui.component.FunctionBasicView;
import com.prototype.vaadin.web.ui.component.field.HrLabel;
import com.prototype.vaadin.web.ui.component.grid.CustomGridLayout;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Scope("prototype")
@Component
public class customGridLayoutView extends FunctionBasicView {
	
	private VerticalLayout v1;
	private VerticalLayout v2;
	private ComboBox columnsBox;
	private ComboBox isSizeFull;
	
	@Override
	protected void createFunctionLayout() {
		columnsBox = new ComboBox();
		columnsBox.setNullSelectionAllowed(false);
		columnsBox.addItems(2, 4, 6);
		
		isSizeFull= new ComboBox();
		isSizeFull.setNullSelectionAllowed(false);
		isSizeFull.addItems(true, false);
		
		CustomGridLayout formLayout = new CustomGridLayout();
		formLayout.setComponentSizeFull(true);
		formLayout.addComponent("columns", columnsBox);
		formLayout.addComponent("isSizeFull", isSizeFull);
		
		v1 = new VerticalLayout();
		v2 = new VerticalLayout();

		v1.addComponent(formLayout.create());
		v1.addComponent(new HrLabel());

		layout.addComponent(v1);
		layout.addComponent(v2);
		
    	setContent(layout);
    	setEvents();
    	setDefaultValue();
	}

	private void setDefaultValue() {
		columnsBox.setValue(columnsBox.getItemIds().iterator().next());
		isSizeFull.setValue(isSizeFull.getItemIds().iterator().next());
	}

	private void setEvents() {
		ValueChangeListener createFormLayout = new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				createFormLayout(v2);				
			}
		};
		
		columnsBox.addValueChangeListener(createFormLayout);
		isSizeFull.addValueChangeListener(createFormLayout);
	}

	private void createFormLayout(VerticalLayout v2) {
		v2.removeAllComponents();
		
	    ComboBox dataSource = new ComboBox();
		Button doQueryBtn = new Button("doQuery");
		CheckBox bindItemClick = new CheckBox();
		TextArea sqlTextArea = new TextArea();
		
		CustomGridLayout formLayout = new CustomGridLayout();
		formLayout.setColumns(columnsBox.getValue());
		formLayout.setComponentSizeFull(isSizeFull.getValue());
		
		doQueryBtn.setIcon(FontAwesome.SEARCH);
		dataSource.setInvalidAllowed(false);
		dataSource.setNullSelectionAllowed(false);
		for (DataSource value : Arrays.asList(DataSource.values())) {
			dataSource.addItem(value);
		}
		
		sqlTextArea.setRows(10);
		sqlTextArea.setSizeFull();

		formLayout.addComponent("bindItemClick", bindItemClick);
		formLayout.addComponent("TextField", new TextField());
		formLayout.addComponent("dataSource", dataSource);
		formLayout.addComponent("set your sql text here", sqlTextArea);
		formLayout.addComponent("", doQueryBtn);
		
//		formLayout.setColspan(dataSource, 3);
//		formLayout.setColspan(sqlTextArea, 3);
		
		v2.addComponent(formLayout.create());
	}

}
