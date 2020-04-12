package org.moose.business.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.moose.business.user.model.vo.ProfileInfoVo;
import org.moose.business.user.service.ProfileService;
import org.moose.commons.base.dto.ResponseResult;
import org.moose.provider.account.model.dto.AccountDTO;
import org.moose.provider.account.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/5 22:50
 * @see org.moose.business.user.service.impl
 */
@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {

  @Reference(version = "1.0.0")
  private AccountService accountService;

  @Override public ResponseResult<?> getProfileInfo() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String accountName = authentication.getName();

    AccountDTO account = accountService.getAccountByAccountName(accountName);

    ProfileInfoVo profileInfo = new ProfileInfoVo();
    BeanUtils.copyProperties(account, profileInfo);

    return new ResponseResult<>(profileInfo);
  }
}
