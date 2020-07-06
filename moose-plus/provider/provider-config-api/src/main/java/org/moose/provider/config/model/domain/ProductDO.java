package org.moose.provider.config.model.domain;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moose.commons.base.domain.BaseDO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/7 21:45
 * @see org.moose.provider.order.model.domain
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductDO extends BaseDO {

  /**
   * 商品 ID
   */
  private Long productId;

  /**
   * 商品名称
   */
  private String productName;

  /**
   * 商品分类
   */
  private String productCategory;

  /**
   * 商品价格
   */
  private BigDecimal productPrice;
}
