package org.moose.operator.model.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/17 00:07
 * @see org.moose.operator.model.dto
 */
@Data
public class AccountDTO extends BaseDTO implements Serializable {

  private Long accountId;

  private String accountName;

  private String status;

  private String avatar;

  private String gender;
}
