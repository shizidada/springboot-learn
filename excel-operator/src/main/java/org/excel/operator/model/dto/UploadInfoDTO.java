package org.excel.operator.model.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author taohua
 */

@Data
public class UploadInfoDTO extends BaseDTO {

  @NotBlank(message = "上传 {platform} 类型不能为空")
  private String platform;
}
