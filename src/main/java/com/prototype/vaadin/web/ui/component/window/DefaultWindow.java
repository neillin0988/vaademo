package com.prototype.vaadin.web.ui.component.window;

import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * @author neil.lin
 */
@SuppressWarnings("serial")
public abstract class DefaultWindow extends Window {
	
	public DefaultWindow(String title) {
		super(title);

        center();
        setModal(true);
	}
	
	public void create() {
	    UI.getCurrent().addWindow(this);
	}
}
