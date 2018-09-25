package com.renren.jinkong.kylin.dbtool.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 表格元数据实体类
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018-09-25
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TableMeta implements Serializable {

	private static final long serialVersionUID = -4659736956433755858L;
	/**
	 * 表名
	 */
	public String name;
	/**
	 * 表备注
	 */
	public String remarks;
	/**
	 * 主键，复合主键以逗号分隔
	 */
	public String primaryKey;
	/**
	 * 字段 meta
	 */
	public List<ColumnMeta> columnMetas = new ArrayList<ColumnMeta>();
}