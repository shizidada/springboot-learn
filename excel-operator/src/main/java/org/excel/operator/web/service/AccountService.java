package org.excel.operator.web.service;

import javax.servlet.http.HttpServletRequest;
import org.excel.operator.model.dto.AccountDTO;
import org.excel.operator.model.dto.RegisterInfoDTO;
import org.excel.operator.web.security.CustomUserDetails;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/17 13:10
 * @see org.excel.operator.web.service
 */
public interface AccountService {

  /**
   * 注册
   *
   * @param registerInfoDTO 注册信息
   * @param request         #HttpServletRequest
   * @return 是否注册成功
   */
  boolean register(HttpServletRequest request, RegisterInfoDTO registerInfoDTO);

  /**
   * 更具账号查找用户
   *
   * @param accountName 账号
   * @return AccountModel
   */
  AccountDTO getByAccountName(String accountName);

  /**
   * 获取用户信息
   *
   * @return AccountDTO
   */
  AccountDTO getAccountInfo();

  /**
   * 判断账号是否登陆
   *
   * @return 是否登陆
   */
  boolean isLogin();

  /**
   * 获取授权信息
   *
   * @return 授权信息
   */
  Object getPrincipal();
}
