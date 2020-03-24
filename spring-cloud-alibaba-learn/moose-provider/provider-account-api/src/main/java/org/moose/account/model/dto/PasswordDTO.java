package org.moose.account.model.dto;

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
 * @date 2020 2020/3/11 23:06
 * @see org.moose.account.model.dto
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PasswordDTO extends BaseDTO implements Serializable {

  /**
   * 密码 id
   */
  private String passwordId;

  /**
   * 关联账号 id
   */
  private String accountId;

  /**
   * 密码
   */
  private String password;
}
