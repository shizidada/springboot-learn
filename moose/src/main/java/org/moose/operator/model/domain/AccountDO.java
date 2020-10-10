package org.moose.operator.model.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = true)
public class AccountDO extends BaseDO {

  /**
   * 账号 Id
   */
  private String accountId;

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
}
