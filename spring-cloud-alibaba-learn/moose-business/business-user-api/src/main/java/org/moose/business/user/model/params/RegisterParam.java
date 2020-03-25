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
@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterParam extends LoginParam implements Serializable {

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
