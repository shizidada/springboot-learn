package org.moose.provider.payment.model.domain;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moose.commons.base.domain.BaseDO;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 11:36
 * @see org.moose.provider.payment.model.domain
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountBalanceDO extends BaseDO {

  /**
   * 账户余额 id
   */
  private Long id;

  /**
   * 账户 id
   */
  private Long accountId;

  /**
   * 账户余额
   */
  private BigDecimal balance;
}
