package org.excel.operator.web.service.model;

import java.util.Date;
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
public class BaseModel {

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 修改时间
   */
  private Date updateTime;
}
