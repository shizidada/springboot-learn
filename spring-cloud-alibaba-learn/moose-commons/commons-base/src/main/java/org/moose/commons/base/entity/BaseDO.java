package org.moose.commons.base.entity;

import java.util.Date;
import lombok.Data;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/15 18:02
 * @see org.moose.commons.base.entity
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
