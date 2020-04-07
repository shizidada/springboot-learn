package org.moose.provider.order.service;

import org.moose.commons.base.dto.ResponseResult;
import org.moose.provider.order.model.dto.OrderDTO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/7 21:23
 * @see org.moose.provider.order
 */
public interface OrderService {

  /**
   * 创建订单
   *
   * @return ResponseResult
   */
  public ResponseResult<?> createOrder(OrderDTO orderDTO);
}
