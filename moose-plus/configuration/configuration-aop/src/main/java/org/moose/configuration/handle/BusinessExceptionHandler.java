package org.moose.configuration.handle;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcException;
import org.moose.commons.base.dto.ResponseResult;
import org.moose.commons.base.dto.ResultCode;
import org.moose.commons.base.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
 * @see org.moose.configuration.handle
 */

@Slf4j
@ControllerAdvice
public class BusinessExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handlerException(HttpServletRequest request, Exception ex) {
    Integer code = ResultCode.UNKNOWN.getCode();
    String message = ResultCode.UNKNOWN.getMessage();
    // 业务异常
    if (ex instanceof BusinessException) {
      BusinessException bse = (BusinessException) ex;
      code = bse.getCode();
      message = bse.getMessage();
    }

    /**
     * RpcException 异常捕获
     */
    if (ex instanceof RpcException) {
      RpcException rpc = (RpcException) ex;
      code = rpc.getCode();
      message = rpc.getMessage();
    }

    if (ex instanceof HttpMessageNotReadableException) {
      HttpMessageNotReadableException hmnre = (HttpMessageNotReadableException) ex;
      code = HttpStatus.BAD_REQUEST.value();
      message = hmnre.getMessage();
    }
    log.warn("[全局业务异常]", ex);
    return new ResponseEntity<>(new ResponseResult<>(code, message), HttpStatus.BAD_REQUEST);
  }
}
