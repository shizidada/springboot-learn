package org.moose.operator.model.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/17 00:02
 * @see org.moose.operator.model.dto
 */
@Data
public class ImportExcelInfoDTO extends BaseSearchDTO implements Serializable {

  private static final long serialVersionUID = -4965876569583536645L;
  /**
   * 主键 id
   */
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
