package org.moose.operator.model.vo;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.moose.operator.annotation.ValueIn;
import org.moose.operator.model.emun.GenderEnum;

/**
 * @author taohua
 */
@Data
public class RegisterInfoVO {

  @NotBlank(message = "账号不能为空")
  private String accountName;

  @NotBlank(message = "密码不能为空")
  private String password;

  @NotBlank(message = "确认密码不能为空")
  private String rePassword;

  @NotBlank(message = "手机号不能为空")
  private String phone;

  @NotBlank(message = "性别不能为空")
  @ValueIn(value = GenderEnum.class, message = "性别不正确")
  private String gender;

  private String avatar;
}
