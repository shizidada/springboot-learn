package org.excel.operator.model.domain;

import lombok.Data;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/16 22:20
 * @see org.excel.operator.model.domain
 */
@Data
public class PasswordDO extends BaseDO {

  private Long passwordId;

  private Long accountId;

  private String password;
}
