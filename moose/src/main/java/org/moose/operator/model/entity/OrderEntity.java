package org.moose.operator.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author taohua
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Document(indexName = "order", type = "order_info")
public class OrderEntity extends BaseEntity {
  /**
   * 主键 id
   */
  @Id()
  private Long id;

  /**
   * 收货人
   */
  @Field(type = FieldType.Text, analyzer = "ik_max_word")
  private String receiver;

  /**
   * 收货手机号
   */
  @Field(type = FieldType.Keyword)
  private String phone;

  /**
   * 收货地址
   */
  @Field(type = FieldType.Text, analyzer = "ik_max_word")
  private String address;
}
