package com.renren.jinkong.kylin.dbtool.core;

import com.renren.jinkong.kylin.dbtool.kit.DateKit;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * TypeMapping 建立起 ResultSetMetaData.getColumnClassName(i)到 java类型的映射关系
 * 特别注意所有时间型类型全部映射为 java.util.Date，可通过继承扩展该类来调整映射满足特殊需求
 * 
 * 与 com.jfinal.plugin.activerecord.JavaType.java 类型映射不同之处在于将所有
 * 时间型类型全部对应到 java.util.Date
 */
public class TypeMapping {
	
	@SuppressWarnings("serial")
	protected Map<String, String> map = new HashMap<String, String>() {{
		// java.util.Data can not be returned
		// java.sql.Date, java.sql.Time, java.sql.Timestamp all extends java.util.Data so getDate can return the three types data
		// put("java.util.Date", "java.util.Date");
		
		// date, year
		put("java.sql.Date", "java.util.Date");
		
		// time
		put("java.sql.Time", "java.util.Date");
		
		// timestamp, datetime
		put("java.sql.Timestamp", "java.util.Date");
		
		// binary, varbinary, tinyblob, blob, mediumblob, longblob
		// qjd project: print_info.content varbinary(61800);
		put("[B", "byte[]");
		
		// ---------
		
		// varchar, char, enum, set, text, tinytext, mediumtext, longtext
		put("java.lang.String", "java.lang.String");
		
		// int, integer, tinyint, smallint, mediumint
		put("java.lang.Integer", "java.lang.Integer");
		
		// bigint
		put("java.lang.Long", "java.lang.Long");
		
		// real, double
		put("java.lang.Double", "java.lang.Double");
		
		// float
		put("java.lang.Float", "java.lang.Float");
		
		// bit
		put("java.lang.Boolean", "java.lang.Boolean");
		
		// decimal, numeric
		put("java.math.BigDecimal", "java.math.BigDecimal");
		
		// unsigned bigint
		put("java.math.BigInteger", "java.math.BigInteger");
		
		// short
		put("java.lang.Short", "java.lang.Short");
		
		// byte
		put("java.lang.Byte", "java.lang.Byte");
	}};
	
	public String getType(String typeString) {
		return map.get(typeString);
	}

	public static Object convertType(String type, String value) {
		Object result = null;

		if(value == null || value.equals("")) {
			return null;
		}

		switch (type) {
			case "class java.lang.String":
				result = value;
				break;
			case "class java.lang.Integer":
			case "int":
				result = Integer.parseInt(value);
				break;
			case "class java.lang.Double":
			case "double":
				result = Double.parseDouble(value);
				break;
			case "class java.lang.Boolean":
			case "boolean":
				result = Boolean.parseBoolean(value);
				break;
			case "class java.util.Date":
				result = DateKit.getDateByStr(value);
				break;
			case "java.lang.Long":
				result = Long.valueOf(value);
				break;
			case "java.lang.Float":
				result = Float.parseFloat(value);
				break;
			case "java.lang.Byte":
				result = Byte.parseByte(value);
				break;
			case "java.math.BigDecimal":
				result = new BigDecimal(value);
				break;
			case "java.math.BigInteger":
				result = new BigInteger(value);
				break;
			default:
				break;
		}

		return result;
	}
}
