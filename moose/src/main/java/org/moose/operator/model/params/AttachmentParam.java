package org.moose.operator.model.params;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author taohua
 */
@Data
public class AttachmentParam {

  @NotBlank(message = "附件Id不能为空")
  String attachmentId;

  @NotBlank(message = "附件标识不能为空")
  String tag;
}
