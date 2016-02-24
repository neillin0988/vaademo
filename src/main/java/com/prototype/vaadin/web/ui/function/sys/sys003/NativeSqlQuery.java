package com.prototype.vaadin.web.ui.function.sys.sys003;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prototype.vaadin.core.Context.DataSource;
import com.prototype.vaadin.core.busniess.facade.SystemFacade;
import com.prototype.vaadin.core.util.JsonUtils;
import com.prototype.vaadin.web.model.SessionInfo;
import com.prototype.vaadin.web.ui.component.FunctionBasicView;
import com.prototype.vaadin.web.ui.component.grid.CustomGridLayout;
import com.prototype.vaadin.web.ui.component.grid.GridView;
import com.prototype.vaadin.web.ui.component.window.Dialog;
import com.prototype.vaadin.web.util.UIUtils;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Scope("prototype")
@Component
public class NativeSqlQuery extends FunctionBasicView {

	private CustomGridLayout formLayout = new CustomGridLayout();
    private VerticalLayout gridviewLayout = new VerticalLayout();
    
    private ComboBox dataSource = new ComboBox();
	private Button doQueryBtn = new Button("doQuery");
	private CheckBox bindItemClick = new CheckBox();
	
	private TextArea sqlTextArea = new TextArea(); 
	
	@Autowired
	private SystemFacade systemFacade;

	@Autowired
	SessionInfo sessionInfo;

	@Override
	protected void createFunctionLayout() {
		
		formLayout.setColumns(4);
		
		doQueryBtn.setIcon(FontAwesome.SEARCH);
		
		dataSource.setInvalidAllowed(false);
		dataSource.setNullSelectionAllowed(false);
		for (DataSource value : Arrays.asList(DataSource.values())) {
			dataSource.addItem(value);
		}
		
		sqlTextArea.setRows(10);
		sqlTextArea.setSizeFull();

		formLayout.addComponent("bindItemClick", bindItemClick);
		formLayout.addComponent("dataSource", dataSource);
		formLayout.addComponent("set your sql text here", sqlTextArea);
		formLayout.addComponent("set your sql text here", doQueryBtn);
		
		formLayout.setColspan(sqlTextArea, 3);
		
		addQueryBtnClick();

		layout.addComponent(formLayout.create());
		layout.addComponent(gridviewLayout);
    	setContent(layout);
	}

	private void addQueryBtnClick() {
		doQueryBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				gridviewLayout.removeAllComponents();
				
				String sqltext = sqlTextArea.getValue();
				DataSource datasource = (DataSource) dataSource.getValue();
				
				if (StringUtils.isBlank(sqltext) || datasource == null) {
					// error message
					return;
				}

				try {
					List<String> visibleColumnLs = systemFacade.getQueryColumns(sqltext, datasource);
					List<Map<String, Object>> list = systemFacade.query(sqltext, datasource);
					
					final GridView gridView = new GridView(visibleColumnLs);
					gridView.create(list);
					
					if (bindItemClick.getValue()) {
						gridView.addItemClickListener(new ItemClickListener() {
							@Override
							public void itemClick(ItemClickEvent event) {
								String rowDataStr = JsonUtils.obj2Json(gridView.getRowData(event.getItemId()));
								
								final Dialog dialog = new Dialog("row data");

						        VerticalLayout content = new VerticalLayout();
						        final TextArea textArea = new TextArea();
						        textArea.setSizeFull();
						        textArea.setValue(rowDataStr);
						        textArea.setRows(15);
						        content.addComponent(textArea);
								dialog.setContent(content);
								dialog.setResizable(true);
								dialog.create();
								dialog.setWidth("500");
							}
						});	
					}
					
					gridviewLayout.addComponent(gridView);
				} catch (Exception e) {
					UIUtils.showException(e);
				}
			}
		});
	}

}
