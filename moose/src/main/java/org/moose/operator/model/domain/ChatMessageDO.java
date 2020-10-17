package org.moose.operator.model.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author taohua
 */
@Data
@Document(collection = "chat_message")
@EqualsAndHashCode(callSuper = true)
public class ChatMessageDO extends BaseDO implements Serializable {

  private static final long serialVersionUID = -5799222851618141727L;
  @Field("message_id")
  private String messageId;

  @Field("message")
  private String message;

  @Field("is_read")
  private Integer isRead;

  @Field("send_time")
  private LocalDateTime sendTime;

  @Field("read_time")
  private LocalDateTime readTime;

  private UserInfoDO to;

  private UserInfoDO from;
}
