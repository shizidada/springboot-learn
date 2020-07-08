package org.moose.business.user.web.service;

import org.moose.business.user.web.model.params.LoginParam;
import org.moose.commons.base.dto.ResponseResult;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 22:02
 * @see org.moose.business.user.web.service
 */
public interface LoginService {

  /**
   * 登录
   *
   * @param loginParam 登录信息
   * @return 是否登录成功
   */
  ResponseResult<?> login(LoginParam loginParam);
}
