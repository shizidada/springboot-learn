package org.moose.operator.model.vo;

import lombok.Data;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-10-19 22:56:22:56
 * @see org.moose.operator.model.vo
 */
@Data
public class SearchVO {
  int pageNum = 1;
  int pageSize = 10;
}
