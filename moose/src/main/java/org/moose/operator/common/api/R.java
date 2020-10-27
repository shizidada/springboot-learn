package org.moose.operator.common.api;

import java.io.Serializable;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/28 21:59
 * @see org.moose.operator.common
 */
public class R<T> implements Serializable {
  private static final long serialVersionUID = 6738387175874422264L;
  private Integer code;

  private String message;

  private T data;

  private R() {
  }

  public static <T> R<T> ok() {
    return createResult(null, ResultCode.SUCCESS.getCode(), null);
  }

  public static <T> R<T> ok(T data) {
    return createResult(data, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
  }

  public static <T> R<T> ok(String message) {
    return createResult(null, ResultCode.SUCCESS.getCode(), message);
  }

  public static <T> R<T> ok(T data, String message) {
    return createResult(data, ResultCode.SUCCESS.getCode(), message);
  }

  public static <T> R<T> failed(ResultCode resultCode) {
    return createResult(null, resultCode.getCode(), resultCode.getMessage());
  }

  public static <T> R<T> failed(ResultCode resultCode, T data) {
    return createResult(data, resultCode.getCode(), resultCode.getMessage());
  }

  public static <T> R<T> failed(Integer code, String message) {
    return createResult(null, code, message);
  }

  private static <T> R<T> createResult(T data, Integer code, String message) {
    R<T> r = new R<>();
    r.setCode(code);
    r.setData(data);
    r.setMessage(message);
    return r;
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
    return "R{" +
        "code=" + code +
        ", data=" + data +
        ", message='" + message + '\'' +
        '}';
  }
}
