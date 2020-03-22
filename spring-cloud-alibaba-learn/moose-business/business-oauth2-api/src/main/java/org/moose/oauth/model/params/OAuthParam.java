package org.moose.oauth.model.params;

import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/21 22:38
 * @see org.moose.oauth.model.dto
 */
@Data
public class OAuthParam implements Serializable {
  private String username;
  private String password;
  private String grantType;
  private String clientId;
  private String clientSecret;
}
