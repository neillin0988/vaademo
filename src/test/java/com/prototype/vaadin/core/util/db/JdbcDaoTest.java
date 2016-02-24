package com.prototype.vaadin.core.util.db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.junit.Test;

import com.prototype.vaadin.BasicTest;
import com.prototype.vaadin.core.Context.DataSource;
import com.prototype.vaadin.core.entity.Menu;
import com.prototype.vaadin.core.util.JsonUtils;

public class JdbcDaoTest extends BasicTest {
	
	@Test
	public void query() throws Exception {
		Connection conn = ConnectionUtils.getConnectionxxxxxx(DataSource.postgres);
		System.out.println(conn);
		
		JdbcDao dao = new JdbcDao(conn);
		Object[] params = new Object[] {
			"1"	
		};
		List<Map<String, Object>> menuLs = dao.query("select * from menu where sort = ?", params);
		
		System.out.println(JsonUtils.obj2Json(menuLs));
	}
	
	@Test
	public void queryEntity() throws Exception {
		Connection conn = ConnectionUtils.getConnectionxxxxxx(DataSource.postgres);
		System.out.println(conn);
		
		JdbcDao dao = new JdbcDao(conn);
		List<Menu> menuLs = dao.query(Menu.class, "select * from menu");
		
		System.out.println(JsonUtils.obj2Json(menuLs));
	}
	
	@Test
	public void deleteEntity() throws Exception {
		Connection conn = ConnectionUtils.getConnectionxxxxxx(DataSource.postgres);
		System.out.println(conn);
		

		Menu entity = new Menu();
		entity.setUuid("28eb58f8-8cd5-4d30-ba4d-1c536ea11933");
		
		JdbcDao dao = new JdbcDao(conn);
		dao.delete(entity);
	}

	@Test
	public void insertEntity() throws Exception {
		Connection conn = ConnectionUtils.getConnectionxxxxxx(DataSource.postgres);
		System.out.println(conn);
		
		Menu entity = new Menu();
		entity.setUuid(UUID.randomUUID().toString());
		entity.setComponent("component");
		entity.setCreatedate(new Date());
		entity.setCreator("creator");
		entity.setEditor("editor");
		entity.setIsalive("Y");
		entity.setKey("key");
		entity.setName("name");
		entity.setParentkey(null);
//		entity.setSort((short) 2);
		
		JdbcDao dao = new JdbcDao(conn);
		dao.insert(entity);	
	}
	
	@Test
	public void batchInsertEntityxxxxxxxxxxxx() throws Exception {
		Connection conn = ConnectionUtils.getConnectionxxxxxx(DataSource.postgres);
		System.out.println(conn);

		List<Object> entityLs = new ArrayList<Object>();
		for (int i = 0; i < 1000; i++) {
			Menu entity = new Menu();
			entity.setUuid(UUID.randomUUID().toString());
			entity.setComponent("component");
			entity.setCreatedate(new Date());
			entity.setCreator("creator");
			entity.setEditdate(new Date());
			entity.setEditor("editor");
			entity.setIsalive("Y");
			entity.setKey("key");
			entity.setName("name");
			entity.setParentkey(null);
//			entity.setSort((short) 2);
			
			entityLs.add(entity);
		}

		JdbcDao dao = new JdbcDao(conn);
		dao.insert(entityLs);
	}
	
	@Test
	public void batchInsertEntity() throws Exception {
		Connection conn = ConnectionUtils.getConnectionxxxxxx(DataSource.postgres);
		System.out.println(conn);

		List<Object[]> paramList = new ArrayList<Object[]>();
		for (int i = 0; i < 1000; i++) {
			paramList.add(new Object[] {
					"component",
					new Date(),
					"creator",
					new Date(),
					"editor",
					"Y",
					"key",
					"name",
					null,
					null,
					UUID.randomUUID().toString()
			});
		}
		
		Set<String> columns = new TreeSet<String>();
		columns.add(Menu.COMPONENT);
		columns.add(Menu.CREATEDATE);
		columns.add(Menu.CREATOR);
		columns.add(Menu.EDITDATE);
		columns.add(Menu.EDITOR);
		columns.add(Menu.ISALIVE);
		columns.add(Menu.KEY);
		columns.add(Menu.NAME);
		columns.add(Menu.PARENTKEY);
		columns.add(Menu.SORT);
		columns.add(Menu.UUID);
		
		Class<?> clz = Menu.class;
		String tableNm = EntityUtils.getTableName(clz);
		String executeScript = SqlTextUtil.getInsertScript(tableNm, columns);
		String queryScript = SqlTextUtil.getQueryScript(tableNm, columns, new Condition());

		JdbcDao dao = new JdbcDao(conn);
		dao.batchExecuteUpdate(executeScript, paramList, queryScript);
	}

	@Test
	public void updateEntity() throws Exception {
		Menu entity = new Menu();
		entity.setUuid("025ac2c3-4395-4ca5-94a3-e7dc466a8855");
		entity.setComponent("123");
		entity.setCreatedate(new Date());
		entity.setCreator("creator");
		entity.setEditdate(new Date());
		entity.setEditor("xxxeditor");
		entity.setIsalive("N");
		entity.setName("new xxx");
		entity.setParentkey(null);
		entity.setKey("keyddd");
		
		Set<String> columns = new TreeSet<String>();
		columns.add(Menu.COMPONENT);
		columns.add(Menu.NAME);
		
		Connection conn = ConnectionUtils.getConnectionxxxxxx(DataSource.postgres);
		JdbcDao dao = new JdbcDao(conn);
		dao.update(entity, columns);
//		dao.update(entity);	
	}
	
	@Test
	public void updateEntity222() throws Exception {
		String executeScript = "UPDATE public.menu SET component = ?,name = ? WHERE uuid = ?";
		Object[] params = {"123", "new ccccccc", "025ac2c3-4395-4ca5-94a3-e7dc466a8855"};
		String queryScript = "SELECT component,name FROM public.menu WHERE 1 != 1 ";

		Connection conn = ConnectionUtils.getConnectionxxxxxx(DataSource.postgres);
		JdbcDao dao = new JdbcDao(conn);
		dao.executeUpdate(executeScript, params, queryScript);
	}
	
}
