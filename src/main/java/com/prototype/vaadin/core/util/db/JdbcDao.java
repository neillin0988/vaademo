package com.prototype.vaadin.core.util.db;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;

/**
 * Neil
 */
public class JdbcDao {
	
	private final String GET = "get";
	private final String SET = "set";
	
	private int fetchSize = 2000; 

	private Connection conn;
	
    private int bufferSize = 200; 
	private boolean debug = false;
    
	public JdbcDao(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public int insert(Object entity) throws Exception {
		Class<?> clz = entity.getClass();
		String tableNm = EntityUtils.getTableName(clz);
		
		Map<String, Object> columnMp = EntityUtils.getValuesMp(entity);
		Set<String> columns = columnMp.keySet();

		// params
		Object[] params = columnMp.values().toArray();

		// executeScript
		String executeScript = SqlTextUtil.getInsertScript(tableNm, columns);
		
		// queryScript
		String queryScript = SqlTextUtil.getQueryScript(tableNm, columns, new Condition());
		
		return executeUpdate(executeScript, params, queryScript);
	}
	
	
	/**
	 * update entity 全部欄位
	 * 
	 * @param entity
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public int update(Object entity) throws Exception {
		Map<String, Object> nonIdMp = EntityUtils.getNonIdValues(entity);
		Set<String> columns = nonIdMp.keySet();
		
		return update(entity, columns);
	}
	
	/**
	 * update entity 指定欄位
	 * 
	 * @param entity
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public int update(Object entity, Set<String> columns) throws Exception {
		Class<?> clz = entity.getClass();
		String tableNm = EntityUtils.getTableName(clz);
		
		Object[] params = EntityUtils.getUpdateParams(entity, columns); 
		
		// executeScript
		String executeScript = EntityUtils.getUpdateScript(clz, columns);
		
		// queryScript
		String queryScript = SqlTextUtil.getQueryScript(tableNm, columns, new Condition());
		
		return executeUpdate(executeScript, params, queryScript);
	}
	
	/**
	 * delete entity
	 * 
	 * @param entity
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public int delete(Object entity) throws Exception {
		Class<?> clz = entity.getClass();
		String tableNm = EntityUtils.getTableName(clz);
		
		Map<String, Object> idMp = EntityUtils.getIdValues(entity);
		Object[] params = idMp.values().toArray(); 
		
		// executeScript
		Condition idCondition = new Condition();
		for (String idColumn : idMp.keySet()) {
			idCondition.and(idColumn + " = ?");
		}
		String executeScript = SqlTextUtil.getDeleteScript(tableNm, idCondition);
		
		// queryScript
		return executeUpdate(executeScript, params, "");
	}
	
	/**
	 * 
	 * @param refEntity refrence entity class
	 * @param sqltext sql for query
	 * @return
	 */
	public <T> List<T> query(Class<T> refEntity, String sqltext) {
		printSql(sqltext);
		List<T> dataList = new ArrayList<T>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sqltext);
			ResultSetMetaData rsmd = pstmt.getMetaData();
			
			rs = pstmt.executeQuery();
			
			int colSize = rsmd.getColumnCount();
            Method[] entyMethods = refEntity.getMethods();
            Map<String, Method> rsMethodMp = new HashMap<String, Method>(); 
            
            for (Method method : entyMethods) {
    			if (method.getAnnotation(Column.class) != null) {
    				String column = method.getAnnotation(Column.class).name();
    				String setterName = method.getName().replace(GET, SET);

					rsMethodMp.put(column, refEntity.getMethod(setterName, method.getReturnType()));
    			}
    		}
            
            Class<?> rsClazz = rs.getClass();
            Method[] rsMethods = rsClazz.getMethods();
            Map<String, MethodCache> tmMp = new HashMap<String, MethodCache>();
            
			while (rs.next()) {
                T entity = (T) refEntity.newInstance();
                
                // each column
                for (int i = 1; i <= colSize; i++) {
                    String columnName = rsmd.getColumnName(i);
                    
                    MethodCache tm = new MethodCache();
                    if (tmMp.containsKey(columnName)) {
                    	tm = tmMp.get(columnName);
                    } else {
                        Method setterMethod = rsMethodMp.get(columnName);
                		
                        Class<?> rtnFieldType = setterMethod.getParameterTypes()[0];
                        setterMethod.setAccessible(true);

                        for (Method rsMth : rsMethods) {
                            String rsMethName = rsMth.getName();
                            rsMethName = rsMethName.substring(3);

                            if (rsMethName.equals(rtnFieldType.getSimpleName())) {
                                String rsOpMethName = GET + rsMethName;
                                Method rsMethod = rsClazz.getMethod(rsOpMethName, new Class[] { int.class });
                                
                                tm.setSetterMethod(setterMethod);
                                tm.setRsMethod(rsMethod);
                                
                                tmMp.put(columnName, tm);
                            }
                        }                    	
                    }
                    
                    tm.getSetterMethod().invoke(entity, new Object[] { (tm.getRsMethod().invoke(rs, new Object[] { i })) }); // 映射
                }

                dataList.add(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}

		return dataList;
	}
	
	/**
	 * 
	 * @param sqltext
	 * @return
	 * @throws SQLException 
	 */
	public List<Map<String, Object>> query(String sqltext) throws SQLException {
		printSql(sqltext);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			stmt.setFetchSize(fetchSize);
			rs = stmt.executeQuery(sqltext);

			ResultSetMetaData rsmd = rs.getMetaData();
			
			int colSize = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				Map<String, Object> data = new LinkedHashMap<String, Object>();

				for (int i = 1; i <= colSize; i++) {
					data.put(rsmd.getColumnLabel(i), rs.getObject(i));
				}
				result.add(data);
			}
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
		}

