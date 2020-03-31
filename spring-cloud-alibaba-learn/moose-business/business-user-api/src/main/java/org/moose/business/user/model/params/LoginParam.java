package org.moose.business.user.model.params;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moose.commons.base.dto.BaseDTO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/21 15:17
 * @see org.moose.business.user.model
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginParam extends BaseDTO implements Serializable {

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
  private String loginType;
}
