DROP TABLE IF EXISTS `t_account_balance`;
CREATE TABLE `t_account_balance` (
  `id`          bigint(64) NOT NULL
  COMMENT '账户余额 主键',
  `account_id`  bigint(64) NOT NULL
  COMMENT '账号 id',
  `balance`     decimal(10, 2) DEFAULT '0.00'
  COMMENT '支付金额',
  `create_time` datetime       DEFAULT NULL
  COMMENT '创建时间',
  `update_time` datetime       DEFAULT NULL
  COMMENT '修改时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '账户余额';

DROP TABLE IF EXISTS `t_payment_record`;
CREATE TABLE `t_payment_record` (
  `id`               bigint(64) NOT NULL
  COMMENT '支付主键',
  `account_id`       varchar(64)    DEFAULT NULL
  COMMENT '账号 id',
  `payment_amount`   decimal(10, 2) DEFAULT '0.00'
  COMMENT '支付金额',
  `payment_status`   int(1)         DEFAULT NULL
  COMMENT '支付状态: 0 -> 未支付; 1 -> 已支付',
  `payment_method`   int(1)         DEFAULT NULL
  COMMENT '0 -> 银行卡; 1 -> 支付宝; 2 -> 微信',
  `payment_category` varchar(255)   DEFAULT ''
  COMMENT '支付分类',
  `payment_num`      varchar(255)   DEFAULT ''
  COMMENT '支付流水编号',
  `payment_tag`      varchar(255)   DEFAULT ''
  COMMENT '支付标签和备注',
  `create_time`      datetime       DEFAULT NULL
  COMMENT '创建时间',
  `update_time`      datetime       DEFAULT NULL
  COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_account_id` (`account_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '支付信息表';
