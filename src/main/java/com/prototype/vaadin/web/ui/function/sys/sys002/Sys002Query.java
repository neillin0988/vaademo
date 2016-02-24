package com.prototype.vaadin.web.ui.function.sys.sys002;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prototype.vaadin.core.Context.DataSource;
import com.prototype.vaadin.core.entity.Logmsg;
import com.prototype.vaadin.web.ui.component.FunctionToolBarView;
import com.prototype.vaadin.web.ui.component.grid.CriteriaView;

@SuppressWarnings("serial")
@Scope("prototype")
@Component
public class Sys002Query extends FunctionToolBarView {
	
	@Override
	protected void createFunctionLayout() {
		CriteriaView criteriaView = getCriteriaView();

		createCriteriaPanel(criteriaView);
	}

	/**
	 * 
	 * @return
	 */
	private CriteriaView getCriteriaView() {
		List<String> visibleColumnLs = new ArrayList<String>();
		visibleColumnLs.add(Logmsg.GUID);     
		visibleColumnLs.add(Logmsg.LOGWHO);   
		visibleColumnLs.add(Logmsg.LOGWHEN);  
		visibleColumnLs.add(Logmsg.LOGWHERE); 
		visibleColumnLs.add(Logmsg.LOGWHAT);  
		visibleColumnLs.add(Logmsg.LOGMSG);
		
		CriteriaView criteriaview = new CriteriaView();
		criteriaview.setEntity(Logmsg.class);
		criteriaview.setVisibleColumnLs(visibleColumnLs);
		criteriaview.setDataSource(DataSource.postgres);
		criteriaview.setAddCheckboxColumn(true);
		criteriaview.setAddSerialColumn(true);
		
		return criteriaview;
	}

}
