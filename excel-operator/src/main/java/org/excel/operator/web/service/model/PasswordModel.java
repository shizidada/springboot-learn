package org.excel.operator.web.service.model;

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

  private Long accountId;

  private String password;
}
