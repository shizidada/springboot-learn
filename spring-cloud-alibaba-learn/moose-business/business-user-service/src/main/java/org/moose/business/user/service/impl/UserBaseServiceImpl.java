package org.moose.business.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.moose.business.user.service.UserBaseService;
import org.moose.commons.base.dto.ResultCode;
import org.moose.commons.base.exception.BusinessException;
import org.moose.provider.account.model.dto.AccountDTO;
import org.moose.provider.account.service.AccountService;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-24 13:31:13:31
 * @see org.moose.business.user.service.impl
 */
@Slf4j
@Component
public class UserBaseServiceImpl implements UserBaseService {

  @Reference(version = "1.0.0")
  private AccountService accountService;

  @Override public AccountDTO findByPhone(String phone) {
    if (phone == null || StringUtils.isBlank(phone)) {
      throw new BusinessException(ResultCode.PHONE_MUST_NOT_BE_NULL);
    }
    AccountDTO accountDTO = accountService.getAccountByPhone(phone);
    return accountDTO;
  }
}
