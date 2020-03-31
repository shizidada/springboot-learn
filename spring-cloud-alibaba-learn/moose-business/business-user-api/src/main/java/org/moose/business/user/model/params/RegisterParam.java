package org.moose.business.user.model.params;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/21 22:38
 * @see org.moose.business.user.model.params
 */
@Data
public class RegisterParam implements Serializable {

  private static final long serialVersionUID = 8200860755139836411L;

  @NotBlank(message = "账号不能为空")
  private String accountName;

  @NotBlank(message = "密码不能为空")
  private String password;

  @NotBlank(message = "二次密码不能为空")
  private String rePassword;

  /**
   * 手机号码
   */
  @NotBlank(message = "手机号不能为空")
  private String phone;

  /**
   * 注册来源
   */
  private Integer sourceType;
}
