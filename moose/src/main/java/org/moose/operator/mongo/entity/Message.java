package org.moose.operator.mongo.entity;

import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author taohua
 */
@Data
@Builder
@Document(collection = "message")
public class Message {

  @Field("message_id")
  private String messageId;

  @Field("is_read")
  private Integer isRead;

  @Field("send_date")
  private Date sendDate;

  @Field("read_date")
  private Date readDate;

  @Field("msg")
  private String msg;

  private User to;

  private User from;
}
