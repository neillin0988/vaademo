/**
 * ...
 */
package com.prototype.vaadin.core.util.db;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.prototype.vaadin.core.util.db.model.ColumnInfo;

/**
 * 針對 與DB連結的 javaBean(entity) 的操作
 * Neil
 */
public class EntityUtils {
	
	/**
	 * 
	 * @param clz
	 * @return
	 */
	public static String getUpdateScript(Class<?> clz, Set<String> columns) {
		isEntityClass(clz);

		try {
			String tableNm = getTableName(clz);
			Map<String, Object> idMp = EntityUtils.getIdValues(clz.newInstance());
			
			Condition idCondition = new Condition();
			for (String idColumn : idMp.keySet()) {
				idCondition.and(idColumn + " = ?");
			}

			return SqlTextUtil.getUpdateScript(tableNm, columns, idCondition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object[] getUpdateParams(Object entity, Set<String> columns) {
		Map<String, Object> nonIdMp = EntityUtils.getNonIdValues(entity);
		Map<String, Object> idMp = EntityUtils.getIdValues(entity);

		// params
		LinkedList<Object> v1 = new LinkedList();
		for (String column : nonIdMp.keySet()) {
			if (columns.contains(column)) {
				Object value = nonIdMp.get(column);
				v1.add(value);
			}
		}
		
		LinkedList<Object> v2 = new LinkedList(idMp.values());
		v1.addAll(v2);
		
		return v1.toArray();
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public static String getTableName(Class<?> clz) {
		isEntityClass(clz);
		
		return clz.getAnnotation(Table.class).name();
	}
	
	/**
	 * 
	 * @param entity
	 * @return Set<String>
	 */
	public static Set<String> getColumns(Class<?> clz) {
		isEntityClass(clz);
		
		Set<String> result = new TreeSet<String>(); 
		for (Method method : clz.getMethods()) {
			if (isGetterField(method)) {
				Column column = method.getAnnotation(Column.class);
				String columnName = column.name();

				result.add(columnName);
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param entity
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> getValuesMp(Object entity) {
		Class<?> clz = entity.getClass();
		isEntityClass(clz);
		
		Map<String, Object> result = new TreeMap<String, Object>(); 
		
		for (Method method : clz.getMethods()) {
			if (isGetterField(method)) {
				Column column = method.getAnnotation(Column.class);
				String columnName = column.name();
				
				try {
					result.put(columnName, method.invoke(entity));	
				} catch (Exception e) {
				}
			}
		}
		
		return result;
	}
	
	public static Map<String, Object> getIdValues(Object entity) {
		Class<?> clz = entity.getClass();
		isEntityClass(clz);
		
		Map<String, Object> result = new TreeMap<String, Object>(); 
		for (Method method : clz.getMethods()) {
			if (isGetterField(method) && isIdField(method)) {
				Column column = method.getAnnotation(Column.class);
				String columnName = column.name();

				try {
					result.put(columnName, method.invoke(entity));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	

	/**
	 * 取得 Id 以外的欄位
	 * 
	 * @param entity
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> getNonIdValues(Object entity) {
		isEntityClass(entity.getClass());
		
		Map<String, Object> result = new TreeMap<String, Object>(); 
		Class<?> clz = entity.getClass();
		
		for (Method method : clz.getMethods()) {
			if (isGetterField(method) && !isIdField(method)) {
				Column column = method.getAnnotation(Column.class);
				String columnName = column.name();
				
				try {
					result.put(columnName, method.invoke(entity));	
				} catch (Exception e) {
					
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param entity
	 * 
	 * @return Set<String>
	 */
	public static Map<String, ColumnInfo> getColumnsInfoMp(Class<?> clz) {
		isEntityClass(clz);
		
		Map<String, ColumnInfo> result = new TreeMap<String, ColumnInfo>(); 
		for (Method method : clz.getMethods()) {
			if (isGetterField(method)) {
				Column column = method.getAnnotation(Column.class);

				ColumnInfo columnInfo = new ColumnInfo();
				
				String name = column.name();
				Class<?> type = method.getReturnType();
				
				columnInfo.setName(name);
				columnInfo.setType(type);

				result.put(name, columnInfo);
			}
		}
		
		return result;
	}
	
	// ===============================================
	
	private static boolean isEntityClass(Class<?> clz) {
		if (clz.getAnnotation(Entity.class) != null) {
			return true;
		} else {
			throw new IllegalArgumentException("Not a @Entity class");	
		}
	}
	
	private static boolean isGetterField(Method method) {
		return method.getName().startsWith("get") && method.getAnnotation(Column.class) != null;
	}
	
	private static boolean isIdField(Method method) {
		return method.getAnnotation(Id.class) != null;
	}

}
