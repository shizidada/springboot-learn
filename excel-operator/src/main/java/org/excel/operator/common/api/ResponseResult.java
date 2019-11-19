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

  private Boolean status;

  private T data;

  private String message;

  private ResponseResult() {
  }

  private ResponseResult(Integer code, Boolean status, String message, T data) {
    this.code = code;
    this.status = status;
    this.data = data;
    this.message = message;
  }

  public static ResponseResult success(Object data) {
    return new ResponseResult<>(ResponseCode.SUCCESS.getCode(), Boolean.TRUE,
        ResponseCode.SUCCESS.getMessage(), data);
  }

  public static ResponseResult success() {
    return success(null);
  }

  public static ResponseResult fail(Object data) {
    return new ResponseResult<>(ResponseCode.FAIL.getCode(), Boolean.FALSE,
        ResponseCode.FAIL.getMessage(), data);
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

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
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
        ", status=" + status +
        ", data=" + data +
        ", message='" + message + '\'' +
        '}';
  }
}
