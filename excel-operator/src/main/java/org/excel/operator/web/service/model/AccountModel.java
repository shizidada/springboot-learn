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
public class AccountModel extends BaseModel {

  private Long accountId;

  @NotBlank(message = "账号不能为空。")
  private String accountName;
}
