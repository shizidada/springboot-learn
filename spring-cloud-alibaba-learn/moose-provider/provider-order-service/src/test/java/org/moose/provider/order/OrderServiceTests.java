package org.moose.provider.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.moose.commons.base.snowflake.SnowflakeIdWorker;
import org.moose.provider.order.model.dto.LogisticsDTO;
import org.moose.provider.order.model.dto.OrderDTO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/7 23:22
 * @see org.moose.provider.order
 */
@Slf4j
public class OrderServiceTests {

  @Test
  public void createOrderTest() {
    SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(2, 16);

    OrderDTO orderDTO = new OrderDTO();
    long orderId = snowflakeIdWorker.nextId();
    orderDTO.setOrderId(orderId);

    orderDTO.setAccountId(696455000650428416L);
    orderDTO.setOrderStatus(0);
    orderDTO.setPayStatus(0);

    long productId = snowflakeIdWorker.nextId();
    orderDTO.setProductId(productId);

    orderDTO.setOrderAmount(new BigDecimal("0"));
    orderDTO.setCreateTime(LocalDateTime.now());
    orderDTO.setUpdateTime(LocalDateTime.now());

    LogisticsDTO logisticsDTO = new LogisticsDTO();
    long logisticsId = snowflakeIdWorker.nextId();
    logisticsDTO.setLogisticsId(logisticsId);
    logisticsDTO.setLogisticsNum("20200506000000001");
    logisticsDTO.setReceiverName("sike");
    logisticsDTO.setReceiverPhone("15678323232");
    logisticsDTO.setReceiverAddress("北京市朝阳区");
    logisticsDTO.setCreateTime(LocalDateTime.now());
    logisticsDTO.setUpdateTime(LocalDateTime.now());

    orderDTO.setLogisticsDTO(logisticsDTO);

    log.info("Create Order Info {}", orderDTO);
  }
}
