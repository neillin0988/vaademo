package com.prototype.vaadin.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;

import com.prototype.vaadin.core.busniess.facade.SystemFacade;

@SuppressWarnings("serial")
public class InitServlet extends HttpServlet {
	
	@Autowired
	SystemFacade systemFacade;

	@Override
	public void init() throws ServletException {
		try {
//			systemFacade.getMenuPojoLs();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
