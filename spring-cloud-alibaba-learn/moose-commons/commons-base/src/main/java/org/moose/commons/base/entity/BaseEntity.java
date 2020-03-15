package org.moose.commons.base.entity;

import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@Getter
@Setter
@ToString
public class BaseEntity {
  private Date createTime;
  private Date updateTime;
}
