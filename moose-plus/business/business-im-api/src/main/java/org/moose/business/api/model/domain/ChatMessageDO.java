package org.moose.business.api.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moose.commons.base.domain.BaseDO;
import org.moose.provider.account.model.domain.AccountDO;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-04-08 14:39:14:39
 * @see org.moose.business.api.model.domain
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChatMessageDO extends BaseDO {

  /**
   * 消息 id
   */
  private Long messageId;

  /**
   * 消息
   */
  private String message;

  /**
   * 消息状态
   *
   * 1 未读 2 已读
   */
  private Integer messageStatus;

  /**
   * 发送时间
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime sendTime;

  /**
   * 已读时间
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime readTime;

  /**
   * 接受者
   */
  private AccountDO to;

  /**
   * 发送者
   */
  private AccountDO from;
}
