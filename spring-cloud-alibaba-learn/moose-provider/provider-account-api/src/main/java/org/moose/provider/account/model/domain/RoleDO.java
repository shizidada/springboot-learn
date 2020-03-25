package org.moose.provider.account.model.domain;

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
 * @date 2020 2020/3/23 22:28
 * @see org.moose.provider.account.model.domain
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDO extends BaseDO {

  /**
   * 角色 id
   */
  private String roleId;

  /**
   * 角色
   */
  private String role;

  /**
   * 账号 id
   */
  private String accountId;
}
