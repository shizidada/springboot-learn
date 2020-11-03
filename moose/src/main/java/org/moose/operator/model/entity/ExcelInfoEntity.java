package org.moose.operator.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/27 15:06
 * @see org.moose.operator.model.entity
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExcelInfoEntity extends BaseEntity {

  /**
   * 主键 id
   */
  @Id()
  private Long id;

  /**
   * iccid SIM卡卡号
   */
  private String iccid;

  /**
   * 运营商
   */
  private String operators;

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

  /**
   * 平台
   */
  private String platform;
}
