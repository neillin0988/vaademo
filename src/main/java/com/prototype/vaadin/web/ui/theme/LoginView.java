package com.prototype.vaadin.web.ui.theme;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prototype.vaadin.core.busniess.facade.SystemFacade;
import com.prototype.vaadin.web.model.SessionInfo;
import com.prototype.vaadin.web.ui.MyVaadinUI;
import com.prototype.vaadin.web.ui.MyVaadinUI.View;
import com.prototype.vaadin.web.ui.component.BasicView;
import com.prototype.vaadin.web.ui.component.window.ConfirmDialog;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class LoginView extends BasicView {
	
	@Autowired
	SystemFacade systemFacade;
	
	@Autowired
	SessionInfo sessionInfo;

    @PostConstruct
    public void postConstruct() {
		setloginForm();
	}

	private void setloginForm() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        
	    FormLayout formLayout = new FormLayout();
	    formLayout.setMargin(true);
	    formLayout.setWidth("100%");
	    
		final TextField accountField = new TextField("account");
	    final TextField passowrdField = new TextField("passowrd"); 

	    formLayout.addComponent(accountField);
	    formLayout.addComponent(passowrdField);

	    layout.addComponent(formLayout);
		layout.setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);
		
	    final ConfirmDialog loginDialog = new ConfirmDialog("登入資訊");
		loginDialog.setDialogContent(layout);
		loginDialog.setClosable(false);
		loginDialog.create();
		
		loginDialog.addOkBtnClickEvent(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				String account = accountField.getValue();
				String passowrd = passowrdField.getValue();
				
				boolean loginResult = systemFacade.login(account, passowrd);
				
				if (!loginResult) {
					Notification.show("login fail","Hint : try admin/admin", Notification.Type.WARNING_MESSAGE);
					
					accountField.setValue("");
					passowrdField.setValue("");
				} else {
					sessionInfo.setAccount(account);
					
					MyVaadinUI MyVaadinUI = (com.prototype.vaadin.web.ui.MyVaadinUI) UI.getCurrent();
					MyVaadinUI.showView(View.MainView);

					loginDialog.close();
					
					Notification.show("Hello " + account, Notification.Type.WARNING_MESSAGE);
				}
			}
		});
		
		loginDialog.addCancelBtnClickEvent(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				accountField.setValue("admin");
				passowrdField.setValue("admin");
			}
		});
	}

}