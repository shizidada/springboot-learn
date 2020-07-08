package org.moose.business.user.web.model.params;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.moose.business.user.web.model.emun.SmsCodeEnum;
import org.moose.configuration.annotation.EnumValidator;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/21 22:38
 * @see org.moose.business.user.web.model.params
 */
@Data
public class RegisterParam implements Serializable {

  private static final long serialVersionUID = 8200860755139836411L;

  @NotBlank(message = "手机号不能为空")
  private String phone;

  @NotBlank(message = "账号不能为空")
  private String accountName;

  @NotBlank(message = "密码不能为空")
  private String password;

  @NotBlank(message = "二次密码不能为空")
  private String rePassword;

  @NotNull(message = "注册来源不能为空")
  private Integer sourceType;

  /////////// sms ///////////

  @NotBlank(message = "短信类型不能为空")
  @EnumValidator(value = SmsCodeEnum.class, message = "短信类型不正确")
  private String type;

  @NotBlank(message = "短信验证码不能为空")
  private String verifyCode;

  /**
   * 短信验证码 token 发送时会返回客户端
   */
  @NotBlank(message = "短信验证令牌")
  private String smsToken;
}
