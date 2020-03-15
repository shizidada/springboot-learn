package org.moose.commons.base.handle;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.moose.commons.base.dto.ResponseResult;
import org.moose.commons.base.dto.ResultCode;
import org.moose.commons.base.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/14 22:07
 * @see org.moose.commons.base.handle
 */

@Slf4j
@ControllerAdvice
public class BusinessExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<?> handlerException(HttpServletRequest request, Exception ex) {
    ResponseResult error = new ResponseResult();
    error.setStatus(Boolean.FALSE);
    // 业务异常
    if (ex instanceof BusinessException) {
      error.setCode(((BusinessException) ex).getCode());
      error.setMessage(ex.getMessage());
      log.warn("[全局业务异常]\r\n业务编码：{}\r\n异常记录：{}", error.getCode(), error.getMessage());
    }

    // 未知错误
    else {
      error.setCode(ResultCode.UNKNOWN.getCode());
      error.setMessage(ResultCode.UNKNOWN.getMessage());
    }
    return new ResponseEntity<ResponseResult>(error, HttpStatus.OK);
  }
}
