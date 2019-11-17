package org.excel.operator.service.model;

import java.util.Date;
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
 * @see org.excel.operator.service.model
 */
@Data
public class AccountModel extends BaseModel {

  private Long accountId;

  @NotBlank(message = "账号不能为空。")
  private String accountName;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 修改时间
   */
  private Date updateTime;
}
