package org.moose.operator.model.params;

import com.sun.org.apache.bcel.internal.generic.JSR;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
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

  /** TODO： JSR 303 且套校验 */
  private List<AttachmentParam> attachmentIds;
}
