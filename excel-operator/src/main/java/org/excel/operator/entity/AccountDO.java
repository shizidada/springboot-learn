package org.excel.operator.entity;

import lombok.Data;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/16 22:20
 * @see org.excel.operator.entity
 */
@Data
public class AccountDO extends BaseDO {

  private Long accountId;

  private String accountName;

  private String password;
}
