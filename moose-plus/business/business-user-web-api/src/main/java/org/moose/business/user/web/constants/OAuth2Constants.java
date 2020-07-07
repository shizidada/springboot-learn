package org.moose.business.user.web.constants;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/21 16:27
 * @see org.moose.business.web.user.web.constants
 */
public interface OAuth2Constants {

  String OAUTH2_TOKEN_URL = "http://localhost:9000/oauth/token";

  String OAUTH2_PASSWORD_GRANT_TYPE = "password";

  String OAUTH2_SMS_GRANT_TYPE = "sms_code";

  String OAUTH2_CLIENT_ID = "client";

  String OAUTH2_CLIENT_SECRET = "secret";
}
