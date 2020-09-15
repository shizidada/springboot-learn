package org.moose.operator.model.domain;

import lombok.Data;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/16 22:20
 * @see org.moose.operator.model.domain
 */
@Data
public class AccountDO extends BaseDO {

  /**
   * 账号 Id
   */
  private Long accountId;

  /**
   * 账号
   */
  private String accountName;

  /**
   * 状态
   */
  private String status;

  /**
   * 手机号码
   */
  private String phone;

  /**
   * 头像
   */
  private String avatar;

  /**
   * 性别
   */
  private String gender;
}
