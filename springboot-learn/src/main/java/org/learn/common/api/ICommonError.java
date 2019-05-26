package org.learn.common.api;

/**
 * 封装API的错误码
 */
public interface ICommonError {
    public long getErrCode();

    public String getErrMessage();

    //自定义错误信息
    public ICommonError setErrMessage(String errMsg);
}
