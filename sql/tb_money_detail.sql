/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : springboot_demo

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-09-20 17:26:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_money_detail
-- ----------------------------
DROP TABLE IF EXISTS `tb_money_detail`;
CREATE TABLE `tb_money_detail` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `full_name` varchar(255) DEFAULT NULL COMMENT 'JV公司全称',
  `nature` varchar(255) DEFAULT NULL COMMENT '股东性质',
  `name` varchar(255) DEFAULT NULL,
  `money` double(20,2) DEFAULT NULL COMMENT '认缴金额',
  `date` date DEFAULT NULL COMMENT '认缴时间',
  `money2` double(20,2) DEFAULT NULL COMMENT '实缴金额',
  `form` varchar(10) DEFAULT NULL COMMENT '出资形式',
  `account_name` varchar(255) DEFAULT NULL COMMENT '出资账户名',
  `account` varchar(255) DEFAULT NULL COMMENT '出资账号',
  `account_bank` varchar(255) DEFAULT NULL COMMENT '出资开户行',
  `accept_account_name` varchar(255) DEFAULT NULL COMMENT '收款账户名',
  `accept_account` varchar(255) DEFAULT NULL COMMENT '收款账号',
  `accept_account_bank` varchar(255) DEFAULT NULL COMMENT '收款开户行',
  `add_money` double(10,2) DEFAULT NULL COMMENT '增资金额',
  `release_money` double(10,2) DEFAULT NULL COMMENT '撤资时间',
  `release_money_date` date DEFAULT NULL COMMENT '撤资金额',
  `register_money` double(10,2) DEFAULT NULL COMMENT '工商注册资本金',
  PRIMARY KEY (`id`)
);
