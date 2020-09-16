package org.moose.operator.model.dto;

import lombok.Data;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-05-21 22:39:22:39
 * @see org.moose.operator.model.dto
 */
@Data
public class PaymentInfoDTO {
  private String paymentId;
  private String receiveName;
  private String fromName;
}
