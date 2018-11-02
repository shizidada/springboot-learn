package org.learn.handler;

import lombok.extern.slf4j.Slf4j;
import org.learn.exception.CustomException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Map<String, Object> errorRuntimeException(RuntimeException e) {
        e.printStackTrace();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("error", "RuntimeException");
        return map;
    }

    // @ExceptionHandler 捕获同名异常只能存在一个
    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public Map<String, Object> errorCustomException(CustomException e) {
        e.printStackTrace();

        Map<String, Object> map = new HashMap<String, Object>();
        log.info("GlobalExceptionHandler CustomException 。。。。");
        map.put("code", e.getCode());
        map.put("message", e.getMessage());
        return map;
    }

//    @ExceptionHandler(CustomException.class)
//    public String errorCustomException2(CustomException e, Model model) {
//        model.addAttribute("error", e.getMessage());
//        return "error";
//    }
}
