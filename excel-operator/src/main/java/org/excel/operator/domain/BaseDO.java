package org.excel.operator.domain;

import java.util.Date;
import lombok.Data;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/17 20:43
 * @see org.excel.operator.entity
 */
@Data
public class BaseDO {

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 修改时间
   */
  private Date updateTime;
}
