SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_sms_code`
-- ----------------------------
DROP TABLE IF EXISTS `t_sms_code`;
CREATE TABLE `t_sms_code` (
  `id` bigint(64) NOT NULL COMMENT '验证码主键',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `verify_code` varchar(10) DEFAULT NULL COMMENT '短信校验码',
  `type` varchar(32) DEFAULT NULL COMMENT '短信校验码类型: register->注册;',
  `sms_token` varchar(32) DEFAULT NULL COMMENT '短信校验令牌',
  `expired_time` datetime DEFAULT NULL COMMENT '过期时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_phone_verify_code_type` (`phone`,`verify_code`,`type`,`sms_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信验证码表';
