package com.renren.jinkong.kylin.dbtool.core.dialect;

/**
 * Dialect.
 */
public abstract class Dialect {
	// 指示 Generator、ModelBuilder、RecordBuilder 是否保持住 Byte、Short 类型
	protected boolean keepByteAndShort = false;
	/**
	 * 指示 MetaBuilder 生成的 ColumnMeta.javaType 是否保持住 Byte、Short 类型
	 * 进而 BaseModelBuilder 生成针对 Byte、Short 类型的获取方法：
	 * getByte(String)、getShort(String)
	 */
	public boolean isKeepByteAndShort() {
		return keepByteAndShort;
	}
	/**
	 * Methods for common
	 * @param tableName
	 * @return
	 */
	public abstract String forTableBuilderDoBuild(String tableName);
}






