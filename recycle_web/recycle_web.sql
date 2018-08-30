/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50713
Source Host           : 127.0.0.1:3306
Source Database       : recycle_web

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2018-07-13 15:59:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bank_card
-- ----------------------------
DROP TABLE IF EXISTS `bank_card`;
CREATE TABLE `bank_card` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bank_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '银行名称',
  `back_code` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '银行标识码',
  `is_support` int(1) DEFAULT '0' COMMENT '是否支持该银行卡(0是1否)',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bank_name` (`bank_name`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支持绑定银行卡表';

-- ----------------------------
-- Table structure for evaluate_property
-- ----------------------------
DROP TABLE IF EXISTS `evaluate_property`;
CREATE TABLE `evaluate_property` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `evaluate_price` decimal(10,2) DEFAULT '0.00' COMMENT '评估价格',
  `rent_day` int(10) DEFAULT '0' COMMENT '租赁时间',
  `platform_percent` double DEFAULT '0' COMMENT '平台服务费百分比',
  `rent_day_fee` decimal(10,2) DEFAULT '0.00' COMMENT '日租金',
  `break_contact_fee_percent` decimal(10,2) DEFAULT '0.00' COMMENT '违约金百分比',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评估属性表';

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `transantion_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单编号',
  `phone` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号码',
  `approve_fee` decimal(10,2) DEFAULT '0.00' COMMENT '审核批准允许的金额',
  `recycle_total_fee` decimal(10,2) DEFAULT '0.00' COMMENT '回收总额',
  `platform_fee` decimal(10,2) DEFAULT '0.00' COMMENT '平台服务费',
  `recylce_remain_total_fee` decimal(10,2) DEFAULT '0.00' COMMENT '回收到账总额',
  `rent_day` int(10) DEFAULT '0' COMMENT '租赁天数',
  `rent_day_fee` decimal(10,2) DEFAULT '0.00' COMMENT '日租金',
  `rent_total_fee` decimal(10,2) DEFAULT '0.00' COMMENT '总租金',
  `expire_recycle_total_fee` decimal(10,2) DEFAULT '0.00' COMMENT '到期回购总金额',
  `status` int(1) DEFAULT '0' COMMENT '订单状态(0待审核1待确认2待结款3已结款4已到期5已违约6已完成7已关闭8已取消)',
  `break_contact_day` int(10) DEFAULT '0' COMMENT '违约天数',
  `break_contact_total_fee` decimal(10,2) DEFAULT '0.00' COMMENT '总的违约金',
  `remark` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '说明内容',
  `rent_start_time` date NOT NULL COMMENT '起租日',
  `rent_end_time` date NOT NULL COMMENT '到期日',
  `channel` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单来源渠道',
  `check_status` int(1) DEFAULT '2' COMMENT '审核状态(0通过1不通过2未审核)',
  `check_user` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审核人',
  `check_time` date DEFAULT NULL COMMENT '审核时间',
  `check_content` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审核内容',
  `suppose_result` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '建议结果(报告确认)',
  `apply_time` timestamp NULL DEFAULT NULL COMMENT '提交订单时间',
  `payed_time` timestamp NULL DEFAULT NULL COMMENT '结款时间',
  `complete_time` timestamp NULL DEFAULT NULL COMMENT '还款时间',
  `finish_time` timestamp NULL DEFAULT NULL COMMENT '审核不通过时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- ----------------------------
-- Table structure for repayment_trade_record
-- ----------------------------
DROP TABLE IF EXISTS `repayment_trade_record`;
CREATE TABLE `repayment_trade_record` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `transantion_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单编号',
  `trade_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '交易ID',
  `channel_transantion_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '渠道交易单号',
  `trade_total_fee` decimal(10,2) DEFAULT '0.00' COMMENT '交易总额',
  `in_fee` decimal(10,2) DEFAULT '0.00' COMMENT '入账金额',
  `poundage` decimal(10,2) DEFAULT '0.00' COMMENT '手续费',
  `poundage_percent` double DEFAULT '0' COMMENT '费率',
  `pay_channel` int(1) DEFAULT NULL COMMENT '支付通道',
  `trade_type` int(1) DEFAULT NULL COMMENT '交易类型(0代扣1代付2还款)',
  `trade_status` int(1) DEFAULT NULL COMMENT '交易状态(0未支付，支付成功，支付失败)',
  `receive_bank_account` varchar(48) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收款方账号(卡号)',
  `receive_bank_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收款方户名',
  `receive_bank_address` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收款方开户行地址',
  `pay_bank_account` varchar(48) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '付款方账号(卡号)',
  `pay_bank_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '付款方户名',
  `pay_bank_address` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '付款方开户行地址',
  `trade_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '交易时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='还款交易记录表';

