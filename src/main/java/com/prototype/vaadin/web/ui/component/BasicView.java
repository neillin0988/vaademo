package com.prototype.vaadin.web.ui.component;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class BasicView extends Panel {
	
    private VerticalLayout layout = new VerticalLayout();;
    
	private VerticalLayout headLayout = new VerticalLayout();
	private VerticalLayout bodyLayout = new VerticalLayout();

	public BasicView() {
    	layout.addComponent(headLayout);
    	layout.addComponent(bodyLayout);
    	
    	// set headLayout
		Image image = new Image(null, new ThemeResource("../../img/logo.png"));
		image.setSizeFull();
		headLayout.addComponent(image);
		
		int browserWindowHeight = UI.getCurrent().getPage().getBrowserWindowHeight();
		int headHeight = 50;
		int bodyHeight = browserWindowHeight - headHeight - 3;
		
		headLayout.setHeight(String.valueOf(headHeight));
		bodyLayout.setHeight(String.valueOf(bodyHeight));
		
    	setContent(layout);
	}

    public VerticalLayout getBodyLayout() {
		return bodyLayout;
	}

	public VerticalLayout getHeadLayout() {
		return headLayout;
	}

}