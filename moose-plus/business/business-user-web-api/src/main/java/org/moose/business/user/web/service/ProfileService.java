package org.moose.business.user.web.service;

import org.moose.commons.base.dto.ResponseResult;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/5 22:48
 * @see org.moose.business.web.user.web.service
 */
public interface ProfileService {

  /**
   * 返回账号基本信息
   *
   * @return ResponseResult
   */
  ResponseResult<?> getProfileInfo();
}
