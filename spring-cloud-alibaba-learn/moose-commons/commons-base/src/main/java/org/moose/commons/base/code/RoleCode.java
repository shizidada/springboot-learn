package org.moose.commons.base.code;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/5 20:02
 * @see org.moose.commons.base.dto
 */
public enum RoleCode {
  // 300001

  /**
   * 角色不能为空
   */
  ROLE_MUST_NOT_BE_NULL(300001, "角色不能为空");

  private final Integer code;

  private final String message;

  RoleCode(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
