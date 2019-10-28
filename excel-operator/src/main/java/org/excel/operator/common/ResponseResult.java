package org.excel.operator.common;

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
  private Long code;

  private Boolean status;

  private T data;

  private String message;

  public ResponseResult() {
  }

  public ResponseResult(Long code, Boolean status, String message, T data) {
    this.code = code;
    this.status = status;
    this.data = data;
    this.message = message;
  }

  public Long getCode() {
    return code;
  }

  public void setCode(Long code) {
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
