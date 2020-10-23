package org.moose.operator.model.vo;

import lombok.Data;

/**
 * @author taohua
 */
@Data
public class AuthTokenVO {

  private String accessToken;

  private String refreshToken;
}
