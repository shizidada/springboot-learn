package org.moose.operator.common.api;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/4 20:10
 * @see org.moose.operator.common
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
  ACCOUNT_IS_EMPTY(10001, "账号不能为空"),
  ACCOUNT_OR_PASSWORD_ERROR(10002, "账号或密码错误"),
  ACCOUNT_NAME_EXITS(10003, "账号已存在"),
  ACCOUNT_DISABLED(10004, "账号已禁用"),
  USER_INFO_NOT_EXIST(10005, "用户信息不存在"),
  USER_INFO_UPDATE_FAIL(10006, "更新用户信息失败"),
  USER_INFO_SAVE_FAIL(10007, "保存用户信息失败"),

  PHONE_EXITS(10100, "手机号已存在"),
  PHONE_NOT_EXITS(10101, "手机号不存在"),
  PHONE_NUMBER_IS_EMPTY(10102, "手机号不能为空"),
  PHONE_IS_EXITS_BIND(10103, "手机号已绑定"),
  PHONE_EXITS_WITH_CURRENT(10104, "手机号与当前一致"),
  PHONE_RESET_FAIL(10105, "变更手机号码失败"),

  PASSWORD_ERROR(10200, "两次密码不一致"),
  PASSWORD_IS_EMPTY(10200, "密码不能为空"),

  SMS_CODE_IS_EMPTY(10300, "验证码不能为空"),
  //SMS_CODE_NOT_EXITS(10301, "验证码不存在"),
  //SMS_CODE_IS_EXPIRED(10302, "验证码已过期"),
  SMS_CODE_ERROR(10303, "验证码不正确"),
  SMS_CODE_COUNT(10304, "验证码超过发送次数"),
  SMS_TYPE_ERROR(10305, "短信类型不正确"),

  LOGIN_SUCCESS(20001, "登录成功"),
  LOGIN_FAIL(20002, "登录失败"),
  LOGOUT_SUCCESS(20003, "登出成功"),
  NOT_LOGIN(20004, "未登录，请先登录"),
  LOGIN_MAX_COUNT_FAIL(20006, "登录频繁异常，请稍后再试"),
  LOGIN_METHOD_IS_EMPTY(20007, "登录方式不能为空"),
  LOGIN_SERVER_ERROR(20008, "登录授权异常"),

  REGISTER_SUCCESS(20100, "注册成功"),
  REGISTER_FAIL(20101, "注册失败"),

  /**
   * 文件上传
   */
  FILE_NOT_EMPTY(30001, "文件不能为空"),
  FILE_NOT_SUPPORT(30002, "文件不支持"),
  FILE_TOO_LARGE(30003, "文件太大"),
  FILE_LEGITIMATE_ERROR(30004, "文件不合法"),
  FILE_UPLOAD_ERROR(30005, "文件上传失败"),

  UPLOAD_ATTACHMENT_SIZE_ERROR(300100, "上传的文件超过6个"),
  UPLOAD_ATTACHMENT_RECORD_NOT_EXIST(300101, "文件记录不存在"),

  /**
   * Excel 导出失败
   */
  EXCEL_IMPORT_FAIL(40001, "excel 导入失败"),
  EXCEL_EXPORT_FAIL(40002, "excel 导出失败"),

  /**
   * 参数校验
   */
  PARAMS_VALIDATE_FAIL(-10000, "参数校验失败"),
  TOKEN_IS_EMPTY(-10001, "token must not be null"),
  TOKEN_VALIDATE_FAIL(-10002, "token check fail"),
  TOKEN_INVALID(-10003, "invalid access token"),
  REFRESH_TOKEN_NOT_EXIST(-10004, "refresh token not exist"),
  ACCESS_TOKEN_IS_EMPTY(-10005, "access token is empty"),

  /**
   * 未知错误
   */
  UN_KNOWN_ERROR(-1, "未知错误"),
  ;

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
