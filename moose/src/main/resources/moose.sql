/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : moose

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 29/07/2020 22:32:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for clientdetails
-- ----------------------------
DROP TABLE IF EXISTS `clientdetails`;
CREATE TABLE `clientdetails` (
  `appId` varchar(128) NOT NULL,
  `resourceIds` varchar(256) DEFAULT NULL,
  `appSecret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `grantTypes` varchar(256) DEFAULT NULL,
  `redirectUrl` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additionalInformation` varchar(4096) DEFAULT NULL,
  `autoApproveScopes` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`appId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(128) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals` (
  `userId` varchar(256) DEFAULT NULL,
  `clientId` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` timestamp NULL DEFAULT NULL,
  `lastModifiedAt` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(128) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO `oauth_client_details` VALUES ('client', 'app-resources', '$2a$10$zImwN.AOfOTU6m9xewoDZOqKHL3YThE4naYi5E1g13ZW2sjF.di8S', 'app', 'password,refresh_token', NULL, NULL, 86400, 2592000, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(128) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
  `code` varchar(256) DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_account
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `account_id` varchar(64) NOT NULL,
  `account_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户名',
  `status` varchar(1) COLLATE utf8_bin DEFAULT '1' COMMENT '帐号启用状态:0->禁用；1->启用',
  `phone` varchar(11) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '手机号',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  UNIQUE KEY `uniq_account_id` (`account_id`),
  UNIQUE KEY `uniq_account_name` (`account_name`) USING BTREE,
  KEY `idx_account_id` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='账号表';

-- ----------------------------
-- Table structure for t_password
-- ----------------------------
DROP TABLE IF EXISTS `t_password`;
CREATE TABLE `t_password` (
  `password_id` varchar(64) NOT NULL COMMENT '密码ID',
  `account_id` varchar(64) NOT NULL COMMENT '账号ID',
  `password` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  UNIQUE KEY `uniq_password_id` (`password_id`),
  UNIQUE KEY `uniq_account_id` (`account_id`),
  KEY `t_account_password_ibfk_1` (`account_id`),
  KEY `idx_password_id` (`password_id`),
  CONSTRAINT `t_account_password_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `t_account` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='密码表';

DROP TABLE IF EXISTS `t_sms_verify`;
CREATE TABLE `t_sms_verify` (
  `id` bigint(32) PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT '短信验证码ID',
  `phone` char(11) COLLATE utf8_bin NOT NULL COMMENT '手机号码',
  `type` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '短信类型',
  `code` char(6) COLLATE utf8_bin NOT NULL COMMENT '验证码',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  KEY `idx_phone` (`phone`),
  KEY `idx_type` (`type`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='短信验证码表';

DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info` (
  `user_id` varchar(64) PRIMARY KEY NOT NULL COMMENT '用户Id',
  `username` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户名',
  `account_id` varchar(64) NOT NULL,
  `account_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户名',
  `phone` varchar(11) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '手机号',
  `gender` char(1) COLLATE utf8_bin NOT NULL COMMENT '性别 male 1； female 2；un_known or hide 0',
  `avatar` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '头像',
  `email` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `job` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '职位',
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  UNIQUE KEY `uniq_phone` (`phone`),
  UNIQUE KEY `uniq_user_id` (`user_id`),
  UNIQUE KEY `uniq_username` (`username`),
  UNIQUE KEY `uniq_account_id` (`account_id`),
  UNIQUE KEY `uniq_account_name` (`account_name`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_update_time` (`update_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信息表';

DROP TABLE IF EXISTS `t_dynamic_record`;
CREATE TABLE `t_dynamic_record` (
  `dr_id` varchar(64) PRIMARY KEY NOT NULL COMMENT '动态记录Id',
  `user_id` varchar(64) NOT NULL COMMENT '用户Id',
  `title` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT "" COMMENT '动态标题',
  `content` varchar(500) COLLATE utf8_bin NOT NULL DEFAULT "" COMMENT '动态描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  KEY `idx_dr_id` (`dr_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_update_time` (`update_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='发布动态记录表';

DROP TABLE IF EXISTS `t_file_record`;
CREATE TABLE `t_file_record` (
  `fr_id` char(64) PRIMARY KEY NOT NULL COMMENT '文件Id',
  `user_id` char(64) NOT NULL COMMENT '用户Id',
  `e_tag` char(32) COLLATE utf8_bin NOT NULL DEFAULT "" COMMENT 'oss 上传标记',
  `file_url` varchar(120) COLLATE utf8_bin NOT NULL DEFAULT "" COMMENT 'oss 文件路径',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  KEY `idx_f_id` (`f_id`),
  KEY `idx_e_tag` (`e_tag`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_update_time` (`update_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='文件记录表';

SET FOREIGN_KEY_CHECKS = 1;
