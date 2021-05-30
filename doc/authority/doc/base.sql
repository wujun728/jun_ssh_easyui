/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50710
Source Host           : localhost:3306
Source Database       : base

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2017-07-23 18:50:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `his_file_data`
-- ----------------------------
DROP TABLE IF EXISTS `his_file_data`;
CREATE TABLE `his_file_data` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '创建日期',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '是否已删除0/1',
  `device_sn` varchar(255) DEFAULT NULL COMMENT '设备标号',
  `doctor_id` varchar(32) DEFAULT NULL COMMENT '此次检测的操作医生，不一定是当前的责任医生 ',
  `download` tinyint(1) DEFAULT '0' COMMENT '是否已下载',
  `file_length` varchar(255) DEFAULT NULL COMMENT '文件大小（Byte）',
  `file_name` varchar(64) DEFAULT NULL COMMENT '原文件名 ',
  `file_path` varchar(255) DEFAULT NULL COMMENT '文件存储路径 ',
  `patient_id` varchar(32) DEFAULT NULL COMMENT '所属患者 ',
  `report` tinyint(1) DEFAULT '0' COMMENT '是否已出报告',
  `type_code` varchar(255) DEFAULT NULL COMMENT '文件类型编码 ，如心电、血压、尿检 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of his_file_data
-- ----------------------------

-- ----------------------------
-- Table structure for `his_file_report`
-- ----------------------------
DROP TABLE IF EXISTS `his_file_report`;
CREATE TABLE `his_file_report` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '创建日期',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '是否已删除0/1',
  `data_id` bigint(20) DEFAULT NULL COMMENT '数据文件ID',
  `device_sn` varchar(255) DEFAULT NULL COMMENT '设备标号',
  `doctor_id` varchar(32) DEFAULT NULL COMMENT '此次检测的操作医生，不一定是当前的责任医生 ',
  `download` tinyint(1) DEFAULT '0' COMMENT '是否已下载',
  `file_length` varchar(255) DEFAULT NULL COMMENT '文件大小（Byte）',
  `file_name` varchar(64) DEFAULT NULL COMMENT '原文件名 ',
  `file_path` varchar(255) DEFAULT NULL COMMENT '文件存储路径 ',
  `patient_id` varchar(32) DEFAULT NULL COMMENT '所属患者 ',
  `type_code` varchar(255) DEFAULT NULL COMMENT '文件类型编码 ，如心电、血压、尿检 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of his_file_report
-- ----------------------------

