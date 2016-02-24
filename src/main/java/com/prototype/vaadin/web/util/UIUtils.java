package com.prototype.vaadin.web.util;

import com.prototype.vaadin.core.Context.Message;
import com.prototype.vaadin.core.util.ExceptionUtils;
import com.prototype.vaadin.web.ui.MyVaadinUI;
import com.prototype.vaadin.web.ui.MyVaadinUI.View;
import com.prototype.vaadin.web.ui.component.window.Dialog;
import com.vaadin.ui.UI;

public class UIUtils {

	public static void showView(View view) {
		MyVaadinUI MyVaadinUI = (com.prototype.vaadin.web.ui.MyVaadinUI) UI.getCurrent();
		MyVaadinUI.showView(view);
	}
	
	public static void showInfo(String message) {
	    // Create a sub-window and set the content
	    String title = Message.system_info.getValue();
	    
	    Dialog dialog = new Dialog(title, message);
	    dialog.create();
	}
	
	public static void showException(Throwable e) {
	    String title = Message.system_error.getValue();
	    String context = ExceptionUtils.parseException(e);
	    
	    Dialog dialog = new Dialog(title, context);
//	    dialog.setWidth("800");
	    dialog.setHeight("500");
	    dialog.create();
	}
}
