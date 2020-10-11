package org.moose.operator.model.params;

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
 * @date 2020-10-11 09:59:09:59
 * @see org.moose.operator.model.params
 */
@Data
public class UserInfoParam {

  @NotBlank(message = "用户名不能为空")
  private String userName;

  @NotBlank(message = "性别不能为空")
  @ValueIn(value = GenderEnum.class, message = "性别不正确")
  private String gender;

  private String avatar;

  private String email;

  private String job;

  private String address;

  private String description;
}