-- ----------------------------
-- Table structure for `his_third_party`
-- ----------------------------
DROP TABLE IF EXISTS `his_third_party`;
CREATE TABLE `his_third_party` (
  `id` varchar(32) NOT NULL,
  `aes_key` varchar(16) DEFAULT NULL,
  `app_name` varchar(16) DEFAULT NULL,
  `create_by` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `pub_key` varchar(128) DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `secret_key` varchar(128) DEFAULT NULL COMMENT 'sk：MD5数字签名的字符串,可双方约定',
  `update_by` varchar(32) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_aqjmp8v2oe0dqhki7hlvfu9nf` (`app_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of his_third_party
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_area`
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `parent_id` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `type` char(1) DEFAULT NULL COMMENT '区域类型',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_area_parent_id` (`parent_id`),
  KEY `sys_area_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域表';

-- ----------------------------
-- Records of sys_area
-- ----------------------------
INSERT INTO `sys_area` VALUES ('1', '0', '0,', '中国', '10', '100000', '1', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('17e8e72326574a0ea94b15d6eeddbb6d', '364a832bee8a42afa333e338813330de', '0,1,c11497c3311a4be29253f29bd59ab485,364a832bee8a42afa333e338813330de,', '软件大道', '30', '', '4', '1', '2016-01-11 23:09:38', '1', '2016-01-11 23:09:38', '', '0');
INSERT INTO `sys_area` VALUES ('19298dc65ecd45cc803a6da294d1ff60', '1', '0,1,', '北京', '30', '', '2', '1', '2016-01-15 19:55:09', '1', '2016-01-15 19:55:09', '', '0');
INSERT INTO `sys_area` VALUES ('364a832bee8a42afa333e338813330de', 'c11497c3311a4be29253f29bd59ab485', '0,1,c11497c3311a4be29253f29bd59ab485,', '南京', '30', '', '3', '1', '2015-12-24 21:38:02', '1', '2016-01-11 23:09:25', '', '0');
INSERT INTO `sys_area` VALUES ('90ecd439eb3845db97a627d9242145e8', '1', '0,1,', '上海', '30', '', '2', '1', '2016-01-15 19:54:55', '1', '2016-01-15 19:54:55', '', '0');
INSERT INTO `sys_area` VALUES ('c11497c3311a4be29253f29bd59ab485', '1', '0,1,', '江苏', '30', '', '2', '1', '2015-11-11 17:37:48', '1', '2015-11-11 17:38:14', '', '0');
INSERT INTO `sys_area` VALUES ('e418dc99691d4e29961d53ca218ece8b', '1', '0,1,', '福建', '30', '0100', '2', '1', '2016-01-15 19:54:30', '1', '2016-01-15 19:54:30', '', '0');
INSERT INTO `sys_area` VALUES ('ee38f91df4444529b00f093be5a07b7a', 'e418dc99691d4e29961d53ca218ece8b', '0,1,e418dc99691d4e29961d53ca218ece8b,', '福州', '30', '0200', '1', '1', '2016-01-15 19:54:44', '1', '2016-01-15 19:54:44', '', '0');

-- ----------------------------
-- Table structure for `sys_depart`
-- ----------------------------
DROP TABLE IF EXISTS `sys_depart`;
CREATE TABLE `sys_depart` (
  `id` varchar(32) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `del_flag` tinyint(4) NOT NULL DEFAULT '0',
  `remarks` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `area_id` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `deputy_person` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `sort` bigint(20) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_depart
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `value` varchar(100) NOT NULL COMMENT '数据值',
  `label` varchar(100) NOT NULL COMMENT '标签名',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `description` varchar(100) NOT NULL COMMENT '描述',
  `sort` decimal(10,0) NOT NULL COMMENT '排序（升序）',
  `parent_id` varchar(64) DEFAULT '0' COMMENT '父级编号',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`),
  KEY `sys_dict_label` (`label`),
  KEY `sys_dict_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('0a22f3278a624882a822e0820f27efcb', '1', '主表', 'table_type', '表类型', '20', null, '1', '2016-01-05 21:47:14', '1', '2016-01-05 21:53:34', '', '0', null);
INSERT INTO `sys_dict` VALUES ('1', '0', '正常', 'del_flag', '删除标记', '10', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('10', 'yellow', '黄色', 'color', '颜色值', '40', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('100', 'java.util.Date', 'Date', 'gen_java_type', 'Java类型', '50', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('101', 'com.thinkgem.jeesite.modules.sys.entity.User', 'User', 'gen_java_type', 'Java类型', '60', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('102', 'com.thinkgem.jeesite.modules.sys.entity.Office', 'Office', 'gen_java_type', 'Java类型', '70', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('103', 'com.thinkgem.jeesite.modules.sys.entity.Area', 'Area', 'gen_java_type', 'Java类型', '80', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('104', 'Custom', 'Custom', 'gen_java_type', 'Java类型', '90', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('105', '1', '会议通告', 'oa_notify_type', '通知通告类型', '10', null, '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('106', '2', '奖惩通告', 'oa_notify_type', '通知通告类型', '20', null, '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('107', '3', '活动通告', 'oa_notify_type', '通知通告类型', '30', null, '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('108', '0', '草稿', 'oa_notify_status', '通知通告状态', '10', null, '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('109', '1', '发布', 'oa_notify_status', '通知通告状态', '20', null, '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('11', 'orange', '橙色', 'color', '颜色值', '50', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('110', '0', '未读', 'oa_notify_read', '通知通告状态', '10', null, '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('111', '1', '已读', 'oa_notify_read', '通知通告状态', '20', null, '1', '2013-11-08 08:00:00', '1', '2013-11-08 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('12', 'default', '默认主题', 'theme', '主题方案', '10', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('13', 'cerulean', '天蓝主题', 'theme', '主题方案', '20', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('14', 'readable', '橙色主题', 'theme', '主题方案', '30', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('17', '1', '国家', 'sys_area_type', '区域类型', '10', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('18', '2', '省份、直辖市', 'sys_area_type', '区域类型', '20', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('19', '3', '地市', 'sys_area_type', '区域类型', '30', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('1c0ac576c33d41fcb4c16602bf4fad5d', 'post', 'post', 'interface_type', '接口类型', '20', null, '1', '2015-11-30 15:52:25', '1', '2015-11-30 15:52:39', '', '0', null);
INSERT INTO `sys_dict` VALUES ('2', '1', '删除', 'del_flag', '删除标记', '20', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('20', '4', '区县', 'sys_area_type', '区域类型', '40', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('21', '1', '公司', 'sys_office_type', '机构类型', '60', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('22', '2', '部门', 'sys_office_type', '机构类型', '70', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('221a918bd1e149239a17ab0fdeaf5ecd', 'get', 'get', 'interface_type', '接口类型', '10', null, '1', '2015-11-30 15:51:56', '1', '2015-11-30 15:51:56', '', '0', null);
INSERT INTO `sys_dict` VALUES ('23', '3', '小组', 'sys_office_type', '机构类型', '80', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('24', '4', '其它', 'sys_office_type', '机构类型', '90', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('25', '1', '综合部', 'sys_office_common', '快捷通用部门', '30', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('26', '2', '开发部', 'sys_office_common', '快捷通用部门', '40', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('27', '3', '人力部', 'sys_office_common', '快捷通用部门', '50', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('28', '1', '一级', 'sys_office_grade', '机构等级', '10', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('29', '2', '二级', 'sys_office_grade', '机构等级', '20', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('3', '1', '显示', 'show_hide', '显示/隐藏', '10', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('30', '3', '三级', 'sys_office_grade', '机构等级', '30', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('31', '4', '四级', 'sys_office_grade', '机构等级', '40', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('32', '1', '所有数据', 'sys_data_scope', '数据范围', '10', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('33', '2', '所在公司及以下数据', 'sys_data_scope', '数据范围', '20', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('34', '3', '所在公司数据', 'sys_data_scope', '数据范围', '30', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('35', '4', '所在部门及以下数据', 'sys_data_scope', '数据范围', '40', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('36', '5', '所在部门数据', 'sys_data_scope', '数据范围', '50', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('37', '8', '仅本人数据', 'sys_data_scope', '数据范围', '90', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('38', '9', '按明细设置', 'sys_data_scope', '数据范围', '100', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('39', '1', '系统管理', 'sys_user_type', '用户类型', '10', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('4', '0', '隐藏', 'show_hide', '显示/隐藏', '20', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('40', '2', '部门经理', 'sys_user_type', '用户类型', '20', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('41', '3', '普通用户', 'sys_user_type', '用户类型', '30', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('42', 'basic', '基础主题', 'cms_theme', '站点主题', '10', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('43', 'blue', '蓝色主题', 'cms_theme', '站点主题', '20', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('43c9472f411c4d3eafb3bf5319ffe499', '2', '异常报告', 'report_type', '异常的报告', '20', null, '1', '2015-12-08 17:49:57', '1', '2015-12-08 17:49:57', '', '0', null);
INSERT INTO `sys_dict` VALUES ('44', 'red', '红色主题', 'cms_theme', '站点主题', '30', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('45', 'article', '文章模型', 'cms_module', '栏目模型', '10', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('46', 'picture', '图片模型', 'cms_module', '栏目模型', '20', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('47', 'download', '下载模型', 'cms_module', '栏目模型', '30', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('48', 'link', '链接模型', 'cms_module', '栏目模型', '40', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('49', 'special', '专题模型', 'cms_module', '栏目模型', '50', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('5', '1', '是', 'yes_no', '是/否', '10', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('50', '0', '默认展现方式', 'cms_show_modes', '展现方式', '10', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('51', '1', '首栏目内容列表', 'cms_show_modes', '展现方式', '20', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('52', '2', '栏目第一条内容', 'cms_show_modes', '展现方式', '30', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('53', '0', '发布', 'cms_del_flag', '内容状态', '10', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('54', '1', '删除', 'cms_del_flag', '内容状态', '20', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('55', '2', '审核', 'cms_del_flag', '内容状态', '15', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('56', '1', '首页焦点图', 'cms_posid', '推荐位', '10', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('57', '2', '栏目页文章推荐', 'cms_posid', '推荐位', '20', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('58', '1', '咨询', 'cms_guestbook', '留言板分类', '10', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('59', '2', '建议', 'cms_guestbook', '留言板分类', '20', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('5b899603552c48519e7ba8eb9da0b41f', '0', '单表', 'table_type', '表类型', '10', null, '1', '2016-01-05 21:46:39', '1', '2016-01-05 21:53:50', '', '0', null);
INSERT INTO `sys_dict` VALUES ('6', '0', '否', 'yes_no', '是/否', '20', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('60', '3', '投诉', 'cms_guestbook', '留言板分类', '30', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('61', '4', '其它', 'cms_guestbook', '留言板分类', '40', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('62', '1', '公休', 'oa_leave_type', '请假类型', '10', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('63', '2', '病假', 'oa_leave_type', '请假类型', '20', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('64', '3', '事假', 'oa_leave_type', '请假类型', '30', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('65', '4', '调休', 'oa_leave_type', '请假类型', '40', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('66', '5', '婚假', 'oa_leave_type', '请假类型', '60', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('67', '1', '接入日志', 'sys_log_type', '日志类型', '30', null, '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('68', '2', '异常日志', 'sys_log_type', '日志类型', '40', null, '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('680ddd8c91fe43588a7bb7aafe816633', '1', '正常报告', 'report_type', '正常的报告', '10', null, '1', '2015-12-08 17:49:28', '1', '2015-12-08 17:49:28', '正常的报告', '0', null);
INSERT INTO `sys_dict` VALUES ('69', 'leave', '请假流程', 'act_type', '流程类型', '10', null, '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('7', 'red', '红色', 'color', '颜色值', '10', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('70', 'test_audit', '审批测试流程', 'act_type', '流程类型', '20', null, '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('71', '1', '常用流程', 'act_category', '流程分类', '10', null, '1', '2013-06-03 08:00:00', '1', '2016-06-19 22:15:01', '', '0', null);
INSERT INTO `sys_dict` VALUES ('71804c6b820048b09c9f6f2c11121113', 'ace', 'ACE风格', 'theme', '主题方案', '15', null, '1', '2016-04-20 21:57:21', '1', '2016-04-20 21:57:21', '', '0', null);
INSERT INTO `sys_dict` VALUES ('72', '2', '办公流程', 'act_category', '流程分类', '20', null, '1', '2013-06-03 08:00:00', '1', '2016-06-19 22:15:09', '', '0', null);
INSERT INTO `sys_dict` VALUES ('73', 'crud', '增删改查', 'gen_category', '代码生成分类', '10', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('74', 'crud_many', '增删改查（包含从表）', 'gen_category', '代码生成分类', '20', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('75', 'tree', '树结构', 'gen_category', '代码生成分类', '30', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('76', '=', '=', 'gen_query_type', '查询方式', '10', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('77', '!=', '!=', 'gen_query_type', '查询方式', '20', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('78', '&gt;', '&gt;', 'gen_query_type', '查询方式', '30', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('79', '&lt;', '&lt;', 'gen_query_type', '查询方式', '40', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('8', 'green', '绿色', 'color', '颜色值', '20', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('80', 'between', 'Between', 'gen_query_type', '查询方式', '50', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('81', 'like', 'Like', 'gen_query_type', '查询方式', '60', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('82', 'left_like', 'Left Like', 'gen_query_type', '查询方式', '70', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('83', 'right_like', 'Right Like', 'gen_query_type', '查询方式', '80', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('84', 'input', '文本框', 'gen_show_type', '字段生成方案', '10', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('85', 'textarea', '文本域', 'gen_show_type', '字段生成方案', '20', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('86', 'select', '下拉框', 'gen_show_type', '字段生成方案', '30', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('87', 'checkbox', '复选框', 'gen_show_type', '字段生成方案', '40', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('88', 'radiobox', '单选框', 'gen_show_type', '字段生成方案', '50', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('89', 'dateselect', '日期选择', 'gen_show_type', '字段生成方案', '60', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('9', 'blue', '蓝色', 'color', '颜色值', '30', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('90', 'userselect', '人员选择', 'gen_show_type', '字段生成方案', '70', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('91', 'officeselect', '部门选择', 'gen_show_type', '字段生成方案', '80', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('92', 'areaselect', '区域选择', 'gen_show_type', '字段生成方案', '90', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('93', 'String', 'String', 'gen_java_type', 'Java类型', '10', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('94', 'Long', 'Long', 'gen_java_type', 'Java类型', '20', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('95', 'dao', '仅持久层', 'gen_category', '代码生成分类\0\0', '40', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('96', '1', '男', 'sex', '性别', '10', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('97', '2', '女', 'sex', '性别', '20', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '0', null);
INSERT INTO `sys_dict` VALUES ('98', 'Integer', 'Integer', 'gen_java_type', 'Java类型', '30', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('99', 'Double', 'Double', 'gen_java_type', 'Java类型', '40', null, '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('bde6043665ef4571b85d0edab667cd15', '3', '树结构表', 'table_type', '表类型', '40', null, '1', '2016-01-06 19:48:50', '1', '2016-01-06 19:48:50', '', '0', null);
INSERT INTO `sys_dict` VALUES ('cc94b0c5df554a46894991210a5fc486', '2', '附表', 'table_type', '表类型', '30', null, '1', '2016-01-05 21:47:38', '1', '2016-01-05 21:53:44', '', '0', null);

-- ----------------------------
-- Table structure for `sys_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` varchar(32) NOT NULL,
  `parent_id` varchar(32) DEFAULT NULL COMMENT '上级菜单，菜单类型有效 ',
  `parent_ids` varchar(128) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL COMMENT '菜单名称',
  `sort` int(11) DEFAULT NULL COMMENT '排序字段',
  `href` varchar(64) DEFAULT NULL COMMENT '菜单链接',
  `target` varchar(255) DEFAULT NULL,
  `icon` varchar(32) DEFAULT NULL COMMENT '目标mainFrame _blank _self _parent _top',
  `is_show` char(1) DEFAULT NULL COMMENT ' 是否在菜单中显示（1：显示；0：不显示）',
  `permission` varchar(32) DEFAULT NULL COMMENT '权限 ',
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK3fekum3ead5klp7y4lckn5ohi` (`parent_id`),
  CONSTRAINT `FK3fekum3ead5klp7y4lckn5ohi` FOREIGN KEY (`parent_id`) REFERENCES `sys_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('0aa3712414d049a6a24e8bcddeae509a', '27', '0,1,27,', '我的日程', '100', '/iim/myCalendar', '', '', '1', '', '1', '2016-04-21 21:52:06', '1', '2016-04-21 21:52:06', '', '0');
INSERT INTO `sys_resource` VALUES ('1', null, '', '功能菜单', '0', null, null, null, '1', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_resource` VALUES ('10', '3', '0,1,3,', '字典管理', '60', '/sys/dict/', '', 'th-list', '1', 'sys:dict:list', '1', '2013-05-27 08:00:00', '1', '2015-12-24 22:23:07', '', '0');
INSERT INTO `sys_resource` VALUES ('11', '10', '0,1,3,10,', '查看', '30', '', '', '', '0', 'sys:dict:view', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_resource` VALUES ('12', '10', '0,1,3,10,', '修改', '40', '', '', '', '0', 'sys:dict:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_resource` VALUES ('14', '3', '0,1,3,', '区域管理', '50', '/sys/area/', '', 'th', '1', 'sys:area:list', '1', '2013-05-27 08:00:00', '1', '2015-12-24 21:35:11', '', '0');
INSERT INTO `sys_resource` VALUES ('15', '14', '0,1,3,14,', '查看', '30', '', '', '', '0', 'sys:area:view', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_resource` VALUES ('16', '14', '0,1,3,14,', '修改', '40', '', '', '', '0', 'sys:area:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_resource` VALUES ('17', '3', '0,1,3,', '机构管理', '40', '/sys/office/', '', 'th-large', '1', 'sys:office:index', '1', '2013-05-27 08:00:00', '1', '2015-12-20 21:32:26', '', '0');
INSERT INTO `sys_resource` VALUES ('18', '17', '0,1,3,17,', '查看', '30', '', '', '', '0', 'sys:office:view', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_resource` VALUES ('19', '17', '0,1,3,17,', '修改', '40', '', '', '', '0', 'sys:office:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_resource` VALUES ('20', '3', '0,1,3,', '用户管理', '30', '/sys/user/index', '', 'icon-adjust', '1', 'sys:user:index', '1', '2013-05-27 08:00:00', '1', '2015-12-19 21:46:20', '', '0');
INSERT INTO `sys_resource` VALUES ('21', '20', '0,1,3,20,', '查看', '30', '', '', '', '0', 'sys:user:view', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_resource` VALUES ('22', '20', '0,1,3,20,', '修改', '40', '', '', '', '0', 'sys:user:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_resource` VALUES ('27', '1', '0,1,', '我的面板', '100', '', '', 'fa-columns', '1', '', '1', '2013-05-27 08:00:00', '1', '2015-12-13 20:18:41', '', '0');
INSERT INTO `sys_resource` VALUES ('29', '27', '0,1,27,', '个人信息', '30', '/sys/user/info', '', 'icon-adjust', '1', '', '1', '2013-05-27 08:00:00', '1', '2016-03-27 22:43:46', '', '0');
INSERT INTO `sys_resource` VALUES ('2a0f940fbe304a05a6b4040ddf6df279', '20', '0,1,3,20,', '增加', '70', '', '', '', '0', 'sys:user:add', '1', '2015-12-19 21:47:00', '1', '2015-12-19 21:47:00', '', '0');
INSERT INTO `sys_resource` VALUES ('3', '1', '0,1,', '系统设置', '500', '', '', 'fa-cog', '1', '', '1', '2013-05-27 08:00:00', '1', '2015-11-04 17:27:37', '', '0');
INSERT INTO `sys_resource` VALUES ('3c1c639c76f14f6f9903b0143371ea09', '7', '0,1,3,7,', '添加', '70', '', '', '', '0', 'sys:role:add', '1', '2015-12-23 21:35:08', '1', '2015-12-23 21:36:18', '', '0');
INSERT INTO `sys_resource` VALUES ('4', '3', '0,1,3,', '菜单管理', '30', '/sys/menu/indexMenu', '', 'list-alt', '1', 'sys:menu:list', '1', '2013-05-27 08:00:00', '1', '2015-12-20 18:59:32', '', '0');
INSERT INTO `sys_resource` VALUES ('5', '4', '0,1,3,4,', '增加', '30', '', '', '', '0', 'sys:menu:add', '1', '2013-05-27 08:00:00', '1', '2015-12-20 19:00:22', '', '0');
INSERT INTO `sys_resource` VALUES ('5239527958e94d418997b584b85d8b80', '14', '0,1,3,14,', '删除', '100', '', '', '', '0', 'sys:area:del', '1', '2015-12-24 21:37:13', '1', '2015-12-24 21:37:13', '', '0');
INSERT INTO `sys_resource` VALUES ('56', '27', '0,1,27,', '文件管理', '90', '/../static/ckfinder/ckfinder.html', '', 'icon-zoom-out', '1', '', '1', '2013-05-27 08:00:00', '1', '2015-11-02 16:17:05', '', '0');
INSERT INTO `sys_resource` VALUES ('56e274e0ec1c41298e19ab46cf4e001f', '1', '0,1,', '常用工具', '2000', '', '', 'fa-anchor', '1', '', '1', '2016-03-03 16:30:04', '1', '2016-03-05 10:06:16', '', '0');
INSERT INTO `sys_resource` VALUES ('6', '4', '0,1,3,4,', '修改', '40', '', '', '', '0', 'sys:menu:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_resource` VALUES ('62', '1', '0,1,', '数据分析', '200', '', '', 'fa-desktop', '1', '', '1', '2013-05-27 08:00:00', '1', '2016-06-20 20:43:59', '', '0');
INSERT INTO `sys_resource` VALUES ('6509eed6eb634030a46723a18814035c', '7', '0,1,3,7,', '分配用户', '100', '', '', '', '0', 'sys:role:assign', '1', '2015-12-23 21:35:37', '1', '2015-12-23 21:53:23', '', '0');
INSERT INTO `sys_resource` VALUES ('67', '1', '0,1,', '急救监控', '985', '', '', 'fa-video-camera', '1', '', '1', '2013-06-03 08:00:00', '1', '2016-03-05 10:18:41', '', '0');
INSERT INTO `sys_resource` VALUES ('68f9151151174868ab436e11e03bf548', '4', '0,1,3,4,', '删除', '70', '', '', '', '0', 'sys:menu:del', '1', '2015-12-20 19:01:16', '1', '2015-12-20 19:03:05', '', '0');
INSERT INTO `sys_resource` VALUES ('6d3a6777693f47c98e9b3051cacbcfdb', '10', '0,1,3,10,', '增加', '70', '', '', '', '0', 'sys:dict:add', '1', '2015-12-24 22:23:39', '1', '2015-12-24 22:24:22', '', '0');
INSERT INTO `sys_resource` VALUES ('7', '3', '0,1,3,', '角色管理', '50', '/sys/role/', '', 'lock', '1', 'sys:role:list', '1', '2013-05-27 08:00:00', '1', '2015-12-23 21:33:46', '', '0');
INSERT INTO `sys_resource` VALUES ('79', '1', '0,1,', '代码生成', '20', '', '', 'fa-codepen', '1', '', '1', '2013-10-16 08:00:00', '1', '2016-05-31 08:56:25', '', '0');
INSERT INTO `sys_resource` VALUES ('79f0ffa47dbe43ffa8824d97612d344f', '4', '0,1,3,4,', '保存排序', '100', '', '', '', '0', 'sys:menu:updateSort', '1', '2015-12-20 19:02:08', '1', '2015-12-20 19:02:08', '', '0');
INSERT INTO `sys_resource` VALUES ('79fca849d3da4a82a4ade3f6b9f45126', '20', '0,1,3,20,', '删除', '100', '', '', '', '0', 'sys:user:del', '1', '2015-12-19 21:47:44', '1', '2015-12-19 21:48:52', '', '0');
INSERT INTO `sys_resource` VALUES ('7fe0397a90214f49adc9bbbe48e5ab69', '1', '0,1,', '统计报表', '10', '', '', 'fa-line-chart', '1', '', '1', '2016-05-26 08:55:24', '1', '2016-05-31 08:57:05', '', '0');
INSERT INTO `sys_resource` VALUES ('8', '7', '0,1,3,7,', '查看', '30', '', '', '', '0', 'sys:role:view', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_resource` VALUES ('843fdb78b8854bd48e503287f795a7d0', '27', '0,1,27,', '我的群组', '15120', '/iim/layGroup', '', '', '1', 'iim:layGroup:list', '1', '2016-08-07 21:15:11', '1', '2016-08-14 22:57:29', '', '0');
INSERT INTO `sys_resource` VALUES ('8930e4aad1ba4a1c958d303968d8c442', '17', '0,1,3,17,', '删除', '100', '', '', '', '0', 'sys:office:del', '1', '2015-12-20 21:19:16', '1', '2015-12-20 21:19:16', '', '0');
INSERT INTO `sys_resource` VALUES ('9', '7', '0,1,3,7,', '修改', '40', '', '', '', '0', 'sys:role:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_resource` VALUES ('95a6a82dc5fc4d07b46e5df57a0606a3', '27', '0,1,27,', '信箱', '10000', '/iim/mailBox/list?orderBy=sendtime desc', '', '', '1', '', '1', '2015-11-14 11:14:30', '1', '2015-11-24 18:01:46', '', '0');
INSERT INTO `sys_resource` VALUES ('9bc1aa1053144a608b73f6fbd841d1c6', '10', '0,1,3,10,', '删除', '100', '', '', '', '0', 'sys:dict:del', '1', '2015-12-24 22:24:04', '1', '2015-12-24 22:24:31', '', '0');
INSERT INTO `sys_resource` VALUES ('9fdf5971a9e64fac8bbffec2825a5f97', '27', '0,1,27,', '我的好友', '11000', '/iim/contact/myFriends', '', '', '1', '', '1', '2015-12-28 22:10:06', '1', '2016-08-14 22:57:08', '', '0');
INSERT INTO `sys_resource` VALUES ('a4c3dcee6cbc4fc6a0bf617d8619edf3', '17', '0,1,3,17,', '增加', '70', '', '', '', '0', 'sys:office:add', '1', '2015-12-20 21:18:52', '1', '2015-12-20 21:18:52', '', '0');
INSERT INTO `sys_resource` VALUES ('af0a174b4f424bc09a8cc0db83a64105', '27', '0,1,27,', '通讯录', '120', '/iim/contact/index', '', '', '1', '', '1', '2015-11-11 16:49:02', '1', '2015-11-11 16:49:02', '', '0');
INSERT INTO `sys_resource` VALUES ('b9a776f5d7194406bb466388e3af9d08', '20', '0,1,3,20,', '导出', '160', '', '', '', '0', 'sys:user:export', '1', '2015-12-19 21:48:34', '1', '2015-12-19 21:48:34', '', '0');
INSERT INTO `sys_resource` VALUES ('c6e0080e06014abd9240f870aadf3200', '14', '0,1,3,14,', '增加', '70', '', '', '', '0', 'sys:area:add', '1', '2015-12-24 21:35:39', '1', '2015-12-24 21:35:39', '', '0');
INSERT INTO `sys_resource` VALUES ('de7c50d276454f80881c41a096ecf55c', '7', '0,1,3,7,', '删除', '160', '', '', '', '0', 'sys:role:del', '1', '2015-12-23 21:59:46', '1', '2015-12-23 21:59:46', '', '0');
INSERT INTO `sys_resource` VALUES ('ec7cf7a144a440cab217aabd4ffb7788', '4', '0,1,3,4,', '查看', '130', '', '', '', '0', 'sys:menu:view', '1', '2015-12-20 19:02:54', '1', '2015-12-20 19:02:54', '', '0');
INSERT INTO `sys_resource` VALUES ('f34887a78fa245c1977603ca7dc98e11', '20', '0,1,3,20,', '导入', '130', '', '', '', '0', 'sys:user:import', '1', '2015-12-19 21:48:13', '1', '2015-12-19 21:48:44', '', '0');
INSERT INTO `sys_resource` VALUES ('f93f9a3a2226461dace3b8992cf055ba', '7', '0,1,3,7,', '权限设置', '130', '', '', '', '0', 'sys:role:auth', '1', '2015-12-23 21:36:06', '1', '2015-12-23 21:36:06', '', '0');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `role_code` varchar(255) DEFAULT NULL COMMENT '英文名称',
  `role_type` varchar(255) DEFAULT NULL COMMENT '角色类型',
  `data_scope` char(1) DEFAULT NULL COMMENT '数据范围',
  `is_sys` varchar(64) DEFAULT NULL COMMENT '是否系统数据',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `dept_id` varchar(32) DEFAULT NULL COMMENT ' 直属机构ID',
  PRIMARY KEY (`id`),
  KEY `sys_role_del_flag` (`del_flag`),
  KEY `sys_role_enname` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1c54e003c1fc4dcd9b087ef8d48abac3', '管理员', 'CJ_ADMIN', 'assignment', '1', '1', '1', '2015-11-11 15:59:43', '1', '2016-08-14 15:31:42', '', '0', null);
INSERT INTO `sys_role` VALUES ('5', '本部门管理员1', 'c2', 'security-role', '8', '0', '1', '2013-05-27 08:00:00', '1', '2015-12-23 20:18:16', '111111111', '0', null);
INSERT INTO `sys_role` VALUES ('781acb2361244e49aef509c8688c3ec2', '人事', 'hr', 'user', '8', '1', '1', '2016-06-17 01:12:55', '1', '2016-06-17 01:12:55', '', '0', null);
INSERT INTO `sys_role` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '部门管理员', 'depart', 'assignment', '8', '0', '1', '2015-11-13 10:54:36', '1', '2016-08-14 15:31:51', '', '0', null);

-- ----------------------------
-- Table structure for `sys_role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `role_id` varchar(32) DEFAULT NULL,
  `resource_id` varchar(32) DEFAULT NULL,
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkj7e3cva1e2s3nsd0yghpbsnk` (`resource_id`),
  KEY `FK7urjh5xeujvp29nihwbs5b9kr` (`role_id`),
  CONSTRAINT `FK7urjh5xeujvp29nihwbs5b9kr` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FKkj7e3cva1e2s3nsd0yghpbsnk` FOREIGN KEY (`resource_id`) REFERENCES `sys_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES ('1c54e003c1fc4dcd9b087ef8d48abac3', '1', '1');
INSERT INTO `sys_role_resource` VALUES ('5', '27', '10');
INSERT INTO `sys_role_resource` VALUES ('5', '3', '11');
INSERT INTO `sys_role_resource` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '67', '111');
INSERT INTO `sys_role_resource` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '79', '112');
INSERT INTO `sys_role_resource` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '7fe0397a90214f49adc9bbbe48e5ab69', '113');
INSERT INTO `sys_role_resource` VALUES ('5', '62', '12');
INSERT INTO `sys_role_resource` VALUES ('5', '67', '13');
INSERT INTO `sys_role_resource` VALUES ('5', '79', '14');
INSERT INTO `sys_role_resource` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '1', '15');
INSERT INTO `sys_role_resource` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '27', '16');
INSERT INTO `sys_role_resource` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '3', '17');
INSERT INTO `sys_role_resource` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '56e274e0ec1c41298e19ab46cf4e001f', '18');
INSERT INTO `sys_role_resource` VALUES ('caacf61017114120bcf7bf1049b6d4c3', '62', '19');
INSERT INTO `sys_role_resource` VALUES ('1c54e003c1fc4dcd9b087ef8d48abac3', '27', '2');
INSERT INTO `sys_role_resource` VALUES ('1c54e003c1fc4dcd9b087ef8d48abac3', '3', '3');
INSERT INTO `sys_role_resource` VALUES ('1c54e003c1fc4dcd9b087ef8d48abac3', '56e274e0ec1c41298e19ab46cf4e001f', '4');
INSERT INTO `sys_role_resource` VALUES ('1c54e003c1fc4dcd9b087ef8d48abac3', '62', '5');
INSERT INTO `sys_role_resource` VALUES ('1c54e003c1fc4dcd9b087ef8d48abac3', '67', '6');
INSERT INTO `sys_role_resource` VALUES ('1c54e003c1fc4dcd9b087ef8d48abac3', '79', '7');
INSERT INTO `sys_role_resource` VALUES ('1c54e003c1fc4dcd9b087ef8d48abac3', '7fe0397a90214f49adc9bbbe48e5ab69', '8');
INSERT INTO `sys_role_resource` VALUES ('5', '1', '9');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `company_id` varchar(64) DEFAULT NULL COMMENT '归属公司',
  `office_id` varchar(64) DEFAULT NULL COMMENT '归属部门',
  `username` varchar(100) NOT NULL COMMENT '登录名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `no` varchar(100) DEFAULT NULL COMMENT '工号',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) DEFAULT NULL COMMENT '手机',
  `user_type` char(1) DEFAULT NULL COMMENT '用户类型',
  `photo` varchar(1000) DEFAULT NULL COMMENT '用户头像',
  `login_ip` varchar(100) DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `login_flag` varchar(64) DEFAULT NULL COMMENT '是否可登录',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  `qrcode` varchar(1000) DEFAULT NULL COMMENT '二维码',
  `sign` varchar(450) DEFAULT NULL COMMENT '个性签名',
  `dept_id` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `rocodes` varchar(255) DEFAULT NULL,
  `role_codes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_user_office_id` (`office_id`),
  KEY `sys_user_login_name` (`username`),
  KEY `sys_user_company_id` (`company_id`),
  KEY `sys_user_update_date` (`update_date`),
  KEY `sys_user_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('0fb8ebbff20a46029596806aa077d3c2', '1', '5', 'fbb', '96e79218965eb72c92a549dd5a330112', '003', '范冰冰', '', '', '', '1', '/jeeplus/userfiles/0fb8ebbff20a46029596806aa077d3c2/images/u=1783243281,2583995645&fm=58.jpg', '0:0:0:0:0:0:0:1', '2016-08-14 18:11:18', '1', '1', '2016-02-24 23:25:41', '1', '2016-08-07 19:34:04', '', '0', '/jeeplus/userfiles/0fb8ebbff20a46029596806aa077d3c2/qrcode/0fb8ebbff20a46029596806aa077d3c2.png', null, null, null, null, null, 'CJ_ADMIN');
INSERT INTO `sys_user` VALUES ('1', '1', '5', 'admin', '96e79218965eb72c92a549dd5a330112', '13815874603', 'admin', '', '', 'rr', '', '/jeeplus/userfiles/1/images/7e3e6709c93d70cf14e01629ffdcd100baa12bff.jpg', '0:0:0:0:0:0:0:1', '2017-05-29 08:57:07', '1', '1', '2013-05-27 08:00:00', '1', '2016-08-10 21:40:18', 'test', '0', '/jeeplus/userfiles/1/qrcode/test.png', '你好啊', null, null, null, null, 'CJ_ADMIN');
INSERT INTO `sys_user` VALUES ('1e8149b33e774daa9a250f5a1a0ad23f', '1', '4', 'dzq', '96e79218965eb72c92a549dd5a330112', '006', '邓紫棋', '', '', '', '', '/jeeplus/userfiles/1e8149b33e774daa9a250f5a1a0ad23f/images/xin_03305061618075782724119.jpg', '0:0:0:0:0:0:0:1', '2016-07-24 20:17:34', '1', '1', '2016-02-24 23:27:47', '1e8149b33e774daa9a250f5a1a0ad23f', '2016-07-24 20:18:10', '', '0', '/jeeplus/userfiles/1e8149b33e774daa9a250f5a1a0ad23f/qrcode/1e8149b33e774daa9a250f5a1a0ad23f.png', null, null, null, null, null, 'CJ_ADMIN');
INSERT INTO `sys_user` VALUES ('7374fe91d19a4b739ae649334c0cc273', '1', '5', 'lxr', '96e79218965eb72c92a549dd5a330112', '004', '林心如', '', '', '', '', '/jeeplus/userfiles/7374fe91d19a4b739ae649334c0cc273/images/xin_03305061618075782724119.jpg', '0:0:0:0:0:0:0:1', '2016-08-09 01:17:28', '1', '1', '2016-02-24 23:26:20', '7374fe91d19a4b739ae649334c0cc273', '2016-07-24 20:54:23', '', '0', '/jeeplus/userfiles/7374fe91d19a4b739ae649334c0cc273/qrcode/7374fe91d19a4b739ae649334c0cc273.png', null, null, null, null, null, 'CJ_ADMIN');
INSERT INTO `sys_user` VALUES ('f7cc1c7e6f494818adffe1de5f2282fb', '1', '4', 'zw', '96e79218965eb72c92a549dd5a330112', '002', '赵薇', '', '', '', '', '/jeeplus/userfiles/f7cc1c7e6f494818adffe1de5f2282fb/images/7e3e6709c93d70cf14e01629ffdcd100baa12bff.jpg', '0:0:0:0:0:0:0:1', '2016-08-14 18:10:01', '1', '1', '2016-02-24 23:24:58', 'f7cc1c7e6f494818adffe1de5f2282fb', '2016-08-10 23:21:47', '', '0', '/jeeplus/userfiles/f7cc1c7e6f494818adffe1de5f2282fb/qrcode/f7cc1c7e6f494818adffe1de5f2282fb.png', '我是赵薇555', null, null, null, null, 'CJ_ADMIN');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  `id` varchar(32) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '创建日期',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识0：未删，1：已删',
  `remarks` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '更新日期',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKhh52n8vd4ny9ff4x9fb8v65qx` (`role_id`),
  CONSTRAINT `FKb40xxfch70f5qnyfw8yme1n1s` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKhh52n8vd4ny9ff4x9fb8v65qx` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1c54e003c1fc4dcd9b087ef8d48abac3', '', null, '0000-00-00 00:00:00', '0', null, null, '0000-00-00 00:00:00');
INSERT INTO `sys_user_role` VALUES ('1', '781acb2361244e49aef509c8688c3ec2', '', null, '0000-00-00 00:00:00', '0', null, null, '0000-00-00 00:00:00');
INSERT INTO `sys_user_role` VALUES ('1', 'caacf61017114120bcf7bf1049b6d4c3', '', null, '0000-00-00 00:00:00', '0', null, null, '0000-00-00 00:00:00');
