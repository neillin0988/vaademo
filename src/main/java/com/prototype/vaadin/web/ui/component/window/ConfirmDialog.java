package com.prototype.vaadin.web.ui.component.window;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;

/**
 * @author neil.lin
 */
@SuppressWarnings("serial")
public class ConfirmDialog extends Dialog {

	private Button cancelBtn;
	
	public ConfirmDialog(String title) {
		this(title, "do something...do something...do something...do something...do something...do something...do something...do something...");
	}
	
	public ConfirmDialog(String title, String message) {
		super(title, message);

		addCancelButton();
	}
	
	private void addCancelButton() {
		cancelBtn = new Button("取消");
		cancelBtn.setIcon(FontAwesome.TIMES);
	    
		addDialogFootButton(cancelBtn);
	}
	
	public void addCancelBtnClickEvent(ClickListener listener) {
		cancelBtn.addClickListener(listener);
	}
}
