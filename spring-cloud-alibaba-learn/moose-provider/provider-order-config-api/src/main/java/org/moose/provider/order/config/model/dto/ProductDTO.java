package org.moose.provider.order.config.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @date 2020 2020/4/7 21:45
 * @see org.moose.provider.order.model.domain
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductDTO extends BaseDTO implements Serializable {

  private static final long serialVersionUID = 7210234374430094392L;

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
