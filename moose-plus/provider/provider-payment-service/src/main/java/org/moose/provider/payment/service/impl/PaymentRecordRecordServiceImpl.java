package org.moose.provider.payment.service.impl;

import javax.annotation.Resource;
import org.moose.commons.base.dto.ResultCode;
import org.moose.commons.provider.exception.ProviderRpcException;
import org.moose.provider.payment.mapper.PaymentRecordMapper;
import org.moose.provider.payment.model.domain.PaymentRecordDO;
import org.moose.provider.payment.model.dto.PaymentRecordDTO;
import org.moose.provider.payment.service.PaymentRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void addPaymentRecord(PaymentRecordDTO paymentRecordDTO) {
    if (paymentRecordDTO == null) {
      throw new ProviderRpcException(ResultCode.PAYMENT_INFO_MUST_NOT_BE_NULL);
    }

    PaymentRecordDO paymentRecordDO = new PaymentRecordDO();
    BeanUtils.copyProperties(paymentRecordDTO, paymentRecordDO);
    int result = paymentRecordMapper.insertPaymentRecord(paymentRecordDO);
    if (result < 0) {
      throw new ProviderRpcException(ResultCode.PAYMENT_BALANCE_RECORD_ADD_FAIL);
    }
  }
}
