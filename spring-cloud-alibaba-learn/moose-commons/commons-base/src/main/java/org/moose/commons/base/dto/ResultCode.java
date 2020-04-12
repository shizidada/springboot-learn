package org.moose.commons.base.dto;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/4 20:10
 * @see org.moose.commons.base.dto
 */
public enum ResultCode {
  /**
   * 成功
   */
  SUCCESS(200, "请求成功"),

  // 100001
  /**
   * 账号或密码错误
   */
  ACCOUNT_PASSWORD_ERROR(100001, "用户名或密码错误"),

  /**
   * 账号不能为空
   */
  ACCOUNT_MUST_NOT_BE_NULL(100002, "账号不能为空"),

  /**
   * 账号 ID 为空
   */
  ACCOUNT_ID_MUST_NOT_BE_NULL(100003, "账号 ID 为空"),

  /**
   * 账号名称已存在
   */
  ACCOUNT_NAME_IS_EXIST(100004, "账号名称已存在"),

  // 200001
  /**
   * 密码不能为空
   */
  PASSWORD_MUST_NOT_BE_NULL(200001, "密码不能为空"),

  /**
   * 两次密码不一致
   */
  PASSWORD_NOT_RIGHT(200002, "两次密码不一致"),

  // 300001

  /**
   * 角色不能为空
   */
  ROLE_MUST_NOT_BE_NULL(300001, "角色不能为空"),

  // 400001

  /**
   * OAuth2 授权异常
   */
  OAUTH_ERROR(400001, "授权异常"),

  /**
   * Unauthorized
   */
  OAUTH_UNAUTHORIZED(400002, "Unauthorized"),

  /**
   * Invalid Token
   */
  OAUTH_INVALID_TOKEN(400003, "Invalid Token"),

  // 500001

  /**
   * 手机号不存在
   */
  PHONE_NOT_FOUND(500001, "手机号不存在"),

  /**
   * 手机号不能为空
   */
  PHONE_MUST_NOT_BE_NULL(500002, "手机号不能为空"),

  /**
   * 手机号已存在
   */
  PHONE_IS_EXIST(500003, "手机号已存在"),

  // 600001

  /**
   * 短信验证码不存在
   */
  SMS_CODE_NOT_FOUNT(600001, "短信验证码不存在"),

  /**
   * 短信验证码不能为空
   */
  SMS_CODE_MUST_NOT_BE_NULL(600002, "短信验证码不能为空"),

  /**
   * 短信验证码错误
   */
  SMS_CODE_ERROR(600003, "短信验证码错误"),

  /**
   * 短信体不能为空
   */
  SMS_CODE_BODY_MUST_NOT_BE_NULL(600004, "解析短信体为空"),

  /**
   * 短信验证解析错误
   */
  SMS_CODE_BODY_PARSE_ERROR(600005, "短信验证解析错误"),

  /**
   * 短信发送失败
   */
  SMS_CODE_SEND_FAIL(600006, "短信发送失败"),

  /**
   * 短信校验码已过期
   */
  SMS_CODE_EXPIRED(600007, "短信校验码已过期"),

  // 700001

  /**
   * 登录方式不能为空
   */
  LOGIN_TYPE_MUST_NOT_BE_NULL(700001, "登录方式不能为空"),

  /**
   * 登录方式不正确
   */
  LOGIN_TYPE_ERROR(700002, "登录方式不正确"),

  // 800001
  PAYMENT_ACCOUNT_BALANCE_MUST_NOT_BE_NULL(800001, "账户余额信息不能为空"),

  PAYMENT_INFO_MUST_NOT_BE_NULL(800002, "支付信息不能为空"),

  PAYMENT_INCREASE_ACCOUNT_BALANCE_FAIL(800003, "充值余额失败"),

  PAYMENT_REDUCE_ACCOUNT_BALANCE_FAIL(800004, "扣减余额失败"),

  PAYMENT_ACCOUNT_NOT_FOUND(800005, "该账户不存在"),

  PAYMENT_BALANCE_NOT_ENOUGH(800006, "账户余额不足"),

  PAYMENT_BALANCE_RECORD_ADD_FAIL(800007, "添加账户余额记录失败"),

  /**
   * 网络错误，请检查网络。
   */
  NETWORK_UNKNOWN(460, "网络错误，请检查网络。"),

  /**
   * 参数校验异常
   */
  VALIDATE_PARAM_FAIL(-2, "参数校验异常 %s"),

  /**
   * 未知错误
   */
  UNKNOWN(-1, "未知错误");

  private final Integer code;

  private final String message;

  ResultCode(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public static ResultCode getResultCode(Integer code) {
    for (ResultCode ele : ResultCode.values()) {
      if (ele.getCode() == code.intValue()) {
        return ele;
      }
    }
    return null;
  }
}
