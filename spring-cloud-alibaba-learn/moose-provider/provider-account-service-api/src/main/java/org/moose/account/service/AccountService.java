package org.moose.account.service;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/7 23:35
 * @see org.moose.account.service
 */
public interface AccountService {
  /**
   * 登录
   *
   * @param accountName 账号
   * @param password 密码
   * @return 登录信息
   */
  String login(String accountName, String password);
}
