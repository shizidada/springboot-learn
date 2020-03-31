package org.moose.provider.account.model.domain;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.moose.commons.base.entity.BaseDO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/11 22:58
 * @see org.moose.provider.account.model.domain
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountDO extends BaseDO {

  /**
   * 账号 ID
   */
  private Long accountId;

  /**
   * 账号名称
   */
  private String accountName;

  /**
   * 昵称
   */
  private String nickName;

  /**
   * 手机号码
   */
  private String phone;

  /**
   * 状态
   */
  private Integer status;

  /**
   * 头像
   */
  private String icon;

  /**
   * 性别
   */
  private Integer gender;

  /**
   * 生日
   */
  private Date birthday;

  /**
   * 注册来源
   */
  private Integer sourceType;

  @Override public String toString() {
    return "AccountDO{" +
        "accountId=" + accountId +
        ", accountName='" + accountName + '\'' +
        ", nickName='" + nickName + '\'' +
        ", phone='" + phone + '\'' +
        ", status=" + status +
        ", icon='" + icon + '\'' +
        ", gender=" + gender +
        ", birthday=" + birthday +
        ", sourceType='" + sourceType + '\'' +
        '}';
  }
}
