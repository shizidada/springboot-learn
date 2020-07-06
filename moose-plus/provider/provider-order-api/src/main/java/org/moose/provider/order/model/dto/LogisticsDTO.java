package org.moose.provider.order.model.dto;

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
 * @date 2020 2020/4/7 23:15
 * @see org.moose.provider.order.model.domain
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LogisticsDTO extends BaseDTO implements Serializable {
  private static final long serialVersionUID = 2400559790167432706L;
  /**
   * 物流 id
   */
  private Long logisticsId;

  /**
   * 物流编号
   */
  private String logisticsNum;

  /**
   * 收货人姓名
   */
  private String receiverName;

  /**
   * 收货人电话
   */
  private String receiverPhone;

  /**
   * 收货人地址
   */
  private String receiverAddress;
}
