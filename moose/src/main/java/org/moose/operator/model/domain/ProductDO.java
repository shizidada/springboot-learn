package org.moose.operator.model.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author taohua
 */
@Data
@Document(collection = "product")
public class ProductDO {
  @Id
  @Field("product_id")
  private String productId;

  @Field("product_name")
  private String productName;

  private BigDecimal price;

  private String description;

  @Field("create_date")
  private Date createDate;

  @Field("update_date")
  private Date updateDate;

  @Field("user_id")
  private String userId;
}
