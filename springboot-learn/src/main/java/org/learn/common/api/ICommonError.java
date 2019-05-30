package org.learn.common.api;

/**
 * 封装API的错误码
 */
public interface ICommonError {
    public long getCode();

    public String getMessage();

    //自定义错误信息
    public ICommonError setMessage(String errMsg);
}
