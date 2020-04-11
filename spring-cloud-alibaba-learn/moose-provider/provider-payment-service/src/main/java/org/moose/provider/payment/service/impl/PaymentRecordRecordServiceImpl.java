package org.moose.provider.payment.service.impl;

import javax.annotation.Resource;
import org.apache.dubbo.rpc.RpcException;
import org.moose.commons.base.code.PaymentCode;
import org.moose.provider.payment.mapper.PaymentRecordMapper;
import org.moose.provider.payment.model.domain.PaymentRecordDO;
import org.moose.provider.payment.model.dto.PaymentRecordDTO;
import org.moose.provider.payment.service.PaymentRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 11:06
 * @see org.moose.provider.payment.service.impl
 */
@Component
@Service(value = "1.0.0")
public class PaymentRecordRecordServiceImpl implements PaymentRecordService {

  @Resource
  private PaymentRecordMapper paymentRecordMapper;

  @Override
  public boolean addPaymentRecord(PaymentRecordDTO paymentRecordDTO) {
    if (paymentRecordDTO == null) {
      throw new RpcException(PaymentCode.PAYMENT_INFO_MUST_NOT_BE_NULL.getCode(),
          PaymentCode.PAYMENT_INFO_MUST_NOT_BE_NULL.getMessage());
    }

    PaymentRecordDO paymentRecordDO = new PaymentRecordDO();
    BeanUtils.copyProperties(paymentRecordDTO, paymentRecordDO);
    int result = paymentRecordMapper.insertPaymentRecord(paymentRecordDO);
    return result > 0;
  }
}
