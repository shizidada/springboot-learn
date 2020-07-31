package org.excel.operator.model.params;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.excel.operator.annotation.EnumValidator;
import org.excel.operator.model.emun.LoginTypeEnum;

@Data
public class LoginParam implements Serializable {

  private static final long serialVersionUID = 8989667558166315317L;

  /**
   * 账号
   */
  private String accountName;

  /**
   * 密码
   */
  private String password;

  /**
   * 手机号码
   */
  private String phone;

  /**
   * 短信验证码
   */
  private String smsCode;

  @NotBlank(message = "登录方式不能为空")
  @EnumValidator(value = LoginTypeEnum.class, message = "登录方式不正确")
  private String loginType;
}