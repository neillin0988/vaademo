package com.prototype.vaadin.core.util.db;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Restriction for condition use
 * 
 * Neil
 */
public class Restriction {
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public enum RestrictionType {
		IS_NULL("%s IS NULL"),
		NOT_NULL("%s IS NOT NULL"),
		EQ("%s = %s"),
		NE("%s != %s"),
		LIKE("%s LIKE %s"),
		NOT_LIKE("%s NOT LIKE %s"),
		GT("%s > %s"),
		GE("%s >= %s"),
		LT("%s < %s"),
		LE("%s <= %s"),
		IN("%s IN (%s)"),
		BETWEEN("%s BETWEEN %s AND %s");
		
		private String format;
		
		RestrictionType(String format) {
			this.format = format;
		}

		public String getFormat() {
			return format;
		}

		public void setFormat(String format) {
			this.format = format;
		}
		
		@Override
		public String toString() {
			// for vaadin component use
			return format.replace("%s", "");
//			return super.toString();
		}
	}
	
	private RestrictionType restrictionType;
	private String column;
	private Object[] value;
	
	private Restriction(RestrictionType restrictionType, String column, Object... value){
		this.restrictionType = restrictionType;
		this.column = column;
		this.value = value;
	};
	
	/**
	 * 
	 * @param restrictionType
	 * @param column
	 * @param value
	 * @return
	 */
	public static Restriction create(RestrictionType restrictionType, String column, Object... value){
		return new Restriction(restrictionType, column, value);
	};

	/**
	 * ${column} IS NULL
	 * 
	 * @param column
	 * @return Restriction
	 */
	public static Restriction isNull(String column) {
		return new Restriction(RestrictionType.IS_NULL, column);
	}
	
	/**
	 * ${column} NOT NULL
	 * 
	 * @param column
	 * @return Restriction
	 */
	public static Restriction notNull(String column) {
		return new Restriction(RestrictionType.NOT_NULL, column);
	}
	
	/**
	 * ${column} = ${value}
	 * 
	 * @param column
	 * @param value
	 * @return Restriction
	 */
	public static Restriction eq(String column, Object value) {
		return new Restriction(RestrictionType.EQ, column, value);
	}
	
	/**
	 * ${column} != ${value}
	 * 
	 * @param column
	 * @param value
	 * @return Restriction
	 */
	public static Restriction ne(String column, Object value) {
		return new Restriction(RestrictionType.NE, column, value);
	}
	
	/**
	 * ${column} LIKE ${value}
	 * 
	 * @param column
	 * @param value
	 * @return Restriction
	 */
	public static Restriction like(String column, Object value) {
		return new Restriction(RestrictionType.LIKE, column, value);
	}
	
	/**
	 * ${column} NOT LIKE ${value}
	 * 
	 * @param column
	 * @param value
	 * @return Restriction
	 */
	public static Restriction notlike(String column, Object value) {
		return new Restriction(RestrictionType.NOT_LIKE, column, value);
	}

	/**
	 * ${column} > ${value}
	 * 
	 * @param column
	 * @param value
	 * @return Restriction
	 */
	public static Restriction gt(String column, Object value) {
		return new Restriction(RestrictionType.GT, column, value);
	}

	/**
	 * ${column} >= ${value}
	 * 
	 * @param column
	 * @param value
	 * @return Restriction
	 */
	public static Restriction ge(String column, Object value) {
		return new Restriction(RestrictionType.GE, column, value);
	}

	/**
	 * ${column} < ${value}
	 * 
	 * @param column
	 * @param value
	 * @return Restriction
	 */
	public static Restriction lt(String column, Object value) {
		return new Restriction(RestrictionType.LT, column, value);
	}

	/**
	 * ${column} <= ${value}
	 * 
	 * @param column
	 * @param value
	 * @return Restriction
	 */
	public static Restriction le(String column, Object value) {
		return new Restriction(RestrictionType.LE, column, value);
	}

	/**
	 * ${column} IN (${value}, ${value}, ${value}, ${value}...)
	 * 
	 * @param column
	 * @param value
	 * @return Restriction
	 */
	public static Restriction in(String column, Object... value) {
		return new Restriction(RestrictionType.IN, column, value);
	}

	/**
	 * ${column} BETWEEN ${valueA} AND ${valueB}
	 * 
	 * @param column
	 * @param valueA
	 * @param valueB
	 * @return Restriction
	 */
	public static Restriction between(String column, Object valueA, Object valueB) {
		return new Restriction(RestrictionType.BETWEEN, column, valueA, valueB);
	}
	
	// ----------------------------------------------------------------------------------------
	
	/**
	 * toString 
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		String format = restrictionType.getFormat();
		String result = "";
		
		if (breakAddCondition(value)) {
			return result;
		}
		
		switch (restrictionType) {
		case BETWEEN:
			result = String.format(format, column, parseSqlValue(value[0]), parseSqlValue(value[1]));
			break;
		case IN:
			StringBuilder sb = new StringBuilder();
			for (Object o : value) {
				sb.append(", " + parseSqlValue(o));
			}
			
			result = String.format(format, column, sb.toString().substring(2));
			break;
		case IS_NULL:
		case NOT_NULL:
			result = String.format(format, column);
			break;
		case LIKE:
		case NOT_LIKE:
			String val = (val = (String) value[0]).contains("%") ? parseSqlValue(val) : parseSqlValue("%" + val + "%");
			result = String.format(format, column, val);
			break;
		default :
			result = String.format(format, column, parseSqlValue(value[0]));
			break;
		}
			
		return result;
	}
	
	/**
	 * parse value for sql condition use
	 * 
	 * @param value
	 * @return String
	 */
	private String parseSqlValue(Object value) {
		if (value instanceof Number) {
			return String.valueOf(value);
		} else if (value instanceof Date || value instanceof Calendar) {
			Date date = null;
			if (value instanceof Date) {
				date = (Date) value;	
			} else if (value instanceof GregorianCalendar) {
				date = ((Calendar) value).getTime();
			}
			
			return "'" + String.valueOf(sdf.format(date)) + "'";
		} else {
			return "'" + solveSqlInjection((String) value) + "'";
		}
	}
	
	/**
	 * solve sql injection, replace ['] to ['']
	 * 
	 * @param x
	 * @return String
	 */
	private String solveSqlInjection(String x) {
		return x.replace("'", "''");
	}
	
	/**
	 * return true - to stop this condition and return empty string
	 * 
	 * @param val
	 * @return
	 */
	private boolean breakAddCondition(Object... val) {
		if (val != null) {
			for (Object o : val) {
				if (o == null || "".equals(String.valueOf(o))) {
					return true;
				}
			}
		} else {
			return true;
		}
		
		return false;
	}
}
