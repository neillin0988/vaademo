package com.prototype.vaadin.web.ui.component.grid;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.prototype.vaadin.core.Context.DataSource;
import com.prototype.vaadin.core.busniess.facade.SystemFacade;
import com.prototype.vaadin.core.util.db.Condition;
import com.prototype.vaadin.core.util.db.Condition.ConditionOper;
import com.prototype.vaadin.core.util.db.EntityUtils;
import com.prototype.vaadin.core.util.db.Restriction;
import com.prototype.vaadin.core.util.db.SqlTextUtil;
import com.prototype.vaadin.web.ui.component.field.ConditionRow;
import com.prototype.vaadin.web.util.SpringContextHelper;
import com.prototype.vaadin.web.util.UIUtils;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class CriteriaView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	
	private VerticalLayout root = new VerticalLayout();
	private VerticalLayout criteriaLayout = new VerticalLayout();
	private VerticalLayout gridviewLayout = new VerticalLayout();
	private VerticalLayout conditionDiv = new VerticalLayout();
	private HorizontalLayout toolBarDiv;

	private Class<?> entity; // grid entity
	private DataSource dataSource;
	private String queryText;
	private List<String> visibleColumnLs; // grid 欄位順序

	private int pageLength = 10; // items per page

	private SystemFacade systemFacade = SpringContextHelper.getBean(SystemFacade.class);

	private boolean addSerialColumn;
	private boolean addCheckboxColumn;

	/**
	 * create grid view
	 * 
	 * @return
	 */
	public VerticalLayout createView() {
		criteriaLayout = createCriteria();
		
		gridviewLayout.addComponent(createGridView(null));

		root.addComponent(criteriaLayout);
		root.addComponent(gridviewLayout);

		root.setMargin(true);

		return root;
	}

	/**
	 * create criteria form entity
	 * 
	 * @return
	 */
	private VerticalLayout createCriteria() {
		VerticalLayout criteria = new VerticalLayout();
		
		toolBarDiv = createCriteriaToolBar();

		criteria.addComponent(conditionDiv);
		criteria.addComponent(toolBarDiv);

		return criteria;
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("serial")
	private HorizontalLayout createCriteriaToolBar() {
		HorizontalLayout criteriaToolBar = new HorizontalLayout();
		criteriaToolBar.setSizeUndefined();
		criteriaToolBar.setSpacing(true);

		// Create a button
		Button addBtn = new Button("Add");
		Button applyBtn = new Button("Apply");
		Button resetBtn = new Button("Reset");

		addBtn.setIcon(FontAwesome.PLUS);
		applyBtn.setIcon(FontAwesome.CHECK);
		resetBtn.setIcon(FontAwesome.REFRESH);

		criteriaToolBar.addComponent(addBtn);
		criteriaToolBar.addComponent(applyBtn);
		criteriaToolBar.addComponent(resetBtn);

		addBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addBtnClick(false);
			}
		});
		addBtnClick(true);

		resetBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				conditionDiv.removeAllComponents();
			}
		});

		applyBtn.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				int cnt = conditionDiv.getComponentCount();

				// get condition
				Condition condition = new Condition();
				for (int i = 0; i < cnt; i++) {
					ConditionRow conditionRow = (ConditionRow) conditionDiv.getComponent(i);

					ConditionOper oper = conditionRow.getConditionOper();
					Restriction restriction = conditionRow.getRestriction();

					condition.add(oper, restriction);
				}

				// get sql text
				String sqltext = "";
				if (StringUtils.isBlank(queryText)) {
					String tableName = EntityUtils.getTableName(entity);
					sqltext = SqlTextUtil.getQueryScript(tableName, condition);
				} else {
					sqltext = sqltext.replace("${codition}", condition.toString());
				}
				
				try {
					// 清空 全部重畫
					gridviewLayout.removeAllComponents();
					
					List<Map<String, Object>> list = systemFacade.query(sqltext, dataSource);

					gridviewLayout.addComponent(createGridView(list));
				} catch (Exception e) {
					UIUtils.showException(e);
				}
			}
		});

		return criteriaToolBar;
	}
	
	private Component createGridView(List<Map<String, Object>> list) {
//		GridViewCheckBoxSample gridView = new GridViewCheckBoxSample(list, visibleColumnLs);
		
		GridView gridView = new GridView(visibleColumnLs);
		gridView.setAddSerialColumn(addSerialColumn);
		gridView.setAddCheckboxColumn(addCheckboxColumn);
		gridView.create(list);
		
		return gridView;
	}

	protected void addBtnClick(boolean firstClick) {
		ConditionRow conditionRow = new ConditionRow();
		conditionRow.setEntity(entity);
		conditionRow.setConditionDiv(conditionDiv);

		conditionDiv.addComponent(conditionRow.create());
	}

	public Class<?> getEntity() {
		return entity;
	}

	public void setEntity(Class<?> entity) {
		this.entity = entity;
	}

	public int getPageLength() {
		return pageLength;
	}

	public void setPageLength(int pageLength) {
		this.pageLength = pageLength;
	}

	public String getQueryText() {
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<String> getVisibleColumnLs() {
		return visibleColumnLs;
	}

	public void setVisibleColumnLs(List<String> visibleColumnLs) {
		this.visibleColumnLs = visibleColumnLs;
	}

	public void setAddSerialColumn(boolean addSerialColumn) {
		this.addSerialColumn = addSerialColumn;
	}

	public void setAddCheckboxColumn(boolean addCheckboxColumn) {
		this.addCheckboxColumn = addCheckboxColumn;
	}
}
