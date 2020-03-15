package org.moose.commons.base.dto;

import java.io.Serializable;

/**
 * 返回通用业务对象
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/28 21:59
 * @see org.moose.commons.base.dto
 */
public class ResponseResult<T> implements Serializable {

  private static final long serialVersionUID = -465227816774150034L;

  private Integer code;

  private Boolean status;

  private T data;

  private String message;

  public ResponseResult() {
  }

  public ResponseResult(T data) {
    this.code = ResultCode.SUCCESS.getCode();
    this.message = ResultCode.SUCCESS.getMessage();
    this.status = Boolean.TRUE;
    this.data = data;
  }

  public ResponseResult(Integer code, String message) {
    this.status = Boolean.FALSE;
    this.code = code;
    this.message = message;
  }

  public ResponseResult(Integer code, Throwable throwable) {
    this.status = Boolean.FALSE;
    this.message = throwable.getMessage();
    this.code = code;
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
