package org.moose.operator.model.domain;

import lombok.Data;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-10-28 22:32:22:32
 * @see org.moose.operator.model.domain
 */
@Data
public class UserGrowthDO extends BaseDO {

  private String ugId;

  private String userId;

  private Integer growth;
}
