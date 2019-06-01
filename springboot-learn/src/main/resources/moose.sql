/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost
 Source Database       : moose

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : utf-8

 Date: 06/01/2019 09:18:23 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_admin`
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `id` bigint(20) NOT NULL,
  `username` varchar(64) DEFAULT NULL,
  `icon` varchar(500) DEFAULT NULL COMMENT '头像',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(200) DEFAULT NULL COMMENT '昵称',
  `note` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` int(1) DEFAULT '1' COMMENT '帐号启用状态：0->禁用；1->启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台用户表';

-- ----------------------------
--  Table structure for `t_admin_password`
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_password`;
CREATE TABLE `t_admin_password` (
  `id` bigint(32) NOT NULL,
  `admin_id` bigint(20) NOT NULL,
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`),
  KEY `admin_id` (`admin_id`),
  CONSTRAINT `t_admin_password_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `t_admin` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='密码表';

-- ----------------------------
--  Table structure for `t_member`
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member` (
  `id` bigint(32) NOT NULL,
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `phone` varchar(64) DEFAULT NULL COMMENT '手机号码',
  `status` int(1) DEFAULT NULL COMMENT '帐号启用状态:0->禁用；1->启用',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `icon` varchar(500) DEFAULT NULL COMMENT '头像',
  `gender` int(1) DEFAULT NULL COMMENT '性别：0->未知；1->男；2->女',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `city` varchar(64) DEFAULT NULL COMMENT '所做城市',
  `job` varchar(100) DEFAULT NULL COMMENT '职业',
  `personalized_signature` varchar(200) DEFAULT NULL COMMENT '个性签名',
  `source_type` int(1) DEFAULT NULL COMMENT '用户来源',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`),
  UNIQUE KEY `idx_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员表';

-- ----------------------------
--  Table structure for `t_member_password`
-- ----------------------------
DROP TABLE IF EXISTS `t_member_password`;
CREATE TABLE `t_member_password` (
  `id` bigint(32) NOT NULL COMMENT '密码主键',
  `member_id` bigint(20) NOT NULL COMMENT '会员ID',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`),
  CONSTRAINT `t_member_password_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `t_member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='密码表';

-- ----------------------------
--  Table structure for `t_member_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_member_role`;
CREATE TABLE `t_member_role` (
  `id` bigint(20) NOT NULL COMMENT '角色主键',
  `member_id` bigint(20) NOT NULL COMMENT '会员ID',
  `role_name` varchar(64) NOT NULL DEFAULT '' COMMENT '角色名称',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '角色描述',
  PRIMARY KEY (`id`),
  KEY `t_member_role_ibfk_1` (`member_id`),
  CONSTRAINT `t_member_role_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `t_member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

SET FOREIGN_KEY_CHECKS = 1;
