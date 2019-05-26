package org.learn.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * `id` BIGINT (20) NOT NULL,
 * `m_id` BIGINT (20) NOT NULL,
 * `password` VARCHAR (64) DEFAULT NULL COMMENT '密码',
 */

@Data
@TableName("t_member_password")
public class MemberPasswordBO {

    public Long id;

    @TableField("m_id")
    public Long mId;

    @NotNull(message = "密码不能为空")
    public String password;
}
