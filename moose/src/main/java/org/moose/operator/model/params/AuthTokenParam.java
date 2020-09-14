package org.moose.operator.model.params;

import java.io.Serializable;

/**
 * @author taohua
 */
public class AuthTokenParam implements Serializable {

  private String accessToken;

  private String refreshToken;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
