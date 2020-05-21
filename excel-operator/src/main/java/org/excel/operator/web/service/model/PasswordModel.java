package org.excel.operator.web.service.model;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/17 00:07
 * @see org.excel.operator.web.service.model
 */
@Data
public class PasswordModel extends BaseModel {

  private Long passwordId;

  @NotBlank(message = "账户 Id 不能为空。")
  private Long accountId;

  @NotBlank(message = "密码不能为空。")
  private String password;
}
