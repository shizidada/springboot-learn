package org.moose.operator.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

/**
 * @author taohua
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderEntity extends BaseEntity {
  /**
   * 主键 id
   */
  @Id()
  private Long id;

  /**
   * 收货人
   */
  private String receiver;

  /**
   * 收货手机号
   */
  private String phone;

  /**
   * 收货地址
   */
  private String address;
}
