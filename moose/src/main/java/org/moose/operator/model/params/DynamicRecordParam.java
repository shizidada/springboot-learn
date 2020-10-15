package org.moose.operator.model.params;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author taohua
 */
@Data
public class DynamicRecordParam {

  /**
   * 动态标题
   */
  @NotBlank(message = "动态标题不能为空")
  private String title;

  /**
   * 动态描述
   */
  @NotBlank(message = "动态描述不能为空")
  private String description;
}
