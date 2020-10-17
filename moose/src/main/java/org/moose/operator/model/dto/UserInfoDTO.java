package org.moose.operator.model.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * Description: Privacy user info
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-09-20 18:12:18:12
 * @see org.moose.operator.model.dto
 */
@Data
public class UserInfoDTO extends UserBaseInfoDTO implements Serializable {

  private static final long serialVersionUID = 2261972319373525797L;
  private String phone;

  private String email;

  private String job;

  private String address;
}
