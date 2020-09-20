package org.moose.operator.model.dto;

import java.io.Serializable;
import lombok.Data;

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

  private String userName;

  private String phone;

  private String avatar;

  private String email;

  private String job;

  private String address;

  private String description;

  private String gender;
}
