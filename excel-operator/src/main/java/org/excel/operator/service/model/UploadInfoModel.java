package org.excel.operator.service.model;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author taohua
 */

@Data
public class UploadInfoModel extends BaseModel {

  @NotBlank(message = "上传 {platform} 类型不能为空。")
  private String platform;
}
