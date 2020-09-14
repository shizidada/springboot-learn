package org.moose.operator.model.params;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author taohua
 */
@Data
public class RegisterInfoParam {

  @NotBlank(message = "账号不能为空")
  private String accountName;

  @NotBlank(message = "密码不能为空")
  private String password;

  @NotBlank(message = "确认密码不能为空")
  private String rePassword;

  @NotBlank(message = "手机号不能为空")
  private String phone;

  @NotBlank(message = "头像不能为空")
  private String avatar;
}
