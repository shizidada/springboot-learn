package org.moose.business.user.web.service.fallback;

import lombok.extern.slf4j.Slf4j;
import org.moose.commons.base.dto.ResponseResult;

/**
 *
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-04-08 13:22:13:22
 * @see org.moose.business.user.web.service.fallback
 */
@Slf4j
public class ProfileServiceFallback {

  public static ResponseResult<?> getProfileInfo(Throwable ex) {
    log.info("ProfileServiceFallback #getProfileInfo {}", ex.getMessage());
    return null;
  }
}
