package org.moose.user.web.dto;

import java.util.Date;
import lombok.Data;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-13 13:32:13:32
 * @see org.moose.user.web.dto
 */
@Data
public class UserInfo {
  private Long userId;
  private String username;
  private Date birthday;
}
