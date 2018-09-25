package com.renren.jinkong.kylin.dbtool.model;

import java.io.Serializable;

/**
 * 表格列元数据实体类
 * @author lixin.tian@renren-inc.com
 * @date 2018-09-25
 */
public class ColumnMeta implements Serializable {

	private static final long serialVersionUID = 6852879237267377779L;
	/**
	 * 字段名
	 */
	public String name;
	/**
	 * 字段对应的 java 类型
	 */
	public String javaType;
	/**
	 * 字段对应的属性名
	 */
	public String attrName;
	
	/*
	-----------+---------+------+-----+---------+----------------
	 Field     | Type    | Null | Key | Default | Remarks
	-----------+---------+------+-----+---------+----------------
	 id		   | int(11) | NO	| PRI | NULL	| remarks here	
	*/
	/**
	 *字段类型(附带字段长度与小数点)，例如：decimal(11,2)
	 */
	public String type;
	/**
	 * 是否允许空值
	 */
	public String isNullable;
	/**
	 * 是否主键
	 */
	public String isPrimaryKey;
	/**
	 * 默认值
	 */
	public String defaultValue;
	/**
	 * 字段备注
	 */
	public String remarks;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public String getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(String isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "ColumnMeta{" +
				"name='" + name + '\'' +
				", javaType='" + javaType + '\'' +
				", attrName='" + attrName + '\'' +
				", type='" + type + '\'' +
				", isNullable='" + isNullable + '\'' +
				", isPrimaryKey='" + isPrimaryKey + '\'' +
				", defaultValue='" + defaultValue + '\'' +
				", remarks='" + remarks + '\'' +
				'}';
	}
}

