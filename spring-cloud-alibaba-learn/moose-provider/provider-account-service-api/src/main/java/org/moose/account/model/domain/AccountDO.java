package org.moose.account.model.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.moose.commons.base.entity.BaseEntity;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/11 22:58
 * @see org.moose.account.model.domain
 */
@Getter
@Setter
@ToString
public class AccountDO extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -8010354800572850555L;

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
