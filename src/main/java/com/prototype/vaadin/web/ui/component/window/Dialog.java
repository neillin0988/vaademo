package com.prototype.vaadin.web.ui.component.window;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * @author neil.lin
 */
@SuppressWarnings("serial")
public class Dialog extends DefaultWindow {

	private VerticalLayout layout = new VerticalLayout();
	private Panel content = new Panel();
	private HorizontalLayout foot = new HorizontalLayout();
	
	private Button okBtn;
	private HorizontalLayout btnLayout;
	
	public Dialog(String title) {
        this(title, "do something...do something...do something...do something...do something...do something...do something...do something...");
	}
	
	public Dialog(String title, String message) {
		super(title);

		layout.addComponent(content);
		layout.addComponent(foot);
		layout.setMargin(true);
		layout.setSpacing(true);
		
		setDialogContent(new Label(message, ContentMode.HTML));
		setDialogFoot();
		
		setResizable(false);
        setContent(layout);
	}
	
	public void addDialogFootButton(Button btn) {
		btnLayout.addComponent(btn);
	}
	
	protected void setDialogFoot() {
		btnLayout = new HorizontalLayout();
		okBtn = new Button("確定");
		okBtn.setIcon(FontAwesome.CHECK);

		btnLayout.addComponent(okBtn);
		btnLayout.setSizeUndefined();
		btnLayout.setSpacing(true);
		
		foot.setWidth("100%");
		foot.addComponent(btnLayout);
		foot.setComponentAlignment(btnLayout, Alignment.MIDDLE_RIGHT);
	}
	
	public void addOkBtnClickEvent(ClickListener listener) {
		okBtn.addClickListener(listener);
	}

	public void setDialogContent(Component content) {
		this.content.setContent(content);
	}

	public Button getOkBtn() {
		return okBtn;
	}

	public void setOkBtn(Button okBtn) {
		this.okBtn = okBtn;
	}
	
	

}
