package com.prototype.vaadin.core.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import com.prototype.vaadin.core.Context;
import com.prototype.vaadin.web.util.SpringContextHelper;

/**
 * Neil
 */
public class ConnectionUtils {
	
    private static String JDBC_PROPS = "jdbc";
    
    /**
     * 
     * @return
     * @throws Exception 
     */
    public static Connection getConnection(Context.DataSource jdbc) throws Exception {
         DataSource dataSource = (DataSource) SpringContextHelper.getBean(jdbc.name);
    	
        return dataSource.getConnection();
    }
    
    /**
     * 
     * @return
     * @throws Exception 
     */
    public static Connection getConnectionxxxxxx(Context.DataSource jdbc) throws Exception {
        Connection conn = null;
        
        String name = jdbc.name;
        
        ResourceBundle rb = ResourceBundle.getBundle(JDBC_PROPS);
        String driver = rb.getString("jdbc." + name + ".driver");
        String url = rb.getString("jdbc." + name + ".url");
        String username = rb.getString("jdbc." + name + ".username");
        String password = rb.getString("jdbc." + name + ".password");

        Class.forName(driver);
        conn = DriverManager.getConnection(url, username, password);

        return conn;
    }

}
