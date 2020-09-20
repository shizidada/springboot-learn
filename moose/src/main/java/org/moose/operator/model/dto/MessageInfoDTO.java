package org.moose.operator.model.dto;

import lombok.Data;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-09-20 16:29:16:29
 * @see org.moose.operator.model.document
 */
@Data
public class MessageInfoDTO extends BaseDTO {
  private String toId;

  private String message;
}
