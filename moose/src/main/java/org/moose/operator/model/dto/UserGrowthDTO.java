package org.moose.operator.model.dto;

import lombok.Data;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-10-28 22:34:22:34
 * @see org.moose.operator.model.dto
 */
@Data
public class UserGrowthDTO extends BaseDTO {

  private String ugId;

  private String userId;

  private Integer growth;
}
