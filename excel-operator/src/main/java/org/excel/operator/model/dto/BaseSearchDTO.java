package org.excel.operator.model.dto;

import lombok.Data;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/4 20:24
 * @see org.excel.operator.common
 */
@Data
public class BaseSearchDTO extends BaseDTO {

  private Integer pageNum;

  private Integer pageSize;
}
