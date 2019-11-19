package org.excel.operator.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.excel.operator.common.api.ResponseCode;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/19 18:55
 * @see org.excel.operator.controller
 */
public class BaseController {

  private final Logger logger = LoggerFactory.getLogger(BaseController.class);

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Object handleException(HttpServletRequest request, Exception ex) {

    logger.error("全局异常捕获. ", ex);

    //创建一个Map进行封装
    Map<String, Object> responseData = new HashMap<>(16);

    //如果捕获到的异常是我们抛出的异常对象
    if (ex instanceof BusinessException) {
      //强转为自定义类型
      BusinessException businessException = (BusinessException) ex;

      responseData.put("errCode", businessException.getCode());
      responseData.put("errMsg", businessException.getMessage());
    } else {
      // 从枚举类中取出自定义的错误码和错误信息
      responseData.put("errCode", ResponseCode.KNOWN_ERROR.getCode());
      responseData.put("errMsg", ex.getMessage());
    }

    //将map封装后的错误信息传入，统一返回
    return ResponseResult.fail(responseData);
  }
}
