package org.excel.operator.common.api;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/28 21:59
 * @see org.excel.operator.common
 */
public class ResponseResult<T> {
  private Integer code;

  private T data;

  private String message;

  private ResponseResult() {
  }

  private ResponseResult(Integer code, String message, T data) {
    this.code = code;
    this.data = data;
    this.message = message;
  }

  public static ResponseResult success(Object data) {
    return new ResponseResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(),
        data);
  }

  public static ResponseResult success(Integer code, String message) {
    return new ResponseResult<>(code, message, null);
  }

  public static ResponseResult success() {
    return success(null);
  }

  public static ResponseResult fail(Object data) {
    return new ResponseResult<>(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMessage(), data);
  }

  public static ResponseResult fail(Integer code, String message) {
    return new ResponseResult<>(code, message, null);
  }

  public static ResponseResult fail(Integer code, Throwable t) {
    return new ResponseResult<>(code, t.getMessage(), null);
  }

  public static ResponseResult fail() {
    return fail(null);
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override public String toString() {
    return "ResponseResult{" +
        "code=" + code +
        ", data=" + data +
        ", message='" + message + '\'' +
        '}';
  }
}
