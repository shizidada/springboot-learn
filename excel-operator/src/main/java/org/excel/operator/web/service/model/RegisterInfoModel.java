package org.excel.operator.web.service.model;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/20 19:56
 * @see org.excel.operator.web.service.model
 */

@Data
public class RegisterInfoModel extends BaseModel {

  @NotBlank(message = "账号不能为空。")
  private String accountName;

  @NotBlank(message = "密码不能为空。")
  private String password;

  @NotBlank(message = "确认密码不能为空。")
  private String rePassword;
}
