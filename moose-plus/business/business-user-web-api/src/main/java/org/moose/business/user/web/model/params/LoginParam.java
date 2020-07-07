package org.moose.business.user.web.model.params;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.moose.business.user.web.model.emun.LoginTypeEnum;
import org.moose.configuration.annotation.EnumValidator;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/21 15:17
 * @see org.moose.business.web.user.web.model
 */
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
