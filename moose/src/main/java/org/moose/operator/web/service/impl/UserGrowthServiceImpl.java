package org.moose.operator.web.service.impl;

import javax.annotation.Resource;
import org.moose.operator.mapper.UserGrowthMapper;
import org.moose.operator.model.domain.UserGrowthDO;
import org.moose.operator.model.dto.UserGrowthDTO;
import org.moose.operator.web.service.UserGrowthService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-10-28 22:46:22:46
 * @see org.moose.operator.web.service.impl
 */
@Service
public class UserGrowthServiceImpl implements UserGrowthService {

  @Resource
  private UserGrowthMapper userGrowthMapper;

  @Override public void addGrowth(UserGrowthDTO userGrowthDTO) {
    UserGrowthDO userGrowthDO = new UserGrowthDO();
    BeanUtils.copyProperties(userGrowthDTO, userGrowthDO);
    userGrowthMapper.insertGrowth(userGrowthDO);
  }

  @Override public Integer updateGrowth(UserGrowthDTO userGrowthDTO) {
    UserGrowthDO userGrowthDO = new UserGrowthDO();
    BeanUtils.copyProperties(userGrowthDTO, userGrowthDO);
    return userGrowthMapper.updateGrowth(userGrowthDO);
  }
}
