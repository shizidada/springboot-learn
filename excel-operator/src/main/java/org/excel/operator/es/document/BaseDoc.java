package org.excel.operator.es.document;

import java.util.Date;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author taohua
 */
@Data
public class BaseDoc {

  @Field(type = FieldType.Date, format = DateFormat.basic_ordinal_date_time_no_millis)
  private Date createTime;

  @Field(type = FieldType.Date, format = DateFormat.basic_ordinal_date_time_no_millis)
  private Date updateTime;
}
