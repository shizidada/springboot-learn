package org.learn.handler;

import lombok.extern.slf4j.Slf4j;
import org.learn.exception.ExceptionModel;
import org.learn.utils.ExceptionUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 调用自定义的统一数据 json 格式返回
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map<String, Object> exceptionHandler(Exception ex) {
        Map<String, Object> responseData = new HashMap<>();
        ExceptionModel model = ExceptionUtil.handlerException(ex);
        responseData.put("code", model.getCode());
        responseData.put("message", model.getMessage());
        responseData.put("status", false);
        responseData.put("data", null);
        log.debug("全局异常捕获 {} :: ", ex.getMessage());
        return responseData;
    }
}
