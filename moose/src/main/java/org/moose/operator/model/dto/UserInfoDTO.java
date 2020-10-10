package org.moose.operator.model.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.moose.operator.annotation.ValueIn;
import org.moose.operator.model.emun.GenderEnum;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-09-20 18:12:18:12
 * @see org.moose.operator.model.dto
 */
@Data
public class UserInfoDTO implements Serializable {

  private String userId;

  @NotBlank(message = "用户名不能为空")
  private String userName;

  @NotBlank(message = "手机号不能为空")
  private String phone;

  @NotBlank(message = "性别不能为空")
  @ValueIn(value = GenderEnum.class, message = "性别不正确")
  private String gender;

  private String avatar;

  private String email;

  private String job;

  private String address;

  private String description;
}
