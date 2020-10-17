package org.moose.operator.model.dto;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/4 20:24
 * @see org.moose.operator.common
 */
@Data
public class BaseDTO {

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 修改时间
   */
  private LocalDateTime updateTime;
}
