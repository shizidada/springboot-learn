package org.moose.provider.account.model.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moose.commons.base.dto.BaseDTO;

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
public class RoleDTO extends BaseDTO implements Serializable {

  /**
   * 角色 id
   */
  private Long roleId;

  /**
   * 角色
   */
  private String role;

  /**
   * 账号 id
   */
  private Long accountId;
}
