package org.learn.handler;

import lombok.extern.slf4j.Slf4j;
import org.learn.exception.ExceptionModel;
import org.learn.security.jwt.AuthExceptionUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 如果捕获到的异常是我们抛出的异常对象
    // 捕获到异常后，进入这个方法中处理，需要做的是在这个方法内封装异常返回信息给页面
    // 调用自定义的统一数据 json 格式返回
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map<String, Object> exceptionHandler(Exception ex) {
        Map<String, Object> responseData = new HashMap<String, Object>();
        ExceptionModel model = AuthExceptionUtil.processException(ex);
        responseData.put("code", model.getCode());
        responseData.put("message", model.getMessage());
        responseData.put("status", false);
        responseData.put("data", null);
        log.debug("全局异常捕获 {} :: ", ex.getMessage());
        return responseData;
    }
}
