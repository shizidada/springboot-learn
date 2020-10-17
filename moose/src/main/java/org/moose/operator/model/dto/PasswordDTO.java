package org.moose.operator.model.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/17 00:07
 * @see org.moose.operator.model.dto
 */
@Data
public class PasswordDTO extends BaseDTO implements Serializable {

  private static final long serialVersionUID = 2459582478102836423L;
  private String passwordId;

  private String accountId;

  private String password;
}
