package org.excel.operator.es.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
@Document(indexName = "moose", type = "excel_info")
public class ExcelInfoEntity extends BaseEntity {

  /**
   * 主键 id
   */
  @Id()
  private Long id;

  /**
   * iccid SIM卡卡号
   */
  @Field(type = FieldType.Keyword)
  private String iccid;

  /**
   * 运营商
   */
  @Field(type = FieldType.Keyword)
  private String operators;

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

  /**
   * 平台
   */
  @Field(type = FieldType.Keyword)
  private String platform;
}
