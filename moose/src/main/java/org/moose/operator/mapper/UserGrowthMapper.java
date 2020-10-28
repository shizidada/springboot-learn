package org.moose.operator.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.moose.operator.model.domain.UserGrowthDO;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-10-28 22:35:22:35
 * @see org.moose.operator.mapper
 */
@Mapper
public interface UserGrowthMapper {

  /**
   * insert a user growth record
   *
   * @param userGrowthDO user growth do
   */
  void insertGrowth(UserGrowthDO userGrowthDO);

  /**
   * update user growth do
   *
   * @param userGrowthDO user growth do
   * @return update count
   */
  Integer updateGrowth(UserGrowthDO userGrowthDO);
}
