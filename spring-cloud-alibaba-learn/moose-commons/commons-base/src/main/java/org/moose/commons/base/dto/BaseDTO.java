package org.moose.commons.base.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/16 23:21
 * @see org.moose.commons.base.dto
 */
@Data
public class BaseDTO {
  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 更新时间
   */
  private Date updateTime;
}
