package org.moose.operator.model.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author taohua
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
