package org.excel.operator.exception;

import com.alibaba.fastjson.JSON;
import org.excel.operator.common.api.ResultCode;
import org.springframework.security.core.AuthenticationException;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/19 18:47
 * @see org.excel.operator.exception
 */
public class BusinessException extends AuthenticationException {

  private Integer code;

  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }

  public BusinessException(ResultCode resultCode) {
    super(resultCode.getMessage());
    this.code = resultCode.getCode();
  }

  public BusinessException(ResultCode resultCode, Object... args) {
    super(String.format("%s %s", resultCode.getMessage(), JSON.toJSONString(args)));
    this.code = resultCode.getCode();
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }
}
