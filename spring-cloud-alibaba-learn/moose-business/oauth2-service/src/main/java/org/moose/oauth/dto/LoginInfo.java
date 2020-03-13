package org.moose.oauth.dto;

import lombok.Data;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-13 13:16:13:16
 * @see org.moose.oauth.dto
 */
@Data
public class LoginInfo {
  private String name;
  private String avatar;
  private String nickName;
}
