package org.excel.operator.web.service;

import javax.servlet.http.HttpServletRequest;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.model.dto.AccountDTO;
import org.excel.operator.model.dto.RegisterInfoDTO;

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
  ResponseResult<Boolean> register(HttpServletRequest request, RegisterInfoDTO registerInfoDTO);

  /**
   * 根据账号查找用户
   *
   * @param accountName 账号
   * @return AccountDTO
   */
  AccountDTO getByAccountName(String accountName);

  /**
   * 根据手机号码查找用户
   *
   * @param phone 账号
   * @return AccountDTO
   */
  AccountDTO getAccountByPhone(String phone);

  /**
   * 获取用户信息
   *
   * @return ResponseResult<AccountDTO>
   */
  ResponseResult<AccountDTO> getAccountInfo();
}
