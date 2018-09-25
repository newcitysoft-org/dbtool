package com.renren.jinkong.kylin.dbtool.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 表格列元数据实体类
 * @author lixin.tian@renren-inc.com
 * @date 2018-09-25
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
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
}

