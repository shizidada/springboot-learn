package org.excel.operator.rocketmq.entity;

import lombok.Data;

/**
 *
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-05-21 22:39:22:39
 * @see org.excel.operator.rocketmq.entity
 */
@Data
public class PaymentInfo {
    private String paymentId;
    private String receiveName;
    private String fromName;
}
