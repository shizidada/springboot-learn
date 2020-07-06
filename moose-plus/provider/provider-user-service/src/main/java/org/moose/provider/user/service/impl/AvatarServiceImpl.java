package org.moose.provider.user.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.moose.provider.user.service.AvatarService;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/8 20:07
 * @see org.moose.provider.user.service.impl
 */
@Service(version = "1.0.0")
public class AvatarServiceImpl implements AvatarService {

  @Override
  public int addAvatar() {
    return 0;
  }
}
