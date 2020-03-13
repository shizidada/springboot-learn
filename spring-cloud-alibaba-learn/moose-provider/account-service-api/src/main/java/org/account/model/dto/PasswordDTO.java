package org.account.model.dto;

import lombok.Data;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/11 23:06
 * @see org.account.model.dto
 */
@Data
public class PasswordDTO {

  private Long passwordId;

  private Long accountId;

  private String password;
}
