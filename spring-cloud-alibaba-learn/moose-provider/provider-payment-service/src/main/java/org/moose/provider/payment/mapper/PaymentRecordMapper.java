package org.moose.provider.payment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.moose.provider.payment.model.domain.PaymentRecordDO;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 10:31
 * @see org.moose.provider.payment.mapper
 */
@Mapper
public interface PaymentRecordMapper {

  /**
   * 添加支付记录
   *
   * @param paymentRecordDO 支付信息
   * @return 是否成功
   */
  int insertPaymentRecord(PaymentRecordDO paymentRecordDO);
}
