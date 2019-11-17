package org.excel.operator.service;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/17 13:10
 * @see org.excel.operator.service
 */
public interface AccountService {

  /**
   * 登录
   */
  boolean login(String accountName, String password);
}
