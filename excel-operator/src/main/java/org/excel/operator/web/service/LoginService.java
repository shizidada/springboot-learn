package org.excel.operator.web.service;

import java.util.Map;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.model.params.LoginParam;

public interface LoginService {

  /**
   * 登录
   *
   * @param loginParam 登录信息
   * @return 是否登录成功信息
   */
  ResponseResult<Map<String, Object>> login(LoginParam loginParam);

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
