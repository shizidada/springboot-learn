package org.moose.operator.model.dto;

import java.io.Serializable;
import lombok.Data;

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
public class BaseSearchDTO extends BaseDTO implements Serializable {

  private static final long serialVersionUID = -2630834847018115027L;
  private Integer pageNum;

  private Integer pageSize;
}
