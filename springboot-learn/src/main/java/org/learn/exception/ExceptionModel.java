package org.learn.exception;

import org.learn.common.api.ResultCode;

public class ExceptionModel {
    private Long code = ResultCode.UNKNOWN_ERROR.getCode();
    private String message = ResultCode.UNKNOWN_ERROR.getMessage();

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
