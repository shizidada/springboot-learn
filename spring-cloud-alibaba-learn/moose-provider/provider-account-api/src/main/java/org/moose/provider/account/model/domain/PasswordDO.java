package org.moose.provider.account.model.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.moose.commons.base.domain.BaseDO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/11 23:06
 * @see org.moose.provider.account.model.domain
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PasswordDO extends BaseDO {
  /**
   * 密码 id
   */
  private Long passwordId;

  /**
   * 关联账号 id
   */
  private Long accountId;

  /**
   * 密码
   */
  private String password;
}
