package org.moose.provider.payment.service;

import org.moose.provider.payment.model.dto.PaymentRecordDTO;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 10:16
 * @see org.moose.provider.payment.service
 */
public interface PaymentRecordService {
  /**
   * 添加支付记录
   * @param paymentRecordDTO
   * @return
   */
  public boolean addPaymentRecord(PaymentRecordDTO paymentRecordDTO);
}
