/*
MySQL Data Transfer
Source Host: localhost
Source Database: moose
Target Host: localhost
Target Database: moose
Date: 2020/5/28 16:39:31
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for t_account
-- ----------------------------
CREATE TABLE `t_account` (
  `account_id` bigint(64) NOT NULL,
  `account_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户名',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '帐号启用状态:0->禁用；1->启用',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `icon` varchar(250) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '头像',
  UNIQUE KEY `idx_account_name` (`account_name`),
  KEY `account_id` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='账号表';

-- ----------------------------
-- Table structure for t_excel_info
-- ----------------------------
CREATE TABLE `t_excel_info` (
  `id` bigint(64) DEFAULT NULL,
  `iccid` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'iccid SIM卡卡号',
  `operators` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '运营商',
  `receiver` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人',
  `phone` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '收货手机号',
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '收货地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `platform` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '不同平台数据: tianpeng : xiaoming',
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='导入excel 信息表';

-- ----------------------------
-- Table structure for t_password
-- ----------------------------
CREATE TABLE `t_password` (
  `password_id` bigint(32) NOT NULL COMMENT '密码ID',
  `account_id` bigint(32) NOT NULL COMMENT '账号ID',
  `password` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  KEY `t_account_password_ibfk_1` (`account_id`),
  KEY `password_id` (`password_id`),
  CONSTRAINT `t_account_password_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `t_account` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='密码表';

-- ----------------------------
-- Table structure for tb_permission
-- ----------------------------
CREATE TABLE `tb_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父权限',
  `name` varchar(64) NOT NULL COMMENT '权限名称',
  `enname` varchar(64) NOT NULL COMMENT '权限英文名称',
  `url` varchar(255) NOT NULL COMMENT '授权路径',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
CREATE TABLE `tb_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父角色',
  `name` varchar(64) NOT NULL COMMENT '角色名称',
  `enname` varchar(64) NOT NULL COMMENT '角色英文名称',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for tb_role_permission
-- ----------------------------
CREATE TABLE `tb_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色 ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限 ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码，加密存储',
  `phone` varchar(20) DEFAULT NULL COMMENT '注册手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '注册邮箱',
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `phone` (`phone`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
CREATE TABLE `tb_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户 ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色 ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `t_account` VALUES ('653655131660746752', 'admin', '1', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '');
INSERT INTO `t_account` VALUES ('713376466800214016', '狮子', '1', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '');INSERT INTO `t_password` VALUES ('653655144210104320', '653655131660746752', '$2a$10$N2jovfQTDdIGrqoOj0.4dumGZKZDbBjBCTRav2I2kVZ4K3jHhj5yi', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO `t_password` VALUES ('713376467228033024', '713376466800214016', '$2a$10$dDqZgTCN2K0q02fmIoVF3.P8z6XI4v5hDAWO9El5yQC6VAjmz1/bK', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO `tb_permission` VALUES ('37', '0', '系统管理', 'System', '/', null, '2019-04-04 23:22:54', '2019-04-04 23:22:56');
INSERT INTO `tb_permission` VALUES ('38', '37', '用户管理', 'SystemUser', '/users/', null, '2019-04-04 23:25:31', '2019-04-04 23:25:33');
INSERT INTO `tb_permission` VALUES ('39', '38', '查看用户', 'SystemUserView', '', null, '2019-04-04 15:30:30', '2019-04-04 15:30:43');
INSERT INTO `tb_permission` VALUES ('40', '38', '新增用户', 'SystemUserInsert', '', null, '2019-04-04 15:30:31', '2019-04-04 15:30:44');
INSERT INTO `tb_permission` VALUES ('41', '38', '编辑用户', 'SystemUserUpdate', '', null, '2019-04-04 15:30:32', '2019-04-04 15:30:45');
INSERT INTO `tb_permission` VALUES ('42', '38', '删除用户', 'SystemUserDelete', '', null, '2019-04-04 15:30:48', '2019-04-04 15:30:45');
INSERT INTO `tb_role` VALUES ('37', '0', '超级管理员', 'admin', null, '2019-04-04 23:22:03', '2019-04-04 23:22:05');
INSERT INTO `tb_role_permission` VALUES ('37', '37', '37');
INSERT INTO `tb_role_permission` VALUES ('38', '37', '38');
INSERT INTO `tb_role_permission` VALUES ('39', '37', '39');
INSERT INTO `tb_role_permission` VALUES ('40', '37', '40');
INSERT INTO `tb_role_permission` VALUES ('41', '37', '41');
INSERT INTO `tb_role_permission` VALUES ('42', '37', '42');
INSERT INTO `tb_user` VALUES ('37', 'admin', '$2a$10$9ZhDOBp.sRKat4l14ygu/.LscxrMUcDAfeVOEPiYwbcRkoB09gCmi', '15888888888', 'lee.lusifer@gmail.com', '2019-04-04 23:21:27', '2019-04-04 23:21:29');
INSERT INTO `tb_user_role` VALUES ('37', '37', '37');
