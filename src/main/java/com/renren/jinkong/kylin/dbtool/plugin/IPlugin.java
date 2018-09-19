package com.renren.jinkong.kylin.dbtool.plugin;

/**
 * 插件接口
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018-09-19
 */
public interface IPlugin {
	boolean start();
	boolean stop();
}