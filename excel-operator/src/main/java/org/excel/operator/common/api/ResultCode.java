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

  LOGIN_SUCCESS(20001, "登录成功"),
  LOGIN_FAIL(20002, "登录失败"),

  RE_PASSWORD_ERROR(20003, "两次密码不一致"),
  REGISTER_SUCCESS(20004, "注册成功"),
  REGISTER_FAIL(20005, "注册失败"),

  MEMBER_LOGOUT_SUCCESS(20006, "登出成功"),

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
  VALIDATE_FAIL(-10000, "参数校验失败"),

  TOKEN_VALIDATE_EMPTY(-10001, "Token 不能为空"),
  TOKEN_VALIDATE_FAIL(-10002, "Token 校验失败"),

  /**
   * 未知错误
   */
  KNOWN_ERROR(-99999, "未知错误");

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
