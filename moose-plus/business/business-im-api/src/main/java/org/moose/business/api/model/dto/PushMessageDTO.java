package org.moose.business.api.model.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moose.commons.base.dto.BaseDTO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/8 21:25
 * @see org.moose.business.api.model.dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PushMessageDTO extends BaseDTO implements Serializable {

  private static final long serialVersionUID = -6253608852573502854L;

  /**
   * 消息标题
   */
  private String title;

  /**
   * 消息类型
   */
  private String content;
}
