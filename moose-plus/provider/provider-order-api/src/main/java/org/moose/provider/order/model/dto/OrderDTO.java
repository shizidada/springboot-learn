package org.moose.provider.order.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moose.commons.base.dto.BaseDTO;
import org.moose.provider.order.model.domain.LogisticsDO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/7 21:25
 * @see org.moose.provider.order.model.dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderDTO extends BaseDTO implements Serializable {
  private static final long serialVersionUID = 6878453514413131235L;

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

  /**
   * 物流信息
   */
  private LogisticsDTO logisticsDTO;
}
