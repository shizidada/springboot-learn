package org.moose.operator.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author taohua
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChatMessageDTO extends BaseDTO implements Serializable {

  private static final long serialVersionUID = 3485857095796059412L;
  private String messageId;

  private String message;

  private Integer isRead;

  private LocalDateTime sendTime;

  private LocalDateTime readTime;

  private UserInfoDTO to;

  private UserInfoDTO from;
}
