package com.prototype.vaadin.core.util.db;

/**
 * 動態查詢條件
 * 
 * Neil
 */
public class Condition {

	private StringBuilder baseCondition = new StringBuilder();
	
    public enum ConditionOper {
    	AND("AND"),
    	OR("OR"),
    	;
    	
    	public String value;
    	
    	ConditionOper(String format) {
    		this.value = format;
    	}
    	
    	public String getValue() {
    		return value;
    	}
    }

	@Override
	public String toString() {
		return baseCondition.length() == 0? " 1 = 1 " : baseCondition.toString();
	}
	
	public void add(ConditionOper oper, Restriction restriction) {
		switch (oper) {
		case AND:
			and(restriction);
			break;
		case OR:
			or(restriction);
			break;
		}
	}
	
	/**
	 * sub condition<br>
	 * 
	 * @param condition
	 */
	public void and(Condition condition) {
		if (condition != null) {
			addCondition(ConditionOper.AND, "(" + condition + ")");	
		}
	}
	
	public void or(Condition condition) {
		if (condition != null) {
			addCondition(ConditionOper.OR, "(" + condition + ")");	
		}
	}
	
	public void and(Restriction restriction) {
		if (restriction != null) {
			addCondition(ConditionOper.AND, restriction.toString());	
		}
	}

	public void or(Restriction restriction) {
		if (restriction != null) {
			addCondition(ConditionOper.OR, restriction.toString());	
		}
	}

	public void and(String conditionStr) {
		if (conditionStr != null && !conditionStr.isEmpty()) {
			addCondition(ConditionOper.AND, conditionStr);	
		}
	}
	
	public void or(String conditionStr) {
		if (conditionStr != null && !conditionStr.isEmpty()) {
			addCondition(ConditionOper.OR, conditionStr);	
		}
	}

	public void append(String conditionStr) {
		if (conditionStr != null && !conditionStr.isEmpty()) {
			baseCondition.append(conditionStr);	
		}
	}
	
	
	//-----------------------------------------------------------------------------

	private void addCondition(ConditionOper oper, String conditionStr) {
		if (conditionStr == null || conditionStr.isEmpty()) {
			return;
		}
		
		if (baseCondition.length() != 0) {
			baseCondition.append(" " + oper.getValue() + " ");
		}
		
		baseCondition.append(conditionStr);
	}

}
