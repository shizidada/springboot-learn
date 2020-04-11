package org.moose.business.user.service;

import org.moose.business.user.model.params.RegisterParam;
import org.moose.commons.base.dto.ResponseResult;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 22:04
 * @see org.moose.business.user.service
 */
public interface RegisterService {

  /**
   * 注册
   *
   * @param registerParam 注册信息
   * @return 是否注册成功
   */
  ResponseResult<?> register(RegisterParam registerParam);
}
