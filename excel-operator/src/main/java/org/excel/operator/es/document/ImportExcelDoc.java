package org.excel.operator.es.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author taohua
 */

@Data
@Document(indexName = "moose", type = "excel_info")
public class ImportExcelDoc extends BaseDoc {
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
