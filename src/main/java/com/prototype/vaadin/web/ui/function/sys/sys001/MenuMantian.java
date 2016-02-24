package com.prototype.vaadin.web.ui.function.sys.sys001;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prototype.vaadin.core.Context.DataSource;
import com.prototype.vaadin.core.entity.Menu;
import com.prototype.vaadin.web.ui.component.FunctionToolBarView;
import com.prototype.vaadin.web.ui.component.grid.CriteriaView;

@SuppressWarnings("serial")
@Scope("prototype")
@Component
public class MenuMantian extends FunctionToolBarView {
	
	@Override
	public void createFunctionLayout() {
		List<String> visibleColumnLs = new ArrayList<String>();
		visibleColumnLs.add(Menu.KEY);          
		visibleColumnLs.add(Menu.PARENTKEY);     
		visibleColumnLs.add(Menu.NAME);          
		visibleColumnLs.add(Menu.ISALIVE);       
		visibleColumnLs.add(Menu.SORT);          
		visibleColumnLs.add(Menu.COMPONENT); 
		
		CriteriaView criteriaview = new CriteriaView();
		criteriaview.setEntity(Menu.class);
		criteriaview.setVisibleColumnLs(visibleColumnLs);
		criteriaview.setDataSource(DataSource.postgres);
		criteriaview.setAddCheckboxColumn(true);
		
		createCriteriaPanel(criteriaview);		
	}

}
