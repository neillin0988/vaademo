package com.prototype.vaadin.web.util;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.server.VaadinServlet;

public class SpringContextHelper {

    private static ApplicationContext context;
    
    public static Object getBean(String beanRef) {
        return getContext().getBean(beanRef);
    }
    
	public static <T> T getBean(Class<T> clz) {
        return getContext().getBean(clz);
    }
	
	private static ApplicationContext getContext() {
		if (context == null) {
			ServletContext servletContext = VaadinServlet.getCurrent().getServletContext();
	        context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		}
		
		return context;
	}
    
}