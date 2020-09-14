package org.moose.operator.model.domain;

import lombok.Data;

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
public class AccountDO extends BaseDO {

  private Long accountId;

  private String accountName;

  private String status;

  private String phone;

  private String avatar;
}
