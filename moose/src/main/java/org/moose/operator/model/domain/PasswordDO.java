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
public class PasswordDO extends BaseDO {

  private String passwordId;

  private String accountId;

  private String password;
}
