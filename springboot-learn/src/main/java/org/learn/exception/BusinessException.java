package org.learn.exception;

import org.learn.common.api.ICommonError;

public class BusinessException extends Exception implements ICommonError {
    private ICommonError commonError;

    //直接接收 EmBusinessError 的传参用于个构造业务异常
    public BusinessException(ICommonError commonError) {
        super();
        this.commonError = commonError;
    }

    //接收自定义errMsg的方式构造业务异常
    public BusinessException(ICommonError commonError, String errMsg) {
        super();
        this.commonError = commonError;
        this.commonError.setMessage(errMsg);
    }


    //异常类中获取错误码和错误信息
    public long getCode() {
        return commonError.getCode();
    }

    public String getMessage() {
        return commonError.getMessage();
    }

    public ICommonError setMessage(String errMsg) {
        commonError.setMessage(errMsg);
        return this;
    }
}
