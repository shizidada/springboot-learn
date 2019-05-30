package org.learn.handler;

import lombok.extern.slf4j.Slf4j;
import org.learn.common.api.AjaxResult;
import org.learn.common.api.ResultCode;
import org.learn.exception.BusinessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map<String, Object> exceptionHandler(Exception ex) {
        ex.printStackTrace();
        // 创建一个Map进行封装
        Map<String, Object> responseData = new HashMap<String, Object>();
        responseData.put("status", false);
        responseData.put("data", null);
        // 如果捕获到的异常是我们抛出的异常对象
        // 捕获到异常后，进入这个方法中处理，需要做的是在这个方法内封装异常返回信息给页面
        // 调用自定义的统一数据json格式返回
        if (ex instanceof BusinessException) {
            //强转为自定义类型
            BusinessException businessException = (BusinessException) ex;
            responseData.put("code", businessException.getCode());
            responseData.put("message", businessException.getMessage());
        } else if (ex instanceof UsernameNotFoundException) {
            responseData.put("code", ResultCode.MEMBER_PASSWORD_NOT_EXIST.getCode());
            responseData.put("message", ResultCode.MEMBER_PASSWORD_NOT_EXIST.getMessage());
        } else {
            // 从枚举类中取出自定义的错误码和错误信息
            responseData.put("code", ResultCode.UNKNOWN_ERROR.getCode());
            responseData.put("message", ex.getMessage());
        }
        return responseData;
    }
}
