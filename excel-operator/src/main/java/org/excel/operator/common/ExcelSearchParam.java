package org.excel.operator.common;

import java.util.Date;
import lombok.Data;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/29 21:01
 * @see org.excel.operator.common
 */

@Data
public class ExcelSearchParam {

  private int pageNum;

  private int pageSize;

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
   * 创建时间
   */
  private Date createTime;

  /**
   * 修改时间
   */
  private Date updateTime;
}
