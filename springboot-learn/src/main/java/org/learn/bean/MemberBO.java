package org.learn.bean;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * `id` BIGINT (20) NOT NULL,
 * `member_level_id` BIGINT (20) DEFAULT NULL,
 * `username` VARCHAR (64) DEFAULT NULL COMMENT '用户名',
 * `nickname` VARCHAR (64) DEFAULT NULL COMMENT '昵称',
 * `phone` VARCHAR (64) DEFAULT NULL COMMENT '手机号码',
 * `status` INT (1) DEFAULT NULL COMMENT '帐号启用状态:0->禁用；1->启用',
 * `create_time` DATETIME DEFAULT NULL COMMENT '注册时间',
 * `icon` VARCHAR (500) DEFAULT NULL COMMENT '头像',
 * `gender` INT (1) DEFAULT NULL COMMENT '性别：0->未知；1->男；2->女',
 * `birthday` date DEFAULT NULL COMMENT '生日',
 * `city` VARCHAR (64) DEFAULT NULL COMMENT '所做城市',
 * `job` VARCHAR (100) DEFAULT NULL COMMENT '职业',
 * `personalized_signature` VARCHAR (200) DEFAULT NULL COMMENT '个性签名',
 * `source_type` INT (1) DEFAULT NULL COMMENT '用户来源',
 * `integration` INT (11) DEFAULT NULL COMMENT '积分',
 * `growth` INT (11) DEFAULT NULL COMMENT '成长值',
 * `luckey_count` INT (11) DEFAULT NULL COMMENT '剩余抽奖次数',
 * `history_integration` INT (11) DEFAULT NULL COMMENT '历史积分数量',
 */

@Data
@TableName("t_member")
public class MemberBO {

    public Long id;

    @NotNull(message = "用户名不允许为空")
    public String username;

    public String nickname;

    @NotNull(message = "手机号码不允许为空")
    public Long phone;

    public Integer status;

    @TableField("create_time")
    public Date createTime;

    public String icon;

    public Integer gender;

    public Date birthday;

}
