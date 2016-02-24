package com.prototype.vaadin.core.busniess.service;

import java.util.List;
import java.util.Map;

import com.prototype.vaadin.core.Context.DataSource;
import com.prototype.vaadin.web.model.MenuPojo;

public interface ISystemService {

	List<MenuPojo> getMenuPojoLs() throws Exception;

	boolean login(String account, String passowrd);

	List<Map<String, Object>> query(String sqltext, DataSource datasource) throws Exception;

	List<String> getQueryColumns(String sqltext, DataSource datasource) throws Exception;
	
}
