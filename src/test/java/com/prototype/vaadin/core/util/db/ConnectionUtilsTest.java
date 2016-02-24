package com.prototype.vaadin.core.util.db;

import java.sql.Connection;

import org.junit.Test;

import com.prototype.vaadin.SpringTest;
import com.prototype.vaadin.core.Context.DataSource;

public class ConnectionUtilsTest extends SpringTest {

	@Test
	public void getConnection() throws Exception {
		Connection conn = ConnectionUtils.getConnectionxxxxxx(DataSource.postgres);
		
		System.out.println(conn);
	}

}
