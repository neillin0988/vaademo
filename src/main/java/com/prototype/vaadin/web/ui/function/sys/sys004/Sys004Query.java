package com.prototype.vaadin.web.ui.function.sys.sys004;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prototype.vaadin.core.Context.DataSource;
import com.prototype.vaadin.core.entity.BlrsFlowSetting;
import com.prototype.vaadin.web.ui.component.FunctionToolBarView;
import com.prototype.vaadin.web.ui.component.grid.CriteriaView;

@SuppressWarnings("serial")
@Scope("prototype")
@Component
public class Sys004Query extends FunctionToolBarView {

	@Override
	protected void createFunctionLayout() {
		List<String> visibleColumnLs = new ArrayList<String>();
		visibleColumnLs.add(BlrsFlowSetting.SRVID);
		visibleColumnLs.add(BlrsFlowSetting.SRCDEVICE);
		visibleColumnLs.add(BlrsFlowSetting.ITEMCODE);
		visibleColumnLs.add(BlrsFlowSetting.FLOWDEFINE);
		visibleColumnLs.add(BlrsFlowSetting.CREATEUSER);
		visibleColumnLs.add(BlrsFlowSetting.CREATEDATE);
		
		CriteriaView criteriaview = new CriteriaView();
		criteriaview.setEntity(BlrsFlowSetting.class);
		criteriaview.setVisibleColumnLs(visibleColumnLs);
		criteriaview.setDataSource(DataSource.DS_Payment);
		criteriaview.setAddSerialColumn(true);
		
		createCriteriaPanel(criteriaview);		
	}

}
