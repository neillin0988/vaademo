package com.prototype.vaadin.core.busniess.service.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.prototype.vaadin.core.Context.DataSource;
import com.prototype.vaadin.core.busniess.service.ISystemService;
import com.prototype.vaadin.core.util.ResourceFileUtil;
import com.prototype.vaadin.core.util.db.ConnectionUtils;
import com.prototype.vaadin.core.util.db.JdbcDao;
import com.prototype.vaadin.web.model.MenuPojo;

@SuppressWarnings("serial")
@Service
public class SystemServiceImpl implements ISystemService, Serializable {

	@Override
	public List<MenuPojo> getMenuPojoLs() throws Exception {
		List<MenuPojo> result = new ArrayList<MenuPojo>();
		
		List<MenuPojo> tempResult = new ArrayList<MenuPojo>();
		Map<String, MenuPojo> menuMp = new HashMap<String, MenuPojo>(); 

		Connection conn = ConnectionUtils.getConnection(DataSource.postgres);
		JdbcDao dao = new JdbcDao(conn);
		
		String sqltext = ResourceFileUtil.SQL.getResource("system", "getMenuPojoLs");
		List<Map<String, Object>> dataLs = dao.query(sqltext);
		
		for (Map<String, Object> data : dataLs) {
			String key = MapUtils.getString(data, "key");
			String name = MapUtils.getString(data, "name");
			String component = MapUtils.getString(data, "component");
			String parent = MapUtils.getString(data, "parentkey");
			
			MenuPojo pojo = new MenuPojo(name);
			pojo.setKey(key);
			pojo.setName(name);
			pojo.setParent(parent);
			
			if (!StringUtils.isBlank(component)) {
				pojo.setComponentId(component);
			}
			
			tempResult.add(pojo);
			menuMp.put(key, pojo);
		}
		
		// 將各個 menu 分配到各自的 parent 底下
		for (MenuPojo pojo : tempResult) {
			String parent = pojo.getParent();
			
			if (StringUtils.isBlank(parent)) {
				// 直接當作第一層
				result.add(pojo);
			} else {
				// 若 parent 存在於 mp 中
				if (menuMp.containsKey(parent)) {
					MenuPojo parentPojo = menuMp.get(parent);
					parentPojo.addSubMenu(pojo);
				}	
			}
		}
		
		tempResult = new ArrayList<MenuPojo>(result);
		result = new ArrayList<MenuPojo>();
		
		// 移除空連結
		for (MenuPojo pojo : tempResult) {
			// 若menu 不含子menu 且 沒有對應　ComponentId, 則不放入result, 反之 放入result
			if (!(!pojo.hasSubMenu() && StringUtils.isBlank(pojo.getComponentId()))) {
				result.add(pojo);
			}
		}
		
		return result;
	}

	@Override
	public boolean login(String account, String passowrd) {
		return "admin".equals(account) && "admin".equals(passowrd);
	}


	@Override
	public List<Map<String, Object>> query(String sqltext, DataSource datasource) throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		
		try {
			conn = ConnectionUtils.getConnection(datasource);
			JdbcDao dao = new JdbcDao(conn);
			
			result = dao.query(sqltext);
		} catch (Exception e) {
			throw e; 		
		} finally {
			if (conn != null) {
				conn.close();		
			}
		}
		
		return result;
	}

	@Override
	public List<String> getQueryColumns(String sqltext, DataSource datasource) throws Exception {
		List<String> result = new ArrayList<String>();
		Connection conn = null;
		
		try {
			conn = ConnectionUtils.getConnection(datasource);
			JdbcDao dao = new JdbcDao(conn);
			
			ResultSetMetaData metaData = dao.getResultSetMetaData(conn, sqltext);
			int colSize = metaData.getColumnCount();
			for (int i = 1; i <= colSize; i++) {
				result.add(metaData.getColumnLabel(i));
			}
		} catch (Exception e) {
			throw e; 		
		} finally {
			if (conn != null) {
				conn.close();		
			}
		}
		
		return result;
	}
}
