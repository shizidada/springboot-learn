package org.excel.operator.web.service.model;

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
public class BaseSearchModel extends BaseModel {

  private int pageNum;

  private int pageSize;
}
