package org.moose.account.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.moose.account.service.AccountService;
import org.apache.dubbo.config.annotation.Service;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/7 23:38
 * @see org.moose.account.service.impl
 */
@Service(version = "1.0.0")
@Slf4j
public class AccountServiceImpl implements AccountService {

  @Override
  public String login(String accountName, String password) {
    log.info(accountName + " :: " + password);
    return "Hello " + accountName + ", Welcome ~";
  }
}
