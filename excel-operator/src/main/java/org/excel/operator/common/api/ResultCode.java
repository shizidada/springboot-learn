package org.excel.operator.common.api;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/4 20:10
 * @see org.excel.operator.common
 */
public enum ResultCode {
  /**
   * 成功
   */
  SUCCESS(200, "请求成功"),

  /**
   * 失败
   */
  FAIL(400, "请求失败"),

  /**
   * 登录
   */
  ACCOUNT_NOT_EMPTY(10001, "账号不能为空"),
  ACCOUNT_OR_PASSWORD_ERROR(10002, "账号或密码错误"),
  ACCOUNT_NAME_EXITS(10003, "账号已存在"),
  ACCOUNT_DISABLED(10004, "账号已禁用"),

  PHONE_EXITS(10100, "手机号已存在"),
  PHONE_NOT_EXITS(10101, "手机号不存在"),
  PHONE_MUST_NOT_EMPTY(10102, "手机号不能为空"),

  PASSWORD_ERROR(10200, "两次密码不一致"),

  SMS_CODE_IS_EMPTY(10300, "验证码不能为空"),
  SMS_CODE_NOT_EXITS(10301, "验证码不存在"),
  SMS_CODE_IS_EXPRIED(10302, "验证码已过期"),
  SMS_CODE_ERROR(10303, "验证码不正确"),
  SMS_CODE_COUNT(10304, "验证码超过发送次数"),

  REGISTER_SUCCESS(20006, "注册成功"),
  REGISTER_FAIL(20007, "注册失败"),

  LOGIN_SUCCESS(20001, "登录成功"),
  LOGIN_FAIL(20002, "登录失败"),
  LOGOUT_SUCCESS(20003, "登出成功"),
  NOT_LOGIN(20004, "未登录，请先登录"),
  LOGIN_MAX_COUNT_FAIL(20006, "频繁登录"),

  /**
   * 文件上传
   */
  FILE_NOT_EMPTY(30001, "文件不能为空，请选择重新上传"),
  FILE_NOT_SUPPORT(30002, "文件不支持，请重新上传"),
  FILE_MUCH(30003, "文件太大，请重新上传"),

  /**
   * Excel 导出失败
   */
  EXCEL_IMPORT_FAIL(40001, "Excel 导入失败"),
  EXCEL_EXPORT_FAIL(40002, "Excel 导出失败"),

  /**
   * 参数校验
   */
  PARAMS_VALIDATE_FAIL(-10000, "参数校验失败"),
  TOKEN_VALIDATE_EMPTY(-10001, "Token 不能为空"),
  TOKEN_VALIDATE_FAIL(-10002, "Token 校验失败"),

  /**
   * 未知错误
   */
  UN_KNOWN_ERROR(-99999, "未知错误");

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
}
