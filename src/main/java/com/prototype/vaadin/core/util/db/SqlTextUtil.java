package com.prototype.vaadin.core.util.db;

import java.util.List;
import java.util.Set;

/**
 * 產生SQL語法
 * 
 * @author Neil
 */
public class SqlTextUtil {
	
	/**
	 * 產生[新增]語法(insert script)
	 * 
	 * @param tableNm
	 * @param columns
	 * @return
	 */
	public static String getInsertScript(String tableNm, Set<String> columns) {
		try {
			String columnStr = "";
			String valueStr = "";
			
			StringBuilder columnsTemp = new StringBuilder();
			StringBuilder valuesTemp = new StringBuilder();
			
			for (String columnNm : columns) {
				columnsTemp.append(", " + columnNm);
				valuesTemp.append(", ?");
			}
			
			columnStr = columnsTemp.toString().substring(1);
			valueStr = valuesTemp.toString().substring(1);
			
			return String.format("INSERT INTO %s (%s) VALUES(%s) ", tableNm, columnStr, valueStr);			
		} catch (Exception e) {
		}

		return "";
	}
	
	/**
	 * 產生[更新]語法(update script)
	 * 
	 * @param tableNm
	 * @param columns
	 * @param condition
	 * @return
	 */
	public static String getUpdateScript(String tableNm, Set<String> columns, Condition condition) {
		try {
			String update = "";
			StringBuilder updateSb = new StringBuilder();

			for (String columnNm : columns) {
				Object value = "?";
				updateSb.append("," + columnNm + " = " + value);
			}
			
			update = updateSb.toString().substring(1);
	
			return String.format("UPDATE %s SET %s WHERE %s", tableNm, update, condition);
		} catch (Exception e) {
		}
	
		return "";
	}
	
	/**
	 * 
	 * @param tableNm
	 * @param condition
	 * @return
	 */
	public static String getQueryScript(String tableNm, Condition condition) {
		return getQueryScript(tableNm, null, condition);	
	}
	
	/**
	 * 產生[查詢]語法(query script)
	 * 
	 * @param tableNm
	 * @param columns
	 * @param condition
	 * @return
	 */
	public static String getQueryScript(String tableNm, Set<String> columns, Condition condition) {
		String columnStr = "";
		StringBuilder columnTemp = new StringBuilder();
		
		if (columns == null || columns.isEmpty()) {
			columnStr = "*";
		} else {
			for (String columnNm : columns) {
				columnTemp.append("," + columnNm);
			}
			
			columnStr = columnTemp.toString().substring(1);	
		}
		
		return String.format("SELECT %s FROM %s WHERE %s ", columnStr, tableNm, condition);	
	}

	/**
	 * 產生[刪除]語法(delete script)
	 * 
	 * @param tableNm
	 * @param condition
	 * @return
	 */
	public static String getDeleteScript(String tableNm, Condition condition) {
		return String.format("DELETE FROM %s WHERE %s", tableNm, condition);
	}
	
	// ===================================================

	/**
	 * 產生[查詢條件 IN]語法
	 * 
	 * @param columns
	 * @return String
	 */
	public static String getConditionInStr(List<String> columns) {
		StringBuffer sqltext = new StringBuffer();

		for (int i = 0; i < columns.size(); i++) {
			sqltext.append(",'" + columns.get(i) + "'");
		}

		return sqltext.substring(1).toString();
	}

}
