SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_member`
-- ----------------------------
DROP TABLE IF EXISTS `t_account_member`;
CREATE TABLE `t_account_member` (
  `id` bigint(32) NOT NULL,
  `account_name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '昵称',
  `phone` varchar(64) DEFAULT NULL COMMENT '手机号码',
  `status` int(1) DEFAULT NULL COMMENT '帐号启用状态:0->禁用；1->启用',
  `icon` varchar(500) DEFAULT NULL COMMENT '头像',
  `gender` int(1) DEFAULT NULL COMMENT '性别：0->未知；1->男；2->女',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `personalized_signature` varchar(200) DEFAULT NULL COMMENT '个性签名',
  `source_type` int(1) DEFAULT NULL COMMENT '用户来源',
  `create_time` timestamp DEFAULT NULL COMMENT '注册时间',
  `update_time` timestamp DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_account_name` (`account_name`),
  UNIQUE KEY `idx_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账号会员表';

-- ----------------------------
--  Table structure for `t_member_password`
-- ----------------------------
DROP TABLE IF EXISTS `t_account_member_password`;
CREATE TABLE `t_account_member_password` (
  `id` bigint(32) NOT NULL COMMENT '密码主键',
  `account_id` bigint(20) NOT NULL COMMENT '会员ID',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `create_time` timestamp DEFAULT NULL COMMENT '注册时间',
  `update_time` timestamp DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `account_id` (`account_id`),
  CONSTRAINT `t_account_member_password_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `t_account_member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='密码表';

SET FOREIGN_KEY_CHECKS = 1;