		return result;
	}
	
	public List<Map<String, Object>> query(String sqltext, Object[] params) throws SQLException {
		printSql(sqltext);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sqltext);
			
			if (params != null && params.length != 0) {
                for (int i = 0; i < params.length; i++) {
                	Object obj = params[i];
                	int rsIndex = i + 1;

                	pstmt.setObject(rsIndex, obj);
                }
            }
			
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int colSize = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				Map<String, Object> data = new LinkedHashMap<String, Object>();

				for (int i = 1; i <= colSize; i++) {
					data.put(rsmd.getColumnLabel(i), rs.getObject(i));
				}
				result.add(data);
			}
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}

		return result;
	}
	
	
    /**
     * 
     * @param target
     * @param sqltext
     * @param paramList
     * @return int
     * @throws Exception
     */
    public int batchExecuteUpdate(String executeScript, List<Object[]> paramList, String queryScript) throws Exception {
		printSql(executeScript);
		
        int count = 0; 
		PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(executeScript);
			ResultSetMetaData metaData = getResultSetMetaData(conn, queryScript);
            pstmt.clearBatch();

            int process = 0;

            for (Object[] params : paramList) {
                for (int i = 0; i < params.length; i++) {
                	int rsIndex = i + 1;
                	Object obj = params[i];
                	
                	if (obj != null) {
                		if (obj instanceof Date) {
                			pstmt.setDate(rsIndex, new java.sql.Date(((Date) obj).getTime()));
                		} else {
                        	pstmt.setObject(rsIndex, obj);	
                		}
                	} else {
                    	if (metaData != null) {
	                		pstmt.setNull(rsIndex, metaData.getColumnType(rsIndex));	
	            		} else {
	                    	pstmt.setNull(rsIndex, Types.NULL);
	            		}
                	}
                }
                pstmt.addBatch();
                process++;
                count++;

                if (process == bufferSize) {
                    pstmt.executeBatch();
                    pstmt.clearBatch();
                    process = 0;
                }
            }

            if (process != 0) {
                pstmt.executeBatch();
                pstmt.clearBatch();
                process = 0;
            }

            return count;
        } finally {
            if (paramList != null) {
            	paramList.clear();
            	paramList = null;
            }
            
            closeStatement(pstmt);
        }
    }
	
	public int executeUpdate(String executeScript, Object[] params, String queryScript) throws Exception {
		printSql(executeScript);
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(executeScript);
			ResultSetMetaData metaData = getResultSetMetaData(conn, queryScript);
			
            if (params != null && params.length != 0) {
                for (int i = 0; i < params.length; i++) {
                	Object obj = params[i];
                	int rsIndex = i + 1;
                	
                	if (obj != null) {
                		if (obj instanceof Date) {
                			pstmt.setDate(rsIndex, new java.sql.Date(((Date) obj).getTime()));
                		} else {
                        	pstmt.setObject(rsIndex, obj);	
                		}
                	} else {
                		if (metaData != null) {
                    		pstmt.setNull(rsIndex, metaData.getColumnType(rsIndex));	
                		} else {
                        	pstmt.setNull(rsIndex, Types.NULL);
                		}
                	}
                }
            }
            
			int rs = pstmt.executeUpdate();
			
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(pstmt);
		}
	}

	// =================== tool mathods ===================
	
    public ResultSetMetaData getResultSetMetaData(Connection conn, String queryScript) throws Exception {
        PreparedStatement pstmt = null;
        ResultSetMetaData rsMetaData = null;

        try {
            // prepare dummy query
            pstmt = conn.prepareStatement(queryScript);

            // query is never executed on the server - only prepared
            rsMetaData = pstmt.getMetaData();
            
            return rsMetaData;
        } catch (SQLException e) {
        	throw e;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
	
	private void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close() {
		closeConnection();
	}
	
    public void commit() throws SQLException {
        this.conn.commit();
    }
	
	private void printSql(String sqltext) {
		if (debug) {
			System.out.println("sqltext = \n" + sqltext + "\n");	
		}
	}

	/**
	 * 暫存
	 * 
	 * @author neil.lin
	 *
	 */
	class MethodCache {
		private Method setterMethod; // 
		private Method rsMethod;	// 

		public Method getSetterMethod() {
			return setterMethod;
		}

		public void setSetterMethod(Method setterMethod) {
			this.setterMethod = setterMethod;
		}

		public Method getRsMethod() {
			return rsMethod;
		}

		public void setRsMethod(Method rsMethod) {
			this.rsMethod = rsMethod;
		}
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

}
