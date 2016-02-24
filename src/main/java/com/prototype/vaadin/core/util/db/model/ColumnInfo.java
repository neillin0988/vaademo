package com.prototype.vaadin.core.util.db.model;


/**
 * column info
 * 
 * @author neil.lin
 * 
 */
public class ColumnInfo {

	private String name; // Column name
	private Class<?> type; // Column type

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return name;
	}

}
