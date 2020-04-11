package org.moose.provider.payment.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moose.commons.base.dto.BaseDTO;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 11:38
 * @see org.moose.provider.payment.model.dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountBalanceDTO extends BaseDTO implements Serializable {

  private static final long serialVersionUID = -6094287368389657758L;
  /**
   * 账户余额 id
   */
  private Long id;

  /**
   * 账户 id
   */
  private Long accountId;

  /**
   * 支付的金额
   */
  private BigDecimal balance;
}
