package com.prototype.vaadin.web.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prototype.vaadin.web.model.SessionInfo;
import com.prototype.vaadin.web.util.SpringContextHelper;
import com.prototype.vaadin.web.util.UIUtils;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme") // bootstrap, mytheme
@SuppressWarnings("serial")
@Component
@Scope("prototype")
@PreserveOnRefresh
public class MyVaadinUI extends UI {
	
	public enum View {
		LoginView("loginView"),
		MainView("mainView"),
        ;
        
        public String value;
    	public String getValue() {
    		return value;
    	}
    	
        private View(String value) {
            this.value = value;
        }
    }
	
	@Autowired
	SessionInfo sessionInfo;
	
	@Override
	protected void init(VaadinRequest request) {
		Object user = sessionInfo.getAccount();
		if (user == null) {
			showView(View.LoginView);
		} else {
			showView(View.MainView);
		}
		
		// Configure the error handler for the UI
		UI.getCurrent().setErrorHandler(new DefaultErrorHandler() {
		    @Override
		    public void error(com.vaadin.server.ErrorEvent event) {
		        Throwable e = event.getThrowable().getCause();
		        
		        UIUtils.showException(e);
		    } 
		});
	}
	
	public void showView(View view) {
		final VerticalLayout layout = new VerticalLayout();
		setContent(layout);
		
		layout.addComponent((com.vaadin.ui.Component) SpringContextHelper.getBean(view.getValue()));
	}

}
