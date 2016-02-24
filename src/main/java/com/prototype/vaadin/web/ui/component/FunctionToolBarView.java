package com.prototype.vaadin.web.ui.component;

import javax.annotation.PostConstruct;

import com.prototype.vaadin.web.ui.component.grid.CriteriaView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
public abstract class FunctionToolBarView extends FunctionBasicView {

    private Panel toolbarPanel = new Panel();
    private Panel criteriaPanel = new Panel();
    
	@PostConstruct
	public void init() {
		layout.setSizeFull();
		toolbarPanel.setSizeFull();
		criteriaPanel.setSizeFull();
		
		layout.addComponent(toolbarPanel);
		layout.addComponent(criteriaPanel);
		
    	setContent(layout);

		createToolbar();
    	createFunctionLayout();
	}

	private void createToolbar() {
	    HorizontalLayout toolbar = new HorizontalLayout();
		final VerticalLayout someCmp = new VerticalLayout();
		someCmp.setSizeFull();
		toolbar.setSizeUndefined();
		toolbar.setSpacing(true);
		
		// Create a button
		Button queryBtn = new Button("Query");
		queryBtn.setId(queryBtn.getCaption());
		
		Button exportBtn = new Button("Export");
		exportBtn.setId(exportBtn.getCaption());
		
		Button createNewBtn = new Button("CreatNew");
		createNewBtn.setId(createNewBtn.getCaption());
		
		Button copyFromBtn = new Button("CopyFrom");
		copyFromBtn.setId(copyFromBtn.getCaption());
		
		Button batchUpdateBtn = new Button("Batch Update");
		batchUpdateBtn.setId(batchUpdateBtn.getCaption());
		
		// Make it look like a hyperlink
		queryBtn.addStyleName(Reindeer.BUTTON_LINK);
		exportBtn.addStyleName(Reindeer.BUTTON_LINK);
		createNewBtn.addStyleName(Reindeer.BUTTON_LINK);
		copyFromBtn.addStyleName(Reindeer.BUTTON_LINK);
		batchUpdateBtn.addStyleName(Reindeer.BUTTON_LINK);
		
		toolbar.addComponent(queryBtn);		
		toolbar.addComponent(exportBtn);		
		toolbar.addComponent(createNewBtn);		
		toolbar.addComponent(copyFromBtn);	
		toolbar.addComponent(batchUpdateBtn);

		toolbarPanel.setContent(toolbar);
	}
	
	protected void createCriteriaPanel(CriteriaView criteriaView) {
		this.criteriaPanel.setContent(criteriaView.createView());
	}
}