package org.excel.operator.entity;

import java.util.Date;
import lombok.Data;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/27 15:06
 * @see org.excel.operator.entity
 */
@Data
public class ImportExcelDO extends BaseDO {

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
}
