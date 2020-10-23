package org.moose.operator.model.vo;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.moose.operator.annotation.ValueIn;
import org.moose.operator.model.emun.LoginTypeEnum;

/**
 * @author taohua
 */
@Data
public class LoginInfoVO {

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
  @ValueIn(value = LoginTypeEnum.class, message = "登录方式不正确")
  private String loginType;
}
