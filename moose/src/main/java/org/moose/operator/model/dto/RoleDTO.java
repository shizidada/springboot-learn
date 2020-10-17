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

  private static final long serialVersionUID = -6787365847689995969L;
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
