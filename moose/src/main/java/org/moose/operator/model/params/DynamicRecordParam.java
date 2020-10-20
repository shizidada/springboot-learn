package org.moose.operator.model.params;

import java.util.List;
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
  @NotBlank(message = "动态内容不能为空")
  private String content;

  private List<AttachmentParam> attachmentIds;
}
