package org.moose.operator.constant;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-10-17 10:30:10:30
 * @see org.moose.operator.constant
 */
public interface PermitAllConstants {

  String LOGIN_IN_URL = "/api/v1/account/login";

  String LOGIN_OUT_URL = "/api/v1/account/logout";

  String REGISTER_URL = "/api/v1/account/register";

  String LOGIN_STATUS_URL = "/api/v1/account/status";

  String GET_REFRESH_TOKEN_URL = "/api/v1/account/getRefreshToken";

  String REFRESH_TOKEN_URL = "/api/v1/account/refreshToken";

  String SEND_SMS_CODE_URL = "/api/v1/sms/send";

  String DYNAMIC_GET_LIST = "/api/v1/dynamic/getRecommendList";
}
