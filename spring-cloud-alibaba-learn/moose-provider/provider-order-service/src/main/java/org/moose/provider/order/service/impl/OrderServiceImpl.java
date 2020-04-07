package org.moose.provider.order.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.moose.commons.base.dto.ResponseResult;
import org.moose.provider.order.model.dto.OrderDTO;
import org.moose.provider.order.service.OrderService;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/7 21:25
 * @see org.moose.provider.order.service.impl
 */
@Component
@Service(version = "1.0.0")
public class OrderServiceImpl implements OrderService {

  @Override public ResponseResult<?> createOrder(OrderDTO orderDTO) {
    return null;
  }
}
