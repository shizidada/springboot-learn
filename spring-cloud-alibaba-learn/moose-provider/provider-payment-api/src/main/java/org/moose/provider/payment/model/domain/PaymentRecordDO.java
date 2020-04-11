package org.moose.provider.payment.model.domain;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moose.commons.base.domain.BaseDO;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 10:16
 * @see org.moose.provider.payment.model.domain
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentRecordDO extends BaseDO {

  /**
   * 支付 id
   */
  private Long paymentId;

  /**
   * 账号 id
   */
  private Long accountId;

  /**
   * 支付金额
   */
  private BigDecimal paymentAmount;

  /**
   * 支付状态
   *
   * 0 -> 未支付
   * 1 -> 已支付
   */
  private Integer paymentStatus;

  /**
   * 支付方式
   *
   * 0 -> 银行卡
   * 1 -> 支付宝
   * 2 -> 微信
   */
  private Integer paymentMethod;

  /**
   * 支付分类
   */
  private String paymentCategory;

  /**
   * 支付流水编号
   */
  private String paymentNum;

  /**
   * 支付标签和备注
   */
  private String paymentTag;
}
