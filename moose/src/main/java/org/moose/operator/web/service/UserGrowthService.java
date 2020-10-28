package org.moose.operator.web.service;

import org.moose.operator.model.dto.UserGrowthDTO;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-10-28 22:31:22:31
 * @see org.moose.operator.web.service
 */
public interface UserGrowthService {

  /**
   * insert a user growth record
   *
   * @param userGrowthDTO user growth dto
   */
  void addGrowth(UserGrowthDTO userGrowthDTO);

  /**
   * update
   *
   * @param userGrowthDTO user growth dto
   * @return update count
   */
  Integer updateGrowth(UserGrowthDTO userGrowthDTO);
}
