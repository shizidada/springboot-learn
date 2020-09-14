package org.moose.operator.model.dto;

import java.io.Serializable;
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
 * @see org.moose.operator.model.dto
 */

@Data
public class RegisterInfoDTO extends BaseDTO implements Serializable {

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
