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
public class ResponseResult<T> implements Serializable {
  private static final long serialVersionUID = 6738387175874422264L;
  private Integer code;

  private String message;

  private T data;

  private ResponseResult() {
  }

  public ResponseResult(T data) {
    this.code = ResultCode.SUCCESS.getCode();
    this.message = ResultCode.SUCCESS.getMessage();
    this.data = data;
  }

  public ResponseResult(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public ResponseResult(Integer code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public ResponseResult(T data, String message) {
    this.code = ResultCode.SUCCESS.getCode();
    this.message = message;
    this.data = data;
  }

  public static <T> ResponseResult<T> ok() {
    return restResult(null, ResultCode.SUCCESS.getCode(), null);
  }

  public static <T> ResponseResult<T> ok(T data) {
    return restResult(data, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
  }

  public static <T> ResponseResult<T> ok(T data, String msg) {
    return restResult(data, ResultCode.SUCCESS.getCode(), msg);
  }

  public static <T> ResponseResult<T> failed(ResultCode resultCode, T data) {
    return restResult(data, resultCode.getCode(), resultCode.getMessage());
  }

  private static <T> ResponseResult<T> restResult(T data, Integer code, String message) {
    ResponseResult<T> apiResult = new ResponseResult<>();
    apiResult.setCode(code);
    apiResult.setData(data);
    apiResult.setMessage(message);
    return apiResult;
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
