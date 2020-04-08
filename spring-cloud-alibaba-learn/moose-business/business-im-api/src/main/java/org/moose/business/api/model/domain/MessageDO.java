package org.moose.business.api.model.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moose.commons.base.domain.BaseDO;
import org.moose.provider.account.model.domain.AccountDO;

/**
 *
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
public class MessageDO extends BaseDO {

  /**
   * 消息 id
   */
  private Long messageId;

  /**
   * 消息
   */
  private String message;

  private Integer messageStatus;

  /**
   * 接受者
   */
  private AccountDO to;

  /**
   * 发送者
   */
  private AccountDO from;
}
