package org.moose.operator.model.domain;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/17 20:43
 * @see org.moose.operator.model.domain
 */
@Data
public class BaseDO {

  /**
   * 创建时间
   */
  @Field("create_time")
  private LocalDateTime createTime;

  /**
   * 修改时间
   */
  @Field("update_time")
  private LocalDateTime updateTime;
}