-- ----------------------------
-- Table structure for sms_code_record
-- ----------------------------
DROP TABLE IF EXISTS `sms_code_record`;
CREATE TABLE `sms_code_record` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号码',
  `sms_code` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发送的验证码',
  `send_ip` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'IP',
  `remark` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `send_time` timestamp NULL DEFAULT NULL COMMENT '获取验证码的时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='获取短信验证码记录表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `login_psw` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '登录密码(加密不可逆储存)',
  `realname` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '真实姓名',
  `birthdate` date DEFAULT NULL COMMENT '出生日期',
  `age` int(3) unsigned DEFAULT NULL COMMENT '年龄',
  `sex` int(11) DEFAULT '0' COMMENT '性别(0-女，1-男，99-其他)',
  `id_card_type` int(11) DEFAULT NULL COMMENT '证件类型',
  `id_card_no` varchar(400) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '证件号码(加密存储)',
  `remark` varchar(400) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `is_del` int(11) DEFAULT '0' COMMENT '是否删除（0否1是）',
  `is_enable` int(1) DEFAULT '1' COMMENT '是否启用(0否1是)',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统-用户表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户姓名',
  `sex` int(1) DEFAULT '0' COMMENT '性别(0男1女99其它)',
  `phone` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号码',
  `id_card` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '身份证号码',
  `sesame_seed` int(5) DEFAULT NULL COMMENT '芝麻信用分',
  `is_credit` int(1) DEFAULT '0' COMMENT '身份认证(0未认证1已认证2认证中3认证失败)',
  `is_bank` int(1) DEFAULT '0' COMMENT '银行卡认证(0未认证1已认证2认证中3认证失败)',
  `is_phone` int(1) DEFAULT '0' COMMENT '手机运营商认证(0未认证1已认证2认证中3认证失败)',
  `is_seed` int(1) DEFAULT '0' COMMENT '芝麻认证(0未认证1已认证2认证中3认证失败)',
  `is_contact` int(11) DEFAULT '0' COMMENT '是否获取通讯录(0未获取1已获取2获取中3获取失败)',
  `is_black` int(1) DEFAULT NULL COMMENT '是否黑名单(0是1否)',
  `limit_times` int(11) DEFAULT '0' COMMENT '同一个手机号码限制每天获取短信的次数',
  `login_status` int(11) DEFAULT '0' COMMENT '登陆的状态(某时间内不登陆),0未过时1已过时',
  `login_code` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '登陆成功的验证码',
  `last_login_ip` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最近登陆的IP',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最近登陆时间',
  `register_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '注册时间',
  `modify_time` date DEFAULT NULL COMMENT '修改资料时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=530 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户登陆(或注册)表';

-- ----------------------------
-- Table structure for user_bank_card
-- ----------------------------
DROP TABLE IF EXISTS `user_bank_card`;
CREATE TABLE `user_bank_card` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_phone` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号码',
  `card_number` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '银行卡号',
  `card_phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '银行预留号码',
  `card_bank_address` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '银行卡开户地址',
  `is_bind` int(1) DEFAULT '1' COMMENT '是否绑定代扣(0是1否)',
  `bind_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '绑定时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_phone` (`user_phone`)
) ENGINE=InnoDB AUTO_INCREMENT=530 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户-银行卡绑定表';

-- ----------------------------
-- Table structure for user_cm_record
-- ----------------------------
DROP TABLE IF EXISTS `user_cm_record`;
CREATE TABLE `user_cm_record` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '接口类型(CREATE,LOGIN_FIRST,LOGIN_SECOND,RESEND_SMS,RESULT)',
  `user_phone` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号码',
  `user_pass` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机服务密码(可留)',
  `user_id_card` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '身份证号码',
  `user_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号码-认证姓名',
  `code` int(11) DEFAULT NULL COMMENT '返回码',
  `message` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提示信息',
  `data` text COLLATE utf8mb4_unicode_ci COMMENT '创建返回的任务信息(json)',
  `task_id` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务编码',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_phone` (`user_phone`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='运营商接口表';

-- ----------------------------
-- Table structure for user_contact
-- ----------------------------
DROP TABLE IF EXISTS `user_contact`;
CREATE TABLE `user_contact` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_phone` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号码',
  `contact_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系人手机号码',
  `permit_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '获取时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_phone` (`user_phone`)
) ENGINE=InnoDB AUTO_INCREMENT=530 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户-通讯录表';
