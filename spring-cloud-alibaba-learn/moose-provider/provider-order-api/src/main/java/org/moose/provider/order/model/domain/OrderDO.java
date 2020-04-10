package org.moose.provider.order.model.domain;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moose.commons.base.domain.BaseDO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/7 21:24
 * @see org.moose.provider.order.model.domain
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderDO extends BaseDO {

  /**
   * 订单 id
   */
  private Long orderId;

  /**
   * 账号 id
   */
  private Long accountId;

  /**
   * 订单状态
   */
  private Integer orderStatus;

  /**
   * 支付状态
   */
  private Integer payStatus;

  /**
   * 商品 id
   */
  private Long productId;

  /**
   * 订单金额
   */
  private BigDecimal orderAmount;
}
