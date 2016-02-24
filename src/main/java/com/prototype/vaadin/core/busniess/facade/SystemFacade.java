package com.prototype.vaadin.core.busniess.facade;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prototype.vaadin.core.Context.DataSource;
import com.prototype.vaadin.core.busniess.service.ISystemService;
import com.prototype.vaadin.web.model.MenuPojo;

@SuppressWarnings("serial")
@Service
public class SystemFacade implements Serializable {

	@Autowired
	ISystemService systemServiceImpl;
	
	public List<MenuPojo> getMenuPojoLs() throws Exception {
		return systemServiceImpl.getMenuPojoLs();
	}

	public boolean login(String account, String passowrd) {
		return systemServiceImpl.login(account, passowrd);
	}

	public List<Map<String, Object>> query(String sqltext, DataSource datasource) throws Exception {
		return systemServiceImpl.query(sqltext, datasource); 
	}
	
	public List<String> getQueryColumns(String sqltext, DataSource datasource) throws Exception {
		return systemServiceImpl.getQueryColumns(sqltext, datasource); 
	}
	
}
